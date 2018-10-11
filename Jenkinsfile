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
                sh "echo \$M2_HOME"
                sh "ls -la \$M2_HOME"

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
                CURRENT_VERSION = readMavenPom(file: 'bom/pom.xml').getVersion()
            }
            when {
                branch 'master'
                expression {
                    env.SKIP_BUILD == "false"
                }
            }
            steps {
                extractVersions(version: env.CURRENT_VERSION)

                /**
                 * Update env
                 */

                sh 'mvn versions:set -DnewVersion=$NEXT_VERSION -f bom/pom.xml -P sunshower -s /root/.m2/settings.xml'

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
                       -f bom/pom.xml -s /root/.m2/settings.xml
                   """

                /**
                 * Update gradle versions
                 */

                sh "find . -name gradle.properties | xargs sed -i  's/^version=${env.CURRENT_VERSION}\$/version=${env.NEXT_VERSION}/g'"

                sh """
                    gradle clean build publish sA -i \
                    -PmavenRepositoryUrl=http://artifacts.sunshower.io/repository/sunshower-releases \
                    -PmavenRepositoryUsername=${MVN_REPO_USR} \
                    -PmavenRepositoryPassword=${MVN_REPO_PSW} \
                    -PbomVersion=${env.CURRENT_VERSION}
                """

                /**
                 * Tag build
                 */
                sh "git tag -a v${env.NEXT_VERSION} -m 'Releasing ${env.NEXT_VERSION} [skip-build]'"

                /**
                 * Update remote
                 */
                sh "git remote set-url origin https://${GITHUB_USR}:${GITHUB_PSW}@github.com/sunshower-io/sunshower-base"


                sh 'mvn versions:set -DnewVersion=$NEXT_SNAPSHOT -f bom/pom.xml -s /root/.m2/settings.xml'

                sh 'mvn clean install deploy -f bom/pom.xml -s /root/.m2/settings.xml'

                sh "find . -name gradle.properties | xargs sed -i  's/^version=${env.NEXT_VERSION}\$/version=${env.NEXT_SNAPSHOT}/g'"


                sh """
                    gradle clean build publish sA -i \
                    -PmavenRepositoryUrl=http://artifacts.sunshower.io/repository/sunshower-snapshots \
                    -PmavenRepositoryUsername=${MVN_REPO_USR} \
                    -PmavenRepositoryPassword=${MVN_REPO_PSW} \
                    -PbomVersion=${env.CURRENT_VERSION}
                """

                /**
                 * Commit snapshots back to master and skip build
                 */
                sh "git commit -am 'Releasing ${env.NEXT_VERSION} [skip-build]'"
                sh "git push -u origin HEAD:master"

                /**
                 * Push tag
                 */
                sh "git push origin v${env.NEXT_VERSION}"
            }
        }
    }


    post {
        always {
            skipRelease action: 'postProcess'
        }
    }
}

