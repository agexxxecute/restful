package org.example.ServletTest;

import DTO.DirectorInDTO;
import DTO.DirectorOutDTO;
import DTO.DirectorUpdateDTO;
import Service.Impl.DirectorServiceImpl;
import Servlet.DirectorServlet;
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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.lang.reflect.Field;

@ExtendWith(
        MockitoExtension.class
)
public class DirectorServletTest {

    private static DirectorServiceImpl mockDirectorService;
    @InjectMocks
    private static DirectorServlet directorServlet;
    private static DirectorServiceImpl oldInstance;
    @Mock
    private HttpServletRequest mockRequest;
    @Mock
    private HttpServletResponse mockResponse;
    @Mock
    private BufferedReader mockReader;

    private static void setMock(DirectorServiceImpl mock){
        try{
            Field instance = DirectorServiceImpl.class.getDeclaredField("instance");
            instance.setAccessible(true);
            oldInstance = (DirectorServiceImpl) instance.get(instance);
            instance.set(instance, mock);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeAll
    static void beforeMock(){
        mockDirectorService = Mockito.mock(DirectorServiceImpl.class);
        setMock(mockDirectorService);
        directorServlet = new DirectorServlet();
    }

    @BeforeEach
    void setUpMock() throws IOException {
        Mockito.doReturn(new PrintWriter(Writer.nullWriter())).when(mockResponse).getWriter();
    }

    @AfterAll
    static void afterAll() throws Exception {
        Field instance = DirectorServiceImpl.class.getDeclaredField("instance");
        instance.setAccessible(true);
        instance.set(instance, oldInstance);
    }

    @AfterEach
    void tearDownMock() {
        Mockito.reset(mockDirectorService);
    }

    @Test
    void doGetAll() throws IOException, ServletException {
        Mockito.doReturn("director/all").when(mockRequest).getPathInfo();
        directorServlet.doGet(mockRequest, mockResponse);
        Mockito.verify(mockDirectorService).findAll();
    }

    @Test
    void doGetById() throws IOException, ServletException {
        Mockito.doReturn("director/5").when(mockRequest).getPathInfo();
        directorServlet.doGet(mockRequest, mockResponse);
        Mockito.verify(mockDirectorService).findById(Mockito.anyInt());
    }

    @Test
    void doGetByUnexistedId() throws IOException, ServletException {
        Mockito.doReturn("director/0").when(mockRequest).getPathInfo();
        directorServlet.doGet(mockRequest, mockResponse);
        Mockito.verify(mockResponse).setStatus(HttpServletResponse.SC_NOT_FOUND);
    }

    @Test
    void doDelete() throws IOException, ServletException {
        Mockito.doReturn("director/5").when(mockRequest).getPathInfo();
        directorServlet.doDelete(mockRequest, mockResponse);
        Mockito.verify(mockDirectorService).delete(Mockito.anyInt());
    }

    @Test
    void doDeleteException() throws IOException, ServletException {
        Mockito.doReturn("director/abc").when(mockRequest).getPathInfo();
        directorServlet.doDelete(mockRequest, mockResponse);
        Mockito.verify(mockResponse).setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Test
    void doPost() throws IOException, ServletException {
        String exprectedFirstName = "firstName";
        String exprectedLastName = "lastName";
        Mockito.doReturn(mockReader).when(mockRequest).getReader();
        Mockito.doReturn("{\"firstName\":\"" + exprectedFirstName + "\"" +
                ",\"lastName\":\"" + exprectedLastName + "\"" +
                "}", null).when(mockReader).readLine();

        directorServlet.doPost(mockRequest, mockResponse);

        ArgumentCaptor<DirectorInDTO> argumentCaptor = ArgumentCaptor.forClass(DirectorInDTO.class);
        Mockito.verify(mockDirectorService).add(argumentCaptor.capture());

        DirectorInDTO directorInDTO = argumentCaptor.getValue();
        Assertions.assertEquals(exprectedFirstName, directorInDTO.getFirstName());
        Assertions.assertEquals(exprectedLastName, directorInDTO.getLastName());
    }

    @Test
    void doPostException() throws IOException, ServletException {
        Mockito.doReturn(mockReader).when(mockRequest).getReader();
        Mockito.doReturn(
                "{\"id\":1}",
                null
        ).when(mockReader).readLine();
        directorServlet.doPost(mockRequest, mockResponse);
        Mockito.verify(mockResponse).setStatus(HttpServletResponse.SC_BAD_REQUEST);

    }

    @Test
    void doPut() throws IOException, ServletException {
        String exprectedFirstName = "firstName";
        String exprectedLastName = "lastName";

        Mockito.doReturn(mockReader).when(mockRequest).getReader();
        Mockito.doReturn("{\"id\": 15" +
                ",\"firstName\":\"" + exprectedFirstName + "\"" +
                ",\"lastName\":\"" + exprectedLastName + "\"" +
                "}", null).when(mockReader).readLine();

        directorServlet.doPut(mockRequest, mockResponse);

        ArgumentCaptor<DirectorUpdateDTO> argumentCaptor = ArgumentCaptor.forClass(DirectorUpdateDTO.class);
        Mockito.verify(mockDirectorService).update(argumentCaptor.capture());

        DirectorUpdateDTO directorUpdateDTO = argumentCaptor.getValue();
        Assertions.assertEquals(exprectedFirstName, directorUpdateDTO.getFirstName());
        Assertions.assertEquals(exprectedLastName, directorUpdateDTO.getLastName());
    }

    @Test
    void doPutException() throws IOException, ServletException {
        Mockito.doReturn(mockReader).when(mockRequest).getReader();
        Mockito.doReturn(
                "{\"id\":1}",
                null
        ).when(mockReader).readLine();
        directorServlet.doPut(mockRequest, mockResponse);
        Mockito.verify(mockResponse).setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }


}
