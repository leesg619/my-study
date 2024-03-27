## Practical Testing: 실용적인 테스트 가이드

> 프로덕션 코드의 품질을 보장하는 탄탄한 테스트 코드 작성법  
> 스프링 & JPA 기반 프로젝트에서의 테스트 코드 작성법  
> 테스트 프레임워크 - JUnit5, Mock 프레임워크 - Mockito  
> 테스트 코드를 통한 문서 자동화 도구 - Spring REST Docs

---

### 테스트 코드 개요

#### 테스트 코드를 작성하지 않는다면

- 변화가 생기는 매순간마다 발생할 수 있는 모든 Case를 고려해야 한다.
- 변화가 생기는 매순간마다 모든 팀원이 동일한 고민을 해야 한다.
- 빠르게 변화하는 소프트웨어의 안정성을 보장할 수 없다.

#### 올바른 테스트 코드는

- 자동화 테스트로 비교적 빠른 시간 안에 버그를 발견할 수 있고, 수동 테스트에 드는 비용을 크게 절약할 수 있다.
- 소프트웨어의 빠른 변화를 지원한다.
- 케이스 별 검증 : 팀원들의 집단 지성을 팀 차원의 이익으로 승격시킨다.
- 가까이 보면 느리지만, 멀리 보면 가장 빠르다.

---

### Practice : 요구사항

- 주문 목록에 음료 추가/삭제 기능
- 주문 목록 전체 지우기
- 주문 목록 총 금액 계산하기
- 주문 생성하기

+ 한종류의 음료 여러잔을 한번에 담는 기능 추가
+ 가게 운영 시간(10:00~22:00) 외에는 주문을 생성할 수 없다.

---

### 수동 테스트 vs 자동화된 테스트

#### 수동 테스트

- 콘솔에 찍힌 글자를 보면서 테스트가 동작대로 가는 지 체크 -> 결국 최종 확인 주체가 사람이 되게 짠 코드
- 다른 사람이 이 테스트 코드를 봤을 때 뭘 검증해야 되는지 어떤 게 맞는 상황이고 어떤 게 틀린 상황인지 알수없는 코드

```
void add(){
	CafeKiosk cafeKiosk=new CafeKiosk();
	cafeKiosk.add(new Americano());
	System.out.println(">>> 담긴 음료 수 : "+cafeKiosk.getBeverages().size());
	System.out.println(">>> 담긴 음료 : "+cafeKiosk.getBeverages().get(0).getName());
}
```

#### 단위 테스트

- 여러 테스트 기법 중 작은 코드 단위 (클래스 or 메소드) 를 독립적으로 검증하는 테스트
- 외부에 의존하지 않아 검증 속도가 빠르고, 안정적이다.
- 단위 테스트부터 일단 꼼꼼하게 잘 작성을 하는 것이 굉장히 중요한 일

#### 자동화된 테스트 (JUnit 5) https://junit.org/junit5

단위 테스트를 위한 테스트 프레임워크

#### AssertJ  https://joel-costigliola.github.io/assertj/index.html

테스트 코드 작성을 원활하게 돕는 테스트 라이브러리
풍부한 API, 메서드 체이닝 지원 (연이은 검증, 쩜 찍어서) -> 깔끔한 코드 작성

---

### 테스트케이스 세분화

#### 질문하기: 암묵적이거나 아직 드러나지 않은 요구사항이 있는지 항상 고민하기

* 해피 케이스
* 예외 케이스

경계값이 존재하는경우, **경계값 테스트**가 고려되어야한다. -> 범위(이상, 이하 , 초과, 미만), 구간, 날짜 등

#### 테스트하기 어려운 영역

- 관측할 때마다 다른 값에 의존하는 코드
    - 현재 날짜/시간, 랜덤 값, 전역 변수/함수, 사용자 입력 등
- 외부 세계에 영향을 주는 코드
    - 표준 출력, 메시지 발송, 데이터베이스에 기록하기 등
- 이럴 땐 테스트 어려운 영역을 외부로 분리하는 것을 고려

#### 테스트하기 어려운 영역을 구분하고 분리하기

