CREATE TABLE prestador_servicos (
                                    id_prestador_servicos TEXT UNIQUE NOT NULL,
                                    nome TEXT NOT NULL,
                                    email TEXT UNIQUE NOT NULL,
                                    password TEXT NOT NULL,
                                    profissao TEXT NOT NULL,
                                    telefone TEXT NOT NULL,
                                    role TEXT NOT NULL,
                                    CONSTRAINT pk_prestador_servicos
                                        PRIMARY KEY(id_prestador_servicos)
);

CREATE TABLE fotos (
                       id_foto TEXT UNIQUE NOT NULL,
                       caminho_foto TEXT NOT NULL,
                       prestador_servicos_id_prestador_servicos TEXT NOT NULL,
                       CONSTRAINT pk_fotos
                           PRIMARY KEY (id_foto),
                       CONSTRAINT fk_fotos
                           FOREIGN KEY (prestador_servicos_id_prestador_servicos)
                               REFERENCES prestador_servicos (id_prestador_servicos)
);

CREATE TABLE videos (
                        id_video TEXT UNIQUE NOT NULL,
                        caminho_video TEXT NOT NULL,
                        prestador_servicos_id_prestador_servicos TEXT NOT NULL,
                        CONSTRAINT pk_videos
                            PRIMARY KEY (id_video),
                        CONSTRAINT fk_videos
                            FOREIGN KEY (prestador_servicos_id_prestador_servicos)
                                REFERENCES prestador_servicos (id_prestador_servicos)
);