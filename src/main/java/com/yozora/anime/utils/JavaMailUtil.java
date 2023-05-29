package com.yozora.anime.utils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.util.MimeType;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class JavaMailUtil {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    public void send(String email,String code) throws Exception {

        Configuration cfg = freeMarkerConfigurer.getConfiguration();
        Template template = cfg.getTemplate("register.ftl");
        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("email",email );
        dataModel.put("registCode",code);
        String temp= FreeMarkerTemplateUtils.processTemplateIntoString(template,dataModel);


        MimeMessage message = mailSender.createMimeMessage();
        // 使用 MimeMessageHelper 构建复杂邮件
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setSubject("激活你的夜空动漫账号");
        helper.setSentDate(new Date());
        helper.setFrom("logfrezze@hotmail.com");  // 设置发件人邮箱
        helper.setTo(email);          // 设置收件人邮箱

        MimeBodyPart mimeBodyPart=new MimeBodyPart();
        mimeBodyPart.setContent(temp,  "text/html; charset=utf-8");

        MimeMultipart mimeMultipart=new MimeMultipart();
        mimeMultipart.addBodyPart(mimeBodyPart);

        message.setContent(mimeMultipart);
        // 发送邮件
        mailSender.send(message);


    }





}
