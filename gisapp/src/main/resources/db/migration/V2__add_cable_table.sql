CREATE TABLE cable
(
    id            BIGSERIAL PRIMARY KEY,
    name          VARCHAR(255),
    start_node_id BIGINT NOT NULL,
    end_node_id   BIGINT NOT NULL,
    path          geometry(LineString, 4326),
    CONSTRAINT fk_start_node FOREIGN KEY (start_node_id) REFERENCES node (id),
    CONSTRAINT fk_end_node FOREIGN KEY (end_node_id) REFERENCES node (id)
);

CREATE INDEX cable_path_gix ON cable USING GIST (path);