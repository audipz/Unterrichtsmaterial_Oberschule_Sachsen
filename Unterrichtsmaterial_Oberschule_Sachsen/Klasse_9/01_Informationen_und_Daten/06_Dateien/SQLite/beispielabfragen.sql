-- 1. Alle Veranstaltungen chronologisch
SELECT titel, datum, beginn, kategorie
FROM veranstaltungen
ORDER BY datum, beginn;

-- 2. Veranstaltungen mit Raum und Kapazität
SELECT v.titel, r.name AS raum, r.kapazitaet
FROM veranstaltungen v
JOIN raeume r ON r.raum_id = v.raum_id
ORDER BY r.kapazitaet DESC;

-- 3. Bezahlte Tickets je Veranstaltung
SELECT v.titel, COUNT(*) AS bezahlte_tickets
FROM tickets t
JOIN veranstaltungen v ON v.veranstaltung_id = t.veranstaltung_id
WHERE t.status = 'bezahlt'
GROUP BY v.veranstaltung_id, v.titel
ORDER BY bezahlte_tickets DESC;

-- 4. Auslastung über die vorbereitete View
SELECT * FROM veranstaltung_auslastung
ORDER BY auslastung_prozent DESC;

-- 5. Umsatz je Artikel
SELECT * FROM umsatz_nach_artikel
ORDER BY umsatz DESC;

-- 6. Besucherinnen und Besucher aus Dresden
SELECT vorname, nachname, geburtsjahr
FROM besucher
WHERE ort = 'Dresden'
ORDER BY nachname, vorname;

-- 7. Anzahl Einsätze je Helfer
SELECT * FROM helfer_einsaetze
ORDER BY anzahl_einsaetze DESC, helfer;

-- 8. Veranstaltungen ohne kostenlose Teilnahme
SELECT titel, eintrittspreis
FROM veranstaltungen
WHERE eintrittspreis > 0
ORDER BY eintrittspreis DESC;

-- 9. Durchschnittlicher Ticketpreis
SELECT ROUND(AVG(preis), 2) AS durchschnittspreis
FROM tickets
WHERE status = 'bezahlt';

-- 10. Kategorien mit mehr als einer Veranstaltung
SELECT kategorie, COUNT(*) AS anzahl
FROM veranstaltungen
GROUP BY kategorie
HAVING COUNT(*) > 1;
