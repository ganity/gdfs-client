package com.ganily.gdfs.util;

/**
 * Created by ganily on 2015/8/23.
 */
public class GdfsUtil {

    public static byte[] intToByteArray (final int integer) {
        int byteNum = (40 - Integer.numberOfLeadingZeros (integer < 0 ? ~integer : integer)) / 8;
        byte[] byteArray = new byte[4];

        for (int n = 0; n < byteNum; n++)
            byteArray[3 - n] = (byte) (integer >>> (n * 8));

        return byteArray;
    }

    /**
     * 从指定数组的追加数组并返回
     *
     * @param src of type byte[] 原数组
     * @param append 要合并的数据
     */
    public static byte[] append(byte[] src, byte[] append) {
        byte[] buf = new byte[src.length + append.length];
        System.arraycopy(src, 0, buf, 0, src.length);
        System.arraycopy(append, 0, buf, src.length, append.length);
        return buf;
    }
}
