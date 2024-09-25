package org.example.ServiceTest;

import DTO.*;
import Entity.Movie;
import Entity.Selection;
import Repository.Impl.MovieRepositoryImpl;
import Repository.MovieRepository;
import Service.Impl.MovieServiceImpl;
import Service.MovieService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;

@ExtendWith(
        MockitoExtension.class
)
public class MovieServiceTest {

    private static MovieRepositoryImpl mockMovieRepository;
    @InjectMocks
    private static MovieService movieService;
    private static MovieRepositoryImpl oldInstance;

    private static void setMock(MovieRepository mock){
        try{
            Field instance = MovieRepositoryImpl.class.getDeclaredField("instance");
            instance.setAccessible(true);
            oldInstance = (MovieRepositoryImpl) instance.get(instance);
            instance.set(instance, mock);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @BeforeAll
    static void beforeAll() {
        mockMovieRepository = Mockito.mock(MovieRepositoryImpl.class);
        setMock(mockMovieRepository);
        movieService = MovieServiceImpl.getInstance();
    }

    @AfterAll
    static void afterAll() throws Exception {
        Field instance = MovieRepositoryImpl.class.getDeclaredField("instance");
        instance.setAccessible(true);
        instance.set(instance, oldInstance);
    }

    @AfterEach
    void tearDownMock() {
        Mockito.reset(mockMovieRepository);
    }

    @Test
    void findAll(){
        movieService.findAll();
        Mockito.verify(mockMovieRepository).findAll();
    }

    @Test
    void findAllSerials(){
        movieService.findAllSerials();
        Mockito.verify(mockMovieRepository).getAllSerials();
    }

    @Test
    void findById(){
        Integer expectedId = 1;
        String expectedTitle = "testTitle";
        int expectedYear = 2018;
        boolean expectedIsSerial = false;
        Movie movie = new Movie(expectedId, expectedTitle, expectedYear, expectedIsSerial, null, null);
        Mockito.doReturn(movie).when(mockMovieRepository).getMovieById(Mockito.anyInt());
        MovieOutDTO result = movieService.findById(expectedId);
        Mockito.verify(mockMovieRepository).getMovieById(Mockito.anyInt());
        Assertions.assertEquals(expectedTitle, result.getTitle());
        Assertions.assertEquals(expectedYear, result.getYear());
        Assertions.assertEquals(expectedIsSerial, result.isSerial());
    }

    @Test
    void add(){
        Integer expectedId = 1;
        String expectedTitle = "testTitle";
        int expectedYear = 2018;
        boolean expectedIsSerial = false;
        MovieInDTO movieInDTO = new MovieInDTO(expectedTitle, expectedYear, expectedIsSerial, null, null);
        Movie movie = new Movie(expectedId, expectedTitle, expectedYear, expectedIsSerial, null, null);
        Mockito.doReturn(movie).when(mockMovieRepository).addMovie(Mockito.any(Movie.class));
        Movie result = movieService.add(movieInDTO);
        Mockito.verify(mockMovieRepository).addMovie(Mockito.any(Movie.class));
        Assertions.assertEquals(expectedId, result.getId());
        Assertions.assertEquals(expectedTitle, result.getTitle());
        Assertions.assertEquals(expectedYear, result.getYear());
        Assertions.assertEquals(expectedIsSerial, result.isSerial());
    }

    @Test
    void update(){
        Integer expectedId = 1;
        String expectedTitle = "testTitle";
        int expectedYear = 2018;
        boolean expectedIsSerial = false;
        MovieUpdateDTO movieUpdateDTO = new MovieUpdateDTO(expectedId, expectedTitle, expectedYear, expectedIsSerial, null, null);
        Movie movie = new Movie(expectedId, expectedTitle, expectedYear, expectedIsSerial, null, null);
        Mockito.doReturn(movie).when(mockMovieRepository).updateMovie(Mockito.any(Movie.class));
        MovieUpdateDTO result = movieService.update(movieUpdateDTO);
        Mockito.verify(mockMovieRepository).updateMovie(Mockito.any(Movie.class));
        Assertions.assertEquals(expectedId, result.getId());
        Assertions.assertEquals(expectedTitle, result.getTitle());
        Assertions.assertEquals(expectedYear, result.getYear());
        Assertions.assertEquals(expectedIsSerial, result.isSerial());
    }

    @Test
    void delete(){
        Integer expectedId = 1;
        movieService.delete(expectedId);
        ArgumentCaptor<Integer> argumentCaptor = ArgumentCaptor.forClass(Integer.class);
        Mockito.verify(mockMovieRepository).deleteMovie(argumentCaptor.capture());

        Integer result = argumentCaptor.getValue();
        Assertions.assertEquals(expectedId, result);

    }


}
