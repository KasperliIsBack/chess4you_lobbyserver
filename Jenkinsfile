pipeline {
    stages {
        stage('checkout') {
            git url: 'https://github.com/Bliblob/chess4you_lobbyserver'
        }
        stage('gradle clean') {
            if(isUnix()) {
                sh './gradlew clean'
            } else {
                bat 'gradlew.bat clean'
            }
        }
        stage('gradle build') {
            if(isUnix()) {
                sh './gradlew build'
            } else {
                bat 'gradlew.bat build'
            }
        }
        stage('gradle run unit tests') {
            if(isUnix()) {
                sh './gradlew jacocoTestReport'
            } else {
                bat 'gradlew.bat jacocoTestReport'
            }
        }
        stage('gradle upload code coverage') {
            if(isUnix()) {
                sh './gradlew sonarqube'
            } else {
                bat 'gradlew.bat sonarqube'
            }
        }
        stage('gradle deploy war') {
            if(isUnix()) {
                sh './gradlew war'
            } else {
                bat 'gradlew.bat war'
            }
        }
    }
}