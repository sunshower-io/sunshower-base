pipeline {
    agent any

    environment {
        VERSION_BASE = "1.0.0"
        MVN_REPO = credentials('sonatype')
        DOCKER_CREDENTIALS = credentials("dockerhub")
        MAVEN_REPOSITORY_URL = "https://oss.sonatype.org/content/repositories/snapshots"
    }

    stages {
        stage('build-docker') {
            steps {
                sh 'docker build -t sunshower-common -f Dockerfile .'
                sh "docker run " +
                        "-e MVN_REPO_USERNAME=${MVN_REPO_USR} " +
                        "-e MVN_REPO_PASSWORD=${MVN_REPO_PSW} " +
                        "-e MVN_REPO_URL=${MAVEN_REPOSITORY_URL} " +
                        "-it --rm --name 'sunshower-common' 'sunshower-common'"
            }
        }
    }
}