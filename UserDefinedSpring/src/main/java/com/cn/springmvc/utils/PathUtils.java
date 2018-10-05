package com.cn.springmvc.utils;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2018/8/10.
 */
public class PathUtils {

    public static List<String> classNames = new ArrayList<>();

    /**
     * 扫描所有的bean---扫描所有的class文件。
     *
     * @param basePackage 类的包名
     */
    public void scanPackage(String basePackage) {
        //basePackage.replaceAll("\\.", "/") --->  com/cn/demoLearn
        //拿到实际class文件的物理路径（把 “.” 换成 “/”）
        //file:/D:/SpringBootBasisAndPromote/SpringUserDefined/out/artifacts/SpringUserDefined_Web_exploded/WEB-INF/classes/com/cn/demoLearn/
        URL url = this.getClass().getClassLoader().getResource("/" + basePackage.replaceAll("\\.", "/"));

        ///D:/SpringBootBasisAndPromote/SpringUserDefined/out/artifacts/SpringUserDefined_Web_exploded/WEB-INF/classes/com/cn/demoLearn/
        String fileStr = url.getFile();

        //使用File类打开一个文件，如果当前文件不存在则创建新文件
        File file = new File(fileStr);

        //获取所有文件的路径(com.cn下的所有文件夹)
        String[] fileStrs = file.list();

        /**
         * 找出所有的class文件
         */
        for (String path : fileStrs) {

            File filePath = new File(fileStr + path);

            /**
             * 判断是否为文件夹,如果是文件夹就继续找
             */
            if (filePath.isDirectory()) {
                scanPackage(basePackage + "." + path);
            } else {
                //加入List(Map)也可以。
                classNames.add(basePackage + "." + filePath.getName());//路劲+class类名  eg:EnjoyAutowired.class
            }
        }

    }



}
