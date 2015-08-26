package com.ganily.gdfs;

import com.ganily.gdfs.util.GdfsUtil;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * Created by ganily on 2015/8/23.
 */
public class GdfsClient {
    private Connection connection;

    public GdfsClient(){}
    public GdfsClient(Connection connection){
        this.connection = connection;
    }
    public String upload(String file) throws Exception{
        connection.connect();
        FileInputStream fis = new FileInputStream(file);
        FileChannel fc = fis.getChannel();
        int flen =  (int)fc.size();
        connection.writeInt(flen);
        connection.write(0);
        byte buffer[]=new byte[1024];
        int len = 0;
        int blen = 0;
        while((len=fis.read(buffer))!=-1){
            blen += len;
            //System.out.println("f:"+flen +"; b:"+blen +"; l:" +len);
            if(buffer.length == 1024){

                connection.write(buffer);
            } else {
                byte[] buf = new byte[len];

                System.arraycopy(buffer, 0, buf, 0, len);
                Thread.sleep(10000);
                connection.write(buf);
            }

        }
        System.out.println("f:"+flen +"; b:"+blen);
        connection.flush();

        fis.close();
        return getFileid();
    }

    public String upload(byte[] buf) throws IOException{
        connection.connect();
        connection.write(buf.length);
        connection.write(0);
        connection.write(buf);
        connection.flush();

        return getFileid();
    }

    private String getFileid() throws IOException {
        String fileid = "";
        byte[] b = new byte[1024];
        int len;
        if((len=connection.read(b))!=-1){
            fileid += new String(b, 0, len);

        }

        connection.close();
        return fileid;
    }

    public void download (String fileid, String savename) throws IOException{
        connection.connect();
        writeFileid(fileid);

        FileOutputStream fos = new FileOutputStream(savename);
        byte[] b = new byte[1024];
        int len;
        while((len=connection.read(b))!=-1){
            if(len == 1024)
                fos.write(b);
            else {
                byte[] buf = new byte[len];
                System.arraycopy(b, 0, buf, 0, len);
                fos.write(buf);
            }
        }
        fos.close();
        connection.close();
    }
    public byte[] download (String fileid) throws IOException{
        connection.connect();
        writeFileid(fileid);

        byte[] buf = new byte[0];
        byte[] b = new byte[1024];
        int len;
        while((len=connection.read(b))!=-1){
            if(len == 1024)
                GdfsUtil.append(buf,b);
            else {
                byte[] buf1 = new byte[len];
                System.arraycopy(b, 0, buf1, 0, len);
                GdfsUtil.append(buf, buf1);
            }
        }
        connection.close();
        return buf;
    }

    private void writeFileid(String fileid) throws IOException {
        connection.writeInt(fileid.length());
        connection.write(1);
        connection.write(fileid.getBytes());
        connection.flush();
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }
}
