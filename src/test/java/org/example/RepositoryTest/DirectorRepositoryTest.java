package org.example.RepositoryTest;

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

@Testcontainers
@Tag("DockerRequired")
public class DirectorRepositoryTest {
    private static final String INIT_SQL = "resources/init.sql";
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
            .withInitScript(INIT_SQL);

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
        ScriptUtils.runInitScript(jdbcDatabaseDelegate, INIT_SQL);
    }

    @Test
    void findALl(){
        int expectedSize = 5;
        int resultSize = directorRepository.findAll().size();
        Assertions.assertEquals(expectedSize, resultSize);
    }


}