- EX) 요구사항 : 가게 운영 시간(10:00~22:00) 외에는 주문을 생성할 수 없다.
- 테스트 시 LocalDateTime.now 를 메소드 안에서 실행하면, 결국 테스트 코드가 시간대에 따라 성공/실패가 좌우됨 (untestable)
- -> 테스트 코드는 테스트하고자 하는 영역을 넣어주고, 실제 production code 에는 LocalDateTime.now 를 넣어줌.

---

### TDD : Test Driven Development

- 프로덕션 코드보다 테스트 코드를 먼저 작성하여 테스트가 구현 과정을 주도하도록 하는 방법론
- 참고 : red green refactoring 사진

#### TDD 핵심 가치 : 피드백

- 내가 작성하는 구현 코드, 프로덕션 코드에 대해서 자주 그리고 빠르게 피드백을 받을 수 있다는 것
- 클라이언트 관점에서의 피드백을 주는 도구

#### (기존 흐름) 선 기능 구현 후 테스트 작성 방식

- 테스트 자체의 누락 가능성
- 특정 테스트 케이스만 검증할 가능성
- 잘못된 구현을 다소 늦게 발견할 가능성

#### 선 테스트 작성, 후 기능 구현

- 복잡도가 낮으며, (유연하며 유지보수가 쉬운) 테스트 가능한 코드로 구현할 수 있게 한다.
- 쉽게 발견하기 어려운 엣지(Edge) 케이스를 놓치지 않게 해준다.
- 구현에 대한 빠른 피드백을 받을 수 있다.
- 과감한 리팩토링이 가능해진다.

#### 테스트는 문서

- 프로덕션 기능을 설명하는 테스트 코드 문서
- 다양한 테스트 케이스를 통해 프로덕션 코드를 이해하는 시각과 관점을 보완
- 어느 한 사람이 과거에 경험했던 고민의 결과물을 팀 차원으로 승격시켜서, 모두의 자산으로 공유할 수 있다.

---

### `@DisplayName` : 섬세하게 테스트 정보 입력하기

- 동료들이 봤을 때 더 직관적으로 이해 가능하게 작성하기
- JUnit5부터 지원 / 메소드 명을 한글로 짓는 방법도 가능하지만, 띄어쓰기마다 언더바를 붙여야하는 번거로움

#### 섬세한 `@DisplayName` 작성

- 음료 1개 추가 테스트 → 음료를 1개 추가할 수 있다.
- 명사의 나열보다 문장으로 (ex. A이면 B이다. / A이면 B가 아니고 C다.)
- 어떤 상태가 주어졌을 때 내가 어떤 행위를 가했고 그 다음에 어떤 상태 변화가 있었다라는 결과까지 명시 가능
- “~테스트” 지양하기


- 음료를 1개 추가할 수 있다 → 음료를 1개 추가하면 주문 목록에 담긴다.
- 테스트 행위에 대한 결과까지 기술하기


- 특정 시간 이전에 주문을 생성하면 실패한다. → 영업 시작 시간 이전에는 주문을 생성할 수 없다.
- 도메인 용어를 사용하여 (메서드 자체의 관점보단 도메인 정책 관점으로) 한층 추상화된 내용을 담기
- 테스트의 현상을 중점으로 기술하지 말 것 (ex 성공한다 / 실패한다 : 내용과 무관)

---

### BDD, Behavior Driven Development

- TDD에서 파생된 개발 방법
- 함수 단위의 테스트에 집중하기보다, 시나리오에 기반한 테스트케이스 (TC) 자체에 집중하여 테스트한다.
- 개발자가 아닌 사람이 봐도 이해할 수 있을 정도의 추상화 수준(레벨)을 권장

#### Given / When / Then

- Given: 시나리오 진행에 필요한 모든 준비 과정 (객체, 값, 상태 등)
- When: 시나리오 행동 진행
- Then: 시나리오 진행에 대한 결과 명시, 검증
- 어떤 환경에서(Given), 어떤 행동을 진행했을 때(When), 어떤 상태 변화가 일어난다(Then)  
  -> DisplayName에 더 명확히 작성 가능

