package com.wangzhixuan.socket.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.Reader;
import java.io.Writer;
import java.net.Socket;
import java.net.SocketException;

import org.apache.commons.net.telnet.TelnetClient;

public class SocketClient {
	public static void main(String[] args) throws IOException {
//		String host = "132.121.21.8";
//		int port = 9030;
//		Socket client = new Socket(host, port);
//		Writer writer = new OutputStreamWriter(client.getOutputStream());
//		writer.write("set_iptv {12345678} {5cc5d44d4f2c@IPTV} {3} {5cc5d44d4f2c@IPTV} {123456}");
//		writer.write("0x0A");
//		writer.flush();
//		
//		Reader reader = new InputStreamReader(client.getInputStream());
//		char[] chars = new char[64];
//		StringBuilder sb = new StringBuilder();
//		int len;
//		String temp;
//		int index;
//		while((len = reader.read(chars)) != -1) {
//			temp = new String(chars, 0, len);
//			if((index = temp.indexOf("0x0A")) != -1) {
//				sb.append(temp.substring(0, index));
//				break;
//			} 
//			sb.append(temp); 
//		}
//		System.out.println("from server: " + sb);
//		writer.close();
//		reader.close();
//		client.close();
		
		try {
            TelnetClient telnetClient = new TelnetClient("vt200");  //指明Telnet终端类型，否则会返回来的数据中文会乱码
            telnetClient.setDefaultTimeout(5000); //socket延迟时间：5000ms
            telnetClient.connect("132.121.21.8",9030);  //建立一个连接,默认端口是23
            InputStream inputStream = telnetClient.getInputStream(); //读取命令的流
            PrintStream pStream = new PrintStream(telnetClient.getOutputStream());  //写命令的流
            byte[] b = new byte[1024];
            int size;
            StringBuffer sBuffer = new StringBuffer(300);
            while(true) {     //读取Server返回来的数据，直到读到登陆标识，这个时候认为可以输入用户名
                size = inputStream.read(b);
                if(-1 != size) {
                    sBuffer.append(new String(b,0,size));
                    if(sBuffer.toString().trim().endsWith("0x0A")) {
                        break;
                    }
                }
            }
            System.out.println(sBuffer.toString());
            pStream.println("exit"); //写命令
            pStream.flush(); //将命令发送到telnet Server
            if(null != pStream) {
                pStream.close();
            }
            telnetClient.disconnect();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
		
		
		
	}
}