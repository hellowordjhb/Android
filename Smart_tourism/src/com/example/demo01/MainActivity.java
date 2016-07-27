package com.example.demo01;

import com.example.fragment.HomeFragment;
import com.example.fragment.MyFragment;
import com.example.fragment.IndentFragment;
import com.example.fragment.SettingFragment;
import com.example.util.DummyTabContent;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

public class MainActivity extends FragmentActivity {

	TabHost tabHost;
	TabWidget tabWidget;
	LinearLayout bottom_layout;
	int CURRENT_TAB = 0; // 设置常量
	HomeFragment homeFragment;
	MyFragment myFragment;
	IndentFragment indentFragment;
	SettingFragment settingFragment;
	android.support.v4.app.FragmentTransaction ft;
	LinearLayout tabIndicator1, tabIndicator2, tabIndicator3, tabIndicator4,
			tabIndicator5;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// //在主线程中忽略
		// if (android.os.Build.VERSION.SDK_INT > 9) {
		// StrictMode.ThreadPolicy policy = new
		// StrictMode.ThreadPolicy.Builder().permitAll().build();
		// StrictMode.setThreadPolicy(policy);
		// }

		// findTabView();
		tabHost = (TabHost) findViewById(android.R.id.tabhost);
		tabWidget = (TabWidget) findViewById(android.R.id.tabs);
		LinearLayout layout = (LinearLayout) tabHost.getChildAt(0);
		TabWidget tw = (TabWidget) layout.getChildAt(1);

		tabIndicator1 = (LinearLayout) LayoutInflater.from(this).inflate(
				R.layout.tab_indicator, tw, false);
		TextView tvTab1 = (TextView) tabIndicator1.getChildAt(1);
		ImageView ivTab1 = (ImageView) tabIndicator1.getChildAt(0);
		ivTab1.setBackgroundResource(R.drawable.selector_home);
		tvTab1.setText(R.string.buttom_home);

		tabIndicator2 = (LinearLayout) LayoutInflater.from(this).inflate(
				R.layout.tab_indicator, tw, false);
		TextView tvTab2 = (TextView) tabIndicator2.getChildAt(1);
		ImageView ivTab2 = (ImageView) tabIndicator2.getChildAt(0);
		ivTab2.setBackgroundResource(R.drawable.selector_my);
		tvTab2.setText(R.string.buttom_my);

		tabIndicator3 = (LinearLayout) LayoutInflater.from(this).inflate(
				R.layout.tab_indicator, tw, false);
		TextView tvTab3 = (TextView) tabIndicator3.getChildAt(1);
		ImageView ivTab3 = (ImageView) tabIndicator3.getChildAt(0);
		ivTab3.setBackgroundResource(R.drawable.selector_search);
		tvTab3.setText(R.string.buttom_indent);

		tabIndicator4 = (LinearLayout) LayoutInflater.from(this).inflate(
				R.layout.tab_indicator, tw, false);
		TextView tvTab4 = (TextView) tabIndicator4.getChildAt(1);
		ImageView ivTab4 = (ImageView) tabIndicator4.getChildAt(0);
		ivTab4.setBackgroundResource(R.drawable.selector_setting);
		tvTab4.setText(R.string.buttom_setting);

		tabHost.setup();

		/** 监听 */
		TabHost.OnTabChangeListener tabChangeListener = new TabHost.OnTabChangeListener() {
			@Override
			public void onTabChanged(String tabId) {

				/** 碎片管理 */
				android.support.v4.app.FragmentManager fm = getSupportFragmentManager();
				homeFragment = (HomeFragment) fm.findFragmentByTag("home");
				myFragment = (MyFragment) fm.findFragmentByTag("my");
				indentFragment = (IndentFragment) fm
						.findFragmentByTag("search");
				settingFragment = (SettingFragment) fm
						.findFragmentByTag("setting");
				ft = fm.beginTransaction();

				/** 如果存在Detaches掉 */
				if (homeFragment != null)
					ft.detach(homeFragment);

				/** 如果存在Detaches掉 */
				if (myFragment != null)
					ft.detach(myFragment);

				/** 如果存在Detaches掉 */
				if (indentFragment != null)
					ft.detach(indentFragment);

				/** 如果存在Detaches掉 */
				if (settingFragment != null)
					ft.detach(settingFragment);

				/** 如果当前选项卡是home */
				if (tabId.equalsIgnoreCase("home")) {
					isTabHome();
					CURRENT_TAB = 1;

					/** 如果当前选项卡是my */
				} else if (tabId.equalsIgnoreCase("my")) {
					isTabMy();
					CURRENT_TAB = 2;

					/** 如果当前选项卡是search */
				} else if (tabId.equalsIgnoreCase("search")) {
					isTabSearch();
					CURRENT_TAB = 3;

					/** 如果当前选项卡是setting */
				} else if (tabId.equalsIgnoreCase("setting")) {
					isTabSetting();
					CURRENT_TAB = 4;

				} else {
					switch (CURRENT_TAB) {
					case 1:
						isTabHome();
						break;
					case 2:
						isTabMy();
						break;
					case 3:
						isTabSearch();
						break;
					case 4:
						isTabSetting();
						break;
					// default:
					// isTabHome();
					// break;
					}

				}
				ft.commit();
			}

		};
		// 设置初始选项卡
		tabHost.setCurrentTab(0);
		tabHost.setOnTabChangedListener(tabChangeListener);
		initTab();
		/** 设置初始化界面 */
		tabHost.setCurrentTab(0);

	}

	// 判断当前
	public void isTabHome() {

		if (homeFragment == null) {
			ft.add(R.id.realtabcontent, new HomeFragment(), "home");
		} else {
			ft.attach(homeFragment);
		}
	}

	public void isTabMy() {

		if (myFragment == null) {
			ft.add(R.id.realtabcontent, new MyFragment(), "my");
		} else {
			ft.attach(myFragment);
		}
	}

	public void isTabSearch() {

		if (indentFragment == null) {
			ft.add(R.id.realtabcontent, new IndentFragment(), "search");
		} else {
			ft.attach(indentFragment);
		}
	}

	public void isTabSetting() {
		if (settingFragment == null) {
			ft.add(R.id.realtabcontent, new SettingFragment(), "setting");
		} else {
			ft.attach(settingFragment);
		}
	}

	/**
	 * 初始化选项卡
	 * 
	 * */
	public void initTab() {

		TabHost.TabSpec tSpecHome = tabHost.newTabSpec("home");
		tSpecHome.setIndicator(tabIndicator1);
		tSpecHome.setContent(new DummyTabContent(getBaseContext()));
		tabHost.addTab(tSpecHome);

		TabHost.TabSpec tSpecMy = tabHost.newTabSpec("my");
		tSpecMy.setIndicator(tabIndicator2);
		tSpecMy.setContent(new DummyTabContent(getBaseContext()));
		tabHost.addTab(tSpecMy);

		TabHost.TabSpec tSpecSearch = tabHost.newTabSpec("search");
		tSpecSearch.setIndicator(tabIndicator3);
		tSpecSearch.setContent(new DummyTabContent(getBaseContext()));
		tabHost.addTab(tSpecSearch);

		TabHost.TabSpec tSpecSetting = tabHost.newTabSpec("setting");
		tSpecSetting.setIndicator(tabIndicator4);
		tSpecSetting.setContent(new DummyTabContent(getBaseContext()));
		tabHost.addTab(tSpecSetting);

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
