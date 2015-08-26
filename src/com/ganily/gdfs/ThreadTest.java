package com.ganily.gdfs;

import java.io.File;

/**
 * Created by ganily on 2015/8/24.
 */
public class ThreadTest {

    public static void main(String[] args) throws Exception {
        //递归显示C盘下所有文件夹及其中文件
       // File root = new File("D:\\myDesktop\\ideatest\\");
        File root = new File("E:\\upload");

        showAllFiles(root);
    }

    final static void showAllFiles(File dir) throws Exception {
        File[] fs = dir.listFiles();
        for(int i=0; i<fs.length; i++){

            if(fs[i].isDirectory()){
                try{
                    showAllFiles(fs[i]);
                }catch(Exception e){}
            } else {
//                System.out.println(fs[i].getAbsolutePath());
                String filename = fs[i].getAbsolutePath();
                String name = fs[i].getName();

                new Thread(new FileRunnable(filename, i, name)).start();
            }
        }
    }

}

class FileRunnable implements Runnable {
    private String filename;
    private int con;
    private String name;
    public FileRunnable(String filename, int con, String name){
        this.filename = filename;
        this.con = con;
        this.name = name;
    }
@Override
    public void run() {
        try {
            Connection conn = ConnectionFactory.getConnection("10.16.11.241", 8088);
            GdfsClient client = conn.getClient();
            //System.out.println(conn);
            String fileid = client.upload(filename);
            System.err.println(fileid);

            Connection conn1 = ConnectionFactory.getConnection("10.16.11.241", 8088);
            GdfsClient client1 = conn1.getClient();
            client1.download(fileid, "D:\\Test\\"+con+"_"+name);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
