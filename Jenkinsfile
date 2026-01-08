#!/usr/bin/env groovy
@Library('jenkins-shared-library')
def gv //importing groovy script function

pipeline {   
    agent any
    parameters {
        choice(name: 'ENV', choices: ['dev', 'prod', 'staging'], description: 'Select environment')
    }
    tools {
        maven 'Maven'
    }
    stages {
        stage("init") {
            steps {
                script {
                    gv = load "script.groovy" //initializing groovy script
                }
            }
        }
        stage("build jar") {
            // when {
            //     expression {
            //         BRANCH_NAME == 'master'
            //     }
            // }
            steps {
                script {
                    buildjar()

                }
            }
        }

        stage("build image") {
            // when {
            //     expression {
            //         BRANCH_NAME == 'master'
            //     }
            // }
            steps {
                script {
                    buildimage 'izik247/java-app:jma-3.1'
                }
            }
        }

        stage("deploy") {
            steps {
                script {
                    gv.deployApp()
                }
            }
        }               
    }
} 
