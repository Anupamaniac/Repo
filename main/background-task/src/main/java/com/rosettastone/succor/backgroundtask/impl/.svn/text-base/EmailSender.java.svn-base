package com.rosettastone.succor.backgroundtask.impl;

import java.util.Date;

import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.rosettastone.succor.backgroundtask.model.Task;
import com.rosettastone.succor.timertask.dao.ReportDao;
import com.rosettastone.succor.timertask.model.ReportEntityType;

/**
 * EmailSender uses for sending email to administrators about some errors.
 */
public class EmailSender {

    private static final Log LOG = LogFactory.getLog(EmailSender.class);

    private JavaMailSender mailSender;
    private String[] emailList;
    private String mailFrom;

    private ExceptionNotificationStatusWatcher watcher;
    private ReportDao reportDao;

    public void sendEmail(Task task, Throwable exception) throws Exception {
        synchronized (watcher) {
            if (watcher.needNotify(task, exception)) {
                LOG.debug("Notify admin: task id #" + task.getId() + " | exception " + exception.getClass().getName());
                MimeMessage message = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message);
                for (String email : emailList) {
                    helper.addTo(email);
                }
                // TODO move subject, from fields to property file
                helper.setSubject("Success Correspondence - error notification");
                StringBuilder buffer = new StringBuilder("An exception occoured during execution: \n");
                buffer.append("Task id: #" + task.getId() + "\n");
                buffer.append("Time: " + new Date() + "\n");
                if (task.getMessage() != null) {
                    buffer.append("\n-----------------------------------------------------------------\n\n");
                    buffer.append("RabbitMQ message: \n");
                    if(task != null && task.getMessage() != null){
                    	buffer.append(task.getMessage().getMessage());
                    }
                }
                buffer.append("-----------------------------------------------------------------\n\n");
                buffer.append(Util.stacktraceToString(exception));
                buffer.append("-----------------------------------------------------------------\n\n");
                buffer.append("This is automatically generated message.\n");
                buffer.append("Please do not reply.\n");
                helper.setText(buffer.toString());
                helper.setFrom(mailFrom);
                mailSender.send(message);
                watcher.notificationSent(task, exception);
                reportDao.createNewEntity(ReportEntityType.EMAIL_ERROR);
            }
        }
    }

    @Required
    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Required
    public void setEmailList(String emailList) {
        this.emailList = emailList.split(",");
    }

    @Required
    public void setMailFrom(String mailFrom) {
        this.mailFrom = mailFrom;
    }

    @Required
    public void setWatcher(ExceptionNotificationStatusWatcher watcher) {
        this.watcher = watcher;
    }

    @Required
    public void setReportDao(ReportDao reportDao) {
        this.reportDao = reportDao;
    }
}
