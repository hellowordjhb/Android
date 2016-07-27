package com.example.demo01;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnValueChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class BusTravealActivity extends Activity {

	private TextView title;
	private LinearLayout layout;
	private RelativeLayout search;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bustraveal);
		title = (TextView) findViewById(R.id.title_text);
		title.setText("大巴旅游");

		layout = (LinearLayout) findViewById(R.id.title_back);
		layout.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});

		Button startdateBn = (Button) findViewById(R.id.startdateBn);
		Button endingdateBn = (Button) findViewById(R.id.endingdateBn);
		Button travealdateBn = (Button) findViewById(R.id.travealdateBn);

		// 为“开始日期”按钮绑定监听器。
		startdateBn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View source) {
				Calendar c = Calendar.getInstance();
				// 直接创建一个DatePickerDialog对话框实例，并将它显示出来
				new DatePickerDialog(BusTravealActivity.this,
				// 绑定监听器
						new DatePickerDialog.OnDateSetListener() {
							@Override
							public void onDateSet(DatePicker dp, int year,
									int month, int dayOfMonth) {
								EditText show = (EditText) findViewById(R.id.show1);
								show.setText(year + "年" + (month + 1) + "月"
										+ dayOfMonth + "日");
							}
						}
						// 设置初始日期
						, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c
								.get(Calendar.DAY_OF_MONTH)).show();
			}
		});
		// 为“结束日期”按钮绑定监听器。
		endingdateBn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View source) {
				Calendar c = Calendar.getInstance();
				// 直接创建一个DatePickerDialog对话框实例，并将它显示出来
				new DatePickerDialog(BusTravealActivity.this,
				// 绑定监听器
						new DatePickerDialog.OnDateSetListener() {
							@Override
							public void onDateSet(DatePicker dp, int year,
									int month, int dayOfMonth) {
								EditText show = (EditText) findViewById(R.id.show2);
								show.setText(year + "年" + (month + 1) + "月"
										+ dayOfMonth + "日");
							}
						}
						// 设置初始日期
						, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c
								.get(Calendar.DAY_OF_MONTH) + 1).show();
			}
		});
		// 为“旅游天数”按钮绑定监听器。
		travealdateBn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View source) {
				NumberPicker mPicker = new NumberPicker(BusTravealActivity.this);
				mPicker.setMinValue(1);
				mPicker.setMaxValue(15);
				mPicker.setOnValueChangedListener(new OnValueChangeListener() {

					@Override
					public void onValueChange(NumberPicker picker, int oldVal,
							int newVal) {
						EditText mdate = (EditText) findViewById(R.id.show3);
						mdate.setText(String.valueOf(newVal));
					}
				});

				AlertDialog mAlertDialog = new AlertDialog.Builder(
						BusTravealActivity.this).setTitle("NumberPicker")
						.setView(mPicker).setPositiveButton("ok", null)
						.create();
				mAlertDialog.show();
			}
		});
	}

}