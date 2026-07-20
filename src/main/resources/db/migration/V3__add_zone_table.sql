CREATE TABLE zone
(
    id         BIGSERIAL PRIMARY KEY,
    name       VARCHAR(255) NOT NULL,
    zone_class VARCHAR(50),
    status     VARCHAR(20),
    shape      geometry(Polygon, 4326)
);

CREATE INDEX zone_shape_gix ON zone USING GIST (shape);