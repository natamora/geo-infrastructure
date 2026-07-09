CREATE EXTENSION IF NOT EXISTS postgis;

CREATE TABLE node (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    -- SRID 4326 (WGS 84)
    location GEOMETRY(Point, 4326)
);

-- Spatial index
CREATE INDEX idx_node_location ON node USING GIST (location);