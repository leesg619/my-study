# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## What this repo is

A personal study monorepo with two coexisting systems:

1. **A static GitHub Pages site** (repo root) — the canonical, actively-maintained place for lecture notes. Hosted at `https://leesg619.github.io/my-study/`.
2. **Practice code projects** under `spring-study/*/practice/` and `test-code-study/*/practice/` — independent Gradle/Spring Boot projects with real, runnable code (not legacy).

The old convention was one `README.md` per course folder with notes in Markdown. That has been superseded by the site for every course that has one: `java-intermediate-1`, `practical-test-guide`, `spring-introduction`, `spring-core-basic`, `spring-core-advanced`. Their original `README.md` files are now trimmed to a one-line pointer at the site plus a `git show <commit>~1:<path>` command to recover the original text from history — **do not restore or re-edit these READMEs**; edit the corresponding HTML section pages instead.

## The site: architecture

Pure static HTML/CSS/vanilla JS, no build step, no bundler, no npm — every page just loads shared assets via plain `<script src>`/`<link>` tags and works by opening the file directly (no `fetch()`-based includes, since those break under `file://`).

- `index.html` — homepage; renders course cards from `assets/js/courses.js` via `renderCourseCards()`.
- `assets/css/style.css` — the only stylesheet, shared by every page. Defines light/dark via `prefers-color-scheme` + CSS variables, responsive breakpoints, and code block / blockquote styling.
- `assets/js/layout.js` — the shared rendering engine. Exposes `initSectionPage()`, `initCourseIndexPage()`, `renderCourseCards()`, `renderSectionList()`. These read a per-page config object and a course's `sections.js` array, then inject the header/breadcrumb, table of contents, and prev/next nav into `#layout-root` and `main.content` — nothing about layout is hand-written per page.
- `assets/js/courses.js` — single array (`window.COURSES`) driving the homepage card list. One object per course: `{ tag, title, description, href }`.
- `<course-folder>/sections.js` — single source of truth for one course's section order/titles/links (`window.SECTIONS`, array of `{ id, title, href }`). The TOC, breadcrumb, and prev/next links on every section page in that course are derived from this array — nothing else needs to change when reordering or renaming a section.
- `<course-folder>/index.html` — course landing page; lists sections via `renderSectionList()`. If `SECTIONS` is empty, it renders a "no sections yet" message instead of an empty list (see `spring-core-advanced`, which has no content yet).
- `<course-folder>/_section-template.html` — copy this to add a new section. Not linked from anywhere (leading underscore keeps it out of Jekyll's way too, though `.nojekyll` at the root disables Jekyll processing entirely for the whole site).
- `<course-folder>/section-NN.html` — one page per section (or one page per *group* of related sub-topics — see `practical-test-guide` and `spring-core-basic`, which each group ~3-4 original subheadings per page rather than going 1:1).

### Adding a new section

1. Copy `_section-template.html` in the target course folder to `section-NN.html`.
2. Set `data-current` on `#layout-root` and `currentId` in the `initSectionPage(...)` call to match the new section's `id`.
3. Fill in the content (plain `h2`/`h3`/`ul`/`pre code`/`blockquote` — no markdown).
4. Append `{ id, title, href }` to that course's `sections.js`.

### Writing Inflearn-course notes: lecture-first, PDF as a fact-check

This routine applies to every course backed by a 김영한 Inflearn lecture with a copyrighted PDF folder — currently `java-intermediate-1` (`java-intermediate-1-pdf/`, "실전 자바 - 중급 1편", `courseId: 333308`) and `java-basic` (`java-basic-pdf/`, "김영한의 실전 자바 - 기본편", `courseId: 332506`). Out of respect for the instructor, section pages must **not** be a transcription or paraphrase-of-the-slides. The lecture content (via MCP) is the primary source; the PDF may only be used afterward as a secondary accuracy check, never as the thing being rewritten:

1. **Lecture → HTML draft.** Call the Inflearn MCP's `get_curriculum` (with that course's `courseId`) to map section/unit IDs — PDF chapter numbers don't line up 1:1 with video units (one PDF file can span several units, e.g. "1. Object 클래스" = units 212188–212198). Then call `get_lecture_content` for each relevant unit — it returns a timestamped breakdown of what the instructor actually said (explanation flow, analogies, example code, verbatim quotes), not just slide text. Skip units that are just "문제와 풀이" (quiz) or pure environment setup.
2. Write the section page by paraphrasing that explanation flow, examples, and reasoning in your own words. Keeping the instructor's underlying teaching structure (e.g. problem → limitation → solution, or bad-example → good-example) is fine and often correct when that's genuinely how the concept was taught — the thing to avoid is mechanically reproducing the MCP response's own `도입/본론/마무리` template on every section regardless of content.
3. **PDF → fact-check pass, not content source.** After the HTML draft exists, open the matching PDF (`<course>-pdf/<N>. <제목>.pdf`) only to verify technical details the audio transcript tends to blur — exact method signatures, diagram structure, precise terminology — and correct the draft accordingly. Never copy the PDF's wording, bullet structure, or layout; never add content that's in the PDF but wasn't already covered by the lecture-based draft. If nothing is wrong, make no edit — most sections pass with zero changes.
4. Match the site's existing bullet-heavy note style: `h2`/`h3` headings + short `<li>` bullets, `<strong>`/`<code>` for key terms, `→` arrows to show cause/effect, minimal prose paragraphs. See `spring-core-basic/section-01.html` as the reference style — do not write essay-style paragraphs even though the source material is conversational.
5. Add a `<blockquote>` right under the `<h1>` crediting the instructor/course and stating the page is a personal paraphrase of the lecture, not a copy of the slides.
6. When asked to do a standalone fact-check pass over an already-written course (no new drafting), just repeat step 3 section-by-section: read the PDF, read the matching `section-NN.html`, compare, and either report "확인 완료 — 오류 없음" or fix the specific inaccuracy via `Edit`. Keep reports terse and one section at a time rather than batching the whole course into one message.

