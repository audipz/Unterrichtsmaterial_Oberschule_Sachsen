PRAGMA foreign_keys = ON;

INSERT INTO Schueler
    (Schuelernummer, Name, KlasseID)
VALUES
    ('S-1010', 'Noah', 1);

SELECT *
FROM Schueler
WHERE Schuelernummer = 'S-1010';

UPDATE Schueler
SET Name = 'Noah Schneider'
WHERE Schuelernummer = 'S-1010';

DELETE FROM Schueler
WHERE Schuelernummer = 'S-1010';
