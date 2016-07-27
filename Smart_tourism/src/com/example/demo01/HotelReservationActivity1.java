package com.example.demo01;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class HotelReservationActivity1 extends Activity {
	TextView message = null;
	EditText search_name = null;
	public String name;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hotelreservation);
		Button btn = (Button) findViewById(R.id.btn);
		search_name = (EditText) findViewById(R.id.search_name);
		// 为btn绑定监听器
		btn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// 执行异步任务
				Search();
			}
		});
	}

	public void Search() {
		SearchTask searchTask = new SearchTask();
		searchTask.execute();
	}

	private class SearchTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... urls) {
			String response = "";

			try {

				// 创建一个DatabaseHelper对象
				DatabaseHelper dbHelper = new DatabaseHelper(
						HotelReservationActivity1.this, "test");
				// 只有调用了DatabaseHelper对象的getReadableDatabase()方法，或者是getWritableDatabase()方法之后，才会创建，或打开一个数据库
				SQLiteDatabase db = dbHelper.getWritableDatabase();

				String name = search_name.getText().toString().trim();
				String result = "";
				Cursor cursor = db.query("frankfurt", new String[] { "id",
						"name", "location", "description" }, "name" + " like?",
						new String[] { "%" + name + "%" }, null, null, null);
				while (cursor.moveToNext()) {
					result += cursor.getString(cursor.getColumnIndex("name"))
							+ "\n";
					// result += rs.getString("name")+"\n";
				}
				return result;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return response;
		}

		@Override
		protected void onPostExecute(String result) {

			ListView listView = (ListView) findViewById(R.id.listview);
			String[] list = result.split("\n");
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(
					HotelReservationActivity1.this, R.layout.array_item, list);
			listView.setAdapter(adapter);
			listView.setTextFilterEnabled(true);
			// 为listview绑定监听器
			listView.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> av, View v,
						int position, long id) {
					String info_name = ((TextView) v).getText().toString(); // 这里应该是这样的
					Intent intent = new Intent();
					intent.setClass(HotelReservationActivity1.this,
							DescActivity1.class);
					Bundle bundle = new Bundle();
					bundle.putString("name", info_name);
					intent.putExtras(bundle);
					startActivity(intent);
				}
			});

		}

		protected void onProgressUpdate(Void... values) {
			Toast toast = Toast.makeText(getApplicationContext(), "正在查询。。。",
					Toast.LENGTH_SHORT);
			toast.show();
		}
	}

}
