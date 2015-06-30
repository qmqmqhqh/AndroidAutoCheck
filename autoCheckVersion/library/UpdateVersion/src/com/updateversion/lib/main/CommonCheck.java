package com.updateversion.lib.main;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


/**
 * 
 * @author baoguo
 * 常规检测，包括检测网络是否连接以及WIFI是否连接，还有包的一系列检测
 */
public class CommonCheck {
	
	private static boolean Net_IsTest=false;
	public static NetworkInfo NetInfo;
	public static int NetType=0;
	
	/**
	 * 判断网络是否连接
	 * @param context上下文，从Activity或service传过来
	 * @return 如果网络已连接返回true，否则返回FALSE
	 */
	public static boolean isNetworkCon(Context context)
	{
		boolean result=false;
		if(context!=null)
		{
			ConnectivityManager m_ConManager=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetInfo=m_ConManager.getActiveNetworkInfo();
			if(NetInfo != null){
				Net_IsTest=true;
				result=NetInfo.isAvailable();
				NetType=NetInfo.getType();
			}
		}
		
		return result;
	}
	
	/**
	 * 判断WIFI是否连接
	 * @return 如果WIFI已连接返回true，否则为false
	 */
	public static boolean isWiFICon()
	{
		boolean result=false;
	    if(Net_IsTest && NetType==ConnectivityManager.TYPE_WIFI) 
			result=true;
		return result;
	}
	
	/* 
	 * 获取当前程序的版本号  
	 */ 
	/**
	 * 获取当前程序的版本号
	 * @param context上下文，从Activity或service传过来
	 * @return 返回当前软件的版本号，String
	 */
	public static String getVersionName(Context context){  
	    //获取packagemanager的实例   
	    PackageManager packageManager = context.getPackageManager();  
	    //getPackageName()是你当前类的包名，0代表是获取版本信息  
	    PackageInfo packInfo;
		try {
			packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
			return packInfo.versionName; 
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "fail";
		}  	      
	}
	

}
