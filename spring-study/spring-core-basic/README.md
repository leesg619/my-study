## 영한님 스프링 핵심 원리 - 기본편

>스프링 입문자가 예제를 만들어가면서 스프링의 핵심 원리를 이해하고,
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
* Java - 역할과 구현을 명확히 분리 (역할 = 인터페이스  / 구현 = 인터페이스를 구현한 클래스, 구현 객체)

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
* 다형성에서 하위 클래스는 인터페이스 규약을 다 지켜야 한다는 것,  다형성을 지원하기 위한 원칙,  
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













