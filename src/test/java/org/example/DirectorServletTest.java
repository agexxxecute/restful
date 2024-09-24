package org.example;

import DTO.DirectorInDTO;
import DTO.DirectorOutDTO;
import Service.DirectorService;
import Service.Impl.DirectorServiceImpl;
import Service.Impl.MovieServiceImpl;
import Servlet.DirectorServlet;
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
            e.printStackTrace();
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
    void doDelete() throws IOException, ServletException {
        Mockito.doReturn("director/5").when(mockRequest).getPathInfo();
        directorServlet.doDelete(mockRequest, mockResponse);
        Mockito.verify(mockDirectorService).delete(Mockito.anyInt());
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
    void doPut() throws IOException, ServletException {
        String exprectedFirstName = "firstName";
        String exprectedLastName = "lastName";

        Mockito.doReturn(mockReader).when(mockRequest).getReader();
        Mockito.doReturn("{\"id\": 15" +
                ",\"firstName\":\"" + exprectedFirstName + "\"" +
                ",\"lastName\":\"" + exprectedLastName + "\"" +
                "}", null).when(mockReader).readLine();

        directorServlet.doPut(mockRequest, mockResponse);

        ArgumentCaptor<DirectorOutDTO> argumentCaptor = ArgumentCaptor.forClass(DirectorOutDTO.class);
        Mockito.verify(mockDirectorService).update(argumentCaptor.capture());

        DirectorOutDTO directorOutDTO = argumentCaptor.getValue();
        Assertions.assertEquals(exprectedFirstName, directorOutDTO.getFirstName());
        Assertions.assertEquals(exprectedLastName, directorOutDTO.getLastName());
    }


}
