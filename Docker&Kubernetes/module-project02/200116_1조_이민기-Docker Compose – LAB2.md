

- ```powershell
  PS C:\Users\HPE\Desktop\docker-project-day02\compose\compose-assignment-2> docker exec -it bb4991b016f3 /bin/bash
  root@bb4991b016f3:/# hostname -i
  172.19.0.2
  root@bb4991b016f3:/# psql -U postgres
  psql (9.6.16)
  Type "help" for help.
  
  postgres=# create database drupalpg;
  CREATE DATABASE
  postgres=# \l
                                   List of databases
     Name    |  Owner   | Encoding |  Collate   |   Ctype    |   Access privileges
  -----------+----------+----------+------------+------------+-----------------------
   drupalpg  | postgres | UTF8     | en_US.utf8 | en_US.utf8 |
   postgres  | postgres | UTF8     | en_US.utf8 | en_US.utf8 |
   template0 | postgres | UTF8     | en_US.utf8 | en_US.utf8 | =c/postgres          +
             |          |          |            |            | postgres=CTc/postgres
   template1 | postgres | UTF8     | en_US.utf8 | en_US.utf8 | =c/postgres          +
             |          |          |            |            | postgres=CTc/postgres
  (4 rows)
  
  postgres=#
  ```

- 

![image-20200116145555852](C:\Users\HPE\AppData\Roaming\Typora\typora-user-images\image-20200116145555852.png)

