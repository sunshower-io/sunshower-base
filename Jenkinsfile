pipeline {
    environment {
        MVN_REPO = credentials('artifacts')
        GITHUB = credentials('Build')
    }
    agent {
        docker {
            image 'sunshower/sunshower-base:1.0.0'
        }
    }

    stages {
        stage('Check Commit Message for Skip Condition') {
            steps {
                skipRelease action: 'check', forceAbort: false
                sh "ls -la"
            }
        }
    }


    post {
        always {
            skipRelease action: 'postProcess'
        }
    }
}

