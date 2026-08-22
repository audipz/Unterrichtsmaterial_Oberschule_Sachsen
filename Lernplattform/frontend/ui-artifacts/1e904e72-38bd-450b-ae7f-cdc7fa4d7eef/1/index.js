const moduleId = '1e904e72-38bd-450b-ae7f-cdc7fa4d7eef';
window.__LP_UI_MODULES__ ??= {};
window.__LP_UI_MODULES__[moduleId] = {
  mount(host, context) {
    const school = context?.context?.schoolName ?? 'Schule';
    host.innerHTML = `<section class="info-card"><p class="eyebrow">${escapeHtml(school)}</p><h2>Schulverwaltung</h2><p>Dieser Bereich wird nur für Lehrer mit schulbezogener SCHOOL_ADMIN-Berechtigung ausgeliefert.</p></section>`;
  },
  unmount(host) { host.replaceChildren(); }
};
function escapeHtml(value) { return String(value).replace(/[&<>"']/g, c => ({'&':'&amp;','<':'&lt;','>':'&gt;','"':'&quot;',"'":'&#39;'}[c])); }
