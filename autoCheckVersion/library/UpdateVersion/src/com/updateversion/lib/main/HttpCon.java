package com.updateversion.lib.main;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.ProgressDialog;

/**
 * 发送HTTP请求
 * @author baoguo
 *
 */
public class HttpCon {

	/**
	 * 从服务器获取APK,并设置进度条最大值（不过好像没什么卵用）
	 * @param path，APK所在路径
	 * @param pd，ProgressDialog对话框
	 * @return InputStream
	 * @throws IOException
	 */
	public static InputStream getapk(String path,ProgressDialog pd) throws IOException
	{
		URL url = new URL(path);  
        HttpURLConnection conn =  (HttpURLConnection) url.openConnection();  
        conn.setConnectTimeout(5000);  
        //获取到文件的大小   
        pd.setMax(conn.getContentLength());  
        InputStream is = conn.getInputStream();  
        return is;
	}
	
	/**
	 * 获取XMl
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public static InputStream getXML(String path) throws IOException
	{
		URL url = new URL(path);  
        HttpURLConnection conn =  (HttpURLConnection) url.openConnection();  
        conn.setConnectTimeout(5000);   
        InputStream is = conn.getInputStream();  
        return is;
	}
}
