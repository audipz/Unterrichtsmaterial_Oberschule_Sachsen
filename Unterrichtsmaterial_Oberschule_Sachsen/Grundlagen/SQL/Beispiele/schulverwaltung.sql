CREATE TABLE Klasse (
    KlasseID INTEGER PRIMARY KEY,
    Bezeichnung VARCHAR(10) NOT NULL UNIQUE
);

CREATE TABLE Schueler (
    SchuelerID INTEGER PRIMARY KEY,
    Schuelernummer VARCHAR(20) NOT NULL UNIQUE,
    Name VARCHAR(100) NOT NULL,
    KlasseID INTEGER,
    FOREIGN KEY (KlasseID)
        REFERENCES Klasse(KlasseID)
);

CREATE TABLE Kurs (
    KursID INTEGER PRIMARY KEY,
    Bezeichnung VARCHAR(100) NOT NULL
);

CREATE TABLE Teilnahme (
    SchuelerID INTEGER,
    KursID INTEGER,
    PRIMARY KEY (SchuelerID, KursID),
    FOREIGN KEY (SchuelerID)
        REFERENCES Schueler(SchuelerID),
    FOREIGN KEY (KursID)
        REFERENCES Kurs(KursID)
);

INSERT INTO Klasse (KlasseID, Bezeichnung)
VALUES (1, '9a');

INSERT INTO Schueler
    (SchuelerID, Schuelernummer, Name, KlasseID)
VALUES
    (1001, 'S-1001', 'Mia', 1),
    (1002, 'S-1002', 'Tim', 1);

SELECT Schueler.Name, Klasse.Bezeichnung
FROM Schueler
JOIN Klasse
  ON Schueler.KlasseID = Klasse.KlasseID;
