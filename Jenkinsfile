pipeline {
    agent any
    stages {
        stage('Unit Tests') {
            agent {
                docker {
                    image 'openjdk:8-jdk-alpine'
                }
            }
            steps {
                sh './gradlew test --debug'

            }
        }
    }
}
