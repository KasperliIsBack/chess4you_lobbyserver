pipeline {
  agent any
  stages {
    stage('gradle clean') {
      steps {
        dir(path: 'lobbyserver') {
          sh './gradlew clean'
        }

      }
    }
    stage('gradle build') {
      steps {
        dir(path: 'lobbyserver') {
          sh './gradlew build -x test'
        }

      }
    }
    stage('gradle code coverage') {
      steps {
        dir(path: 'lobbyserver') {
          sh './gradlew jacocoTestReport'
        }

      }
    }
    stage('gradle sonarqube') {
      steps {
        dir(path: 'lobbyserver') {
          sh ''
        }

      }
    }
    stage('gradle docker') {
      steps {
        dir(path: 'lobbyserver') {
          sh './gradlew build docker -x test'
        }

      }
    }
    stage('docker remove old container') {
      steps {
        sh 'docker rm $(docker ps -a -q --filter "tag=lobbyserver")'
      }
    }
    stage('docker run') {
      steps {
        sh 'docker run -p 8082:8082 -t com.chess4you/lobbyserver'
      }
    }
  }
}
