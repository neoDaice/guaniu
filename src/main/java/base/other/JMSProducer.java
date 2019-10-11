package base.other;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.activemq.ActiveMQConnection;
import org.activemq.ActiveMQConnectionFactory;

/**
 * 消息发布者
 *
 */
public class JMSProducer {

    private static final String USERNAME = "admin";

    private static final String PASSWORD = "admin";

    private static final String BROKERURL = ActiveMQConnection.DEFAULT_BROKER_URL;

    private static final int SENDNUM = 10;

    public static void main(String[] args) {
        ConnectionFactory connectionFactory = null; //连接工厂
        Connection connection = null; //连接
        Session session = null; //会话接收或者发送消息的线程
        Destination destination = null; //消息的目的地
        MessageProducer messageProducer = null; //消息生产者
        //实例化连接工厂
        connectionFactory = new ActiveMQConnectionFactory(USERNAME, PASSWORD, BROKERURL);
        try {
            //通过连接工厂获取连接
            connection = connectionFactory.createConnection();
            connection.start();
            session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
            destination = session.createQueue("procure_order_export");

            messageProducer = session.createProducer(destination);

            sendMessage(session, messageProducer);

            session.commit();
        } catch (JMSException e) {
            e.printStackTrace();
        } finally {
            if(connection != null){
                try {
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void sendMessage(Session session, MessageProducer messageProducer) {
        try {
            TextMessage textMessage = session.createTextMessage("{\"orderIds\":[13984065],\"userId\":10372,\"userName\":\"daice\",\"mode\":\"BATCH\",\"createTime\":null,\"withImage\":true,\"extras\":null}");
            System.out.println("发送消息：ActiveMQ发送的消息");
            messageProducer.send(textMessage);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

}
