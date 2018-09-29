第一步：添加spring-boot-starter-data-redis依赖
第二步：配置@EnableCaching开启缓存
第三步：在application.yml内配置Redis相关的信息
第四步：配置@Cacheable注解完成数据缓存


注解详解：
使用@Cacheable标记的方法在执行后Spring Cache将缓存其返回结果，
而使用@CacheEvict标记的方法会在方法执行前或者执行后移除Spring Cache中的某些元素。

1. @Cacheable
@Cacheable可以标记在一个方法上，也可以标记在一个类上。
当标记在一个方法上时表示该方法是支持缓存的，当标记在一个类上时则表示该类所有的方法都是支持缓存的。

对于一个支持缓存的方法，Spring会在其被调用后将其返回值缓存起来，以保证下次利用同样的参数来执行该方法时可以直接从缓存中获取结果，而不需要再次执行该方法。
Spring在缓存方法的返回值时是以键值对进行缓存的，值就是方法的返回结果，
至于键的话，Spring又支持两种策略，默认策略和自定义策略，这个稍后会进行说明。
需要注意的是当一个支持缓存的方法在对象内部被调用时是不会触发缓存功能的。
@Cacheable可以指定三个属性，value、key和condition。

2.@CachePut
与@Cacheable不同的是使用@CachePut标注的方法在执行前不会去检查缓存中是否存在之前执行过的结果，
而是每次都会执行该方法，并将执行结果以键值对的形式存入指定的缓存中。

3.@CacheEvict
  @CacheEvict是用来标注在需要清除缓存元素的方法或类上的。当标记在一个类上时表示其中所有的方法的执行都会触发缓存的清除操作。

4.@Caching
  @Caching注解可以让我们在一个方法或者类上同时指定多个Spring Cache相关的注解

5.自定义注解
如：@SingleUserCache