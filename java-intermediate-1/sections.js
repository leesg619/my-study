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
  { id: "section-01", title: "1. Object 클래스", href: "section-01.html" },
  { id: "section-02", title: "2. 불변 객체", href: "section-02.html" },
  { id: "section-03", title: "3. String 클래스", href: "section-03.html" },
  { id: "section-04", title: "4. 래퍼, Class 클래스", href: "section-04.html" },
  { id: "section-05", title: "5. 열거형 - ENUM", href: "section-05.html" },
  { id: "section-06", title: "6. 날짜와 시간", href: "section-06.html" },
  { id: "section-07", title: "7. 중첩 클래스, 내부 클래스 1", href: "section-07.html" },
  { id: "section-08", title: "8. 중첩 클래스, 내부 클래스 2", href: "section-08.html" },
  { id: "section-09", title: "9. 예외 처리 1 - 이론", href: "section-09.html" },
  { id: "section-10", title: "10. 예외 처리 2 - 실습", href: "section-10.html" },
  { id: "section-11", title: "11. 다음으로", href: "section-11.html" },
];
