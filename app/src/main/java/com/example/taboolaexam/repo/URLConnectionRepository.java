package com.example.taboolaexam.repo;

import com.example.taboolaexam.model.Arcticle;
import com.example.taboolaexam.util.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class URLConnectionRepository {
    HttpURLConnection urlConnection = null;
    JSONArray jsonArray = null;

    public List<Arcticle> getDataFromURL() {

        try {
            String responseString = getResponseStringFromServer();
            return parseArcticles(responseString);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e){
        }finally {
            if(urlConnection != null){
                urlConnection.disconnect();
            }
        }
        return null;
    }

    private List<Arcticle> parseArcticles(String responseString) throws JSONException {
        ArrayList< Arcticle> list = new ArrayList<>();
        jsonArray = new JSONArray(responseString);
        for(int i = 0; i < jsonArray.length() ; i ++){
            list.add(Arcticle.fromJSONObject(jsonArray.getJSONObject(i)));
        }
        return list;
    }

    private String getResponseStringFromServer() throws IOException {

        StringBuilder response = new StringBuilder();
        
            URL jsonRepoUrl = new URL(Constants.URL_STRING);

            urlConnection = (HttpURLConnection) jsonRepoUrl.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Content-length", "0");
            urlConnection.connect();

            int status = urlConnection.getResponseCode();
            if(status != 200) {
                throw new IOException("Problem connecting to the server");
            } else {
                BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
            }
            
        return response.toString();
    }
}
