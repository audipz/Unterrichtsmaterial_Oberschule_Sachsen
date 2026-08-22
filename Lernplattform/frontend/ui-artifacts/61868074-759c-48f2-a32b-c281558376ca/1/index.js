const moduleId = '61868074-759c-48f2-a32b-c281558376ca';
window.__LP_UI_MODULES__ ??= {};
window.__LP_UI_MODULES__[moduleId] = {
  mount(host, context) {
    const school = context?.context?.schoolName ?? 'Schule';
    host.innerHTML = `<section class="info-card"><p class="eyebrow">${escapeHtml(school)}</p><h2>Klassen</h2><p>Die Klassenverwaltung wird als eigenständiges Laufzeitmodul geladen. Die fachlichen Endpunkte werden im nächsten Ausbauschritt angebunden.</p></section>`;
  },
  unmount(host) { host.replaceChildren(); }
};
function escapeHtml(value) { return String(value).replace(/[&<>"']/g, c => ({'&':'&amp;','<':'&lt;','>':'&gt;','"':'&quot;',"'":'&#39;'}[c])); }
