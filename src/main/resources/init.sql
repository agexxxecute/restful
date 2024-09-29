DROP TABLE IF EXISTS movie_selection;
DROP TABLE IF EXISTS movie;
DROP TABLE IF EXISTS director;
DROP TABLE IF EXISTS selection;

CREATE TABLE director(
                         Id SERIAL PRIMARY KEY,
                         firstname VARCHAR(50),
                         lastname VARCHAR(50)
);




CREATE TABLE selection(
                          Id SERIAL PRIMARY KEY,
                          name VARCHAR(50)
);

CREATE TABLE movie(
                      Id SERIAL PRIMARY KEY,
                      title VARCHAR(50),
                      year INTEGER,
                      isserial BOOLEAN,
                      director_id INTEGER,
                      FOREIGN KEY (director_id) REFERENCES director(id)
);

CREATE TABLE movie_selection(
                                Id SERIAL PRIMARY KEY,
                                movie_id INTEGER,
                                selection_id INTEGER,
                                FOREIGN KEY (movie_id) REFERENCES movie(id),
                                FOREIGN KEY (selection_id) references selection(id)
);



INSERT INTO director (firstname, lastname) VALUES ('Alexey', 'Balabanov'),
                                                  ('Hayao', 'Miyazaki'),
                                                  ('Petr','Tochilin'),
                                                  ('Stas','Dmitriev'),
                                                  ('David','Fincher');

INSERT INTO selection (name) VALUES ('Anime'),
                                    ('Russian'),
                                    ('Classical');

INSERT INTO movie (title, year, isserial, director_id) VALUES ('Brother', 1997, false, 1),
                                                              ('Brother 2', 2000, false, 1),
                                                              ('Spirited Away', 2001, false, 2),
                                                              ('Princess Mononoke', 1997, false, 2),
                                                              ('Univer', 2008, true, 3),
                                                              ('War', 2002, false, 1),
                                                              ('Cyberslav', 2024, true, 4),
                                                              ('Fight Club', 1999, false, 5);

INSERT INTO movie_selection (movie_id, selection_id) VALUES (1, 2),
                                                            (1,3),
                                                            (2,2),
                                                            (2,3),
                                                            (3,1),
                                                            (3,3),
                                                            (4,1),
                                                            (5,2),
                                                            (6,2),
                                                            (7,1),
                                                            (7,2),
                                                            (8,3);
