const moduleId = '61868074-759c-48f2-a32b-c281558376ca';
window.__LP_UI_MODULES__ ??= {};

window.__LP_UI_MODULES__[moduleId] = {
  async mount(host, context) {
    const school = context?.context;
    if (!school?.schoolSlug) {
      host.innerHTML = message('Kein Schulkontext ausgewählt.');
      return;
    }

    host.innerHTML = `<section class="info-card"><p class="eyebrow">${escapeHtml(school.schoolName)}</p><h2>Klassen</h2><p class="lead">Klassen werden geladen …</p></section>`;

    try {
      const classes = await api(`/api/v1/schools/${encodeURIComponent(school.schoolSlug)}/classes`);
      renderClasses(host, school, classes);
    } catch (error) {
      host.innerHTML = message('Die Klassen konnten nicht geladen werden.');
    }
  },
  unmount(host) { host.replaceChildren(); }
};

function renderClasses(host, school, classes) {
  if (!classes.length) {
    host.innerHTML = `<section class="info-card"><p class="eyebrow">${escapeHtml(school.schoolName)}</p><h2>Klassen</h2><p>Für diese Schule sind aktuell keine aktiven Klassen angelegt.</p></section>`;
    return;
  }

  host.innerHTML = `<section class="runtime-feature"><p class="eyebrow">${escapeHtml(school.schoolName)}</p><h2>Klassen</h2><div class="runtime-grid">${classes.map(renderClassCard).join('')}</div><div data-detail></div></section>`;
  host.querySelectorAll('[data-class-id]').forEach(button => {
    button.addEventListener('click', () => loadStudents(host, school, button.dataset.classId, button.dataset.className));
  });
}

function renderClassCard(item) {
  return `<article class="info-card runtime-card">
    <div class="runtime-card-heading"><div><p class="eyebrow">Klasse ${escapeHtml(item.name)}</p><h3>${escapeHtml(item.name)}</h3></div>${item.responsible ? '<span class="status-badge">Zuständig</span>' : ''}</div>
    <dl class="registration-details">
      <div><dt>Jahrgang</dt><dd>${escapeHtml(item.gradeLevel)}</dd></div>
      <div><dt>Schuljahr</dt><dd>${escapeHtml(item.schoolYear)}</dd></div>
      <div><dt>Schüler</dt><dd>${escapeHtml(item.studentCount)}</dd></div>
      <div><dt>Zuständige Lehrer</dt><dd>${escapeHtml(item.teacherCount)}</dd></div>
    </dl>
    <button class="button" type="button" data-class-id="${escapeHtml(item.id)}" data-class-name="${escapeHtml(item.name)}">Klasse öffnen</button>
  </article>`;
}

async function loadStudents(host, school, classId, className) {
  const detail = host.querySelector('[data-detail]');
  detail.innerHTML = `<section class="info-card runtime-detail"><h3>${escapeHtml(className)}</h3><p>Schüler werden geladen …</p></section>`;
  try {
    const students = await api(`/api/v1/schools/${encodeURIComponent(school.schoolSlug)}/classes/${encodeURIComponent(classId)}/students`);
    detail.innerHTML = `<section class="info-card runtime-detail"><h3>${escapeHtml(className)}</h3>${students.length ? `<ul class="runtime-list">${students.map(student => `<li><strong>${escapeHtml(student.displayName)}</strong><span>seit ${formatDate(student.validFrom)}</span></li>`).join('')}</ul>` : '<p>Dieser Klasse sind aktuell keine Schüler zugeordnet.</p>'}</section>`;
    detail.scrollIntoView({ behavior: 'smooth', block: 'start' });
  } catch (error) {
    detail.innerHTML = message('Die Schüler dieser Klasse konnten nicht geladen werden.');
  }
}

async function api(url) {
  const response = await fetch(url, { credentials: 'include', headers: { Accept: 'application/json' } });
  if (!response.ok) throw new Error(`HTTP ${response.status}`);
  return response.json();
}

function formatDate(value) {
  return new Intl.DateTimeFormat('de-DE', { dateStyle: 'medium' }).format(new Date(`${value}T00:00:00`));
}

function message(text) {
  return `<section class="info-card"><p class="message error" role="alert">${escapeHtml(text)}</p></section>`;
}

function escapeHtml(value) {
  return String(value ?? '').replace(/[&<>"']/g, c => ({ '&': '&amp;', '<': '&lt;', '>': '&gt;', '"': '&quot;', "'": '&#39;' }[c]));
}
