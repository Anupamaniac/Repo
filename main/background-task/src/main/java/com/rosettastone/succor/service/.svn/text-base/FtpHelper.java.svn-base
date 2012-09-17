package com.rosettastone.succor.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * User: mixim
 * Date: 5/19/11
 */

public class FtpHelper {

    private static final Log LOG = LogFactory.getLog(FtpHelper.class);

    final static Integer FTP1_MARKER_REPLY = 110;
    final static Integer FTP1_SERVICE_READY_IN = 120;
    final static Integer FTP1_CONNECTION_ALREADY_OPEN = 125;
    final static Integer FTP1_OPEN_DATA = 150;

    final static Integer FTP3_NEED_PASSWORD = 331;
    final static Integer FTP3_NEED_ACCOUNT_FOR_LOGIN = 332;
    final static Integer FTP3_PENDING_FURTHER_INFO = 350;

    final static Integer FTP4_SERVICE_NOT_AVAILABLE = 421;
    final static Integer FTP4_CANNOT_OPEN_DATA_CONNECTION = 425;
    final static Integer FTP4_CONNECTION_CLOSED = 426;
    final static Integer FTP4_HOST_NOT_AVAILABLE = 434;
    final static Integer FTP4_FILE_NOT_AVAILABLE = 450;
    final static Integer FTP4_LOCAL_ERROR = 451;
    final static Integer FTP4_INSUFFICIENT_STORAGE_PLACE = 452;

    final static Integer FTP5_SYNTAX_ERROR = 500;
    final static Integer FTP5_SYNTAX_ERROR_IN_PARAM = 501;
    final static Integer FTP5_COMMAND_NOT_IMPLEMENTED = 502;
    final static Integer FTP5_BAD_COMAMND_SEQ = 503;
    final static Integer FTP5_COMMAND_NOT_IMPL_FOR_PARAMS = 504;
    final static Integer FTP5_USER_NOT_LOGGED = 530;
    final static Integer FTP5_NEED_AUTH_FOR_STORING = 532;
    final static Integer FTP5_REQ_FILE_UNAVAILABLE = 550;
    final static Integer FTP5_REQUEST_FILE_ACTION_ABORTED = 552;
    final static Integer FTP5_REQUEST_ACTION_NOT_TAKEN = 553;

    private static FtpHelper helper = null;

    public static FtpHelper getHelper(){
        if(helper==null)
            helper = new FtpHelper();
        return helper;
    }

    private FtpHelper() {
        createCodeMap();
    }

    public Map<Integer,String> getCodeMap(){
        return codeMap;
    }

    final private Map<Integer,String> codeMap = new HashMap<Integer,String>();

    private Map createCodeMap(){
        codeMap.put(FTP1_MARKER_REPLY, "Informational");
        codeMap.put(FTP1_SERVICE_READY_IN, "Informational");
        codeMap.put(FTP1_CONNECTION_ALREADY_OPEN, "Informational");
        codeMap.put(FTP1_OPEN_DATA, "Informational");

        codeMap.put(FTP3_NEED_PASSWORD,"You see this status code after the client sends a user name, regardless of whether the user name that is provided is a valid account on the system.");
        codeMap.put(FTP3_NEED_ACCOUNT_FOR_LOGIN, "Provide login credentials");
        codeMap.put(FTP3_PENDING_FURTHER_INFO, "Informational");

        codeMap.put(FTP4_SERVICE_NOT_AVAILABLE, "Try logging in later");
        codeMap.put(FTP4_CANNOT_OPEN_DATA_CONNECTION, "Change from PASV to PORT mode, check your firewall settings, or try to connect via HTTP");
        codeMap.put(FTP4_CONNECTION_CLOSED, "Try logging back in; contact your hosting provider to check if you need to increase your hosting account; try disabling the firewall");
        codeMap.put(FTP4_HOST_NOT_AVAILABLE, "Host not available. Try again later");
        codeMap.put(FTP4_FILE_NOT_AVAILABLE, "Try again later");
        codeMap.put(FTP4_LOCAL_ERROR, "Ensure command and parameters were typed correctly");
        codeMap.put(FTP4_INSUFFICIENT_STORAGE_PLACE, "Ask FTP administrator to increase allotted storage space, or archive/delete remote files");

        codeMap.put(FTP5_SYNTAX_ERROR, "Try switching to passive mode");
        codeMap.put(FTP5_SYNTAX_ERROR_IN_PARAM, "Verify your input; i.e., make sure there are no erroneous characters, spaces, etc.");
        codeMap.put(FTP5_COMMAND_NOT_IMPLEMENTED, "The server does not support this command");
        codeMap.put(FTP5_BAD_COMAMND_SEQ, "Verify command sequence");
        codeMap.put(FTP5_COMMAND_NOT_IMPL_FOR_PARAMS, "Ensure entered parameters are correct");
        codeMap.put(FTP5_USER_NOT_LOGGED, "Ensure that you typed the correct user name and password combination");
        codeMap.put(FTP5_NEED_AUTH_FOR_STORING, "Logged in user does not have permission to store files on the server");
        codeMap.put(FTP5_REQ_FILE_UNAVAILABLE, "Verify that you are attempting to connect to the  correct server/location. The administrator of the server must provide you with permission to connect via FTP");
        codeMap.put(FTP5_REQUEST_FILE_ACTION_ABORTED, "More disk space is needed. Archive files on the remote server that you no longer need");
        codeMap.put(FTP5_REQUEST_ACTION_NOT_TAKEN, "Change the file name or delete spaces/special characters in the file name");
        return codeMap;
    }
}
