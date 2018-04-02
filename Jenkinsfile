pipeline {
  agent any
  tools {
    jdk "Java 8"
    maven "maven-3.5.3"
  }
  
  stages {
    stage("Build") {
      steps {
          echo "Building app"
          //sh "mvn -version" 
        }
      }
    stage('Test') {
        steps {
            echo "Testing app"
            //sh "mvn -version"
        }
    }
  }
  post {
    always {
         echo "Always run"
    }
    
    success {
      echo "Only when we haven't failed running the first stage"
    }

    failure {
      echo "Only when we fail running the first stage."
    }
  }
}
