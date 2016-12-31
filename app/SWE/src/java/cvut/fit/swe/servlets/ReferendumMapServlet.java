/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cvut.fit.swe.servlets;

import com.google.gson.Gson;
import cvut.fit.swe.models.TownMap;
import cvut.fit.swe.models.TownResult;
import cvut.fit.swe.services.SparqlService;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.query.BindingSet;

/**
 *
 * @author janprasil
 */
@WebServlet(name = "ReferendumMapServlet", urlPatterns = {"/referendum-map"})
public class ReferendumMapServlet extends HttpServlet {

    private static String queryString = "PREFIX foaf: <http://xmlns.com/foaf/0.1/#>\n" +
        "PREFIX town: <http://prasija1.cz/resources/town/#>\n" +
        "PREFIX referendum:  <http://prasija1/resources/referendum/#>\n" +
        "PREFIX dbo:  <http://dbpedia.org/ontology/#>\n" +
        "PREFIX sm:  <https://schema.org/>\n" +
        "\n" +
        "select distinct(?postcode) ?name ?latitude ?longitude ?voteFor ?voteAgainst where {\n" +
        "   ?geo a sm:GeoCoordinates ;\n" +
        "        town:Town ?town ;\n" +
        "        sm:latitude ?latitude ;\n" +
        "        sm:longitude ?longitude .\n" +
        "   ?t a town:Town ;\n" +
        "        foaf:city ?name ;\n" +
        "        foaf:area ?area ;\n" +
        "        foaf:postcode ?postcode .\n" +
        "   ?ref a referendum:Referendum ;\n" +
        "        town:name ?refTown ;\n" +
        "        dbo:voteFor ?voteFor ;\n" +
        "        dbo:voteAgainst ?voteAgainst .\n" +
        "   filter(?t = ?town).\n" +
        "   filter(?area = ?refTown) .\n" +
        "}\n" +
        "ORDER BY DESC (?voteAgainst + ?voteFor)\n" +
        "LIMIT 10000";
    
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
            List<BindingSet> bindings = sparqlService.Select(queryString);
            List<TownMap> result = new ArrayList<>();
            for(BindingSet b : bindings) result.add(new TownMap(b));
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
