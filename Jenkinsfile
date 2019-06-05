node {
    stage('checkout') {
            git url: 'https://github.com/Bliblob/chess4you_lobbyserver'
    }
    stage('gradle clean') {
        if(isUnix()) {
            sh 'cd ./lobbyserver'
            sh 'sh gradlew clean'
        } else {
            bat 'cd lobbyserver'
            bat 'gradlew.bat clean'
        }
    }
    stage('gradle build') {
        if(isUnix()) {
            sh 'cd ./lobbyserver'
            sh 'sh gradlew build'
        } else {
            bat 'cd lobbyserver'
            bat 'gradlew.bat build'
        }
    }
    stage('gradle run unit tests') {
        if(isUnix()) {
            sh 'cd ./lobbyserver'
            sh 'sh gradlew jacocoTestReport'
        } else {
            bat 'cd lobbyserver'
            bat 'gradlew.bat jacocoTestReport'
        }
    }
    stage('gradle upload code coverage') {
        if(isUnix()) {
            sh 'cd ./lobbyserver'
            sh 'sh gradlew sonarqube'
        } else {
            bat 'cd lobbyserver'
            bat 'gradlew.bat sonarqube'
        }
    }
    stage('gradle deploy war') {
        if(isUnix()) {
            sh 'cd ./lobbyserver'
            sh 'sh gradlew war'
        } else {
            bat 'cd lobbyserver'
            bat 'gradlew.bat war'
        }
    }
}