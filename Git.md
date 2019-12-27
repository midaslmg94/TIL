# Git

> Git은 분산 버전 관리 시스템(DVCS)의 일종이다.
>
> 소스코드의 이력을 남기고 관리할 수 있다.







## 준비사항

윈도우에서 git을 쓰기 위해서는 [git bash](https://gitforwindows.org/)을 설치한다. 

컴퓨터에 처음 설치하는 경우 커밋을 작성하는 사람(Author)를 global로 설정을 해야한다.



```bash
$ git config --global user.name midaslmg94
$ git config --global user.email midaslmg94@gmail.com
```







## 로컬 저장소 활용하기



### 1. `init`

```bash
$ git init
Initialized empty Git repository in C:/Users/HPE/Desktop/til/.git/
(master)$
```

* git 저장소를 생성
* git 저장소를 생성하면, 해당 디렉토리에 `.git` 폴더가 생성된다.
* `(master)`는 현재 작업중인 브랜치가 master라는 의미를 가지고 있다.





### 2. `add`

* working directory(작업공간)에서 staging area로 해당 파일을 이동시키기 위해서는 아래의 명령어를 사용한다.

  ```bash
  $ git add markdow.md # 특정 파일
  $ git add images/ # 특정 폴더
  $ git add . # 현재 디렉토리
  ```

  * `add` 전 상태

    ```bash
    $ git status
    On branch master
    
    No commits yet
    # 트래킹이 되지 않고 있는 파일들
    # --> commit 이력에 한번도 저장되지 않은 파일들. 즉 새로 생성된 파일
    Untracked files:
    # commit이 될 것들에 포함시키기 위해서는 add
    # --> staging area에 담으려면 add
      (use "git add <file>..." to include in what will be committed)
            Git.md
            images/
            markdown.md
    
    nothing added to commit but untracked files present (use "git add" to track)
    
    ```

  * `add` 이후 상태

    ```bash
    $ git add markdown.md
    
    HPE@DESKTOP-DFE1UPJ MINGW64 ~/Desktop/til (master)
    $ git status
    On branch master
    
    No commits yet
    # 커밋 될 변화들 
    # --> staging area에 있는 파일들
    Changes to be committed:
      (use "git rm --cached <file>..." to unstage) # 이 명령을 사용하면 staging area에 있던 파일이 working direcory로 내려온다.
            new file:   markdown.md # 내가 add 한 파일
    
    # working directory에 있는 파일들
    Untracked files:
      (use "git add <file>..." to include in what will be committed)
            Git.md
            images/
    
    ```
    
    



### 3. `commit`

* 이력을 남기기 위해서는 아래의 명령어를 활용한다.

  ```bash
  $ git commit -m '커밋메시지'
  [master (root-commit) fd8e951] Markdown 활용법 추가
   1 file changed, 154 insertions(+)
   create mode 100644 markdown.md
  ```

* 커밋메시지는 해당 이력을 나타낼 수 있도록 작성하는 것이 중요하고, 항상 일관성있게 작성하자. 

* 커밋 내역은 아래의 명령어로 확인 가능하다

  ```bash
  $ git log
  
  commit fd8e9514ac3ca863059c78bc46c149ef32ec297f (HEAD -> master)
  Author: mk <midaslmg94@gmail.com>
  
  Date:   Fri Dec 27 14:19:34 2019 +0900
  
      Markdown 활용법 추가
  
  $ git log --oneline
  
  fd8e951 (HEAD -> master) Markdown 활용법 추가
  
  $ git log -1 # 한 개의 커밋 메시지만 보는 것
  
  commit fd8e9514ac3ca863059c78bc46c149ef32ec297f (HEAD -> master)
  Author: mk <midaslmg94@gmail.com>
  Date:   Fri Dec 27 14:19:34 2019 +0900
  
      Markdown 활용법 추가
  
  
  ```
  
  
  
* `git commit -am "커밋메시지"` : `add`와 `commit` 을 한번에 하게 해준다.

  ```bash
  $ git commit -am "오타 수정"
  [master 8c399dc] 오타 수정
   1 file changed, 2 insertions(+), 1 deletion(-)
  
  ```






## 원격저장소(remote repository) 활용하기



> 원격 저장소를 제공하는 서비스는`gitlab`, `github`, `bitbucket` 등 다양하나 `github`을 기준으로 설명한다.



### 1. 원격 저장소에서 로컬 저장소로 프로젝트 가져오기

```bash
$ git clone github_url
```

- 원격 저장소에 만들어진 레파지토리를 `clone` 해온다.
- 파일을 생성, 수정하고 `git add .` , `git commit -m " "`을 해준다.
- 마지막으로 `git push`를 해주면 반영된다.



### 2. 원격 저장소 설정하기



```bash
$ git remote add origin github_url
```

- 원격저장소(`remote`)를 `origin`으로 `github_url`을 추가(`add`)한다.

- 설정된 원격 저장소 목록을 확인하기 위해서는 아래의 명령어를 활용한다.

  ```bash
  $ git remote -v
  origin  https://github.com/midaslmg94/TIL.git (fetch)
  origin  https://github.com/midaslmg94/TIL.git (push)
  ```

- 설정된 원격 저장소를 삭제하기 위해서는 아래의 명령어를 활용한다.

  ```bash
  $ git remote rm origin
  ```



### 2.`push`

```bash
$ git push origin master
```

- `origin` 으로 설정된 url에 `master` 브랜치로 `push` 한다.



## 3. git flow

- `git init` 을 통해 최초에 저장소를 만듦
- `git clone` 를 통해 만들어진 저장소를 가져옴 
- `git push` 를 통해 업로드
- `git pull` 을 통해 다운로드

