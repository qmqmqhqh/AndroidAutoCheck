package com.example.test;

import com.updateversion.lib.data.UpdataInfo;
import com.updateversion.lib.main.updateCheck;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {

	public static String path="http://1.wuliutong.sinaapp.com/update/update1.xml";
	public static UpdataInfo info;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		updateCheck m_check=new updateCheck(MainActivity.this,path,"test.apk");
		ProgressDialog pd = new ProgressDialog(this);
		pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		pd.setMessage("正在下载新版本");
		//m_check.setLayoutView(new LayoutInfo(R.layout.dialog,R.id.exit_bt_ok,R.id.exit_bt_quit,R.id.edit_text_msg));
		m_check.setProgressDialog(pd);
		m_check.setVersionInfo("theVersion", "updateUrl", "description");
		m_check.check();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
