package com.crunchify.controller;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class SolrQueryResponse {
 
  //public int age = 29;
  //public String name = "whatever";

  
 
  
  
  public ResponseHeader responseHeader;
  
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
  
      

  
  public static void main(String[] args) 
  {
    System.out.println("testing...");
    
    try {
    ObjectMapper mapper = new ObjectMapper();
    
    SolrQueryResponse testResponse = new SolrQueryResponse();
    testResponse.responseHeader = new ResponseHeader();
    testResponse.responseHeader.QTime = 3;
    
    System.out.println(mapper.writeValueAsString(testResponse));
    String testJson = mapper.writeValueAsString(testResponse);
    
    SolrQueryResponse response = mapper.readValue(testJson, SolrQueryResponse.class);

    response = mapper.readValue(jsonNoResultResponse, SolrQueryResponse.class);

    response = mapper.readValue(jsonResultsResponse, SolrQueryResponse.class);
    
    testJson = mapper.writeValueAsString(response);
    System.out.println(mapper.writeValueAsString(response));
        
    System.out.println("done!");

    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    
  }

}

