# Chapter 07 - 라이프 사이클 메서드





라이프사이클 메서드의 종류는 총 9가지이다.

- Will 접두사가 붙은 메서드 : 어떤 작업을 작동하기 전에 실행되는 메서드
- Did 접두사가 붙은 메서드 : 어떤 작업을 작동한 후에 실행되는 메서드



라이프사이클 메서드는 컴포넌트의 상태에 변화가 있을 때 마다 실행하는 메서드이다. 이 메서드들은 서드파트 라이브러리를 사용하거나, DOM을 직접 건드려야 하는 상황에서 유용하다.

또한, 컴포넌트 업데이트의 성능을 개선할 때는 `shouldComponentUpdate`가 중요하게 사용된다.



### [라이프 사이클 메서드](https://ko.reactjs.org/docs/react-component.html) 

![img](https://user-images.githubusercontent.com/6733004/45587740-f8ed0e00-b944-11e8-9c99-7baab37944e8.jpg)



- 마운트 : DOM이 생성되고 웹 브라우저상에 나타나는 것. 페이지에 컴포넌트가 나타난다. 마운트 시 호출하는 메서드는 다음과 같다

  - [`constructor()`](https://ko.reactjs.org/docs/react-component.html#constructor): 컴포넌트를 새로 만들 때마다 호출되는 `클래스 생성자 메서드`
  - [`getDerivedStateFromProps()`](https://ko.reactjs.org/docs/react-component.html#static-getderivedstatefromprops)  : `props` 값을 `state`에 넣을 때 사용하는 메서드
    - v16.3 이후 새로 만들어진 메서드
  - [`render()`](https://ko.reactjs.org/docs/react-component.html#render)  : UI를 렌더링 하는 메서드
  - [`componentDidMount()`](https://ko.reactjs.org/docs/react-component.html#componentdidmount) : 컴포넌트가 웹 브라우저에 나타난 후 호출하는 메서드
    - 자바 스크립트의 `setTimeout`, `setInterval`, 네트워크 요청과 같은 `비동기 작업`을 처리 할 수 있다 

  

- 업데이트 : 컴포넌트가 업데이트하는 경우는 다음과 같다.

  > 1. `props`가 바뀔 때 : 컴포넌트에 전달하는 `props`의 값이 바뀌면 컴포넌트 렌더링이 이루어 진다
  > 2. `state`가 바뀔 때 : `setState`를 통해 업데이트 될 때.
  > 3. 부모 컴포넌트가 리렌더링 될 때, 자식 컴포넌트 또한 리렌더링 된다.
  > 4. `this.forceUpdate`로 강제로 렌더링을 트리거 할 때

  

  컴포넌트가 다시 렌더링 될 때(업데이트 될 때) 호출되는 메서드는 다음과 같다.

  - [`getDerivedStateFromProps()`](https://ko.reactjs.org/docs/react-component.html#static-getderivedstatefromprops) : 마운트 과정에서 호출되는 메서드와 동일하다.`props`의 변화에 따라 `state`값에 변화를 주고 싶을 때 사용

  

  - [`shouldComponentUpdate() `](https://ko.reactjs.org/docs/react-component.html#shouldcomponentupdate) : 컴포넌트가 `리렌더링` 해야할 지 말아야 할지를 결정하는 메서드. 

    - `props` 또는 `state` 를 변경했을 때 리렌더링의 여부를 결정

    - `true` 또는 `false` 값을 반환하며, `true` 값을 반환할 경우 다음 라이프 사이클 메서드를 실행한다. `false` 값을 반환할 경우 작업을 중지한다. (`default`값은 `true`)

    - 현재 `props`와 `state` 는 `this.props`, `this.state`로 접근하고, 새로 설정될 `props`와 `state`는 `nextProps`와 `nextState`로 접근한다

      

  - [`render()`](https://ko.reactjs.org/docs/react-component.html#render) : 컴포넌트를 리렌더링 한다.

  - [`getSnapshotBeforeUpdate() `](https://ko.reactjs.org/docs/react-component.html#getsnapshotbeforeupdate) : 컴포넌트의 변화를 DOM에 반영하기 바로 직전에 호출하는 메서드

    - 리액트 v16.3 이후 만들어진 메서드

  - [`componentDidUpdate()`](https://ko.reactjs.org/docs/react-component.html#componentdidupdate) : 컴포넌트의 업데이트 작업이 끝난 후 호출하는 메서드

    - `prevProps` 또는 `prevState`를 사용하여 컴포넌트가 이전에 가졌던 데이터에 접근 가능

  

- 언마운트 : 컴포넌트가 DOM 상에서 제거될 때 호출하는 메서드

  - [`componentWillUnmount()`](https://ko.reactjs.org/docs/react-component.html#componentwillunmount) : 컴포넌트가 웹 브라우저 상에서 사라지기 전에 호출하는 메서드

    





