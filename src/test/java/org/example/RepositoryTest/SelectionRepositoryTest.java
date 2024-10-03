package org.example.RepositoryTest;


import Entity.Director;
import Entity.Movie;
import Entity.Selection;
import Repository.Impl.SelectionRepositoryImpl;
import Repository.SelectionRepository;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Testcontainers
@Tag("DockerRequired")
public class SelectionRepositoryTest {
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

    public static SelectionRepository selectionRepository;
    private static JdbcDatabaseDelegate jdbcDatabaseDelegate;

    @BeforeAll
    static void beforeAll() {
        container.start();
        selectionRepository = SelectionRepositoryImpl.getInstance();
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
    void findALl(){
        int expectedSize = 3;
        int resultSize = selectionRepository.findAll().size();
        Assertions.assertEquals(expectedSize, resultSize);

    }

    @Test
    void findById(){
        int selectedId = 1;
        String expectedName = "Anime";
        Selection selection = selectionRepository.findSelectionById(selectedId);
        Assertions.assertEquals(expectedName, selection.getName());
    }

    @Test
    void add(){
        String expectedName = "NewSelection";
        Selection selection = new Selection(null, expectedName, null);
        Optional<Selection> addedSelection = Optional.ofNullable(selectionRepository.addSelection(selection));
        Assertions.assertTrue(addedSelection.isPresent());
        Assertions.assertEquals(expectedName, addedSelection.get().getName());
    }

    @Test
    void addWithMovies(){
        String expectedName = "NewSelection";
        List<Movie> movies = new ArrayList<>();
        movies.add(new Movie(1, "Brother", 1997, false, new Director(1, "Alexey", "Balabanov"), null));
        Selection selection = new Selection(null, expectedName, movies);
        Optional<Selection> addedSelection = Optional.ofNullable(selectionRepository.addSelection(selection));
        Assertions.assertTrue(addedSelection.isPresent());
        Assertions.assertEquals(expectedName, addedSelection.get().getName());
        Assertions.assertEquals(selection.getMovies().get(0).getTitle(), addedSelection.get().getMovies().get(0).getTitle());

    }

    @Test
    void update(){
        int selectedId = 3;
        String newName = "NewSelection";
        Selection selection = new Selection(selectedId, newName, null);
        Selection oldSelection = selectionRepository.findSelectionById(selectedId);
        Selection newSelection = selectionRepository.updateSelection(selection);
        Assertions.assertNotEquals(newSelection.getName(), oldSelection.getName());
        Assertions.assertEquals(newName, newSelection.getName());
    }


    @Test
    void delete(){
        int selectedId = 4;
        Assertions.assertTrue(selectionRepository.deleteSelection(selectedId));
        Assertions.assertNull(selectionRepository.findSelectionById(selectedId));
    }

    @Test
    void findUnexisted(){
        Selection selection = selectionRepository.findSelectionById(0);
    }
}
