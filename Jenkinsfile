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
    stage('Install in maven'){
        agent any
        when{
            branch 'master'
        }
        steps {
            echo 'Installing on maven repository'
        }
    }
    stage('Archive'){
        agent any
        when{
            branch 'master'
        }
        steps {
            echo 'Archive artifacts'
        }
    }
  }
}
