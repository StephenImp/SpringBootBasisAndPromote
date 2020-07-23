① 基本流程
https://www.jianshu.com/p/aea406f24b77

启动服务，打开 http://localhost:8080/nasus  客服端 与服务端 建立连接，并且订阅指定的请求

点击发送 请求 /hello,处理业务，返回前端


② 通过 websocket 的拦截器与监听器 实现 鉴权
https://blog.csdn.net/a2231476020/article/details/99465532?utm_medium=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-2.nonecase&depth_1-utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-2.nonecase


鉴权
https://www.cnblogs.com/taich-flute/p/11971557.html
在连接建立时，检查连接的HTTP请求头信息（比如cookies中关于用户的身份信息）
在每次接收到消息时，检查连接是否已授权过，及授权是否过期
以上两点，只要答案为否，则服务端主动关闭socket连接