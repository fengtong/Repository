package com.wangzhixuan.socket.server;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;

import com.wangzhixuan.model.FileUD;
import com.wangzhixuan.service.FileUDService;
import com.wangzhixuan.utils.BeanUtils;

public class Server {

	private static int PORT = 8881;
	
	@SuppressWarnings("resource")
	public void service() {
		try {
			ServerSocket server = new ServerSocket(PORT);
			
			while (true) {
				Socket socket = server.accept();
				new Thread(new Task(socket)).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	protected class Task implements Runnable {

		private Socket socket;

		public Socket getSocket() {
			return socket;
		}

		public void setSocket(Socket socket) {
			this.socket = socket;
		}

		public Task(Socket socket) {
			this.socket = socket;
		}

		@Override
		public void run() {

			serverMethod();
		}

		private synchronized void serverMethod() {
			FileUDService fileUDService = (FileUDService)BeanUtils.getBean("fileUDService");
			try {
				Reader reader = new InputStreamReader(socket.getInputStream());
				StringBuilder sb = new StringBuilder();
				char[] chars = new char[64];
				int len;
				String temp = "";
				int index;
				while ((len = reader.read(chars)) != -1) {
					temp = new String(chars, 0, len);
					if ((index = temp.indexOf("0x0A")) != -1) {
						sb.append(temp.substring(0, index));
						break;
					}
					sb.append(temp);
				}
				String[] strs = String.valueOf(sb).split(" ");
				String[] sqlStrs = new String[strs.length-1];
				//实时开户
				if(strs[0].equals("new_iptv")) {
					for (int i = 1; i < strs.length; i++) {
						if(strs[i].indexOf("{") != -1 && strs[i].indexOf("}") != -1) {
							if(String.valueOf(strs[i].subSequence(1, strs[i].length()-1)).equals("")) {
								sqlStrs[i-1] = "";
							}else {
								sqlStrs[i-1] = String.valueOf(strs[i].subSequence(1, strs[i].length()-1));
							}
						}else {
							continue;
						}
					}
					Integer count = fileUDService.getFileUDCount(sqlStrs[2]);
					FileUD fileUD = new FileUD();
					fileUD.setCreateNodeMark(sqlStrs[0]);
					fileUD.setUserName(sqlStrs[1]);
					fileUD.setPassWord(sqlStrs[2]);
					fileUD.setWideBandProp(sqlStrs[3]);
					fileUD.setSaveField(sqlStrs[4]);
					fileUD.setConsumerName(sqlStrs[5]);
					fileUD.setAddress(sqlStrs[6]);
					fileUD.setPhone(sqlStrs[7]);
					if(count != null && count > 0) {
						System.out.println("数据库已经存在此账号" + sqlStrs[2] +"更新其密码并保存到数据库...");
						FileUD fileUDObj = fileUDService.getFileUDEntity(sqlStrs[2]);
						fileUD.setId(fileUDObj.getId());
						Integer updateCount = fileUDService.updateFileUDPwd(fileUD);
						if(updateCount != null && updateCount > 0) {
							System.out.println("更新成功");
						}else{
							System.out.println("更新失败");
						}
					}else {
						fileUDService.setFileUDService(fileUD);
					}
				}
				System.out.println("from Client: " + sb);
				
				
				Writer writer = new OutputStreamWriter(socket.getOutputStream());
				writer.write("from Server: " + sb);
				writer.flush();
				
				writer.close();
				reader.close();
				socket.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
}
