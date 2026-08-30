> ⚠️ **이관 완료**: 이 문서의 정리 내용은 [GitHub Pages 사이트](https://leesg619.github.io/my-study/spring-core-basic/)로 이전되었습니다.
> 앞으로 내용을 추가/수정할 때는 이 README가 아니라 사이트 쪽(`/spring-core-basic/section-*.html`)을 편집해주세요.
> 이 파일은 이관 시점의 원문 보존용으로만 남겨둡니다.

## 영한님 스프링 핵심 원리 - 기본편

> 스프링 입문자가 예제를 만들어가면서 스프링의 핵심 원리를 이해하고,
> 스프링 기본기를 확실히 다질 수 있습니다. 강의가 끝나면 여러분은 스프링의 기본 기능을 잘 사용하는 것은 물론이고,
> 스프링의 본질에 대해서 깊이있는 이해를 하게 됩니다. 단순히 스프링의 기능만 사용하는 개발자에서 객체 지향 애플리케이션의
> 설계와 아키텍처 레벨까지 고민하는 개발자로 성장할 수 있습니다.

---

### 스프링 개요

#### Spring Framework

* **핵심 기술** (스프링 DI 컨테이너, AOP, 이벤트, 기타) + **웹기술** (스프링 MVC, 스프링 WebFlux)
* **데이터 접근 기술** (트랜잭션, JDBC, ORM 지원, XML 지원) + **기술 통합** (캐시, 이메일, 원격접근, 스케줄링)
* 최근에는 스프링 부트를 통해서 스프링 프레임워크의 기술들을 편리하게 사용한다.

#### Spring Boot

* 스프링을 편리하게 사용할 수 있도록 지원한다.
* **단독으로 실행**할 수 있는 스프링 애플리케이션을 쉽게 생성 (Tomcat 같은 웹 서버를 내장해서 별도의 웹 서버를 설치하지 않아도 됨)
* **손쉬운 빌드 구성을 위한 starter 종속성 제공** : 라이브러리를 묶어서 당겨올때, 한번에 종속된 라이브러리 다 땡겨온다.
* 해당 스프링 버전에 따라 3rd party(외부) 라이브러리를 **버전에 맞게** 자동 구성
* **메트릭, 상태 확인, 외부 구성 같은 프로덕션 준비 기능 제공** : 운영환경에서 모니터링이 중요 - 기본적으로 제공함.
* **관례에 의한 간결한 설정** : 필요한 곳만 커스텀 할 수 있도록 편리함을 지원

---

### 다형성 Polymorphism (객체지향의 꽃)

* 인터페이스를 구현한 객체 인스턴스를 실행 시점에 유연하게 변경할 수 있다.
* 클라이언트를 변경하지 않고, 서버의 구현 기능을 유연하게 변경할 수 있다.  
  (+확장 가능한 설계 가능)
* Java - 역할과 구현을 명확히 분리 (역할 = 인터페이스 / 구현 = 인터페이스를 구현한 클래스, 구현 객체)

#### 스프링과 다형성의 관계

* 스프링은 다형성이 가장 중요하며, 다형성을 극대화해서 이용할 수 있게 도와준다.
* 스프링에서 이야기하는 **제어의 역전(IoC)**, **의존관계 주입(DI)** 은 다형성을 활용해서 역할과 구현을 편리하게 다룰 수 있도록 지원한다.

---

### 좋은 객체지향프로그래밍이란? (feat. 5가지 원칙(SOLID))

* SRP: 단일 책임 원칙(Single Responsibility Principle)
* OCP: 개방-폐쇄 원칙 (Open/Closed Principle)
* LSP: 리스코프 치환 원칙 (Liskov Substitution Principle)
* ISP: 인터페이스 분리 원칙 (Interface Segregation Principle)
* DIP: 의존관계 역전 원칙 (Dependency Inversion Principle)

#### SRP 단일 책임 원칙

* 한 클래스는 **하나의 책임**만 가져야 한다. (클 수 있고, 작을 수 있으며, 문맥과 상황에 따라 다르다.)
* 따라서 중요한 판단기준은 변경이다. 변경이 있을 때 파급 효과가 적으면 단일 책임 원칙을 잘 따른 것이다.
* ex) UI 변경, 객체의 생성과 사용을 분리  
  (우리가 **계층**을 잘 나눠 설계 및 구현하는 이유는 다 단일 책임 원칙을 지키려고 하는 것이다.)

#### OCP 개방-폐쇄 원칙 (중요)

* 소프트웨어 요소는 확장에는 열려 있으나 변경에는 닫혀 있어야 한다.
* 다형성을 활용  
  -> 인터페이스를 구현한 새로운 클래스를 하나 만들어서 새로운 기능을 구현하면 -> 클라이언트의 변경없이 확장
* OCP 원칙을 지킬 수 없는 상황의 예

```
// MemberService 클라이언트가 구현 클래스를 직접 선택하는 상황
// 구현 객체를 변경하려면 클라이언트 코드를 변경해야 함. (분명 다형성을 사용했지만 OCP 원칙을 지킬 수 없는 상황)
MemberRepository m = new MemoryMemberRepository();  // 기존 코드에서
MemberRepository m = new JdbcMemberRepository();    // 변경할 코드로 변경
```

#### LSP 리스코프 치환 원칙

* 프로그램의 객체는 프로그램의 정확성을 깨뜨리지 않으면서 하위 타입의 인스턴스로 바꿀 수 있어야 한다.
* 다형성에서 하위 클래스는 인터페이스 규약을 다 지켜야 한다는 것, 다형성을 지원하기 위한 원칙,  
  인터페이스를 구현한 구현체는 믿고 사용하려면, 이 원칙이 필요하다.
* 단순히 컴파일에 성공하는 것이 중요한게 아님.
* 예) 자동차 인터페이스의 엑셀은 앞으로 가라는 기능, 뒤로 가게 구현하면 LSP 위반임, 느리더라도 앞으로 가야함.

#### ISP 인터페이스 분리 원칙

* 특정 클라이언트를 위한 인터페이스 여러 개가 범용 인터페이스 하나보다 낫다.
* 인터페이스가 명확해지고, 대체 가능성이 높아진다.

#### DIP 의존관계 역전 원칙 (중요)

* 추상화에 의존해야지, 구체화에 의존하면 안된다. (구현 클래스에 의존하지 말고, 인터페이스에 의존해야 한다.)  
  구현체에 의존하게 되면 변경이 아주 어려워진다.
* **의존성 주입(Dependency Injection)** 은 이 원칙을 따르는 방법 중 하나이다.
* DIP 원칙을 지킬 수 없는 상황의 예

```
// MemberService 클라이언트가 MemberRepository 구현 클래스를 직접 선택
// 의존한다 = 알고만 있어도 의존 하는것이다. 
MemberRepository m = new MemoryMemberRepository();  // 인터페이스만 (MemberRepository) 알아야 하는데 구현체도 알고있음.
```

#### 정리

* 객체 지향의 핵심은 다형성
* 다형성 만으로는 구현 객체를 변경할 때 클라이언트 코드도 함께 변경된다 (OCP, DIP를 지킬 수 없다.)

---

### 객체 지향 설계와 스프링

스프링 이야기에 왜 객체 지향 이야기가 나오는가?

