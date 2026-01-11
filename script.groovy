def incrementVersion() {
    echo 'incrementing the application version...'
    sh 'mvn build-helper:parse-version versions:set -DnewVersion=${parsedVersion.majorVersion}.' +
       '${parsedVersion.minorVersion}.$((${parsedVersion.nextIncrementalVersion})) versions:commit'
    def matcher = readFile('pom.xml') =~ '<version>(.+)</version>'
    def version = matcher[0][1]
    env.IMAGE_NAME = "${version}-${BUILD_NUMBER}"
}
def buildJar() {
    echo 'building the application...'
    sh 'mvn package'
}

def buildImage() {
    echo "building the docker image..."
    withCredentials([usernamePassword(credentialsId: 'docker-hub-credentials', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
        sh "docker build -t izik247/java-app:$IMAGE_NAME ."
        sh "echo $PASS | docker login -u $USER --password-stdin"
        sh "docker push izik247/java-app:$IMAGE_NAME"
    }
}

def deployApp() {
    echo "deploying the application to environment..."
}

return this
