package org.example.ServiceTest;

import DTO.DirectorInDTO;
import DTO.DirectorOutDTO;
import DTO.DirectorUpdateDTO;
import Entity.Director;
import Repository.DirectorRepository;
import Repository.Impl.DirectorRepositoryImpl;
import Repository.Impl.MovieRepositoryImpl;
import Repository.MovieRepository;
import Service.DirectorService;
import Service.Impl.DirectorServiceImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;

@ExtendWith(MockitoExtension.class)
public class DirectorServiceTest {

    @Mock
    private static DirectorRepositoryImpl mockDirectorRepository;
    private static MovieRepositoryImpl mockMovieRepository;
    @InjectMocks
    private static DirectorService directorService;
    private static DirectorRepositoryImpl oldDirectorRepository;
    private static MovieRepositoryImpl oldMovieRepository;

    private static void setMock(DirectorRepository mock1, MovieRepository mock2) {
        try{
            Field instance = DirectorRepositoryImpl.class.getDeclaredField("instance");
            instance.setAccessible(true);
            oldDirectorRepository = (DirectorRepositoryImpl) instance.get(instance);
            instance.set(instance, mock1);

            Field instance2 = MovieRepositoryImpl.class.getDeclaredField("instance");
            instance2.setAccessible(true);
            oldMovieRepository = (MovieRepositoryImpl) instance2.get(instance2);
            instance2.set(instance2, mock2);

        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @BeforeAll
    static void beforeAll() {
        mockDirectorRepository = Mockito.mock(DirectorRepositoryImpl.class);
        mockMovieRepository = Mockito.mock(MovieRepositoryImpl.class);
        setMock(mockDirectorRepository, mockMovieRepository);
        directorService = DirectorServiceImpl.getInstance();


    }

    @AfterAll
    static void afterAll() throws Exception {
        Field instance1 = DirectorRepositoryImpl.class.getDeclaredField("instance");
        instance1.setAccessible(true);
        instance1.set(instance1, oldDirectorRepository);

        Field instance2 = MovieRepositoryImpl.class.getDeclaredField("instance");
        instance2.setAccessible(true);
        instance2.set(instance2, oldMovieRepository);
    }

    @AfterEach
    void tearDownMock() {
        Mockito.reset(mockDirectorRepository);
    }


    @Test
    void findAll(){
        directorService.findAll();
        Mockito.verify(mockDirectorRepository).findAll();
    }

    @Test
    void add(){
        String expectedFirstName = "firstName";
        String expectedLastName = "lastName";
        DirectorInDTO directorInDTO = new DirectorInDTO(expectedFirstName, expectedLastName);
        Director director = new Director(0, "firstName", "lastName");
        Mockito.doReturn(director).when(mockDirectorRepository).addDirector(Mockito.any(Director.class));
        Director result = directorService.add(directorInDTO);
        Mockito.verify(mockDirectorRepository).addDirector(Mockito.any(Director.class));
        Assertions.assertEquals(expectedFirstName, result.getFirstName());
        Assertions.assertEquals(expectedLastName, result.getLastName());
    }


    @Test
    void update(){
        Integer expectedId = 5;
        String expectedFirstName = "firstName";
        String expectedLastName = "lastName";
        DirectorUpdateDTO directorUpdateDTO = new DirectorUpdateDTO(expectedId, expectedFirstName, expectedLastName, null);
        Director director = new Director(5, "firstName", "lastName");
        Mockito.doReturn(director).when(mockDirectorRepository).updateDirector(Mockito.any(Director.class));
        DirectorUpdateDTO result = directorService.update(directorUpdateDTO);
        Mockito.verify(mockDirectorRepository).updateDirector(Mockito.any(Director.class));
        Assertions.assertEquals(expectedId, result.getId());
        Assertions.assertEquals(expectedFirstName, result.getFirstName());
        Assertions.assertEquals(expectedLastName, result.getLastName());
    }

    @Test
    void delete(){
        Integer expectedId = 5;
        directorService.delete(expectedId);
        ArgumentCaptor<Integer> argumentCaptor = ArgumentCaptor.forClass(Integer.class);
        Mockito.verify(mockDirectorRepository).deleteDirectorById(argumentCaptor.capture());


        Integer result = argumentCaptor.getValue();
        Assertions.assertEquals(expectedId, result);
    }

    @Test
    void findById(){
        Integer expectedId = 4;
        String expectedFirstName = "firstName";
        String expectedLastName = "lastName";
        Director director = new Director(expectedId, expectedFirstName, expectedLastName);
        Mockito.doReturn(director).when(mockDirectorRepository).findById(Mockito.anyInt());
        DirectorOutDTO result = directorService.findById(expectedId);
        Assertions.assertEquals(expectedId, result.getId());
    }

}
