pipeline {
    agent any
    stages {
        stage('Unit Tests') {
            agent {
                docker {
                    image 'mhart/alpine-node'
                }
            }
            steps {
                sh './gradlew test'

            }
        }
    }
}