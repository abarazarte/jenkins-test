pipeline {
  agent any
  tools {
    jdk "Java 8"
    maven "maven-3.5.3"
  }
  stages {
    stage('Build') {
      steps {
        echo "The Maven version should be 3.5.3"
        sh "mvn -version"
        sh 'mvn -B -DskipTests clean package'
      }
    }
    post {
      // Always runs. And it runs before any of the other post conditions.
      always {
        echo "Alwais execute"
      }
      
      success {
        echo "Only when we haven't failed running the first stage"
      }

      failure {
        echo "Only when we fail running the first stage."
      }
    }
  }
}
