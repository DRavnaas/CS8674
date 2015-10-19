package com.crunchify.controller;

public class ResponseBody {
  //"\"response\":{\"numFound\":19,\"start\":0,\"maxScore\":0.7061373,\"docs:\"[]}}";

  public int numFound;
  public int start;
  public double maxScore;
  
  public ProviderInfo[] docs;

}
