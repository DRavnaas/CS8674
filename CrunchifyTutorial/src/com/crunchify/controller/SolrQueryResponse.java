package com.crunchify.controller;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.CloudSolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class SolrQueryResponse {
 
  //public int age = 29;
  //public String name = "whatever";
 
  
  
  public com.crunchify.controller.ResponseHeaderX responseHeader;
  
  public ResponseBody response;
  
  
  static String jsonNoResultResponse = "{ \"responseHeader\":{" +
      "\"status\":0," +
      "\"QTime\":16," +
      "\"params\":{" +
      "\"q\":\"inpatient\"," +
      "\"indent\":\"true\"," +
      "\"wt\":\"json\"} }," +
      "\"response\":{\"numFound\":0,\"start\":0,\"docs\":[]} }";
  
  static String jsonResultsResponse = "{ \"responseHeader\":{" +
      "\"status\":0," +
      "\"QTime\":16," +
      "\"params\":{" +
      "\"q\":\"inpatient\"," +
      "\"indent\":\"true\"," +
      "\"wt\":\"json\"} }," +
      "\"response\":{\"numFound\":1,\"start\":0,\"docs\":[" +
      "{" +
        "\"id\": \"1003006115IO735622012\"," +
        "\"year\": 2012," +
        "\"NPI\": \"1003006115\"," +
        "\"NPPES_PROVIDER_LAST_ORG_NAME\": \"DURHAM\"," +
        "\"NPPES_PROVIDER_FIRST_NAME\": \"BENJAMIN\"," +
        "\"NPPES_CREDENTIALS\": \"PA-C\"," +
        "\"NPPES_ENTITY_CODE\": \"I\"," +
        "\"NPPES_PROVIDER_CITY\": \"COLUMBUS\"," +
        "\"NPPES_PROVIDER_ZIP\": \"319046802\"," +
        "\"NPPES_PROVIDER_STATE\": \"GA\"," +
        "\"NPPES_PROVIDER_COUNTRY\": \"US\"," +
        "\"PROVIDER_TYPE\": \"Physician Assistant\"," +
        "\"PLACE_OF_SERVICE\": \"O\"," +
        "\"HCPCS_CODE\": \"73562\"," +
        "\"HCPCS_DESCRIPTION\": \"X-ray of knee, 3 views\"," +
        "\"LINE_SRVC_CNT\": 39," +
        "\"BENE_UNIQUE_CNT\": 36," +
        "\"BENE_DAY_SRVC_CNT\": 37," +
        "\"_version_\": 1515338585533841400" +
        "}" +      
      "]} }";
  
  public SolrQueryResponse()
  {
    this.responseHeader = new ResponseHeaderX();
    this.response = new ResponseBody();    
  }

  public SolrQueryResponse(String json) throws JsonParseException, JsonMappingException, IOException
  {
    ObjectMapper mapper = new ObjectMapper();
    
    SolrQueryResponse parsedResponse = mapper.readValue(json, SolrQueryResponse.class);
    this.responseHeader = parsedResponse.responseHeader;    
    this.response = parsedResponse.response;

  }
  
  public String toJson() throws JsonGenerationException, IOException
  {
    ObjectMapper mapper = new ObjectMapper();    

    return mapper.writeValueAsString(this);      

  }
  
  public static void main(String[] args) throws IOException 
  {
    System.out.println("testing...");
    
    SolrClient solr = null;
    
    try {
      //ObjectMapper mapper = new ObjectMapper();
      
      SolrQueryResponse testResponse = new SolrQueryResponse();
      testResponse.responseHeader.QTime = 3;
      
      System.out.println(testResponse.toJson());
       
      SolrQueryResponse response = new SolrQueryResponse(testResponse.toJson());
  
      response = new SolrQueryResponse(jsonNoResultResponse);
  
      response = new SolrQueryResponse(jsonResultsResponse);
      
      String testJson = response.toJson();
      System.out.println(testJson);
          
      String queryJson = CrunchifyHelloWorld.GetSolrResponse("http://localhost:8983/solr/csvtest/select?wt=json&indent=true&q=knee&rows=10");
      response = new SolrQueryResponse(queryJson);
      
      System.out.println("done - numResponse = " + response.response.numFound + "!");

      // Try SolrJ
      // - add to maven pom.xml
      // - add imports
      // - know what your zookeeper names/ports are...
      
      //String zkHostString = "localhost:9983/solr";
      //solr = new CloudSolrClient(zkHostString);
      // set default collection?
      
      solr = new HttpSolrClient("http://localhost:8983/solr/csvtest");      
      
      SolrQuery query = new SolrQuery();
      //query.setQuery(mQueryString);
      
      query.set("rows", "10");
      //query.setFields("category", "title", "price");
      query.set("q", "knee");
      
      QueryResponse solrJresponse = solr.query(query);
      
      SolrDocumentList list = solrJresponse.getResults();
      
      System.out.println("Query returned " + list.size() + " results out of " + list.getNumFound());
      
      if (list.getNumFound() > 0)
      {
        if (list.size() > 0)
        {
          
        }
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    finally {
      if (solr != null)
      {
        solr.close();
      }
    }
    
  }

}

