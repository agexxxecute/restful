package org.example.ServiceTest;

import DTO.SelectionInDTO;
import DTO.SelectionOutDTO;
import DTO.SelectionUpdateDTO;
import Entity.Selection;
import Repository.Impl.MovieRepositoryImpl;
import Repository.Impl.MovieToSelectionRepositoryImpl;
import Repository.Impl.SelectionRepositoryImpl;
import Repository.MovieRepository;
import Repository.MovieToSelectionRepository;
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
    private static MovieToSelectionRepositoryImpl mockMovieToSelectionRepository;
    private static MovieRepositoryImpl mockMovieRepository;
    @InjectMocks
    private static SelectionService selectionService;
    @InjectMocks
    private static SelectionRepositoryImpl oldSelectionRepository;
    private static MovieToSelectionRepository oldMovieToSelectionRepository;
    private static MovieRepository oldMovieRepository;

    private static void setMock(SelectionRepositoryImpl mockSelectionRepository, MovieToSelectionRepositoryImpl mockMovieToSelectionRepository, MovieRepositoryImpl mockMovieRepository) {
        try{
            Field instance = SelectionRepositoryImpl.class.getDeclaredField("instance");
            instance.setAccessible(true);
            oldSelectionRepository = (SelectionRepositoryImpl) instance.get(instance);
            instance.set(instance, mockSelectionRepository);

            Field instance2 = MovieToSelectionRepositoryImpl.class.getDeclaredField("instance");
            instance2.setAccessible(true);
            oldMovieToSelectionRepository = (MovieToSelectionRepositoryImpl) instance2.get(instance2);
            instance2.set(instance2, mockMovieToSelectionRepository);

            Field instance3 = MovieRepositoryImpl.class.getDeclaredField("instance");
            instance3.setAccessible(true);
            oldMovieRepository = (MovieRepositoryImpl) instance3.get(instance3);
            instance3.set(instance3, mockMovieRepository);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeAll
    static void beforeAll(){
        mockSelectionRepository = Mockito.mock(SelectionRepositoryImpl.class);
        mockMovieToSelectionRepository = Mockito.mock(MovieToSelectionRepositoryImpl.class);
        mockMovieRepository = Mockito.mock(MovieRepositoryImpl.class);
        setMock(mockSelectionRepository, mockMovieToSelectionRepository, mockMovieRepository);
        selectionService = SelectionServiceImpl.getInstance();
    }

    @BeforeEach
    void setUp(){
        Mockito.reset(mockSelectionRepository);
        Mockito.reset(mockMovieToSelectionRepository);
        Mockito.reset(mockMovieRepository);
    }

    @AfterAll
    static void afterAll() throws Exception{
        Field instance = SelectionRepositoryImpl.class.getDeclaredField("instance");
        instance.setAccessible(true);
        instance.set(instance, oldSelectionRepository);

        Field instance2 = MovieToSelectionRepositoryImpl.class.getDeclaredField("instance");
        instance2.setAccessible(true);
        instance2.set(instance2, oldMovieToSelectionRepository);

        Field instance3 = MovieRepositoryImpl.class.getDeclaredField("instance");
        instance3.setAccessible(true);
        instance3.set(instance3, oldMovieRepository);
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
        int expectedId = 1;
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
