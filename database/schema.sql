-- ============================================================
-- EcoPower Monitor - Script de criacao do banco de dados
-- SGBD: PostgreSQL
-- ============================================================

-- Remove as tabelas na ordem correta (dependencias primeiro)
DROP TABLE IF EXISTS leituras;
DROP TABLE IF EXISTS alertas;
DROP TABLE IF EXISTS dispositivos;
DROP TABLE IF EXISTS usuarios;

-- ------------------------------------------------------------
-- Tabela: usuarios
-- Armazena o cadastro do morador, a tarifa e a meta de consumo
-- ------------------------------------------------------------
CREATE TABLE usuarios (
    id            SERIAL       PRIMARY KEY,
    nome          VARCHAR(100) NOT NULL,
    email         VARCHAR(100) UNIQUE NOT NULL,
    tipo_usuario  VARCHAR(20)  NOT NULL DEFAULT 'CONSUMIDOR',
    tarifa_kwh    DECIMAL(10,4) NOT NULL DEFAULT 0,
    meta_kwh      DECIMAL(10,2) NOT NULL DEFAULT 0
);

-- ------------------------------------------------------------
-- Tabela: dispositivos
-- Aparelhos vinculados ao usuario (relacao 1:N com usuarios)
-- ------------------------------------------------------------
CREATE TABLE dispositivos (
    id             SERIAL       PRIMARY KEY,
    usuario_id     INT          NOT NULL,
    nome           VARCHAR(100) NOT NULL,
    localizacao    VARCHAR(50),
    potencia_watts DECIMAL(10,2) NOT NULL,
    CONSTRAINT fk_dispositivo_usuario
        FOREIGN KEY (usuario_id) REFERENCES usuarios (id)
        ON DELETE CASCADE
);

-- ------------------------------------------------------------
-- Tabela: leituras
-- Medicoes de consumo de cada dispositivo (relacao 1:N com dispositivos)
-- ------------------------------------------------------------
CREATE TABLE leituras (
    id             SERIAL      PRIMARY KEY,
    dispositivo_id INT         NOT NULL,
    data_hora      TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    consumo_kwh    DECIMAL(10,4) NOT NULL,
    CONSTRAINT fk_leitura_dispositivo
        FOREIGN KEY (dispositivo_id) REFERENCES dispositivos (id)
        ON DELETE CASCADE
);

-- ------------------------------------------------------------
-- Tabela: alertas
-- Alertas emitidos quando a meta de consumo e aproximada/ultrapassada
-- ------------------------------------------------------------
CREATE TABLE alertas (
    id          SERIAL      PRIMARY KEY,
    usuario_id  INT         NOT NULL,
    tipo        VARCHAR(30) NOT NULL,
    mensagem    TEXT        NOT NULL,
    data_hora   TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_alerta_usuario
        FOREIGN KEY (usuario_id) REFERENCES usuarios (id)
        ON DELETE CASCADE
);

-- ------------------------------------------------------------
-- Indices para acelerar as consultas de relatorio mais comuns
-- ------------------------------------------------------------
CREATE INDEX idx_leituras_dispositivo ON leituras (dispositivo_id);
CREATE INDEX idx_leituras_data        ON leituras (data_hora);
CREATE INDEX idx_dispositivos_usuario ON dispositivos (usuario_id);

-- ------------------------------------------------------------
-- Dados de exemplo (opcional - pode remover antes de entregar)
-- ------------------------------------------------------------
INSERT INTO usuarios (nome, email, tipo_usuario, tarifa_kwh, meta_kwh)
VALUES ('Morador Exemplo', 'morador@exemplo.com', 'CONSUMIDOR', 0.9500, 200.00);

INSERT INTO dispositivos (usuario_id, nome, localizacao, potencia_watts)
VALUES
    (1, 'Geladeira',      'Cozinha',   150.00),
    (1, 'Ar-condicionado','Quarto',   1400.00),
    (1, 'Chuveiro',       'Banheiro', 5500.00);
