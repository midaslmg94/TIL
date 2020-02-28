# Chapter 05 - ref: DOM에 이름 달기



#### ref를 사용하는 상황

- DOM을 직접적으로 건드려야 하는 작업을 할 때 `ref`를 사용한다.
- 클래스형 컴포넌트에서 사용한다. 
- 함수형 컴포넌트에서 ref를 사용하려면 Hooks를 사용해야한다



------

#### 클래스형 컴포넌트에서 ref 사용

state를 사용하여 필요한 기능을 구현하기 어려운 경우는 다음과 같다

- 특정 input에 포커스 주기
- 스크롤 박스 조작하기
- Canvas 요소에 그림그리기 등



------

#### ref 사용하기

1. ##### 콜백 함수를 통한 ref 설정

   - 가장 기본적인 방법

   - 사용법 

     ```
      <input ref={(ref)=>{this.input=ref}}> 
     ```

2. ##### createRef를 통한 ref 설정

   - 리액트에 내장되어 있는 `createRef` 함수를 사용한다. 이것은 `리액트 v16.3`부터 도입된 기능이다.

   - `React.createRef()` 키워드를 사용한다.

   - 사용법

     ```react
     class RefSample extends Component {
         input = React.createRef();
         handleFocus = () => {
             this.input.current.focus();
         }
         render() {
             return (
                 <div>
                     <input ref={this.input}/>
                 </div>
             );
         }
     }
     ```

     



------

#### 주의 사항

- 서로 다른 컴포넌트끼리 데이터를 교류할 때 `ref`를 사용하는 것은 좋지 않다. 리액트 설계에 맞지 않다. 
- 컴포넌트끼리 데이터를 교류할 때는 부모 ​<-> 자식 흐름으로 교류해야 한다.
- Redux, Context API 를 사용하여 데이터 교류를 효율적으로 할 수 있다.
- 함수형 컴포넌트에서는 `useRef` 라는 `Hook`함수를 사용한다.

