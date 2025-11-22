package abdo.java;

def build(IMAGE_NAME, IMAGE_TAG){
    sh "docker build -t ${IMAGE_NAME}:${IMAGE_TAG} ."
}

def login(DOCKER_USERNAME, DOCKER_PASSWORD){
    sh "docker login -u ${DOCKER_USERNAME} -p ${DOCKER_PASSWORD}"
}
