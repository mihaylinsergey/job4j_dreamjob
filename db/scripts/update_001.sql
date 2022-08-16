CREATE TABLE if not exists post (
                                    post_id SERIAL PRIMARY KEY,
                                    post_name TEXT,
                                    post_description TEXT,
                                    post_visible bool,
                                    post_city_id int
);
CREATE TABLE IF NOT EXISTS candidate (
                                         can_id SERIAL PRIMARY KEY,
                                         can_name TEXT,
                                         can_description TEXT,
                                         can_visible bool,
                                         can_city_id int,
                                         can_photo bytea
)