package com.cn.actual.utils;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

/**
 * 
 * @ClassName: JsonUtil
 * @Description: TODO转换JSON工具类
 * @author lism
 * @date 2016年6月6日 上午11:39:37
 *
 */
public class JsonUtil {

    private JsonUtil() {
    }

    private static Gson gson;

    /**
     * 获取Gson的实例
     * 
     * @param createNew 方式：true,创建新实例；false 存在的gson实例。
     * @return
     */
    private static synchronized Gson getGsonInstance(Boolean createNew) {
        if (createNew) {
            return new Gson();
        } else if (gson == null) {
            gson = new Gson();
        }
        return gson;
    }

    // ========================================我是邪恶的分割线===============================================

    /**
     * java对象转json字符窜1
     * 
     * @param obj 要被转换的java对象
     * @return json字符窜
     * @throws Exception
     */
    public static String toJson(Object obj) {
        try {
            Gson gson = getGsonInstance(Boolean.FALSE);
            return gson.toJson(obj);
        } catch (Exception e) {
            throw new RuntimeException("json转换异常" + e.getMessage(), e);
        }
    }

    /**
     * java对象转json字符窜2
     * 
     * @param obj 要被转换的java对象
     * @param createNew Gson实例方式:true，新实例;false,存在的gson实例
     * @return json字符窜
     * @throws Exception
     */
    public static String toJson(Object obj, Boolean createNew) throws Exception {
        try {
            Gson gson = getGsonInstance(createNew);
            return gson.toJson(obj);
        } catch (Exception e) {
            throw new Exception(e.getMessage(), e);
        }
    }

    // ========================================我是邪恶的分割线===============================================

    /**
     * json字符窜转java对象1
     * 
     * @param jsonstr 准备转换的json字符串
     * @param type 准备转换的类对象(e.g Student.class)
     * @return 该类的java对象
     * @throws Exception
     */
    public static <T> T fromJson(String jsonstr, Type type) throws Exception {
        try {
            Gson gson = getGsonInstance(Boolean.FALSE);
            return gson.fromJson(jsonstr, type);
        } catch (Exception e) {
            throw new Exception(e.getMessage(), e);
        }
    }

    /**
     * json字符窜转java对象2
     * 
     * @param jsonstr 准备转换的json字符串
     * @param type 准备转换的类对象(e.g Student.class)
     * @param createNew Gson实例方式:true，新实例;false,存在的gson实例
     * @return 该类的java对象
     * @throws Exception
     */
    public static <T> T fromJson(String jsonstr, Type type, Boolean createNew) throws Exception {
        try {
            Gson gson = getGsonInstance(createNew);
            return gson.fromJson(jsonstr, type);
        } catch (Exception e) {
            throw new Exception(e.getMessage(), e);
        }
    }

    // ========================================我是邪恶的分割线===============================================

    /**
     * json字符窜转java对象(集合)1
     * 
     * @param jsonstr 准备转换的json字符串
     * @param type 准备转换的类对象(e.g new TypeToken<List<Student>>() {}.getType())
     * @return 该类的java对象(集合)
     * @throws Exception
     */
    public static <T> T fromJson(String jsonstr, Class<T> type) throws Exception {
        try {
            Gson gson = getGsonInstance(Boolean.FALSE);
            return gson.fromJson(jsonstr, type);
        } catch (Exception e) {
            throw new Exception(e.getMessage(), e);
        }
    }

    /**
     * json字符窜转java对象(集合)2
     * 
     * @param jsonstr 准备转换的json字符串
     * @param type 准备转换的类对象(e.g new TypeToken<List<Student>>() {}.getType())
     * @param createNew Gson实例方式:true，新实例;false,存在的gson实例
     * @return 该类的java对象(集合)
     * @throws Exception
     */
    public static <T> T fromJson(String jsonstr, Class<T> type, Boolean createNew) throws Exception {
        try {
            Gson gson = getGsonInstance(createNew);
            return gson.fromJson(jsonstr, type);
        } catch (Exception e) {
            throw new Exception(e.getMessage(), e);
        }
    }

    /**
     * 使用指定的json字符串，生成JsonElement。 支持json对象格式({xxx:xxx, yyy:yyy})、json数组格式([{xxx:xxx}, {yyy:yyy}])。
     * 
     * @param jsonString
     * @return
     */
    public static JsonElement toJsonObject(String jsonString) {
        return new JsonParser().parse(jsonString);
    }

    /**
     * 对象转换成JsonElement
     * 
     * @param src
     * @return
     * @throws Exception
     */
    public static JsonElement objToJson(Object src) throws Exception {
        Gson gson = getGsonInstance(Boolean.FALSE);
        return gson.toJsonTree(src);
    }

