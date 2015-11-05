package org.base;

import org.quartz.Job;
import org.quartz.JobExecutionContext;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.*;

public class JobOne implements Job {
    private static String current = "1.0.0.1";
    final String username = "xxx@gmail.com";
    final String password = "xxxxx*";

    public void execute(JobExecutionContext context) {
        System.out.println("test !" + new Date());

        String str;

        String ipTemp = null;
        try {
            ipTemp = ipWindows() + getIPAddress().toString();
        } catch (SocketException e) {
            e.printStackTrace();
        }


        str = ipTemp + "\n\n************* \n\nhave a nice day";

        if (ipTemp.hashCode() != current.hashCode()) {
            sendMail(str);
        }
        current = ipTemp;

    }

    private String ipWindows() {
        InetAddress ip = null;
        try {
            ip = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        String ipTemp = ip.getHostAddress();
        return ipTemp;
    }


    public static List<InetAddress> getIPAddress() throws SocketException {

        List<InetAddress> ipAddresses = new ArrayList<InetAddress>();
        Enumeration e;
        e = NetworkInterface.getNetworkInterfaces();
        while (e.hasMoreElements()) {
            NetworkInterface ni = (NetworkInterface) e.nextElement();
            if (ni.isLoopback() || !ni.isUp()) continue;

            for (Enumeration e2 = ni.getInetAddresses(); e2.hasMoreElements(); ) {
                InetAddress ip = (InetAddress) e2.nextElement();
                ipAddresses.add(ip);
            }
        }
        return ipAddresses;
    }

    private void sendMail(String str) {
        final String username = this.username;
        final String password = this.password;

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("demirelozgur@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("ozgur.demirel@etiya.com"));
            message.setSubject("local ip address");
            message.setText("\nmy ip adress is :" + "\n\n" + str + "");
            Transport.send(message);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}