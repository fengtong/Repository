package com.wangzhixuan.socket.thread;

import com.wangzhixuan.socket.server.Server;

/**
 * @author zgt
 * 把整个监听服务先用一个监听器启动这个线程，在接收到一个socket之后，再重新启动一个线程去处理新的问题。
 * 确保socket端口不与tomcat端口冲突，或者hold住tomcat服务器，使其不能启动完整。
 */
public class SocketServerThread extends Thread {

	@Override
	public void run() {
		Server server = new Server();
		server.service();
	}

}