    /**
     * 将Json对象用Java对象的方式表现出来
     * 
     * @param element
     * @return
     */
    public static Object envelopJson(JsonElement element) {
        if (element.isJsonObject()) {
            LinkedHashMap<String, Object> map = new LinkedHashMap<String, Object>();
            Set<Entry<String, JsonElement>> entrySet = ((JsonObject) element).entrySet();
            for (Entry<String, JsonElement> entry : entrySet) {
                map.put(entry.getKey(), envelopJson(entry.getValue()));
            }
            return map;
        } else if (element.isJsonArray()) {
            ArrayList<Object> list = new ArrayList<Object>();
            Iterator<JsonElement> it = ((JsonArray) element).iterator();
            while (it.hasNext()) {
                JsonElement elChild = it.next();
                list.add(envelopJson(elChild));
            }
            return list;
        } else if (element.isJsonPrimitive()) {
            JsonPrimitive primitive = (JsonPrimitive) element;
            if (primitive.isBoolean()) {
                return element.getAsBoolean();
            }
            if (primitive.isNumber()) {
                return primitive.getAsNumber();
            } else
                return primitive.getAsString();
        }
        return null;
    }

    /**
     * 获取JsonObject指定名称的下级JsonObject
     * 
     * @param src
     * @param key
     * @return
     */
    public static JsonObject getObjectPropertyOf(JsonObject src, String key) {
        JsonElement el = src.get(key);
        if (el != null) {
            if (el.isJsonObject())
                return el.getAsJsonObject();
        }
        return null;
    }

    /**
     * 获取JsonObject指定名称的字符串属性值
     * 
     * @param src
     * @param key
     * @return
     */
    public static String getStringPropertyOf(JsonObject src, String key) {
        JsonElement el = src.get(key);
        if (el != null && !el.isJsonNull()) {
            return el.getAsString();
        }
        return null;
    }

    /**
     * 获取JsonObject指定名称的下级JsonArray
     * 
     * @param src
     * @param key
     * @return
     */
    public static JsonArray getJsonArrayPropertyOf(JsonObject src, String key) {
        JsonElement el = src.get(key);
        if (el != null) {
            if (el.isJsonArray())
                return el.getAsJsonArray();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Object> toMap(JsonObject src) {
        return (Map<String, Object>) envelopJson(src);
    }

    /**
     * 将对象转换成json格式(并自定义日期格式)
     * 
     * @param ts
     * @return
     */
    public static String objectToJsonDateSerializer(Object ts, final String dateformat) {
        String jsonStr = null;
        gson = new GsonBuilder().registerTypeAdapter(Date.class, new JsonSerializer<Date>() {
            @Override
            public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
                SimpleDateFormat format = new SimpleDateFormat(dateformat);
                return new JsonPrimitive(format.format(src));
            }
        }).setDateFormat(dateformat).create();
        if (gson != null) {
            jsonStr = gson.toJson(ts);
        }
        return jsonStr;
    }

    /**
     * 将json转换成bean对象
     * 
     * @param jsonStr
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T jsonToBean(String jsonStr, Class<T> cl) {
        Object obj = null;
        Gson gson = getGsonInstance(Boolean.FALSE);
        if (gson != null) {
            obj = gson.fromJson(jsonStr, cl);
        }
        return (T) obj;
    }

    /**
     * 将json转换成bean对象
     * 
     * @param jsonStr
     * @param cl
     * @param pattern 时间格式
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T jsonToBeanDateSerializer(String jsonStr, Class<T> cl, final String pattern) {
        Object obj = null;
        gson = new GsonBuilder().registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
            @Override
            public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                SimpleDateFormat format = new SimpleDateFormat(pattern);
                String dateStr = json.getAsString();
                try {
                    return format.parse(dateStr);
                } catch (ParseException e) {
                    throw new RuntimeException("网络异常", e);
                }
            }
        }).setDateFormat(pattern).create();
        if (gson != null) {
            obj = gson.fromJson(jsonStr, cl);
        }
        return (T) obj;
    }

    /**
     * 
     * @Title: jsonToBeanList
     * @Description: TODO json转对象集合
     * @param json
     * @param c
     * @return
     * @throws Exception
     * @return List<T>
     * @throws
     *
     *             @date 2016年6月16日 下午4:26:26
     * @author lism
     */
    public static <T> List<T> jsonToBeanList(String json, Class<T> c) throws Exception {
        JsonElement jo = toJsonObject(json);
        List<T> list = new ArrayList<T>();
        if (jo.isJsonArray()) {
            JsonArray joArr = jo.getAsJsonArray();
            for (int i = 0; i < joArr.size(); i++) {
                list.add(jsonToBean(joArr.get(i).toString(), c));
            }
        }
        return list;

    }

    /**
     * 
     *
     * 功能描述：通过jsonObject中的key,获取jsonObject中的值
     * 
     * @param jsonObject
     * @param key
     * @return String
     *
     */
    public static String getJsonObjectVal(JsonObject jsonObject, String key) {
        return jsonObject.has(key) ? jsonObject.get(key).getAsString() : "";
    }
}
