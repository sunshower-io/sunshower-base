podTemplate(containers: [
        containerTemplate(
                name: 'maven',
                image: 'maven:3.6.3-openjdk-16-slim',
                ttyEnabled: true,
                command: 'cat'
        ),
]) {

    node(POD_LABEL) {
        stage('Get a Maven project') {
            container('maven') {
                stage('Build a Maven project') {
                    sh "ls -la"
                    sh "ls -la ../"
                    sh "ls -la ../../"
                    sh 'mvn clean install -f bom'
                }
            }
        }
    }
}