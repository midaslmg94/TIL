## 도커 명령어

- `docker version` : 도커의 버전을 확인
- `docker images` : 현재 도커가 가지고 있는 이미지 출력
- `docker rmi 'IMAGE ID'`: 해당 이미지 삭제
- `docker [container] run IMAGE NAME` : 이미지를 컨테이너화 시킴
  - `run`은 `create`와 `start`가 합쳐진 것이다.
  - 만일 해당 이미지가 없는 이미지라면, `docker-hub`에서 다운받아서 컨테이너화 된다.
- `docker container ls` 또는 `docker ps` : 현재 컨테이너의 상태를 보여준다. 
- `docker stop CONTAINER ID` : 해당 컨테이너를 중지시킴
- `docker rm CONTAINER ID` : 컨테이너 삭제
  - `docker stop`  --> `docker rm CONTAINER` --> `docker rmi IMAGE` 의 순서로 진행
- `docker image pull IMAGE_NAME` : `docker hub`에서 해당 이미지를 다운받음