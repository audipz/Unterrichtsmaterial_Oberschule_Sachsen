CREATE TABLE anmeldungen (
    anmeldung_id INTEGER PRIMARY KEY,
    helfer_id INTEGER NOT NULL,
    veranstaltung_id INTEGER NOT NULL,
    aufgabe TEXT NOT NULL,
    schichtbeginn TEXT NOT NULL,
    schichtende TEXT NOT NULL,
    FOREIGN KEY (helfer_id) REFERENCES helfer(helfer_id),
    FOREIGN KEY (veranstaltung_id) REFERENCES veranstaltungen(veranstaltung_id)
);
CREATE TABLE artikel (
    artikel_id INTEGER PRIMARY KEY,
    bezeichnung TEXT NOT NULL UNIQUE,
    kategorie TEXT NOT NULL,
    verkaufspreis REAL NOT NULL CHECK (verkaufspreis >= 0),
    einkaufspreis REAL NOT NULL CHECK (einkaufspreis >= 0)
);
CREATE TABLE besucher (
    besucher_id INTEGER PRIMARY KEY,
    vorname TEXT NOT NULL,
    nachname TEXT NOT NULL,
    geburtsjahr INTEGER NOT NULL CHECK (geburtsjahr BETWEEN 1940 AND 2020),
    ort TEXT NOT NULL,
    email TEXT NOT NULL UNIQUE
);
CREATE TABLE helfer (
    helfer_id INTEGER PRIMARY KEY,
    vorname TEXT NOT NULL,
    nachname TEXT NOT NULL,
    rolle TEXT NOT NULL,
    telefon TEXT NOT NULL,
    verfuegbar_ab TEXT NOT NULL
);
CREATE TABLE raeume (
    raum_id INTEGER PRIMARY KEY,
    name TEXT NOT NULL UNIQUE,
    gebaeude TEXT NOT NULL,
    kapazitaet INTEGER NOT NULL CHECK (kapazitaet > 0),
    barrierefrei INTEGER NOT NULL DEFAULT 0 CHECK (barrierefrei IN (0,1))
);
CREATE TABLE tickets (
    ticket_id INTEGER PRIMARY KEY,
    besucher_id INTEGER NOT NULL,
    veranstaltung_id INTEGER NOT NULL,
    kaufdatum TEXT NOT NULL,
    preis REAL NOT NULL CHECK (preis >= 0),
    status TEXT NOT NULL CHECK (status IN ('bezahlt','reserviert','storniert')),
    FOREIGN KEY (besucher_id) REFERENCES besucher(besucher_id),
    FOREIGN KEY (veranstaltung_id) REFERENCES veranstaltungen(veranstaltung_id)
);
CREATE TABLE veranstaltungen (
    veranstaltung_id INTEGER PRIMARY KEY,
    titel TEXT NOT NULL,
    kategorie TEXT NOT NULL,
    datum TEXT NOT NULL,
    beginn TEXT NOT NULL,
    ende TEXT NOT NULL,
    raum_id INTEGER NOT NULL,
    eintrittspreis REAL NOT NULL CHECK (eintrittspreis >= 0),
    zielgruppe TEXT NOT NULL,
    FOREIGN KEY (raum_id) REFERENCES raeume(raum_id)
);
CREATE TABLE verkaeufe (
    verkauf_id INTEGER PRIMARY KEY,
    artikel_id INTEGER NOT NULL,
    veranstaltung_id INTEGER NOT NULL,
    zeitpunkt TEXT NOT NULL,
    menge INTEGER NOT NULL CHECK (menge > 0),
    FOREIGN KEY (artikel_id) REFERENCES artikel(artikel_id),
    FOREIGN KEY (veranstaltung_id) REFERENCES veranstaltungen(veranstaltung_id)
);
CREATE INDEX idx_veranstaltungen_datum ON veranstaltungen(datum);
CREATE INDEX idx_tickets_veranstaltung ON tickets(veranstaltung_id);
CREATE INDEX idx_tickets_besucher ON tickets(besucher_id);
CREATE INDEX idx_anmeldungen_veranstaltung ON anmeldungen(veranstaltung_id);
CREATE INDEX idx_verkaeufe_artikel ON verkaeufe(artikel_id);
CREATE INDEX idx_verkaeufe_veranstaltung ON verkaeufe(veranstaltung_id);
CREATE VIEW veranstaltung_auslastung AS
SELECT
    v.veranstaltung_id,
    v.titel,
    v.datum,
    r.name AS raum,
    r.kapazitaet,
    SUM(CASE WHEN t.status = 'bezahlt' THEN 1 ELSE 0 END) AS verkaufte_tickets,
    ROUND(
        100.0 * SUM(CASE WHEN t.status = 'bezahlt' THEN 1 ELSE 0 END) / r.kapazitaet,
        1
    ) AS auslastung_prozent
FROM veranstaltungen v
JOIN raeume r ON r.raum_id = v.raum_id
LEFT JOIN tickets t ON t.veranstaltung_id = v.veranstaltung_id
GROUP BY v.veranstaltung_id, v.titel, v.datum, r.name, r.kapazitaet;
CREATE VIEW umsatz_nach_artikel AS
SELECT
    a.bezeichnung,
    a.kategorie,
    SUM(v.menge) AS verkaufte_menge,
    ROUND(SUM(v.menge * a.verkaufspreis), 2) AS umsatz,
    ROUND(SUM(v.menge * (a.verkaufspreis - a.einkaufspreis)), 2) AS deckungsbeitrag
FROM verkaeufe v
JOIN artikel a ON a.artikel_id = v.artikel_id
GROUP BY a.artikel_id, a.bezeichnung, a.kategorie;
CREATE VIEW helfer_einsaetze AS
SELECT
    h.helfer_id,
    h.vorname || ' ' || h.nachname AS helfer,
    h.rolle,
    COUNT(a.anmeldung_id) AS anzahl_einsaetze
FROM helfer h
LEFT JOIN anmeldungen a ON a.helfer_id = h.helfer_id
GROUP BY h.helfer_id, h.vorname, h.nachname, h.rolle;
