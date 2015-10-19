package com.crunchify.controller;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

/*
 * original by Crunchify.com
 * 
 */
 
@Controller
public class CrunchifyHelloWorld {
 
  static boolean useSolrJ = true;
  
  @RequestMapping(value="/welcome", method=RequestMethod.GET)
  public ModelAndView helloWorld(
                                 @RequestParam(value="query", required=false)String queryTerm) throws IOException {
 
    String solrQueryUrl = "http://localhost:8983/solr/";

    String status = QuerySolrUrl(solrQueryUrl, queryTerm);
    
    String message = status;
    return new ModelAndView("welcome", "message", message);
  }
  
  
  public String QuerySolrUrl(String solrUrlBase, String queryTerm) throws IOException
  {
    String solrQueryUrl = solrUrlBase;
    
    if (queryTerm == null || queryTerm.length() == 0)
    {
      solrQueryUrl = solrQueryUrl + "admin/collections?action=CLUSTERSTATUS&wt=json&indent=true";
    }
    else if (!useSolrJ){     
      solrQueryUrl = solrQueryUrl + "csvtest/select?wt=json&indent=true&q=" + queryTerm;
    }
    
    String solrStatus = "Querying " + solrQueryUrl + "...<br><br>";
    String rawResponse = "";
    SolrClient solr = null;
    
    try {

      if (!useSolrJ)
      {
        // Use Jackson JSON parsing...
        rawResponse = GetSolrResponse(solrQueryUrl);
        SolrQueryResponse response = new SolrQueryResponse(rawResponse);
 
        solrStatus = solrStatus + "Solr response:  ";        
      
        if (response.responseHeader.status == 0)
        {
           solrStatus = solrStatus + "<b>Solr response = ok.</b>";
        }
      }
      else {
        // Use SolrJ
        solr = new HttpSolrClient("http://localhost:8983/solr/csvtest");      
        
        SolrQuery query = new SolrQuery();
        
        query.set("rows", "10");
        query.set("q", "knee");
        query.setStart(0);
        
        QueryResponse solrJresponse = solr.query(query);
        
        SolrDocumentList list = solrJresponse.getResults(); 
        for (int i=0; i<list.size(); i++)
        {
          SolrDocument doc = list.get(i);
          System.out.println("Query result " + i + ": id = " + doc.getFieldValue("id").toString());
          Map<String,Object> fields = doc.getFieldValueMap();
          for (String key : fields.keySet())
          {
            Object value = fields.get(key);
            if (value != null)
            {
              System.out.println("    Field " + key + " = " + value);
            }   
          }   
        }
      }
    }
    catch (Exception e) {

      solrStatus = solrStatus + "<b>Error querying Solr.</b>";
      rawResponse = e.toString();

    }
    finally {
      if (solr != null)
      {
        solr.close();
      }
    }

    if (rawResponse.length() > 0)
    {
      int endIndex = rawResponse.length();
      if (endIndex > 2000)
      {
        rawResponse = rawResponse.substring(0, 2000) + "...";        
      }
      solrStatus = solrStatus + "<br><div>" + rawResponse + "</div>";
    }
    return solrStatus;
        
   }
  
  public static String GetSolrResponse(String solrUrl) throws Exception
  {
    String jsonResponse = "";
    HttpURLConnection conn = null;
    
    try {
      URL url = new URL(solrUrl);
      conn = (HttpURLConnection) url.openConnection();
      conn.setRequestMethod("GET");
      conn.setRequestProperty("Accept", "application/json");

      if (conn.getResponseCode() != 200) {        
        throw new Exception("Warning: Http response <> 200: "
            + conn.getResponseCode());
      }

      BufferedReader br = new BufferedReader(new InputStreamReader(
        (conn.getInputStream())));

      String output;
      while ((output = br.readLine()) != null) {
        jsonResponse = jsonResponse + output;    
      }

    }
    finally {
      if (conn != null)
      {
        conn.disconnect();
      }
    }
    return jsonResponse;
  }
}