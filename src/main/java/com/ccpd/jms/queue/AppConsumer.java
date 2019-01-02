package com.ccpd.jms.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**队列模式下，2个消费者是随机消费消息的，队列中的每一条消息只能被一个消费者消费
 * @author forestsun
 * @date 2019/1/2
 */
public class AppConsumer {

    //目标地址
    public static final String url="tcp://127.0.0.1:61616";

    //队列名称
    public static  final  String queueName="queue-test";

    public static void main(String[] args) throws JMSException {

        //创建一个connectionFactory
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(url);

        //创建一个连接
        Connection connection = activeMQConnectionFactory.createConnection();

        //开启连接
        connection.start();

        //获得一个会话
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        //创建一个消息队列
        Destination destination = session.createQueue(queueName);

        //创建一个消费者
        MessageConsumer consumer1 = session.createConsumer(destination);

        //创建一个监听器
        consumer1.setMessageListener(new MessageListener() {
            public void onMessage(Message message) {
                TextMessage msg = (TextMessage) message;
                System.out.println("消费者1接收到消息："+msg);
            }
        });

        //创建第二个消费者
        MessageConsumer consumer2 = session.createConsumer(destination);

        //创建一个监听器
        consumer2.setMessageListener(new MessageListener() {
            public void onMessage(Message message) {
                TextMessage msg = (TextMessage) message;
                System.out.println("消费者2接收到消息："+msg);
            }
        });


    }
}