---

### 통합 테스트

- 여러 모듈이 협력하는 기능을 통합적으로 검증하는 테스트 (ex. 레이어드 아키텍처 검증)
- 단위 테스트만으로는 커버하기가 어려운 영역들이 생기기 시작 ( 동작 순서 차이가 있거나 예기치 못한 결과가 나올 수가 있음 )
- 그래서 보통은 풍부한 단위 테스트와 그 다음에 큰 기능 단위 / 시나리오 단위를 검증하는 통합 테스트 이렇게 두 가지 관점으로 접근하면 좋다.

---

요구사항

* 키오스크 주문을 위한 상품 후보 리스트 조회하기
* 상품의 판매 상태: 판매중, 판매보류, 판매중지
* 판매중, 판매보류인 상태의 상품을 화면에 보여준다.
* id, 상품 번호, 상품 타입, 판매 상태, 상품 이름, 가격

### `JpaRepository` 같은 걸 사용하는 경우, Query Method 를 사용하여 이름을 잘 지으면 쿼리가 잘 날아갈 것이 명확한데 왜 테스트쿼리를 작성할까요?

* 간단한 쿼리들은 예측이 되게 쉽게 되지만, 예를 들어 where절의 조건이 엄청 많아서 쿼리 메서드가 엄청 길어진다거나, 파라미터를 잘못 줄 수 있고,  
  아니면 구현하는 기술 자체가 `JPA`가 아니라 `mybatis / jpql / querydsl` 등으로 구현 방법 자체가 변경될 때가 있으니 그것들을 보장하는 차원에서 작성한다.  
  아니면 JPA 로 작성한 코드 그 자체가 미래에 어떻게 변환이 될지도 모르기 때문에 작성해두는 것이 좋다.
* 사실상 `Repository Test`는 단위 테스트 성격에 가까운 테스트이다.
  통합 테스트라 하여 스프링 서버를 띄워서 `Repository`에 대한 테스트를 진행을 하지만, 레이어별로 끊어서 봤을 때는 어떤 DB에 access하는 계층이 퍼시스턴스 레이어의 역할인데,  
  데이터베이스에 액세스하는 그 로직만 갖고 있기 때문에 이 기능 단위로 보자면 약간 단위 테스트 성격을 갖고 있긴 한 것이다.

### `@DataJpaTest` 소개

* 스프링 서버를 띄워서 테스트를 하지만, `@DataJpaTest`는 스프링 부트 테스트보다 가볍다. jpa 관련된 빈들만 주입을 해줘서 서버를 띄워주기 때문이다.
  그래서 속도가 좀 더 빠르게 서버를 띄울 수 있는 장점이 있는데 결론적으로는 데이터 jpa 테스트보다는 스프링 부트 테스트가 좀 더 선호된다. 다음섹션 정리

#### +인프런 강의 노트에 assertThat 검증 체이닝 + profile 지정해주는것 추가해줌 (안해주니까 얘가 local로 인식해서 data.sql 이 동작했었던 작은 이슈)

#### 개인 참고 link

