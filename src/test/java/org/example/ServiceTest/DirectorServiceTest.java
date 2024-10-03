package org.example.ServiceTest;

import DTO.DirectorInDTO;
import DTO.DirectorOutDTO;
import Entity.Director;
import Repository.DirectorRepository;
import Repository.Impl.DirectorRepositoryImpl;
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
    @InjectMocks
    private static DirectorService directorService;
    private static DirectorRepositoryImpl oldInstance;

    private static void setMock(DirectorRepository mock) {
        try{
            Field instance = DirectorRepositoryImpl.class.getDeclaredField("instance");
            instance.setAccessible(true);
            oldInstance = (DirectorRepositoryImpl) instance.get(instance);
            instance.set(instance, mock);

        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @BeforeAll
    static void beforeAll() {
        mockDirectorRepository = Mockito.mock(DirectorRepositoryImpl.class);
        setMock(mockDirectorRepository);
        directorService = DirectorServiceImpl.getInstance();


    }

    @AfterAll
    static void afterAll() throws Exception {
        Field instance1 = DirectorRepositoryImpl.class.getDeclaredField("instance");
        instance1.setAccessible(true);
        instance1.set(instance1, oldInstance);

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
        DirectorOutDTO result = directorService.add(directorInDTO);
        Mockito.verify(mockDirectorRepository).addDirector(Mockito.any(Director.class));
        Assertions.assertEquals(expectedFirstName, result.getFirstName());
        Assertions.assertEquals(expectedLastName, result.getLastName());
    }


    @Test
    void update(){
        Integer expectedId = 5;
        String expectedFirstName = "firstName";
        String expectedLastName = "lastName";
        DirectorOutDTO directorOutDTO = new DirectorOutDTO(expectedId, expectedFirstName, expectedLastName);
        Director director = new Director(5, "firstName", "lastName");
        Mockito.doReturn(director).when(mockDirectorRepository).updateDirector(Mockito.any(Director.class));
        DirectorOutDTO result = directorService.update(directorOutDTO);
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
