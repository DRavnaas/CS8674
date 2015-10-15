package com.crunchify.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;
//import org.json.simple.parser.ParseException;
//import org.scribe.model.Request;
//import org.scribe.model.Response

/*
 * original by Crunchify.com
 * 
 */
 
@Controller
public class CrunchifyHelloWorld {
 
  @RequestMapping(value="/welcome", method=RequestMethod.GET)
  public ModelAndView helloWorld(
                                 @RequestParam(value="query", required=false)String queryTerm) {
 
    String solrQueryUrl = "http://localhost:8983/solr/";
    if (queryTerm == null || queryTerm.length() == 0)
    {
      solrQueryUrl = solrQueryUrl + "admin/collections?action=CLUSTERSTATUS&wt=json&indent=true";
    }
    else {     
      solrQueryUrl = solrQueryUrl + "csvtest/select?wt=json&indent=true&q=" + queryTerm;
    }

    String status = QuerySolrUrl(solrQueryUrl);
    
    String message = status;
    return new ModelAndView("welcome", "message", message);
  }
  
  
  public String QuerySolrUrl(String solrUrl)
  {
    String solrStatus = "Querying " + solrUrl + "...<br><br>";
    String details = "";

    // A rest client library with JSON processing (maybe that's two libraries)
    // would be useful here.
    HttpURLConnection conn = null;
    
    try {
      URL url = new URL(solrUrl);
      conn = (HttpURLConnection) url.openConnection();
      conn.setRequestMethod("GET");
      conn.setRequestProperty("Accept", "application/json");

      if (conn.getResponseCode() != 200) {
        solrStatus = "Failed : HTTP error code : "
            + conn.getResponseCode();
      }

      solrStatus = solrStatus + "Solr response:  ";

      BufferedReader br = new BufferedReader(new InputStreamReader(
        (conn.getInputStream())));

      String output;
      while ((output = br.readLine()) != null) {
          details = details + output + "<br>";    
      }

      if (details.startsWith("{\"responseHeader\":{\"status\":0,"))
      {
        solrStatus = solrStatus + "<b>Solr response = ok.</b>";
      }
        
      //conn.disconnect();

    } catch (Exception e) {

      solrStatus = solrStatus + "<b>Error querying Solr.</b>";
      details = e.toString();

    }
    finally {
      if (conn != null)
      {
        conn.disconnect();
      }
    }

    if (details.length() > 0)
    {
      int endIndex = details.length();
      if (endIndex > 2000)
      {
        details = details.substring(0, 2000) + "...";        
      }
      solrStatus = solrStatus + "<br><div>" + details + "</div>";
    }
    return solrStatus;
        
   }
}