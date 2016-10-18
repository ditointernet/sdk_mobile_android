package ioasys.com.br.sacapp.utils;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
* Created by Daniel on 05/06/2015.
*/
public class JSONUtils {

   public static List<?> getJSONList(String response, final Class clazz){

       Type collectionType = new  TypeToken<List<?>>(){}.getType();

       try{
           return  (List<?>) ParserUtil.parserJsonToList(response, collectionType);

       }catch (JsonSyntaxException e) {
           Log.e("JSON_ERROR", e.getMessage());
           return null;
       }

   }

   public static Object getJSONObject(String response, Class clazz){

       return  ParserUtil.parserJsonToObject(response, clazz);

   }


   public static String getJSONString(Object object){

       return new Gson().toJson(object);

   }

}