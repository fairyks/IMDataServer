/**
 * Copyright 山东众阳软件有限公司 All rights reserved.
 */
package com.msunsoft.starter;

import org.apache.log4j.Logger;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;

import com.msunsoft.server.IMServerStarter;
import com.msunsoft.util.ConfigRead;

/**
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>公司名称 :山东众阳软件有限公司 </p>
 * <p>项目名称 : IMDataServer</p>
 * <p>创建时间 : 2016年5月19日 下午4:18:32</p>
 * <p>类描述 :         </p>
 *
 *
 * @version 1.0.0
 * @author <a href=" ">chenyanjun</a>
 */

public class ServerStarter {

	private static Logger logger = Logger.getLogger(ServerStarter.class);

	/**
	 * 方法描述 : 启动服务
	 * @param args
	 */
	public static void main(String[] args) {
		logger.info("server starting...");

		try {
			IMServerStarter.startIMServer();
			int host = Integer.parseInt(ConfigRead.getValue("im.properties", "WEB_PORT"));

			ResourceHandler resourceHandler = new ResourceHandler();
			resourceHandler.setDirectoriesListed(true);
			resourceHandler.setResourceBase("data");
			resourceHandler.setStylesheet("");
			ContextHandler staticContextHandler = new ContextHandler();
//			staticContextHandler.setContextPath("/data");
			staticContextHandler.setHandler(resourceHandler);

			HandlerList handlers = new HandlerList();
			//	    handlers.addHandler(servletContextHandler);
			handlers.addHandler(staticContextHandler);

			Server server = new Server(host);
			server.setHandler(handlers);

			server.start();
			server.join();
			logger.info("server started...");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getCause().toString());
		}
	}

}
