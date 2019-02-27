①SimpleContextListener  implements ServletContextListener
    1.
        //扫描所有的bean---扫描所有的class文件。
        p.scanPackage("com.cn.springmvc");//写到preperties

    2.
        //将通过controller和service标注的实体类全部通过反射获取实例。
         simpleApplicationContext.initBean();

        为每个class文件创建类的实例，并存入map中。
            1)如果是@EnjoyController
               再通过  @EnjoyRequestMaping     拿到请求路径
               beans.put(rmvalue, instance);

             2)如果是@EnjoyService     拿到ServiceImpl的实例和对应的bean的名称
               beans.put(enjoyService.value(), instance);

         这里将扫描的bean放在容器中SimpleApplicationContext


    3.

        // 给controller类中的属性赋值。
        simpleApplicationContext.giveValue();

        通过@EnjoyController 注解获得class字节码文件
        并通过这些字节码文件  获取属性，
        如果有@EnjoyAutowired  标识，则加入到容器中


        service层同理。

    4.
    //  获取请求地址    demo/query
    simpleApplicationContext.obtainUrlMapping();

    在@EnjoyController 类上 标注了@EnjoyRequestMaping 方法的，或者方法上标注了@EnjoyRequestMaping

    handleMap.put(classPath + methodPath, method);

②EnjoyDispatcherServlet

    EnjoyDispatcherServlet  继承  HttpServlet
    在servlet初始化时，将继承了ApplicationContextAware这个接口的类中的simpleApplicationContext这个属性进行赋值。
    这样就能获取自定义的容器。

   根据请求的地址，获取参数，通过反射，执行方法。


   ApplicationContextAware

   Spring容器会检测容器中的所有Bean，如果发现某个Bean实现了ApplicationContextAware接口，Spring容器会在创建该Bean之后，
   自动调用该Bean的setApplicationContextAware()方法，调用该方法时，
   会将容器本身作为参数传给该方法

   ——该方法中的实现部分将Spring传入的参数（容器本身）赋给该类对象的applicationContext实例变量，
   因此接下来可以通过该applicationContext实例变量来访问容器本身。


