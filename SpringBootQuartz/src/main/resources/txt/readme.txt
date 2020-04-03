定时任务是在项目启动后2秒进行执行初始化，并且通过ClusterManager来完成了instance的创建，
创建的节点唯一标识为yuqiyu1509876084785。


quartz.properties配置文件一定要放在classpath下，放在别的位置有部分功能不会生效。

quzrtz.properties 配置详解

持久化方式配置数据驱动，MySQL数据库
org.quartz.jobStore.driverDelegateClass = org.quartz.impl.jdbcjobstore.StdJDBCDelegate

属性配置了定时任务数据表的前缀，在quartz官方提供的创建表SQL脚本默认就是qrtz_
org.quartz.jobStore.tablePrefix

属性配置了开启定时任务分布式功能，再开启分布式时对应属性org.quartz.scheduler.instanceId 改成Auto配置即可，
实例唯一标识会自动生成，这个标识具体生成的内容，我们一会在运行的控制台就可以看到了，定时任务分布式准备好后会输出相关的分布式节点配置信息。
这个属性才是真正的开启了定时任务的分布式配置，当我们配置为true时quartz框架就会调用ClusterManager来初始化分布式节点。
org.quartz.jobStore.isClustered

定时任务的实例编号，如果手动指定需要保证每个节点的唯一性，
因为quartz不允许出现两个相同instanceId的节点，我们这里指定为Auto就可以了，我们把生成编号的任务交给quartz。
org.quartz.scheduler.instanceId


quartz表
quartz_job_details  --- quartz记录任务的表


jobName和groupName能确定  唯一一条quartz_job_details
quartz中的ClusterManager能帮我们自动的管理mysql中的数据，做到分布式定时任务。

那么，我们思考一下原理是什么？
猜测：
    肯定是用到了分布式锁(估计它是基于mysql的)
    ①用一张表，记录哪个节点在跑定时任务，那么其他的节点不跑
    ②宕机后，其他机器重启任务时，根据数据状态去处理数据。


就已合力这个为例，数据导入过程中，宕机，那么，只能是，根据文件名，把之前的已经导入的数据，全部删除，重新导入。
这里就要用到quartz中misfirm处理机制

任务框架quartz的misfire的理解

在trigger中可以设置各种策略，实现容灾。

https://www.jianshu.com/p/634d2a6fae7b

分布式定时任务的两种处理方式
抢占式：谁先获得资源谁执行，这种模式无法将单个任务的数据交给其他节点协同处理，一般用于处理数据量较小、任务较多的场景。
协同式：可以将单个任务处理的数据均分到多个JVM中处理，提高数据的并行处理能力。

通过分布式锁互斥执行，详见https://blog.csdn.net/penguinhao/article/details/83515024

demo
https://blog.csdn.net/qq_24986595/article/details/89966520
