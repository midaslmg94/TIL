## Mysql 연동&실습

- Dockerfile
  - docker build -t test_mysql:latest .
- docker-compose.yml
  - version:"3"
  - services :
    - my_sql:
      - image:test_mysql