package com.ganily.gdfs;

/**
 * Created by ganily on 2015/8/23.
 */
public class ConnectionFactory {

    public static Connection getConnection(String url, int port) throws Exception {

        return new Connection(url, port);
    }
}
