package com.crunchify.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


/*
 * original by Crunchify.com
 * 
 */
 
@Controller
public class CrunchifyHelloWorld {
 
  static boolean useSolrJ = true;  // I was using this to toggle between implementations
  
  @RequestMapping(value="/welcome", method=RequestMethod.GET)
  public ModelAndView helloWorld(
                                 @RequestParam(value="query", required=true)String queryTerm) throws Exception {
 
    String solrQueryUrl = "http://localhost:8983/solr/";
    String message = "";
    
    if (queryTerm == null || queryTerm.equalsIgnoreCase(""))
    {
        message = "A query term is required.";
    }
    else {
      String solrFormattedMsg = "Querying " + solrQueryUrl + "...<br><br>";

      SolrQueryResponse response = QuerySolrUrl(solrQueryUrl, queryTerm);

      solrFormattedMsg = solrFormattedMsg + "Solr response:  ";        
      
      if (response.header.status == 0)
      {
        solrFormattedMsg = solrFormattedMsg + "<b>Solr response = ok.</b>";
        
        // Build a table of providers:
        String providerList = "";
        for (Provider pi : response.body.docs)
        {
          providerList = providerList.concat("<li>");

          // First name, last name, type, city, state, hcpcs description

          providerList = providerList.concat(pi.last_or_org_name);
          if (!pi.first_name.isEmpty())
          {
            providerList = providerList.concat(", " + pi.first_name);
          }
          providerList = providerList.concat(", " + pi.provider_type);
          providerList = providerList.concat(", " + pi.city);
          providerList = providerList.concat(", " + pi.state);
          providerList = providerList.concat(", " + pi.hcpcs_description);
          
          providerList = providerList.concat("</li>");                   
        }
        
        if (response.body.numFound > 0)
        {
          solrFormattedMsg = solrFormattedMsg + "<br />Found " + response.body.numFound +
                           " providers for query; first " + response.body.docs.size() + " are...<br />";
          solrFormattedMsg = solrFormattedMsg + "<ul>" + providerList + "</ul>";        

        }
        else {
          solrFormattedMsg = solrFormattedMsg + "<br />Query for " + queryTerm + " returned 0 results<br />";
        }
      }
      else {
        solrFormattedMsg = solrFormattedMsg + "<b>Solr response not ok = " + response.header.status + "</b>";
       
      }
      
      message = solrFormattedMsg;
    }
    
    return new ModelAndView("welcome", "message", message);
  }
  
  
  public SolrQueryResponse QuerySolrUrl(String solrUrlBase, String queryTerm) throws Exception
  {
    SolrQueryResponse response = null;        
    int numRows = 10;      
    
      
    // Hydrate our object from the SolrJ results (maybe this could be a constructor)
    List<Provider> hits = SolrQueryResponse.getProviders(numRows, queryTerm);
        
    for (Provider p : hits)
    {
       System.out.println("Query result : id = " + p.id);         
    } 
    
    return response;
        
   }
  
}