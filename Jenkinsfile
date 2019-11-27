node {
    bitbucketStatusNotify ( buildState: 'INPROGRESS' )
    stage 'Checkout'
    checkout scm
    stage 'Test Project'
    mvn 'clean test'
    stage 'Build'
    mvn 'clean package -DskipTests'
    stage 'Docker Build'
    mvn 'docker:build'
    
    def image=docker.image("onefi/bank-statement-service")
    if(env.BRANCH_NAME == 'master' || env.BRANCH_NAME == 'dev' || env.BRANCH_NAME.contains('features-')){
        stage 'Publish Docker'
        docker.withRegistry('https://334236250727.dkr.ecr.us-west-2.amazonaws.com', 'ecr:us-west-2:ecr_access_key') {
            image.tag(env.BRANCH_NAME)
            image.push(env.BRANCH_NAME)
            image.tag("${env.BRANCH_NAME}-${env.BUILD_NUMBER}")
            image.push("${env.BRANCH_NAME}-${env.BUILD_NUMBER}")            
            if(env.BRANCH_NAME =='master'){
                stage 'Publish Latest Docker'
                image.tag('latest')
                image.push('latest')
            }
        }
    }
    bitbucketStatusNotify ( buildState: 'SUCCESSFUL' )
}


def mvn(args) {
    sh "mvn ${args}"
}

def version() {
    def matcher = readFile('pom.xml') =~ '<version>(.+)-.*</version>'
    matcher ? matcher[0][1].tokenize(".") : null
}

