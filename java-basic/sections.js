/**
 * Single source of truth for this course section order, titles, and links.
 * The header crumb, table of contents, and prev/next nav on every section
 * page are all generated from this array — adding a section here is enough
 * to wire it into the shared layout.
 *
 * To add a new section:
 * 1. Copy _section-template.html to section-XX.html (zero-padded).
 * 2. Set data-current and currentId inside it to match the id below.
 * 3. Append an entry to this array.
 */
window.SECTIONS = [
  { id: "section-01", title: "1. 클래스와 데이터", href: "section-01.html" },
  { id: "section-02", title: "2. 기본형과 참조형", href: "section-02.html" },
  { id: "section-03", title: "3. 객체 지향 프로그래밍", href: "section-03.html" },
  { id: "section-04", title: "4. 생성자", href: "section-04.html" },
  { id: "section-05", title: "5. 패키지", href: "section-05.html" },
  { id: "section-06", title: "6. 접근 제어자", href: "section-06.html" },
  { id: "section-07", title: "7. 자바 메모리 구조와 static", href: "section-07.html" },
  { id: "section-08", title: "8. final", href: "section-08.html" },
  { id: "section-09", title: "9. 상속", href: "section-09.html" },
  { id: "section-10", title: "10. 다형성1", href: "section-10.html" },
  { id: "section-11", title: "11. 다형성2", href: "section-11.html" },
  { id: "section-12", title: "12. 다형성과 설계", href: "section-12.html" },
  { id: "section-13", title: "13. 다음으로", href: "section-13.html" },
];
