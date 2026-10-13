pipeline {
    agent any

    tools {
        maven 'Maven 3.10.0' // Matches your environment setup, or use default if preconfigured
        jdk 'JDK 17'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build and Test') {
            steps {
                // Runs clean and test from repository root across reactor
                bat 'mvn clean test'
            }
        }

        stage('Package') {
            steps {
                // Packages the child module jar
                bat 'mvn package -DskipTests'
            }
        }
    }

    post {
        always {
            // Publishes JUnit test reports from the child module surefire-reports path
            junit 'course-registration-app/target/surefire-reports/TEST-*.xml'
        }
        success {
            // Archives the packaged artifact from the child module target path
            archiveArtifacts artifacts: 'course-registration-app/target/*.jar', fingerprint: true
        }
    }
}