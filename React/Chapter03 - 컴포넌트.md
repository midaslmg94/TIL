# Chapter 03 - 컴포넌트



#### 클래스형 컴포넌트 vs 함수형 컴포넌트

- 클래스형 컴포넌트 -- `단축키 : rcc`
  - `render()`가 꼭 필요하다.
- 함수형 컴포넌트 -- `단축키 : rsc`
  - 메모리 자원을 클래스형 컴포넌트 보다 덜 사용한다.
  - state와 라이프 사이클 API 사용이 불가능하지만, Hooks의 도입으로 해결 가능하다
  - 리액트 공식 매뉴얼에서는 함수형 컴포넌트와 Hooks를 권장한다.



#### props : 컴포넌트 속성을 설정할 때 사용하는 요소.

- `props` 값은 해당 컴포넌트를 불러와 사용하는 부모 컴포넌트에서 설정할 수 있다.

- 컴포넌트 자신은 `props`를 읽기 전용으로만 사용가능하다
- `props`를 바꾸려면 부모 컴포넌트에서 바꿔야 한다.

 ##### 1. 함수형 컴포넌트에서 props 사용하기

```react
# App.js

import React from 'react';
import MyComponent from './MyComponent'
const App = () => {
  return (
    <div>
      <MyComponent name="이민기">리액트</MyComponent>
    </div>
  );
};


export default App;
```



- 비구조화 할당을 통해 `props` 값을 넘겨주었다.
- `defaultProps`를 사용하면 부모에서 props의 값이 넘어오지 않았을 때의 사용할 값을 설정할 수 있다.

```react
# MyComponent.js

import React from 'react';
const MyComponent = ({name, children}) => {    
    return (
        <div>
            안녕하세요 제 이름은 {name}입니다.<br/>
            children 값은 {children}입니다.
        </div>
    );
};
MyComponent.defaultProps = {
    name: '기본이름'
};

export default MyComponent;
```



##### 2. 클래스형 컴포넌트에서 props 사용하기

```react	
# App.js
import React from 'react';
import MyComponent from './MyComponent'
class App extends React.Component {
  render(){
    return (
      <div>
        <MyComponent name="이민기">리액트</MyComponent>
      </div>
    );    
  }
};

export default App;

# MyComponent.js
import React, {Component} from 'react';
import Protypes from 'prop-types';
class MyComponent extends Component {
    render() {
        const {name, children} = this.props;
        return (
            <div>
                안녕하세요 제 이름은 {name}입니다.<br/>
                children 값은 {children}입니다.
            </div>
        );
    }
}
MyComponent.defaultProps = {
    name: '기본이름'
};
export default MyComponent;
```



#### state : 컴포넌트 내부에서 바뀔 수 있는 값

##### 	1. 클래스형 컴포넌트가 가지는 `state`

클래스형 컴포넌트에서 `constructor`을 작성할 때는 반드시 `super(props)`를 호출 해야한다.

```react
class Counter extends Component { 
    constructor(props){
            super(props); //초기값 설정
            this.state={
                number:0
            };
        }
    render() {
    .
    .
    .
```



하지만 `construct` 메서드를 선언하지 않고도 `state`의 초기값을 설정할 수 있다. 아래와 같은 방식이 더 자주 쓰이며 보기도 좋다.

```react
class Counter extends Component {
    state={
        number:0,
        fixedNumber:0
    }
    render() {
        .
        .
        .
```

`render()` 함수에서 `state`를 조회할 때는 `this.state`를 쓰면 된다. 

```react
render() {
        const{number,fixedNumber}=this.state; // state 조회
        return (
            <div>
                <h1>{number}</h1>
                <button onClick = {()=>{
                    this.setState(prevState=>({
                        number: prevState.number+1
                    }));                    
                }}>
                    +1
                </button>
            </div>
        );
    }
```





##### 	2. 함수형 컴포넌트가 가지는 `useState`

- 배열의 비구조화 할당

  ```react
  const array = [1,2];
  const one = array[0];
  const two = array[1];
  ```

  위와 같은 코드를

  ```react
  const array = [1,2];
  const[one, two] = array;
  ```

  와 같이 표현하는 것을 `배열의 비구조화 할당` 이라고 한다

- `useState`사용

```react
import React,{useState} from 'react';

const Say = () => {
    const [message,setMessage] = useState("초기 상태"); // 상태의 초기값
    const onClickEnter = () => setMessage("안녕하세요");
    const onClickLeave = () => setMessage("안녕히 가세요");

    const [color, setColor] = useState('black');

    return (
        <div>
            <button onClick ={onClickEnter}>입장</button>
            <button onClick ={onClickLeave}>퇴장</button>
            <h1 style={{color}}>{message}</h1>
            <button style={{color:'red'}} onClick = {()=>setColor('red')}> 빨간색</button>
            <button style={{color:'green'}}onClick={()=>setColor('green')}>초록색</button>
            <button style={{color:'blue'}}onClick ={()=>setColor('blue')}>파란색</button>
        </div>
    );
};
export default Say;
```



- `state`의 값을 바꾸어야 할 때는 `setState()` 또는 `useState()`를 통해 전달받은  `setter`함수를 사용해야한다.