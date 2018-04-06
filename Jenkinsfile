pipeline {
  agent any
  stages {
    stage('Migrate database') {
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
          sh 'mvn test -DskipTests=false -Denv=development'
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
    stage('Build API app') {
      steps {
        dir(path: 'app/api-users/') {
          sh 'mvn -DskipTests clean package'
        }
      }
    }
    stage('Test API app') {
      steps {
        echo 'Testing app'
      }
      post {
        always {
          echo 'Copying test results'
        }
      }
    }
    stage('Run API app') {
      steps {
        dir(path: 'app/api-users/') {
          script{
            sh './run.sh'
          }
        }
      }
    }
    stage('Migrate kong') {
      steps {
        dir(path: 'kong/') {
          sh './execute-migrations.sh -kong_host http://localhost:8001 -api_host http://localhost:3100 -silence true'
        }
      }
    }
    stage('Test kong migrations') {
      steps {
        dir(path: 'kong/') {
          sh 'mvn test -DskipTests=false -Denv=development'
        }
      }
      post {
        always {
          dir(path: 'kong/') {
            junit '**/target/surefire-reports/TEST-*.xml'
          }
        }
      }
    }
    stage('Install artifact in mvn repository') {
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
    stage('Archive artifact') {
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
  }
  tools {
    jdk 'Java 8'
    maven 'maven-3.5.3'
  }
}