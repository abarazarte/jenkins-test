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
    stage('Test database migrations') {
      steps {
        dir(path: 'pg/proyecto1/') {
          sh 'mvn test -Denv=development'
        }
      }
      post {
        always {
          dir(path: 'pg/proyecto1/') {
            junit '**/target/surefire-reports/TEST-*.xml'
          }
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
          sh './run.sh'
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
    stage('Test Kong') {
      steps {
        dir(path: 'kong/') {
          echo 'Testing Kong'
        }
      }
    }
  }
  tools {
    jdk 'Java 8'
    maven 'maven-3.5.3'
  }
}