package jobs.rest;

import java.io.IOException;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

class JSONProcessor {
  private final CloseableHttpClient httpClient = HttpClients.createDefault();
  private String restEndpointURI = "https://data.sfgov.org/resource/p4e4-a5a7.json";

  private void close() throws IOException {
    httpClient.close();
  }

  /*
   * Sends get request
   * Returns response body
   * */
  private String sendGet() throws Exception {
    HttpGet request = new HttpGet(restEndpointURI);
    try (CloseableHttpResponse response = httpClient.execute(request)) {
      HttpEntity entity = response.getEntity();
      if (entity != null) {
        // return it as a String
        return EntityUtils.toString(entity);
      }
    }
    return null;
  }

  static void process() throws Exception {
    JSONProcessor obj = new JSONProcessor();
    try {
      parseJSON(obj.sendGet());
    } finally {
      obj.close();
    }
  }

  /*
   * Find relevant fields and create report
   * */
  private static void parseJSON(String body) throws IOException {
    JSONArray jsonArray = new JSONArray(body);
    for (int i = 0; i < jsonArray.length(); i++) {
      JSONObject obj = (JSONObject) jsonArray.get(i);
      String zipCode = (String) obj.get("zipcode");
      String recordID = (String) obj.get("record_id");
      String streetName = (String) obj.get("street_name");
      String status = (String) obj.get("status");

      Item item = new Item(zipCode, recordID, streetName, status);
      ReportProcessor.createReport(item, i);
    }
  }
}
