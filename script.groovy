def incrementVersion() {
    echo 'incrementing the application version...'
    sh '''
    mvn build-helper:parse-version versions:set \
    -DnewVersion=\\\${parsedVersion.majorVersion}.\\\${parsedVersion.minorVersion}.\\\${parsedVersion.nextIncrementalVersion} \
    versions:commit
    ''' //building the java maven app using incremental versioning maj:min:patch
    def matcher = readFile('pom.xml') =~ '<version>(.+)</version>' //reads the pom.xml file as an array and saves it to a matcher variable
    def version = matcher[0][1] //[0][1] signifies the version of the app in the array
    env.IMAGE_NAME = "${version}-${BUILD_NUMBER}" // appending the build number from jenkins with the version name saved in the IMAGE_NAME variable
}
def buildJar() {
    echo 'building the application...'
    sh 'mvn clean package'
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
