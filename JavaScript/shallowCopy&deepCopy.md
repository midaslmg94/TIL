# 얕은 복사 vs 깊은 복사



#### 얕은복사(shallow copy)

- `객체 자료형`의 `참조`복사 : 원본 데이터가 바뀌면 서로의 데이터에 영향을 준다

  ```javascript
  // 객체 자료형의 복사 - 얕은 복사(shallow copy)
      let oldArray = [1, 2, 3, 4];
      let newArray = oldArray;
      oldArray[0] = 5;
      console.log(oldArray, newArray); // [5,2,3,4] [5,2,3,4]
      newArray[1] = 6;
      console.log(oldArray, newArray); // [5,6,3,4] [5,6,3,4]
  ```

- 참조 복사라고도 한다

- 두 배열은 `같은 위치`를 참조 하고 있다.



#### 깊은 복사(deep copy)

- `기본 자료형`의 `값` 복사 : 원본 데이터가 바뀌어도 영향을 주지 않음

  ```javascript
  // 기본 자료형의 복사 - 깊은 복사(deep copy)        
      let oldValue =10;
      let newValue = oldValue;
      console.log(oldValue, newValue); // 10 10
      newValue=9;
      console.log(oldValue, newValue); // 10 9
      oldValue=20;
      console.log(oldValue, newValue); // 20 9
  ```

- `객체 자료형` `값` 복사 

  1. 새로운 객체를 만들고 `for in` 반복문으로 원본 객체에 반복문을 돌려서 키와 값을 하나하나 옮기는 것

  2. 전개 연산자 사용

     ```javascript
         let oldArray = [1,2,3,4];
         let newArray = [...oldArray];
         oldArray[0]= 5;
         console.log(oldArray, newArray); // [5,2,3,4] [1,2,3,4]
         newArray[1]=6;
         console.log(oldArray, newArray); // [5,2,3,4] [1,6,3,4]
     ```

     

