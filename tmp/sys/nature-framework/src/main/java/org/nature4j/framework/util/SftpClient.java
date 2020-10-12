package org.nature4j.framework.util;

import com.jcraft.jsch.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;


public class SftpClient {
    private JSch jsch = null;
    private Session session = null;
    private Channel channel = null;
    private ChannelSftp channelSftp = null;

    public SftpClient() {
        jsch = new JSch();
    }

    public SftpClient connect(String username, String password, String host, Integer port, String privateKey, String passphrase) {
        try {
            if (privateKey != null)
                jsch.addIdentity(privateKey);
            if (passphrase != null && privateKey != null)
                jsch.addIdentity(privateKey, passphrase);
            if (port == null)
                port = 22;
            session = jsch.getSession(username, host, port);
            if (password != null) {
                session.setPassword(password);
            }
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect(30000);
            channel = session.openChannel("sftp");
            channel.connect(1000);
            channelSftp = (ChannelSftp) channel;
        } catch (JSchException e) {
            e.printStackTrace();
        }
        return this;
    }

    public SftpClient connect(String username, String password, String host, int port) {
        return connect(username, password, host, port, null, null);
    }

    public SftpClient connect(String username, String host, String privateKey, String passphrase) {
        return connect(username, null, host, null, privateKey, passphrase);
    }

    public SftpClient connect(String username, String host, Integer port, String privateKey) {
        return connect(username, null, host, port, privateKey, null);
    }

    public SftpClient connect(String username, String password, String host) {
        return connect(username, password, host, 22);
    }

    public SftpClient cd(String path) {
        try {
            channelSftp.cd(path);
        } catch (SftpException e) {
            e.printStackTrace();
        }
        return this;
    }

    public SftpClient get(String remoteFile, String localFile) {
        FileOutputStream fileOutputStream = null;
        InputStream inputStream = null;
        try {
            inputStream = channelSftp.get(remoteFile);
            File file = new File(localFile);
            fileOutputStream = new FileOutputStream(file);
            byte[] buff = new byte[1024];
            int len;
            while ((len = inputStream.read(buff)) != -1) {
                fileOutputStream.write(buff, 0, len);
            }
            fileOutputStream.flush();
        } catch (SftpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileOutputStream != null)
                    fileOutputStream.close();
                if (inputStream != null)
                    inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return this;
    }

    public SftpClient rm(String remoteFile, boolean isFile) {
        try {
            if (isFile)
                channelSftp.rm(remoteFile);
            else
                channelSftp.rmdir(remoteFile);
        } catch (SftpException e) {
            e.printStackTrace();
        }
        return this;
    }

    public SftpClient mkdir(String remoteFile) {
        try {
            channelSftp.mkdir(remoteFile);
        } catch (SftpException e) {
            e.printStackTrace();
        }
        return this;
    }

    public SftpClient put(String remoteFile, String localFile) {
        try {
            channelSftp.put(localFile, remoteFile);
        } catch (SftpException e) {
            e.printStackTrace();
        }
        return this;
    }

    public Vector<ChannelSftp.LsEntry> ls(String path) {
        Vector<ChannelSftp.LsEntry> vector = null;
        try {
            vector = channelSftp.ls(path);

        } catch (SftpException e) {
            e.printStackTrace();
        }
        return vector;
    }

    public void close() {
        if (channelSftp != null)
            channelSftp.disconnect();
        if (channel != null)
            channel.disconnect();
        if (session != null)
            session.disconnect();
    }

    public JSch getJsch() {
        return jsch;
    }

    public Session getSession() {
        return session;
    }

    public Channel getChannel() {
        return channel;
    }

    public ChannelSftp getChannelSftp() {
        return channelSftp;
    }

    public static void main(String[] args) throws JSchException, SftpException, IOException {
        SftpClient sftpClient = new SftpClient();
        sftpClient.connect("hdsftp", "huadian@123", "192.168.6.201").cd("upload");
        Vector<ChannelSftp.LsEntry> entries = sftpClient.ls("*");
        entries.forEach(lsEntry -> {
            System.out.println(lsEntry.getLongname());
        });
        sftpClient.put("nginx.gz", "nginx.gz").close();
    }
}
