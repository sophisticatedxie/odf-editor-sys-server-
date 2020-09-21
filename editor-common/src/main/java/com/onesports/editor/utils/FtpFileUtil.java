package com.onesports.editor.utils;

import com.jcraft.jsch.*;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Properties;
import java.util.Vector;

/**
 * @program: odf-editor-system
 * @description: ftp工具类
 * @author: xjr
 * @create: 2020-07-21 14:23
 **/
@Component
public  class FtpFileUtil {
    private static ChannelSftp sftp = null;

    //账号
    private static String user = "root";
    //主机ip
    private static  String host =  "47.98.155.18";
    //密码
    private static  String password = "Xjr19970305";
    //端口
    private static  int port = 22;
    //上传地址
    private static  String directory = "/usr/local/nginx_images/images/";
    //下载目录
    private static  String saveFile = "D:\\VMware\\XuNiJi\\imgs";

    public static FtpFileUtil getConnect(){
        FtpFileUtil ftp = new FtpFileUtil();
        try {
            JSch jsch = new JSch();

            //获取sshSession  账号-ip-端口
            Session sshSession =jsch.getSession(user, host,port);
            //添加密码
            sshSession.setPassword(password);
            Properties sshConfig = new Properties();
            //严格主机密钥检查
            sshConfig.put("StrictHostKeyChecking", "no");

            sshSession.setConfig(sshConfig);
            //开启sshSession链接
            sshSession.connect();
            //获取sftp通道
            Channel channel = sshSession.openChannel("sftp");
            //开启
            channel.connect();
            sftp = (ChannelSftp) channel;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ftp;
    }

    /**
     *
     * @param fileName 上传文件的路径
     *  @param inputStream 文件流
     * @return 服务器上文件名
     */
    public String upload(String fileName, InputStream inputStream) {
        try {
            sftp.cd(directory);
            //获取随机文件名
            //文件名是 随机数加文件名的后5位
            sftp.put(inputStream, fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            sftp.disconnect();
        }
        return fileName;
    }

    /**
     * 下载文件
     *
     *
     *            下载目录
     *
     *            下载的文件名
     *
     *            存在本地的路径
     *
     */
    public void download(String downloadFileName) {
        try {
            sftp.cd(directory);

            File file = new File(saveFile);

            sftp.get(downloadFileName, new FileOutputStream(file));
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            sftp.disconnect();
        }
    }

    /**
     * 删除文件
     *
     * @param deleteFile
     *            要删除的文件名字
     */
    public void delete(String deleteFile) {
        try {
            sftp.cd(directory);
            sftp.rm(deleteFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            sftp.disconnect();
        }
    }

    /**
     * 列出目录下的文件
     *
     *            要列出的目录
     *
     * @return
     * @throws SftpException
     */
    public Vector listFiles()
            throws SftpException {
        Vector vector= sftp.ls(directory);
        sftp.disconnect();
        return vector;
    }
}
