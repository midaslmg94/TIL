### Docker Compose – LAB1



- drupal service를 Docker compose 파일로 작성하기

```yaml
version: '3'
services:
  drupal:
    image: drupal
    ports:
      - "8080:80"
    volumes:
      - drupal-modules:/var/www/html/modules
      - drupal-profiles:/var/www/html/profiles
      - drupal-sites:/var/www/html/sites
      - drupal-themes:/var/www/html/themes
  mysql:
    image: mysql:5.7
    environment:
      - MYSQL_ROOT_PASSWORD=mypasswd 
volumes:
  drupal-modules:
  drupal-profiles:
  drupal-sites:
  drupal-themes:
```



- power shell에서 `docker-compose up`으로 실행

```powershell
PS C:\Users\HPE\Desktop\docker-project-day02\compose\compose-assignment-1> docker-compose up
Recreating compose-assignment-1_drupal_1 ...
Recreating compose-assignment-1_drupal_1 ... done
Attaching to compose-assignment-1_mysql_1, compose-assignment-1_drupal_1
drupal_1  | AH00558: apache2: Could not reliably determine the server's fully qualified domain name, using 172.18.0.2. Set the 'ServerName' directive globally to suppress this message
drupal_1  | AH00558: apache2: Could not reliably determine the server's fully qualified domain name, using 172.18.0.2. Set the 'ServerName' directive globally to suppress this message
drupal_1  | [Thu Jan 16 04:34:32.758640 2020] [mpm_prefork:notice] [pid 1] AH00163: Apache/2.4.25 (Debian) PHP/7.3.13 configured -- resuming normal operations
drupal_1  | [Thu Jan 16 04:34:32.758685 2020] [core:notice] [pid 1] AH00094: Command line: 'apache2 -D FOREGROUND'
...
```

- 새로운 power shell을 켜서 이미지, 프로세스 확인

```powershell
PS C:\Users\HPE\Desktop\docker-project-day02\compose\compose-assignment-1> docker ps
CONTAINER ID        IMAGE                  COMMAND                  CREATED             STATUS              PORTS                  NAMES
6a67729e1fd6        db39680b63ac           "docker-entrypoint.s…"   24 minutes ago      Up 23 minutes       3306/tcp, 33060/tcp    compose-assignment-1_mysql_1
4d76edc1a29f        drupal                 "docker-php-entrypoi…"   29 minutes ago      Up 23 minutes       0.0.0.0:8080->80/tcp   compose-assignment-1_drupal_1
```

- `mysql`로 접속한다.

  ```powershell
  PS C:\Users\HPE\Desktop\docker-project-day02\compose\compose-assignment-1> docker exec -it 6a67729e1fd6 /bin/bash
  root@6a67729e1fd6:/#
  ```

  - mysql의 ip확인

    ```powershell
    root@6a67729e1fd6:/# hostname -i
    172.18.0.2
    ```

  - mysql root로 접속

    ```powershell
    root@6a67729e1fd6:/# mysql -uroot -p
    Enter password:
    Welcome to the MySQL monitor.  Commands end with ; or \g.
    Your MySQL connection id is 75
    Server version: 5.7.28 MySQL Community Server (GPL)
    
    Copyright (c) 2000, 2019, Oracle and/or its affiliates. All rights reserved.
    
    Oracle is a registered trademark of Oracle Corporation and/or its
    affiliates. Other names may be trademarks of their respective
    owners.
    
    Type 'help;' or '\h' for help. Type '\c' to clear the current input statement.
    
    mysql>
    ```

  - drupal에서 사용할 database를 생성

    ```powershell
    mysql> create drupaldb;
    .
    .
    mysql> show databases;
    +--------------------+
    | Database           |
    +--------------------+
    | information_schema |
    | drupaldb           |
    | mysql              |
    | performance_schema |
    | sys                |
    +--------------------+
    5 rows in set (0.00 sec)
    ```



- drupal page에 가서 접속



- 사이트 설정

  ![image-20200116141144774](images/image-20200116141144774.png)

- 완성~

  ![image-20200116141553753](images/image-20200116141553753.png)