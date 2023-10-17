
pipeline {
    agent any
  environment {
    MAVEN_ARGS=" -e clean install"
    registry = ""
    dockerContainerName = 'optimistic_goldstine'
    dockerImageName = 'sha256'
  }
  stages {
    stage('Build') {
      steps {
        checkout scm
        sh './mvnw compile'
      }
    }
    stage('Test') {
      steps {
        sh './mvnw test'
        junit '**/target/surefire-reports/TEST-*.xml'
      }
    }
    stage('Package') {
      steps {
        sh './mvnw package -DskipTests'
      }
    }
  }
}
