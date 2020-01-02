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
- `docker image pull IMAGE_NAME` : `docker hub`에서 해당 이미지를 ~~다운받음~~
- `docker run -d -p 3306:3306\ -e MYSQL_ALLOW_EMPTY_PASSWARD=true\ --name my-mysql -v/my/datadir:/var/lib/mysql mysql:5.7 `  
  - `-d ` : 백그라운드에서 데몬으로 실행되라는 명령어
  - `-e` : 환경변수 설정 옵션. `MYSQL_ALLOW_EMPTY_PASSWARD=true`은 따로 비밀번호가 없게 설정한 것
  - `--name` : 관리하기 위한 이름을 my-mysql로 이름을 사용하는 것
  - `-v` :  `:`을 기준으로 앞에 있는 것은 호스트 OS에 있는 경로. `:`뒤쪽은 Mysql의 경로를 의미
    - 호스트가 가지고 있는 위치에 포함시켜 주겠다.
  - `mysql:5.7` : 실행할 이미지, 그 이미지에 대한 태그