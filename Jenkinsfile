pipeline {
    agent any

    stages {
        stage('Build and Test') {
            steps {
                bat '''
                    set JAVA_HOME=C:\\Program Files\\Java\\jdk-17
                    set PATH=%JAVA_HOME%\\bin;%PATH%
                    C:\\Users\\SNUC\\Downloads\\apache-maven-3.10.0-bin\\apache-maven-3.10.0\\bin\\mvn.cmd clean test
                '''
            }
        }

        stage('Package') {
            steps {
                bat '''
                    set JAVA_HOME=C:\\Program Files\\Java\\jdk-17
                    set PATH=%JAVA_HOME%\\bin;%PATH%
                    C:\\Users\\SNUC\\Downloads\\apache-maven-3.10.0-bin\\apache-maven-3.10.0\\bin\\mvn.cmd package -DskipTests
                '''
            }
        }

        stage('Copy Artifacts to Workspace') {
            steps {
                bat '''
                    if not exist "surefire-reports" mkdir surefire-reports
                    copy /Y course-registration-app\\target\\surefire-reports\\TEST-*.xml surefire-reports\\
                    
                    if not exist "target" mkdir target
                    copy /Y course-registration-app\\target\\*.jar target\\
                '''
            }
        }
    }

    post {
        always {
            junit 'surefire-reports/TEST-*.xml'
        }
        success {
            archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
        }
    }
}