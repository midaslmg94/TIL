## Docker Swarm을 이용한 실전애플리케이션 개발 - TODO App

### 아키텍처

![image-20200103153958578](images/image-20200103153958578.png)

------



### 배치 전략

![image-20200103155157535](images/image-20200103155157535.png)



------



### TODO overlay network 구축

- create network

  ```powershell
  PS C:\Users\HPE> docker container exec -it manager sh
  / # docker network create --driver=overlay --attachable todoapp
  80bpj4cyex4h91eqzlbu9ns9u
  ```

- 생성된 네트워크 확인(`80bpj4cyex4h        todoapp              overlay             swarm`)

  ```powershell
  / # docker network ls
  NETWORK ID          NAME                 DRIVER              SCOPE
  797e17ef3adf        bridge               bridge              local
  tmiu9rmpk30c        ch03                 overlay             swarm
  98edfadd5f8a        docker_gwbridge      bridge              local
  fae88880bfdf        host                 host                local
  t9oq7k8wwvl0        ingress              overlay             swarm
  a60b1b6c2f53        none                 null                local
  80bpj4cyex4h        todoapp              overlay             swarm
  m19s39jh4hmf        visualizer_default   overlay             swarm
  ```

  

------



### MySQL 서비스 구축

1. Master/Slave 구조 구축

- `todo-mysql.yml` 파일은 stack 폴더에 복붙 해놓는다`C:\Users\HPE\Work\docker\day03\swarm\todo\tododb\etc\mysql\mysql.conf` 확인  

- `log-bin=/var/log/mysql/mysql-bin.log`를 사용해서 바이너리 로그 파일을 만들겠다.

- 이미지 생성

  - 폴더를 `C:\Users\HPE\Work\docker\day03\swarm\todo\tododb`로 이동한다.
  - 빌드 실행 : `docker build -t ch04/tododb:latest .`

  

- 빌드가 된 `tododb`를 로컬호스트에 등록 & push

  - `docker tag ch04/tododb:latest localhost:5000/ch04/tododb:latest`
  - `docker push localhost:5000/ch04/tododb:latest`
  - Registry의 이미지 목록은 `localhost:5000/v2/_catalog` 에서 확인가능
   ![image-20200103165144134](images/image-20200103165144134.png)

- 기존에 있던 echo와 ingress stack은 지우자

  - `docker stack rm echo`
  - `docker stack rm ingress`

- `swarm`으로 배포(master 1개, slave 2개) 

  ```powershell
  / # docker stack deploy -c /stack/todo-mysql.yml todo_mysql
  Creating service todo_mysql_slave
  Creating service todo_mysql_master
  ```

- `todo_mysql`에 올라간 서비스확인

```powershell
/ # docker stack services todo_mysql
ID                  NAME                MODE                REPLICAS            IMAGE                              PORTS
dgj73fc7bqbv        todo_mysql_master   replicated          1/1                 registry:5000/ch04/tododb:latest
scqvahvn1jdo        todo_mysql_slave    replicated          0/2                 registry:5000/ch04/tododb:latest
```

*문제 :slave의 replicas가 올라오지 않음 : cnf 파일과 sh 파일을 CRLF에서 LF로 바꿈*

- *Dockerfile* 의 CMD부분을 주석처리하고 새로 빌드







   

- worker01로 접속 : `PS C:\Users\HPE\Work\docker\day03\swarm\todo\tododb> docker exec -it worker01 sh`
- 만들었던 이미지를 다운받아 오자
  - `docker pull registry:5000/ch04/tododb:latest`