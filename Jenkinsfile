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
        }
        post {
            failure {
                echo "Rollback database migrations"
            }
        }
    }
    stage('Build Artifact') {
      steps {
          dir('app/api-users/') {
              sh "./test.sh" 
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
            dir('app/api-users/') {
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
            dir('app/api-users/') {
              sh "mvn -B -DskipTests clean package" 
              archiveArtifacts 'target/*.jar'
            }
        }
    }
    stage('Deliver'){
        when{
            branch 'master'
        }
        steps {
            dir('app/api-users/') {
              sh "./run.sh" 
            }
        }
    }
    stage('Setup Kong'){
        steps {
            dir('kong/') {
                sh "./execute-migrations.sh -kong_host http://localhost:8001 -api_host http://localhost:3100 -silence true"
            }
        }
    }
  }
}