    起始：TestMyBatis

	阶段一

	①.sqlSessionFactoy获取db信息，mapper.xml信息
	②.实例化过程中，加载配置文件创建configuration对象（放入db链接信息）
	③.通过sqlSessionFactoy创建sqlSession

	阶段二

	④.通过sqlSession获取mapper接口动态代理
	⑤.动态代理回调sqlSession中的某个查询方法

	阶段三

	⑥.sqlSession将查询方法转发给Exector
	    executor.query(ms, parameter);

	⑦.Exector基于JDBC访问数据库数据
	⑧.Exector通过反射将数据转换成POJO并返回给sqlSession
	⑨.将数据返回给调用者

	final 变量 在高并发条件下 很好的去共享

	--------------------------------------------------------------------------------------------------------------
	思路整理：

    ①sqlSessionFactoy

     * 两个作用：
     * 1.把配置文件加载到内存Configuration
     * 2.生产sqlSession(一个线程产生一个sqlSession)


    一.在数据库配置时，配置，扫描指定的xml文件，指定的entity
    初始化sqlSessionFactoy时，

        //加载db.properties文件加载到conf中
        loadDbInfo();

        //把mapper中的信息加载到conf中的mapperStatements中
        loadMappersInfo();

        利用反射    获取mapper文件夹下所有的xml文件
        利用dom4j   将xml对象转换成document对象

        将各个节点信息封装成MapperStatement对象
        再存放到    Configuration   的    private Map<String,MapperStatement> mapperStatements = new HashMap<>(); 属性中。

     二.对外提供接口，一个线程创建一个sqlSession

    ②重点还是动态代理这里

        动态代理类实现InvocationHandler接口

        //3.通过代理模式跨越面向接口编程和ibatis编程模型的鸿沟
        TUserMapper userMapper = session.getMapper(TUserMapper.class);

        1)创建一个与代理对象（sqlSession）相关联的InvocationHandler(MapperProxy)
        2)创建一个代理对象(userMapper)，代理对象的每个执行方法都会替换执行Invocation中的invoke方法



        https://www.cnblogs.com/gonjan-blog/p/6685611.html