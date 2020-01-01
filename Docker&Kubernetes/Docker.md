## 도커



- `nginx ` : 웹 서버. OS가 반드시 필요하다. OS위에서 동작하기 때문. 이 위에 어플리케이션을 올릴 수 있다. 

- OS에 특화된 기능(OS와 똑같은 환경 구축 필요)을 써야한다면 도커보다는 기존에 사용하던 `VirtualBox`, `VMWare`를 사용하는 것이 더 낫다.

- 가상화 OS : 호스트 OS가 가지고 있던 리소스를 `Guest OS`로 나눠주는 것

  - Guest OS 

    - vagrant에서 사용하고 있는 `node01`, `node02`, `node03`이 모두 Guest OS이다. 아무 작업을 하지 않아도 메모리를 고정적으로 차지한다.

    - OS가 더 필요하면 Guest OS를 추가해주기만 하면 된다.

    - Guest OS는 독립적이기 때문에 운영체제에 특화된 기능을 사용하기 좋다.

      

- 컨테이너 형 가상화 : 리소스 사용하는 측면에서 효과적. OS는 호스트 OS 1개뿐임

## Docker

- 컨테이너 기반의 오픈소스 가상화 플랫폼
- AWS, Azure, GCP에서 실행 가능
- 기존 가상화 : OS를 가상화했음
  - VMWare, VirtualBox(Host OS위에 Guest OS 전체를 가상화) ex) vagrant, centOS를 설치
  - 단점 : 무겁고 느림. Guest OS가 물리적인 리소스를 차지함
- 컨테이너 기반의 가상화 
  - 기존의 Host OS위에 Docker를 설치한다. 
  - Docker 서버 : 적은 용량, 배포단계에서도 적은 용량
  - Docker 클라이언트 : 커맨트 명령을 통해 도커를 사용
  - Docker가 설치되면 그 위에 바로 어플리케이션 A, B를 설치한다. 설치가 빠름. 
  - 만일 어플리케이션 A, B가 공통적인 라이브러리를 사용할 경우 이를 공유 할 수 있음
  - OS끼리 공유가 되기 때문에 설치된 파일들을 공유 가능하다. 



- nginx 웹 어플리케이션방식. 도커를 사용하면 용랑이 많이 아낄 수 있다.
  - 예를들어 ubuntu, nginx, web app가 파일
- 이미지 : 실행가능한 상태, 필요한 리소스. 이미지를 파일 설치
- 도커 사이트에서 필요한 이미지가 있는 사진을 가지고 오면 된다. [DockerHub] 





### Docker 실행

**`docker run -t -p 9000:8080 gihyodocker/echo:latest`** 

- `gihyodocker/echo ` : 이미지를 다운받아온 레파지토리 이름. 
- `latest` : 태그. 대개 버전을 의미함
- `-t ` : 터미널 같은 역할을 하는 화면을 띄워주는 옵션
- `-p` : 포트포워딩 옵션
  - 9000:8080 은 이미지가 갖고있는 포트번호가 8080번이고, 외부에서 9000번 포트번호로 접속하면 이 8080포트로 포트포워딩 시켜준다는 것을 의미한다.  성공적으로 서버가 실행되면 아래와 같이 `localhost:9000` 으로 접속했을 때 Hello Docker가 출력된다![img](https://lh5.googleusercontent.com/OrbvZaffYeJdbfzzhediP_1UNNfsvgfFwL5pBGs7ObX7eeEQM5TMnFCJ_oVh67FN9vK7c5Ykr7-IoGmvxqwxk4hOL8ApMCKzF_WG6YjKey6M37U783nHUeBqYK9NXFwtruGCsSSd)



### Dockerfile

Dockerfile는 Docker이미지 설정 파일이다. 

- FROM
- RUN
- COPY
- CMD





