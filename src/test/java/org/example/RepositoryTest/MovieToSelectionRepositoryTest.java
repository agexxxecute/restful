package org.example.RepositoryTest;

import Entity.MovieToSelection;
import Repository.Impl.MovieToSelectionRepositoryImpl;
import Repository.MovieToSelectionRepository;
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

@Testcontainers
@Tag("DockerRequired")
public class MovieToSelectionRepositoryTest {
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

    public static MovieToSelectionRepository movieToSelectionRepository;
    private static JdbcDatabaseDelegate jdbcDatabaseDelegate;

    @BeforeAll
    static void beforeAll() {
        container.start();
        movieToSelectionRepository = MovieToSelectionRepositoryImpl.getInstance();
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
    void findByMovieId(){
        int movieId = 1;
        int expectedSize = 2;
        Assertions.assertEquals(expectedSize, movieToSelectionRepository.findByMovieId(movieId).size());
    }

    @Test
    void findBySelectionId(){
        int selectionId = 1;
        int expectedSize = 3;
        Assertions.assertEquals(expectedSize, movieToSelectionRepository.findBySelectionId(selectionId).size());
    }

    @Test
    void add(){
        int movieId = 1;
        int selectionId = 1;
        MovieToSelection movieToSelection = new MovieToSelection(null, movieId, selectionId);
        movieToSelection = movieToSelectionRepository.addMovieToSelection(movieToSelection);
        Assertions.assertEquals(movieId, movieToSelection.getMovieId());
        Assertions.assertEquals(selectionId, movieToSelection.getSelectionId());
    }

    @Test
    void deleteByMovieId(){
        int movieId = 1;
        boolean result = movieToSelectionRepository.deleteByMovieId(movieId);
        Assertions.assertTrue(result);
        Assertions.assertTrue(movieToSelectionRepository.findByMovieId(movieId).isEmpty());
    }

    @Test
    void deleteBySelectionId(){
        int selectionId = 1;
        boolean result = movieToSelectionRepository.deleteBySelectionId(selectionId);
        Assertions.assertTrue(result);
        Assertions.assertTrue(movieToSelectionRepository.findBySelectionId(selectionId).isEmpty());
    }

    @Test
    void findByUnexistedMovie(){
        Assertions.assertTrue(movieToSelectionRepository.findByMovieId(0).isEmpty());
    }

    @Test
    void findByUnexistedSelection(){
        Assertions.assertTrue(movieToSelectionRepository.findBySelectionId(0).isEmpty());
    }
}
