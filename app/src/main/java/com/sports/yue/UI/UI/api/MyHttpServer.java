package com.sports.yue.UI.UI.api;

import android.net.Uri;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

public class MyHttpServer {

    private Uri imageUri;
    private String ip;
    private int port;
    File file;
    private String newName = "1.mp4";
    private String serverUrl = "http://api2.bmob.cn/2/files/1.mp4";
    private HttpURLConnection connection = null;
    private DataOutputStream dos = null;
    private FileInputStream fin = null;
    private static final String BOUNDARY = "-----------------------------1954231646874";
    private byte[] testBuffer;


    String end = "\r\n";
    String twoHyphens = "--";
    String boundary = "*****";

    public MyHttpServer(Uri imageUri){
        this.imageUri = imageUri;
        file = new File(imageUri.getPath());
    }

    public MyHttpServer(File file){
        this.file = file;
    }


    public String uploadVideoToServer(){

        try{
            URL url = new URL(serverUrl);
            connection = (HttpURLConnection)url.openConnection();

            //允许向url流中读写数据
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            //设置POST方法
            connection.setRequestMethod("POST");

            //设置请求头
            connection.setRequestProperty("connection","Keep-Alive");
            connection.setRequestProperty("X-Bmob-Application-Id","5fad9f2543ffa83e56155a46398d6ede");
            connection.setRequestProperty("X-Bmob-REST-API-Key","918a3c131997a216e99fd565230832f5");
            connection.setRequestProperty("Charset", "UTF-8");
            connection.setRequestProperty("Content-Type",
                    "video/mpeg4;boundary=" + boundary);


            /* 设置DataOutputStream */
            dos = new DataOutputStream(connection.getOutputStream());
            //fin = new FileInputStream(filePath);

            dos.writeBytes(twoHyphens + boundary + end);
            dos.writeBytes("Content-Disposition: form-data; "
                    + "name=\"userfile\";filename=\"" + newName + "\"" + end);
            dos.writeBytes(end);

            /* 取得文件的FileInputStream */
            FileInputStream fStream = new FileInputStream(file);
            ByteArrayOutputStream baso = new ByteArrayOutputStream();
            /* 设置每次写入1024bytes */
//            int bufferSize = 1024 * 1024;
//            byte[] buffer = new byte[bufferSize];
//
//            int length = -1;
//            /* 从文件读取数据至缓冲区 */
//            while ((length = fStream.read(buffer)) != -1) {
//                /* 将资料写入DataOutputStream中 */
//                dos.write(buffer, 0, length);
//            }

            int bufferSize = 1024 * 1024 * 10;
            byte[] buffer = new byte[bufferSize];
            buffer = getFileToByte(file);
            dos.write(buffer, 0, buffer.length);



            dos.writeBytes(end);

            // -----
            dos.writeBytes(twoHyphens + boundary + end);
            dos.writeBytes("Content-Disposition: form-data;name=\"name\"" + end);
            dos.writeBytes(end + URLEncoder.encode("xiexiezhichi", "UTF-8")
                    + end);
            // -----

            dos.writeBytes(twoHyphens + boundary + twoHyphens + end);
            /* close streams */
            fStream.close();
            dos.flush();

            InputStream is = connection.getInputStream();
            int ch;
            StringBuffer b = new StringBuffer();
            while ((ch = is.read()) != -1) {
                b.append((char) ch);
            }
            dos.close();

            return b.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public byte[] getFileToByte(File file) {
        byte[] by = new byte[(int) file.length()];
        try {
            InputStream is = new FileInputStream(file);
            ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
            byte[] bb = new byte[2048];
            int ch;
            ch = is.read(bb);
            while (ch != -1) {
                bytestream.write(bb, 0, ch);
                ch = is.read(bb);
            }
            by = bytestream.toByteArray();
        } catch (Exception ex) {
            throw new RuntimeException("transform file into bin Array 出错",ex);
        }
        return by;
    }


}
