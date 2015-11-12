package org.hunter.medicare.solr;

import java.util.Map;

import org.hunter.medicare.controller.Views;

import com.datastax.driver.core.ColumnDefinitions.Definition;
import com.datastax.driver.core.Row;
import com.fasterxml.jackson.annotation.JsonView;

public class Provider {
    @Override
    public String toString() {
  String ret = "";
  if (providerDetails != null)
      ret += String.format("averageSubmittedChargeAmount %.2f,", providerDetails.averageSubmittedChargeAmount);

  ret += String.format("daycount %d,\t firstname: %s, \tlastname: %s \t zip:%s", beneficiaries_day_service_count,
    first_name, last_or_org_name, zip);

  return ret;
    }

    // See here for quick reference on the medicare derived fields
    // http://www.t1cg.io/medicare-glossary

    @JsonView(Views.Public.class)
    public String id;
    @JsonView(Views.Public.class)
    public long year;
    @JsonView(Views.Public.class)
    public String npi;
    @JsonView(Views.Public.class)
    public String last_or_org_name;
    @JsonView(Views.Public.class)
    public String first_name;
    @JsonView(Views.Public.class)
    public String credentials;
    @JsonView(Views.Public.class)
    public String entity_code; // or enum?
    @JsonView(Views.Public.class)
    public String city;
    @JsonView(Views.Public.class)
    public String zip;
    @JsonView(Views.Public.class)
    public String state;
    @JsonView(Views.Public.class)
    public String country;
    @JsonView(Views.Public.class)
    public String provider_type;
    @JsonView(Views.Public.class)
    public String place_of_service; // or enum?
    @JsonView(Views.Public.class)
    public String hcpcs_code;
    @JsonView(Views.Public.class)
    public String hcpcs_description;
    @JsonView(Views.Public.class)
    public float line_service_count;
    @JsonView(Views.Public.class)
    public long beneficiaries_unique_count;
    @JsonView(Views.Public.class)
    public long beneficiaries_day_service_count;

    // These are only in Cassandra.
    public class ExtendedInfo {
  public String middle_initial;
  public String gender; // Or could be an enum?
  public String streetAddress1;
  public String streetAddress2;
  public boolean medicare_participation; // true or false (y/n in the csv)
  public boolean hcpcs_drug_indicator; // true or false (y/n in the csv)
  public float averageMedicareAllowedAmount;
  public float stddevMedicareAllowedAmount;
  public float averageSubmittedChargeAmount; // sort this field to find
               // top expensive ones.
  public float stddevSubmittedChargeAmount;
  public float averageMedicarePaymentAmount;
  public float stddevMedicarePaymentAmount;
    }

    // Set this to null for Solr queries?
    public ExtendedInfo providerDetails;

    public Provider(String id) {
  this.id = id;
    }

    // Constructor used by SolrJ
    public Provider(Map<String, Object> fields) {
  this.providerDetails = null; // Null = Solr doesn't have these details

  this.id = fields.get("id").toString();
  for (String key : fields.keySet()) {
      switch (key) {
      case "id":
    // Already set this one
    break;
      case "year":
    this.year = (long) fields.get(key);
    break;
      case "NPI":
    this.npi = fields.get(key).toString();
    break;
      case "NPPES_PROVIDER_LAST_ORG_NAME":
    this.last_or_org_name = fields.get(key).toString();
    break;
      case "NPPES_PROVIDER_FIRST_NAME":
    this.first_name = fields.get(key).toString();
    break;
      case "NPPES_CREDENTIALS":
    this.credentials = fields.get(key).toString();
    break;
      case "NPPES_ENTITY_CODE":
    this.entity_code = fields.get(key).toString();
    break;
      case "NPPES_PROVIDER_CITY":
    this.city = fields.get(key).toString();
    break;
      case "NPPES_PROVIDER_ZIP":
    this.zip = fields.get(key).toString();
    break;
      case "NPPES_PROVIDER_STATE":
    this.state = fields.get(key).toString();
    break;
      case "NPPES_PROVIDER_COUNTRY":
    this.country = fields.get(key).toString();
    break;
      case "PROVIDER_TYPE":
    this.provider_type = fields.get(key).toString();
    break;
      case "PLACE_OF_SERVICE":
    this.place_of_service = fields.get(key).toString();
    break;
      case "HCPCS_CODE":
    this.hcpcs_code = fields.get(key).toString();
    break;
      case "HCPCS_DESCRIPTION":
    this.hcpcs_description = fields.get(key).toString();
    break;
      case "LINE_SRVC_CNT":
    this.line_service_count = (float) fields.get(key);
    break;
      case "BENE_UNIQUE_CNT":
    this.beneficiaries_unique_count = (long) fields.get(key);
    break;
      case "BENE_DAY_SRVC_CNT":
    this.beneficiaries_day_service_count = (long) fields.get(key);
    break;
      case "_version_":
    break;
      default:
    // We just ignore fields we don't recognize
    break;
      }
  }
    }

