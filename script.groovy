def buildJar() {
    echo 'building the application...'
    sh 'mvn package'
}

def buildImage() {
    echo "building the docker image..."
    withCredentials([usernamePassword(credentialsId: 'docker-hub-credentials', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
        sh 'docker build -t izik247/java-app:jma-2.0 .'
        sh "echo $PASS | docker login -u $USER --password-stdin"
        sh 'docker push izik247/java-app:jma-2.0'
    }
}

def deployApp() {
    input message: 'Do you want to deploy the application?', ok: 'Deploy'
    parameters{
        choice(name: 'ENV', choices: ['dev', 'prod', 'staging'], description: 'Select environment')
    }
    echo "deploying the application to ${ENV} environment..."
}

return this