* 스프링은 다음 기술로 다형성 + OCP, DIP를 가능하게 지원한다.
    * **DI(Dependency Injection)** : 의존관계, 의존성 주입
    * **DI 컨테이너 제공** : 객체를 생성하고, 연관관계를 맺어주는 별도의 조립, 설정자의 역할을 한다.
* 클라이언트 코드의 변경 없이 기능 확장가능하여 쉽게 부품을 교체하듯이 개발할 수 있다.

#### 실무 고민

* 하지만 인터페이스를 도입하면 추상화라는 비용이 발생한다.
* 기능을 확장할 가능성이 없다면, 구체 클래스를 직접 사용하고, 향후 꼭 필요할 때 리팩터링해서 인터페이스를 도입하는 것도 방법이다.  
  (장점이 단점을 뛰어넘도록 알맞게 설계하는 것이 좋은 엔지니어)

---

#### section2, section3 예제 : 좋은 객체 지향 설계 흐름 정리

* 새로운 할인 정책 개발
    * 다형성 덕분에 새로운 정률 할인 정책 코드를 추가로 개발하는 것 자체는 아무 문제가 없음
* 새로운 할인 정책 적용과 문제점
    * 새로 개발한 정률 할인 정책을 적용하려고 하니 클라이언트 코드인 주문 서비스 구현체도 함께 변경해야함 -> OCP 위반
    * 주문 서비스 클라이언트가 인터페이스인 `DiscountPolicy` 뿐만 아니라, 구체 클래스인 `FixDiscountPolicy` 도 함께 의존 -> DIP 위반
* 관심사의 분리
    * 기존에는 클라이언트가 의존하는 서버 구현 객체를 직접 생성하고, 실행함
    * 공연으로 비유를 하면 기존에는 남자 주인공 배우가 공연도 하고, 동시에 여자 주인공도 직접 초빙하는 다양한 책임을 가지고 있음
    * 공연 기획자인 AppConfig가 등장
    * `AppConfig`의 등장으로 애플리케이션이 크게 사용 영역과, 객체를 생성하고 구성(`Configuration`)하는 영역으로 분리
    * 이제부터 클라이언트 객체는 자신의 역할을 실행하는 것만 집중, 권한이 줄어듬(책임이 명확해짐)
    * 할인 정책을 변경해도 `AppConfig`가 있는 구성 영역만 변경하면 됨, 사용 영역은 변경할 필요가 없음.  
      물론 클라이언트 코드인 주문 서비스 코드도 변경하지 않아도 됨.


* 여기서 3가지 SRP, DIP, OCP 를 적용 (좋은 객체 지향 설계)
    * SRP 단일 책임 원칙
        * 구현 객체를 생성하고 연결하는 책임은 AppConfig가 담당
        * 클라이언트 객체는 실행하는 책임만 담당
    * DIP 의존관계 역전 원칙
        * 새로운 할인 정책을 개발하고, 적용하려고 하니 클라이언트 코드도 함께 변경해야 했다.  
          왜냐하면 기존 클라이언트 코드`OrderServiceImpl`는 DIP를 지키며 `DiscountPolicy` 추상화 인터페이스에 의존하는 것 같았지만,  
          `FixDiscountPolicy` 구체화 구현 클래스에도 함께 의존했다.
      ```java
      public class OrderServiceImpl implements OrderService {
        // OCP / DIP 위반 중
        private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
      }
      ```
        * 클라이언트 코드가 DiscountPolicy 추상화 인터페이스에만 의존하도록 코드를 변경 후,  
          `AppConfig`가 `FixDiscountPolicy` 객체 인스턴스를 클라이언트 코드 대신 생성해서 클라이언트 코드에 의존관계를 주입했다.  
          이렇게해서 DIP 원칙을 따르면서 문제도 해결했다.
          ```java
          public class OrderServiceImpl implements OrderService {
              private final DiscountPolicy discountPolicy; // 그리고 AppConfig 에서 구현체 주입하는 방식으로 해결
          }
          ```
    * OCP 개방-폐쇄 원칙
        * 애플리케이션을 사용 영역과 구성 영역으로 나눔
        * `AppConfig`가 의존관계를 `FixDiscountPolicy` -> `RateDiscountPolicy` 로 변경해서 클라이언트 코드에 주입하므로 클라이언트 코드는 변경하지 않아도 됨
        * **소프트웨어 요소를 새롭게 확장해도 사용 영역의 변경은 닫혀 있다**
