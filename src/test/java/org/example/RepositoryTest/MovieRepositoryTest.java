package org.example.RepositoryTest;

import Entity.Director;
import Entity.Movie;
import Repository.DirectorRepository;
import Repository.Impl.DirectorRepositoryImpl;
import Repository.Impl.MovieRepositoryImpl;
import Repository.MovieRepository;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.ext.ScriptUtils;
import org.testcontainers.jdbc.JdbcDatabaseDelegate;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

@Testcontainers
@Tag("DockerRequired")
public class MovieRepositoryTest {
    private static final int containerPort = 5432;
    private static final int localPort = 5432;

    @Container
    public static PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:13-alpine")
            .withDatabaseName("postgres")
            .withUsername("postgres")
            .withPassword("password")
            .withExposedPorts(containerPort)
            .withCreateContainerCmdModifier(cmd -> cmd.withHostConfig(
                    new HostConfig().withPortBindings(new PortBinding(Ports.Binding.bindPort(localPort), new ExposedPort(containerPort)))
            ))
            .withInitScript("init.sql");

    public static MovieRepository movieRepository;
    private static JdbcDatabaseDelegate jdbcDatabaseDelegate;

    @BeforeAll
    static void beforeAll() {
        container.start();
        movieRepository = MovieRepositoryImpl.getInstance();
        jdbcDatabaseDelegate = new JdbcDatabaseDelegate(container, "");
    }

    @AfterAll
    static void afterAll() {
        container.stop();
    }

    @BeforeEach
    void setUp() {
        ScriptUtils.runInitScript(jdbcDatabaseDelegate, "init.sql");
    }

    @Test
    void findALl() {
        int expectedSize = 8;
        Assertions.assertEquals(expectedSize, movieRepository.findAll().size());
    }

    @Test
    void findAllSerials(){
        int expectedSize = 2;
        Assertions.assertEquals(expectedSize, movieRepository.getAllSerials().size());
    }

    @Test
    void findById(){
        int movieId = 1;
        String expectedTitle = "Brother";
        int expectedYear = 1997;
        boolean expectedIsSerial = false;
        int expectedDirectorId = 1;
        String expectedDirectorFirstName = "Alexey";
        String expectedDirectorLastName = "Balabanov";
        Movie movie = movieRepository.getMovieById(movieId);
        Assertions.assertNotNull(movie);
        Assertions.assertEquals(expectedTitle, movie.getTitle());
        Assertions.assertEquals(expectedYear, movie.getYear());
        Assertions.assertEquals(expectedIsSerial, movie.isSerial());
        Assertions.assertEquals(expectedDirectorId, movie.getDirector().getId());
        Assertions.assertEquals(expectedDirectorFirstName, movie.getDirector().getFirstName());
        Assertions.assertEquals(expectedDirectorLastName, movie.getDirector().getLastName());
    }

    @Test
    void add(){
        String title = "NewTitle";
        int year = 2000;
        boolean isSerial = false;
        Movie newMovie = new Movie(null, title, year, isSerial, null, null);
        newMovie = movieRepository.addMovie(newMovie);
        Optional<Movie> movie = Optional.ofNullable(movieRepository.getMovieById(newMovie.getId()));
        Assertions.assertTrue(movie.isPresent());
        Assertions.assertEquals(newMovie.getTitle(), movie.get().getTitle());
        Assertions.assertEquals(newMovie.getYear(), movie.get().getYear());
        Assertions.assertEquals(newMovie.isSerial(), movie.get().isSerial());

    }

    @Test
    void update(){
        int movieId = 1;
        String title = "NewTitle";
        int year = 2000;
        boolean isSerial = true;
        Director director = new Director(2, "Hayao", "Miyazaki");
        Movie movie = new Movie(movieId, title, year, isSerial, director, null);
        Movie oldMovie = movieRepository.getMovieById(movieId);
        Movie newMovie = movieRepository.updateMovie(movie);
        Assertions.assertNotEquals(newMovie.getTitle(), oldMovie.getTitle());
        Assertions.assertNotEquals(newMovie.getYear(), oldMovie.getYear());
        Assertions.assertNotEquals(newMovie.isSerial(), oldMovie.isSerial());
        Assertions.assertNotEquals(newMovie.getDirector().getId(), oldMovie.getDirector().getId());
        Assertions.assertNotEquals(newMovie.getDirector().getFirstName(), oldMovie.getDirector().getFirstName());
        Assertions.assertNotEquals(newMovie.getDirector().getLastName(), oldMovie.getDirector().getLastName());

    }

    @Test
    void delete(){
        int movieId = 9;
        boolean result = movieRepository.deleteMovie(movieId);
        Assertions.assertTrue(result);
        Assertions.assertNull(movieRepository.getMovieById(movieId));
    }

    @Test
    void findUnexisted(){
        Movie movie = movieRepository.getMovieById(0);
        Assertions.assertNull(movie);
    }
}