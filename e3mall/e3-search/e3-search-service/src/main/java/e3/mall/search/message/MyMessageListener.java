package e3.mall.search.message;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class MyMessageListener implements MessageListener {

	@Override
	public void onMessage(Message me) {
		//取消息
		TextMessage textMessage = (TextMessage) me;
		try {
			String message = textMessage.getText();
			System.out.println(message);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
