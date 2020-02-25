# Chapter 04 - 이벤트 핸들링



이벤트는 HTML과 비슷하지만 조금 다르다.

- 이벤트의 이름은 카멜 표기법으로 작성한다. ex) `onClick` , `onKeyUp` 
- 이벤트에 실행할 자바스크립트의 코드를 전달하는 것이 아니라, **함수 형태**의 값을 전달한다.



### 클래스형 컴포넌트로 구현



##### 메서드 만들기

```react
import React, {Component} from 'react';

class EventPractice extends Component {
    state = {
        message: "aa"
    }
    constructor(props){
        super(props);
        this.handleChange=this.handleChange.bind(this);
        this.handleClick=this.handleClick.bind(this);
    }
    handleChange(e){
        this.setState({
            message: e.target.value
        });
    }
    handleClick(){
        alert(this.state.message);
        this.setState({
            message:''
        })
    }
    render() {
        return (
            <div>
                <h1>이벤트 연습</h1>
                <input
                    type="text"
                    name="message"
                    placeholder="아무거나 입력하세요"
                    value={this.state.message}
                    onChange={this.handleChange}></input>
                <button
                    onClick={this.handleClick}>확인</button>
            </div>
        );
    }
}

export default EventPractice;
```



##### Property Initializer Syntax를 사용한 메서드 작성

- 메서드 바인딩은 생성자 메서드에서 하는데, 이 작업이 귀찮다.  새로운 메서드를 만들 때 마다 `constructor`도 수정해야 하기 때문이다.

- 이를 조금 쉽게 한것이 바벨의 `transform-class-properties` 문법을 사용하여 `화살표 함수`의 형태로 메서드를 정의할 수 있다.

- 위의 코드를 `transform-class-properties` 문법을 사용하여 다시 만들어 보자.

```react
import React, {Component} from 'react';

class EventPractice extends Component {
    state = {
        message: "aa"
    }
// constructor 부분이 아예 빠짐
    handleChange=(e)=>{ // 기존 코드 : handleChange(e) 
        this.setState({
            message: e.target.value
        });
    }
    handleClick=()=>{  // 기존 코드 :handleClick()
        alert(this.state.message);
        this.setState({
            message:''
        })
    }
    render() {
        return (
            <div>
            ...           
            </div>            
        );
    }
}

export default EventPractice;
```



##### input 여러개

- `event` 객체를 활용한다. -> `e.target.name` 사용

```react
import React, {Component} from 'react';

class EventPractice extends Component {
    state = {
        username: "",
        message: ""
    }
    handleChange = (e) => {
        this.setState({
            [e.target.name]: e.target.value // 기존 코드 : message: e.target.value
        });
    }
    handleClick = () => {
        alert(this.state.username + ':'+this.state.message);
        this.setState({message: "", username: ""})
    }
    render() {
        return (
            <div>
                <h1>이벤트 연습</h1>
                ...
            </div>
        );
    }
}

export default EventPractice;
```



##### `onKeyPress` 이벤트 핸들링 

- 키를 눌렀을 때 발생하는 이벤트를처리

```react
import React, {Component} from 'react';

class EventPractice extends Component {
   ...
    handleKeyPress =(e)=>{
        if(e.key==='Enter'){
            this.handleClick();
        }
    }
    render() {
        return (
            <div>
                ...
                <input
                    type="text"
                    name="message"
                    placeholder="아무거나 입력하세요"
                    value={this.state.message}
                    onChange={this.handleChange}
                    onKeyPress={this.handleKeyPress}></input>
                <button onClick={this.handleClick}>확인</button>
            </div>
        );
    }
}

export default EventPractice;
```





### 함수형 컴포넌트로 구현

- 위의 코드들을 함수형 컴포넌트로 구현해보자

```react
import React, {useState} from 'react';

const EventPractice = () => {
    const [username, setUsername] = useState("");
    const [message, setMessage] = useState("");
    const onChangeUsername = e => setUsername(e.target.value);
    const onChangeMessage = e => setMessage(e.target.value);

    const onClick = () => {
        alert(username + " : " + message);
        setUsername(""); //???
        setMessage(""); //???
    }

    const onKeyPress = (e) => {
        if (e.key === 'Enter') {
            onClick();
        }
    };
    return (
        <div>
            <input
                type="text"
                name="username"
                placeholder="사용자명"
                value={username}
                onChange={onChangeUsername}/>
            <input
                type="text"
                name="message"
                placeholder="아무거나 입력"
                value={message}
                onChange={onChangeMessage}
                onKeyPress={onKeyPress}/>
                <button onClick={onClick}>확인</button>
        </div>
    );
};

export default EventPractice;
```



- `useState`를 통해 사용하는 상태에 문자열이 아닌 **객체**를 넣음

  ```react
  import React, {useState} from 'react';
  
  const EventPractice = () => {
      const [form,setForm] = useState({
          username:'',
          message:''
      });
      const{username, message}=form;
      const onChange = e =>{
          const nextForm={
              ...form,
              [e.target.name]:e.target.value
          };
          setForm(nextForm);
      }
  
      const onClick = () => {
          alert(username + " : " + message);
          setForm({
              username:'',
              message:''
          });
      }
  
      const onKeyPress = (e) => {
          if (e.key === 'Enter') {
              onClick();
          }
      };
      return (
          <div>
              <input
                  type="text"
                  name="username"
                  placeholder="사용자명"
                  value={username}
                  onChange={onChange}/>
              <input
                  type="text"
                  name="message"
                  placeholder="아무거나 입력"
                  value={message}
                  onChange={onChange}
                  onKeyPress={onKeyPress}/>
                  <button onClick={onClick}>확인</button>
          </div>
      );
  };
  
  export default EventPractice;
  ```

