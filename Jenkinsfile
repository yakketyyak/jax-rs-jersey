pipeline {
    agent any
    stages{

      stage('Docker Build') { 
        steps{
          script{
            docker.image('maven:3.6-jdk-8').inside ('-v $HOME/.m2:/root/.m2'){
          
            stage('Build'){
              git 'https://github.com/yakketyyak/jax-rs-jersey.git'
              sh '''
                 mvn -B -DskipTests clean package
              '''
            }

            stage('Test'){
              sh '''
                mvn test
              '''
              archiveArtifacts artifacts: '**/target/*.war', fingerprint: true
            }
        }
      }
     }
      
    }

    stage('Deploy'){
    environment {
        artifactId = readMavenPom().getArtifactId()
        version = readMavenPom().getVersion()
        packaging = readMavenPom().getPackaging()
        tomcatUrl = 'http://192.168.1.111:8888/'
      }
	steps {
            deploy (
                war: '**/*.war', onFailure: true,
                adapters: [
                    tomcat8(
                      url: "${env.tomcatUrl}",
                      credentialsId: 'tomcat-deployer',
                      path: ''
                    )
                ]
            )
          }
    }
    
  } 
}