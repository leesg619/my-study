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

### 