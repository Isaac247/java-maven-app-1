def deployApp() {
    input message: 'Do you want to deploy the application?', ok: 'Deploy'
    echo "deploying the application to ${params.ENV} environment..."
}

return this
