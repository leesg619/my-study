# 영한님 스프링 입문

>스프링 입문 - 코드로 배우는 스프링 부트, 웹 MVC, DB 접근 기술
스프링 입문자가 예제를 만들어가면서 스프링 웹 애플리케이션 개발 전반을 빠르게 학습할 수 있습니다. 처음 스프링을 접하는 분들께 강력 추천합니다.

>최대한 쉽게 서술하는 방식으로 정리했으며, 딥한내용은 뒤에서 자세히 다룹니다.

---

### 기본 FLOW
WEB browser "/hello" url 호출 -> 내장 톰캣 서버에서 자동으로 `@GetMapping("hello")` 가 있는 컨트롤러를 찾음  
-> 리턴 값 반환 시 상황에 맞는 Handler / Resolver 가 적절하게 처리 

### 스프링 정적 컨텐츠 동작 FLOW (static)
WEB browser "/hello-static.html" url 호출 -> 내장 톰캣 서버가 요청을 받고 스프링 컨테이너에 넘겨줌   
-> 스프링 컨테이너는 컨트롤러에서 먼저 찾음 (우선순위를 가짐)   
-> 컨트롤러에 없다면 resources; static/hello-static.html 을 찾아서 있다면 넘겨줌  

---

### MVC 와 Template Engine 동작
WEB browser " /hello-mvc?name=spring!" url 호출 -> 내장 톰캣 서버가 요청을 받고 스프링 컨테이너에 넘겨줌   
-> 스프링 컨테이너에서 매핑된 컨트롤러를 찾고, Model 에 name:spring! 셋팅 후 return "hello-template"; viewResolver 가 동작하면서
템플릿 엔진을 연결시켜줌.  
템플릿 엔진이 변환 후 웹브라우저에 넘겨주며 종료

```java
@GetMapping("hello-mvc")
public String helloMvc(@RequestParam("name") String name, Model model) {
    model.addAttribute("name", name);
    return "hello-template";
}
<body>
<p th:text="'hello ' + ${name}">hello! empty</p>
</body>
```

---

### API 동작 @ResponseBody
http body에 내용 직접 반환. 기존 mvc방식처럼 동작하는것이 아니고 (ViewResolver X) `httpMessageConverter`가 동작한다.  
ex) `JsonConverter` 으로 객체 return 처리

---

### TEST (JUnit Framework)
모든 테스트는 각각의 메소드에 대하여 독립적이어야한다.  
(한번에 모든 테스트를 실행했을 때 각 테스트가 서로의 테스트에 영향주지 않도록 해야함)  

- @AfterEach  
한번에 여러 테스트를 실행하면 메모리 DB에 직전 테스트의 결과가 남을 수 있다.  
이렇게되면 다음 이전 테스트 때문에 다음 테스트가 실패할 가능성이 있다.   
@AfterEach 사용 시 각 테스트가 종료 될 때 마다 이 기능을 실행한다. 적절히 활용하여 테스트를 작성한다.

- @BeforeEach  
각 테스트 실행 전에 호출된다. 테스트가 서로 영향이 없도록 항상 새로운 객체를 생성하고, 의존관계도 새로 맺어주는 방식으로 활용 가능하다.

---

### 컴포넌트 스캔과 자동 의존관계 설정 `@Component`
* `@Component` 애노테이션이 있으면 컨테이너 올라갈 때 스캔해서 빈 객체로 자동 등록  
* `@Autowired` 는 컨테이너가 연결관계를 매칭해준다 (자동 의존 관계 설정)  
+DI의 기본이라고 할 수 있음 (바깥에서 스프링이 주입시킴)
* 메인 메소드의 `@SpringBootApplication` 애노테이션이 하는 컴포넌트 스캔의 대상 : 기본적으로는 해당 메인메소드가 속한 패키지  
ex package com.practice.intro; (설정으로 바꿀수 있음)  
* `@Component` 를 포함하는 다음 애노테이션도 스프링 빈으로 자동 등록된다. `@Controller` `@Service` `@Repository`

```java
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    
    @Autowired 
    public MemberService(MemberRepository memberRepository) {
    this.memberRepository = memberRepository;
    }
}
```

* 생성자에 `@Autowired` 를 사용하면 객체 생성 시점에 스프링 컨테이너에서 해당 스프링 빈을 찾아 주입한다.  
생성자가 1개만 있으면 @Autowired 는 생략할 수 있다.
* 스프링 빈을 등록할 때, 기본으로 싱글톤으로 등록한다. (설정으로 바꿀 순 있지만 대부분 싱글톤 사용)

