CREATE TABLE IF NOT EXISTS post (
   id SERIAL PRIMARY KEY,
   name TEXT,
   description TEXT,
   created DATE,
   visible boolean,
   city_id int
);

CREATE TABLE IF NOT EXISTS candidates (
   id SERIAL PRIMARY KEY,
   name TEXT,
   description TEXT,
   created DATE,
   visible boolean,
   city_id int,
   photo BYTEA
);