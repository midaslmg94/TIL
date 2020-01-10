## Openstack : Manual Setting

##### 200110 금요일

- VM에 manual-controller를 띄운다.

  - 

- Xshell에 들어가서 hosts 설정에서 controller와 compute1을 수정한다

  ```powershell
  127.0.0.1   localhost localhost.localdomain localhost4 localhost4.localdomain4
  ::1         localhost localhost.localdomain localhost6 localhost6.localdomain6
  10.0.0.11 controller
  10.0.0.31 compute1
  ```

- `chrony` 를 다운받고 `manual`의 `vi /etc/chrony.conf`를 편집한다.

  ```powershell
  [root@controller ~]# yum install chrony -y
  .
  .
  .
  
  [root@controller ~]# vi /etc/chrony.conf 
  .
  .
  .
  server 0.centos.pool.ntp.org iburst
  server 1.centos.pool.ntp.org iburst
  #server 2.centos.pool.ntp.org iburst
  #server 3.centos.pool.ntp.org iburst
  server 10.0.0.100 iburst # 10.0.0.100으로 하여 현재 서버인 controller를 바라보게 해줌
  ```

- [Manual 설치](https://docs.openstack.org/install-guide/environment-packages-rdo.html)

  - Security

  - Host networking

  - NTP

  - Openstack packages

    - `#yum install python-openstackclient -y`
    - `# yum install openstack-selinux`

  - [SQL database](https://docs.openstack.org/install-guide/environment-sql-database-rdo.html) 

    - `# yum install mariadb mariadb-server python2-PyMySQL`

    - ```powershell
      /etc/my.cnf.d/openstack.cnf
      .
      .
      .
      [mysqld]
      bind-address = 10.0.0.11 # ip-address를 10.0.0.11에서만 들어올 수 있도록 제한
      
      default-storage-engine = innodb
      innodb_file_per_table = on
      max_connections = 4096
      collation-server = utf8_general_ci
      character-set-server = utf8
      ```

    - 서비스 시작

      ```powershell
      # systemctl enable mariadb.service
      # systemctl start mariadb.service
      ```

    - root 비밀번호를 설정하고 불필요한 보안 이슈 제거. 비밀번호가 설정되어있지 않으므로 엔터치고, 이 후의 Y Y Y Y Y

    - ```powershell
      # mysql_secure_installation
      ```

    - db connect : `mysql -uroot -pabc123`

  - [Message queue](https://docs.openstack.org/install-guide/environment-messaging.html)

    - 비동기적 방식 : Sender는 큐에 message를 보내고 자신이 해야할 일을 한다. Receiver는 큐에서 Sender의 message를 받는다. 비동기적 방식은 MSA의 중요한 방식이다. 약결합 아키텍쳐 방식.

    - 패키지 설치 : `yum install rabbitmq-server`

    - 서비스 시작 :  

      ```
      # systemctl enable rabbitmq-server.service
      # systemctl start rabbitmq-server.service
      ```

    - user 설정 : `# rabbitmqctl add_user openstack RABBIT_PASS`

      - user : openstack
      - pw : RABBIT_PASS

    - Permission 설정 : `rabbitmqctl set_permissions openstack ".*" ".*" ".*"`

      - 상태 확인 `[root@controller ~]# rabbitmqctl status`

  - Memcached L 인메모리 설치

    - `yum install memcached python-memcached`

    - ```powershell
      # vi /etc/sysconfig/memcached
      .
      .
      OPTIONS="-l 127.0.0.1,::1,controller"
      .
      .
      # systemctl enable memcached.service
      # systemctl start memcached.service
      ```

      
    
  
  

------

#### Keystone : 인증서비스

- Keystone 설치

  - Prerequisites

    - `MariaDB`접속 : database만 생성. 안에  table은 집어넣지 않음

      ```powershell
      [root@controller ~]# mysql -uroot -pabc123
      
      MariaDB [(none)]> CREATE DATABASE keystone;
      Query OK, 1 row affected (0.00 sec)
      
      MariaDB [(none)]> GRANT ALL PRIVILEGES ON keystone.* TO 'keystone'@'localhost' \
          -> IDENTIFIED BY 'KEYSTONE_DBPASS';
      Query OK, 0 rows affected (0.00 sec)
      
      MariaDB [(none)]> GRANT ALL PRIVILEGES ON keystone.* TO 'keystone'@'%' \
          -> IDENTIFIED BY 'KEYSTONE_DBPASS';
      Query OK, 0 rows affected (0.00 sec)
      
      MariaDB [(none)]> exit
      Bye
      
      ```

  - 패키지 설치

    ```powershell
    # yum install openstack-keystone httpd mod_wsgi
    ```

  - `.conf`파일 수정 : `vi /etc/keystone/keystone.conf`

    ```powershell
    # 742 line, 2829 line 수정
    742 connection = mysql+pymysql://keystone:KEYSTONE_DBPASS@controller/keystone
    2829 provider = fernet
    ```

    테이블 생성 및 조회

    - `su -s /bin/sh -c "keystone-manage db_sync" keystone`

      - `keystone-manage`은 테이블을 자동으로 만들어주는 것임. 이것을 해주기 때문에 `/var/lib/mysql` 밑의 `keystone`파일을 확인해보았을 때 `.frm`, `.ibd`파일들이 생긴것임

    - ```powershell
      [root@controller ~]# cd /var/lib/mysql
      [root@controller mysql]# ls
      aria_log.00000001  ib_logfile1  multi-master.info  mysql_upgrade_info
      aria_log_control   ibdata1      mysql              performance_schema
      ib_logfile0        keystone     mysql.sock         tc.log
      
      [root@controller mysql]# ls keystone/
      access_token.frm                 idp_remote_ids.ibd          registered_limit.ibd
      access_token.ibd                 implied_role.frm            request_token.frm
      application_credential.frm       implied_role.ibd            request_token.ibd
      application_credential.ibd       limit.frm                   revocation_event.frm
      application_credential_role.frm  limit.ibd                   revocation_event.ibd
      application_credential_role.ibd  local_user.frm              role.frm
      assignment.frm                   local_user.ibd              role.ibd
      assignment.ibd                   mapping.frm                 sensitive_config.frm
      config_register.frm              mapping.ibd                 sensitive_config.ibd
      config_register.ibd              migrate_version.frm         service.frm
      consumer.frm                     migrate_version.ibd         service.ibd
      consumer.ibd                     nonlocal_user.frm           service_provider.frm
      credential.frm                   nonlocal_user.ibd           service_provider.ibd
      credential.ibd                   password.frm                system_assignment.frm
      db.opt                           password.ibd                system_assignment.ibd
      endpoint.frm                     policy.frm                  token.frm
      endpoint.ibd                     policy.ibd                  token.ibd
      endpoint_group.frm               policy_association.frm      trust.frm
      endpoint_group.ibd               policy_association.ibd      trust.ibd
      federated_user.frm               project.frm                 trust_role.frm
      federated_user.ibd               project.ibd                 trust_role.ibd
      federation_protocol.frm          project_endpoint.frm        user.frm
      federation_protocol.ibd          project_endpoint.ibd        user.ibd
      group.frm                        project_endpoint_group.frm  user_group_membership.frm
      group.ibd                        project_endpoint_group.ibd  user_group_membership.ibd
      id_mapping.frm                   project_tag.frm             user_option.frm
      id_mapping.ibd                   project_tag.ibd             user_option.ibd
      identity_provider.frm            region.frm                  whitelisted_config.frm
      identity_provider.ibd            region.ibd                  whitelisted_config.ibd
      idp_remote_ids.frm               registered_limit.frm
      
      ```

    - `provider = fernet` : 암호화 하여 사용한다는 것 이것을 초기화 해주자

      - ```powershell
        # keystone-manage fernet_setup --keystone-user keystone --keystone-group keystone
        # keystone-manage credential_setup --keystone-user keystone --keystone-group keystone
        
        ```

      - Password 가 ADMIN_PASS임

        ```powershell
        keystone-manage bootstrap --bootstrap-password ADMIN_PASS \
          --bootstrap-admin-url http://controller:5000/v3/ \
          --bootstrap-internal-url http://controller:5000/v3/ \
          --bootstrap-public-url http://controller:5000/v3/ \
          --bootstrap-region-id RegionOne
        ```

      - `controll`로 이동 후 admin으로 접속 

      - ```powershell
        [root@controller ~]# . keystonerc_admin
        ```

      - 다시 `manaual`로 돌아감

      - ```powershell
        [root@controller mysql]#  keystone-manage bootstrap --bootstrap-password ADMIN_PASS \
        >   --bootstrap-admin-url http://controller:5000/v3/ \
        >   --bootstrap-internal-url http://controller:5000/v3/ \
        >   --bootstrap-public-url http://controller:5000/v3/ \
        >   --bootstrap-region-id RegionOne
        
        ```

      - `[root@controller mysql]# vi /etc/httpd/conf/httpd.conf`

        - ```powershell
          95 #ServerName www.example.com:80
          96 ServerName controller
          --> 96 line 추가
          ```

      - 추가적인 설정

        - symbolic link 설정

          ```powershell
          # ln -s /usr/share/keystone/wsgi-keystone.conf /etc/httpd/conf.d/
          ```

        - httpd.service 실행

          ```powershell
          # systemctl enable httpd.service
          # systemctl start httpd.service
          ```

        - `systemctl status httpd.service`로 서비스가 잘 올라갔나 확인

          ```powershell
          [root@controller mysql]# systemctl status httpd.service
          ● httpd.service - The Apache HTTP Server
             Loaded: loaded (/usr/lib/systemd/system/httpd.service; enabled; vendor preset: disabled)
             Active: active (running) since 금 2020-01-10 11:24:49 KST; 6s ago
               Docs: man:httpd(8)
                     man:apachectl(8)
           Main PID: 15506 (httpd)
             Status: "Processing requests..."
             CGroup: /system.slice/httpd.service
                     ├─15506 /usr/sbin/httpd -DFOREGROUND
                     ├─15527 (wsgi:keystone- -DFOREGROUND
                     ├─15528 (wsgi:keystone- -DFOREGROUND
                     ├─15529 (wsgi:keystone- -DFOREGROUND
                     ├─15530 (wsgi:keystone- -DFOREGROUND
                     ├─15532 (wsgi:keystone- -DFOREGROUND
                     ├─15533 /usr/sbin/httpd -DFOREGROUND
                     ├─15534 /usr/sbin/httpd -DFOREGROUND
                     ├─15535 /usr/sbin/httpd -DFOREGROUND
                     ├─15544 /usr/sbin/httpd -DFOREGROUND
                     └─15545 /usr/sbin/httpd -DFOREGROUND
          
           1월 10 11:24:49 controller systemd[1]: Starting The Apache HTTP Server...
           1월 10 11:24:49 controller systemd[1]: Started The Apache HTTP Server.
          ```

        - `admin` 사용자 생성

          ```pow
          [root@controller mysql]# export OS_USERNAME=admin
          [root@controller mysql]# export OS_PASSWORD=ADMIN_PASS
          [root@controller mysql]# export OS_PROJECT_NAME=admin
          [root@controller mysql]# export OS_USER_DOMAIN_NAME=Default
          [root@controller mysql]# export OS_PROJECT_DOMAIN_NAME=Default
          [root@controller mysql]# export OS_AUTH_URL=http://controller:5000/v3
          [root@controller mysql]# export OS_IDENTITY_API_VERSION=3
          ```

        - 확인 : 여기까지하면 기본 keystone 설정이 완료 된 것임

          ```powershell
          [root@controller mysql]# openstack user list
          +----------------------------------+-------+
          | ID                               | Name  |
          +----------------------------------+-------+
          | 5d358e5b069e4adaba0e33bca5240ede | admin |
          +----------------------------------+-------+
          
          ```

  - [Create a domain, projects, users, and roles](https://docs.openstack.org/keystone/rocky/install/keystone-users-rdo.html)

    - 

    - `Service Project` 생성

      ```powershell
      [root@controller mysql]# openstack project create --domain default \
      >   --description "Service Project" service
      +-------------+----------------------------------+
      | Field       | Value                            |
      +-------------+----------------------------------+
      | description | Service Project                  |
      | domain_id   | default                          |
      | enabled     | True                             |
      | id          | 609ed1ff14ea4685b9389a8db2ff0bc4 |
      | is_domain   | False                            |
      | name        | service                          |
      | parent_id   | default                          |
      | tags        | []                               |
      +-------------+----------------------------------+
      ```

    - `Demo Project` 생성

      ```powershell
      [root@controller mysql]# openstack project create --domain default \
      >   --description "Demo Project" myproject
      +-------------+----------------------------------+
      | Field       | Value                            |
      +-------------+----------------------------------+
      | description | Demo Project                     |
      | domain_id   | default                          |
      | enabled     | True                             |
      | id          | 55f3c91687b543faa526540fa1e059f7 |
      | is_domain   | False                            |
      | name        | myproject                        |
      | parent_id   | default                          |
      | tags        | []                               |
      +-------------+----------------------------------+
      ```

    - `myuser` 생성 : `password`는 abc123

      ```powershell
      [root@controller mysql]# openstack user create --domain default \
      >   --password abc123 myuser
      +---------------------+----------------------------------+
      | Field               | Value                            |
      +---------------------+----------------------------------+
      | domain_id           | default                          |
      | enabled             | True                             |
      | id                  | 4965ccffb3b74b778da9d66d0529ad3e |
      | name                | myuser                           |
      | options             | {}                               |
      | password_expires_at | None                             |
      +---------------------+----------------------------------+
      ```

    - `myrole` 생성 후` myrole`을 `myuser`에 적용

      ```powershell
      [root@controller mysql]# openstack role create myrole
      +-----------+----------------------------------+
      | Field     | Value                            |
      +-----------+----------------------------------+
      | domain_id | None                             |
      | id        | 1ce6c82c26e24c3cb68af932886d7adc |
      | name      | myrole                           |
      +-----------+----------------------------------+
      
      [root@controller mysql]# openstack role add --project myproject --user myuser myrole
      ```

  - [Create OpenStack client environment scripts](https://docs.openstack.org/keystone/rocky/install/keystone-openrc-rdo.html)
  
    - `manaual` 에서 `vi admin-openrc` 파일 편집
  
      ```powershell
      export OS_PROJECT_DOMAIN_NAME=Default
      export OS_USER_DOMAIN_NAME=Default
      export OS_PROJECT_NAME=admin
      export OS_USERNAME=admin
      export OS_PASSWORD=ADMIN_PASS
      export OS_AUTH_URL=http://controller:5000/v3
      export OS_IDENTITY_API_VERSION=3
      export OS_IMAGE_API_VERSION=2
      export PS1='[\u@\h \W(keystone_admin)]\$ ' # 이걸 추가하면 prompt창에서 변화
      ```
  
    - prompt 창에서 현재 사용자가 누군지(지금은 `admin`) 알려줌
  
      ```powershell
      [root@controller ~]# . admin-openrc 
      [root@controller ~(keystone_admin)]# 
      ```
  
    - `manaual` 에서 `vi demo-openrc` 파일 편집
  
      ```powershell
      export OS_PROJECT_DOMAIN_NAME=Default
      export OS_USER_DOMAIN_NAME=Default
      export OS_PROJECT_NAME=myproject
      export OS_USERNAME=myuser
      export OS_PASSWORD=abc123
      export OS_AUTH_URL=http://controller:5000/v3
      export OS_IDENTITY_API_VERSION=3
      export OS_IMAGE_API_VERSION=2
      export PS1='[\u@\h \W(keystone_myuser)]\$ '
      ```
  
      - `export OS_USERNAME=myuser`
      - `export OS_PASSWORD=abc123`
      - `export PS1='[\u@\h \W(keystone_myuser)]\$ '`
  
    - `demo` 사용자 적용 후 토큰 생성
  
      - ```powershell
        [root@controller ~(keystone_admin)]# . demo-openrc
        [root@controller ~(keystone_myuser)]# openstack token issue
        +------------+-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
        | Field      | Value                                                                                                                                                                                   |
        +------------+-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
        | expires    | 2020-01-10T05:30:27+0000                                                                                                                                                                |
        | id         | gAAAAABeF_3j-8WpB80pflwi9PHSjtCJikhYHM-67VFzWEbNKMDY_ZMcJSkX90hlG86uNPOIu_St07GoL2xZ0VsfY0nRPwnfwsBO7-FIvgle_40zGWm-uxb83U8q9IgKGvSRkrrHpJuvVq9v7gOq6mScD4essWu1DNLUeto-bvT8WEGa_Mm2xaI |
        | project_id | 55f3c91687b543faa526540fa1e059f7                                                                                                                                                        |
        | user_id    | 4965ccffb3b74b778da9d66d0529ad3e                                                                                                                                                        |
        +------------+-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+
        
        ```



------

#### Image Service(Glance)

- Glance 구조
  - glance-api 
  - glance-registry

- Glance 명령어

  - `[root@controller ~(keystone_admin)]# glance image-list`
    - glance에서 사용하는 명령어 
  - `[root@controller ~(keystone_admin)]# openstack image list`
    - openstack의 공용 명령어

- `glance` DB 생성

  - ```powershell
    [root@controller ~(keystone_myuser)]#  mysql -u root -p
    Enter password: #abc123
    Welcome to the MariaDB monitor.  Commands end with ; or \g.
    Your MariaDB connection id is 20
    Server version: 10.1.20-MariaDB MariaDB Server
    
    Copyright (c) 2000, 2016, Oracle, MariaDB Corporation Ab and others.
    
    Type 'help;' or '\h' for help. Type '\c' to clear the current input statement.
    
    MariaDB [(none)]> CREATE DATABASE glance;
    Query OK, 1 row affected (0.00 sec)
    
    MariaDB [(none)]> GRANT ALL PRIVILEGES ON glance.* TO 'glance'@'localhost' \
        ->   IDENTIFIED BY 'GLANCE_DBPASS';
    Query OK, 0 rows affected (0.01 sec)
    
    MariaDB [(none)]> GRANT ALL PRIVILEGES ON glance.* TO 'glance'@'%' \
        ->   IDENTIFIED BY 'GLANCE_DBPASS';
    Query OK, 0 rows affected (0.00 sec)
    
    MariaDB [(none)]> exit
    Bye
    
    ```

  - `user` 생성, role 등록

    ```powershell
    [root@controller ~(keystone_myuser)]# . admin-openrc
    [root@controller ~(keystone_admin)]# openstack user create --domain default --password-prompt glance
    User Password: #GLANCE_PASS
    Repeat User Password: #GLANCE_PASS
    +---------------------+----------------------------------+
    | Field               | Value                            |
    +---------------------+----------------------------------+
    | domain_id           | default                          |
    | enabled             | True                             |
    | id                  | f1f3657ecd8448a9a504d681be35273f |
    | name                | glance                           |
    | options             | {}                               |
    | password_expires_at | None                             |
    +---------------------+----------------------------------+
    
    [root@controller ~(keystone_admin)]# openstack user create --domain default --password-prompt glance
    User Password:
    Repeat User Password:
    +---------------------+----------------------------------+
    | Field               | Value                            |
    +---------------------+----------------------------------+
    | domain_id           | default                          |
    | enabled             | True                             |
    | id                  | f1f3657ecd8448a9a504d681be35273f |
    | name                | glance                           |
    | options             | {}                               |
    | password_expires_at | None                             |
    +---------------------+----------------------------------+
    
    
    [root@controller ~(keystone_admin)]# openstack service create --name glance \
    >   --description "OpenStack Image" image
    +-------------+----------------------------------+
    | Field       | Value                            |
    +-------------+----------------------------------+
    | description | OpenStack Image                  |
    | enabled     | True                             |
    | id          | e4c79cee229644fc9a74fec2de439292 |
    | name        | glance                           |
    | type        | image                            |
    +-------------+----------------------------------+
    
    ```

    

  - `API endpoints` 생성 : `public`, `internal`, `admin` 

    ```powershell
    [root@controller ~(keystone_admin)]# openstack endpoint create --region RegionOne \
    >   image public http://controller:9292
    +--------------+----------------------------------+
    | Field        | Value                            |
    +--------------+----------------------------------+
    | enabled      | True                             |
    | id           | 65b5e06c1a624adf9b9f221d2a92717d |
    | interface    | public                           |
    | region       | RegionOne                        |
    | region_id    | RegionOne                        |
    | service_id   | e4c79cee229644fc9a74fec2de439292 |
    | service_name | glance                           |
    | service_type | image                            |
    | url          | http://controller:9292           |
    +--------------+----------------------------------+
    
    [root@controller ~(keystone_admin)]# openstack endpoint create --region RegionOne \
    >   image internal http://controller:9292
    +--------------+----------------------------------+
    | Field        | Value                            |
    +--------------+----------------------------------+
    | enabled      | True                             |
    | id           | 0160d9e8bbbf4102b513e9679cabdbf0 |
    | interface    | internal                         |
    | region       | RegionOne                        |
    | region_id    | RegionOne                        |
    | service_id   | e4c79cee229644fc9a74fec2de439292 |
    | service_name | glance                           |
    | service_type | image                            |
    | url          | http://controller:9292           |
    +--------------+----------------------------------+
    
    [root@controller ~(keystone_admin)]# openstack endpoint create --region RegionOne \
    >   image admin http://controller:9292
    +--------------+----------------------------------+
    | Field        | Value                            |
    +--------------+----------------------------------+
    | enabled      | True                             |
    | id           | dbc76dee6c514fb4a9672127139776fa |
    | interface    | admin                            |
    | region       | RegionOne                        |
    | region_id    | RegionOne                        |
    | service_id   | e4c79cee229644fc9a74fec2de439292 |
    | service_name | glance                           |
    | service_type | image                            |
    | url          | http://controller:9292           |
    +--------------+----------------------------------+
    
    ```

  - package 설치 : `yum install openstack-glance`

  - `/etc/glance/glance-api.conf` 파일 편집

  - ```powershell
    [root@controller ~(keystone_admin)]# vi /etc/glance/glance-api.conf
       1901 connection = mysql+pymysql://glance:GLANCE_DBPASS@controller/glance
       3475 [keystone_authtoken]
       3476 www_authenticate_uri  = http://controller:5000
       3477 auth_url = http://controller:5000
       3478 memcached_servers = controller:11211
       3479 auth_type = password
       3480 project_domain_name = Default
       3481 user_domain_name = Default
       3482 project_name = service
       3483 username = glance
       3484 password = GLANCE_PASS
    
    	4424 flavor = keystone
    	2007 [glance_store]
       2008 stores = file,http
       2009 default_store = file
       2010 filesystem_store_datadir = /var/lib/glance/images/
    ```

  - `/etc/glance/glance-registry.conf`파일 편집

  - ```powershell
    [root@controller ~(keystone_admin)]# vi /etc/glance/glance-registry.conf
    
    1147 connection = mysql+pymysql://glance:GLANCE_DBPASS@controller/glance
    1254 [keystone_authtoken]
    1255 www_authenticate_uri = http://controller:5000
    1256 auth_url = http://controller:5000
    1257 memcached_servers = controller:11211
    1258 auth_type = password
    1259 project_domain_name = Default
    1260 user_domain_name = Default
    1261 project_name = service
    1262 username = glance
    1263 password = GLANCE_PASS
    
    2153 [paste_deploy]
    2154 flavor = keystone
    
    ```

  - ```powershell
    su -s /bin/sh -c "glance-manage db_sync" glance
    ls /var/lib/mysql/glance  
    ```

  - ```powershell
    [root@controller ~(keystone_admin)]# systemctl enable openstack-glance-api.service \
    >   openstack-glance-registry.service
    Created symlink from /etc/systemd/system/multi-user.target.wants/openstack-glance-api.service to /usr/lib/systemd/system/openstack-glance-api.service.
    Created symlink from /etc/systemd/system/multi-user.target.wants/openstack-glance-registry.service to /usr/lib/systemd/system/openstack-glance-registry.service.
    
    
    [root@controller ~(keystone_admin)]# systemctl start openstack-glance-api.service \
    >   openstack-glance-registry.service
    
    ```

  - 최종 서비스 상태 확인 : `[root@controller ~(keystone_admin)]# openstack-status`

    ```powershell
    [root@controller ~(keystone_admin)]# openstack-status
    == Glance services ==
    openstack-glance-api:                   active
    openstack-glance-registry:              active
    == Keystone service ==
    openstack-keystone:                     inactive  (disabled on boot)
    == Support services ==
    mariadb:                                active
    dbus:                                   active
    rabbitmq-server:                        active
    memcached:                              active
    == Keystone users ==
    +----------------------------------+--------+
    | ID                               | Name   |
    +----------------------------------+--------+
    | 4965ccffb3b74b778da9d66d0529ad3e | myuser |
    | 5d358e5b069e4adaba0e33bca5240ede | admin  |
    | f1f3657ecd8448a9a504d681be35273f | glance |
    +----------------------------------+--------+
    == Glance images ==
    +----+------+
    | ID | Name |
    +----+------+
    +----+------+
    ```

  - 









- qemu : 







------

Xshell의 정보를 마운트 해서 VM ware에 올리자

- vmhgfs-fuse /mnt
- mkdir /win
- vmhgfs-fuse /win
- df -h
- cp cirros-0.3.5-x85_64-disk.vmdk /win/share
- 









------

#### [Nova 설치](https://docs.openstack.org/nova/rocky/install/controller-install-rdo.html)

- ```powershell
  [root@controller share(keystone_admin)]# cd /var/lib/mysql/
  [root@controller mysql(keystone_admin)]# ls
  aria_log.00000001  cinder  ib_logfile0  ibdata1   multi-master.info  mysql.sock  nova      nova_cell0      performance_schema  test
  aria_log_control   glance  ib_logfile1  keystone  mysql              neutron     nova_api  nova_placement  tc.log
  [root@controller mysql(keystone_admin)]# cd n
  neutron/        nova/           nova_api/       nova_cell0/     nova_placement/ 
  [root@controller mysql(keystone_admin)]# ls nova
  
  agent_builds.frm             instance_groups.frm               resource_providers.ibd                   shadow_instance_extra.frm             shadow_quotas.ibd
  agent_builds.ibd             instance_groups.ibd               s3_images.frm                            shadow_instance_extra.ibd             shadow_reservations.frm
  aggregate_hosts.frm          instance_id_mappings.frm          s3_images.ibd                            shadow_instance_faults.frm            shadow_reservations.ibd
  # DB의 정보가 쌍으로 만들어져 있음
  ```

- 포트가 열려있는지 확인 : `ss -nlp|grep PORT`

  - ```powershell
    [root@controller ~(keystone_admin)]# ss -nlp|grep 8774
    tcp    LISTEN     0      128       *:8774           
    ```

- http가 

  - keystone : 5000번 port
  - horizone : 80번 port
  - 

- Queue : RabbitMQService