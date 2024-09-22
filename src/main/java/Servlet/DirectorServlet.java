package Servlet;

import DTO.DirectorInDTO;
import DTO.DirectorOutDTO;
import Service.DirectorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@WebServlet (urlPatterns = {"/director/*"})
public class DirectorServlet extends HttpServlet {
    private Gson gson = new Gson();
    private ObjectMapper objectMapper = new ObjectMapper();

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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] pathPart = req.getPathInfo().split("/");
        if("all".equals(pathPart[1])) {
            List<DirectorOutDTO> directors = DirectorService.findAll();
            resp.setContentType("application/json");
            resp.getWriter().println(gson.toJson(directors));
        } else {
            int directorId = Integer.parseInt(pathPart[1]);
            DirectorOutDTO directorOutDTO = DirectorService.findById(directorId);
            resp.setContentType("application/json");
            if (directorOutDTO != null) {
                resp.getWriter().write(gson.toJson(directorOutDTO));
            } else resp.getWriter().write("No such movie");
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String json = getJson(req);
        DirectorInDTO directorInDTO = gson.fromJson(json, DirectorInDTO.class);
        DirectorService.add(directorInDTO);
        List<DirectorOutDTO> directors = DirectorService.findAll();
        //resp.setContentType("application/json");
        resp.getWriter().println(gson.toJson(directors));

    }

    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String json = getJson(req);
        DirectorOutDTO directorOutDTO = gson.fromJson(json, DirectorOutDTO.class);
        DirectorService.update(directorOutDTO);
        List<DirectorOutDTO> directors = DirectorService.findAll();
        //resp.setContentType("application/json");
        resp.getWriter().println(gson.toJson(directors));
    }

    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String json = getJson(req);
        DirectorOutDTO directorOutDTO = gson.fromJson(json, DirectorOutDTO.class);
        DirectorService.delete(directorOutDTO);
        List<DirectorOutDTO> directors = DirectorService.findAll();
        resp.getWriter().println(gson.toJson(directors));
    }
}
