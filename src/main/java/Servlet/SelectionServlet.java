package Servlet;

import DTO.SelectionInDTO;
import DTO.SelectionNoIdDTO;
import DTO.SelectionOutDTO;
import DTO.SelectionUpdateDTO;
import Service.SelectionService;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/selection/*"})
public class SelectionServlet extends HttpServlet {

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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String[] pathPart = req.getPathInfo().split("/");
        if ("all".equals(pathPart[1])) {
            List<SelectionOutDTO> selectionOutDTOS = SelectionService.findAll();
            resp.getWriter().println(gson.toJson(selectionOutDTOS));
        } else{
            int id = Integer.parseInt(pathPart[1]);
            SelectionOutDTO selectionOutDTO = SelectionService.findById(id);
            resp.getWriter().println(gson.toJson(selectionOutDTO));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String json = getJson(req);
        SelectionInDTO selectionInDTO = gson.fromJson(json, SelectionInDTO.class);
        SelectionService.add(selectionInDTO);
        List<SelectionOutDTO> selectionOutDTOS = SelectionService.findAll();
        resp.getWriter().println(gson.toJson(selectionOutDTOS));
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String json = getJson(req);
        SelectionUpdateDTO selectionUpdateDTO = gson.fromJson(json, SelectionUpdateDTO.class);
        SelectionService.update(selectionUpdateDTO);
        List<SelectionOutDTO> selectionOutDTOS = SelectionService.findAll();
        resp.getWriter().println(gson.toJson(selectionOutDTOS));
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String json = getJson(req);
        SelectionUpdateDTO selectionUpdateDTO = gson.fromJson(json, SelectionUpdateDTO.class);
    }
}
