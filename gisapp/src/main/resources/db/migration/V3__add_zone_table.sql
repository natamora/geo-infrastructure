CREATE TABLE zone (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    area geometry(Polygon, 4326)
);

CREATE INDEX zone_area_gix ON zone USING GIST (area);