* 참고 : [Practice Commit Link](https://github.com/leesg619/my-study/tree/c6a1272cdc826e028e95e7ba1ba70718aad9e347)

---

### 제어의 역전 IoC(Inversion of Control)

* 기존 프로그램은 클라이언트 구현 객체가 스스로 필요한 서버 구현 객체를 생성하고, 연결하고, 실행했다.  
  한마디로 구현 객체가 프로그램의 제어 흐름을 스스로 조종했다. 개발자 입장에서는 자연스러운 흐름이다.
* 반면에 `AppConfig`가 등장한 이후에 구현 객체는 자신의 로직을 실행하는 역할만 담당한다.  
  프로그램의 제어 흐름은 이제 `AppConfig`가 가져간다.  
  예를 들어서 OrderServiceImpl 은 필요한 인터페이스들을 호출하지만 어떤 구현 객체들이 실행될지 모른다.
* 프로그램에 대한 제어 흐름에 대한 권한은 모두 `AppConfig`가 가지고 있다.  
  심지어 `OrderServiceImpl` 도 `AppConfig`가 생성한다.  
  그리고 `AppConfig`는 `OrderServiceImpl` 이 아닌 `OrderService` 인터페이스의 다른 구현 객체를 생성하고 실행할 수도 있다.  
  그런 사실도 모르고 `OrderServiceImpl` 은 묵묵히 자신의 로직을 실행할 뿐이다.
* 이렇듯 **프로그램의 제어 흐름을 직접 제어하는 것이 아니라 외부에서 관리하는 것**을 **제어의 역전(IoC)** 이라 한다.
* 프레임워크 vs 라이브러리
    * 프레임워크가 내가 작성한 코드를 제어하고, 대신 실행하면 그것은 프레임워크가 맞다. (JUnit)
    * 반면에 내가 작성한 코드가 직접 제어의 흐름을 담당한다면 그것은 프레임워크가 아니라 라이브러리다.

### 의존관계 주입 DI(Dependency Injection)

* `OrderServiceImpl` 은 `DiscountPolicy` 인터페이스에 의존한다. 실제 어떤 구현 객체가 사용될지는 모른다.
* 의존관계는 정적인 클래스 의존 관계와, 실행 시점에 결정되는 동적인 객체(인스턴스) 의존 관계 둘을 분리해서 생각해야 한다.
    * 정적인 클래스 의존관계
        * 클래스가 사용하는 import 코드만 보고 의존관계를 쉽게 판단할 수 있고, 애플리케이션을 실행하지 않아도 분석할 수 있다.  
          (실제 어떤 객체가 주입될 지 알 수 없음)
    * 동적인 객체 인스턴스 의존 관계
        * 애플리케이션 실행 시점에 실제 생성된 객체 인스턴스의 참조가 연결된 의존 관계 다.


* **애플리케이션 실행 시점(런타임)에 외부에서 실제 구현 객체를 생성하고 클라이언트에 전달해서 클라이언트와 서버의 실제 의존관계가 연결 되는 것**을 **의존관계 주입**이라 한다.
* 객체 인스턴스를 생성하고, 그 참조값을 전달해서 연결된다.
* **DI**를 사용하면 클라이언트 코드를 변경하지 않고, 클라이언트가 호출하는 대상의 타입 인스턴스를 변경할 수 있다.
* **DI를 사용하면 정적인 클래스 의존관계를 변경하지 않고, 동적인 객체 인스턴스 의존관계를 쉽게 변경할 수 있다.**

#### IoC 컨테이너, DI 컨테이너

* AppConfig 처럼 객체를 생성하고 관리하면서 의존관계를 연결해 주는 것을 IoC 컨테이너 또는 DI 컨테이너라 한다.
* 의존관계 주입에 초점을 맞추어 최근에는 주로 DI 컨테이너라 한다. (또는 어샘블러, 오브젝트 팩토리 등으로 불리기도 한다)

---

### 스프링 컨테이너

* ApplicationContext 를 스프링 컨테이너라 한다.

```java
public class OrderApp {
	public static void main(String[] args) {
		ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
		MemberService memberService = applicationContext.getBean("memberService", MemberService.class);
		OrderService orderService = applicationContext.getBean("orderService", OrderService.class);
	}
}
```

* 스프링 컨테이너는 @Configuration 이 붙은 AppConfig 를 설정(구성) 정보로 사용한다. 여기서 @Bean 이
  라 적힌 메서드를 모두 호출해서 반환된 객체를 스프링 컨테이너에 등록한다. 이렇게 스프링 컨테이너에 등록된
  객체를 스프링 빈이라 한다.
* 스프링 빈은 @Bean 이 붙은 메서드의 명을 스프링 빈의 이름으로 사용한다.

---

### 스프링 컨테이너 생성

`ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);`

* 스프링 컨테이너는 XML을 기반으로 만들 수 있고, 애노테이션 기반의 자바 설정 클래스로 만들 수 있다.
* 직전에 `AppConfig` 를 사용했던 방식이 애노테이션 기반의 자바 설정 클래스로 스프링 컨테이너를 만든 것이다.
* 참고: 더 정확히는 스프링 컨테이너를 부를 때 `BeanFactory` , `ApplicationContext` 로 구분해서 이야기하는데,  
  `BeanFactory` 를 직접 사용하는 경우는 거의 없으므로 일반적으로 `ApplicationContext` 를 스프링 컨테이너라 한다.

주의: 빈 이름은 항상 다른 이름을 부여해야 한다. 같은 이름을 부여하면, 다른 빈이 무시되거나, 기존 빈을 덮어버
리거나 설정에 따라 오류가 발생한다. (최근 스프링 버전에서는 같은 이름의 빈이 충돌나면 경고를 날리면서 기본적으로 튕김.)
실무에서는 항상 다른 이름으로 명확히 부여 하는게 제일 중요

---

### 스프링 빈 조회

_스프링 컨테이너에서 빈을 직접 조회하는 일은 거의 없다. 대부분 생성자, @Autowired 등으로 의존관계 주입을 받음_

#### 컨테이너에 등록된 모든 빈 조회 : [Junit5 TEST Code](https://github.com/leesg619/my-study/blob/main/spring-study/spring-core-basic/practice/core/src/test/java/com/practice/core/beanfind/ApplicationContextInfoTest.java)

* 모든 빈 출력하기
    * 실행하면 스프링에 등록된 모든 빈 정보를 출력할 수 있다.
    * `ac.getBeanDefinitionNames()` : 스프링에 등록된 모든 빈 이름을 조회한다.
    * `ac.getBean(String beanDefinitionName)` : 빈 이름으로 빈 객체(인스턴스)를 조회한다.

* 애플리케이션 빈 출력하기
    * 스프링이 내부에서 사용하는 빈은 제외하고 출력하려면? 스프링이 내부에서 사용하는 빈은 `getRole()` 로 구분할 수 있다.
        * `ROLE_APPLICATION` : 일반적으로 사용자가 정의한 빈
        * `ROLE_INFRASTRUCTURE` : 스프링이 내부에서 사용하는 빈

#### 스프링 빈 조회 - 기본 : [Junit5 TEST Code](https://github.com/leesg619/my-study/blob/main/spring-study/spring-core-basic/practice/core/src/test/java/com/practice/core/beanfind/ApplicationContextBasicFindTest.java)

* 스프링 컨테이너에서 스프링 빈을 찾는 가장 기본적인 조회
    * `ac.getBean(빈이름, 타입)`
    * `ac.getBean(타입)`
* 조회 대상 스프링 빈이 없으면 예외 발생
    * `NoSuchBeanDefinitionException: No bean named 'xxxxx' available`
    * 참고: 구체 타입으로 조회하면 변경시 유연성이 떨어진다.

#### 스프링 빈 조회 - 동일한 타입이 둘 이상 : [Junit5 TEST Code](https://github.com/leesg619/my-study/blob/main/spring-study/spring-core-basic/practice/core/src/test/java/com/practice/core/beanfind/ApplicationContextSameBeanFindTest.java)

* 동일한 타입이 둘 이상이면, 타입으로 조회시 같은 타입의 스프링 빈이 둘 이상이면 오류가 발생한다.  
  `NoUniqueBeanDefinitionException` 발생
* 이때는 빈 이름을 지정하면 되며, `ac.getBeansOfType()` 을 사용하면 해당 타입의 모든 빈을 조회할 수 있다.

#### 스프링 빈 조회 - 상속 관계 : [Junit5 TEST Code](https://github.com/leesg619/my-study/blob/main/spring-study/spring-core-basic/practice/core/src/test/java/com/practice/core/beanfind/ApplicationContextExtendsFindTest.java)

* 대원칙 : 부모 타입으로 조회 시, 자식 타입도 함께 조회한다.
* 그래서 모든 자바 객체의 최고 부모인 Object 타입으로 조회하면, 모든 스프링 빈을 조회한다.

---

### BeanFactory와 ApplicationContext

#### BeanFactory

* 스프링 컨테이너의 최상위 인터페이스, 스프링 빈을 관리하고 조회하는 역할을 담당한다.
* `getBean()` 을 제공한다.

#### ApplicationContext

* `BeanFactory` 기능을 모두 상속받아서 제공한다.
* 빈을 관리하고 검색하는 기능을 `BeanFactory` 가 제공해주지만, 그 외에  
  애플리케이션을 개발할 때는 빈을 관리하고 조회하는 기능은 물론이고, 수 많은 부가기능이 필요하다.

#### ApplicatonContext가 제공하는 부가기능

![image](https://github.com/leesg619/my-study/assets/52132929/57e7e286-c575-49e2-8522-2b66060ded55)

* 메시지소스를 활용한 국제화 기능
    * 예를 들어서 한국에서 들어오면 한국어로, 영어권에서 들어오면 영어로 출력
* 환경변수
    * 로컬, 개발, 운영등을 구분해서 처리
* 애플리케이션 이벤트
    * 이벤트를 발행하고 구독하는 모델을 편리하게 지원
* 편리한 리소스 조회
    * 파일, 클래스패스, 외부 등에서 리소스를 편리하게 조회

#### 정리

* `ApplicationContext`는 `BeanFactory`의 기능을 상속받는다.
* `ApplicationContext`는 빈 관리기능 + 편리한 부가 기능을 제공한다.
* `BeanFactory`를 직접 사용할 일은 거의 없다. 부가기능이 포함된 `ApplicationContext`를 사용한다.
* `BeanFactory`나 `ApplicationContext`를 스프링 컨테이너라 한다

---

### 스프링 빈 설정 메타 정보 - BeanDefinition

#### 다양한 설정 형식 지원 - 자바 코드, XML

* 스프링 컨테이너는 다양한 형식의 설정 정보를 받아들일 수 있게 유연하게 설계되어 있다.
    * 자바 코드, XML, Groovy 등등


* 애노테이션 기반 **자바 코드** 설정 사용
    * `new AnnotationConfigApplicationContext(AppConfig.class)`
    * `AnnotationConfigApplicationContext` 클래스를 사용하면서 자바 코드로된 설정 정보를 넘기면 된다.

* XML 설정 사용
    * `GenericXmlApplicationContext` 를 사용하면서 `xml` 설정 파일을 넘기면 된다.
    * 최근에는 스프링 부트를 많이 사용하면서 `XML`기반의 설정은 잘 사용하지 않는다.
    * 아직 많은 레거시 프로젝트 들이 `XML`로 되어 있고, 컴파일 없이 빈 설정 정보를 변경할 수 있는 장점이 있다.

#### BeanDefinition 살펴보기

* 스프링은 어떻게 이런 다양한 설정 형식을 지원하는 것일까? 그 중심에는 `BeanDefinition` 이라는 추상화가 있다.
* 쉽게 이야기해서 역할과 구현을 개념적으로 나눈 것이다.
    * `XML`을 읽어서 `BeanDefinition`을 만들면 된다. 또는 자바 코드를 읽어서 `BeanDefinition`을 만들면 된다.
    * 스프링 컨테이너는 자바 코드인지, `XML`인지 몰라도 된다. 오직 `BeanDefinition`만 알면 된다.
* `BeanDefinition` 을 **빈 설정 메타정보**라 한다.
* `@Bean` , `<bean>` 당 각각 하나씩 메타 정보가 생성된다.
* **스프링 컨테이너는 이 메타정보를 기반으로 스프링 빈을 생성한다.**
  ![image](https://github.com/leesg619/my-study/assets/52132929/96790930-5fd5-40d0-9631-e531075f7220)

#### 코드 레벨의 BeanDefinition

* `AnnotationConfigApplicationContext` 는 `AnnotatedBeanDefinitionReader` 를 사용해서 `AppConfig.class` 를 읽고 `BeanDefinition` 을 생성한다.
* `GenericXmlApplicationContext` 는 `XmlBeanDefinitionReader` 를 사용해서 `appConfig.xml` 설정 정보를 읽고 `BeanDefinition` 을 생성한다.
* 새로운 형식의 설정 정보가 추가되면, `XxxBeanDefinitionReader`를 만들어서 `BeanDefinition` 을 생성하 면 된다.

#### BeanDefinition 정보 (참고)

* `BeanClassName`: 생성할 빈의 클래스 명(자바 설정 처럼 팩토리 역할의 빈을 사용하면 없음)
* `factoryBeanName`: 팩토리 역할의 빈을 사용할 경우 이름, 예) `appConfig`
* `factoryMethodName`: 빈을 생성할 팩토리 메서드 지정, 예) `memberService`
* `Scope`: 싱글톤(기본값)
* `lazyInit`: 스프링 컨테이너를 생성할 때 빈을 생성하는 것이 아니라, 실제 빈을 사용할 때 까지 최대한 생성을 지연 처리 하는지 여부
* `InitMethodName`: 빈을 생성하고, 의존관계를 적용한 뒤에 호출되는 초기화 메서드 명
* `DestroyMethodName`: 빈의 생명주기가 끝나서 제거하기 직전에 호출되는 메서드 명
* `Constructor arguments`, `Properties`: 의존관계 주입에서 사용한다. (자바 설정 처럼 팩토리 역할의 빈을 사용 하면 없음)

#### 정리

* `BeanDefinition`을 직접 생성해서 스프링 컨테이너에 등록할 수 도 있다. 하지만 실무에서 `BeanDefinition`을 **직접 정의하거나 사용할 일은 거의 없다.**
* `BeanDefinition`에 대해서는 너무 깊이있게 이해하기 보다는, 스프링이 다양한 형태의 설정 정보를 `BeanDefinition`으로 추상화해서 사용하는 것 정도만 이해하면 된다.
* 가끔 스프링 코드나 스프링 관련 오픈 소스의 코드를 볼 때, BeanDefinition 이라는 것이 보일 때가 있다. 이때 이러한 메커니즘을 떠올리면 된다.

---

### Singleton Pattern 싱글톤 패턴

#### 웹 애플리케이션과 싱글톤

* 앞서 만들었던 스프링 없는 순수한 DI 컨테이너인 AppConfig는 요청을 할 때 마다 객체를 새로 생성한다.
* 고객 트래픽이 초당 100이 나오면 초당 100개 객체가 생성되고 소멸된다! 메모리 낭비가 심하다.
* 해결방안은 해당 객체가 딱 1개만 생성되고, 공유하도록 설계하면 된다. 싱글톤 패턴

#### 싱글톤 패턴

* 클래스의 인스턴스가 딱 1개만 생성되는 것을 보장한다.
* 싱글톤 패턴을 적용하면 고객의 요청이 올 때 마다 객체를 생성하는 것이 아니라, 이미 만들어진 객체를 공유해서 효율적으로 사용할 수 있다.
* 싱글톤 패턴을 구현하는 방법은 여러가지가 있는데, 순수하게 싱글톤 패턴을 구현하면 문제점이 몇가지 있다.

#### 순수 싱글톤 패턴 문제점

* 싱글톤 패턴을 구현하는 코드 자체가 많이 들어간다.
* 의존관계상 클라이언트가 구체 클래스에 의존한다. (**DIP를 위반한다.**)
* 클라이언트가 구체 클래스에 의존해서 **OCP 원칙을 위반**할 가능성이 높다.
* 테스트하기 어렵고, 내부 속성을 변경하거나 초기화 하기 어렵다. / private 생성자로 자식 클래스를 만들기 어렵다.
* 결론적으로 유연성이 떨어진다. (안티패턴으로 불리기도 한다.)

---

### 싱글톤 컨테이너

스프링 컨테이너는 싱글톤 패턴의 문제점을 해결하면서, 객체 인스턴스를 싱글톤(1개만 생성)으로 관리한다.

#### 특징

* 스프링 컨테이너는 싱글턴 패턴을 적용하지 않아도, 객체 인스턴스를 싱글톤으로 관리한다.
    * 이전에 설명한 컨테이너 생성 과정을 자세히 보자. 컨테이너는 객체를 하나만 생성해서 관리한다.
* 스프링 컨테이너는 싱글톤 컨테이너 역할을 한다. 이렇게 싱글톤 객체를 생성하고 관리하는 기능을 **싱글톤 레지스트리**라 한다.
* 스프링 컨테이너의 이런 기능 덕분에 싱글턴 패턴의 모든 단점을 해결하면서 객체를 싱글톤으로 유지할 수 있다.
    * 싱글톤 패턴을 위한 지저분한 코드가 들어가지 않아도 된다.
    * DIP, OCP, 테스트, private 생성자로 부터 자유롭게 싱글톤을 사용할 수 있다.
* 스프링 컨테이너 덕분에 고객의 요청이 올 때 마다 객체를 생성하는 것이 아니라, 이미 만들어진 객체를 공유해서 효율적으로 재사용할 수 있다.
* **참고**: 스프링의 기본 빈 등록 방식은 싱글톤이지만, 싱글톤 방식만 지원하는 것은 아니다. 요청할 때 마다 새로운 객체를 생성해서 반환하는 기능도 제공한다.

#### 싱글톤 방식의 주의점

* 싱글톤 패턴이든, 스프링 같은 싱글톤 컨테이너를 사용하든, 객체 인스턴스를 하나만 생성해서 공유하는 싱글톤
  방식은 여러 클라이언트가 하나의 같은 객체 인스턴스를 공유하기 때문에 싱글톤 객체는 상태를 유지(stateful)하
  게 설계하면 안된다.
* **무상태(stateless)**로 설계해야 한다.
    * 특정 클라이언트에 의존적인 필드가 있으면 안된다.
    * 특정 클라이언트가 값을 변경할 수 있는 필드가 있으면 안된다
    * 가급적 읽기만 가능해야 한다.
    * **필드 대신**에 자바에서 공유되지 않는, **지역변수, 파라미터, ThreadLocal** 등을 사용해야 한다.
* 스프링 빈의 필드에 공유 값을 설정하면 정말 큰 장애가 발생할 수 있다.
* 문제점
  예시 : [StatefulService](https://github.com/leesg619/my-study/commit/cba0364ed034abe72443211c3db75b240659e2c2#diff-642c09647ba515d0b79b7ab19adffa2c381f25aabfeb11fa2b8da6926ddcfccd) , [StatefulServiceTest](https://github.com/leesg619/my-study/commit/cba0364ed034abe72443211c3db75b240659e2c2#diff-05d99b203001913163164a26165baf2146855cb33790d1e566664f50c9a7228c)

---

### `@Configuration`과 싱글톤

* [AppConfig](https://github.com/leesg619/my-study/blob/main/spring-study/spring-core-basic/practice/core/src/main/java/com/practice/core/config/AppConfig.java) 코드를 보면, 스프링 컨테이너가 각각 `@Bean`을 호출해서 스프링
  빈을 생성한다. 그래서 `memberRepository()` 는 다음과 같이 총 3번이 호출되어야 하는 것 아닐까?

1. 스프링 컨테이너가 스프링 빈에 등록하기 위해 `@Bean`이 붙어있는 `memberRepository()` 호출
2. `memberService()` 로직에서 `memberRepository()` 호출
3. `orderService()` 로직에서 `memberRepository()` 호출

그러나 호출 테스트 시 결과는 모두 1번만 호출된다.

### `@Configuration`과 바이트코드 조작의 마법

스프링 컨테이너는 싱글톤 레지스트리다. 따라서 스프링 빈이 싱글톤이 되도록 보장해주어야 한다. 그런데 스프링이 자바 코드까지 어떻게 하기는 어렵다.  
저 자바 코드를 보면 분명 3번 호출되어야 하는 것이 맞다. 그래서 스프링은 클래스의 바이트코드를 조작하는 라이브러리를 사용한다.

```java
@Test
void configurationDeep(){
	ApplicationContext ac=new AnnotationConfigApplicationContext(AppConfig.class);
	//AppConfig도 스프링 빈으로 등록된다.
	AppConfig bean=ac.getBean(AppConfig.class);

	System.out.println("bean = "+bean.getClass());
	//출력: bean = class hello.core.AppConfig$$EnhancerBySpringCGLIB$$bd479d70
	}
```

* 사실 AnnotationConfigApplicationContext 에 파라미터로 넘긴 값은 스프링 빈으로 등록된다. 그래서 AppConfig 도 스프링 빈이 된다.
* AppConfig 스프링 빈을 조회해서 클래스 정보를 출력해보면 아래와 같다.  
  `bean = class hello.core.AppConfig$$EnhancerBySpringCGLIB$$bd479d70`  
  (순수한 클래스라면 다음과 같이 출력되어야 함 `class hello.core.AppConfig` )

이것은 내가 만든 클래스가 아니라 스프링이 `CGLIB`라는 바이트코드 조작 라이브러리를 사용해서  
`AppConfig` 클래스를 상속받은 임의의 다른 클래스를 만들고, 그 다른 클래스를 스프링 빈으로 등록한 것이다.

#### `AppConfig@CGLIB` 예상 코드 동작

* `@Bean`이 붙은 메서드마다 이미 스프링 빈이 존재하면 존재하는 빈을 반환하고, 스프링 빈이 없으면 생성해서 스
  프링 빈으로 등록하고 반환하는 코드가 동적으로 만들어진다. 그 덕분에 싱글톤이 보장되는 것이다.
* 참고 `AppConfig@CGLIB`는 `AppConfig`의 자식 타입이므로, `AppConfig` 타입으로 조회 할 수 있다.

---

#### @Configuration 을 적용하지 않고, @Bean 만 적용하면 어떻게 될까?

* `bean = class hello.core.AppConfig` -> CGLIB 기술 없이 순수한 `AppConfig`로 스프링 빈에 등록된 것을 확인할 수 있으며,
* 인스턴스 확인 시 각각 다른 인스턴스를 가짐 (싱글톤 X) (`memberRepository()` 생성 메소드를 3번 호출)

#### 정리

* `@Bean`만 사용해도 스프링 빈으로 등록되지만, 싱글톤을 보장하지 않는다.
* `memberRepository()` 처럼 의존관계 주입이 필요해서 메서드를 직접 호출할 때 싱글톤을 보장하지 않는다.
* 크게 고민할 것이 없다. **스프링 설정 정보는 항상 `@Configuration` 을 사용하자**

---

### 컴포넌트 스캔

* 지금까지 스프링 빈을 등록할 때는 자바 코드의 `@Bean`이나 XML의 `<bean>` 등을 통해서 설정 정보에 직접 등록할 스프링 빈을 나열했다.
* 스프링은 설정 정보가 없어도 자동으로 스프링 빈을 등록하는 컴포넌트 스캔이라는 기능을 제공한다.
* 또 의존관계도 자동으로 주입하는 `@Autowired` 라는 기능도 제공한다.

사용법

* 컴포넌트 스캔을 사용하려면 먼저 `@ComponentScan` 을 설정 정보에 붙여주면 된다.
* 컴포넌트 스캔은 이름 그대로 `@Component` 애노테이션이 붙은 클래스를 스캔해서 스프링 빈으로 등록한다. 각 클래스가 스캔 대상이 되도록 `@Component` 를 붙여준다.
* 참고: `@Configuration` 이 컴포넌트 스캔의 대상이 된 이유도 `@Configuration` 소스코드를 열어보면 `@Component` 애노테이션이 붙어있기 때문이다.
* 이전에 `AppConfig`에서는 `@Bean` 으로 직접 설정 정보를 작성했고, 의존관계도 직접 명시했다.  
  이제는 이런 설정 정보 자체가 없기 때문에, 의존관계 주입도 이 클래스 안에서 해결해야 한다.
* `@Autowired` 는 의존관계를 자동으로 주입해준다. (`@Autowired` 를 사용하면 생성자에서 여러 의존관계도 한번에 주입받을 수 있다.)
* 스프링 빈의 기본 이름은 클래스명을 사용하되 맨 앞글자만 소문자를 사용한다.
    * **빈 이름 기본 전략**: `MemberServiceImpl` 클래스 ->  `memberServiceImpl`
    * **빈 이름 직접 지정**: 만약 스프링 빈의 이름을 직접 지정하고 싶으면  
      `@Component("memberService2")` 이런식으로 이름을 부여하면 된다.

--

### 컴포넌트 스캔 : 탐색 위치와 기본 스캔 대상

#### 탐색할 패키지의 시작 위치 지정

모든 자바 클래스를 다 컴포넌트 스캔하면 시간이 오래 걸린다. 그래서 꼭 필요한 위치부터 탐색하도록 시작 위치를 지
정할 수 있다.

```
@ComponentScan(
 basePackages = "hello.core",
}
```

* `basePackages` : 탐색할 패키지의 시작 위치를 지정한다. 이 패키지를 포함해서 하위 패키지를 모두 탐색한다.
    * `basePackages` = `{"hello.core", "hello.service"}` 이렇게 여러 시작 위치를 지정할 수도 있다.
* `basePackageClasses` : 지정한 클래스의 패키지를 탐색 시작 위치로 지정한다.
* **만약 지정하지 않으면 `@ComponentScan` 이 붙은 설정 정보 클래스의 패키지가 시작 위치가 된다.**

#### 권장하는 방법

패키지 위치를 지정하지 않고, 설정 정보 클래스의 위치를 프로젝트 최상단에 두는 것이다. 최근 스프링 부트도 이 방법을 기본으로 제공한다.
참고로 스프링 부트를 사용하면 스프링 부트의 대표 시작 정보인 `@SpringBootApplication` 를 이 프로젝트 시작루트 위치에 두는 것이 관례이다.  
(이 설정안에 `@ComponentScan` 이 들어있다.)

#### 컴포넌트 스캔 기본 대상

컴포넌트 스캔은 `@Component` 뿐만 아니라 다음과 내용도 추가로 대상에 포함한다.

* `@Component` : 컴포넌트 스캔에서 사용
* `@Controller` : 스프링 MVC 컨트롤러에서 사용
* `@Service` : 스프링 비즈니스 로직에서 사용
* `@Repository` : 스프링 데이터 접근 계층에서 사용
* `@Configuration` : 스프링 설정 정보에서 사용
* 해당 클래스들의 소스 코드를 보면 @Component 를 포함하고 있는 것을 알 수 있다.

```java

@Component
public @interface Controller {
}

@Component
public @interface Service {
}

@Component
public @interface Configuration {
}
```

참고: 사실 애노테이션에는 상속관계라는 것이 없다.  
그래서 이렇게 애노테이션이 특정 애노테이션을 들고 있는것을 인식할 수 있는 것은 자바 언어가 지원하는 기능은 아니고, 스프링이 지원하는 기능이다.

컴포넌트 스캔의 용도 뿐만 아니라 다음 애노테이션이 있으면 스프링은 부가 기능을 수행한다.

* `@Controller` : 스프링 MVC 컨트롤러로 인식
* `@Repository` : 스프링 데이터 접근 계층으로 인식하고, 데이터 계층의 예외를 스프링 예외로 변환해준다.
* `@Configuration` : 앞서 보았듯이 스프링 설정 정보로 인식하고, 스프링 빈이 싱글톤을 유지하도록 추가 처리를 한다.
* `@Service` : 특별한 처리를 하지 않는다. 대신 개발자들이 핵심 비즈니스 로직이 여기에 있겠구나 라고 비즈니스 계층을 인식하는데 도움이 된다.

---

#### 필터

* `includeFilters` : 컴포넌트 스캔 대상을 추가로 지정한다.
* `excludeFilters` : 컴포넌트 스캔에서 제외할 대상을 지정한다.

FilterType : 5가지 옵션이 있다.

* ANNOTATION: 기본값, 애노테이션을 인식해서 동작한다.
    * ex) org.example.SomeAnnotation
* ASSIGNABLE_TYPE: 지정한 타입과 자식 타입을 인식해서 동작한다.
    * ex) org.example.SomeClass
* ASPECTJ: AspectJ 패턴 사용
    * ex) org.example..*Service+
