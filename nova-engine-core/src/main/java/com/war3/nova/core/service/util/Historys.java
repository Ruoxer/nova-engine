package com.war3.nova.core.service.util;

import com.war3.nova.core.service.HistoryService;
import com.war3.nova.core.util.SpringContexts;

public final class Historys {

	private final static HistoryService historyService = SpringContexts.getBean(HistoryService.class);
	
	
	public final static void toHistory(String processInstId) {
		historyService.toHistory(processInstId);
	}
	
	
}
