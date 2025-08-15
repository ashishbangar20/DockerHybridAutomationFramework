pipeline {
    agent any

    environment {
        IMAGE_NAME = 'hybrid-framework-docker'
        REPORT_DIR = 'reports'
        REPORT_FILE = 'extent-report.html'
    }

    stages {

        stage('Checkout Code from GitHub') {
            steps {
                echo "📥 Checking out latest code from GitHub..."
                git branch: 'master', url: 'https://github.com/ashishbangar20/DockerHybridAutomationFramework.git'
            }
        }

        stage('Build Docker Image') {
            steps {
                echo "🐳 Building Docker image..."
                sh "docker build -t ${IMAGE_NAME} ."
            }
        }

        stage('Run Tests in Docker') {
            steps {
                echo "🛠️ Running Selenium tests inside Docker container..."
                sh "docker run --rm -v \$(pwd)/${REPORT_DIR}:/app/${REPORT_DIR} ${IMAGE_NAME}"
            }
        }

        stage('Publish Extent HTML Report') {
            steps {
                echo "📄 Publishing Extent Report..."
                publishHTML(target: [
                    allowMissing: false,
                    alwaysLinkToLastBuild: true,
                    keepAll: true,
                    reportDir: "${REPORT_DIR}",
                    reportFiles: "${REPORT_FILE}",
                    reportName: 'Extent Report'
                ])
            }
        }
    }

    post {
        success {
            echo "✅ Build & Tests executed successfully!"
        }
        failure {
            echo "❌ Build or Tests failed. Check logs and reports."
        }
        always {
            echo "🧹 Cleaning workspace..."
            deleteDir()
        }
    }
}
