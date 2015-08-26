package com.ganily.gdfs;

import com.ganily.gdfs.util.GdfsUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * Created by ganily on 2015/8/23.
 */
public class Connection {
    private String url;
    private int port;
    private Socket socket;
    private OutputStream out;
    private InputStream in;
    private SocketAddress socketAddress;

    public Connection(String url, int port) {
        this.url = url;
        this.port = port;
        this.socketAddress = new InetSocketAddress(url, port);

    }
    public GdfsClient getClient(){

        return new GdfsClient(this);
    }
    public Connection connect() throws IOException {
        this.socket = new Socket();
        socket.connect(socketAddress);

        out = socket.getOutputStream();
        in  = socket.getInputStream();
        return this;
    }

    public void write(byte[] buf) throws IOException {
        out.write(buf);
    }

    public void write(byte b) throws IOException {
        out.write(b);
    }

    public void write(int i) throws IOException {
        out.write(i);
    }

    public void writeInt(int i) throws IOException {
        write(GdfsUtil.intToByteArray(i));
    }
    public void flush() throws IOException {
        out.flush();
        socket.shutdownOutput();
    }

    public int read(byte[] buf) throws IOException {

        return in.read(buf);
    }

    void close() {
        try {
            if (null != in) {
                in.close();
            }
            if (null != out) {
                out.close();
            }
            if (null != socket) {
                socket.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }

    }


    public void setPort(int port) {
        this.port = port;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getPort() {
        return port;
    }

    public String getUrl() {
        return url;
    }
}
