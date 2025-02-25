pipeline {
    agent any
    tools {
        jdk 'C:/Program Files/Java/jdk-23'
    }
    stages {
        stage('Build') {
            steps {
                sh 'mvn clean install'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }
    }
}
