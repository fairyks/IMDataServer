/**
 * Copyright 山东众阳软件有限公司 All rights reserved.
 */
package com.msunsoft.server;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import com.msunsoft.util.ConfigRead;

/**
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>公司名称 :山东众阳软件有限公司 </p>
 * <p>项目名称 : IMDataServer</p>
 * <p>创建时间 : 2016年5月19日 下午4:20:30</p>
 * <p>类描述 :     IM服务启动器    </p>
 *
 *
 * @version 1.0.0
 * @author <a href=" ">chenyanjun</a>
 */
public class IMServerStarter {

	private static Logger logger = Logger.getLogger(IMServerStarter.class);

	// 定义监听端口
	private static final int PORT = Integer.parseInt(ConfigRead.getValue("im.properties", "IMSERVER_PORT"));

	public static void startIMServer() {
		try {
			logger.info("starting imserver......");
			// 创建服务端监控线程
			IoAcceptor acceptor = new NioSocketAcceptor();
			acceptor.getSessionConfig().setReadBufferSize(2048);
			// 设置session配置，30秒内无操作进入空闲状态
			acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 30);
			// 设置日志记录器
			acceptor.getFilterChain().addLast("logger", new LoggingFilter());
			// 设置编码过滤器
			acceptor.getFilterChain().addLast("codec",
					new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));

			// 指定业务逻辑处理器
			acceptor.setHandler(new ServerMessageHandler());

			// 设置端口号
			acceptor.bind(new InetSocketAddress(PORT));
			// 启动监听线程
			acceptor.bind();

		} catch (Exception e) {
			logger.error(e.getCause().toString());
		}
	}
}