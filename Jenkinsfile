pipeline {
    agent any

    environment {
        DOCKER_IMAGE = "agile-tracker:latest"
        MONGO_URI = credentials('MONGO_URI')
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/pearlrathour/Agile-Tracker.git'
            }
        }

        stage('Build') {
            steps {
                sh './gradlew clean bootJar'
            }
        }

        stage('Docker Build & Run') {
            steps {
                script {
                    sh 'docker build -t $DOCKER_IMAGE .'
                    sh 'docker stop agile-tracker || true && docker rm agile-tracker || true'
                    sh 'docker run -d -p 8080:8080 --env MONGO_URI=$MONGO_URI $DOCKER_IMAGE'
                }
            }
        }

        stage('DevSecOps Checks') {
            steps {
                echo 'Run security scans, code analysis, linting here'
            }
        }
    }

    post {
        always {
            script {
                sh 'docker system prune -f'
            }
        }
    }
}
