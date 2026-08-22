const moduleId = 'd6dedfef-5cc7-4be0-9ab1-d8d40ce1950d';
window.__LP_UI_MODULES__ ??= {};
window.__LP_UI_MODULES__[moduleId] = {
  mount(host, context) {
    const school = context?.context?.schoolName ?? 'Schule';
    host.innerHTML = `<section class="info-card"><p class="eyebrow">${escapeHtml(school)}</p><h2>Lernstände</h2><p>Hier wird später ausschließlich der Bearbeitungs- und Vollständigkeitsstand der Schüler angezeigt, nicht deren Lösungsinhalt.</p></section>`;
  },
  unmount(host) { host.replaceChildren(); }
};
function escapeHtml(value) { return String(value).replace(/[&<>"']/g, c => ({'&':'&amp;','<':'&lt;','>':'&gt;','"':'&quot;',"'":'&#39;'}[c])); }
