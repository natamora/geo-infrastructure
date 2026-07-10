CREATE TABLE zone (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    shape geometry(Polygon, 4326)
);

CREATE INDEX zone_shape_gix ON zone USING GIST (shape);