/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cvut.fit.swe.servlets;

import com.google.gson.Gson;
import cvut.fit.swe.models.TownDifference;
import cvut.fit.swe.models.TownMap;
import cvut.fit.swe.services.SparqlService;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.eclipse.rdf4j.query.BindingSet;

/**
 *
 * @author janprasil
 */
@WebServlet(name = "ReferendumVotesDifferenceServlet", urlPatterns = {"/ReferendumVotesDifferenceServlet"})
public class ReferendumVotesDifferenceServlet extends HttpServlet {

    private static String queryString = "PREFIX referendum:  <http://prasija1/resources/referendum/#>\n" +
        "PREFIX dbo:  <http://dbpedia.org/ontology/#>\n" +
        "PREFIX town:  <http://prasija1.cz/resources/town/#>\n" +
        "\n" +
        "select distinct * where {\n" +
        "   ?ref a referendum:Referendum ;\n" +
        "        dbo:voteFor ?voteFor ;\n" +
        "        dbo:voteAgainst ?voteAgainst ;\n" +
        "        town:name ?town .\n" +
        "   filter(?voteFor > ?voteAgainst*{RATIO}) .\n" +
        "} ORDER BY {SORT} (?voteFor / ?voteAgainst) ";
    
    @EJB
    private SparqlService sparqlService;
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        try {
            String ratio = request.getParameter("ratio");
            if (ratio == null || !ratio.matches("\\d+(?:\\.\\d+)?")) {
                ratio = "1.0";
            }
            String sort = request.getParameter("sort");
            if (sort == null) sort = "DESC";
            else
                switch (sort) {
                    case "desc":
                        sort = "DESC";
                        break;
                    case "asc":
                        sort = "ASC";
                        break;
                    default:
                        sort = "DESC";
                }
            List<BindingSet> bindings = sparqlService.Select(queryString.replace("{RATIO}", ratio).replace("{SORT}", sort));
            List<TownDifference> result = new ArrayList<>();
            for(BindingSet b : bindings) result.add(new TownDifference(b));
            String json = new Gson().toJson(result);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
            //request.setAttribute("results", results);
            //RequestDispatcher rd = request.getRequestDispatcher("referendumResults.jsp");
            //rd.forward(request, response);
        } catch (Exception e) {
            response.getWriter().println("En error has occured:");
            response.getWriter().println(e.toString());
            response.getWriter().println(e.getMessage());
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
