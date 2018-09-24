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

        stage('Release and Deploy Artifacts') {
            environment {
                CURRENT_VERSION = readMavenPom(file: 'sunshower-env/pom.xml').getVersion()
            }
//            when {
////                branch 'master'
////                expression {
////                    env.SKIP_BUILD == "false"
////                }
//            }
            steps {
                extractVersions(version: env.CURRENT_VERSION)

                /**
                 * Update env
                 */

                sh 'mvn versions:set -DnewVersion=$NEXT_VERSION -f bom/pom.xml -P sunshower'

                /**
                 * Git config
                 */

                sh "git config user.name '$GITHUB_USR'"
                sh "git config user.email '${GITHUB_USR}@sunshower.io'"

                /**
                 * Deploy boms
                 */



                sh """
                       mvn clean install deploy \
                       -f bom/pom.xml
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

