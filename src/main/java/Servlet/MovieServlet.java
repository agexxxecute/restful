package Servlet;

import DTO.MovieInDTO;
import DTO.MovieOutDTO;
import DTO.MovieUpdateDTO;
import Service.Impl.MovieServiceImpl;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@WebServlet (urlPatterns = {"/movie/*"})
public class MovieServlet extends HttpServlet {
    private final Gson gson = new Gson();
    private final MovieServiceImpl movieService = MovieServiceImpl.getInstance();

    private static String getJson(HttpServletRequest req) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader postData = req.getReader();
        String line;
        while ((line = postData.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] pathPart =req.getPathInfo().split("/");
        if("all".equals(pathPart[1])){
            List<MovieOutDTO> movies = movieService.findAll();
            resp.setContentType("application/json");
            resp.getWriter().write(gson.toJson(movies));
        } else if ("serials".equals(pathPart[1])){
            List<MovieOutDTO> serials = movieService.findAllSerials();
            resp.setContentType("application/json");
            if(serials!=null){
                resp.getWriter().write(gson.toJson(serials));
            } else { resp.getWriter().write("No serials"); }
        } else {

            try {
                int movieId = Integer.parseInt(pathPart[1]);
                MovieOutDTO movie = movieService.findById(movieId);
                resp.setContentType("application/json");
                if (movie != null) {
                    resp.getWriter().write(gson.toJson(movie));
                } else {
                    resp.getWriter().write("No such movie");
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                }
            } catch (Exception e) {
                resp.getWriter().write("Bad request");
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        }

    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String json = getJson(req);
        MovieInDTO movie = gson.fromJson(json, MovieInDTO.class);
        if(movie.getTitle() == null || movie.getYear() == 0){
            resp.getWriter().write("Bad request");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else {
            movieService.add(movie);
            List<MovieOutDTO> movies = movieService.findAll();
            resp.getWriter().write(gson.toJson(movies));
        }
    }

    public void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String json = getJson(req);
        MovieUpdateDTO movie = gson.fromJson(json, MovieUpdateDTO.class);
        if(movie.getTitle() == null || movie.getYear() == 0){
            resp.getWriter().write("Bad request");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else {
            movieService.update(movie);
            List<MovieOutDTO> movies = movieService.findAll();
            resp.getWriter().write(gson.toJson(movies));
        }
    }

    public void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] pathPart =req.getPathInfo().split("/");
        try {
            int movieId = Integer.parseInt(pathPart[1]);
            movieService.delete(movieId);
            resp.setContentType("application/json");
            List<MovieOutDTO> movies = movieService.findAll();
            resp.getWriter().write(gson.toJson(movies));
        } catch (Exception e) {
            resp.getWriter().write("Bad request");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