[@Autowired vs @MockBean](https://upcurvewave.tistory.com/600)  
[생성자 주입 선택 시 테스트 코드 작성의 장점?](https://www.inflearn.com/questions/515588/%EC%83%9D%EC%84%B1%EC%9E%90-%EC%A3%BC%EC%9E%85-%EC%84%A0%ED%83%9D-%EC%8B%9C-%ED%85%8C%EC%8A%A4%ED%8A%B8-%EC%BD%94%EB%93%9C-%EC%9E%91%EC%84%B1%EC%9D%98-%EC%9E%A5%EC%A0%90)  
[생성자 주입 이슈](https://minkukjo.github.io/framework/2020/06/28/JUnit-23/)

---

요구사항

* 상품 번호 리스트를 받아 주문 생성하기
* 주문은 주문 상태, 주문 등록 시간을 가진다.
* 주문의 총 금액을 계산할 수 있어야 한다.
* 주문 생성 시 재고 확인 및 개수 차감 후 생성하기
* 재고는 상품번호를 가진다. 재고와 관련 있는 상품 타입은 병 음료, 베이커리이다.

### Persistence Layer

* Data Access의 역할
* 비즈니스 가공 로직이 포함되어서는 안 된다.
* Data에 대한 CRUD에만 집중한 레이어

### Business Layer

비즈니스 로직을 구현하는 역할

* Persistence Layer와의 상호작용(Data를 읽고 쓰는 행위)을 통해 비즈니스 로직을 전개시킨다.
* 트랜잭션을 보장해야 한다.
* Persistene Layer는 테스트 시 스프링 서버 올려서 하지만 단위테스트 느낌이 나고,  
  Busines Lawyer 는 통합 테스트 시 Persistence Layer를 거칠 수 밖에 없기 때문에 통합적인 느낌이 나는 것이다.

> **@Transactional 위험성**  
> Service 단에 붙이지 않고 test단에서만 `@Transactional` 를 붙이면, 실제 프로덕션 코드에 적용이 돼야 할 `@Transactional` 이 잘 동작한다고 착각해서 문제를 뒤늦게 발견할 수도 있다,   
> 테스트단에서의 @Transactional 을 잘 사용을 해야하고,  
> 실수를 방지하려면 테스트단에서 @Transactional 대신에 아래 코드로 delete 시켜주는 방법도 있다.

```
@AfterEach
void tearDown(){
	//productRepository.deleteAll()
	orderProductRepository.deleteAllInBatch();
	productRepository.deleteAllInBatch();
	orderRepository.deleteAllInBatch();
	stockRepository.deleteAllInBatch();
	}
```

결론 : 쓰지 말자는 것이 아니라, 팀원 모두 부작용을 인지한 후에 잘 사용하는 것이 중요하다.

---

### Presentation Layer

* 외부 세계의 요청을 가장 먼저 받는 계층
* 파라미터에 대한 최소한의 검증을 수행한다.

그래서 이 값들이 정말 우리가 비즈니스 로직을 전개시키기 전에 어떤 유효한가에 대한 검증을
하는 게 최우선, 그런 것들을 중점으로 테스트를 한번 작성을 해보면 좋음.

저희가 퍼시센트 레이어랑 비즈니스 레이어 같은 경우는 스프링을 통으로 띄워서 우리가 통합테스트를 진행을 했었죠
그래서 이제 특히 비즈니스 레이어 같은 경우는 데이터 엑섹스 하는 퍼시센트 레이어를 뭔가
Mocking을 하거나 이러지 않고 이제 통으로 같이 테스트를 했었는데 저는 이제 프레젠테이션
레이어를 테스트할 때는 사실 이제 하위에 있는 저 두 레이어를 Mocking 처리를 할 거예요
정상 동작하는 걸 가정하고 이제 프레젠테이션 레이어, 내가 테스트하고자 하는 레이어에만 집중해서 테스트하겠다.

#### MockMvc

* Mock(가짜) 객체를 사용해 스프링 MVC 동작을 재현할 수 있는 테스트 프레임워크
  우리가 테스트하는 대상에 Spring Framework를 사용하면서 어떤 하나의 객체 혹은하나의 레이어를 테스트할 때 의존관계를 갖고 있는 것들이 있는데,
  준비해야할 것이 너무 많아서, 가짜로 잘 동작하는 걸 가정하고 처리하고 싶을때 사용하는 테스트 프레임워크

#### 요구사항

* 관리자 페이지에서 신규 상품을 등록할 수 있다.
* 상품명, 상품 타입, 판매 상태, 가격 등을 입력받는다.

### Presentation Layer : Controller Test 작성하기

#### `@WebMvcTest`

* 전체 Bean Context 를 올려서 사용하는 `@SpringbootTest` 대신, 컨트롤러만 떼서 컨트롤러와 관련된 빈들만 올려서 테스트할 수 있는 가벼운 테스트 annotation
* 사용법

```java

@WebMvcTest(controllers = ProductController.class)
class ProductControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ProductService productService;
}
```

테스트하고자 하는 컨트롤러를 명시하면 된다.

#### `@MockBean` , `@Mock`

* `spring-boot-starter-test` 를 쓰면 자동으로 **mockito** 가 포함되어있음.  (mockito : 모킹 프레임워크)
* `@MockBean` 목 객체를 만들어 대신 빈으로 넣어주는 역할
    * 위 예로 보면 ProductController 라는 빈을 생성을 하면, ProductService가 없다고 나올 것이므로 @MockBean 처리를 해야 예외가 터지지 않을 것이다.

#### mockMvc.perform()

* api를 날리는 동작 수행
* 괄호 내부에 수행에 필요한 정보들을 넣을 수 있다.
* ex code

```
  mockMvc.perform(
          post("/api/v1/products/new")
              .content(objectMapper.writeValueAsString(request))
              .contentType(MediaType.APPLICATION_JSON)
      )
```

> post같은 경우는 http body의 값을 넣다 보니까 직렬화랑 역직렬화의 과정을 거치게 됨  
> 따라서 우리가 만든 object 를 json 형태로 직렬화하는 과정을 거쳐서 byte 배열 형태나 아니면 string 형태로 content 에 넣어주면 되는데,  
> 이 직렬화를 하기 위해서 ObjectMapper를 @Atuowired 로 테스트에 포함시킨다.  
> ObjectMapper : 직렬화와 역직렬화, Json과 Object 간의 직렬화,역직렬화를 도와준다.  
> contentType : json 타입을 헤더에 명시를 해준다.

* 검증은 `.andExpect(MockMvcResultMatchers.status().isOk());` 이렇게 체이닝식으로 걸어줘서 확인함
* `.andDo(MockMvcResultHandlers.print())` 를 써서 자세한 로그를 찍어볼 수 있다.

* 참고
* 오류 1 : `Error creating bean with name 'jpaMappingContext': JPA metamodel must not be empty ` 오류 : @SpringBootApplication위에 달아놓은 @EnableJpaAuditing 관련 문제.  
  config로 따로 관리해주면 해결
* 오류 2 : `MockHttpServletResponse:    Status = 400 Error message = Invalid request content.` 인 경우, controller 에 @Requestbody 달았는지 확인
* 오류 3 : `java.lang.IllegalStateException: Cannot resolve parameter names for constructor` 오류 : 스프링 버전이 올라가면서 체킹해야될 문제. build.gradle 에 정리해놓음.
* 오류 4 : `H2 Console Sorry, remote connections ('webAllowOthers') are disabled on this server.` 오류 : 아래 yml 설정하기

```
h2:
  console:
    enabled: true
  settings:
    web-allow-others: true
```

### Presentation Layer 테스트 (2)

컨트롤러의 역할 중에 하나는 파라미터가 잘 들어왔는지 기본적인 유효성 검사, validation을 잘 하는 것

---
### Mockito로 Stubbing하기
---

### Test Double

* Dummy
    * 아무 것도 하지 않는 깡통 객체
* Fake
    * 단순한 형태로 동일한 기능은 수행하나, 프로덕션에서 쓰기에는 부족한 객체 (ex. FakeRepository)
* Stub
    * 테스트에서 요청한 것에 대해 미리 준비한 결과를 제공하는 객체 그 외에는 응답하지 않는다.
* Spy
    * Stub이면서 호출된 내용을 기록하여 보여줄 수 있는 객체 일부는 실제 객체처럼 동작시키고 일부만 Stubbing할 수 있다.
* Mock
    * 행위에 대한 기대를 명세하고, 그에 따라 동작하도록 만들어진 객체

#### Stub vs Mock

Stub : 상태 검증 (State Verification)
Mock : 행위 검증 (Behavior Verification)

---

### 순수 Mockito로 검증해보기 (@Mock, @Spy, @InjectMocks)

#### `@MockBean`

스프링 컨텍스트에 등록된 Bean을 Mock 객체로 치환해주는 역할 = 즉, 스프링 서버가 떠야 사용 가능한 어노테이션

#### `@Mock + @InjectMocks` 조합

스프링 컨텍스트가 필요하지 않은 곳에서 사용 (주로 단위 테스트)