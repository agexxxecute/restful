package org.example.ServletTest;

import DTO.MovieInDTO;
import DTO.MovieUpdateDTO;
import Service.Impl.MovieServiceImpl;
import Servlet.MovieServlet;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;


@ExtendWith(
        MockitoExtension.class
)

public class MovieServletTest {

    private static MovieServiceImpl mockMovieService;
    @InjectMocks
    private static MovieServlet movieServlet;
    private static MovieServiceImpl oldInstance;
    @Mock
    private HttpServletRequest mockRequest;
    @Mock
    private HttpServletResponse mockResponse;
    @Mock
    private BufferedReader mockReader;

    //private static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:16.4");

    private static void setMock(MovieServiceImpl mock){
        try {
            Field instance = MovieServiceImpl.class.getDeclaredField("instance");
            instance.setAccessible(true);
            oldInstance = (MovieServiceImpl) instance.get(instance);
            instance.set(instance,mock);
        } catch (Exception e){
            e.printStackTrace();
        }
    }


//    @BeforeAll
//    public static void setUp() throws SQLException, IOException {
//        postgreSQLContainer.start();
//        Connection connection = DriverManager.getConnection(postgreSQLContainer.getJdbcUrl(), postgreSQLContainer.getUsername(), postgreSQLContainer.getPassword());
//        ScriptRunner scriptRunner = new ScriptRunner(connection);
//        scriptRunner.setDelimiter(";");
//        Reader scriptReader = Resources.getResourceAsReader("init.sql");
//        scriptRunner.runScript(scriptReader);
//    }

    @BeforeAll
    static void beforeMock(){
        mockMovieService = Mockito.mock(MovieServiceImpl.class);
        setMock(mockMovieService);
        movieServlet = new MovieServlet();
    }

    @BeforeEach
    void setUpMock() throws IOException{
        Mockito.doReturn(new PrintWriter(Writer.nullWriter())).when(mockResponse).getWriter();
    }

//    @AfterAll
//    public static void tearDown() {
//        postgreSQLContainer.stop();
//    }

    @AfterAll
    static void afterAll() throws Exception {
        Field instance = MovieServiceImpl.class.getDeclaredField("instance");
        instance.setAccessible(true);
        instance.set(instance, oldInstance);
    }

    @AfterEach
    void tearDownMock() {
        Mockito.reset(mockMovieService);
    }

//    @Test
//    public void test2() throws SQLException, IOException {
//        Connection connection = DriverManager.getConnection(postgreSQLContainer.getJdbcUrl(), postgreSQLContainer.getUsername(), postgreSQLContainer.getPassword());
//        Statement statement = connection.createStatement();
//
//
//
//        statement.execute("INSERT INTO movie(title) VALUES ('a')");
//        ResultSet resultSet = statement.executeQuery("SELECT * FROM movie");
//        assert resultSet.next();
//    }

    @Test
    void doGetAll() throws IOException, ServletException {
        Mockito.doReturn("movie/all").when(mockRequest).getPathInfo();
        movieServlet.doGet(mockRequest, mockResponse);
        Mockito.verify(mockMovieService).findAll();
    }

    @Test
    void doGetById() throws IOException, ServletException {
        Mockito.doReturn("movie/15").when(mockRequest).getPathInfo();
        movieServlet.doGet(mockRequest, mockResponse);
        Mockito.verify(mockMovieService).findById(Mockito.anyInt());
    }

    @Test
    void doGetSerials() throws IOException, ServletException {
        Mockito.doReturn("movie/serials").when(mockRequest).getPathInfo();
        movieServlet.doGet(mockRequest, mockResponse);
        Mockito.verify(mockMovieService).findAllSerials();
    }

    @Test
    void doPost() throws IOException, ServletException {
        String expectedTitle = "New Movie";
        int expectedYear = 0;
        boolean expectedIsSerial = false;
        int expectedDirectiorId = 5;

        Mockito.doReturn(mockReader).when(mockRequest).getReader();
        Mockito.doReturn("{\"title\":\"" + expectedTitle + "\"" +
                ",\"year\":\"" + expectedYear + "\"" +
                ",\"isSerial\":\"" + expectedIsSerial + "\"" +
                ",\"director_id\":\"" + expectedDirectiorId + "\"" +
                "}", null).when(mockReader).readLine();

        movieServlet.doPost(mockRequest, mockResponse);

        ArgumentCaptor<MovieInDTO> argumentCaptor = ArgumentCaptor.forClass(MovieInDTO.class);
        Mockito.verify(mockMovieService).add(argumentCaptor.capture());

        MovieInDTO result = argumentCaptor.getValue();
        Assertions.assertEquals(expectedTitle, result.getTitle());
        Assertions.assertEquals(expectedYear, result.getYear());
        Assertions.assertEquals(expectedIsSerial, result.isSerial());
        Assertions.assertEquals(expectedDirectiorId, result.getDirector_id());

    }

    @Test
    void doPut() throws IOException, ServletException {
        String expectedTitle = "New Movie";
        int expectedYear = 0;
        boolean expectedIsSerial = false;
        int expectedDirectiorId = 5;

        Mockito.doReturn(mockReader).when(mockRequest).getReader();
        Mockito.doReturn("{\"id\": 15" +
                ",\"title\":\"" + expectedTitle + "\"" +
                ",\"year\":\"" + expectedYear + "\"" +
                ",\"isSerial\":\"" + expectedIsSerial + "\"" +
                ",\"director_id\":\"" + expectedDirectiorId + "\"" +
                ",\"selections\": [2]" +
                "}", null).when(mockReader).readLine();

        movieServlet.doPut(mockRequest, mockResponse);

        ArgumentCaptor<MovieUpdateDTO> argumentCaptor = ArgumentCaptor.forClass(MovieUpdateDTO.class);
        Mockito.verify(mockMovieService).update(argumentCaptor.capture());

        MovieUpdateDTO result = argumentCaptor.getValue();
        Assertions.assertEquals(expectedTitle, result.getTitle());
        Assertions.assertEquals(expectedYear, result.getYear());
        Assertions.assertEquals(expectedIsSerial, result.isSerial());
        Assertions.assertEquals(expectedDirectiorId, result.getDirector_id());

    }

    @Test
    void doDelete() throws IOException, ServletException {
        Mockito.doReturn("movie/15").when(mockRequest).getPathInfo();
        movieServlet.doDelete(mockRequest, mockResponse);
        Mockito.verify(mockMovieService).delete(Mockito.anyInt());
    }




}
