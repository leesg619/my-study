/**
 * List of courses shown as cards on the homepage (index.html).
 *
 * To add a new course:
 * 1. Create a new folder at the repo root (e.g. /math-xxx/).
 * 2. Copy java-intermediate-1/index.html and _section-template.html into it.
 * 3. Add an entry below pointing at the new folder index.html.
 */
window.COURSES = [
  {
    tag: "Java",
    title: "김영한의 실전 자바 - 중급 1편",
    description: "인프런 김영한 강의. 섹션별 핵심 내용과 코드 예제를 정리합니다.",
    href: "java-intermediate-1/index.html",
  },
  {
    tag: "Testing",
    title: "Practical Testing: 실용적인 테스트 가이드",
    description: "인프런 강의. 스프링 & JPA 기반 프로젝트의 단위/통합 테스트 작성법을 정리합니다.",
    href: "practical-test-guide/index.html",
  },
  {
    tag: "Spring",
    title: "스프링 입문",
    description: "인프런 김영한 강의. 코드로 배우는 스프링 부트, 웹 MVC, DB 접근 기술을 정리합니다.",
    href: "spring-introduction/index.html",
  },
  {
    tag: "Spring",
    title: "스프링 핵심 원리 - 기본편",
    description: "인프런 김영한 강의. 객체지향 설계, IoC/DI, 싱글톤 컨테이너, 컴포넌트 스캔 등을 정리합니다.",
    href: "spring-core-basic/index.html",
  },
  {
    tag: "Spring",
    title: "스프링 핵심 원리 - 고급편",
    description: "인프런 김영한 강의. 디자인 패턴, 쓰레드 로컬, 스프링 AOP를 다룹니다. (준비 중)",
    href: "spring-core-advanced/index.html",
  },
];
