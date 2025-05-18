pipeline {
    agent any

    environment {
        DOCKERHUB_CREDENTIALS = credentials('dockerhub-credentials')  // Jenkins Credential ID
        DOCKER_IMAGE_NAME = "okanumur/project1"
        K8S_DEPLOYMENT_FILE = "k8s/deployment.yaml"
        K8S_SERVICE_FILE = "k8s/service.yaml"
    }

    stages {
        stage('Clone Repository') {
            steps {
                git branch: 'main', url: 'https://github.com/OkanUmur/DevOps_Project1.git'
            }
        }

        stage('Build Jar') {
            steps {
                sh './gradlew clean build'
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    dockerImage = docker.build("${DOCKER_IMAGE_NAME}:latest")
                }
            }
        }

        stage('Docker Login') {
            steps {
                script {
                    docker.withRegistry('https://index.docker.io/v1/', "${DOCKERHUB_CREDENTIALS}") {
                        echo 'Logged in to DockerHub'
                    }
                }
            }
        }

        stage('Push Docker Image') {
            steps {
                script {
                    docker.withRegistry('https://index.docker.io/v1/', "${DOCKERHUB_CREDENTIALS}") {
                        dockerImage.push()
                    }
                }
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                sh 'kubectl apply -f ${K8S_DEPLOYMENT_FILE}'
                sh 'kubectl apply -f ${K8S_SERVICE_FILE}'
            }
        }

        stage('Scale Application') {
            steps {
                sh 'kubectl scale deployment project1-deployment --replicas=2'
            }
        }

        stage('Verify Deployment') {
            steps {
                sh 'kubectl get pods'
                sh 'kubectl get services'
            }
        }
    }
}
