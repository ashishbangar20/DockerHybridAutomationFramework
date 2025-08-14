pipeline {
    agent any

    tools {
        maven 'Maven_3.9.6'   // Jenkins → Manage Jenkins → Tools me jo name diya wahi use karo
        jdk 'JDK21'           // Jenkins me configured JDK ka name
    }

    environment {
        REPORT_DIR = 'reports'             // Tumhare ExtentReports ka folder
        REPORT_FILE = 'extent-report.html' // Extent report ka file name
    }

    stages {

        stage('Checkout Code from GitHub') {
            steps {
                echo "📥 Checking out latest code from GitHub..."
                git branch: 'master', url: 'https://github.com/ashishbangar20/DockerHybridAutomationFramework.git'
            }
        }

        stage('Build & Run Selenium Tests') {
            steps {
                echo "🛠️ Building project and running Selenium tests..."
                sh 'mvn clean test -Dbrowser=chrome'
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
