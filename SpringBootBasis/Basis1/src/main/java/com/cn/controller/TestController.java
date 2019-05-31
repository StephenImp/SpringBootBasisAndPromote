package com.cn.controller;

import com.alibaba.fastjson.JSONObject;
import com.cn.entity.TestObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MOZi on 2019/3/11.
 */
@RestController
@RequestMapping("test")
public class TestController {


    private String[] str1 = {"a","b"};
    private String[] str2 = {"c","d"};
    private String[] str3 = {"e","f"};

    @RequestMapping("list")
    @ResponseBody
    public List<String[]> testList(){

        List<String[]> strs = new ArrayList<>();
        strs.add(str1);
        strs.add(str2);
        strs.add(str3);

        System.out.println( JSONObject.toJSONString(strs));

        return strs;
    }


    @RequestMapping("object")
    @ResponseBody
    public List<List<TestObject>> testObject(){

        TestObject testObject1 = new TestObject("a");
        TestObject testObject2 = new TestObject("b");
        TestObject testObject3 = new TestObject("c");
        TestObject testObject4 = new TestObject("d");
        TestObject testObject5 = new TestObject("e");
        TestObject testObject6 = new TestObject("f");

        List<TestObject> list1 = new ArrayList<>();
        list1.add(testObject1);
        list1.add(testObject2);


        List<TestObject> list2 = new ArrayList<>();
        list2.add(testObject3);
        list2.add(testObject4);

        List<TestObject> list3 = new ArrayList<>();
        list3.add(testObject5);
        list3.add(testObject6);


        List<List<TestObject>> lists = new ArrayList<>();
        lists.add(list1);
        lists.add(list2);
        lists.add(list3);

        System.out.println( JSONObject.toJSONString(lists));

        return lists;

    }

    @RequestMapping("string")
    @ResponseBody
    public List<List<String>> testString(){

        List<String> string1 = new ArrayList<>();
        string1.add("a");
        string1.add("b");

        List<String> string2 = new ArrayList<>();
        string2.add("c");
        string2.add("d");

        List<String> string3 = new ArrayList<>();
        string3.add("e");
        string3.add("f");

        List<List<String>> lists = new ArrayList<>();
        lists.add(string1);
        lists.add(string2);
        lists.add(string3);

        System.out.println( JSONObject.toJSONString(lists));

        return lists;
    }



}
