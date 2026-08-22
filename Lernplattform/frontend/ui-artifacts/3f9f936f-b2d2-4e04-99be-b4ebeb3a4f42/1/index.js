const moduleId = '3f9f936f-b2d2-4e04-99be-b4ebeb3a4f42';
window.__LP_UI_MODULES__ ??= {};
window.__LP_UI_MODULES__[moduleId] = {
  mount(host, context) {
    const school = context?.context?.schoolName ?? 'Schule';
    host.innerHTML = `<section class="info-card"><p class="eyebrow">${escapeHtml(school)}</p><h2>Lernen</h2><p>Die Lerninhalte werden in diesem eigenständigen Schüler-Modul geladen. Welche Inhalte sichtbar sind, wird serverseitig über Schul- und Lernkontext bestimmt.</p></section>`;
  },
  unmount(host) { host.replaceChildren(); }
};
function escapeHtml(value) { return String(value).replace(/[&<>"']/g, c => ({'&':'&amp;','<':'&lt;','>':'&gt;','"':'&quot;',"'":'&#39;'}[c])); }
