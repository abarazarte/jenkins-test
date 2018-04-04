pipeline {
  agent any
  stages {
    stage('Setup database') {
      steps {
        echo 'Migrating database'
        dir(path: 'pg/proyecto1/') {
          sh './migrate-status.sh'
          sh './migrate-up.sh'
          sh './migrate-status.sh'
        }
        
      }
      post {
        failure {
          echo 'Rollback database migrations'
          
        }
        
      }
    }
    stage('Build Artifact') {
      steps {
        dir(path: 'app/api-users/') {
          sh 'mvn -DskipTests clean package'
        }
        
      }
    }
    stage('Test Artifact') {
      steps {
        echo 'Testing app'
      }
      post {
        always {
          echo 'Copying test results'
          
        }
        
      }
    }
    stage('Install in maven') {
      when {
        branch 'master'
      }
      steps {
        echo 'Installing on maven repository'
        dir(path: 'app/api-users/') {
          sh 'mvn jar:jar install:install help:evaluate -Dexpression=project.name'
        }
        
      }
    }
    stage('Archive') {
      when {
        branch 'master'
      }
      steps {
        echo 'Archive artifacts'
        dir(path: 'app/api-users/') {
          archiveArtifacts 'target/*.jar'
        }
        
      }
    }
    stage('Deliver') {
      when {
        branch 'master'
      }
      steps {
        dir(path: 'app/api-users/') {
          sh '''kill $(lsof -i :3100 | grep LISTEN | awk \'{print $2}\') &
BUILD_ID=do_not_kill_me &
java -jar target/api-users-1.0.2.jar --server.port=3100 &'''
        }
        
      }
    }
    stage('Setup Kong') {
      steps {
        dir(path: 'kong/') {
          sh './execute-migrations.sh -kong_host http://localhost:8001 -api_host http://localhost:3100 -silence true'
        }
        
      }
    }
  }
  tools {
    jdk 'Java 8'
    maven 'maven-3.5.3'
  }
}