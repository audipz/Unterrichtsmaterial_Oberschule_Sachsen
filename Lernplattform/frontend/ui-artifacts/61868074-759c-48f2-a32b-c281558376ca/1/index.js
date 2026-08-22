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
    } catch {
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
    button.addEventListener('click', () => loadClassDetail(host, school, button.dataset.classId, button.dataset.className));
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

async function loadClassDetail(host, school, classId, className) {
  const detail = host.querySelector('[data-detail]');
  detail.innerHTML = `<section class="info-card runtime-detail"><h3>${escapeHtml(className)}</h3><p>Daten werden geladen …</p></section>`;
  try {
    const [students, teachers, availableTeachers] = await Promise.all([
      api(`/api/v1/schools/${encodeURIComponent(school.schoolSlug)}/classes/${encodeURIComponent(classId)}/students`),
      api(`/api/v1/schools/${encodeURIComponent(school.schoolSlug)}/classes/${encodeURIComponent(classId)}/teachers`),
      api(`/api/v1/schools/${encodeURIComponent(school.schoolSlug)}/teachers`)
    ]);
    renderDetail(detail, school, classId, className, students, teachers, availableTeachers);
    detail.scrollIntoView({ behavior: 'smooth', block: 'start' });
  } catch {
    detail.innerHTML = message('Die Klassendetails konnten nicht geladen werden.');
  }
}

function renderDetail(detail, school, classId, className, students, teachers, availableTeachers) {
  const assigned = new Set(teachers.map(t => t.membershipId));
  const assignable = availableTeachers.filter(t => !assigned.has(t.membershipId));
  detail.innerHTML = `<section class="info-card runtime-detail">
    <h3>${escapeHtml(className)}</h3>
    <div class="runtime-detail-grid">
      <div>
        <h4>Zuständige Lehrer</h4>
        ${teachers.length ? `<ul class="runtime-list" data-teacher-list>${teachers.map(t => `<li><strong>${escapeHtml(t.displayName)}</strong><button class="button" type="button" data-remove-teacher="${escapeHtml(t.membershipId)}">Entfernen</button></li>`).join('')}</ul>` : '<p>Keine zuständigen Lehrer.</p>'}
        <div class="runtime-inline-form">
          <select data-add-teacher ${assignable.length ? '' : 'disabled'}>
            <option value="">Lehrer auswählen</option>
            ${assignable.map(t => `<option value="${escapeHtml(t.membershipId)}">${escapeHtml(t.displayName)}</option>`).join('')}
          </select>
          <button class="button primary" type="button" data-add-teacher-button ${assignable.length ? '' : 'disabled'}>Hinzufügen</button>
        </div>
        <p class="privacy-note">Eine aktive Klasse muss immer mindestens einen zuständigen Lehrer haben.</p>
        <div data-teacher-message></div>
      </div>
      <div>
        <h4>Schüler</h4>
        ${students.length ? `<ul class="runtime-list">${students.map(student => `<li><strong>${escapeHtml(student.displayName)}</strong><span>seit ${formatDate(student.validFrom)}</span></li>`).join('')}</ul>` : '<p>Dieser Klasse sind aktuell keine Schüler zugeordnet.</p>'}
      </div>
    </div>
  </section>`;

  detail.querySelector('[data-add-teacher-button]')?.addEventListener('click', async () => {
    const select = detail.querySelector('[data-add-teacher]');
    if (!select?.value) return;
    try {
      await api(`/api/v1/schools/${encodeURIComponent(school.schoolSlug)}/classes/${encodeURIComponent(classId)}/teachers`, {
        method: 'POST', headers: { 'Content-Type': 'application/json', Accept: 'application/json' }, body: JSON.stringify({ membershipId: select.value })
      }, false);
      await refreshDetail(detail, school, classId, className);
    } catch {
      showTeacherMessage(detail, 'Der Lehrer konnte nicht hinzugefügt werden.');
    }
  });

  detail.querySelectorAll('[data-remove-teacher]').forEach(button => {
    button.addEventListener('click', async () => {
      try {
        const response = await fetch(`/api/v1/schools/${encodeURIComponent(school.schoolSlug)}/classes/${encodeURIComponent(classId)}/teachers/${encodeURIComponent(button.dataset.removeTeacher)}`, { method: 'DELETE', credentials: 'include' });
        if (response.status === 409) {
          showTeacherMessage(detail, 'Der letzte zuständige Lehrer kann nicht entfernt werden. Weise zuerst einen weiteren Lehrer zu.');
          return;
        }
        if (!response.ok) throw new Error(`HTTP ${response.status}`);
        await refreshDetail(detail, school, classId, className);
      } catch {
        showTeacherMessage(detail, 'Der Lehrer konnte nicht entfernt werden.');
      }
    });
  });
}

async function refreshDetail(detail, school, classId, className) {
  const [students, teachers, availableTeachers] = await Promise.all([
    api(`/api/v1/schools/${encodeURIComponent(school.schoolSlug)}/classes/${encodeURIComponent(classId)}/students`),
    api(`/api/v1/schools/${encodeURIComponent(school.schoolSlug)}/classes/${encodeURIComponent(classId)}/teachers`),
    api(`/api/v1/schools/${encodeURIComponent(school.schoolSlug)}/teachers`)
  ]);
  renderDetail(detail, school, classId, className, students, teachers, availableTeachers);
}

function showTeacherMessage(detail, text) {
  const target = detail.querySelector('[data-teacher-message]');
  if (target) target.innerHTML = `<p class="message error" role="alert">${escapeHtml(text)}</p>`;
}

async function api(url, options = {}, expectJson = true) {
  const response = await fetch(url, { credentials: 'include', ...options, headers: { Accept: 'application/json', ...(options.headers || {}) } });
  if (!response.ok) throw new Error(`HTTP ${response.status}`);
  if (!expectJson || response.status === 204) return null;
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
