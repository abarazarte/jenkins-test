pipeline {
  agent any
  tools {
    jdk "Java 8"
    maven "maven-3.5.3"
  }
  
  stages {
    stage('Setup database'){
        steps {
          echo "Migrating database"
          echo "Testing database migrations"
        }
        post {
            failure {
                echo "Rolling back database migrations"
            }
        }
    }
    stage('Build Artifact') {
      steps {
          echo "Building app"
          sh "mvn -B -DskipTests clean package" 
        }
    }
    stage('Test Artifact') {
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
    stage('Setup Kong'){
        steps {
            echo "Executing Kong migrations"
        }
        post {
            failure {
                echo "Rolling back Kong migrations"
            }
        }
    }
  }
}