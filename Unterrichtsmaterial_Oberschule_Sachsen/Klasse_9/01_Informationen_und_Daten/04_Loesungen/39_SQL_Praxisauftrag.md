# Lösung – SQL Praxisauftrag

Eine mögliche Lösung:

```sql
CREATE TABLE Schueler (
    SchuelerID INTEGER PRIMARY KEY,
    Schuelernummer VARCHAR(20) NOT NULL UNIQUE,
    Name VARCHAR(100) NOT NULL
);

CREATE TABLE Kurs (
    KursID INTEGER PRIMARY KEY,
    Bezeichnung VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE Teilnahme (
    SchuelerID INTEGER NOT NULL,
    KursID INTEGER NOT NULL,
    PRIMARY KEY (SchuelerID, KursID),
    FOREIGN KEY (SchuelerID)
        REFERENCES Schueler(SchuelerID),
    FOREIGN KEY (KursID)
        REFERENCES Kurs(KursID)
);
```

Beispieldaten:

```sql
INSERT INTO Schueler
    (SchuelerID, Schuelernummer, Name)
VALUES
    (1, 'S-001', 'Mia'),
    (2, 'S-002', 'Tim'),
    (3, 'S-003', 'Lea');

INSERT INTO Kurs
    (KursID, Bezeichnung)
VALUES
    (1, 'Informatik'),
    (2, 'Robotik');

INSERT INTO Teilnahme
    (SchuelerID, KursID)
VALUES
    (1,1),
    (2,1),
    (2,2),
    (3,2);
```

CRUD-Beispiele:

```sql
SELECT * FROM Schueler;

UPDATE Schueler
SET Name = 'Lea Schneider'
WHERE SchuelerID = 3;

DELETE FROM Teilnahme
WHERE SchuelerID = 2
  AND KursID = 2;
```

Die Tabelle `Teilnahme` benötigt zwei Fremdschlüssel, weil jeder Datensatz genau einen Schüler mit genau einem Kurs verbindet.
