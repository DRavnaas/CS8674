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
  
  
  static String jsonResponse = "{ \"responseHeader\":{" +
      "\"status\":0," +
      "\"QTime\":16," +
      "\"params\":{" +
      "\"q\":\"inpatient\"," +
      "\"indent\":\"true\"," +
      "\"wt\":\"json\"} }," +
      "\"response\":{\"numFound\":19,\"start\":0,\"maxScore\":0.7061373,\"docs\":null} }";
  
      

  
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

    response = mapper.readValue(jsonResponse, SolrQueryResponse.class);
    
    System.out.println("done!");

    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    
  }

}

