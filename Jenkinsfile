pipeline {
  agent any
  stages {
    stage('Init database') {
      steps {
        echo 'Init database'
        dir(path: 'scripts') {
          sh './db-init.sh'
        }
      }
    }
    stage('Migrate database') {
      steps {
        echo 'Migrating database'
        dir(path: 'pg/proyecto1/') {
          sh './migrate-status.sh --env=jenkins'
          sh './migrate-up.sh --env=jenkins'
          sh './migrate-status.sh --env=jenkins'
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
          sh './test.sh -Denv=jenkins'
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
    stage('Test API App') {
      steps {
        dir(path: 'app/api-users/') {
          sh './test.sh'
        }
      }
      post {
        always {
          dir(path: 'app/api-users/') {
            junit '**/target/surefire-reports/TEST-*.xml'
          }
        }
      }
    }
    stage('Run API App') {
      steps {
        dir(path: 'app/api-users/') {
          script{
              withEnv(['JENKINS_NODE_COOKIE=dontKillMe']) {
                sh './run.sh'
              }
          }
        }
      }
    }
    stage('Migrate Kong') {
      steps {
        dir(path: 'kong/') {
          sh './execute-migrations.sh -kong_host http://localhost:8001 -api_host http://localhost:3100 -silence true'
        }
      }
    }
    stage('Test kong migrations') {
      steps {
        dir(path: 'kong/') {
          sh './test.sh -Dkong_host=http://localhost:8000'
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