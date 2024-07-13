CREATE TABLE barcode_counter (
    id SERIAL PRIMARY KEY,
    next_barcode_number INTEGER NOT NULL
);

INSERT INTO barcode_counter (next_barcode_number) VALUES (1);
