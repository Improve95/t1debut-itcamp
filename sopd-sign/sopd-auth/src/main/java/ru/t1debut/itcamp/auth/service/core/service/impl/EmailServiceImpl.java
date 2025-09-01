package ru.t1debut.itcamp.auth.service.core.service.impl;

import org.springframework.stereotype.Service;
import ru.t1debut.itcamp.auth.service.api.exception.ServiceException;
import ru.t1debut.itcamp.auth.service.core.service.EmailService;

import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Hashtable;

import static ru.t1debut.itcamp.auth.service.api.exception.ErrorCode.ILLEGAL_VALUE;
import static ru.t1debut.itcamp.auth.service.api.exception.ErrorCode.INTERNAL_SERVER_ERROR;

@Service
public class EmailServiceImpl implements EmailService {

    @Override
    public void emailAddressIsExist(String email) {
        String smtpDomainAddress = checkEmailDomainExist(email);
        if (smtpDomainAddress == null || !checkEmailAddressExist(email, smtpDomainAddress)) {
            throw new ServiceException(ILLEGAL_VALUE, "email");
        }
    }

    private String checkEmailDomainExist(String email) {
        try {
            String domain = email.substring(email.indexOf('@') + 1);
            Hashtable<String, String> env = new Hashtable<>();
            env.put("java.naming.factory.initial", "com.sun.jndi.dns.DnsContextFactory");
            DirContext ctx = new InitialDirContext(env);
            Attributes attrs = ctx.getAttributes(domain, new String[]{"MX"});
            Attribute attr = attrs.get("MX");
            if (attr == null) return null;
            return ((String) attrs.get("MX").get(0)).split(" ")[1];
        } catch (Exception ex) {
            throw new ServiceException(INTERNAL_SERVER_ERROR);
        }
    }

    private boolean checkEmailAddressExist(String email, String smtpDomainAddress) {
        String domain = email.substring(email.indexOf('@') + 1);
        try (Socket socket = new Socket(smtpDomainAddress, 25);
             InputStream in = socket.getInputStream();
             OutputStream out = socket.getOutputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(in));
             PrintWriter writer = new PrintWriter(out, true)
        ) {
            String line = reader.readLine();
            if (!line.startsWith("220")) return false;

            writer.println("HELO example.com");
            line = reader.readLine();
            if (!line.startsWith("250")) return false;

            writer.println("MAIL FROM: <check@" + domain + ">");
            line = reader.readLine();
            if (!line.startsWith("250")) return false;

            writer.println("RCPT TO: <" + email + ">");
            line = reader.readLine();
            return line.startsWith("250");
        } catch (IOException ex) {
            throw new ServiceException(INTERNAL_SERVER_ERROR);
        }
    }
}
