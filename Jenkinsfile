stage("Prepare container") {
  agent {
    docker {
      image 'openjdk:11.0.5-slim'
      args '-v $HOME/.m2:/root/.m2'
    }
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
