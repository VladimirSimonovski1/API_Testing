pipeline {
    agent any
    tools {
        jdk 'JDK 17'
    }
    stages {
        stage('Clean') {
                steps {
                    bat 'mvn clean'
                }
            }

        stage('Build') {
            steps {
                bat 'mvn install'
            }
        }
        stage('Test') {
            steps {
                bat 'mvn test'
            }
        }
    }
}
