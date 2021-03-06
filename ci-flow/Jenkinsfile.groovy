pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                sh './gradlew clean build -x test -x integrationTest -x checkstyleMain -x checkstyleTest ' +
                        '-x checkstyleIntegrationTest'
            }
        }
        stage('Checkstyle') {
            steps {
                sh './gradlew checkstyleMain'
            }
        }
        stage('Unit tests') {
            steps {
                sh './gradlew test --info'
            }
        }
        stage('Init and migrate databse') {
            steps {
                sh 'mysql -u ${DB_USERNAME} -p${DB_PASSWORD} -h ${DB_HOST} -P 3306 < ./ci-flow/db/db_init.sql'
                sh './gradlew flywayMigrate'
            }
        }
        stage('Integration tests') {
            steps {
                sh './gradlew integrationTest --info'
            }
        }
    }
    post {
        always {
            junit 'build/test-results/**/*.xml'
        }
    }
}