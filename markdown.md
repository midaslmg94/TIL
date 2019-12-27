# 마크다운 문법

## 제목(heading)

제목은 `#` 으로 작성하며, 개수로 제목의 레벨을 나타낸다. 

### 제목 3

#### 제목 4

##### 제목 5

###### 제목 6



## 목록

목록은 순서가 있는 목록, 순서가 없는 목록으로 구분된다.

1. 순서가 있는 목록의 작성법은 Word와 똑같음
2. 두번째 순서
   1. tab를 통해 목록의 레벨을 설정
   2. 엔터
3. 엔터



목록이 없는 것은 `*` 을 사용

* 순서가 없는 목록
  * tab를 통해 레벨 지정



## 코드 블록 ```` ` 을 사용

```mysql
-- sql에서 주석
select * 
from tables;
```

```python
# python 주석
print("hello")
def foo:
    return bar
```

```c++
// c++ 문법
int main(){
    int a = 10;
    for(int i=0; i<a; i++){
        cout<<"hello"<<endl;
    }
    
}
```

```bash
$git init
```





## 인용문  `>`

> 인용문 작성 가능 





## 링크

`[google](https://google.com)`

`[이름](url주소)`

[google](https://google.com) ctrl+click하면 이동가능











# typora의 기능 사용

## 표

본문 - 표 - 표 삽입

| 순번 | 이름 | 나이 |
| ---- | ---- | ---- |
| 1    |      |      |
| 2    |      |      |
| 3    |      |      |





## 이미지 삽입

<img src="C:\Users\HPE\Desktop\output_3362843146.jpg" style="zoom:50%;" />



* 기본 설정으로는 이미지의 절대경로`<img src="C:\Users\HPE\Desktop\output_3362843146.jpg" style="zoom:50%;" />`가 표시된다. 이렇게 github에 올리면 잘려서 안보임.

  

* 아래 설정을 typora에 한다.

* 파일 > 환경설정 > 이미지 탭 클릭

  * copy image to custom folder 선택
    * `./images`
  * 로컬 이미지에 위 규칙 적용
  * 온라인 이미지에 위 규칙 적용
  * 가능하다면 상대적 위치 사용
  * `![보노보노](images/보노보노-1577421673017.jpg)`![보노보노](images/보노보노-1577421673017.jpg)







## 수직선

**굵게(볼드체)**

`**`굵게`**`

`*`*기울임(이텔릭체*`*`

`~~`~~취소선~~`~~`







