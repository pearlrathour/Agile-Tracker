pipeline {
    agent any

    environment {
        DOCKER_IMAGE = "agile-tracker:latest"
        MONGO_URI = credentials('MONGO_URI')  // Optional: if stored in Jenkins credentials
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/your-org/your-repo.git'
            }
        }

        stage('Build') {
            steps {
                sh './gradlew clean bootJar'
            }
        }

        stage('Docker Build') {
            steps {
                sh 'docker build -t $DOCKER_IMAGE .'
            }
        }

        stage('Docker Run') {
            steps {
                sh 'docker stop agile-tracker || true && docker rm agile-tracker || true'
                sh 'docker run -d -p 8080:8080 --env MONGO_URI=$MONGO_URI $DOCKER_IMAGE'
            }
        }

        stage('DevSecOps Checks') {
            steps {
                echo 'Run security scans, code analysis, linting here'
                // Example: sh 'mvn sonar:sonar' or OWASP dependency check
            }
        }
    }

    post {
        always {
            echo 'Cleaning up...'
            sh 'docker system prune -f'
        }
    }
}
