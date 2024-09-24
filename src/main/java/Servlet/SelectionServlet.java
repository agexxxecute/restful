package Servlet;

import DTO.SelectionInDTO;
import DTO.SelectionOutDTO;
import DTO.SelectionUpdateDTO;
import Service.Impl.SelectionServiceImpl;
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
    private SelectionService selectionService = new SelectionServiceImpl();

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
        if ("all".equals(pathPart[1])) {
            List<SelectionOutDTO> selectionOutDTOS = selectionService.findAll();
            resp.getWriter().println(gson.toJson(selectionOutDTOS));
        } else{
            int id = Integer.parseInt(pathPart[1]);
            SelectionOutDTO selectionOutDTO = selectionService.findById(id);
            resp.getWriter().println(gson.toJson(selectionOutDTO));
        }
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String json = getJson(req);
        SelectionInDTO selectionInDTO = gson.fromJson(json, SelectionInDTO.class);
        selectionService.add(selectionInDTO);
        List<SelectionOutDTO> selectionOutDTOS = selectionService.findAll();
        resp.getWriter().println(gson.toJson(selectionOutDTOS));
    }

    @Override
    public void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String json = getJson(req);
        SelectionUpdateDTO selectionUpdateDTO = gson.fromJson(json, SelectionUpdateDTO.class);
        selectionService.update(selectionUpdateDTO);
        List<SelectionOutDTO> selectionOutDTOS = selectionService.findAll();
        resp.getWriter().println(gson.toJson(selectionOutDTOS));
    }

    @Override
    public void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String[] pathPart = req.getPathInfo().split("/");
        int selectionId = Integer.parseInt(pathPart[1]);
        selectionService.delete(selectionId);
        List<SelectionOutDTO> selectionOutDTOS = selectionService.findAll();
        resp.getWriter().println(gson.toJson(selectionOutDTOS));
    }
}
