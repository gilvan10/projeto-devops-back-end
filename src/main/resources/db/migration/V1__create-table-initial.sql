CREATE TABLE prestador_servicos (
   id_prestador_servicos TEXT UNIQUE NOT NULL,
   nome TEXT NOT NULL,
   CONSTRAINT pk_prestador_servicos
     PRIMARY KEY(id_prestador_servicos)
);

