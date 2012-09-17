package com.rosettastone.succor.service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import com.rosettastone.succor.exception.FTPException;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.beans.factory.annotation.Required;

import com.rosettastone.succor.backgroundtask.impl.ExceptionNotificationStatusWatcher;
import com.rosettastone.succor.backgroundtask.impl.NotifyAdminExceptionHandler;
import com.rosettastone.succor.backgroundtask.model.Task;
import com.rosettastone.succor.backgroundtask.model.TaskType;

/**
 * Stores postal history report to the file on the ftp server
 */

public class FtpService {

    private String ftpServerName;
    private String ftpDir;
    private String userName;
    private String password;

    private ExceptionNotificationStatusWatcher exceptionNotificationStatusWatcher;
    private NotifyAdminExceptionHandler notifyAdminExceptionHandler;

    private static final Log LOG = LogFactory.getLog(FtpService.class);

    /**
     *Calls the {@link #saveWithFTPClient(byte[], java.util.Locale)} method
     * @param csvFile argument for {@link #saveWithFTPClient(byte[], java.util.Locale)} method
     * @param locale argument for {@link #saveWithFTPClient(byte[], java.util.Locale)} method
     * @return <tt>true</tt> if data was saved on the ftp server
     * @throws FTPException
     */
    public boolean saveToFile(byte[] csvFile, Locale locale) throws FTPException{
        return saveWithFTPClient(csvFile, locale);
    }

    private String lastSavedFileName;

    /**
     * Generates file name {@link FtpService#lastSavedFileName}
     * Connects to the ftp server, login to the ftp server, check for available space,
     * sets the file type "FTP.BINARY_FILE_TYPE", gets outputStream for storing file,
     * save data for save, verifies the success of the server entire transaction.
     * @param csvFile data for save to file
     * @param locale locale for generate file name
     * @return True if file was saved.
     * @throws FTPException
     */

    private boolean saveWithFTPClient(byte[] csvFile, Locale locale) throws FTPException{
        boolean saved = true;
        Task task = new Task();
        task.setType(TaskType.FTP);
        Date saveDate = new Date();
        lastSavedFileName = "postal-" + DateFormatUtils.format(saveDate, "MMddyyyy-HHmmss", Locale.ENGLISH) + "-"
                + locale + ".csv";
        FTPClient ftp = null;
        OutputStream out = null;
        try {
            ftp = new FTPClient();
            String serverName = getFtpServerName();
            if (!serverName.contains(":")) {
                ftp.connect(serverName);
            } else {
                String hostPort[] = serverName.split(":");
                try {
                    int port = Integer.parseInt(hostPort[1]);
                    ftp.connect(hostPort[0], port);
                } catch (NumberFormatException e){
                    throw new Exception("Couldn't parse value '" + hostPort[1] + "' for port.");
                }
	        }
            int reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(ftp.getReplyCode())) {
                Map<Integer, String> map = FtpHelper.getHelper().getCodeMap();
                String errorMsg = "Ftp return error code '" + reply + "' while saving file '" + "/" + ftpDir + "/"
                        + lastSavedFileName + "' on server '" + ftpServerName + "'";
                if (map.containsKey(reply)) {
                    errorMsg = errorMsg + "\n" + map.get(reply);
                }
                throw new FTPException(errorMsg);
            }
    	    ftp.setControlKeepAliveTimeout(60);
            ftp.login(getUserName(), getPassword());
            String ftpDir = getFtpDir();
            if (ftpDir!=null && !ftpDir.trim().isEmpty()) {
                ftp.changeWorkingDirectory(ftpDir);
            }
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
            ftp.enterLocalPassiveMode();
            if (!ftp.allocate(csvFile.length)) {
                throw new FTPException("Not enough disk space!");
            }
            out = ftp.storeFileStream(lastSavedFileName);
            if (out == null) {
                throw new FTPException("FTP File transfer failed. Can not get outputStream.");
            } else {
                out.write(csvFile);
	    	    out.close();
                if (!ftp.completePendingCommand()) {
                    throw new FTPException("FTP File transfer failed. Can not complete pending commands.");
	    	    }
                if (exceptionNotificationStatusWatcher != null) {
                    exceptionNotificationStatusWatcher.cleanNotificationStatuses(task);
                }
            }
            ftp.logout();
        } catch (Exception e) {
            saved = false;
            LOG.error("FTP FAIL save file '" + lastSavedFileName + "' to '" + getFtpServerName() + "/" + getFtpDir() + "'");
            if (notifyAdminExceptionHandler != null) {
                notifyAdminExceptionHandler.handleException(task, e);
            }
            throw new FTPException(e);
        } finally {
            try {
                if (ftp.isConnected()) {
                    ftp.disconnect();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return saved;
    }

    public String getFtpDir() {
        return ftpDir;
    }

    @Required
    public void setFtpDir(String ftpDir) {
        this.ftpDir = ftpDir;
    }

    public String getFtpServerName() {
        return ftpServerName;
    }

    @Required
    public void setFtpServerName(String ftpServerName) {
        this.ftpServerName = ftpServerName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public NotifyAdminExceptionHandler getNotifyAdminExceptionHandler() {
        return notifyAdminExceptionHandler;
    }

    public void setNotifyAdminExceptionHandler(NotifyAdminExceptionHandler notifyAdminExceptionHandler) {
        this.notifyAdminExceptionHandler = notifyAdminExceptionHandler;
    }

    public ExceptionNotificationStatusWatcher getExceptionNotificationStatusWatcher() {
        return exceptionNotificationStatusWatcher;
    }

    public void setExceptionNotificationStatusWatcher(
            ExceptionNotificationStatusWatcher exceptionNotificationStatusWatcher) {
        this.exceptionNotificationStatusWatcher = exceptionNotificationStatusWatcher;
    }

    public String getLastSavedFileName() {
        return lastSavedFileName;
    }

}
