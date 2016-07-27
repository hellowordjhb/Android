package com.example.demo01;

import java.sql.*;

import com.example.demo01.R;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
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

public class HotelReservationActivity extends Activity {
	TextView message = null;
	EditText search_name = null;
	Connection con = null;
	private static final String url = "jdbc:mysql://mysql55.all123.net:3306/test";
	private static final String user = "root";
	private static final String pass = "735278";

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
				Class.forName("com.mysql.jdbc.Driver");
				Connection con = (Connection) DriverManager.getConnection(url,
						user, pass);
				System.out.println("Success connect Mysql server!");
				Statement st = (Statement) con.createStatement();
				String name = search_name.getText().toString().trim();
				String str = "select name from frankfurt where name like '%%'";
				StringBuffer sb = new StringBuffer(str);
				sb.insert(45, name);
				String sql = sb.toString();
				ResultSet rs = st.executeQuery(sql);
				String result = "";
				if (rs != null)
				{
					while(rs.next()) {
					result += rs.getString("name") + "\n";}
				}else {
					result = "";
				}
				// rs.close();
				return result;
			
			} catch (SQLException ee) {
				ee.printStackTrace();
			} catch (Exception e) {
				System.out.print("Error loading Mysql Driver!");
				e.printStackTrace();
			}
			return response;
		}

		@Override
		protected void onPostExecute(String result) {
			ListView listView = (ListView) findViewById(R.id.listview);
			String[] list = result.split("\n");
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(
					HotelReservationActivity.this, R.layout.array_item, list);
			listView.setAdapter(adapter);
			
			if(result == "")
			{
				AlertDialog.Builder builder = new AlertDialog.Builder(HotelReservationActivity.this);
			    //设置标题
			    builder.setTitle("提醒：");
			    //设置提示内容
			    builder.setMessage("您所搜索的景点无信息\n请重新输入！");
			    //设置中性按钮
			    builder.setNeutralButton("确定", null);
			    AlertDialog dlg = builder.create();
			    //显示AlertDialog
			    dlg.show();
			}
			else
			{
//				listView.setTextFilterEnabled(true);
			// 为listview绑定监听器
			listView.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> av, View v,
						int position, long id) {
					String info_name = ((TextView) v).getText().toString(); // 这里应该是这样的
					Intent intent = new Intent();
					intent.setClass(HotelReservationActivity.this,
							DescActivity.class);
					Bundle bundle = new Bundle();
					bundle.putString("name", info_name);
					intent.putExtras(bundle);
					startActivity(intent);
				}
			});
			}
		}

		protected void onProgressUpdate(Void... values) {
			Toast toast = Toast.makeText(getApplicationContext(), "正在查询。。。",
					Toast.LENGTH_SHORT);
			toast.show();
		}
	}
	
	
}
