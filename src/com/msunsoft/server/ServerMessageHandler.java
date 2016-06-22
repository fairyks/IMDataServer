/**
 * Copyright 山东众阳软件有限公司 All rights reserved.
 */
package com.msunsoft.server;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

import com.google.gson.Gson;
import com.msunsoft.cache.SessionCache;
import com.msunsoft.model.Message;
import com.msunsoft.util.Constants;
import com.msunsoft.worker.SendMessageWorker;

/**
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>公司名称 :山东众阳软件有限公司 </p>
 * <p>项目名称 : IMDataServer</p>
 * <p>创建时间 : 2016年5月19日 下午4:35:20</p>
 * <p>类描述 :     IM消息处理器类    </p>
 *
 *
 * @version 1.0.0
 * @author <a href=" ">chenyanjun</a>
 */
public class ServerMessageHandler extends IoHandlerAdapter {

	private static Logger logger = Logger.getLogger(ServerMessageHandler.class);
	private static Gson gson = new Gson();
	private static ExecutorService servicePool = Executors.newCachedThreadPool();

	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		logger.error(cause.getMessage().toString());
		cause.printStackTrace();
	}

	/**
	 * 消息接收事件
	 */
	@Override
	public void messageReceived(IoSession session, Object message) {

		try {
			String json = message.toString();
			Message msg = gson.fromJson(json, Message.class);
			logger.info("received message is :" + json);
			if (msg.getType() != null && !"".equals(msg.getType())) {
				if (Constants.MessageType.equals(msg.getType())) {
					String clientIP = ((InetSocketAddress) session.getRemoteAddress()).getAddress().getHostAddress();
					if (SessionCache.map != null) {
						if (SessionCache.map.containsKey(clientIP)) {
							SessionCache.userSessionMap.put(msg.getFrom(), SessionCache.map.get(clientIP));
						}
					}
				}
			} else {
				if (SessionCache.userSessionMap != null) {
					if (SessionCache.userSessionMap.containsKey(msg.getTo())) {
						servicePool.execute(new SendMessageWorker(msg));
					}
				}
				/*				if (SessionCache.map != null) {
									if (SessionCache.map.containsKey(
											((InetSocketAddress) session.getRemoteAddress()).getAddress().getHostAddress())) {
										servicePool.execute(new SendMessageWorker(msg));
									}
								}
				*/ }
		} catch (Exception e) {
			logger.error(e.getMessage().toString());
		}
	}

	/**
	 * 连接创建事件
	 */
	@Override
	public void sessionCreated(IoSession session) {
		try {
			String clientIP = ((InetSocketAddress) session.getRemoteAddress()).getAddress().getHostAddress();
			SessionCache.map.put(clientIP, session);
			logger.debug(session.getRemoteAddress().toString() + "---session created");
		} catch (Exception e) {
			logger.error(e.getMessage().toString());
		}
	}

	@Override
	public void sessionClosed(IoSession session) {
		try {
			String clientIP = ((InetSocketAddress) session.getRemoteAddress()).getAddress().getHostAddress();
			if (SessionCache.map.containsKey(clientIP)) {
				SessionCache.map.remove(clientIP);
			}
			super.sessionClosed(session);
		} catch (Exception e) {
			logger.error(e.getMessage().toString());
		}
	}

	//	@Override
	//	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
	//		System.out.println("IDLE" + session.getIdleCount(status));
	//	}

}
