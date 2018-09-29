
	阶段一

	①.sqlSessionFactoy获取db信息，mapper.xml信息
	②.实例化过程中，加载配置文件创建configuration对象（放入db链接信息）
	③.通过sqlSessionFactoy创建sqlSession

	阶段二

	④.通过sqlSession获取mapper接口动态代理
	⑤.动态代理回调sqlSession中的某个查询方法

	阶段三

	⑥.sqlSession将查询方法转发给Exector
	⑦.Exector基于JDBC访问数据库数据
	⑧.Exector通过反射将数据转换成POJO并返回给sqlSession
	⑨.将数据返回给调用者

	final 变量 在高并发条件下 很好的去共享