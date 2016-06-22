/**
 * Copyright 山东众阳软件有限公司 All rights reserved.
 */
package com.msunsoft.cache;

import java.util.HashMap;
import java.util.Map;

import org.apache.mina.core.session.IoSession;
/**
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>公司名称 :山东众阳软件有限公司 </p>
 * <p>项目名称 : IMDataServer</p>
 * <p>创建时间 : 2016年5月19日 下午4:30:30</p>
 * <p>类描述 :     IMSession管理器    </p>
 *
 *
 * @version 1.0.0
 * @author <a href=" ">chenyanjun</a>
 */
public class SessionCache {

	public static Map<String, IoSession> map = new HashMap<>();
	
	public static Map<String, IoSession> userSessionMap = new HashMap<>();
}
