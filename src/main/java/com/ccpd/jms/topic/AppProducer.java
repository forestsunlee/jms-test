package com.ccpd.jms.topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @author forestsun
 * @date 2019/1/2
 */
public class AppProducer {

    //目标地址
    private static final String  url="tcp://127.0.0.1:61616";

    //队列名称
    private static final String topicName = "topic-test";

    public static void main(String[] args) throws JMSException {

        //1 创建connectionFactory

        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(url);

        //2 创建connection
        Connection connection = activeMQConnectionFactory.createConnection();

        //3启动连接
        connection.start();

        //4创建会话  第一个参数：是否使用事务  第二个参数：设置应答模式，自动应答
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        //5创建一个消息队列(主题模式)
        Destination destination = session.createTopic(topicName);

        //6创建一个生产者,并设置目的队列
        MessageProducer producer = session.createProducer(destination);

        for (int i = 0; i < 100; i++) {
            //7创建消息
            TextMessage textMessage = session.createTextMessage("test" + i);

            //8使用生产者向目标队列发布消息
            producer.send(textMessage);
            System.out.println("发送主题消息："+textMessage);
        }

        System.out.println("消息已经发送完毕");

        //9关闭连接
        connection.close();

    }
}
