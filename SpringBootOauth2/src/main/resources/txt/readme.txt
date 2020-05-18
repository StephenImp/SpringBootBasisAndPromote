1.因为OAuth2内部操作数据库使用的JdbcTemplate我们只需要传入一个DataSource对象就可以了，实体并不需要配置。

2. 用户实体以及角色实体是用来配置SpringSecurity时用到的实体，
我们配置SpringSecurity时需要使用SpringDataJPA从数据库中读取数据，

3.SpringSecurity在使用数据库的数据时需要自定义UserDetailsService
用来从数据库中根据用户名查询用户信息以及角色信息并返回给SpringSecurity存放到内存中。

4.
utl:
http://localhost:8080/oauth/token?username=admin&password=admin&grant_type=password
post方法

username和password错误,都会导致无法生成token

Authorization  type--->Basic Auth
               username = yuqiyu_home_pc
               password = yuqiyu_secret

这个获取token的用户名密码在applocation.properties中配置

得到的数据:
               {
                   "access_token": "65b758b3-97dd-48ec-823d-32ab0c6418d6",
                   "token_type": "bearer",
                   "refresh_token": "a5797057-8547-4668-8b8f-934ad4759423",
                   "expires_in": 1799,
                   "scope": "read write"
               }

access_token：本地访问获取到的access_token，会自动写入到数据库中。
token_type：获取到的access_token的授权方式
refersh_token：刷新token时所用到的授权token
expires_in：有效期（从获取开始计时，值秒后过期）
scope：客户端的接口操作权限（read：读，write：写）

访问页面
http://127.0.0.1:8080/secure?access_token=65b758b3-97dd-48ec-823d-32ab0c6418d6
get方法

页面正常显示

token失效后,刷新token
http://localhost:8080/oauth/token?grant_type=refresh_token&refresh_token=c1407d5c-fe25-4398-a396-38fd43808ace
get方法

5.OAuth2主要作用是

“客户端”不能直接登录“服务提供商”，只能登录授权层，以此将用户与客户端分离。
“客户端”登录需要OAuth提供的令牌，否则将提示认证失败而导致客户端无法访问服务。


OAuth2为我们提供了四种授权方式：

1、授权码模式（authorization code）
2、简化模式（implicit）
3、密码模式（resource owner password credentials）
4、客户端模式（client credentials）


https://blog.csdn.net/cauchy6317/article/details/85162225

https://www.jianshu.com/p/ded9dc32f550
https://www.jianshu.com/p/63115c71a590