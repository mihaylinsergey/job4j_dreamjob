CREATE TABLE candidates (
   id SERIAL PRIMARY KEY,
   name TEXT,
   description TEXT,
   created DATE,
   visible boolean,
   city_id int,
   photo BYTEA
);