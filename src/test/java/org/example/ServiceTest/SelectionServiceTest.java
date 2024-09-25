package org.example.ServiceTest;

import DTO.SelectionInDTO;
import DTO.SelectionOutDTO;
import DTO.SelectionUpdateDTO;
import Entity.Selection;
import Repository.Impl.SelectionRepositoryImpl;
import Repository.SelectionRepository;
import Service.Impl.SelectionServiceImpl;
import Service.SelectionService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;

@ExtendWith(MockitoExtension.class)
public class SelectionServiceTest {
    @Mock
    private static SelectionRepositoryImpl mockSelectionRepository;
    @InjectMocks
    private static SelectionService selectionService;
    @InjectMocks
    private static SelectionRepositoryImpl oldInstance;

    private static void setMock(SelectionRepositoryImpl mock){
        try{
            Field instance = SelectionRepositoryImpl.class.getDeclaredField("instance");
            instance.setAccessible(true);
            oldInstance = (SelectionRepositoryImpl) instance.get(instance);
            instance.set(instance, mock);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeAll
    static void beforeAll(){
        mockSelectionRepository = Mockito.mock(SelectionRepositoryImpl.class);
        setMock(mockSelectionRepository);
        selectionService = SelectionServiceImpl.getInstance();
        System.out.println(0);
    }

    @BeforeEach
    void setUp(){
        Mockito.reset(mockSelectionRepository);
    }

    @AfterAll
    static void afterAll() throws Exception{
        Field instance = SelectionRepositoryImpl.class.getDeclaredField("instance");
        instance.setAccessible(true);
        instance.set(instance, oldInstance);
    }

    @AfterEach
    void tearDownMock(){
        Mockito.reset(mockSelectionRepository);
    }

    @Test
    void findAll(){
        selectionService.findAll();
        Mockito.verify(mockSelectionRepository).findAll();
    }

    @Test
    void findById(){
        Integer expectedId = 1;
        String expectedName = "testName";
        Selection selection = new Selection(1 ,expectedName, null);
        Mockito.doReturn(selection).when(mockSelectionRepository).findSelectionById(Mockito.anyInt());
        SelectionOutDTO result = selectionService.findById(expectedId);
        Mockito.verify(mockSelectionRepository).findSelectionById(Mockito.anyInt());
        Assertions.assertEquals(expectedName, result.getName());

    }

    @Test
    void add(){
        String expectedName = "name";
        SelectionInDTO selectionInDTO = new SelectionInDTO(expectedName, null);
        Selection selection = new Selection(0, expectedName, null);
        Mockito.doReturn(selection).when(mockSelectionRepository).addSelection(Mockito.any(Selection.class));
        SelectionInDTO result = selectionService.add(selectionInDTO);
        Mockito.verify(mockSelectionRepository).addSelection(Mockito.any(Selection.class));
        Assertions.assertEquals(expectedName, result.getName());
    }

    @Test
    void update(){
        Integer expectedId = 1;
        String expectedName = "testName";
        SelectionUpdateDTO selectionUpdateDTO = new SelectionUpdateDTO(expectedId, expectedName, null);
        Selection selection = new Selection(1 ,expectedName, null);
        Mockito.doReturn(selection).when(mockSelectionRepository).updateSelection(Mockito.any(Selection.class));
        SelectionUpdateDTO result = selectionService.update(selectionUpdateDTO);
        Mockito.verify(mockSelectionRepository).updateSelection(Mockito.any(Selection.class));
        Assertions.assertEquals(expectedName, result.getName());
    }

    @Test
    void delete(){
        Integer expectedId = 1;
        selectionService.delete(expectedId);
        ArgumentCaptor<Integer> argumentCaptor = ArgumentCaptor.forClass(Integer.class);
        Mockito.verify(mockSelectionRepository).deleteSelection(argumentCaptor.capture());

        Integer result = argumentCaptor.getValue();
        Assertions.assertEquals(expectedId, result);
    }
}
