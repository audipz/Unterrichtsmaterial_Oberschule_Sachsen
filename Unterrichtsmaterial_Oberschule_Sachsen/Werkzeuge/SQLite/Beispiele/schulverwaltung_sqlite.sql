PRAGMA foreign_keys = ON;

CREATE TABLE Klasse (
    KlasseID INTEGER PRIMARY KEY,
    Bezeichnung TEXT NOT NULL UNIQUE
) STRICT;

CREATE TABLE Schueler (
    SchuelerID INTEGER PRIMARY KEY,
    Schuelernummer TEXT NOT NULL UNIQUE,
    Name TEXT NOT NULL,
    KlasseID INTEGER,
    FOREIGN KEY (KlasseID)
        REFERENCES Klasse(KlasseID)
        ON DELETE SET NULL
) STRICT;

CREATE TABLE Kurs (
    KursID INTEGER PRIMARY KEY,
    Bezeichnung TEXT NOT NULL
) STRICT;

CREATE TABLE Teilnahme (
    SchuelerID INTEGER NOT NULL,
    KursID INTEGER NOT NULL,
    PRIMARY KEY (SchuelerID, KursID),
    FOREIGN KEY (SchuelerID)
        REFERENCES Schueler(SchuelerID)
        ON DELETE CASCADE,
    FOREIGN KEY (KursID)
        REFERENCES Kurs(KursID)
        ON DELETE CASCADE
) STRICT;

INSERT INTO Klasse (Bezeichnung)
VALUES ('9a'), ('9b');

INSERT INTO Schueler
    (Schuelernummer, Name, KlasseID)
VALUES
    ('S-1001', 'Mia', 1),
    ('S-1002', 'Tim', 1),
    ('S-1003', 'Lea', 2);

INSERT INTO Kurs (Bezeichnung)
VALUES ('Informatik'), ('Robotik');

INSERT INTO Teilnahme (SchuelerID, KursID)
VALUES
    (1, 1),
    (2, 1),
    (3, 2);
