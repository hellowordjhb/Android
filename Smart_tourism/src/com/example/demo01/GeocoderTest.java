package com.example.demo01;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class GeocoderTest extends Activity implements OnMapReadyCallback {
	Button parseBn, reverseBn, navroute;
	EditText etLng, etLat, etAddress, etResult;
	GoogleMap mGoogleMap;
	private CameraPosition cameraPosition;
	private MarkerOptions markerOpt;

	protected double initlat;
	protected double initlng;
	protected double directionlat;
	protected double directionlng;
	LocationManager locManager;
	private LatLng locLatLng;
	final static String dangqian = "��ǰλ��";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.map_main);
		// ��ȡ�����еĿ��ӻ����
		navroute = (Button) findViewById(R.id.route);
		reverseBn = (Button) findViewById(R.id.reverse);
		etLng = (EditText) findViewById(R.id.lng);
		etLat = (EditText) findViewById(R.id.lat);
		Intent intent_loc = getIntent();
		String address = intent_loc.getStringExtra("location");
		// etAddress.setText(address);

		// parseBn.setOnClickListener(this);
		// reverseBn.setOnClickListener(this);

		MapFragment mapFragment = (MapFragment) getFragmentManager()
				.findFragmentById(R.id.google_map);
		mGoogleMap = mapFragment.getMap();
		mGoogleMap.setMyLocationEnabled(true);
		// getMapAsync()�Զ���ʼ����ͼϵͳ����ͼ
		mapFragment.getMapAsync(this);
		try {
		// ����LocationManager����
		locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		// ��GPS��ȡ����Ķ�λ��Ϣ
		Location location = locManager
				.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		if (location == null) {
			Location location1 = locManager
					.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
			initlat = location1.getLatitude();
			initlng = location1.getLongitude();
		} else {
			initlat = location.getLatitude();
			initlng = location.getLongitude();
		}

		} catch (Exception e) {
			Toast toast = Toast.makeText(getApplicationContext(), "������������ԣ�",
			Toast.LENGTH_SHORT);
			toast.show();
		}
		//ʵʱ��ǰλ�ø���
		locManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 3000,
				8, new LocationListener() {

					@Override
					public void onLocationChanged(Location location) {
						// TODO Auto-generated method stub
						if (location != null) {
							initlat = location.getLatitude();
							initlng = location.getLongitude();
							locLatLng = new LatLng(initlat, initlng);
							// mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(locLatLng));
							// mGoogleMap.moveCamera(CameraUpdateFactory.zoomTo(15));
						}
					}

					@Override
					public void onStatusChanged(String provider, int status,
							Bundle extras) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onProviderEnabled(String provider) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onProviderDisabled(String provider) {
						// TODO Auto-generated method stub

					}
				});

		try {

			double[] results = ConvertUtil.getLocationInfo(address);
			directionlat = results[1];
			directionlng = results[0];
			updateToNewLocation(results);

		} catch (Exception e) {
			// TODO: handle exception
		}
		// Ϊ��ǰλ�õ�Ŀ�ĵذ�ť�󶨼�����
		navroute.setOnClickListener(new OnClickListener() {
			private double startlat;
			private double startlng;
			private double endlat;
			private double endlng;

			@Override
			public void onClick(View v) {
				// LatLng start = new LatLng(50.10592, 8.67726);
				// LatLng end = new LatLng(50.10935, 8.68273);
				startlat = initlat;
				startlng = initlng;
				LatLng end = new LatLng(directionlat, directionlng);
				endlat = end.latitude;
				endlng = end.longitude;
				Intent i = new Intent(
						Intent.ACTION_VIEW,
						Uri.parse("http://ditu.google.cn/maps?f=d&source=s_d&saddr="
								+ startlat
								+ ","
								+ startlng
								+ "&daddr="
								+ endlat + "," + endlng + "&hl=zh"));
				// ���ǿ��ʹ��googlemap��ͼ�ͻ��˴򿪣��ͼ���������
				i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
						& Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
				i.setClassName("com.google.android.apps.maps",
						"com.google.android.maps.MapsActivity");
				startActivity(i);
			}

		});
		// Ϊ�Զ���·�߰�ť�󶨼�����
		reverseBn.setOnClickListener(new OnClickListener() {
			double startlat;
			double startlng;
			double endlat;
			double endlng;
			@Override
			public void onClick(View v) {
				String start = etLng.getText().toString().trim();
				String des = etLat.getText().toString().trim();
				// ����ո�
				start = start.replace(" ", "");
				des = des.replace(" ", "");
				try {
					if (start.equals(dangqian)) {
						startlat = initlat;
						startlng = initlng;
					} else {
						double[] origin = ConvertUtil.getLocationInfo(start);
						startlat = origin[1];
						startlng = origin[0];
					}
					double[] destination = ConvertUtil.getLocationInfo(des);
					endlat = destination[1];
					endlng = destination[0];

					Intent i = new Intent(
							Intent.ACTION_VIEW,
							Uri.parse("http://ditu.google.cn/maps?f=d&source=s_d&saddr="
									+ startlat
									+ ","
									+ startlng
									+ "&daddr="
									+ endlat + "," + endlng + "&hl=zh"));
					// ���ǿ��ʹ��googlemap��ͼ�ͻ��˴򿪣��ͼ���������
					i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
							& Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
					i.setClassName("com.google.android.apps.maps",
							"com.google.android.maps.MapsActivity");
					startActivity(i);
				} catch (Exception e) {
				}
			}
		});
	}

	private void updateToNewLocation(double[] results) {
		mGoogleMap.clear();
		markerOpt = new MarkerOptions();
		markerOpt.position(new LatLng(results[1], results[0]));
		markerOpt.draggable(false);
		markerOpt.visible(true);
		markerOpt.anchor(0.5f, 0.5f);// ��ΪͼƬ����
		markerOpt.icon(BitmapDescriptorFactory
				.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
		mGoogleMap.addMarker(markerOpt);
		// ����Ӱ���ƶ���ָ���ĵ���λ��
		cameraPosition = new CameraPosition.Builder()
				.target(new LatLng(results[1], results[0])) // Sets the center
															// of the map to
															// ZINTUN
				.zoom(15) // ���ű���
				.bearing(0) // Sets the orientation of the camera to east
				.build(); // Creates a CameraPosition from the builder
		mGoogleMap.animateCamera(CameraUpdateFactory
				.newCameraPosition(cameraPosition));
	}

	@Override
	public void onMapReady(GoogleMap googleMap) {
		// TODO Auto-generated method stub

	}

}