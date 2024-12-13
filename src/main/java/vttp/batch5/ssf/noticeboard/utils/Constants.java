package vttp.batch5.ssf.noticeboard.utils;

import jakarta.json.JsonObject;

import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import jakarta.json.Json;
import jakarta.json.JsonReader;

public class Constants {

    public static Date stringToDate(String jsonDate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("EEE, MM/dd/yyyy");
            return sdf.parse(jsonDate);
        } catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static long dateTolong(Date date) {
        try{
            return date.getTime();
        } catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    public static Date longToDate(JsonObject jsonObject, String dateFormat) {
        long longDate = jsonObject.getJsonNumber(dateFormat).longValue();
        return new Date(longDate);
    }

    // take in payload and return a JsonObject
    public static JsonObject toJson(String response) {
        JsonReader reader = Json.createReader(new StringReader(response));
		return reader.readObject();
    }
}
