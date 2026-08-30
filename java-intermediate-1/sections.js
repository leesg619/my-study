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
  { id: "section-01", title: "1. (섹션 제목을 입력하세요)", href: "section-01.html" },
];
