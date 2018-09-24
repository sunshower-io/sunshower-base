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
            }
        }

        stage('Build and deploy BOM POM') {
            steps {
                sh """
                        mvn clean install deploy \
                        -f bom/pom.xml -P sunshower
                    """
            }
        }

        stage('Build and Deploy Artifact Snapshots') {
            steps {
                sh """
                    gradle clean build publish sA -i \
                    -PmavenRepositoryUrl=http://artifacts.sunshower.io/repository/sunshower-snapshots \
                    -PmavenRepositoryUsername=${MVN_REPO_USR} \
                    -PmavenRepositoryPassword=${MVN_REPO_PSW} \
                    -PbomVersion=${env.CURRENT_VERSION}
                """
            }
        }
    }


    post {
        always {
            skipRelease action: 'postProcess'
        }
    }
}

