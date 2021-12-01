CREATE TABLE link(
    id BIGSERIAL PRIMARY KEY,
    title TEXT NOT NULL,
    source TEXT NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE
);

CREATE TABLE nested_link(
    id BIGSERIAL PRIMARY KEY,
    content TEXT NOT NULL,
    source_id BIGINT NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE,
    updated_at TIMESTAMP WITH TIME ZONE,

    FOREIGN KEY (source_id) REFERENCES link(id)
);