package cn.sportsdata.webapp.youth.web.utils;

import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import cn.sportsdata.webapp.youth.common.utils.ConfigProps;

public class EmailUtils {
    private static final String PWD_RESET_SMTP_SERVER = ConfigProps.getInstance().getConfigValue("pwd.reset.smtp");
    private static final String PWD_RESET_EMAIL = ConfigProps.getInstance().getConfigValue("pwd.reset.email");
    private static final String PWD_RESET_EMAIL_PWD = ConfigProps.getInstance().getConfigValue("pwd.reset.email.pwd");
	public static boolean sendmail(final String to_email, final String subject, final String content) {
        try {
        	final Executor executor = Executors.newFixedThreadPool(10); 
        	//创建邮件发送任务  
            Runnable task = new Runnable() {

				@Override
				public void run() {
					try {
						Properties prop = new Properties();
						prop.setProperty("mail.host", PWD_RESET_SMTP_SERVER);
						prop.setProperty("mail.transport.protocol", "smtp");
						prop.setProperty("mail.smtp.auth", "true");
						//使用JavaMail发送邮件的5个步骤
						//1、创建session
						Session session = Session.getInstance(prop);
						//开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
						session.setDebug(true);
						//2、通过session得到transport对象
						Transport ts = session.getTransport();
						//3、使用邮箱的用户名和密码连上邮件服务器，发送邮件时，发件人需要提交邮箱的用户名和密码给smtp服务器，用户名和密码都通过验证之后才能够正常发送邮件给收件人。
						ts.connect(PWD_RESET_SMTP_SERVER, PWD_RESET_EMAIL, PWD_RESET_EMAIL_PWD);
						//4、创建邮件
						//创建邮件对象
						MimeMessage message = new MimeMessage(session);
						//指明邮件的发件人
						message.setFrom(new InternetAddress(PWD_RESET_EMAIL));
						//指明邮件的收件人，现在发件人和收件人是一样的，那就是自己给自己发
						message.setRecipient(Message.RecipientType.TO, new InternetAddress(to_email));
						//邮件的标题
						message.setSubject(MimeUtility.encodeText(subject, "UTF-8", "B"));
						//邮件的文本内容
						message.setContent(content, "text/html;charset=UTF-8");
						//返回创建好的邮件对象
						//5、发送邮件
						ts.sendMessage(message, message.getAllRecipients());
						ts.close();
					}
			        catch (Exception e) {
			            // TODO: handle exception 
			            e.printStackTrace();
			        }
				}  
            };
            executor.execute(task);  
			return true;
        }
        catch (Exception e) {
            // TODO: handle exception 
            e.printStackTrace();
            return false;
        }
	}
}
