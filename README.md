# my-study
study anything

## GitHub Pages

강의별 학습 노트를 정리한 정적 사이트가 이 레포 루트에 있습니다: `index.html` → 강의 목록, `/java-intermediate-1/` 등 강의별 폴더 → 섹션 페이지.
빌드 툴체인 없이 순수 HTML/CSS/JS이며, 헤더·목차·이전/다음 네비게이션은 각 강의 폴더의 `sections.js`를 기반으로 `assets/js/layout.js`가 자동으로 렌더링합니다.

- 새 섹션 추가: 강의 폴더의 `_section-template.html`을 복사해 채운 뒤 `sections.js` 배열에 한 줄 추가
- 새 강의 추가: 루트에 새 폴더를 만들고 `assets/js/courses.js`에 카드 항목 추가
- 인프런 강의는 강의 `index.html`의 섹션 목록 아래에 **복습 가이드**를 둠 (PDF → 정리 노트 → 인프런 "정리" 영상 순서 + 챕터별 바로가기, 마지막 "다음으로" 영상 안내) — `java-basic/index.html` 참고

GitHub Pages는 저장소 Settings → Pages에서 `Deploy from a branch` → `main` / `(root)`로 설정하면 활성화됩니다.
