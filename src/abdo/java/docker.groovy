package abdo.java;

def build(IMAGE_NAME, IMAGE_TAG){
    sh "docker build -t ${IMAGE_NAME}:${IMAGE_TAG} ."
}

def login(DOCKER_USERNAME, DOCKER_PASSWORD){
    sh "docker login -u ${DOCKER_USERNAME} -p ${DOCKER_PASSWORD}"
}

def run(IMAGE_NAME, IMAGE_TAG){
    def fullImageTag = "${IMAGE_NAME}:${IMAGE_TAG}"
    def dockerIsRunning = sh(script: "docker ps --filter 'name=iti-java' --filter 'status=running' -q", returnStdout: true).trim()
    def dockerIsStopped = sh(script: "docker ps --filter 'name=iti-java' --filter 'status=exited' -q", returnStdout: true).trim()
    if (dockerIsRunning) {
        echo "Container 'iti-java' is currently running. Stopping and removing it."
        sh """
            docker stop iti-java
            docker rm iti-java
        """
        sh "docker run -d -p 8090:8090 --name iti-java ${fullImageTag}"
    } else if (dockerIsStopped) {
        echo "Container 'iti-java' is currently stopped. Removing it."
        sh "docker rm iti-java"
        sh "docker run -d -p 8090:8090 --name iti-java ${fullImageTag}"
    } else {
        echo "No container named 'iti-java' found. Starting new container."
        sh "docker run -d -p 8090:8090 --name iti-java ${fullImageTag}"
    }
}


def print_java_version(){
    sh "java --version"
}
def test_app(){
    sh "mvn test"
}
def install_packages(){
    sh "mvn package install"
}
