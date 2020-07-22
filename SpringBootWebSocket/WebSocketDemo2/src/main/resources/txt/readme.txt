1.IndexController-->这里是前往webSocket的入口，因为用的是templates模板引擎，不能直接去请求
2.注入ServerEndpointExporter,这个bean会自动注册使用了@ServerEndpoint注解声明的Websocket endpoint。
3.websocket.html--> new WebSocket("ws://localhost:10000/websocket/111");
                    对应
 WebSocket.java -->@ServerEndpoint(value = "/websocket/{Token}")
  此时开启服务后，前端与后台可以建立长连接


4. websocket.html-->websocket.send(message);发送消息
5. WebSocket.java-->onMessage接收消息，并且通过item.sendMessage(message);将消息返回给前端


6.WebSocketController，在与页面进行连接的情况下，将发送的请求返回给前端.


请求示例：

模拟客户端①
http://localhost:8080/welcome?message=demo1


模拟客户端②
http://localhost:8080/welcome?message=demo2


当第二个客户端开启连接的时候，第一个客户端将收到消息

客户端发送连接请求  http://localhost:8080/welcome?message=demo2
把之前建立连接的websocket获取出来，给每个客户端发送消息