package com.example.demo01;

import java.io.InputStream;
import java.net.URL;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DescActivity extends Activity {
	private static final String url = "jdbc:mysql://mysql55.all123.net:3306/test";
	private static final String user = "root";
	private static final String pass = "735278";
	TextToSpeech tts;
	Bitmap bitmap;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.desc);
		TextView textname = (TextView) findViewById(R.id.desc_name);
		Intent intent = getIntent();
		String name = intent.getStringExtra("name");
		textname.setText(name);
		
		//图片加载
		Desc3();
		
		// 初始化TextToSpeech对象
		tts = new TextToSpeech(this, new OnInitListener() {
					@Override
					public void onInit(int status) {
						// 如果装载TTS引擎成功
						if (status == TextToSpeech.SUCCESS) {
							// 设置使用中文朗读
							int result = tts.setLanguage(Locale.CHINESE);
							// 如果不支持所设置的语言
							if (result != TextToSpeech.LANG_COUNTRY_AVAILABLE
									&& result != TextToSpeech.LANG_AVAILABLE) {
								Toast.makeText(DescActivity.this, "TTS暂时不支持这种语言的朗读。",
										Toast.LENGTH_SHORT).show();
							}
						}
					}
				});
				
		//执行description异步任务
		Desc2();

		
		
		//执行location任务
		Desc1();

	}

	public void Desc1() {
		DescTask1 descTask1 = new DescTask1();
		descTask1.execute();
	}

	public void Desc2() {
		DescTask2 descTask2 = new DescTask2();
		descTask2.execute();
		// executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
	}
	
	public void Desc3() {
		DescTask3 descTask3 = new DescTask3();
		descTask3.execute();
		// executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
	}

	public class DescTask1 extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... urls) {
			String response = "";
			try {
				Class.forName("com.mysql.jdbc.Driver");
				Connection con = (Connection) DriverManager.getConnection(url,
						user, pass);
				System.out.println("Success connect Mysql server!");
				// 从上级activity取出数据
				Intent intent = getIntent();
				String name = intent.getStringExtra("name");
				Statement st = (Statement) con.createStatement();
				// //测试
				// String str = "select * from frankfurt where name=''";
				// StringBuffer sb = new StringBuffer(str);
				// sb.insert(36, name);
				// String sql = sb.toString();
				// ResultSet rs = st.executeQuery(sql);
				// String result = "";
				// while(rs.next())
				// {
				// result = rs.getString("location");
				// }
				String str1 = "select Location from frankfurt where name=''";
				StringBuffer sb1 = new StringBuffer(str1);
				sb1.insert(43, name);
				String sql1 = sb1.toString();
				ResultSet rs1 = st.executeQuery(sql1);
				String result1 = "";
				while (rs1.next()) {
					result1 = rs1.getString("location");
				}
				return result1;
			} catch (SQLException ee) {
				ee.printStackTrace();
			} catch (Exception e) {
				System.out.print("Error loading Mysql Driver!");
				e.printStackTrace();
			}
			return response;
		}

		protected void onPostExecute(String result1) {
			// 显示location
			TextView textlocation = (TextView) findViewById(R.id.desc_location);
			Button btnlocation = (Button) findViewById(R.id.btn_location);
			textlocation.setText(result1);
			final String location = textlocation.getText().toString();
			// 为btnlocation绑定监听器
			btnlocation.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					tts.shutdown();
					Intent intent_loc = new Intent();
					intent_loc.setClass(DescActivity.this, GeocoderTest.class);
					Bundle bundle = new Bundle();
					bundle.putString("location", location);
					intent_loc.putExtras(bundle);
					startActivity(intent_loc);
				}
			});
		};

		protected void onProgressUpdate(Void... values) {
			// Toast toast = Toast.makeText(getApplicationContext(), "正在查询。。。",
			// Toast.LENGTH_SHORT);
			// toast.show();
		}
	}

	public class DescTask2 extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... urls) {
			String response = "";
			try {
				Class.forName("com.mysql.jdbc.Driver");
				Connection con = (Connection) DriverManager.getConnection(url,
						user, pass);
				System.out.println("Success connect Mysql server!");
				// 从上级activity取出数据
				Intent intent = getIntent();
				String name = intent.getStringExtra("name");
				Statement st = (Statement) con.createStatement();
				String str2 = "select Description from frankfurt where name=''";
				StringBuffer sb2 = new StringBuffer(str2);
				sb2.insert(46, name);
				String sql2 = sb2.toString();
				ResultSet rs2 = st.executeQuery(sql2);
				String result2 = "";
				while (rs2.next()) {
					result2 = rs2.getString("description");
				}
				return result2;
			} catch (SQLException ee) {
				ee.printStackTrace();
			} catch (Exception e) {
				System.out.print("Error loading Mysql Driver!");
				e.printStackTrace();
			}
			return response;
		}

		protected void onPostExecute(String result2) {
			// 显示description
			final TextView textdescription = (TextView) findViewById(R.id.desc_description);
			textdescription.setText(result2);
			Button speaking = (Button) findViewById(R.id.btn_speaking);
			//为语音按钮绑定监听器
			speaking.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					// 执行朗读
//					tts.shutdown();
					tts.speak(textdescription.getText().toString(),
							TextToSpeech.QUEUE_FLUSH, null);
				}
			});
		}

		protected void onProgressUpdate(Void... values) {
			// Toast toast = Toast.makeText(getApplicationContext(), "正在查询。。。",
			// Toast.LENGTH_SHORT);
			// toast.show();
		}
	}
	
	public class DescTask3 extends AsyncTask<String, Void, Bitmap> {
		@Override
		protected Bitmap doInBackground(String... urls) {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				Connection con = (Connection) DriverManager.getConnection(url,
						user, pass);
				System.out.println("Success connect Mysql server!");
				// 从上级activity取出数据
				Intent intent = getIntent();
				String name = intent.getStringExtra("name");
				Statement st = (Statement) con.createStatement();
				String str3 = "select id from frankfurt where name=''";
				StringBuffer sb3 = new StringBuffer(str3);
				sb3.insert(37, name);
				String sql3 = sb3.toString();
				ResultSet rs3 = st.executeQuery(sql3);
				int num = 0;
				while (rs3.next()) {
					num = rs3.getInt("id");
				}
				String string = "http://p1.qhimg.com/t01a303e61a6b4a6804.jpg\n"
						+ "http://www.gdcts.com/upload/mu-de-di/de-guo/1437084671.jpg\n"
						+ "http://dimg02.c-ctrip.com/images/tg/424/547/616/6beaed1793f24b46951f046654e6d8b1_C_640_320.jpg";
				String[] list = string.split("\n");
				String imageloc = list[num-1];
					// 定义一个URL对象
					URL url = new URL(imageloc);
					// 打开该URL对应的资源的输入流
					InputStream is = url.openStream();
					// 从InputStream中解析出图片
					bitmap = BitmapFactory.decodeStream(is);
					// 发送消息、通知UI组件显示该图片
//					handler.sendEmptyMessage(0x123);
					is.close();
			} catch (SQLException ee) {
				ee.printStackTrace();
			} catch (Exception e) {
				System.out.print("Error loading Mysql Driver!");
				e.printStackTrace();
			}
			return bitmap;
		}

		protected void onPostExecute(Bitmap bitmap) {
			ImageView imageView = (ImageView) findViewById(R.id.image);
			// 使用ImageView显示该图片
			imageView.setImageBitmap(bitmap);
			
		};

		protected void onProgressUpdate(Void... values) {
			// Toast toast = Toast.makeText(getApplicationContext(), "正在查询。。。",
			// Toast.LENGTH_SHORT);
			// toast.show();
		}
	}
	
//	public void onDestroy()
//	{
//		// 关闭TextToSpeech对象
//		if (tts != null)
//		{
//			tts.shutdown();
//		
//		unbindService((ServiceConnection) tts);
//		}
//		    super.onDestroy();
//	}
}
