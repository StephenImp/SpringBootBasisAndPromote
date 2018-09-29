①添加jar包
		<dependency>
			<groupId>javax.servlet</groupId>
			<artifactId>javax.servlet-api</artifactId>
		</dependency>

②
方式1：
   TestServlet+ServletConfiguration(@Configuration+@Bean)
   这个没问题

方式2：
                                                          加载启动类上面
   TestServlet(@WebServlet(urlPatterns = "/testServlet"))+ServletConfiguration(@ServletComponentScan)

   @WebServlet(asyncSupported = true,value = "/async")

   value = "/async"  如果去掉/会他妈报错，我曹