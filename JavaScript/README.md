# JavaScript 



- `for ~ in` vs `for ~ of`

  ```javascript
          let fruits = ["사과", "오렌지", "딸기", "바나나"];
          for(let idx_item in fruits){
              console.log(fruits[idx_item]);
          } # 값의 인덱스를 반환
  
          for(let item of fruits){
              console.log(item);
          } # 값 자체를 반환
  ```

- `array`연산

  ```javascript
  let arr1 =new Array(10); // 하나만 있으면 empty * 10
  console.log(arr1); // empty*10
  
  
  let arr2 = new Array(10,2); // 2개 이상이면 원소로 들어감
  console.log(arr2);  // [10, 2]
  
  
  let fruits = ['사과', '바나나','수박', '복숭아 ']
  #fruits.push('오렌지'); // 맨 뒤에 항목 추가
  fruits.forEach(element => {
      console.log(element);    
  });
  
  #fruits.pop(); // 맨 뒤 항목 삭제
  console.log('-------------')
  fruits.forEach(element => {
      console.log(element);    
  });
  
  #fruits.shift();// 맨 앞 항목 삭제
  console.log('-------------')
  fruits.forEach(element => {
      console.log(element);    
  });
  
  #fruits.unshift("멜론"); // 맨 앞에 항목 추가
  console.log('-------------')
  fruits.forEach(element => {
      console.log(element);    
  });
  ```

- 가변인자 함수

  ```javascript
  function sumAll() {
      let sum = 0;    
      for (let i of arguments) {    
          sum += i;        
      }    
      return sum;    
  }
  
  let res1 = sumAll(1, 2, 3);
  let res2 = sumAll(1, 2, 3, 4);
  let res3 = sumAll(1, 2, 3, 4, 5);
  console.log(res1, res2, res3); // 6 10 15
  ```

- 

