package com.ccpd.jms.topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/** 主题模式下，要先启动消费者，因为这样才算是先订阅，才能收到消息。且所有的消费者收到的消息是一样的
 * @author forestsun
 * @date 2019/1/2
 */
public class AppConsumer {

    //目标地址
    public static final String url="tcp://127.0.0.1:61616";

    //队列名称
    public static  final  String topicName="topic-test";

    public static void main(String[] args) throws JMSException {

        //创建一个connectionFactory
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(url);

        //创建一个连接
        Connection connection = activeMQConnectionFactory.createConnection();

        //开启连接
        connection.start();

        //获得一个会话
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        //创建一个消息队列(主题模式)
        Destination destination = session.createTopic(topicName);

        //创建一个消费者
        MessageConsumer consumer1 = session.createConsumer(destination);

        //创建一个监听器
        consumer1.setMessageListener(new MessageListener() {
            public void onMessage(Message message) {
                TextMessage msg = (TextMessage) message;
                System.out.println("消费者1接收到主题消息："+msg);
            }
        });

        //创建第二个消费者
        MessageConsumer consumer2 = session.createConsumer(destination);

        //创建一个监听器
        consumer2.setMessageListener(new MessageListener() {
            public void onMessage(Message message) {
                TextMessage msg = (TextMessage) message;
                System.out.println("消费者2接收到主题消息："+msg);
            }
        });


    }
}
