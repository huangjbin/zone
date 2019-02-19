package com.zone.quartz_module.utils;

import java.io.*;
import java.lang.reflect.Method;
import java.util.UUID;

public class CommonUtils {
	
	/**
	 * uuid
	 * @return
	 */
	public static String getUUID32(){
	    String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
	    return uuid;
	}
	
	public static void getMethodInfo(String pkgName) {
	       try {
	           Class clazz = Class.forName(pkgName);
	           Method[] methods = clazz.getMethods();
	           for (Method method : methods) {
	               String methodName = method.getName();
	               Class<?>[] parameterTypes = method.getParameterTypes();
	               for (Class<?> clas : parameterTypes) {
	                   String parameterName = clas.getName();
	                   System.out.println("参数名称:" + parameterName);
	               }
	           }
	       } catch (ClassNotFoundException e) {
	           e.printStackTrace();
	       }
	   }
	
	//序列化为byte[]
	public static byte[] serialize(Object object) {
	    ObjectOutputStream oos = null;
	    ByteArrayOutputStream bos = null;
	    try {
	        bos = new ByteArrayOutputStream();
	        oos = new ObjectOutputStream(bos);
	        oos.writeObject(object);
	        byte[] b = bos.toByteArray();
	        return b;
	    } catch (IOException e) {
	        System.out.println("序列化失败 Exception:" + e.toString());
	        return null;
	    } finally {
	        try {
	            if (oos != null) {
	                oos.close();
	            }
	            if (bos != null) {
	                bos.close();
	            }
	        } catch (IOException ex) {
	            System.out.println("io could not close:" + ex.toString());
	        }
	    }
	}
	
	//反序列化为Object
	public static Object deserialize(byte[] bytes) {
	    ByteArrayInputStream bais = null;
	    try {
	        // 反序列化
	        bais = new ByteArrayInputStream(bytes);
	        ObjectInputStream ois = new ObjectInputStream(bais);
	        return ois.readObject();
	    } catch (Exception e) {
	        System.out.println("bytes Could not deserialize:" + e.toString());
	        return null;
	    } finally {
	        try {
	            if (bais != null) {
	                bais.close();
	            }
	        } catch (IOException ex) {
	            System.out.println("LogManage Could not serialize:" + ex.toString());
	        }
	    }
	}

	public static String changeLanguage(String language){
		if(language.equals("zh_cn")){
			return "zh-CN";
		}else if(language.equals("english")){
			return "zh-US";
		}else{
			return "zh-US";
		}
	}

}
