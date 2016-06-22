/**
 * Copyright 山东众阳软件有限公司 All rights reserved.
 */
package com.msunsoft.worker;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.msunsoft.cache.SessionCache;
import com.msunsoft.model.Message;

/**
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>公司名称 :山东众阳软件有限公司 </p>
 * <p>项目名称 : IMDataServer</p>
 * <p>创建时间 : 2016年5月19日 下午4:46:20</p>
 * <p>类描述 :     IM消息发送者    </p>
 *
 *
 * @version 1.0.0
 * @author <a href=" ">chenyanjun</a>
 */
public class SendMessageWorker implements Runnable {

	private static Logger logger = Logger.getLogger(SendMessageWorker.class);

	private Message message;

	private static Gson gson = new Gson();

	public SendMessageWorker(Message message) {
		this.message = message;
	}

	/**
	 * <h4>  </h4>
	 * @see java.lang.Runnable#run()
	 * @throws 
	 */
	@Override
	public void run() {
		try {
//			SessionCache.map.get(message.getTo()).write(gson.toJson(message));
			SessionCache.userSessionMap.get(message.getTo()).write(gson.toJson(message));
		} catch (Exception e) {
			logger.error(e.getMessage().toString());
		}
	}

}
