CREATE TABLE IF NOT EXISTS director(
    Id SERIAL PRIMARY KEY,
    firstname VARCHAR(50),
    lastname VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS movie(
    Id SERIAL PRIMARY KEY,
    title VARCHAR(50),
    year INTEGER,
    isserial BOOLEAN,
    director_id INTEGER,
    FOREIGN KEY (director_id) REFERENCES director(id)
);

CREATE TABLE IF NOT EXISTS selection(
    Id SERIAL PRIMARY KEY,
    name VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS movie_selection(
    Id SERIAL PRIMARY KEY,
    movie_id INTEGER,
    selection_id INTEGER,
    FOREIGN KEY (movie_id) REFERENCES movie(id),
    FOREIGN KEY (selection_id) references selection(id)
);