* REGEX: 정규 표현식
    * ex) org\.example\.Default.*
* CUSTOM: TypeFilter 이라는 인터페이스를 구현해서 처리
    * ex) org.example.MyTypeFilter

> 참고: `@Component` 면 충분하기 때문에, `includeFilters` 를 사용할 일은 **거의 없다.**  
`excludeFilters` 는 여러가지 이유로 간혹 사용할 때가 있지만 많지는 않다.  
> 특히 최근 스프링 부트는 컴포넌트 스캔을 기본으로 제공하는데, 옵션을 변경하면서 사용하기 보다는 스프링의 기본 설정에 최대한 맞추어 사용하는 것을 권장한다.

---

#### 컴포넌트 중복 등록과 충돌

자동 빈 등록 vs 자동 빈 등록

* 컴포넌트 스캔에 의해 자동으로 스프링 빈이 등록되는데, 그 이름이 같은 경우 스프링은 오류를 발생시킨다
    * `ConflictingBeanDefinitionException` 예외 발생

수동 빈 등록 vs 자동 빈 등록
이 경우 수동 빈 등록이 우선권을 가진다. (수동 빈이 자동 빈을 오버라이딩 해버린다.)

수동 빈 등록시 남는 로그 :
`Overriding bean definition for bean 'memoryMemberRepository' with a different definition: replacing`

