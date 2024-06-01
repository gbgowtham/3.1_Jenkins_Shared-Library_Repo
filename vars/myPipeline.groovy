def call() {
    pipeline {
        agent any
        stages {
             stage('Git Checkout') {
                 steps {
                     script {
                         https://github.com/gbgowtham/multi-branch.git
                     }
                 }
             }
            stage ('Build') {
                steps {
                    script {
                        sh 'npm install'
                    }
                }
            }
            // stage ('SonarQube Analysis') {
            //     steps {
            //         script { 
            //             def scannerHome = tool 'sonarscanner4'
            //             withSonarQubeEnv('sonar-pro') {
            //                 sh "${scannerHome}/bin/sonar-scanner -Dsonar.projectKey=node.js-app"
            //             }
            //         }
            //     }
            // }
            stage('Docker Build Images') {
                steps {
                    script {
                        sh 'docker build -t gbgowtham/multi-branch:v1 .'
                        sh 'docker images'
                        sh 'docker ps'
                    }
                }
            }
             stage('Docker Push') {
                 steps {
                     script {
                         withCredentials([string(credentialsId: 'dockerPass', variable: 'dockerPassword')]) {
                             sh "docker login -u gbgowtham -p ${dockerPassword}"
                             sh 'docker push gbgowtham/multi-branch:v2'
             //                sh 'docker rmi gbgowtham/multi:v2'
                         }
                     }
                 }
             }
            // stage('Deploy on k8s') {
            //     steps {
            //         script {
            //             withKubeCredentials(kubectlCredentials: [[ credentialsId: 'kubernetes', namespace: 'ms' ]]) {
            //                 sh 'kubectl apply -f kube.yaml'
            //                 sh 'kubectl get pods -o wide'
            //             }
            //         }
            //     }
            // }
        }
    }
}
