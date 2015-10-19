package com.crunchify.controller;

import java.util.ArrayList;
import java.util.List;

public class ResponseBody {
  //"\"response\":{\"numFound\":19,\"start\":0,\"maxScore\":0.7061373,\"docs:\"[]}}";

  public long numFound;
  public long start;
  public double maxScore;
  
  public List<ProviderInfo> docs;
  //public ProviderInfo[] docs;  // Json parsing might not like the generic type
  
  public ResponseBody()
  {
    this.docs = new ArrayList<ProviderInfo>();
  }

}