    /**
     * Cassandra constructor
     */
    // TODO make sure this works on empty columns, otherwise will
    // have to do something similar to Doyle, but don't think
    // cases can be Definition class type
    // could try definition.getName() -> suppose to return string
    public Provider(Row result) {
  this.id = result.getString("id");
  this.year = (long) result.getInt("year");
  this.npi = result.getString("npi");
  this.last_or_org_name = result.getString("nppes_provider_last_org_name");
  this.first_name = result.getString("nppes_provider_first_name");
  this.credentials = result.getString("nppes_credentials");
  this.entity_code = result.getString("nppes_entity_code");
  this.city = result.getString("nppes_provider_city");
  this.zip = result.getString("nppes_provider_zip");
  this.state = result.getString("nppes_provider_state");
  this.country = result.getString("nppes_provider_country");
  this.provider_type = result.getString("provider_type");
  this.place_of_service = result.getString("place_of_service");
  this.hcpcs_code = result.getString("hcpcs_code");
  this.hcpcs_description = result.getString("hcpcs_description");
  this.line_service_count = (float) result.getInt("line_srvc_cnt");
  this.beneficiaries_unique_count = (long) result.getInt("bene_unique_cnt");
  this.beneficiaries_day_service_count = (long) result.getInt("bene_day_srvc_cnt");
  providerDetails.middle_initial = result.getString("nppes_provider_mi");
  providerDetails.gender = result.getString("nppes_provider_gender");
  providerDetails.streetAddress1 = result.getString("nppes_provider_street1");
  providerDetails.streetAddress2 = result.getString("nppes_provider_street2");

  if (result.getString("medicare_participation_indicator").equals("y")) {
      providerDetails.medicare_participation = true;
  } else {
      providerDetails.medicare_participation = false;
  }
  if (result.getString("hcpcs_drug_indicator").equals("y")) {
      providerDetails.hcpcs_drug_indicator = true;
  } else {
      providerDetails.hcpcs_drug_indicator = false;
  }
  providerDetails.averageMedicareAllowedAmount = result.getFloat("average_medicare_allowed_amt");
  providerDetails.stddevMedicareAllowedAmount = result.getFloat("stdev_Medicare_allowed_amt");
  providerDetails.averageSubmittedChargeAmount = result.getFloat("average_submitted_chrg_amt");
  providerDetails.stddevSubmittedChargeAmount = result.getFloat("stdev_submitted_chrg_amt");
  providerDetails.averageMedicarePaymentAmount = result.getFloat(" average_medicare_payment_amt");
  providerDetails.stddevMedicarePaymentAmount = result.getFloat(" stdev_medicare_payment_amt");
    }


   public static List<Provider> buildMockResponse(String state, String procedure) throws JsonParseException, JsonMappingException, IOException
   {
     List<Provider> providers = new ArrayList<Provider>();
     
     String json1 = 
         "{" +
         "\"id\": \"1003000720IO992132012\"," +
         "\"year\": 2012," +
         "\"npi\": \"1003000720\"," +
         "\"last_or_org_name\": \"HERNANDEZ\"," +
         "\"first_name\": \"OTNIEL\"," +
         "\"credentials\": \"DNP, FNP\"," +
         "\"entity_code\": \"I\"," +
         "\"city\": \"HIALEAH\"," +
         "\"zip\": \"330127100\"," +
         "\"state\": \"FL\"," +
         "\"country\": \"US\"," +
         "\"provider_type\": \"Nurse Practitioner\"," +
         "\"place_of_service\": \"O\"," +
         "\"hcpcs_code\": \"99213\"," +
         "\"hcpcs_description\": \"Established patient office or other outpatient visit, typically 15 minutes\"," +
         "\"line_service_count\": 37," +
         "\"beneficiaries_unique_count\": 15," +
         "\"beneficiaries_day_service_count\": 37," +
         "\"providerDetails\": null" +
         "}";
     
     String json2 = 
         "{" +
         "\"id\": \"1003000522IF992132012\"," +
         "\"year\": 2012," +
         "\"npi\": \"1003000522\"," +
         "\"last_or_org_name\": \"WEIGAND\"," +
         "\"first_name\": \"FREDERICK\"," +
         "\"credentials\": \"MD\"," +
         "\"entity_code\": \"I\"," +
         "\"city\": \"DELTONA\"," +
         "\"zip\": \"327255836\"," +
         "\"state\": \"FL\"," +
         "\"country\": \"US\"," +
         "\"provider_type\": \"Family Practice\"," +
         "\"place_of_service\": \"F\"," +
         "\"hcpcs_code\": \"99213\"," +
         "\"hcpcs_description\": \"Established patient office or other outpatient visit, typically 15 minutes\"," +
         "\"line_service_count\": 449," +
         "\"beneficiaries_unique_count\": 268," +
         "\"beneficiaries_day_service_count\": 449," +
         "\"providerDetails\": null" +
         "}";
     
     ObjectMapper mapper = new ObjectMapper();
     
     Provider prov1 = (Provider) mapper.readValue(json1, Provider.class);
     
     Provider prov2 = (Provider) mapper.readValue(json2, Provider.class);

     providers.add(prov1);
     providers.add(prov2);
     
     return providers;
   }
   
   public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException
   {
     buildMockResponse("fl", "99213");
   }
}
