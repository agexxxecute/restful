package org.example.ServletTest;


import DTO.SelectionInDTO;
import DTO.SelectionUpdateDTO;
import Service.Impl.SelectionServiceImpl;
import Servlet.SelectionServlet;
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
public class SelectionServletTest {

    private static SelectionServiceImpl mockSelectionService;
    @InjectMocks
    private static SelectionServlet selectionServlet;
    private static SelectionServiceImpl oldInstance;
    @Mock
    private static HttpServletRequest mockRequest;
    @Mock
    private static HttpServletResponse mockResponse;
    @Mock
    private BufferedReader mockReader;

    private static void setMock(SelectionServiceImpl mock){
        try{
            Field instance = SelectionServiceImpl.class.getDeclaredField("instance");
            instance.setAccessible(true);
            oldInstance = (SelectionServiceImpl) instance.get(instance);
            instance.set(instance, mock);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @BeforeAll
    static void beforeMock(){
        mockSelectionService = Mockito.mock(SelectionServiceImpl.class);
        setMock(mockSelectionService);
        selectionServlet = new SelectionServlet();
    }

    @BeforeEach
    void setUpMock() throws IOException {
        Mockito.doReturn(new PrintWriter(Writer.nullWriter())).when(mockResponse).getWriter();
    }

    @AfterAll
    static void afterAll() throws Exception {
        Field instance = SelectionServiceImpl.class.getDeclaredField("instance");
        instance.setAccessible(true);
        instance.set(instance, oldInstance);
    }

    @AfterEach
    void tearDownMock() {
        Mockito.reset(mockSelectionService);
    }

    @Test
    void doGetAll() throws IOException, ServletException {
        Mockito.doReturn("selecion/all").when(mockRequest).getPathInfo();
        selectionServlet.doGet(mockRequest, mockResponse);
        Mockito.verify(mockSelectionService).findAll();
    }

    @Test
    void doGetById() throws IOException, ServletException {
        Mockito.doReturn("selecion/3").when(mockRequest).getPathInfo();
        selectionServlet.doGet(mockRequest, mockResponse);
        Mockito.verify(mockSelectionService).findById(Mockito.anyInt());
    }

    @Test
    void doDelete() throws IOException, ServletException {
        Mockito.doReturn("selecion/4").when(mockRequest).getPathInfo();
        selectionServlet.doDelete(mockRequest, mockResponse);
        Mockito.verify(mockSelectionService).delete(Mockito.anyInt());
    }

    @Test
    void doPost() throws IOException, ServletException {
        String expectedName = "name";

        Mockito.doReturn(mockReader).when(mockRequest).getReader();
        Mockito.doReturn("{\"name\":\"" + expectedName + "\"" +
                "}", null).when(mockReader).readLine();

        selectionServlet.doPost(mockRequest, mockResponse);

        ArgumentCaptor<SelectionInDTO> argumentCaptor = ArgumentCaptor.forClass(SelectionInDTO.class);
        Mockito.verify(mockSelectionService).add(argumentCaptor.capture());

        SelectionInDTO selectionInDTO = argumentCaptor.getValue();
        Assertions.assertEquals(expectedName, selectionInDTO.getName());
    }

    @Test
    void doPut() throws IOException, ServletException {
        String expectedName = "name";

        Mockito.doReturn(mockReader).when(mockRequest).getReader();
        Mockito.doReturn("{\"id\": 3" +
                ",\"name\":\"" + expectedName + "\"" +
                ",\"movies\": [4]" +
                "}", null).when(mockReader).readLine();

        selectionServlet.doPut(mockRequest, mockResponse);
        ArgumentCaptor<SelectionUpdateDTO> argumentCaptor = ArgumentCaptor.forClass(SelectionUpdateDTO.class);
        Mockito.verify(mockSelectionService).update(argumentCaptor.capture());

        SelectionUpdateDTO selectionUpdateDTO = argumentCaptor.getValue();
        Assertions.assertEquals(expectedName, selectionUpdateDTO.getName());
    }

}
