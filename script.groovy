def buildJar() {
    echo 'building the application...'
    sh 'mvn package'
}

def buildImage() {
    echo "building the docker image..."
    withCredentials([usernamePassword(credentialsId: 'docker-hub-credentials', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
        sh 'docker build -t izik247/java-app:jma-2.2 .'
        sh "echo $PASS | docker login -u $USER --password-stdin"
        sh 'docker push izik247/java-app:jma-2.2'
    }
}

def deployApp() {
    echo "deploying the application to environment..."
}

return this
