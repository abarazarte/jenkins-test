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
          dir('pg/proyecto1/') {
              sh "./migrate-status.sh" 
              sh "./migrate-up.sh" 
              sh "./migrate-status.sh" 
          }
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
          dir('app/') {
              sh "mvn -B -DskipTests clean package" 
          }
        }
    }
    stage('Test Artifact') {
        steps {
            echo "Testing app"
            dir('app/') {
              sh "mvn test"
            }
        }
        post {
            always {
                dir('app/') {
                    junit '**/target/surefire-reports/TEST-*.xml'
                }
            }
        }
    }
    stage('Install in maven'){
        when{
            branch 'master'
        }
        steps {
            echo 'Installing on maven repository'
            dir('app/') {
              sh "mvn jar:jar install:install help:evaluate -Dexpression=project.name"
            }
        }
    }
    stage('Archive'){
        when{
            branch 'master'
        }
        steps {
            echo 'Archive artifacts'
            dir('app/') {
              sh "mvn -B -DskipTests clean package" 
              archiveArtifacts 'target/*.jar'
            }
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