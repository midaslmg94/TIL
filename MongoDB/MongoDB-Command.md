

# II. MongoDB 명령어

- DB 조회

  ```bash
  MongoDB Enterprise myapp:PRIMARY> show dbs
  ```

- DB 생성 & 사용(해당 DB의 이름이 없으면 생성해줌)

  ```bash
  MongoDB Enterprise myapp:PRIMARY> use dbs
  ```

- 컬렉션(테이블) 생성

  ```bash
  MongoDB Enterprise myapp:PRIMARY> db.createCollection("bookstore")
  ```

- 테이블 삽입

  ```bash
  MongoDB Enterprise myapp:PRIMARY> db.bookstore.insert({bookname : "JAVA"})
  ```

- db.members.find() 

- db.members.remove() // 모든 데이터 삭제

- db.members.remove({type : "ACE"}) // type이 ACE인 데이터 삭제