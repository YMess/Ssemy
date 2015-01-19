package com.ymess.ymail.util;

import java.util.Set;

public class YMailCommonUtility {

	public static String getCassandraInQuery(Set<Long> ids){
		StringBuilder idsSB = new StringBuilder();
		for (Long sentMailId : ids) {
			idsSB.append(sentMailId).append(",");
		}
		String idsStr = "";
		if(idsSB.length() > 0)
			idsStr = idsSB.substring(0,idsSB.lastIndexOf(","));
		
		return idsStr == null ? "" : idsStr;
	}
	
}
