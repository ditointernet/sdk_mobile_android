package ioasys.com.br.sacapp.utils;

import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

/**
* Created by Daniel on 05/06/2015.
*/
public final class ParserUtil {
   private ParserUtil() {
   }

   /**
    * Method for generating a JSON an object of any
    *
    * @param object
    *            Object to parse
    * @return String
    */
   public static String parserObjectToJson(Object object) {
       return new Gson().toJson(object);
   }

   /**
    * Method for converting a JSON an object in any
    *
    * @param json
    *            JSON of object
    * @param clazz
    *            Reference class to be converted
    * @return Object
    */
   @SuppressWarnings({ "unchecked", "rawtypes" })
   public static Object parserJsonToObject(String json, Class clazz) {
       return new Gson().fromJson(json, clazz);
   }

   /**
    * Method to convert a JSON in a list any
    *
    * @param json
    *            JSON of list
    * @param type
    *            Type to the parser may be obtained from the code<br/>
    *            <p>
    *            Type collectionType = new
    *            TypeToken<List<ObjectType>>(){}.getType();
    *            </p>
    * @return List<?>
    */
   public static List<?> parserJsonToList(String json, Type type) {
       Gson gson = new Gson();
       List<?> list = gson.fromJson(json, type);

       return list;
   }

   /**
    * Method to convert a JSON into a HashMap
    *
    * @param json
    *            JSON of HashMap
    * @param type
    *            Type to the parser may be obtained from the code<br/>
    *            <p>
    *            Type type = new TypeToken<Map<ObjectType,
    *            ObjectType>>(){}.getType();
    *            </p>
    * @return HashMap<?, ?>
    */
   public static HashMap<?, ?> parserJsonToHashMap(String json, Type type) {
       Gson gson = new Gson();

       return gson.fromJson(json, type);
   }
}