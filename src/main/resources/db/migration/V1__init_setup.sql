CREATE EXTENSION IF NOT EXISTS postgis;

CREATE TABLE node
(
    id                BIGSERIAL PRIMARY KEY,
    name              VARCHAR(255) NOT NULL,
    type              VARCHAR(50),
    status            VARCHAR(20),
    installation_date DATE,
    -- SRID 4326 (WGS 84)
    shape             GEOMETRY(Point, 4326)
);

-- Spatial index
CREATE INDEX idx_node_shape ON node USING GIST (shape);