> 하지만 현실은 개발자가 의도적으로 설정해서 이런 결과가 만들어지기 보다는 여러 설정들이 꼬여서 이런 결과가 만들어지는 경우가 대부분이다.  
> 그러면 정말 잡기 어려운 버그가 만들어진다. 항상 잡기 어려운 버그는 애매한 버그다.  
> 그래서 최근 스프링 부트에서는 수동 빈 등록과 자동 빈 등록이 충돌나면 **오류가 발생하도록 기본 값을 바꾸었다.**

수동 빈 등록, 자동 빈 등록 오류시 스프링 부트 에러 :
`Consider renaming one of the beans or enabling overriding by setting spring.main.allow-bean-definition-overriding=true`

---

### 다양한 의존관계 주입 방법

#### 생성자 주입

* 생성자 호출시점에 딱 1번만 호출되는 것이 보장된다.
* 불변, 필수 의존관계에 사용

> 중요! 생성자가 딱 1개만 있으면 `@Autowired`를 생략해도 자동 주입 된다. 물론 **스프링 빈**에만 해당한다.  
> ***순수한 자바 테스트 코드에는 당연히 `@Autowired`가 동작하지 않는다.*** `@SpringBootTest` 처럼 스프링 컨테이너를 테스트에 통합한 경우에만 가능하다.

