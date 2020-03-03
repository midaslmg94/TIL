# Chapter 06 - 컴포넌트 반복



리액트에서 반복되는 내용을 처리할 때는 자바스크립트 배열 객체의 내장함수인 `map` 함수를 사용하여 반복되는 컴포넌트를 렌더링 할 수 있다.  



문법은 다음과 같다. `arr.map(callback, [thisArg])`

- callback : 새로운 배열의 요소를 생성하는 함수
  - currentValue : 현재 처리하는 요소
  - index : 현재 처리하고 있는 요소의 index 값
  - array : 현재 처리하고 있는 원본 배열
- thisArg : callback 함수 내부에서 사용할 this 레퍼런스



배열에 원소를 추가할 때 사용하는 함수

- `push` 함수 : 기존 배열 자체를 변경
- `concat` 함수 : 새로운 배열을 만들어준다.



배열의 원소를 삭제할 때 사용하는 함수

- `filter` 함수 : 배열에서 특정 조건을 만족하는 원소들만 분류 가능

  ```react
  const numbers = [1,2,3,4,5,6];
  const biggerThanThree = numbers.filter(number=>number>3)
  // [4,5,6]
  ```

  

