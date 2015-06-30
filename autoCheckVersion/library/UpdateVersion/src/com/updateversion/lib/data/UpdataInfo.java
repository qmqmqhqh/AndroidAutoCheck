package com.updateversion.lib.data;

/** 
 * @author 马保国 
 * @email: 1345359393@qq.com 
 * @date 创建时间：2015-6-29 下午3:50:51 
 * @version 1.0 
 * @parameter
 * @since  
 * @return  
 * 将从服务器获取的更新结果解析后存储
 */

public class UpdataInfo {
	private String Version;
	private String UpdateUrl;
	private String Description;
	
	/**
	 * 设置从服务器获取的最新版本号
	 * @param Version，版本号
	 */
	public void setVersion(String Version)
	{
		this.Version=Version;
	}

	/**
	 * 设置从服务器获取的更新URL
	 * @param url,APK所在的url
	 */
	public void setUrl(String url)
	{
		this.UpdateUrl=url;
	}
	
	
	/**
	 * 设置从服务器获取的最新版本的描述信息
	 * @param desc，新版本描述信息
	 */
	public void setDescription(String desc)
	{
		this.Description=desc;
	}
	
	/**
	 * 获取最新版本号
	 * @return 最新版本号
	 */
	public String getVersion()
	{
		return this.Version;
	}

	/**
	 * 获取更新apk所在url
	 * @return url
	 */
	public String getUrl()
	{
		return this.UpdateUrl;
	}
	
	
	/**
	 * 获取更新版本描述信息
	 * @return 新版本描述信息
	 */
	public String getDescription()
	{
		return this.Description;
	}

}
