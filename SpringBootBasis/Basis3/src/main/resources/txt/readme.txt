1.CORS跨域访问
①CORSConfiguration
②TestCORSController
③test文件夹下的index.html,在外部访问,测试跨域

2.@Scheduled创建定时任务
①找到入口程序SpringBootdemo3Application添加注解@EnableScheduling
②PrintTask 类上添加@Component,方法上添加 @Scheduled(cron = "表达式")

3. ApplicationEvent&Listener完成业务解耦
    ApplicationEvent以及Listener是Spring为我们提供的一个事件监听、订阅的实现，
    内部实现原理是观察者设计模式，设计初衷也是为了系统业务逻辑之间的解耦，提高可扩展性以及可维护性。
    事件发布者并不需要考虑谁去监听，监听具体的实现内容是什么，发布者的工作只是为了发布事件而已。

我们平时日常生活中也是经常会有这种情况存在，
    如：我们在平时拔河比赛中，裁判员给我们吹响了开始的信号，也就是给我们发布了一个开始的事件，
    而拔河双方人员都在监听着这个事件，一旦事件发布后双方人员就开始往自己方使劲。
    而裁判并不关心你比赛的过程，只是给你发布事件你执行就可以了。

jar包
<!--lombok-->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.16.16</version>
        </dependency>

        它可以结合Idea开发工具完成对实体的动态添加构造函数、Getter/Setter方法、toString方法等。

①创建UserRegisterEvent事件,监听都是围绕着事件来挂起的
    我们自定义事件UserRegisterEvent继承了ApplicationEvent，继承后必须重载构造函数，构造函数的参数可以任意指定，
    其中source参数指的是发生事件的对象，一般我们在发布事件时使用的是this关键字代替本类对象，
    而user参数是我们自定义的注册用户对象，该对象可以在监听内被获取。


②创建UserBean

③创建UserService
    UserService内添加一个注册方法，该方法只是实现注册事件发布功能
    事件发布是由ApplicationContext对象管控的，我们发布事件前需要注入ApplicationContext对象调用publishEvent方法完成事件发布。

④创建UserController

⑤在Spring内部中有多种方式实现监听如：
     @EventListener注解、
     实现ApplicationListener泛型接口、
     实现SmartApplicationListener接口等，
     我们下面来讲解下这三种方式分别如何实现。

<1>@EventListener实现监听 com.cn.eventandlistener.listener.annotation
    注解方式比较简单，并不需要实现任何接口--->AnnotationRegisterListener
    我们只需要让我们的监听类被Spring所管理即可，在我们用户注册监听实现方法上添加@EventListener注解，该注解会根据方法内配置的事件完成监听。

    测试事件监听:http://127.0.0.1:8080/register?name=admin&password=123456
    debug 顺序: userController,UserService,UserRegisterEvent,AnnotationRegisterListener

    总结:service触发事件,当事件走完后,然后被监听(开始走listener)
    当我们在UserService内发布了注册事件时，监听方法自动被调用并且输出内信息到控制台。

<2>ApplicationListener实现监听 com.cn.eventandlistener.listener.disorder

    RegisterListener

    这种方式也是Spring之前比较常用的监听事件方式，在实现ApplicationListener接口时需要将监听事件作为泛型传递

    我们实现接口后需要使用@Component注解来声明该监听需要被Spring注入管理，
    当有UserRegisterEvent事件发布时监听程序会自动调用onApplicationEvent方法并且将UserRegisterEvent对象作为参数传递。

    debug 顺序: userController,UserService,UserRegisterEvent,AnnotationRegisterListener



    注意:
    **事件发布后就不会考虑具体哪个监听去处理业务，甚至可以存在多个监听同时需要处理业务逻辑。**
    ps:com.cn.eventandlistener.listener.annotation

    service触发事件,当事件走完后,然后被监听(开始走listener),当一个Listener走完后,触发下一个Listener

<3>SmartApplicationListener实现有序监听 com.cn.eventandlistener.listener.order

    SmartApplicationListener接口继承了全局监听ApplicationListener，并且泛型对象使用的ApplicationEvent来作为全局监听，
    可以理解为使用SmartApplicationListener作为监听父接口的实现，监听所有事件发布。

    getOrder方法，这个方法就可以解决执行监听的顺序问题，
    return的数值越小证明优先级越高，执行顺序越靠前。

