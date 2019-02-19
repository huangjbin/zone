package com.zone.quartz_module.common;

import java.util.HashMap;
import java.util.Map;

public class ResLanguage {

	public static final String ZH_CN = "zh_cn";
	public static final String ENGLISH = "english";
	
	public static final int RES_SUCCESS_CODE = 200;
	private static Map<String, String> successMap = new HashMap<String, String>();

	public static final int RES_SYSTEM_ERR_CODE = 400;
	private static Map<String, String> systemErrMap = new HashMap<String, String>();

	public static final int RES_PARAM_NO_CODE = 500;
	private static Map<String, String> paramNoMap = new HashMap<String, String>();

	public static final int RES_PARAM_ERR_CODE = 501;
	private static Map<String, String> paramErrMap = new HashMap<String, String>();

	public static final int RES_TOKEN_ERR_CODE = 502;
	private static Map<String, String> tokenErrMap = new HashMap<String, String>();
	
	public static final int RES_NOFOUND_ERR_CODE = 503;
	private static Map<String, String> nofoundErrMap = new HashMap<String, String>();
	
	public static final int RES_TASK_NO_CODE = 504;
	private static Map<String, String> noTaskErrMap = new HashMap<String, String>();

	public static final int RES_AUTH_NO_CODE = 505;
	private static Map<String, String> authNoErrMap = new HashMap<String, String>();

	public static final int RES_SYS_MODULE_ERR_CODE = 506;
	private static Map<String, String> sysModuleErrMap = new HashMap<String, String>();
	
	static{
		successMap.put(ZH_CN, "操作成功");
		successMap.put(ENGLISH, "success");

		sysModuleErrMap.put(ZH_CN, "系统管理模块服务器繁忙,请稍后再试");
		sysModuleErrMap.put(ENGLISH, "system module server is busy, try again later");

		authNoErrMap.put(ZH_CN, "暂无权限");
		authNoErrMap.put(ENGLISH, "no auth");
		
		noTaskErrMap.put(ZH_CN, "任务不存在");
		noTaskErrMap.put(ENGLISH, "the task no exit");
		
		nofoundErrMap.put(ZH_CN, "未找到该id对应的数据");
		nofoundErrMap.put(ENGLISH, "no data for id");
		
		systemErrMap.put(ZH_CN, "服务器繁忙,请稍后再试");
		systemErrMap.put(ENGLISH, "server is busy, try again later");

		paramNoMap.put(ZH_CN, "缺少%s请求参数");
		paramNoMap.put(ENGLISH, "missing %s parameters");
		
		paramErrMap.put(ZH_CN, "参数%s数据类型错误");
		paramErrMap.put(ENGLISH, "%s param type error");
		
		tokenErrMap.put(ZH_CN, "Token失效请重新登录");
		tokenErrMap.put(ENGLISH, "Token expired, please login again");
		
		
	}
	

	public static String getRes_success_mes(String language) {
		if(successMap.containsKey(language)){
			return successMap.get(language);
		}else{
			return successMap.get(ENGLISH);
		}
	}

	public static String getSystemModule_err_mes(String language) {
		if(sysModuleErrMap.containsKey(language)){
			return sysModuleErrMap.get(language);
		}else{
			return sysModuleErrMap.get(ENGLISH);
		}
	}

	public static String getRes_auth_no_mes(String language){
		if(authNoErrMap.containsKey(language)){
			return authNoErrMap.get(language);
		}else{
			return authNoErrMap.get(ENGLISH);
		}
	}
	
	public static String getRes_notask_mes(String language) {
		if(noTaskErrMap.containsKey(language)){
			return noTaskErrMap.get(language);
		}else{
			return noTaskErrMap.get(ENGLISH);
		}
	}
	
	public static String getRes_nofound_mes(String language) {
		if(nofoundErrMap.containsKey(language)){
			return nofoundErrMap.get(language);
		}else{
			return nofoundErrMap.get(ENGLISH);
		}
	}

	public static String getRes_system_err_mes(String language) {
		if(systemErrMap.containsKey(language)){
			return systemErrMap.get(language);
		}else{
			return systemErrMap.get(ENGLISH);
		}
	}

	public static String getRes_param_no_mes(String language,String param) {
		if(paramNoMap.containsKey(language)){
			return String.format(paramNoMap.get(language), param);
		}else{
			return String.format(paramNoMap.get(ENGLISH), param);
		}
	}

	public static String getRes_param_err_mes(String language,String param) {
		if(paramErrMap.containsKey(language)){
			return String.format(paramErrMap.get(language), param);
		}else{
			return String.format(paramErrMap.get(ENGLISH), param);
		}
	}

	public static String getRes_token_err_mes(String language) {
		if(tokenErrMap.containsKey(language)){
			return tokenErrMap.get(language);
		}else{
			return tokenErrMap.get(ENGLISH);
		}
	}

}
