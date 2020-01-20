- redis : 값이 key와 value로 들어가는 NoSQL
- worker가 db쪽으로 데이터 전달, db의 값을 result-app로 전달하여 값을 보여줌
- docker swarm init 후 join 3개
- docker exec -it manager docker network create -d overlay 
- 윈도우에서 8000번을 호출하면 manager의 80에서 응답, 5001번을 호출하면 5001에서 응답
- manager가 80을 호출하면 vote의 80에서 응답



manager에 접속해서 CLI창에서 명령어로 vote-app 구현 가능

1. 네트워크 구성
   - docker network create -d overlay backend
   - docker network create -d overlay frontend

- `docker service create --name vote -p 80:80 --network frontend --replicas brefis~~~`
- `docker service create --name redis --network frontend redis:3.2`
- `docker service create --name db --network backend --mount type=volume, source=db-data, target=/var/lib/postgresql/data postgres:9.4
- docker service create --name worker --network frontend  --network backend brefis~~:java
  - worker는 front, back 둘다 참여해야함
- docker service create --name result --network backend -p 5001:80 brefs~~







*docker-compose.yml*

```yaml
version: "3"
services: 
  registry:
    container_name: registry
    image: registry:latest
    ports: 
      - 5000:5000
    volumes: 
      - "./registry-data:/var/lib/registry"

  manager:
    container_name: manager
    image: docker:19.03.5-dind
    privileged: true
    tty: true
    ports:
      - 8080:80
      - 9000:9000
    depends_on: 
      - registry
    expose: 
      - 3375
    command: "--insecure-registry registry:5000"
    volumes: 
      - "./stack:/stack"

  worker01:
    container_name: work01
    hostname: work01
    image: docker:19.03.5-dind
    privileged: true
    tty: true
    ports:
      - 8000:8000
    depends_on: 
      - manager
      - registry
    expose: 
      - 7946
      - 7946/udp
      - 4789/udp
    command: "--insecure-registry registry:5000"

  worker02:
    container_name: work02
    hostname: work02
    image: docker:19.03.5-dind
    privileged: true
    tty: true
    ports:
      - 5001:5001
    depends_on: 
      - manager
      - registry
    expose: 
      - 7946
      - 7946/udp
      - 4789/udp
    command: "--insecure-registry registry:5000"

  worker03:
    container_name: work03
    image: docker:19.03.5-dind
    privileged: true
    tty: true
    depends_on: 
      - manager
      - registry
    expose: 
      - 7946
      - 7946/udp
      - 4789/udp
    command: "--insecure-registry registry:5000"
      
```





*voting-app.yml*

```yaml
# requirements
# - We create a stack and services, you need to create a yaml file like
# todo_app.yml
# - volume x 1 (db-data,target=/var/lib/postgresql/data)
# - networks x 2 (frontend, backend)
# - services x 5 (voting-app, redis, db, worker, result-app)
# - stack x 1 (my-vote-app)

version: "3"
networks:
    frontend:
        driver: overlay
        external: true
    backend:
        driver: overlay
        external: true
services:
    db:
        image: postgres:9.4
        deploy: 
            placement:
                constraints: [node.role != manager]
        networks:
            - backend
        volumes: # 볼륨 마운트 걸어놓음
            - db-data:/var/lib/postgresql/data
    
    
    result-app:
        image: bretfisher/examplevotingapp_result
        deploy:
            placement:
                constraints: [node.hostname == work02]
        ports:
            - 5001:80
        depends_on:
            - db
        networks:
            - backend

    redis:
        image: redis:3.2
        deploy:
            placement:
                constraints: [node.hostname != manager]             
        networks:
            - frontend
    
    worker:
        image: bretfisher/examplevotingapp_worker:java
        deploy:
            placement:
                constraints: [node.hostname != manager]
        depends_on:
            - redis
        networks:
            - frontend
            - backend


    voting-app:
        image: bretfisher/examplevotingapp_vote
        ports:
            - 8000:80        
        deploy:
            placement:
                constraints: [node.hostname == work01]
        depends_on:
            - worker
        networks:
            - frontend
volumes:
    db-data:
    
```