참고 : 다음 코드와 같이 `@Bean` 에서 파라미터에 의존관계는 자동 주입된다. 수동 등록시 자동 등록된 빈의 의존 관계가 필요할 때 문제를 해결할 수 있다.

```
  @Bean
  OrderService orderService(MemberRepository memberRepoisitory,DiscountPolicy discountPolicy){
      return new OrderServiceImpl(memberRepository,discountPolicy);
  }
```

#### 수정자 주입(setter 주입)

* 선택, 변경 가능성이 있는 의존관계에 사용
* 자바빈 프로퍼티 규약의 수정자 메서드 방식을 사용하는 방법이다
* 참고: `@Autowired` 의 기본 동작은 주입할 대상이 없으면 오류가 발생한다. 주입할 대상이 없어도 동작하게 하려면 `@Autowired(required = false)` 로 지정하면 된다.

#### 필드 주입

* 코드가 간결해서 많은 개발자들을 유혹하지만 외부에서 변경이 불가능해서 테스트 하기 힘들다는 치명적인 단점이 있다.
* DI 프레임워크가 없으면 아무것도 할 수 없다.
* 사용하지 말자!
    * **애플리케이션의 실제 코드와 관계 없는 테스트 코드** 에서는 사용 가능, 프로덕션 코드에서는 사용하지 말자
    * 스프링 설정을 목적으로 하는 `@Configuration` 같은 곳에서만 특별한 용도로 사용 가능, 그러나 권장하지 않음

