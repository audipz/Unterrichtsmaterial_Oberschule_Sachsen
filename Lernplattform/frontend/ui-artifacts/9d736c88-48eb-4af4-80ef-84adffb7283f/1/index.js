const MODULE_ID = '9d736c88-48eb-4af4-80ef-84adffb7283f';
const API_BASE = '/api/v1/system-admin';

async function json(url, options = {}) {
  const response = await fetch(url, { credentials: 'include', ...options });
  if (!response.ok) throw new Error(`HTTP ${response.status}`);
  if (response.status === 204) return null;
  return response.json();
}

async function csrf() {
  return json(`${API_BASE}/auth/csrf`);
}

async function post(url, body) {
  const token = await csrf();
  return json(url, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      [token.headerName]: token.token
    },
    body: JSON.stringify(body ?? {})
  });
}

function el(tag, className, text) {
  const node = document.createElement(tag);
  if (className) node.className = className;
  if (text !== undefined) node.textContent = text;
  return node;
}

function formatDate(value) {
  return new Intl.DateTimeFormat('de-DE', { dateStyle: 'medium', timeStyle: 'short' }).format(new Date(value));
}

function registrationCard(item, reload) {
  const card = el('article', 'info-card registration-card');
  const heading = el('div', 'registration-heading');
  const title = el('div');
  title.append(el('p', 'eyebrow', `${item.schoolType} · ${item.federalState}`), el('h2', '', item.schoolName));
  heading.append(title, el('span', 'status-badge', 'E-Mail bestätigt'));
  card.append(heading);

  const details = el('dl', 'registration-details');
  for (const [label, value] of [
    ['Ort', item.city],
    ['Kontakt', item.contactEmail],
    ['Eingereicht', formatDate(item.submittedAt)],
    ['Bestätigt', formatDate(item.emailVerifiedAt)]
  ]) {
    const row = el('div');
    row.append(el('dt', '', label), el('dd', '', value));
    details.append(row);
  }
  card.append(details);

  if (item.schoolWebsite) {
    const p = el('p');
    const a = el('a', '', 'Schulwebseite öffnen');
    a.href = item.schoolWebsite;
    a.target = '_blank';
    a.rel = 'noopener noreferrer';
    p.append(a);
    card.append(p);
  }

  const actions = el('div', 'review-actions');
  const approve = el('button', 'button primary', 'Freigeben');
  approve.type = 'button';
  approve.addEventListener('click', async () => {
    approve.disabled = true;
    try {
      await post(`${API_BASE}/school-registrations/${item.id}/approve`, {});
      await reload();
    } finally {
      approve.disabled = false;
    }
  });

  const reject = el('button', 'button', 'Ablehnen');
  reject.type = 'button';
  reject.addEventListener('click', () => showRejectForm(card, item, reload));
  actions.append(approve, reject);
  card.append(actions);
  return card;
}

function showRejectForm(card, item, reload) {
  card.querySelector('.runtime-reject-form')?.remove();
  const form = el('form', 'reject-form runtime-reject-form');
  const label = el('label', '', 'Ablehnungsgrund');
  const textarea = el('textarea');
  textarea.rows = 4;
  textarea.maxLength = 1000;
  textarea.required = true;
  label.append(textarea);

  const actions = el('div', 'review-actions');
  const cancel = el('button', 'button', 'Abbrechen');
  cancel.type = 'button';
  cancel.addEventListener('click', () => form.remove());
  const confirm = el('button', 'button primary', 'Ablehnung bestätigen');
  confirm.type = 'submit';
  actions.append(cancel, confirm);
  form.append(label, actions);
  form.addEventListener('submit', async (event) => {
    event.preventDefault();
    const reason = textarea.value.trim();
    if (!reason) return;
    confirm.disabled = true;
    try {
      await post(`${API_BASE}/school-registrations/${item.id}/reject`, { reason });
      await reload();
    } finally {
      confirm.disabled = false;
    }
  });
  card.append(form);
  textarea.focus();
}

async function mount(host) {
  host.replaceChildren();
  const status = el('p', 'lead', 'Anträge werden geladen …');
  host.append(status);

  async function reload() {
    host.replaceChildren(status);
    status.textContent = 'Anträge werden geladen …';
    try {
      const registrations = await json(`${API_BASE}/school-registrations`);
      host.replaceChildren();
      if (!registrations.length) {
        const empty = el('section', 'info-card empty-state');
        empty.append(el('h2', '', 'Keine offenen Anträge'), el('p', '', 'Aktuell gibt es keine bestätigten Schulregistrierungen zur Prüfung.'));
        host.append(empty);
        return;
      }
      const list = el('section', 'registration-list');
      registrations.forEach((item) => list.append(registrationCard(item, reload)));
      host.append(list);
    } catch {
      status.textContent = 'Die Registrierungsanträge konnten nicht geladen werden.';
      host.replaceChildren(status);
    }
  }

  await reload();
}

window.__LP_UI_MODULES__ ??= {};
window.__LP_UI_MODULES__[MODULE_ID] = {
  mount,
  unmount(host) { host.replaceChildren(); }
};
