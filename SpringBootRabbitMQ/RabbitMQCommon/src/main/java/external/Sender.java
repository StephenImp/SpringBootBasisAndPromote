package external;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * RabbitMQ发送消息
 */
public class Sender {

    //队列名称
    private final static String QUEUE_NAME = "queue-test";

    public static void main(String[] argv) throws Exception {

        //1.创建一个ConnectionFactory连接工厂connectionFactory
        ConnectionFactory connectionFactory = new ConnectionFactory();
        //2.通过connectionFactory设置RabbitMQ所在IP等信息
        connectionFactory.setHost("192.168.255.129");
        connectionFactory.setPort(5672); //指定端口
        connectionFactory.setUsername("superrd");//用户名
        connectionFactory.setPassword("superrd");//密码

        //3.通过connectionFactory创建一个连接connection
        Connection connection = connectionFactory.newConnection();
        //4.通过connection创建一个频道channel
        Channel channel = connection.createChannel();
        //5.通过channel指定一个队列
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        //发送的消息
        String message = "成功了，哈哈哈";
        //6.通过channel向队列中添加消息，第一个参数是转发器，使用空的转发器（默认的转发器，类型是direct）
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        System.out.println("向" + QUEUE_NAME + "中添加了一条消息:" + message);
        //7.关闭频道
        channel.close();
        //8.关闭连接
        connection.close();
    }
}
