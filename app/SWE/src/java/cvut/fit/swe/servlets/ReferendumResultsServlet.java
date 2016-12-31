/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cvut.fit.swe.servlets;

import com.google.gson.Gson;
import cvut.fit.swe.models.TownResult;
import cvut.fit.swe.services.SparqlService;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.model.Value;
import org.eclipse.rdf4j.query.BindingSet;
import org.eclipse.rdf4j.query.GraphQuery;
import org.eclipse.rdf4j.query.GraphQueryResult;
import org.eclipse.rdf4j.query.Query;
import org.eclipse.rdf4j.query.QueryLanguage;
import org.eclipse.rdf4j.query.QueryResults;
import org.eclipse.rdf4j.query.TupleQuery;
import org.eclipse.rdf4j.query.TupleQueryResult;
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.sparql.SPARQLRepository;


/**
 *
 * @author janprasil
 */
@WebServlet(name = "ReferendumResultsServlet", urlPatterns = {"/referendum-results"})
public class ReferendumResultsServlet extends HttpServlet {

    @EJB
    private SparqlService sparqlService;
   
    private static String queryString = "PREFIX foaf: <http://xmlns.com/foaf/0.1/#>\n" +
                    "PREFIX town: <http://prasija1.cz/resources/town/#>\n" +
                    "PREFIX referendum:  <http://prasija1/resources/referendum/#>\n" +
                    "PREFIX prosperity:  <http://prasija1.cz/resources/prosperity/#>\n" +
                    "PREFIX dbo:  <http://dbpedia.org/ontology/#>\n" +
                    "PREFIX sm:  <https://schema.org/>\n" +
                    "PREFIX rdf: <http://www.w3.org/1999/02/22-rdfsyntax-ns#>\n" +
                    "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>\n" +
                    "CONSTRUCT {\n" +
                    "   ?town a town:Town ;\n" +
                    "      foaf:area ?area ;\n" +
                    "      foaf:city ?city ;\n" +
                    "      rdf:voteRatio ?voteRatio ;\n" +
                    "      rdf:rank ?e_rank .\n" +
                    "} WHERE {\n" +
                    "   ?prosperity a prosperity:Prosperity ;\n" +
                    "      town:name ?prosp_town .\n" +
                    "   ?economic a prosperity:Economic ;\n" +
                    "      prosperity:Prosperity ?e_pros ;\n" +
                    "      dbo:rank ?e_rank .\n" +
                    "   filter(?prosperity = ?e_pros) .\n" +
                    "   ?town a town:Town ;\n" +
                    "      foaf:area ?area ;\n" +
                    "      foaf:city ?city .\n" +
                    "   filter(?area = ?prosp_town) .\n" +
                    "   ?referendum a referendum:Referendum ;\n" +
                    "     town:name ?reftown ;\n" +
                    "     dbo:voteAgainst ?voteAgainst ;\n" +
                    "     dbo:voteFor ?voteFor .\n" +
                    "   filter(?reftown = ?area) .\n" +
                    "   BIND( xsd:float(?voteAgainst) / xsd:float(?voteFor) AS ?voteRatio) .\n" +
                    "}";
    
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
            try {
                Iterator<Statement> res = sparqlService.Construct(queryString).iterator();
                List<TownResult> results = new ArrayList<>();
               
                while (res.hasNext()) {
                    TownResult tr = new TownResult();
                    
                    Statement s = res.next();
                    s.getObject().stringValue();
                    
                    s = res.next();
                    tr.setName(s.getObject().stringValue());
                    
                    s = res.next();
                    tr.setArea(s.getObject().stringValue());
                    
                    s = res.next();
                    tr.setVoteRatio(Float.parseFloat(s.getObject().stringValue()));
                    
                    s = res.next();
                    tr.setRank(Integer.parseInt(s.getObject().stringValue()));
                    
                    results.add(tr);
                }
                
                
                String json = new Gson().toJson(results);
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
