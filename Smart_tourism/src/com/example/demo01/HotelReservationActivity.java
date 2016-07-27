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
		// Ϊbtn�󶨼�����
		btn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// ִ���첽����
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
			    //���ñ���
			    builder.setTitle("���ѣ�");
			    //������ʾ����
			    builder.setMessage("���������ľ�������Ϣ\n���������룡");
			    //�������԰�ť
			    builder.setNeutralButton("ȷ��", null);
			    AlertDialog dlg = builder.create();
			    //��ʾAlertDialog
			    dlg.show();
			}
			else
			{
//				listView.setTextFilterEnabled(true);
			// Ϊlistview�󶨼�����
			listView.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> av, View v,
						int position, long id) {
					String info_name = ((TextView) v).getText().toString(); // ����Ӧ����������
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
			Toast toast = Toast.makeText(getApplicationContext(), "���ڲ�ѯ������",
					Toast.LENGTH_SHORT);
			toast.show();
		}
	}
	
	
}
