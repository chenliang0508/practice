/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2016 All Rights Reserved.
 */
package cn.com.upcard.mgateway.channel.bank.mybank.sdk.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import org.apache.log4j.Logger;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

/**
 * 
 * @author simon.xxm
 * @version $Id: SFTPUtil.java, v 0.1 2016年1月25日 下午2:00:49 simon.xxm Exp $
 */
public class SFTPUtil {
    public Logger       log        = Logger.getLogger(getClass());

    private Session     sshSession;

    private String      userName   = "yqzl001";                      // FTP服务器用户名

    private String      password   = "3hflsnvlywor";                  // FTP服务器密码

    private String      FTP_IP     = "sftp.alipay.net";           // FTP服务器IP地址

    private String      fileDir    = "/jfuz/201501121000171508";  // FTP服务器存放文件路径

    private int         part       = 22;                        // 端口号

    private ChannelSftp sftpClient = null;

    public SFTPUtil(String userName, String password, String fTP_IP, String fileDir, int part) {
        this.userName = userName;
        this.password = password;
        FTP_IP = fTP_IP;
        this.fileDir = fileDir;
        this.part = part;
    }

    public SFTPUtil() {

    }

    public static void main(String[] args) throws Exception {
               new SFTPUtil().connectFtpServer();
        //        System.out.println(response);
        //        System.out.println(response.split("\n").length);
    }

    public ChannelSftp connectFtpServer() throws SocketException, IOException {
        ChannelSftp sftp = null;
        try {
            JSch jsch = new JSch();
            jsch.getSession(userName, FTP_IP, part);
            sshSession = jsch.getSession(userName, FTP_IP, part);
            System.out.println("Session created.");
            sshSession.setPassword(password);
            Properties sshConfig = new Properties();
            sshConfig.put("StrictHostKeyChecking", "no");
            sshSession.setConfig(sshConfig);
            System.out.println("Session connected before");
            sshSession.connect(5000);
            System.out.println("Session connected.");
            System.out.println("Opening Channel.");
            Channel channel = sshSession.openChannel("sftp");
            channel.connect();
            sftp = (ChannelSftp) channel;

            System.out.println("连接SFTP服务器 " + FTP_IP + "成功.");
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return null;
        }
        this.sftpClient = sftp;
        try {
            sftpClient.setFilenameEncoding("UTF-8");
        } catch (SftpException e) {
            e.printStackTrace();
        }
        return sftp;
    }

    /**
     * 断开与对方FTP Server的连接
     * 
     * @throws IOException
     */
    public void disconnectFtpServer() throws IOException {
        sftpClient.disconnect();
        sshSession.disconnect();
    }

    public boolean changeWorkingDirectory(String fileDir) throws IOException {
        try {
            if (fileDir.trim().length() > 0) {
                sftpClient.cd(fileDir);
            } else {
                sftpClient.cd(sftpClient.getHome());
            }
            return true;
        } catch (SftpException e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return false;
        }
    }

    // "JYMX_201501121000171508_20150112.txt"
    public InputStream downLoadFile(String downLoadFileName) throws IOException {
        InputStream in = null;
        connectFtpServer(); // 连接FTP服务器
        changeWorkingDirectory(fileDir); // 进入FTP服务器指定目录
        try {
            sftpClient.get(downLoadFileName, "./" + downLoadFileName);
            in = new FileInputStream(new File("./" + downLoadFileName));
        } catch (Exception e) {
            log.error("下载sftp文件异常", e);
        }
        disconnectFtpServer();
        return in;
    }

    public List<String> list(String filePath) throws IOException {
        connectFtpServer(); // 连接FTP服务器
        changeWorkingDirectory(filePath); // 进入FTP服务器指定目录
        List<String> list = new ArrayList<String>();
        try {
            @SuppressWarnings("rawtypes")
            Vector data = sftpClient.ls("./");
            LsEntry record = null;
            for (Object obj : data) {
                record = (LsEntry) obj;
                list.add(record.getFilename());
            }
        } catch (Exception e) {
            log.error("下载sftp文件异常", e);
        }
        disconnectFtpServer();
        return list;
    }

    public String upLoadFile(InputStream in, String dest) throws IOException {
        String result = "";
        connectFtpServer(); // 连接FTP服务器
        changeWorkingDirectory(fileDir); // 进入FTP服务器指定目录
        try {
            sftpClient.put(in, dest);
        } catch (Exception e) {
            log.error("下载sftp文件异常", e);
        }
        disconnectFtpServer();
        return result;
    }

}