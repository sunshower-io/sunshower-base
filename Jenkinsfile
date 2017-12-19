#!/usr/bin/env groovy

def majorVersion   = "1"
def minorVersion   = "0"
def buildNumber    = "141738651"
def buildSuffix    = "FEATURE"
def version        = "$majorVersion.$minorVersion"
def runSystemTests = false
def gradleTasks    = []

if (env.BRANCH_NAME == "master") {
    buildNumber = env.BUILD_NUMBER
    buildSuffix = "Final"
    gradleTasks = [
        "clean",
        "releaseBom",
        "build",
        "artifactoryPublish",
        "-Pversion=$majorVersion.$minorVersion.$buildNumber.$buildSuffix"
    ]
} else {
    if (buildSuffix == "FEATURE" && env.BRANCH_NAME.contains(buildNumber)) {
        gradleTasks = [
                "clean",
                "releaseBom",
                "build",
                "artifactoryPublish",
                "-Pversion=$majorVersion.$minorVersion.$buildNumber.$buildSuffix"
        ]
    } else {
        gradleTasks = ["clean", "installBillOfMaterials", "build"]
        buildNumber = "${env.BUILD_NUMBER}.${convertBranchName(env.BRANCH_NAME)}"
    }
}

node('docker-registry') {

	echo """
Build Details
-------------------------------------------------------
majorVersion: $majorVersion
minorVersion: $minorVersion
 buildNumber: $buildNumber
 buildSuffix: $buildSuffix
      branch: ${env.BRANCH_NAME}
gradle tasks: ${gradleTasks.join(" ")}
"""

    stage('Checkout') {
    	checkout scm
	}

    timeout(time: 60, unit: 'MINUTES') {
        stage('Gradle Tasks') {
			sh "chmod +x gradlew"

			if (env.BRANCH_NAME =~ /(?i)^pr-/) {
                sh "./gradlew prepareForRelease"
            }

			try {
				sh "./gradlew ${gradleTasks.join(" ")}"
			} catch (Exception e) {
				error "Failed: ${e}"
				throw (e)
			} finally {
				junit allowEmptyResults: true, keepLongStdio: true, testResults: '**/build/test-results/**/*.xml'
			}
		}
    }
}

def convertBranchName(String name) {
    return name.replaceAll('/', '_')
}
