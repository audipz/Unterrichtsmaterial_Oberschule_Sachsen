const moduleId = '754a1826-5e1c-4fc7-8ea4-8eb059832cd6';
window.__LP_UI_MODULES__ ??= {};
window.__LP_UI_MODULES__[moduleId] = {
  mount(host, context) {
    const school = context?.context?.schoolName ?? 'Schule';
    host.innerHTML = `<section class="info-card"><p class="eyebrow">${escapeHtml(school)}</p><h2>Schüler</h2><p>Schülerverwaltung und Schul-/Klassenwechsel werden in diesem getrennten Laufzeitmodul umgesetzt.</p></section>`;
  },
  unmount(host) { host.replaceChildren(); }
};
function escapeHtml(value) { return String(value).replace(/[&<>"']/g, c => ({'&':'&amp;','<':'&lt;','>':'&gt;','"':'&quot;',"'":'&#39;'}[c])); }
