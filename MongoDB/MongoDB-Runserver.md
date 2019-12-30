# I. MongoDB 서버



## 1. 초기 설정 정보

- node 01(Secondary) --> port : 27018
- node 02(**Primary**) --> port : 27019
- node 03(Secondary) --> port : 27020
- arbiter --> port : 27030



## 2. 각 노드 켜기

- 우선 mongodb 폴더로 간다

  ```bash
  C:\User\HPE\Work\mongodb-4.2.2
  ```

  

- 명령어 

  ```bash
  C:\User\HPE\Work\mongodb-4.2.2>mongod --dbpath .\data\node01 --port 27018 --replSet myapp
  ```

  - node별 이름과 포트 번호에 맞게 3개의 노드, 1개의 아비터 서버를 켜준다.





## 3. Primary 서버(Master) 접속

- Primary 서버에 접속한다.

- 명령어

  ```bas
  C:\User\HPE>mongo --host 127.0.0.1 --port 27019
  ```



## 4. Secondary 서버(Slave) 접속

- Secondary 서버에 접속한다.

- 명령어

  ```bas
  C:\User\HPE>mongo --host 127.0.0.1 --port 27018
  
  C:\User\HPE>mongo --host 127.0.0.1 --port 27020
  ```



### 5. 어떤 서버가 Primary인지, Secondary 인지 확인하자

- cmd 창에 `MongoDB Enterprise myapp:PRIMARY>` 또는  `MongoDB Enterprise myapp:SECONDARY>`라고 뜨는데 명령어를 사용해서 확인할 수 있다.

- 명령어

  ```bash
  MongoDB Enterprise myapp:PRIMARY> db.isMaster()
  {
          "hosts" : [
                  "localhost:27018",
                  "127.0.0.1:27019",
                  "127.0.0.1:27020"
          ],
          "arbiters" : [
                  "127.0.0.1:27030"
          ],
          "setName" : "myapp",
          "setVersion" : 4,
          "ismaster" : true,
          "secondary" : false,
          "primary" : "127.0.0.1:27019",
          "me" : "127.0.0.1:27019",
          "electionId" : ObjectId("7fffffff0000000000000005"),
          "lastWrite" : {
                  "opTime" : {
                          "ts" : Timestamp(1577668906, 1),
                          "t" : NumberLong(5)
                  },
                  "lastWriteDate" : ISODate("2019-12-30T01:21:46Z"),
                  "majorityOpTime" : {
                          "ts" : Timestamp(1577668906, 1),
                          "t" : NumberLong(5)
                  },
                  "majorityWriteDate" : ISODate("2019-12-30T01:21:46Z")
          },
          "maxBsonObjectSize" : 16777216,
          "maxMessageSizeBytes" : 48000000,
          "maxWriteBatchSize" : 100000,
          "localTime" : ISODate("2019-12-30T01:21:54.952Z"),
          "logicalSessionTimeoutMinutes" : 30,
          "connectionId" : 17,
          "minWireVersion" : 0,
          "maxWireVersion" : 8,
          "readOnly" : false,
          "ok" : 1,
          "$clusterTime" : {
                  "clusterTime" : Timestamp(1577668906, 1),
                  "signature" : {
                          "hash" : BinData(0,"AAAAAAAAAAAAAAAAAAAAAAAAAAA="),
                          "keyId" : NumberLong(0)
                  }
          },
          "operationTime" : Timestamp(1577668906, 1)
  }
  ```





## 6. 데이터 베이스 생성 및 사용

- show dbs 를 하면 현재 가지고 있는 데이터베이스의 리스트를 보여준다.

  - Primary의 DB

    ```bash
    MongoDB Enterprise myapp:PRIMARY> show dbs
    admin        0.000GB
    bookstore    0.000GB
    bookstore()  0.000GB
    config       0.000GB
    local        0.000GB
    ```

  - Secondary의 DB

    ```bash
  MongoDB Enterprise myapp:SECONDARY> show dbs
    2019-12-29T18:25:06.680-0700 E  QUERY    [js] uncaught exception: Error: listDatabases failed:{
            "operationTime" : Timestamp(1577669096, 1),
            "ok" : 0,
            "errmsg" : "not master and slaveOk=false",
            "code" : 13435,
            "codeName" : "NotMasterNoSlaveOk",
            "$clusterTime" : {
                    "clusterTime" : Timestamp(1577669096, 1),
                    "signature" : {
                            "hash" : BinData(0,"AAAAAAAAAAAAAAAAAAAAAAAAAAA="),
                            "keyId" : NumberLong(0)
                    }
            }
    } :
    ```
  
    - Secondary DB는 `slave`이고, `show dbs` 명령어를 실행할 권한이 없기 때문에 따로 부여를 해줘야 한다.

      ```
      MongoDB Enterprise myapp:SECONDARY> rs.slaveOk()
      ```
  



### 7. Primary 종료, 새로운 Primary 책정

- Primary 서버(현재 node02, port : 27019)를 종료하면 `arbiter`에 의해 Secondary 서버들 중에서 새로운 Primary 서버가 책정된다.
- 새롭게 책정된 Primary 서버는 기존 Primary 서버의 기능을 똑같이 수행한다.
- 만일, 이전의 Primary 서버였던 node02를 새롭게 시동하면 node02는 Secondary로 들어간다. 


