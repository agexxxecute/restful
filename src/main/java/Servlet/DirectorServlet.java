package Servlet;

import DTO.DirectorInDTO;
import DTO.DirectorOutDTO;
import Service.DirectorService;
import Service.Impl.DirectorServiceImpl;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet (urlPatterns = {"/director/*"})
public class DirectorServlet extends HttpServlet {
    private DirectorService directorService = new DirectorServiceImpl();
    private Gson gson = new Gson();

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
        resp.setContentType("application/json");
        String[] pathPart = req.getPathInfo().split("/");
        if("all".equals(pathPart[1])) {
            List<DirectorOutDTO> directors = directorService.findAll();
            resp.getWriter().println(gson.toJson(directors));
        } else {
            int directorId = Integer.parseInt(pathPart[1]);
            DirectorOutDTO directorOutDTO = directorService.findById(directorId);
            if (directorOutDTO != null) {
                resp.getWriter().write(gson.toJson(directorOutDTO));
            } else{
                resp.getWriter().write("No such movie");
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        }
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String json = getJson(req);
        Optional<DirectorInDTO> directorInDTO;
        try{
            directorInDTO = Optional.ofNullable(gson.fromJson(json, DirectorInDTO.class));
            if(directorInDTO.get().getFirstName()!=null && directorInDTO.get().getLastName()!=null) {
                directorService.add(directorInDTO.get());
                List<DirectorOutDTO> directors = directorService.findAll();
                resp.getWriter().println(gson.toJson(directors));
            } else {
                throw new Exception ("Bad request");
            }
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Bad request");

        }

    }

    public void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String json = getJson(req);
        DirectorOutDTO directorOutDTO = gson.fromJson(json, DirectorOutDTO.class);
        if(directorOutDTO.getFirstName()!=null && directorOutDTO.getLastName()!=null) {
            directorService.update(directorOutDTO);
            List<DirectorOutDTO> directors = directorService.findAll();
            resp.getWriter().println(gson.toJson(directors));
        } else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("Bad request");
        }
    }

    public void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] pathPart = req.getPathInfo().split("/");
        try {
            int directorId = Integer.parseInt(pathPart[1]);
            directorService.delete(directorId);
        } catch (Exception e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        List<DirectorOutDTO> directors = directorService.findAll();
        resp.setContentType("application/json");
        resp.getWriter().println(gson.toJson(directors));
    }
}
