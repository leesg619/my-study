/**
 * Shared layout renderer for the My Study site.
 *
 * No build step: every page loads this file via a plain <script> tag and
 * calls one of the init/render functions below with a small config
 * object. New pages should not need to duplicate header/TOC/prev-next
 * markup by hand.
 */
(function () {
  function ce(tag, opts) {
    var el = document.createElement(tag);
    opts = opts || {};
    if (opts.class) el.className = opts.class;
    if (opts.text != null) el.textContent = opts.text;
    if (opts.html != null) el.innerHTML = opts.html;
    if (opts.href != null) el.setAttribute("href", opts.href);
    return el;
  }

  function buildHeader(config) {
    var header = ce("header", { class: "site-header" });
    var inner = ce("div", { class: "site-header-inner" });
    var homeHref = (config.rootPath || "") + "index.html";
    inner.appendChild(ce("a", { class: "site-title", text: "📚 My Study", href: homeHref }));

    if (config.courseTitle) {
      inner.appendChild(ce("span", { class: "crumb-sep", text: "/" }));
      inner.appendChild(
        ce("a", { class: "course-title", text: config.courseTitle, href: config.courseHome || "index.html" })
      );
    }

    header.appendChild(inner);
    return header;
  }

  function buildToc(config) {
    var details = ce("details", { class: "toc" });
    details.appendChild(ce("summary", { text: "▤ 목차" }));

    var ol = ce("ol", { class: "toc-list" });
    (config.sections || []).forEach(function (section) {
      var li = ce("li");
      var a = ce("a", { text: section.title, href: section.href });
      if (section.id === config.currentId) {
        a.className = "current";
        details.open = true;
      }
      li.appendChild(a);
      ol.appendChild(li);
    });

    details.appendChild(ol);
    return details;
  }

  function buildPrevNext(config) {
    var sections = config.sections || [];
    var idx = sections.findIndex(function (s) {
      return s.id === config.currentId;
    });
    var prev = idx > 0 ? sections[idx - 1] : null;
    var next = idx > -1 && idx < sections.length - 1 ? sections[idx + 1] : null;

    var nav = ce("nav", { class: "prev-next" });
    nav.appendChild(
      prev
        ? ce("a", { class: "prev-next-link prev", href: prev.href, html: "← <span>" + prev.title + "</span>" })
        : ce("span", { class: "prev-next-link prev disabled" })
    );
    nav.appendChild(
      next
        ? ce("a", { class: "prev-next-link next", href: next.href, html: "<span>" + next.title + "</span> →" })
        : ce("span", { class: "prev-next-link next disabled" })
    );
    return nav;
  }

  /** Call on every lecture-section page. */
  window.initSectionPage = function (config) {
    var root = document.getElementById("layout-root");
    if (!root) return;

    root.appendChild(buildHeader(config));
    if (config.sections && config.sections.length) {
      root.appendChild(buildToc(config));
    }

    var main = document.querySelector("main.content");
    if (main && config.sections && config.sections.length) {
      main.insertBefore(buildPrevNext(config), main.firstChild);
      main.appendChild(buildPrevNext(config));
    }
  };

  /** Call on the site homepage and on each course index page. */
  window.initCourseIndexPage = function (config) {
    var root = document.getElementById("layout-root");
    if (!root) return;
    root.appendChild(buildHeader(config || {}));
  };

  /** Renders course cards on the homepage from window.COURSES. */
  window.renderCourseCards = function (selector) {
    var container = document.querySelector(selector);
    if (!container || !window.COURSES) return;

    window.COURSES.forEach(function (course) {
      var card = ce("a", { class: "course-card", href: course.href });
      card.appendChild(ce("span", { class: "course-card-tag", text: course.tag }));
      card.appendChild(ce("h2", { text: course.title }));
      card.appendChild(ce("p", { text: course.description }));
      container.appendChild(card);
    });
  };

  /** Renders the section list on a course index page from its sections.js. */
  window.renderSectionList = function (selector, sections) {
    var container = document.querySelector(selector);
    if (!container || !sections) return;

    if (!sections.length) {
      container.appendChild(ce("p", { class: "eyebrow", text: "아직 정리된 섹션이 없습니다. 강의를 들으며 추가될 예정입니다." }));
      return;
    }

    var ol = ce("ol", { class: "section-list" });
    sections.forEach(function (section) {
      var li = ce("li");
      li.appendChild(ce("a", { text: section.title, href: section.href }));
      ol.appendChild(li);
    });
    container.appendChild(ol);
  };
})();
