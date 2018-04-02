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
          sh "mvn -B -DskipTests clean package" 
        }
      }
    stage('Test') {
        steps {
            echo "Testing app"
            sh "mvn test"
            junit '**/target/surefire-reports/TEST-*.xml'
        }
    }
    stage('Install in maven'){
        agent any
        when{
            branch 'master'
        }
        steps {
            echo 'Installing on maven repository'
            sh "mvn jar:jar install:install help:evaluate -Dexpression=project.name"
        }
    }
    stage('Archive'){
        agent any
        when{
            branch 'master'
        }
        steps {
            echo 'Archive artifacts'
            sh "mvn -B -DskipTests clean package" 
            archiveArtifacts 'target/*.jar'
        }
    }
  }
}
