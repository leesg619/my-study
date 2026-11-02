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
  { id: "section-01", title: "1. 제네릭 - Generic 1", href: "section-01.html" },
  { id: "section-02", title: "2. 제네릭 - Generic 2", href: "section-02.html" },
  { id: "section-03", title: "3. 컬렉션 프레임워크 - ArrayList", href: "section-03.html" },
  { id: "section-04", title: "4. 컬렉션 프레임워크 - LinkedList", href: "section-04.html" },
  { id: "section-05", title: "5. 컬렉션 프레임워크 - List", href: "section-05.html" },
  { id: "section-06", title: "6. 컬렉션 프레임워크 - 해시(Hash)", href: "section-06.html" },
  { id: "section-07", title: "7. 컬렉션 프레임워크 - HashSet", href: "section-07.html" },
  { id: "section-08", title: "8. 컬렉션 프레임워크 - Set", href: "section-08.html" },
  { id: "section-09", title: "9. 컬렉션 프레임워크 - Map, Stack, Queue", href: "section-09.html" },
  { id: "section-10", title: "10. 컬렉션 프레임워크 - 순회, 정렬, 전체 정리", href: "section-10.html" },
  { id: "section-11", title: "11. 다음으로", href: "section-11.html" },
];
