package com.ganily.gdfs;

/**
 * Created by ganily on 2015/8/23.
 */
public class Test {
    public static void  main(String[] args) throws Exception {
//        Connection conn = ConnectionFactory.getConnection("10.16.11.241", 8088);
        Connection conn = ConnectionFactory.getConnection("10.16.21.162", 8088);
        GdfsClient client = conn.getClient();

        String fileid = client.upload("C:/Users/ganily/Desktop/20141116135722282.jpg");
        System.err.println(fileid);

//        client.download("fileid/258968324/344150/10970648842153288125.txt", "C:\\Users\\ganily\\Desktop\\demo1.txt");
        client.download(fileid, "C:\\Users\\ganily\\Desktop\\demo1.jpg");

    }
}
