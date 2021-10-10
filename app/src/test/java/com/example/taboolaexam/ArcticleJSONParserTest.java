package com.example.taboolaexam;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.example.taboolaexam.model.Arcticle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.util.ArrayList;

public class ArcticleJSONParserTest {


    @Test
    public void arcticle_EmptyJSON_ShouldReturnEmptyArcticle() {
        try {
            String jsonString = "{}";
            JSONObject jsonObject = new JSONObject(jsonString);
            assertEquals(Arcticle.fromJSONObject(jsonObject).toString(),new Arcticle().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void arcticle_FieldMissing_ShouldReturnEmptyArcticle() {
        try {
            String jsonString = "{\"name\":\"john\",\"description\":\"mca\"}";
            JSONObject jsonObject = new JSONObject(jsonString);
            assertEquals(Arcticle.fromJSONObject(jsonObject).toString(),new Arcticle().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    @Test
    public void arcticle_FullObject_ShouldReturnNormalArcticle() {
        try {
            String jsonString = "{\"name\":\"test\",\"thumbnail\":\"test\",\"description\":\"test\"}";
            JSONObject jsonObject = new JSONObject(jsonString);
            assertEquals(Arcticle.fromJSONObject(jsonObject).toString(),new Arcticle("test","test","test").toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



}
