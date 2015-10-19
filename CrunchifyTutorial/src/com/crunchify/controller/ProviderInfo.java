package com.crunchify.controller;

import java.util.Map;

public class ProviderInfo {  
  public String id;
  public long year;
  public String NPI;
  public String NPPES_PROVIDER_LAST_ORG_NAME;
  public String NPPES_PROVIDER_FIRST_NAME;
  public String NPPES_CREDENTIALS;
  public String NPPES_ENTITY_CODE;
  public String NPPES_PROVIDER_CITY;
  public String NPPES_PROVIDER_ZIP;
  public String NPPES_PROVIDER_STATE;
  public String NPPES_PROVIDER_COUNTRY;
  public String PROVIDER_TYPE;
  public String PLACE_OF_SERVICE;
  public String HCPCS_CODE;
  public String HCPCS_DESCRIPTION;
  public float LINE_SRVC_CNT;
  public long BENE_UNIQUE_CNT;
  public long BENE_DAY_SRVC_CNT;
  public long _version_;
  
  public ProviderInfo(String id)
  {
    this.id = id;
  }
  
  // Useful for setting info from SolrJ
  public ProviderInfo(Map<String, Object> fields)
  {
    this.id = fields.get("id").toString();
    for (String key : fields.keySet())
    {
      switch(key){
        case "id" :
            // Already set this one
            break;
        case "year" :
            this.year = (long)fields.get(key);
            break;
        case "NPI" :
          this.NPI = fields.get(key).toString();
          break;
        case "NPPES_PROVIDER_LAST_ORG_NAME" :
          this.NPPES_PROVIDER_LAST_ORG_NAME = fields.get(key).toString();
          break;
        case "NPPES_PROVIDER_FIRST_NAME" :
          this.NPPES_PROVIDER_FIRST_NAME = fields.get(key).toString();
          break;
        case "NPPES_CREDENTIALS" :
          this.NPPES_CREDENTIALS = fields.get(key).toString();
          break;
        case "NPPES_ENTITY_CODE" :
          this.NPPES_ENTITY_CODE = fields.get(key).toString();
          break;
        case "NPPES_PROVIDER_CITY" :
          this.NPPES_PROVIDER_CITY = fields.get(key).toString();
          break;
        case "NPPES_PROVIDER_ZIP" :
          this.NPPES_PROVIDER_ZIP = fields.get(key).toString();
          break;
        case "NPPES_PROVIDER_STATE" :
          this.NPPES_PROVIDER_STATE = fields.get(key).toString();
          break;
        case "NPPES_PROVIDER_COUNTRY" :
          this.NPPES_PROVIDER_COUNTRY = fields.get(key).toString();
          break;
        case "PROVIDER_TYPE" :
          this.PROVIDER_TYPE = fields.get(key).toString();
          break;
        case "PLACE_OF_SERVICE" :
          this.PLACE_OF_SERVICE = fields.get(key).toString();
          break;
        case "HCPCS_CODE" :
          this.HCPCS_CODE = fields.get(key).toString();
          break;
        case "HCPCS_DESCRIPTION" :
          this.HCPCS_DESCRIPTION = fields.get(key).toString();
          break;
        case "LINE_SRVC_CNT" :
          this.LINE_SRVC_CNT = (float)fields.get(key);
          break; 
        case "BENE_UNIQUE_CNT" :
          this.BENE_UNIQUE_CNT = (long)fields.get(key);
          break;
        case "BENE_DAY_SRVC_CNT" :
          this.BENE_DAY_SRVC_CNT = (long)fields.get(key);
          break;
        case "_version_" :
          break;
        default: 
            // We just ignore fields we don't recognize
            break;
      }

    }
  }
}
