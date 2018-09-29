package com.cn.session;

import com.cn.config.Configuration;
import com.cn.config.MapperStatement;
import com.cn.session.impl.DefaultSqlSession;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Properties;

/**
 * Created by Administrator on 2018\9\12 0012.
 * 两个作用：
 * 1.把配置文件加载到内存Configuration
 * 2.生产sqlSession(一个线程产生一个sqlSession)
 */
public class SqlSessionFactory {

    /**
     * 这里是单例的。
     */
    private Configuration conf = new Configuration();

    public SqlSessionFactory() {

        //加载db.properties文件加载到conf中
        loadDbInfo();
        //把mapper中的信息加载到conf中的mapperStatements中
        loadMappersInfo();
    }

    //记录mapper.xml文件存放的位置
    public static final String MAPPER_CONFIG_LOCATION = "mappers";

    public static final String DB_CONFIG_FILE = "db.properties";

    //加载数据库配置信息
    private void loadDbInfo() {

        //加载数据库信息配置文件
        InputStream dbIn = SqlSessionFactory.class.getClassLoader().getResourceAsStream(DB_CONFIG_FILE);

        Properties p = new Properties();

        try {
            //将配置信息写入Properties对象
            p.load(dbIn);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //将数据库配置信息写入conf对象
        conf.setJdbcDriver(p.get("jdbc.driver").toString());
        conf.setJdbcUrl(p.get("jdbc.url").toString());
        conf.setJdbcUsername(p.get("jdbc.username").toString());
        conf.setJdbcPassword(p.get("jdbc.password").toString());
    }

    //加载指定文件夹下的所有mapper.xml
    private void loadMappersInfo() {
        URL resources = null;
        resources = SqlSessionFactory.class.getClassLoader().getResource(MAPPER_CONFIG_LOCATION);
        //获取指定文件夹下的信息
        File mappers = new File(resources.getFile());
        if (mappers.isDirectory()) {

            File[] listFiles = mappers.listFiles();
            //遍历文件夹下所有的mapper.xml文件,解析信息后，注册至conf对象中
            for (File file : listFiles) {
                loadMapperInfo(file);
            }
        }
    }

    //加载指定的mapper.xml文件
    private void loadMapperInfo(File file) {

        //创建saxReader对象
        SAXReader reader = new SAXReader();
        //通过read方法读取一个文件 转换成Document对象
        Document document = null;
        try {
            document = reader.read(file);
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        //获取跟节点元素对象<mapper>
        Element node = document.getRootElement();
        //获取命名空间
        String namespace = node.attribute("namespace").getData().toString();

        //获取select节点列表
        List<Element> selects = node.elements("select");

        //遍历select节点，将信息记录到MapperStatement对象，并登记到configuration对象中
        for (Element element : selects) {

            MapperStatement mapperStatement = new MapperStatement();

            //读取id属性
            String id = element.attribute("id").getData().toString();

            //读取resultType属性
            String resultType = element.attribute("resultType").getData().toString();

            //读取sql语句信息
            String sql = element.getData().toString();

            String sourceId = namespace+"."+id;

            mapperStatement.setSourceId(sourceId);
            mapperStatement.setResultType(resultType);
            mapperStatement.setNamespace(namespace);
            mapperStatement.setSql(sql);

            //注册到configuration对象中
            conf.getMapperStatements().put(sourceId,mapperStatement);
        }
    }

    /**
     * 一个线程产生一个sqlSession
     * @return
     */
    public SqlSession opeanSession(){
        return new DefaultSqlSession(conf);
    }
}
