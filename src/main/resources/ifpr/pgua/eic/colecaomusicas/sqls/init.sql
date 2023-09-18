use musicas;

CREATE TABLE IF NOT EXISTS OOIIGeneros (
    id int NOT NULL AUTO_INCREMENT,
    nome varchar(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS OOIIArtistas (
    id int NOT NULL AUTO_INCREMENT,
    nome varchar(255) NOT NULL,
    contato varchar(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS OOIIMusicas (
    id int NOT NULL AUTO_INCREMENT,
    nome varchar(255) NOT NULL,
    duracao int NOT NULL,
    anoLancamento int NOT NULL,
    artistaId int NOT NULL,
    generoId int NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (artistaId) REFERENCES OOIIArtistas(id),
    FOREIGN KEY (generoId) REFERENCES OOIIGeneros(id)
);

CREATE TABLE IF NOT EXISTS OOIIPlaylist (
    id int NOT NULL AUTO_INCREMENT,
    nome varchar(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS OOIIPlaylistMusicas (
    id int NOT NULL AUTO_INCREMENT,
    idMusica int NOT NULL,
    idPlaylist int NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (idMusica) REFERENCES OOIIMusicas(id),
    FOREIGN KEY (idPlaylist) REFERENCES OOIIPlaylist(id)
);