### Adding a new course

1. Create a new folder at the repo root (sibling to `java-intermediate-1`).
2. Copy that course's `index.html`, `_section-template.html`, and `sections.js` as a starting point, and adjust `courseTitle`/`courseHome` in each page's `initSectionPage`/`initCourseIndexPage` call.
3. Add one entry to `assets/js/courses.js`.
4. If it's an Inflearn course, add a 복습 가이드 to its `index.html` (see below).

### Course index page: 복습 가이드 (Inflearn courses)

Every Inflearn-backed course's `index.html` ends with a short `<h2>복습 가이드</h2>` block — a note to future-me on how to re-review a chapter. `java-basic/index.html` and `java-intermediate-1/index.html` are the reference; copy that block and swap the IDs/titles.

- **Placement**: *below* `<h2>섹션 목록</h2>` + `#section-list` (the section list stays first), as the last thing in `main.content`.
- **3-step order per chapter** (`<ol>`): ① PDF 강의자료 — 해당 챕터 PDF → ② 정리 노트 — 이 사이트의 해당 섹션 페이지 → ③ 정리 영상 — 인프런에서 그 챕터의 마지막 "정리" 강의 하나. Keep step ① that terse: **no local folder paths or file-naming notes** (they change, and the PDF folder is gitignored anyway — never link to a PDF).
- **"다음으로" line**: one `<p>` pointing at the course's final "다음으로" lecture (video link + its section note) as the place to restart after a long break.
- **Per-chapter shortcut list** inside a collapsed `<details>` ("챕터별 노트 · 정리 영상 바로가기"): one `<li>` per chapter (excluding "다음으로") = section link · "정리 영상" link. Get unit IDs from `get_curriculum` — use the unit titled `정리` in each curriculum section. Link format: `https://www.inflearn.com/courses/lecture?courseId=<id>&amp;unitId=<id>` (escape `&` as `&amp;`).
- If a chapter has no `정리` unit (e.g. 중급 1편 ch.7, ch.9 — the "…1" half of a two-part topic), write "정리 영상 없음 → N장 정리 영상으로" pointing at the paired chapter instead of linking an arbitrary unit.
- Hardcode this list as static HTML (not generated from `sections.js`) so it renders without JS.

### Gotchas learned the hard way

- **The lecture PDFs' Korean text extracts as garbage** (embedded AppleSDGothicNeo with no ToUnicode map; PyMuPDF gives `�`, pypdf gives odd Unicode like `৘৻ ୊ܻ`). Fix: extract with pypdf, then map each char `chr(1086 + i)` → the i-th Hangul syllable of KS X 1001 (decode bytes `0xB0A1`–`0xC8FE` with `euc-kr`, skipping invalid). Result is readable enough to fact-check, with occasional swapped syllables.
- **Never let a `*/` appear inside a `/** ... */` block comment**, even as part of a word — it silently closes the comment early and corrupts everything after it (bit us once in `layout.js`; the file that got broken loaded fine over HTTP with a 200, so a network check alone won't catch this — you have to actually execute/parse the JS).
- **Escape `<`/`>` inside `<pre><code>` blocks** whenever the sample code contains generics (`List<String>`) or literal HTML tags — otherwise the browser parses them as real markup instead of text.
- After editing anything under `assets/`, verify by actually starting a local server and loading pages in the browser tool (checking console errors + `get_page_text`/screenshots) — a plain `curl`/`fetch` 200 does not prove the JS parsed or executed correctly.

### Local preview

`.claude/launch.json` defines a `static-site` config (`python -m http.server 4321`) for the Browser tool's `preview_start`. Opening files directly via `file://` also mostly works except that some sandboxed preview contexts silently skip script execution — prefer the HTTP server for anything that must actually render JS-driven content.

## Practice code projects

Three independent Gradle projects, each with its own wrapper — there is no root Gradle build aggregating them:

- `spring-study/spring-introduction/practice/intro/`
- `spring-study/spring-core-basic/practice/core/`
- `test-code-study/practical-test-guide/practice/cafekiosk/`

All are Spring Boot 3.x / Java 17, using JUnit 5 (`useJUnitPlatform()`). `cd` into a project directory before running Gradle — there's nothing to run from the repo root.

```bash
./gradlew test              # run all tests in that project
./gradlew test --tests "com.practice.core.beanfind.ApplicationContextInfoTest"   # single test class
./gradlew build              # full build
```

On Windows use `gradlew.bat` instead of `./gradlew`.
