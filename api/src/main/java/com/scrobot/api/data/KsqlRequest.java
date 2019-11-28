package com.scrobot.api.data;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class KsqlRequest {

    private final ResponseBuilder responseBuilder = new ResponseBuilder();
    private final String ksqlEndpoint = "http://localhost:8011/query";

    public String getAllMessages() {
        try {
            return responseBuilder.getResponse(new URL(ksqlEndpoint));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static class ResponseBuilder {
        public String getResponse(URL url) throws IOException {
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");

            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("Accept", "application/json");

            con.setDoOutput(true);

            //JSON String need to be constructed for the specific resource.
            //We may construct complex JSON using any third-party JSON libraries such as jackson or org.json
            String jsonInputString = "{\n" +
                "  \"ksql\": \"SELECT * FROM messages;\",\n" +
                "  \"streamsProperties\": {\n" +
                "    \"ksql.streams.auto.offset.reset\": \"earliest\"\n" +
                "  }\n" +
                "}";

            try(OutputStream os = con.getOutputStream()){
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int code = con.getResponseCode();
            System.out.println(code);

            try(BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))){
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                return response.toString();
            }
        }
    }
}
