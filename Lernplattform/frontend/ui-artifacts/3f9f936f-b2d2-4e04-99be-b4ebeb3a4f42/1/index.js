const moduleId = '3f9f936f-b2d2-4e04-99be-b4ebeb3a4f42';
window.__LP_UI_MODULES__ ??= {};

window.__LP_UI_MODULES__[moduleId] = {
  async mount(host, appContext) {
    const school = appContext?.context;
    if (!school?.schoolSlug) {
      host.innerHTML = message('Kein Schulkontext ausgewählt.');
      return;
    }

    host.innerHTML = `<section class="info-card"><p class="eyebrow">${escapeHtml(school.schoolName)}</p><h2>Lernen</h2><p class="lead">Lerninhalte werden geladen …</p></section>`;
    try {
      const data = await api(`/api/v1/schools/${encodeURIComponent(school.schoolSlug)}/learning?language=de-DE`);
      render(host, data);
    } catch {
      host.innerHTML = message('Die Lerninhalte konnten nicht geladen werden.');
    }
  },
  unmount(host) { host.replaceChildren(); }
};

function render(host, data) {
  const context = data.context ?? {};
  const manifest = data.manifest ?? {};
  const items = Array.isArray(data.items) ? data.items : [];

  if (!items.length) {
    host.innerHTML = `<section class="info-card">
      <p class="eyebrow">${escapeHtml(context.schoolName ?? 'Schule')}</p>
      <h2>Lernen</h2>
      <p>Für deinen aktuellen Lernkontext sind noch keine veröffentlichten Inhalte verfügbar.</p>
      <p class="privacy-note">${context.gradeLevel ? `Jahrgang ${escapeHtml(context.gradeLevel)} · ` : ''}${escapeHtml(label(context.schoolType))} · ${escapeHtml(label(context.federalState))}</p>
    </section>`;
    return;
  }

  const byId = new Map(items.map(item => [item.id, item]));
  const children = new Map();
  for (const item of items) {
    const parent = item.parentId ?? null;
    if (!children.has(parent)) children.set(parent, []);
    children.get(parent).push(item);
  }

  host.innerHTML = `<section class="runtime-feature learning-feature">
    <header class="info-card learning-header">
      <p class="eyebrow">${escapeHtml(context.schoolName ?? 'Schule')}</p>
      <h2>${escapeHtml(manifest.title ?? 'Lernen')}</h2>
      <p class="privacy-note">${context.gradeLevel ? `Jahrgang ${escapeHtml(context.gradeLevel)} · ` : ''}${escapeHtml(label(context.schoolType))} · ${escapeHtml(label(context.federalState))}</p>
    </header>
    <div class="learning-layout">
      <nav class="info-card learning-toc" aria-label="Lerninhalte">
        <h3>Inhalte</h3>
        ${renderTree(children, null)}
      </nav>
      <div class="learning-content" data-learning-content>
        ${renderContent(items[0])}
      </div>
    </div>
  </section>`;

  host.querySelectorAll('[data-content-id]').forEach(button => {
    button.addEventListener('click', () => {
      const item = byId.get(button.dataset.contentId);
      const target = host.querySelector('[data-learning-content]');
      if (!item || !target) return;
      host.querySelectorAll('[data-content-id]').forEach(node => node.classList.remove('active'));
      button.classList.add('active');
      target.innerHTML = renderContent(item);
      target.scrollIntoView({ behavior: 'smooth', block: 'start' });
    });
  });
  host.querySelector('[data-content-id]')?.classList.add('active');
}

function renderTree(children, parentId) {
  const entries = children.get(parentId) ?? [];
  if (!entries.length) return '';
  return `<ul class="learning-tree">${entries.map(item => `<li>
    <button type="button" data-content-id="${escapeHtml(item.id)}">${escapeHtml(item.title)}</button>
    ${renderTree(children, item.id)}
  </li>`).join('')}</ul>`;
}

function renderContent(item) {
  if (!item) return '<section class="info-card"><p>Kein Inhalt ausgewählt.</p></section>';
  return `<article class="info-card learning-article">
    <p class="eyebrow">${escapeHtml(contentTypeLabel(item.type))}</p>
    <h3>${escapeHtml(item.title)}</h3>
    <div class="markdown-body">${renderMarkdown(item.bodyMarkdown ?? '')}</div>
  </article>`;
}

function renderMarkdown(source) {
  const escaped = escapeHtml(source).replace(/\r\n/g, '\n');
  const lines = escaped.split('\n');
  const out = [];
  let list = false;
  let code = false;
  let codeLines = [];

  const closeList = () => { if (list) { out.push('</ul>'); list = false; } };
  for (const raw of lines) {
    const line = raw.trimEnd();
    if (line.startsWith('```')) {
      closeList();
      if (code) {
        out.push(`<pre><code>${codeLines.join('\n')}</code></pre>`);
        codeLines = [];
      }
      code = !code;
      continue;
    }
    if (code) { codeLines.push(line); continue; }
    if (/^###\s+/.test(line)) { closeList(); out.push(`<h5>${inline(line.replace(/^###\s+/, ''))}</h5>`); continue; }
    if (/^##\s+/.test(line)) { closeList(); out.push(`<h4>${inline(line.replace(/^##\s+/, ''))}</h4>`); continue; }
    if (/^#\s+/.test(line)) { closeList(); out.push(`<h3>${inline(line.replace(/^#\s+/, ''))}</h3>`); continue; }
    if (/^[-*]\s+/.test(line)) {
      if (!list) { out.push('<ul>'); list = true; }
      out.push(`<li>${inline(line.replace(/^[-*]\s+/, ''))}</li>`);
      continue;
    }
    closeList();
    if (!line.trim()) { out.push(''); continue; }
    out.push(`<p>${inline(line)}</p>`);
  }
  closeList();
  if (code && codeLines.length) out.push(`<pre><code>${codeLines.join('\n')}</code></pre>`);
  return out.join('\n');
}

function inline(value) {
  return value
    .replace(/`([^`]+)`/g, '<code>$1</code>')
    .replace(/\*\*([^*]+)\*\*/g, '<strong>$1</strong>')
    .replace(/\*([^*]+)\*/g, '<em>$1</em>');
}

async function api(url) {
  const response = await fetch(url, { credentials: 'include', headers: { Accept: 'application/json' } });
  if (!response.ok) throw new Error(`HTTP ${response.status}`);
  return response.json();
}

function contentTypeLabel(value) {
  return ({ TOPIC: 'Thema', SECTION: 'Abschnitt', EXERCISE: 'Übung', WORKSHEET: 'Arbeitsblatt', REFERENCE: 'Nachschlagewerk' })[value] ?? value ?? 'Inhalt';
}
function label(value) { return String(value ?? '').replaceAll('_', ' ').toLowerCase().replace(/(^|\s)\p{L}/gu, c => c.toUpperCase()); }
function message(text) { return `<section class="info-card"><p class="message error" role="alert">${escapeHtml(text)}</p></section>`; }
function escapeHtml(value) { return String(value ?? '').replace(/[&<>"']/g, c => ({ '&': '&amp;', '<': '&lt;', '>': '&gt;', '"': '&quot;', "'": '&#39;' }[c])); }
