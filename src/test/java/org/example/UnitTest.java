package org.example;

import Entity.Movie;
import Entity.User;
import Service.MovieService;
import Servlet.MovieServlet;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testcontainers.containers.PostgreSQLContainer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.sql.*;

public class UnitTest {

    private static MovieService mockMovieService;
    @InjectMocks
    private static MovieServlet movieServlet;
    private static MovieService oldInstance;
    @Mock
    private HttpServletRequest mockRequest;
    @Mock
    private HttpServletResponse mockResponse;
    @Mock
    private BufferedReader mockReader;

    private static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:16.4").withDatabaseName("postgres").withUsername("postgres").withPassword("password");

    private static void setMock(MovieService mock){
        try {
            Field instance = MovieService.class.getDeclaredField("instance");
            instance.setAccessible(true);
            oldInstance = (MovieService) instance.get(instance);
            instance.set(instance,mock);
        } catch (Exception e){
            e.printStackTrace();
        }
    }


    @Test
    public void test1() {
        User user1 = new User(1, "a", "b");
        User user2 = new User(2, "a", "d");
        assert user1.getUsername().equals(user2.getUsername());
    }

    @BeforeAll
    public static void setUp() throws SQLException, IOException {
        postgreSQLContainer.start();
        Connection connection = DriverManager.getConnection(postgreSQLContainer.getJdbcUrl(), postgreSQLContainer.getUsername(), postgreSQLContainer.getPassword());
        ScriptRunner scriptRunner = new ScriptRunner(connection);
        scriptRunner.setDelimiter(";");
        Reader scriptReader = Resources.getResourceAsReader("init.sql");
        scriptRunner.runScript(scriptReader);
    }

    @BeforeAll
    static void beforeMock(){
        mockMovieService = Mockito.mock(MovieService.class);
        setMock(mockMovieService);
        movieServlet = new MovieServlet();
    }

    @BeforeEach
    void setUpMock() throws IOException{
        Mockito.doReturn(new PrintWriter(Writer.nullWriter())).when(mockResponse).getWriter();
    }

    @AfterAll
    public static void tearDown() {
        postgreSQLContainer.stop();
    }

    @Test
    public void test2() throws SQLException, IOException {
        Connection connection = DriverManager.getConnection(postgreSQLContainer.getJdbcUrl(), postgreSQLContainer.getUsername(), postgreSQLContainer.getPassword());
        Statement statement = connection.createStatement();



        statement.execute("INSERT INTO movie(title) VALUES ('a')");
        ResultSet resultSet = statement.executeQuery("SELECT * FROM movie");
        assert resultSet.next();
    }

    @Test
    void doGetAll() throws IOException, ServletException {
        Mockito.doReturn("movie/all").when(mockRequest).getPathInfo();
        movieServlet.doGet(mockRequest, mockResponse);
        Mockito.verify(mockMovieService).findAll();

    }


}
