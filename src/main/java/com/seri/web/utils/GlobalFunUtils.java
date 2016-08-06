package com.seri.web.utils;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import com.seri.service.notification.Notification;
import com.seri.service.notification.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.seri.web.dao.daoImpl.UserDaoImpl;
import com.seri.web.model.User;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by puneet on 02/04/16.
 */
@Component
public class GlobalFunUtils {

    @Autowired
    private NotificationService notificationService;

    public String getDateTime(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //get current date time with Date()
        Date date = new Date();
        return dateFormat.format(date);
    }

    public void inActiveAllTransaction(EntityManager em){
        if(em.getTransaction().isActive())
            em.getTransaction().rollback();
    }

    public static String getMd5Hex(String inputString) {
        
        //convert the byte to hex format method 2
		StringBuffer hexString = new StringBuffer();;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(inputString.getBytes());

			byte byteData[] = md.digest();

			//convert the byte to hex format method 1
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < byteData.length; i++) {
			    sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
			}

			//System.out.println("Digest(in hex format):: " + sb.toString());

			for (int i=0;i<byteData.length;i++) {
			    String hex=Integer.toHexString(0xff & byteData[i]);
			    if(hex.length()==1) hexString.append('0');
			    hexString.append(hex);
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
        return hexString.toString();
    }

    public static Boolean sendMail(Map<String, String> map){
        // Recipient's email ID needs to be mentioned.
        String to = map.get("to");

        // Sender's email ID needs to be mentioned
        String from = map.get("from");

        final String username = "seripanel01@gmail.com";//change accordingly
        final String password = "Nwac@123";//change accordingly

        // Assuming you are sending email through relay.jangosmtp.net
        String host = "smtp.gmail.com";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");

        // Get the Session object.
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            // Create a default MimeMessage object.
            Message message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

            // Set Subject: header field
            message.setSubject(map.get("subject"));

            // Now set the actual message
            message.setText(map.get("body"));

            // Send message
            Transport.send(message);

            System.out.println("Sent message successfully....");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    public static String getSiteUrl(HttpServletRequest request) throws IOException {

        String path = request.getContextPath();

        String getProtocol=request.getScheme();
        String getDomain=request.getServerName();
        Integer getPort=request.getServerPort();

        String getPath = getProtocol+"://"+getDomain+
                (getPort==80 || getPort==443 ? ":"+path : ":"+getPort+path).trim();

        //String path = request.getContextPath()+"/";
        String uri = request.getScheme() + "://" +
                request.getServerName() +
                ("http".equals(request.getScheme()) && request.getServerPort() == 80 || "https".equals(request.getScheme()) && request.getServerPort() == 443 ? path : ":" + request.getServerPort()+path );

        return getPath;
    }

    public Map<String, String> getLoggedInUserDetails(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Map<String, String> userDetails = null;
        if(auth.isAuthenticated() && !auth.getName().equals("anonymousUser")) {
            String name = auth.getName();
            userDetails = (Map<String, String>) auth.getDetails();
        }
        return userDetails;
    }

    public User getLoggedInUserDetail(){
        UserDaoImpl userDao = new UserDaoImpl();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User userDetails = new User();
        if(auth.isAuthenticated() && !auth.getName().equals("anonymousUser")) {
            String name = auth.getName();
            userDetails = userDao.getUserUsingEmail(name);
        }
        return userDetails;
    }

    public void getNotification(ModelAndView model){
        List<Notification> notificationList = notificationService.getNotificationForUser(LoggedUserUtil.getUser());
        model.addObject("notificationList", notificationList);
    }
    
    public static List<Long> convertInLongList(String[] array){
    	List<Long> longList = new ArrayList<Long>();
    	if(array == null){
    		longList.add(0L);
    		return longList;
    	}
    	for(int i=0 ; i < array.length ; i++)
    		longList.add(Long.valueOf(array[i]));
    	return longList;
    }
    
}
