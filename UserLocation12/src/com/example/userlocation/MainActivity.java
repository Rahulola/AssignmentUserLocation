package com.example.userlocation;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

public class MainActivity extends FragmentActivity {
	GoogleMap map;
	public  double lat,log;
	public  static String city; 
	static boolean check = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		map = ((SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map)).getMap();
		if (map == null) {
			Toast.makeText(this, "Google Maps not available", Toast.LENGTH_LONG)
					.show();
		}

/*
 * Button onClickListener		
 */
		
	Button buttonOne = (Button) findViewById(R.id.cityInfo);
	buttonOne.setOnClickListener(new Button.OnClickListener() {
	    public void onClick(View v) {
	    		    	
	    if (check==false)
	    { 	
	    	Toast.makeText(getBaseContext(), "Location is not selected", Toast.LENGTH_SHORT).show(); 
	    }
/*
 *  Getting Address from lat, log
 */	    	
	    else if (check == true){
	    	Geocoder geocoder;
	    	List<Address> addresses;
	    	geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

	    	try {
	    		addresses = geocoder.getFromLocation(lat,log, 1);
	    	//	String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
	    	  city = addresses.get(0).getLocality();
	    	  
	    	//	String state = addresses.get(0).getAdminArea();
	    	//	String country = addresses.get(0).getCountryName();
	    	//	String postalCode = addresses.get(0).getPostalCode();
	    	//	String knownName = addresses.get(0).getFeatureName(); 
	    	
	    	} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 	
	    	
	    Intent i  = new Intent(getApplicationContext(), Webview_Activity.class);
	    startActivity(i);	
	    		}
	       }
		});	
	
	}
	
/* 
 * Creating menu bar 
 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is
		// present.
		getMenuInflater().inflate(R.menu.main, menu);
		map.setMyLocationEnabled(true);

		map.setOnMyLocationButtonClickListener(new OnMyLocationButtonClickListener() {
			@Override
			public boolean onMyLocationButtonClick() {
				LocationManager lm = (LocationManager) getApplicationContext()
						.getSystemService(Context.LOCATION_SERVICE);
				boolean gps_enabled = false;
				boolean network_enabled = false;

				try {
					gps_enabled = lm
							.isProviderEnabled(LocationManager.GPS_PROVIDER);
				} catch (Exception ex) {
				}

				try {
					network_enabled = lm
							.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
				} catch (Exception ex) {
				}
				
				if (gps_enabled && network_enabled){
					
					map.setMyLocationEnabled(true);
					Location myLocation = map.getMyLocation();
					lat = myLocation.getLatitude();
					log = myLocation.getLongitude();
					LatLng myLatLng = new LatLng(lat,log);
					
					CameraPosition myPosition = new CameraPosition.Builder()
							.target(myLatLng).zoom(15).bearing(0).tilt(0).build();
					map.animateCamera(CameraUpdateFactory.newCameraPosition(myPosition));
					check = true;
				}
								
				else if (!gps_enabled && !network_enabled) {
					// notify user
					AlertDialog.Builder dialog = new AlertDialog.Builder(
							MainActivity.this);
					dialog.setMessage("Turn on your GPS?");
					dialog.setPositiveButton("Yes",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(
										DialogInterface paramDialogInterface,
										int paramInt) {
									// TODO Auto-generated method stub
									Intent myIntent = new Intent(
											Settings.ACTION_LOCATION_SOURCE_SETTINGS);
									startActivity(myIntent);
									// get gps
								}
							});
					dialog.setNegativeButton("No",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(
										DialogInterface paramDialogInterface,
										int paramInt) {
									// TODO Auto-generated method stub

								}
							});
					dialog.show();
				}
				return true;
			}
		});
		return true;
	
	}
	
	
/*
 * for the menu bar list  
 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {

		case R.id.menu_zoomin:
			map.animateCamera(CameraUpdateFactory.zoomIn());
			break;
		case R.id.menu_zoomout:
			map.animateCamera(CameraUpdateFactory.zoomOut());
			break;

		}
		return true;

	}
	
	
}
