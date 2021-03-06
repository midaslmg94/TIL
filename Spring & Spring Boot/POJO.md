### POJO(Plain Old Java Object)

- 간단히 말해서 `오래된 방식의 자바 오브젝트`
- 특정 자바 모델이나 기능, 프레임워크 등을 따르지 않는 자바 오브젝트
- 특정 기술과 환경에 종속, 의존하게 되면 가독성이 떨어져 유지보수가 어렵고, 의존성으로 인해 확장성이 매우 떨어지는 단점이 있다. 이는 객체지향 설계의 장점을 사용하지 못하는 것이다.
- 스프링 프레임워크는 POJO 방식의 프레임워크이다.
    - 예를 들어, 스프링에서 ORM기술을 사용하고 했을 때, ORM을 지원하는 프레임워크인 `Hibernate` 를 사용한다면 특정 기술에 종속되었으므로 POJO라고 할 수 없다.
    - 그런데, Hibernate는 스프링 개발에서 많이 사용되고 있다. 이는 스프링에서 정한 표준 인터페이스인 `JPA` 가 있기 때문이다. 여러 ORM 프레임워크는 JPA라는 표준 인터페이스 아래에서 구현되어 실행되는 것이다.