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

	// ����ͼƬ��Դ
	private static final int[] pics = { R.drawable.text1, R.drawable.text2,
			R.drawable.text3, R.drawable.lyouu_new };

	// �ײ�С��ͼƬ
	private ImageView[] dots;

	// ��¼��ǰѡ��λ��
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
		// Ϊ������ΰ󶨼�����������ת����������
		HomeButton button01 = (HomeButton) view.findViewById(R.id.bustraveal);
		button01.setOnHomeClick(new HomeClickListener() {

			@Override
			public void onclick() {
				Intent intent = new Intent();
				intent.setClass(getActivity(), BusTravealActivity.class);// �ɲ���һ��
				startActivity(intent);
			}
		});
		// Ϊ���η���󶨼�����
		HomeButton button02 = (HomeButton) view.findViewById(R.id.guide);
		button02.setOnHomeClick(new HomeClickListener() {

			@Override
			public void onclick() {
				Intent intent = new Intent();
				intent.setClass(getActivity(), GuideActivity.class);
				startActivity(intent);
			}
		});
		// Ϊ��ŷר�߰�
		HomeButton button03 = (HomeButton) view.findViewById(R.id.specialline);
		button03.setOnHomeClick(new HomeClickListener() {

			@Override
			public void onclick() {
				Intent intent = new Intent();
				intent.setClass(getActivity(), SpecialLineActivity.class);
				startActivity(intent);
			}
		});
		// Ϊ�Ƶ�Ԥ����
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
		// �ȵ���Ļ�Ĵ�С
		width = getActivity().getWindowManager().getDefaultDisplay().getWidth();
		// ��670*240��ͼƬΪ��,�����в�Ҫ������
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

		// ��ʼ������ͼƬ�б�
		for (int i = 0; i < pics.length; i++) {
			ImageView iv = new ImageView(getActivity());
			iv.setLayoutParams(mParams);
			// �ı��С
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

		// ��ʼ��Adapter
		vpAdapter = new ViewPagerAdapter(views);
		vp.setAdapter(vpAdapter);
		// �󶨻ص�
		vp.setOnPageChangeListener(this);

		// ��ʼ���ײ�С��
		initDots();

		return view;
	}

	private void initDots() {
		LinearLayout ll = (LinearLayout) view.findViewById(R.id.ll);
		dots = new ImageView[pics.length];

		// ѭ��ȡ��С��ͼƬ
		for (int i = 0; i < pics.length; i++) {
			dots[i] = (ImageView) ll.getChildAt(i);
			dots[i].setEnabled(true);// ����Ϊ��ɫ
			dots[i].setOnClickListener(this);
			dots[i].setTag(i);// ����λ��tag������ȡ���뵱ǰλ�ö�Ӧ
		}

		currentIndex = 0;
		dots[currentIndex].setEnabled(false);// ����Ϊ��ɫ����ѡ��״̬
	}

	/**
	 * ���õ�ǰ������ҳ
	 */
	private void setCurView(int position) {
		if (position < 0 || position >= pics.length) {
			return;
		}

		vp.setCurrentItem(position);
	}

	/**
	 * ��ֻ��ǰ����С���ѡ��
	 */
	private void setCurDot(int positon) {
		if (positon < 0 || positon > pics.length - 1 || currentIndex == positon) {
			return;
		}

		dots[positon].setEnabled(false);
		dots[currentIndex].setEnabled(true);

		currentIndex = positon;
	}

	// ������״̬�ı�ʱ����
	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	// ����ǰҳ�汻����ʱ����
	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

		vp.requestDisallowInterceptTouchEvent(true);
	}

	// ���µ�ҳ�汻ѡ��ʱ����
	@Override
	public void onPageSelected(int arg0) {
		// ���õײ�С��ѡ��״̬
		setCurDot(arg0);
	}

	@Override
	public void onClick(View view) {
		int position = (Integer) view.getTag();
		setCurView(position);
		setCurDot(position);
	}

}