---

### `@Autowired` 옵션 처리

* 주입할 스프링 빈이 없어도 동작해야 할 때가 있다.
* 그런데 @Autowired 만 사용하면 required 옵션의 기본값이 true 로 되어 있어서 자동 주입 대상이 없으면 오류가 발생한다.

자동 주입 대상을 옵션으로 처리하는 방법은 다음과 같다.

* `@Autowired(required=false)` : 자동 주입할 대상이 없으면 수정자 메서드 자체가 호출 안됨
* `org.springframework.lang.@Nullable` : 자동 주입할 대상이 없으면` null`이 입력된다.
* `Optional<> `: 자동 주입할 대상이 없으면 `Optional.empty` 가 입력된다.

예제 : [TESTLINK](https://github.com/leesg619/my-study/blob/main/spring-study/spring-core-basic/practice/core/src/test/java/com/practice/core/autowired/AutowiredTest.java)

> 참고: `@Nullable`, `Optional`은 스프링 전반에 걸쳐서 지원된다. 예를 들어 생성자 자동 주입에서 **특정 필드에만** 사용해도 된다.

---

### 생성자 주입을 선택해라!

과거에는 수정자 주입과 필드 주입을 많이 사용했지만, 최근에는 스프링을 포함한 DI 프레임워크 대부분이 생성자 주입을 권장한다. 그 이유는 다음과 같다.

#### 불변

* 대부분의 의존관계 주입은 한번 일어나면 애플리케이션 종료시점까지 의존관계를 변경할 일이 없다.  
  오히려 대부분의 의존관계는 애플리케이션 종료 전까지 변하면 안된다. (불변해야 한다.)
* 수정자 주입을 사용하면, `setXxx` 메서드를 `public` 으로 열어두어야 한다.
* 누군가 실수로 변경할 수도 있고, 변경하면 안되는 메서드를 열어두는 것은 좋은 설계 방법이 아니다.
* 생성자 주입은 객체를 생성할 때 딱 1번만 호출되므로 이후에 호출되는 일이 없다. 따라서 **불변하게 설계**할 수 있다.

#### 누락

프레임워크 없이 순수한 자바 코드를 단위 테스트 하는 경우

* EX) 테스트코드로 `OrderServiceImpl` 을 테스트 하고자 한다. 내부적으로 MemberRepository와 DiscountPolicy 를 주입받아야 함.
* 생성자 주입이 아닌 Setter 주입일 경우
    * 아래 코드는 IDE 단에서 오류로 탐지 불가능함.  
      (`@Autowired` 가 프레임워크 안에서 동작할 때는 의존관계가 없으면 오류가 발생하지만,  
      지금은 프레임워크 없이 순수한 자바 코드로만 단위 테스트를 수행하고 있어서)
    * 막상 실행 결과는 **NPE** 가 발생하는데, `memberRepository`, `discountPolicy` 모두 의존관계 주입이 누락되었기 때문이다.

```
@Test
void createOrder(){
  OrderServiceImpl orderService=new OrderServiceImpl();
  orderService.createOrder(1L,"itemA",10000);
}
```

* 반면 생성자 주입을 사용하면 위처럼 주입 데이터를 누락 했을 때 컴파일 오류가 발생한다.  
  그리고 IDE에서 바로 어떤 값을 필수로 주입해야 하는지 알 수 있다.

#### final 키워드

* 생성자 주입을 사용하면 필드에 final 키워드를 사용할 수 있다.
* 그래서 생성자에서 혹시라도 값이 설정되지 않는 오류를 컴파일 시점에 막아준다.
* **컴파일 오류**는 세상에서 가장 빠르고, 좋은 오류다.

> 참고: 수정자 주입을 포함한 나머지 주입 방식은 모두 생성자 이후에 호출되므로, 필드에 final 키워드를 사용 할 수 없다. 오직 생성자 주입 방식만 final 키워드를 사용할 수 있다.

#### 정리

* 생성자 주입 방식을 선택하는 이유는 여러가지가 있지만, **프레임워크에 의존하지 않고, 순수한 자바 언어의 특징**을 잘 살리는 방법이기도 하다. (ex final)
* 기본으로 생성자 주입을 사용하고, 필수 값이 아닌 경우에는 수정자 주입 방식을 **옵션**으로 부여하면 된다. **생성자 주입과 수정자 주입을 동시에 사용할 수 있다.**
* **항상 생성자 주입을 선택해라**! 그리고 가끔 옵션이 필요하면 수정자 주입을 선택해라. 필드 주입은 사용하지 않는게 좋다.

---

### LOMBOK과 최신 트랜드

* 막상 개발을 해보면, 대부분이 다 불변이고, 그래서 다음과 같이 필드에 final 키워드를 사용하게 된다.  
  그런데 생성자도 만들어야 하고, 주입 받은 값을 대입하는 코드도 만들어야 하고… 편리하게 사용하는 방법은 없을까?

* 롬복 라이브러리가 제공하는 `@RequiredArgsConstructor` 기능을 사용하면 final이 붙은 필드를 모아서 생성자를 자동으로 만들어준다. (다음 코드에는 보이지 않지만 실제 호출 가능하다.)

최종 결과 코드

```java

@Component
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
	private final MemberRepository memberRepository;
	private final DiscountPolicy discountPolicy;
}
```

* 이 최종결과 코드와 이전의 코드는 완전히 동일하다. 롬복이 자바의 애노테이션 프로세서라는 기능을 이용해서 컴파일 시점에 생성자 코드를 자동으로 생성해준다.
* 실제 class 를 열어보면 다음 코드가 추가되어 있는 것을 확인할 수 있다.

```java
public OrderServiceImpl(MemberRepository memberRepository,DiscountPolicy
	discountPolicy){
	this.memberRepository=memberRepository;
	this.discountPolicy=discountPolicy;
	}
```

#### 정리

* 최근에는 생성자를 딱 1개 두고, `@Autowired` 를 생략하는 방법을 주로 사용한다.  
  여기에 `Lombok` 라이브러리의 `@RequiredArgsConstructor` 함께 사용하면 기능은 다 제공하면서, 코드는 깔끔하게 사용할 수 있다.

---

### 조회 빈이 2개 이상 - 문제

* @Autowired 는 타입(Type)으로 조회한다.
  `@Autowired private DiscountPolicy discountPolicy`
* 타입으로 조회하기 때문에, 마치 다음 코드와 유사하게 동작한다. (실제로는 더 많은 기능을 제공한다.)
  `ac.getBean(DiscountPolicy.class)`
