CREATE TABLE IF NOT EXISTS OOIIGeneros (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nome varchar(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS OOIIArtistas (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nome varchar(255) NOT NULL,
    contato varchar(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS OOIIMusicas (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nome varchar(255) NOT NULL,
    duracao int NOT NULL,
    anoLancamento int NOT NULL,
    artistaId int NOT NULL,
    generoId int NOT NULL,
    FOREIGN KEY (artistaId) REFERENCES OOIIArtistas(id),
    FOREIGN KEY (generoId) REFERENCES OOIIGeneros(id)
    
);

CREATE TABLE IF NOT EXISTS OOIIPlaylist (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    nome varchar(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS OOIIPlaylistMusicas (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    playlistId int NOT NULL,
    musicaId int NOT NULL,
    FOREIGN KEY (playlistId) REFERENCES OOIIPlaylist(id),
    FOREIGN KEY (musicaId) REFERENCES OOIIMusicas(id)
);