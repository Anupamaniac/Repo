package com.rosettastone.succor.timertask;

import java.util.Date;
import java.util.Locale;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import com.rosettastone.succor.exception.ApplicationException;
import com.rosettastone.succor.timertask.dao.EmailReportHistoryDao;
import com.rosettastone.succor.timertask.dao.ReportDao;
import com.rosettastone.succor.timertask.model.EmailReportHistory;
import com.rosettastone.succor.timertask.model.ReportEntityType;
import com.rosettastone.succor.timertask.model.StatisticBean;

/**
 * Is is task for spring timer.
 * It generates statistic and sends by email.
 */
public class EmailReportTimer {

    private static final Log LOG = LogFactory.getLog(EmailReportTimer.class);

    private EmailReportHistoryDao emailReportHistoryDao;
    private ReportDao reportDao;

    private TransactionTemplate transactionTemplate;

    private JavaMailSender mailSender;
    private String[] emailList;
    private String mailFrom;

    private boolean lastExecutionFailed = false;
    
    private ReadWriteLock readWriteLock;

    /**
     * Main method for cron-like scheduler
     */
    public synchronized void mainTimer() throws Throwable {
        LOG.debug("mainTimer");
        executeInTransaction();
    }

    /**
     * Main method for simple scheduler
     * It works only if some error occured in last call of @method mainTimer.
     */
    public synchronized void checkFailureTimer() throws MessagingException {
        LOG.debug("checkFailureTimer");
        if (lastExecutionFailed) {
            executeInTransaction();
        }
    }

    /**
     * Execute all work in transaction with locking all task executors.
     */
    private void executeInTransaction() {
        lastExecutionFailed = true;
        transactionTemplate.execute(new TransactionCallback<Void>() {
            @Override
            public Void doInTransaction(TransactionStatus status) {
                Lock writeLock = readWriteLock.writeLock();
                writeLock.lock();
                try {
                    execute();
                } catch (MessagingException e) {
                    throw new ApplicationException("Can not send statistic email", e);
                } finally {
                    writeLock.unlock();
                }
                return null;
            }
        });
        lastExecutionFailed = false;
    }

    /**
     * Algorithm that is running in transaction.
     * @throws MessagingException
     */
    private void execute() throws MessagingException {
        LOG.debug("Do send statistic");
        EmailReportHistory newExecution = new EmailReportHistory();
        newExecution.setDate(new Date());
        StatisticBean statistic = generateStatistic(newExecution);
        sendStatistic(statistic);
        emailReportHistoryDao.create(newExecution);
        LOG.debug("Statistic was sent");
        reportDao.cleanDb(statistic.getEndDate());
    }

    /**
     * Load statistic information from DB.
     * @param newExecution
     * @return
     */
    private StatisticBean generateStatistic(EmailReportHistory newExecution) {
        EmailReportHistory lastExecution = emailReportHistoryDao.loadLast();
        Date startDate;
        if (lastExecution == null) {
            startDate = new Date(0);
        } else {
            startDate = lastExecution.getDate();
        }
        return reportDao.generateStatistic(startDate, newExecution.getDate());
    }

    /**
     * Send statistic by email
     * @param statistic model object
     * @throws MessagingException
     */
    private void sendStatistic(StatisticBean statistic) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setSubject("Success Correspondence - statistic");
        helper.setFrom(mailFrom);
        for (String email : emailList) {
            helper.addTo(email);
        }

        StringBuilder builder = new StringBuilder(String.format(Locale.US,
                "Statistics for the period from %1$tb %1$td, %1$tY %1$tH:%1$tM to %2$tb %2$td, %2$tY %2$tH:%2$tM",
                statistic.getStartDate(),
                statistic.getEndDate()));
        builder.append("\n-----------------------------------------------------------------\n");
        builder.append("\nStatistics for the period from " + " to " + ":");
        builder.append("\nMessages skipped(N/A): " + statistic.get(ReportEntityType.MESSAGE_SKIPPED));
        builder.append("\nEmails sent with error messages: " + statistic.get(ReportEntityType.EMAIL_ERROR));
        builder.append("\nEmails sent to the customers: " + statistic.get(ReportEntityType.EMAIL_CUSTOMER));

        builder.append("\n\n\nParature API statistic:");
        builder.append("\nTicket Created/Updated, total: " + statistic.getTicketTotal());
        builder.append("\nSuperticket updated: " + statistic.get(ReportEntityType.SUPER_TIKET));
        builder.append("\nPostal ticket created: " + statistic.get(ReportEntityType.POSTAL_TICKET));
        builder.append("\nPhone ticket created: " + statistic.get(ReportEntityType.PHONE_TICKET));
        builder.append("\nInstitutional ticket created: " + statistic.get(ReportEntityType.INSTITUTIONAL_TICKET));

        builder.append("\n-----------------------------------------------------------------\n\n");
        builder.append("This is automatically generated message.\n");
        builder.append("Please do not reply.\n");

        helper.setText(builder.toString());
        mailSender.send(message);
    }

    @Required
    public void setEmailReportHistoryDao(EmailReportHistoryDao emailReportHistoryDao) {
        this.emailReportHistoryDao = emailReportHistoryDao;
    }

    @Required
    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Required
    public void setEmailList(String[] emailList) {
        this.emailList = emailList;
    }

    @Required
    public void setMailFrom(String mailFrom) {
        this.mailFrom = mailFrom;
    }

    @Required
    public void setReportDao(ReportDao reportDao) {
        this.reportDao = reportDao;
    }

    @Required
    public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
        this.transactionTemplate = transactionTemplate;
    }

    @Required
    public void setReadWriteLock(ReadWriteLock readWriteLock) {
        this.readWriteLock = readWriteLock;
    }
}
