package com.example.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.adapter.ViewPagerAdapter;
import com.example.demo01.BitmapUtils;
import com.example.demo01.BusTravealActivity;
import com.example.demo01.GuideActivity;
import com.example.demo01.HotelReservationActivity;
import com.example.demo01.HotelReservationActivity1;
import com.example.demo01.R;
import com.example.demo01.SpecialLineActivity;
import com.example.myinterface.HomeClickListener;
import com.example.util.HomeButton;

public class HomeFragment extends Fragment implements OnClickListener,
		OnPageChangeListener {

	private ViewPager vp;
	private ViewPagerAdapter vpAdapter;
	private List<View> views;
	private int width;
	private int height;

	private RelativeLayout relativeLayout;

	// 引导图片资源
	private static final int[] pics = { R.drawable.text1, R.drawable.text2,
			R.drawable.text3, R.drawable.lyouu_new };

	// 底部小点图片
	private ImageView[] dots;

	// 记录当前选中位置
	private int currentIndex;
	private View view;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.home, container, false);
		// 为大巴旅游绑定监听器，并跳转到搜索界面
		HomeButton button01 = (HomeButton) view.findViewById(R.id.bustraveal);
		button01.setOnHomeClick(new HomeClickListener() {

			@Override
			public void onclick() {
				Intent intent = new Intent();
				intent.setClass(getActivity(), BusTravealActivity.class);// 可并成一行
				startActivity(intent);
			}
		});
		// 为导游翻译绑定监听器
		HomeButton button02 = (HomeButton) view.findViewById(R.id.guide);
		button02.setOnHomeClick(new HomeClickListener() {

			@Override
			public void onclick() {
				Intent intent = new Intent();
				intent.setClass(getActivity(), GuideActivity.class);
				startActivity(intent);
			}
		});
		// 为旅欧专线绑定
		HomeButton button03 = (HomeButton) view.findViewById(R.id.specialline);
		button03.setOnHomeClick(new HomeClickListener() {

			@Override
			public void onclick() {
				Intent intent = new Intent();
				intent.setClass(getActivity(), SpecialLineActivity.class);
				startActivity(intent);
			}
		});
		// 为酒店预订绑定
		HomeButton button04 = (HomeButton) view
				.findViewById(R.id.hotelreservation);
		button04.setOnHomeClick(new HomeClickListener() {

			@Override
			public void onclick() {
				Intent intent = new Intent();
				intent.setClass(getActivity(), HotelReservationActivity1.class);
				startActivity(intent);
			}
		});
		// 等到屏幕的大小
		width = getActivity().getWindowManager().getDefaultDisplay().getWidth();
		// 以670*240的图片为例,正常中不要这样用
		height = width * 240 / 670;
		relativeLayout = (RelativeLayout) view.findViewById(R.id.relative);
		LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) relativeLayout
				.getLayoutParams();
		layoutParams.width = width;
		layoutParams.height = height;
		relativeLayout.setLayoutParams(layoutParams);

		views = new ArrayList<View>();

		LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);

		// 初始化引导图片列表
		for (int i = 0; i < pics.length; i++) {
			ImageView iv = new ImageView(getActivity());
			iv.setLayoutParams(mParams);
			// 改变大小
			// iv.setImageResource(pics[i]);
			iv.setImageBitmap(BitmapUtils.zoomImage(
					BitmapFactory.decodeResource(getResources(), pics[i]),
					width, height));
			views.add(iv);
		}
		vp = (ViewPager) view.findViewById(R.id.viewpager);

		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
				width, height);
		params.leftMargin = 10;
		params.topMargin = 10;
		params.rightMargin = 10;
		vp.setLayoutParams(params);

		// 初始化Adapter
		vpAdapter = new ViewPagerAdapter(views);
		vp.setAdapter(vpAdapter);
		// 绑定回调
		vp.setOnPageChangeListener(this);

		// 初始化底部小点
		initDots();

		return view;
	}

	private void initDots() {
		LinearLayout ll = (LinearLayout) view.findViewById(R.id.ll);
		dots = new ImageView[pics.length];

		// 循环取得小点图片
		for (int i = 0; i < pics.length; i++) {
			dots[i] = (ImageView) ll.getChildAt(i);
			dots[i].setEnabled(true);// 都设为灰色
			dots[i].setOnClickListener(this);
			dots[i].setTag(i);// 设置位置tag，方便取出与当前位置对应
		}

		currentIndex = 0;
		dots[currentIndex].setEnabled(false);// 设置为白色，即选中状态
	}

	/**
	 * 设置当前的引导页
	 */
	private void setCurView(int position) {
		if (position < 0 || position >= pics.length) {
			return;
		}

		vp.setCurrentItem(position);
	}

	/**
	 * 这只当前引导小点的选中
	 */
	private void setCurDot(int positon) {
		if (positon < 0 || positon > pics.length - 1 || currentIndex == positon) {
			return;
		}

		dots[positon].setEnabled(false);
		dots[currentIndex].setEnabled(true);

		currentIndex = positon;
	}

	// 当滑动状态改变时调用
	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	// 当当前页面被滑动时调用
	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

		vp.requestDisallowInterceptTouchEvent(true);
	}

	// 当新的页面被选中时调用
	@Override
	public void onPageSelected(int arg0) {
		// 设置底部小点选中状态
		setCurDot(arg0);
	}

	@Override
	public void onClick(View view) {
		int position = (Integer) view.getTag();
		setCurView(position);
		setCurDot(position);
	}

}
