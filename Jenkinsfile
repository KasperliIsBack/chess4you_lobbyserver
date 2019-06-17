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
          sh './gradlew sonarqube -Dsonar.projectKey=lobbyserver -Dsonar.organization=chess4you -Dsonar.host.url=https://sonarcloud.io -Dsonar.login=f8b719179bf3f28f6b4292771d0f07cf97f682cc -Dsonar.coverage.jacoco.xmlReportPaths=C:\\git\\chess4you_lobbyserver\\lobbyserver\\build\\code_coverage\\reports\\jacocoXml\\jacocoXml.xml'
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
        sh 'docker rm $(docker ps -aq --filter "server=lobbyserver")'
      }
    }
    stage('docker run') {
      steps {
        sh 'docker run -p 8082:8082 -t com.chess4you/lobbyserver'
      }
    }
  }
}