pipeline {
  agent any
  tools {
    jdk "Java 8"
    maven "maven-3.5.3"
  }
  
  stages {
    stage("first stage") {
      steps {
          echo "We're not doing anything particularly special here."
          echo "Just making sure that we don't take longer than five minutes"
          echo "Which, I guess, is kind of silly."
          
          // This'll output 3.3.3, since that's the Maven version we
          // configured above. Well, once we fix the validation error!
          sh "mvn -version" 
        }
      }
    }
    
    stage('second stage') {
      steps {
        echo "The Maven version should be 3.5.3 again"
        sh "mvn -version"
      }
    }
    
    stage('third stage') {
      steps {
        parallel(one: {
                  echo "I'm on the first branch!"
                 },
                 two: {
                   echo "I'm on the second branch!"
                 },
                 three: {
                   echo "I'm on the third branch!"
                   echo "But you probably guessed that already."
                 })
      }
    }
  }
  
  post {
    // Always runs. And it runs before any of the other post conditions.
    //always {
      // Let's wipe out the workspace before we finish!
      //deleteDir()
    //}
    
    success {
      echo "Only when we haven't failed running the first stage"
    }

    failure {
      echo "Only when we fail running the first stage."
    }
  }
}
