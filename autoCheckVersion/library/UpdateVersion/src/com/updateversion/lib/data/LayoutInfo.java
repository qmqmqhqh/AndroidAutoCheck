package com.updateversion.lib.data;
/** 
 * @author 马保国 
 * @email: 1345359393@qq.com 
 * @date 创建时间：2015-6-29 下午3:50:51 
 * @version 1.0 
 * @parameter  
 * @since  
 * @return 
 * 要自定义显示的选择更新的对话框信息 
 */
public class LayoutInfo {
	public int LayoutId;
	public int Bt_Ok;
	public int Bt_Quit;
	public int Title;
	public int Tip;
	
	/**
	 * 构造函数
	 * @param layout,布局ID
	 * @param ok，确定按钮ID
	 * @param quit，取消按钮ID
	 * @param message，提示消息ID
	 */
	public LayoutInfo(int layout,int ok,int quit,int message)
	{
		this.LayoutId=layout;
		this.Bt_Ok=ok;
		this.Bt_Quit=quit;
		this.Title=-1;
		this.Tip=message;
	}
	
	/**
	 * 构造函数
	 * @param layout,布局ID
	 * @param ok，确定按钮ID
	 * @param quit，取消按钮ID
	 * @param message，提示消息ID
	 * @param title，标题ID
	 */
	public LayoutInfo(int layout,int ok,int quit,int title,int message)
	{
		this.LayoutId=layout;
		this.Bt_Ok=ok;
		this.Bt_Quit=quit;
		this.Title=title;
		this.Tip=message;
	}
}