---

### 자바 코드로 직접 스프링 빈 등록 `@Configuration` `@Bean`
* 상황에 따라 구현 클래스를 변경해야 할 때 설정을 통해 스프링 빈으로 등록하는 방법이다.
* `@Service`, `@Repository`, `@Autowired` 제거 후 아래와 같이 Configuration 자바 파일로 관리

```java
@Configuration
public class SpringConfig {
    @Bean
    public MemberService memberService() { return new MemberService(memberRepository()); }
    @Bean
    public MemberRepository memberRepository() { return new MemoryMemberRepository(); }
}
```

* 최상위인 컨트롤러는 @Controller , @Autowired 그대로 사용함. 
-> 스프링은 @Controller annotation 이 있어야 스프링의 MVC 컨트롤러 인식할 수 있고,  
-> 어차피 스프링부트는 기본으로 컴포넌트 스캔을 하기 때문에 간편히 annotation 사용하는 게 편함.  
* 주의: @Autowired 를 통한 DI는 helloController , memberService 등과 같이 스프링이 관리하는 객체에서만 동작한다.  
스프링 빈으로 등록하지 않고 내가 직접 생성한 객체에서는 동작하지 않는다.

---

### 스프링 통합 테스트
* 스프링 컨테이너와 DB까지 연결한 통합 테스트를 말한다.
* 보통 스프링 컨테이너까지 올리는 스프링 통합 테스트보다는, 안 올려도 되는 단위 테스트가 더 좋은 테스트일 확률이 높기 때문에 최대한 안쓰는 것이 좋다.
* 테스트 시 필드 `@Autowired`를 쓰면 시간 단축 + 편하다.   
(어차피 test단은 끝단이라 한번 테스트하고 끝이기 때문에 어디서 갖다쓰거나 엉킬 일이 없으므로 굳이 생성자로 할 필요는 없다고 한다.)
  
#### Annotation
* `@SpringBootTest` : 스프링 컨테이너와 테스트를 함께 실행한다.
* `@Transactional` : 테스트 케이스에 이 애노테이션이 있으면, 테스트 시작 전에 트랜잭션을 시작하고,  
테스트 완료 후에 커밋을 하지 않기 때문에 롤백된다. 이렇게 하면 DB에 데이터가 남지 않으므로 다음 테스트에 영향을 주지 않는다.  
  (서비스단에 붙으면 롤백하지 않고 당연히 정상적으로 트랜잭션 동작을 하며, 테스트 케이스에 붙었을 때만 이런 식으로 항상 롤백하도록 동작함)

---

### JPA
* JPA는 기존의 반복 코드는 물론이고, 기본적인 SQL도 JPA가 직접 만들어서 실행해준다.
* JPA를 사용하면, SQL과 데이터 중심의 설계에서 객체 중심의 설계로 패러다임을 전환을 할 수 있다.
* JPA를 사용하면 개발 생산성을 크게 높일 수 있다.
* 참고  
build.gradle : `spring-boot-starter-data-jpa` 는 내부에 jdbc 관련 라이브러리를 포함하므로 jdbc dependency 는 제거해도 된다.  
application properties :  
`show-sql` : JPA가 생성하는 SQL을 출력한다.  
`ddl-auto` : JPA는 테이블을 자동으로 생성하는 기능을 제공하는데 `none` 를 사용하면 해당 기능을 끈다.  
`create` 를 사용하면 엔티티 정보를 바탕으로 테이블도 직접 생성한다.  
`starter-jpa dependency` 추가 후 `application properties` 정보 셋팅에 따라  
스프링 부트는 데이터베이스 커넥션 정보를 담은 `EntityManager를` 생성하고 스프링 빈으로 생성해놓는다.

#### 서비스 계층에 트랜잭션 추가
* org.springframework.transaction.annotation.Transactional 를 사용하자.
* 스프링은 트랜잭션 붙은 메서드를 실행할 때 트랜잭션을 시작하고, 메서드가 정상 종료되면 트랜잭션을 커밋한다.  
만약 런타임 예외가 발생하면 롤백한다.
* JPA를 통한 모든 데이터 변경은 트랜잭션 안에서 실행해야 한다.

---

### 스프링 데이터 JPA