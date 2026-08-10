INSERT INTO Schueler
    (SchuelerID, Schuelernummer, Name, KlasseID)
VALUES
    (1003, 'S-1003', 'Lea', 1);

SELECT *
FROM Schueler
WHERE SchuelerID = 1003;

UPDATE Schueler
SET Name = 'Lea Schneider'
WHERE SchuelerID = 1003;

DELETE FROM Schueler
WHERE SchuelerID = 1003;
