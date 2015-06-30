package com.updateversion.lib.main;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import com.updateversion.lib.data.LayoutInfo;
import com.updateversion.lib.data.UpdataInfo;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * 更新检测，实现更新检测并下载的所有功能
 * @author baoguo
 *
 */
public class updateCheck {

	public static final int UN_NEED_UPDATE = 0;// 不需要更新
	public static final int NEED_UPDATE = 1; // 需要更新
	public static final int DOWNLOAD_SUCCESS = 2; // 下载成功
	public static final int DOWNLOAD_FAIL = 3; // 下载失败
	public static final int NET_UN_CON = 4;// WIFI未连接

	private int CUR_STATE = UN_NEED_UPDATE;
	private Context context;
	private String BasePath;
	public UpdataInfo info;
	private AlertDialog Dialog;
	private LayoutInfo layInfo;
	private ProgressDialog pd;
	
	private String VersionName;
	private String UrlName;
	private String DescriptionName;
	private String filename;

	/**
	 * 构造函数
	 * @param context上下文环境
	 * @param basePath，服务器更新xml的路径
	 * @param filename，如果更新，下载后保存的文件名
	 */
	public updateCheck(Context context, String basePath,String filename) {
		this.context = context;
		this.BasePath = basePath;
		this.filename=filename;
		VersionName="version";
		UrlName="url";
		DescriptionName="description";
	}
	
	
	

	/**
	 * 设置更新选择提示框
	 * @param linfo，提示框控件信息
	 */
	public void setLayoutView(LayoutInfo linfo)
	{
		this.layInfo=linfo;
	}
	
	/**
	 * 设置下载进度条
	 * @param pd，ProgressDialog进度条
	 */
	public void setProgressDialog(ProgressDialog pd)
	{
		this.pd=pd;
	}
	
	
	/**
	 * 设置版本信息，即从服务器的更新xml中获取的版本名，url名，描述名
	 * @param versionname版本号
	 * @param urlname更新链接
	 * @param descname新版本描述信息
	 */
	public void setVersionInfo(String versionname,String urlname,String descname)
	{
		this.VersionName=versionname;
		this.UrlName=urlname;
		this.DescriptionName=descname;
	}
	
	/**
	 * 获取更新信息并解析
	 * @return UpdataInfo
	 */
	private UpdataInfo getUpdateInfo() {
		UpdataInfo info;
		try {
			InputStream is = HttpCon.getXML(BasePath);
			info = DataParser.getUpdataInfo(is,VersionName,UrlName,DescriptionName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return null;
		}
		return info;
	}

	/**
	 * 判断是否需要更新
	 * @return 如果需要更新返回true，否则返回false
	 */
	public boolean ifUpdate() {
		boolean result = false;
		UpdataInfo info;
		if ((info = getUpdateInfo()) != null
				&& !info.getVersion().equals(
						CommonCheck.getVersionName(context))) {
			// showUpdataDialog(info);
			this.info = info;
			result = true;
		}
		return result;
	}

	/**
	 * 如果需要更新，弹出对话框提示用户更新程序，对话框的样式为默认形式
	 * @param info,UpdataInfo更新信息
	 */
	private void showUpdataDialog(final UpdataInfo info) {
		AlertDialog.Builder builer = new Builder(context);
		builer.setTitle("版本升级");
		builer.setMessage(info.getDescription());
		// 当点确定按钮时从服务器上下载 新的apk 然后安装
		builer.setPositiveButton("确定", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				downLoadApk(info);
				Dialog.dismiss();
			}
		});
		builer.setNegativeButton("取消", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
			}
		});
		Dialog = builer.create();
		Dialog.show();
	}

	/**
	 * 如果需要更新，弹出对话框提示用户更新程序，对话框的样式为自定义形式
	 * @param info,UpdataInfo更新信息
	 * @param layinfo,LayoutInfo控件信息
	 */
	private void showUpdataDialog(final UpdataInfo info, LayoutInfo layinfo) {
		if (layinfo == null)
			showUpdataDialog(info);
		else {
			Dialog = new AlertDialog.Builder(context).create();
			Dialog.setCanceledOnTouchOutside(false);
			LayoutInflater inflater = LayoutInflater.from(context);
			View v = inflater.inflate(layinfo.LayoutId, null);
			TextView tip = (TextView) v.findViewById(layinfo.Tip);
			tip.setText(info.getDescription());
			Button bt_ok = (Button) v.findViewById(layinfo.Bt_Ok);
			Button bt_quit = (Button) v.findViewById(layinfo.Bt_Quit);
			bt_ok.setOnClickListener(new android.view.View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					downLoadApk(info);
					Dialog.dismiss();
				}
			});

			bt_quit.setOnClickListener(new android.view.View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Dialog.dismiss();
				}
			});

			Dialog.show();
			Dialog.getWindow().setContentView(v);
			Dialog.setCancelable(true);
			
		}

	}
	
	
	/**
	 * 从服务器下载APK
	 * @param info，UpdataInfo更新信息
	 */
	@SuppressWarnings("deprecation")
	protected void downLoadApk(final UpdataInfo info) {
		if(pd==null)
		{
			pd = new ProgressDialog(context);
			pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			pd.setMessage("正在下载更新");
			pd.setButton("取消", new OnClickListener(){

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					pd.cancel();
				}});
		}
		//ClipDrawable d = new ClipDrawable(new ColorDrawable(Color.BLACK), Gravity.LEFT, ClipDrawable.HORIZONTAL);
		//pd.setProgressDrawable(d);
		pd.show();
		new Thread() {
			@Override
			public void run() {
				try {
					File file = DataParser.getFileFromServer(info.getUrl(),filename, pd);
					CUR_STATE = DOWNLOAD_SUCCESS;
					sleep(1000);
					installApk(file);
					pd.dismiss(); // 结束掉进度条对话框
				} catch (Exception e) {
					CUR_STATE = DOWNLOAD_FAIL;
					pd.dismiss();
				}
			}
		}.start();

	}
	
	/**
	 * 安装APK
	 * @param file，下载好的APK文件
	 */
	protected void installApk(File file) {
		if (CUR_STATE == DOWNLOAD_SUCCESS) {
			Intent intent = new Intent();
			// 执行动作
			intent.setAction(Intent.ACTION_VIEW);
			// 执行的数据类型
			intent.setDataAndType(Uri.fromFile(file),
					"application/vnd.android.package-archive");
			context.startActivity(intent);
		}
	}


	/**
	 * 开始检测，相当于main函数
	 */
	public void check() {
		if (CommonCheck.isNetworkCon(context.getApplicationContext())
				&& CommonCheck.isWiFICon()) {
			Thread check = new Thread(new checkUpdate());
			check.start();
		} else
			CUR_STATE=NET_UN_CON;
	}
	
	/*
	 * 获取更新状态
	 */
	public int getState()
	{
		return CUR_STATE;
	}

	
	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case 0:
				CUR_STATE = NEED_UPDATE;
				showUpdataDialog(info,layInfo);
				break;
			case 1:
				CUR_STATE = UN_NEED_UPDATE;
				break;
			}
		}
	};

	/**
	 * Runnable,异步检测是否需要更新
	 * @author baoguo
	 *
	 */
	public class checkUpdate implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			Message msg = new Message();

			if (ifUpdate())
				msg.what = 0;
			else
				msg.what = 1;
			handler.sendMessage(msg);
		}
	}

}