<4>使用@Async实现异步监听--->ListenerAsyncConfiguration

    @Aysnc其实是Spring内的一个组件，可以完成对类内单个或者多个方法实现异步调用，这样可以大大的节省等待耗时。
    内部实现机制是线程池任务ThreadPoolTaskExecutor，通过线程池来对配置@Async的方法或者类做出执行动作。


    com.cn.eventandlistener.listener.order.UserRegisterSendMailListener

        @Override
        @Async
        public void onApplicationEvent(ApplicationEvent applicationEvent) {
            try {
                Thread.sleep(3000);
            }catch (Exception e)
            {
                e.printStackTrace();
            }
            //转换事件类型
            UserRegisterEvent userRegisterEvent = (UserRegisterEvent) applicationEvent;
            //获取注册用户对象信息
            UserBean user = userRegisterEvent.getUser();
            System.out.println("用户："+user.getName()+"，注册成功，发送邮件通知。");
        }

    注意：
    如果存在多个监听同一个事件时，并且存在异步与同步同时存在时则不存在执行顺序。


⑥总结:
    我们在传统项目中往往各个业务逻辑之间耦合性较强，因为我们在service都是直接引用的关联service或者jpa来作为协作处理逻辑，
    然而这种方式在后期更新、维护性难度都是大大提高了。然而我们采用事件通知、事件监听形式来处理逻辑时耦合性则是可以降到最小。

4.SpringBoot使用AutoConfiguration自定义Starter

①    在注解@SpringBootApplication上存在一个开启自动化配置的注解@EnableAutoConfiguration来完成自动化配置

    在@EnableAutoConfiguration注解内使用到了@import注解来完成导入配置的功能
    ，而EnableAutoConfigurationImportSelector内部则是使用了SpringFactoriesLoader.loadFactoryNames方法进行扫描具有META-INF/spring.factories文件的jar包。

    自定义spring.factories
    我们在src/main/resource目录下创建META-INF目录，并在目录内添加文件spring.factories

②创建测试SpringBoot项目

 在使用自定义starter之前需要将starter作Maven Jar Install到本地，我们使用idea工具自带的maven命令完成该操作

 步骤：工具右侧 -> Maven Projects -> Lifecycle -> install

 pom.xml--->
            <!--自定义starter依赖-->
            <dependency>
                <groupId>com.cn</groupId>
                <artifactId>springbootdemo3</artifactId>
                <version>0.0.1-SNAPSHOT</version>
            </dependency>


    可以在HelloProperties中配置加载信息,
    也可以在application.properties文件中配置初始化信息

③总结:
    自定义starter主要操作是:
    HelloAutoConfiguration
    HelloProperties
    Springbootdemo3Application中添加注解 @EnableAutoConfiguration
    在src/main/resource目录下创建META-INF目录，并在目录内添加文件spring.factories
    application.properties

5.SpringBoot使用MapStruct自动映射DTO

    MapStruct是一种类型安全的bean映射类生成java注释处理器。
    我们要做的就是定义一个映射器接口，声明任何必需的映射方法。
    在编译的过程中，MapStruct会生成此接口的实现。
    该实现使用纯java方法调用的源和目标对象之间的映射，MapStruct节省了时间，通过生成代码完成繁琐和容易出错的代码逻辑。

    我们在数据库内创建两张表信息分别是商品基本信息表、商品类型表。
    两张表有相应的关联，我们在不采用连接查询的方式模拟使用MapStruct


    基于SpringBoot开发框架上集成MapStruct自动映射框架，完成模拟多表获取数据后将某一些字段通过@Mapping配置自动映射到DTO实体实例指定的字段内。

    核心GoodInfoMapper
    Mapper这个定义一般是被广泛应用到MyBatis半自动化ORM框架上，而这里的Mapper跟Mybatis没有关系。


    @Mappings & @Mapping

    在Mapper接口定义方法上面声明了一系列的注解映射@Mapping以及@Mappings，那么这两个注解是用来干什么工作的呢？
    @Mapping注解我们用到了两个属性，分别是source、target

    source代表的是映射接口方法内的参数名称，如果是基本类型的参数，参数名可以直接作为source的内容，如果是实体类型，则可以采用实体参数名.字段名的方式作为source的内容，配置如上面GoodInfoMapper内容所示。

    target代表的是映射到方法方法值内的字段名称，配置如GoodInfoMapper所示。


    查看Mapper实现

    执行maven compile命令(编译所有源文件生成class文件至target\classes目录下)，到target/generated-sources/annotations目录下查看对应Mapper实现类

    MapStruct根据我们配置的@Mapping注解自动将source实体内的字段进行了调用target实体内字段的setXxx方法赋值，并且做出了一切参数验证。
    我们采用了Spring方式获取Mapper，在自动生成的实现类上MapStruct为我们自动添加了@ComponentSpring声明式注入注解配置。