* 스프링 빈 조회에서 학습했듯, 타입으로 조회하면, 선택된 빈이 2개 이상일 때 문제가 발생한다.
* `NoUniqueBeanDefinitionException` 오류 발생
    * `NoUniqueBeanDefinitionException: No qualifying bean of type 'hello.core.discount.DiscountPolicy' available: expected single matching bean but found 2: fixDiscountPolicy,rateDiscountPolicy`

> 이때 하위 타입으로 지정할 수 도 있지만, 하위 타입으로 지정하는 것은 DIP를 위배하고 유연성이 떨어진다.  
> 그리고 이름만 다르고, 완전히 똑같은 타입의 스프링 빈이 2개 있을 때 해결이 안된다.  
> 스프링 빈을 수동 등록해서 문제를 해결해도 되지만, 의존 관계 자동 주입에서 해결하는 여러 방법이 있다.

---

#### 1. `@Autowired` 필드 명 매칭

* 필드 명 매칭은 먼저 타입 매칭을 시도 하고 그 결과에 여러 빈이 있을 때 추가로 동작하는 기능이다.
* 필드 명을 빈 이름으로 변경하면 된다.
    * ex. `@Autowired private DiscountPolicy discountPolicy` -> `@Autowired private DiscountPolicy rateDiscountPolicy`

참고 : `@Autowired` 매칭 순서

1. 타입 매칭
2. 타입 매칭의 결과가 2개 이상일 때 필드 명, 파라미터 명으로 빈 이름 매칭

---

#### 2. `@Qualifier` 사용

* `@Qualifier` 는 추가 구분자를 붙여주는 방법이다. 주입시 추가적인 방법을 제공하는 것이지 **빈 이름을 변경하는 것은 아니다.**

a) 빈 등록시 `@Qualifier` 를 붙여 준다.

```
    @Component
    @Qualifier("mainDiscountPolicy")
    public class RateDiscountPolicy implements DiscountPolicy {}
```

b) 주입시에 `@Qualifier`를 붙여주고 등록한 이름을 적어준다.

```
@Autowired
public OrderServiceImpl(MemberRepository memberRepository, @Qualifier("mainDiscountPolicy") DiscountPolicy discountPolicy) {
    this.memberRepository = memberRepository;
    this.discountPolicy = discountPolicy;
}
```

> @Qualifier 로 주입할 때 `@Qualifier("mainDiscountPolicy")` 를 못찾으면 어떻게 될까?  
> 그러면 `mainDiscountPolicy`라는 이름의 스프링 빈을 추가로 찾는다.  
> 하지만 `@Qualifier` 는 `@Qualifier` 를 찾는 용도로만 사용하는게 명확하고 좋다.

다음과 같이 직접 빈 등록시에도 @Qualifier를 동일하게 사용할 수 있다.

```
@Bean
@Qualifier("mainDiscountPolicy")
public DiscountPolicy discountPolicy(){
	return new...
}
```

`@Qualifier` 찾는 순서 정리

1. `@Qualifier`끼리 매칭
2. 빈 이름 매칭
3. `NoSuchBeanDefinitionException` 예외 발생

---

#### 3. `@Primary`

* `@Primary` 는 우선순위를 정하는 방법이다. `@Autowired` 시에 여러 빈이 매칭되면 `@Primary` 가 우선권을 가진다.

```java
// rateDiscountPolicy 가 우선권을 가지도록 한다. 선언하면 끝
@Component
@Primary
public class RateDiscountPolicy implements DiscountPolicy {
}

@Component
public class FixDiscountPolicy implements DiscountPolicy {
}
```

> **정리**  
> 여기까지 보면 `@Primary` 와 `@Qualifier` 중에 어떤 것을 사용하면 좋을지 고민이 될 것이다.  
> @Qualifier 의 단점은 주입 받을 때 모든 코드에 `@Qualifier` 를 붙여주어야 한다는 점이다.
> 반면에 `@Primary` 를 사용하면 이렇게 `@Qualifier` 를 붙일 필요가 없다.

### `@Primary`, `@Qualifier` 활용

코드에서 자주 사용하는 메인 DB의 커넥션을 획득하는 스프링 빈이 있고, 코드에서 특별한 기능으로 가끔 사용하는 서브 DB의 커넥션을 획득하는 스프링 빈이 있다고 생각해보자.  
메인 DB의 커넥션을 획득하는 스프링 빈은 `@Primary` 를 적용해서 조회하는 곳에서 `@Qualifier` 지정 없이 편리하게 조회하고,
서브 DB 커넥션 빈을 획득할 때는 `@Qualifier` 를 지정해서 명시적으로 획득하는 방식으로 사용하면 코드를 깔끔하게 유지할 수 있다.  
물론 이때 메인 데이터베이스의 스프링 빈을 등록할 때 `@Qualifier` 를 지정해주는 것은 상관없다.

#### 우선순위

`@Primary` 는 기본값 처럼 동작하는 것이고,` @Qualifier` 는 매우 상세하게 동작한다. 이런 경우 어떤 것이 우선권을
가져갈까? 스프링은 자동보다는 수동이, 넒은 범위의 선택권 보다는 좁은 범위의 선택권이 우선 순위가 높다. 따라서 여
기서도 `@Qualifier` 가 우선권이 높다.

---

### 애노테이션 직접 만들기

* `@Qualifier("mainDiscountPolicy")` 이렇게 문자를 적으면 컴파일시 타입 체크가 안된다.
* 다음과 같은 애노테이션을 만들어서 문제를 해결할 수 있다. 참고 : **[QnA](https://www.inflearn.com/questions/401416)**

```java
package hello.core.annotataion;

import org.springframework.beans.factory.annotation.Qualifier;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER,
	ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Qualifier("mainDiscountPolicy")
public @interface MainDiscountPolicy {
	// 위 애노테이션 선언 시 : @Qualiofier 의 정의부에서 가져온 annotation 들 이며, 붙여줘야 스프링이 인식한다.
	// @Retention은 자바 언어차원에서 처리되는 부분이다. 컴포넌트 스캔이 런타임에 일어나므로 런타임으로 설정해야함. 
}
```

```java

@Component
@MainDiscountPolicy
public class RateDiscountPolicy implements DiscountPolicy {
}
```

```java
//생성자 자동 주입
@Autowired
public OrderServiceImpl(MemberRepository memberRepository,
@MainDiscountPolicy DiscountPolicy discountPolicy){}
```

> 애노테이션에는 상속이라는 개념이 없다. 이렇게 여러 애노테이션을 모아서 사용하는 기능은 스프링이 지원해주는 기능이다.  
> `@Qualifier` 뿐만 아니라 다른 애노테이션들도 함께 조합해서 사용할 수 있다. 단적으로 `@Autowired` 도 재정의 할 수 있다.  
> 물론 스프링이 제공하는 기능을 뚜렷한 목적 없이 무분별하게 재정의 하는 것은 유지보수에 더 혼란만 가중할 수 있다.
 
---

### 조회한 빈이 모두 필요할 때, List, Map

* 의도적으로 정말 해당 타입의 스프링 빈이 다 필요한 경우도 있다.
* 예를 들어서 할인 서비스를 제공하는데, 클라이언트가 할인의 종류(rate, fix)를 선택할 수 있다고 가정해보자.  
  스프링을 사용하면 소위 말하는 전략 패턴을 매우 간단하게 구현할 수 있다.

