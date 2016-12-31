/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cvut.fit.swe.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.ejb.Singleton;
import javax.ejb.Startup;
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

@Startup
@Singleton
public class SparqlService {
    
    private static String CONNECTION_URL = "http://localhost:8890/sparql";
    private static Repository repository;
    
    
    public SparqlService() {
        repository = new SPARQLRepository(CONNECTION_URL);
        repository.initialize();
    }
    
    public List<BindingSet> Select(String queryString) {
        try (RepositoryConnection con = repository.getConnection()) {
            TupleQuery tupleQuery = con.prepareTupleQuery(QueryLanguage.SPARQL, queryString);
            TupleQueryResult result = tupleQuery.evaluate();
            
            List<BindingSet> bindings = QueryResults.stream(result).collect(Collectors.toList());
            return bindings;
        } catch (Exception e) {
            throw e;
        }
    }
    
    public List<Statement> Construct(String queryString) {
        List<Statement> result = new ArrayList<>();
        try (RepositoryConnection con = repository.getConnection()) {
            Query query = con.prepareQuery(QueryLanguage.SPARQL, queryString);
            GraphQueryResult res = ((GraphQuery) query).evaluate();

//            Map<String, String> namespaces = res.getNamespaces();
//            
            while (res.hasNext()) {
                Statement s = res.next();
                result.add(s);
            }
            res.close();
            return result;
        } catch (Exception e) {
            throw e;
        }

    }
    
}
