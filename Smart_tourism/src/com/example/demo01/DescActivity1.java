package com.example.demo01;

import java.io.InputStream;
import java.net.URL;
import java.util.Locale;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

public class DescActivity1 extends Activity {

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

		// ͼƬ����
		Desc3();

		// ��ʼ��TextToSpeech����
		tts = new TextToSpeech(this, new OnInitListener() {
			@Override
			public void onInit(int status) {
				// ���װ��TTS����ɹ�
				if (status == TextToSpeech.SUCCESS) {
					// ����ʹ�������ʶ�
					int result = tts.setLanguage(Locale.CHINESE);
					// �����֧�������õ�����
					if (result != TextToSpeech.LANG_COUNTRY_AVAILABLE
							&& result != TextToSpeech.LANG_AVAILABLE) {
						Toast.makeText(DescActivity1.this, "TTS��ʱ��֧���������Ե��ʶ���",
								Toast.LENGTH_SHORT).show();
					}
				}
			}
		});

		// ִ��description�첽����
		Desc2();

		// ִ��location����
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
				// ���ϼ�activityȡ������
				Intent intent = getIntent();
				String name = intent.getStringExtra("name");
				// //����
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

				// ����һ��DatabaseHelper����
				DatabaseHelper dbHelper = new DatabaseHelper(
						DescActivity1.this, "test");
				// ֻ�е�����DatabaseHelper�����getReadableDatabase()������������getWritableDatabase()����֮�󣬲Żᴴ�������һ�����ݿ�
				SQLiteDatabase db = dbHelper.getReadableDatabase();
				Cursor cursor = db.query("frankfurt",
						new String[] { "location" }, "name=?",
						new String[] { name }, null, null, null);
				String result1 = "";
				while (cursor.moveToNext()) {
					result1 += cursor.getString(cursor
							.getColumnIndex("location"));
					// result += rs.getString("name")+"\n";
				}
				return result1;
			} catch (Exception e) {
				System.out.print("Error loading Mysql Driver!");
				e.printStackTrace();
			}
			return response;
		}

		protected void onPostExecute(String result1) {
			// ��ʾlocation
			TextView textlocation = (TextView) findViewById(R.id.desc_location);
			Button btnlocation = (Button) findViewById(R.id.btn_location);
			textlocation.setText(result1);
			final String location = textlocation.getText().toString();
			// Ϊbtnlocation�󶨼�����
			btnlocation.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					tts.shutdown();
					Intent intent_loc = new Intent();
					intent_loc.setClass(DescActivity1.this,
							GeocoderTest.class);
					Bundle bundle = new Bundle();
					bundle.putString("location", location);
					intent_loc.putExtras(bundle);
					startActivity(intent_loc);
				}
			});
		};

		protected void onProgressUpdate(Void... values) {
			// Toast toast = Toast.makeText(getApplicationContext(), "���ڲ�ѯ������",
			// Toast.LENGTH_SHORT);
			// toast.show();
		}
	}

	public class DescTask2 extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... urls) {
			String response = "";
			try {
				// ���ϼ�activityȡ������
				Intent intent = getIntent();
				String name = intent.getStringExtra("name");

				// ����һ��DatabaseHelper����
				DatabaseHelper dbHelper = new DatabaseHelper(
						DescActivity1.this, "test");
				// ֻ�е�����DatabaseHelper�����getReadableDatabase()������������getWritableDatabase()����֮�󣬲Żᴴ�������һ�����ݿ�
				SQLiteDatabase db = dbHelper.getReadableDatabase();
				Cursor cursor = db.query("frankfurt",
						new String[] { "description" }, "name=?",
						new String[] { name }, null, null, null);
				String result2 = "";
				while (cursor.moveToNext()) {
					result2 += cursor.getString(cursor
							.getColumnIndex("description"));
					// result += rs.getString("name")+"\n";
				}
				return result2;
			} catch (Exception e) {
				System.out.print("Error loading Mysql Driver!");
				e.printStackTrace();
			}
			return response;
		}

		protected void onPostExecute(String result2) {
			// ��ʾdescription
			final TextView textdescription = (TextView) findViewById(R.id.desc_description);
			textdescription.setText(result2);
			Button speaking = (Button) findViewById(R.id.btn_speaking);
			// Ϊ������ť�󶨼�����
			speaking.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					// ִ���ʶ�
					// tts.shutdown();
					tts.speak(textdescription.getText().toString(),
							TextToSpeech.QUEUE_FLUSH, null);
				}
			});
		}

		protected void onProgressUpdate(Void... values) {
			// Toast toast = Toast.makeText(getApplicationContext(), "���ڲ�ѯ������",
			// Toast.LENGTH_SHORT);
			// toast.show();
		}
	}

	public class DescTask3 extends AsyncTask<String, Void, Bitmap> {
		@Override
		protected Bitmap doInBackground(String... urls) {
			try {

				// ���ϼ�activityȡ������
				Intent intent = getIntent();
				String name = intent.getStringExtra("name");

				// ����һ��DatabaseHelper����
				DatabaseHelper dbHelper = new DatabaseHelper(
						DescActivity1.this, "test");
				// ֻ�е�����DatabaseHelper�����getReadableDatabase()������������getWritableDatabase()����֮�󣬲Żᴴ�������һ�����ݿ�
				SQLiteDatabase db = dbHelper.getReadableDatabase();
				Cursor cursor = db.query("frankfurt", new String[] { "id" },
						"name=?", new String[] { name }, null, null, null);
				int num = 0;
				while (cursor.moveToNext()) {
					num = cursor.getInt(cursor.getColumnIndex("id"));
					// result += rs.getString("name")+"\n";
				}

				String string = "http://p1.qhimg.com/t01a303e61a6b4a6804.jpg\n"
						+ "http://dimg02.c-ctrip.com/images/tg/424/547/616/6beaed1793f24b46951f046654e6d8b1_C_640_320.jpg";

				String[] list = string.split("\n");
				String imageloc = list[num - 1];
				// ����һ��URL����
				URL url = new URL(imageloc);
				// �򿪸�URL��Ӧ����Դ��������
				InputStream is = url.openStream();
				// ��InputStream�н�����ͼƬ
				bitmap = BitmapFactory.decodeStream(is);
				// ������Ϣ��֪ͨUI�����ʾ��ͼƬ
				// handler.sendEmptyMessage(0x123);
				is.close();
			} catch (Exception e) {
				System.out.print("Error loading Mysql Driver!");
				e.printStackTrace();
			}
			return bitmap;
		}

		protected void onPostExecute(Bitmap bitmap) {
			ImageView imageView = (ImageView) findViewById(R.id.image);
			// ʹ��ImageView��ʾ��ͼƬ
			imageView.setImageBitmap(bitmap);

		}

		protected void onProgressUpdate(Void... values) {
			// Toast toast = Toast.makeText(getApplicationContext(), "���ڲ�ѯ������",
			// Toast.LENGTH_SHORT);
			// toast.show();
		}
	}

	// public void onDestroy()
	// {
	// // �ر�TextToSpeech����
	// if (tts != null)
	// {
	// tts.shutdown();
	//
	// unbindService((ServiceConnection) tts);
	// }
	// super.onDestroy();
	// }
}
