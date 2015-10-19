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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
      
      if (response.responseHeader.status == 0)
      {
        solrFormattedMsg = solrFormattedMsg + "<b>Solr response = ok.</b>";
        
        // Build a table of providers:
        String providerList = "";
        for (ProviderInfo pi : response.response.docs)
        {
          providerList = providerList.concat("<li>");

          // First name, last name, type, city, state, hcpcs description

          providerList = providerList.concat(pi.NPPES_PROVIDER_LAST_ORG_NAME);
          if (!pi.NPPES_PROVIDER_FIRST_NAME.isEmpty())
          {
            providerList = providerList.concat(", " + pi.NPPES_PROVIDER_FIRST_NAME);
          }
          providerList = providerList.concat(", " + pi.PROVIDER_TYPE);
          providerList = providerList.concat(", " + pi.NPPES_PROVIDER_CITY);
          providerList = providerList.concat(", " + pi.NPPES_PROVIDER_STATE);
          providerList = providerList.concat(", " + pi.HCPCS_DESCRIPTION);
          
          providerList = providerList.concat("</li>");                   
        }
        
        if (response.response.numFound > 0)
        {
          solrFormattedMsg = solrFormattedMsg + "<br />Found " + response.response.numFound +
                           " providers for query; first " + response.response.docs.size() + " are...<br />";
          solrFormattedMsg = solrFormattedMsg + "<ul>" + providerList + "</ul>";        

        }
        else {
          solrFormattedMsg = solrFormattedMsg + "<br />Query for " + queryTerm + " returned 0 results<br />";
        }
      }
      else {
        solrFormattedMsg = solrFormattedMsg + "<b>Solr response not ok = " + response.responseHeader.status + "</b>";
       
      }
      
      message = solrFormattedMsg;
    }
    
    return new ModelAndView("welcome", "message", message);
  }
  
  
  public SolrQueryResponse QuerySolrUrl(String solrUrlBase, String queryTerm) throws Exception
  {
    String solrQueryUrl = solrUrlBase;
    SolrQueryResponse response = null;        
    int numRows = 10;
   
    SolrClient solr = null;
    
    try {

      if (!useSolrJ)
      {
        // note that some work is needed to get this path working again,
        // the response objects migrated a bit for solrJ
        
        // Use built http request + json parsing into java objects.
        solrQueryUrl = solrQueryUrl + "csvtest/select?wt=json&indent=true&q=" + queryTerm
            + "&rows=" + numRows + "&start=0";

        String rawResponse = GetSolrResponse(solrQueryUrl);
        response = new SolrQueryResponse(rawResponse);       
        
      }
      else {
        // Use SolrJ
        solr = new HttpSolrClient(solrUrlBase + "csvtest");      
        
        SolrQuery query = new SolrQuery();
        
        query.set("rows", numRows);
        query.set("q", queryTerm);
        query.setStart(0);
        
        QueryResponse solrJresponse = solr.query(query);
        
        SolrDocumentList list = solrJresponse.getResults();
        
        // Hydrate our object from the SolrJ results (maybe this could be a constructor)
        response = new SolrQueryResponse();
        response.responseHeader.status = solrJresponse.getStatus();
        response.responseHeader.QTime = solrJresponse.getQTime();
        response.response.start = list.getStart();
        response.response.maxScore = list.getMaxScore();
        response.response.numFound = list.getNumFound();
        
        for (int i=0; i<list.size(); i++)
        {
          SolrDocument doc = list.get(i);
          System.out.println("Query result " + i + ": id = " + doc.getFieldValue("id").toString());

          response.response.docs.add(new ProviderInfo(doc.getFieldValueMap()));         
          
        }      

      }
    }    
    finally {
      if (solr != null)
      {
        solr.close();
      }
    }
    
    return response;
        
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