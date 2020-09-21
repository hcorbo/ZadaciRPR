BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "korisnik" (
    "id"    INTEGER,
	"ime"	TEXT,
	"prezime"	TEXT,
	"email"	TEXT,
	"korisnicko_ime"	TEXT,
	"lozinka"	TEXT,
	"slika"    TEXT
);
INSERT INTO "korisnik" VALUES (1, 'Vedran','Ljubović','vljubovic@etf.unsa.ba','vedranlj','test', 'resources/slicice/face-smile.png');
INSERT INTO "korisnik" VALUES (2, 'Amra','Delić','adelic@etf.unsa.ba','amrad','test', 'resources/slicice/face-smile.png');
INSERT INTO "korisnik" VALUES (3, 'Tarik','Sijerčić','tsijercic1@etf.unsa.ba','tariks','test', 'resources/slicice/face-smile.png');
INSERT INTO "korisnik" VALUES (4, 'Rijad','Fejzić','rfejzic1@etf.unsa.ba','rijadf','test', 'resources/slicice/face-smile.png');
COMMIT;
