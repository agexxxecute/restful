package org.example.RepositoryTest;

import Entity.Director;
import Repository.DirectorRepository;
import Repository.Impl.DirectorRepositoryImpl;
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
public class DirectorRepositoryTest {
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

    public static DirectorRepository directorRepository;
    private static JdbcDatabaseDelegate jdbcDatabaseDelegate;

    @BeforeAll
    static void beforeAll() {
        container.start();
        directorRepository = DirectorRepositoryImpl.getInstance();
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
    void findAll(){
        int expectedSize = 5;
        int resultSize = directorRepository.findAll().size();
        Assertions.assertEquals(expectedSize, resultSize);
    }

    @Test
    void save(){
        String expectedFirstName = "FirstName";
        String expectedLastName = "LastName";
        Director director = new Director(null, expectedFirstName, expectedLastName);
        director = directorRepository.addDirector(director);
        Optional<Director> directorOptional = Optional.ofNullable(directorRepository.findById(director.getId()));

        Assertions.assertTrue(directorOptional.isPresent());
        Assertions.assertEquals(director.getFirstName(), directorOptional.get().getFirstName());
        Assertions.assertEquals(director.getLastName(), directorOptional.get().getLastName());

    }

    @Test
    void update(){
        String expectedFirstName = "NewFirstName";
        String expectedLastName = "NewLastName";
        Director updateDirector = directorRepository.findById(5);
        Director newDirector = new Director(5, expectedFirstName, expectedLastName);
        String oldFirstName = updateDirector.getFirstName();
        String oldLastName = updateDirector.getLastName();
        Director updatedDirector = directorRepository.updateDirector(newDirector);
        Assertions.assertNotEquals(updatedDirector.getFirstName(), oldFirstName);
        Assertions.assertNotEquals(updatedDirector.getLastName(), oldLastName);
        Assertions.assertEquals(updatedDirector.getFirstName(), expectedFirstName);
        Assertions.assertEquals(updatedDirector.getLastName(), expectedLastName);
    }

    @Test
    void findById(){
        String expectedFirstName = "Alexey";
        String expectedLastName = "Balabanov";
        Director director = directorRepository.findById(1);
        Assertions.assertNotNull(director);
        Assertions.assertEquals(director.getFirstName(), expectedFirstName);
        Assertions.assertEquals(director.getLastName(), expectedLastName);
    }

    @Test
    void delete(){
        int deletingId = 6;
        boolean result = directorRepository.deleteDirectorById(deletingId);
        Assertions.assertTrue(result);
        Assertions.assertNull(directorRepository.findById(deletingId));
    }

    @Test
    void getNotFound(){
        Director director = directorRepository.findById(0);
        Assertions.assertNull(director);
    }



}
