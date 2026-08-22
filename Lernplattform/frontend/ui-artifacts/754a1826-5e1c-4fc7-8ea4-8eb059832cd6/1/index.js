const moduleId = '754a1826-5e1c-4fc7-8ea4-8eb059832cd6';
window.__LP_UI_MODULES__ ??= {};

window.__LP_UI_MODULES__[moduleId] = {
  async mount(host, context) {
    const school = context?.context;
    if (!school?.schoolSlug) {
      host.innerHTML = message('Kein Schulkontext ausgewählt.');
      return;
    }
    host.innerHTML = `<section class="info-card"><p class="eyebrow">${escapeHtml(school.schoolName)}</p><h2>Schüler</h2><p>Schüler werden geladen …</p></section>`;
    try {
      const [students, classes] = await Promise.all([
        api(`/api/v1/schools/${encodeURIComponent(school.schoolSlug)}/students`),
        api(`/api/v1/schools/${encodeURIComponent(school.schoolSlug)}/classes`)
      ]);
      render(host, school, students, classes);
    } catch {
      host.innerHTML = message('Die Schüler konnten nicht geladen werden.');
    }
  },
  unmount(host) { host.replaceChildren(); }
};

function render(host, school, students, classes) {
  host.innerHTML = `<section class="runtime-feature">
    <p class="eyebrow">${escapeHtml(school.schoolName)}</p>
    <h2>Schüler</h2>
    ${students.length ? `<div class="runtime-grid">${students.map(student => renderStudent(student, classes)).join('')}</div>` : '<section class="info-card"><p>Aktuell sind keine Schüler dieser Schule zugeordnet.</p></section>'}
  </section>`;

  host.querySelectorAll('[data-move-student]').forEach(button => {
    button.addEventListener('click', async () => {
      const card = button.closest('[data-student-card]');
      const select = card?.querySelector('[data-target-class]');
      const targetClassId = select?.value;
      if (!targetClassId || targetClassId === button.dataset.currentClass) return;
      button.disabled = true;
      clearCardMessage(card);
      try {
        await api(`/api/v1/schools/${encodeURIComponent(school.schoolSlug)}/students/${encodeURIComponent(button.dataset.moveStudent)}/move-class`, {
          method: 'POST',
          headers: { 'Content-Type': 'application/json', Accept: 'application/json' },
          body: JSON.stringify({ targetClassId })
        });
        const [freshStudents, freshClasses] = await Promise.all([
          api(`/api/v1/schools/${encodeURIComponent(school.schoolSlug)}/students`),
          api(`/api/v1/schools/${encodeURIComponent(school.schoolSlug)}/classes`)
        ]);
        render(host, school, freshStudents, freshClasses);
      } catch {
        button.disabled = false;
        showCardMessage(card, 'Der Klassenwechsel konnte nicht durchgeführt werden.');
      }
    });
  });
}

function renderStudent(student, classes) {
  const current = student.className ? `${student.className}${student.gradeLevel ? ` · Jahrgang ${student.gradeLevel}` : ''}` : 'Keine aktive Klasse';
  return `<article class="info-card runtime-card" data-student-card>
    <p class="eyebrow">${escapeHtml(current)}</p>
    <h3>${escapeHtml(student.displayName)}</h3>
    <label>Zielklasse
      <select data-target-class>
        <option value="">Klasse auswählen</option>
        ${classes.map(item => `<option value="${escapeHtml(item.id)}" ${item.id === student.classId ? 'selected' : ''}>${escapeHtml(item.name)} · Jahrgang ${escapeHtml(item.gradeLevel)}</option>`).join('')}
      </select>
    </label>
    <div class="review-actions">
      <button class="button primary" type="button" data-move-student="${escapeHtml(student.id)}" data-current-class="${escapeHtml(student.classId ?? '')}">Klasse wechseln</button>
    </div>
    <p class="privacy-note">Die bisherige Klassenzuordnung bleibt historisch erhalten.</p>
    <div data-card-message></div>
  </article>`;
}

async function api(url, options = {}) {
  const response = await fetch(url, { credentials: 'include', ...options, headers: { Accept: 'application/json', ...(options.headers || {}) } });
  if (!response.ok) throw new Error(`HTTP ${response.status}`);
  if (response.status === 204) return null;
  return response.json();
}

function showCardMessage(card, text) {
  const target = card?.querySelector('[data-card-message]');
  if (target) target.innerHTML = `<p class="message error" role="alert">${escapeHtml(text)}</p>`;
}
function clearCardMessage(card) {
  const target = card?.querySelector('[data-card-message]');
  if (target) target.replaceChildren();
}
function message(text) { return `<section class="info-card"><p class="message error" role="alert">${escapeHtml(text)}</p></section>`; }
function escapeHtml(value) { return String(value ?? '').replace(/[&<>"']/g, c => ({'&':'&amp;','<':'&lt;','>':'&gt;','"':'&quot;',"'":'&#39;'}[c])); }
