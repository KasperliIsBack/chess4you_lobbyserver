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
          sh './gradlew -x test sonarqube -Dsonar.projectKey=lobbyserver -Dsonar.organization=chess4you -Dsonar.host.url=https://sonarcloud.io -Dsonar.login=f8b719179bf3f28f6b4292771d0f07cf97f682cc -Dsonar.coverage.jacoco.xmlReportPaths=C:\\git\\chess4you_lobbyserver\\lobbyserver\\build\\code_coverage\\reports\\jacocoXml\\jacocoXml.xml'
        }

      }
    }
    stage('docker stop') {
      steps {
        sh '[ -z $(docker ps -aqf "label=server=lobbyServer") ] || docker stop $(docker ps -aqf "label=server=lobbyServer")'
      }
    }
    stage('docker remove') {
      steps {
        sh '[ -z $(docker ps -aqf "label=server=lobbyServer") ] || docker rm $(docker ps -aqf "label=server=lobbyServer")'
      }
    }
    stage('gradle docker build') {
      steps {
        dir(path: 'lobbyserver') {
          sh './gradlew build docker -x test'
          sh 'docker ps -aqf "label=server=lobbyServer"'
        }

      }
    }
    stage('docker run') {
      steps {
        sh 'docker run -d -p 8082:8082 -t com.chess4you/lobbyserver'
      }
    }
  }
}