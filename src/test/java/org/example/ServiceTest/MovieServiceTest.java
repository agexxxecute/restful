package org.example.ServiceTest;

import DTO.*;
import Entity.Director;
import Entity.Movie;
import Entity.Selection;
import Repository.DirectorRepository;
import Repository.Impl.DirectorRepositoryImpl;
import Repository.Impl.MovieRepositoryImpl;
import Repository.Impl.MovieToSelectionRepositoryImpl;
import Repository.Impl.SelectionRepositoryImpl;
import Repository.MovieRepository;
import Repository.MovieToSelectionRepository;
import Repository.SelectionRepository;
import Service.Impl.MovieServiceImpl;
import Service.MovieService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(
        MockitoExtension.class
)
public class MovieServiceTest {

    private static MovieRepositoryImpl mockMovieRepository;
    private static MovieToSelectionRepositoryImpl mockMovieToSelectionRepository;
    private static SelectionRepositoryImpl mockSelectionRepository;
    private static DirectorRepositoryImpl mockDirectorRepository;
    @InjectMocks
    private static MovieService movieService;
    private static MovieRepositoryImpl oldMovieRepository;
    private static MovieToSelectionRepository oldMovieToSelectionRepository;
    private static SelectionRepository oldSelectionRepository;
    private static DirectorRepository oldDirectorRepository;

    private static void setMock(MovieRepository mockMovieRepository, MovieToSelectionRepositoryImpl mockMovieToSelectionRepository, SelectionRepositoryImpl mockSelectionRepository, DirectorRepositoryImpl mockDirectorRepository) {
        try{
            Field instance = MovieRepositoryImpl.class.getDeclaredField("instance");
            instance.setAccessible(true);
            oldMovieRepository = (MovieRepositoryImpl) instance.get(instance);
            instance.set(instance, mockMovieRepository);

            Field instance2 = MovieToSelectionRepositoryImpl.class.getDeclaredField("instance");
            instance2.setAccessible(true);
            oldMovieToSelectionRepository = (MovieToSelectionRepositoryImpl) instance2.get(instance2);
            instance2.set(instance2, mockMovieToSelectionRepository);

            Field instance3 = SelectionRepositoryImpl.class.getDeclaredField("instance");
            instance3.setAccessible(true);
            oldSelectionRepository = (SelectionRepositoryImpl) instance3.get(instance3);
            instance3.set(instance3, mockSelectionRepository);

            Field instance4 = DirectorRepositoryImpl.class.getDeclaredField("instance");
            instance4.setAccessible(true);
            oldDirectorRepository = (DirectorRepositoryImpl) instance4.get(instance4);
            instance4.set(instance4, mockDirectorRepository);


        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @BeforeAll
    static void beforeAll() {
        mockMovieRepository = Mockito.mock(MovieRepositoryImpl.class);
        mockMovieToSelectionRepository = Mockito.mock(MovieToSelectionRepositoryImpl.class);
        mockSelectionRepository = Mockito.mock(SelectionRepositoryImpl.class);
        mockDirectorRepository = Mockito.mock(DirectorRepositoryImpl.class);
        setMock(mockMovieRepository, mockMovieToSelectionRepository, mockSelectionRepository, mockDirectorRepository);
        movieService = MovieServiceImpl.getInstance();
    }

    @AfterAll
    static void afterAll() throws Exception {
        Field instance = MovieRepositoryImpl.class.getDeclaredField("instance");
        instance.setAccessible(true);
        instance.set(instance, oldMovieRepository);

        Field instance2 = MovieToSelectionRepositoryImpl.class.getDeclaredField("instance");
        instance2.setAccessible(true);
        instance2.set(instance2, oldMovieToSelectionRepository);

        Field instance3 = SelectionRepositoryImpl.class.getDeclaredField("instance");
        instance3.setAccessible(true);
        instance3.set(instance3, oldSelectionRepository);

        Field instance4 = DirectorRepositoryImpl.class.getDeclaredField("instance");
        instance4.setAccessible(true);
        instance4.set(instance4, oldDirectorRepository);

    }

    @AfterEach
    void tearDownMock() {
        Mockito.reset(mockMovieRepository);
        Mockito.reset(mockMovieToSelectionRepository);
        Mockito.reset(mockSelectionRepository);
        Mockito.reset(mockDirectorRepository);
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
        int expectedId = 1;
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
