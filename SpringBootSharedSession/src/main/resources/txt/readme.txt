一、Session的概念

Session 是存放在服务器端的，类似于Session结构来存放用户数据，
当浏览器 第一次发送请求时，服务器自动生成了一个Session和一个Session ID用来唯一标识这个Session，
并将其通过响应发送到浏览器。当浏览器第二次发送请求，会将前一次服务器响应中的Session ID放在请求中一并发送到服务器上，
服务器从请求中提取出Session ID，并和保存的所有Session ID进行对比，找到这个用户对应的Session。


二、Session的客户端实现形式（即Session ID的保存方法）

[1] 使用Cookie来保存，这是最常见的方法，本文“记住我的登录状态”功能的实现正式基于这种方式的。
服务器通过设置Cookie的方式将Session ID发送到浏览器。如果我们不设置这个过期时间，
那么这个Cookie将不存放在硬盘上，当浏览器关闭的时候，Cookie就消失了，这个Session ID就丢失了。
如果我们设置这个时间为若干天之后，那么这个Cookie会保存在客户端硬盘中，即使浏览器关闭，这个值仍然存在，下次访问相应网站时，同 样会发送到服务器上。


[2] 使用URL附加信息的方式，也就是像我们经常看到JSP网站会有aaa.jsp?JSESSIONID=*一样的。这种方式和第一种方式里面不设置Cookie过期时间是一样的。


[3] 第三种方式是在页面表单里面增加隐藏域，这种方式实际上和第二种方式一样，只不过前者通过GET方式发送数据，后者使用POST方式发送数据。但是明显后者比较麻烦。


cookie与session的区别：

cookie数据保存在客户端，session数据保存在服务器端。