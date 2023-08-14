package com.example.jps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.app.FragmentManager;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class GoogleMapActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private MapFragment mapFragment;

    private EditText etaddr;
    private Button convertbtn;
    private EditText et_result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_map);

        MapView mapView = new MapView(this);

        ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);


        //해시값 받아오기(로그로 확인가능)
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("키해시는 :", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (
                NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        etaddr = findViewById(R.id.et_addr);
        convertbtn = findViewById(R.id.btn_view);

        et_result = findViewById(R.id.et_result);

        convertbtn.setOnClickListener(v -> {
            String addressInput = etaddr.getText().toString();

            if (!addressInput.isEmpty()) {
                List<Double> latandlon = GeoCoding(addressInput);

                //지도이동+ 마커 표시
                moveCameraToLocation(mapView, latandlon.get(0), latandlon.get(1));
                addMarker(mapView, latandlon.get(0), latandlon.get(1), addressInput);

                //길찾기
                showMap(Uri.parse("kakaomap://route?sp=37.537229,127.005515&ep=37.4979502,127.0276368&by=FOOT"));

            } else {
                et_result.setText("정보를 입력하세요");
            }
        });
    }


    //지오 코딩
    private List<Double> GeoCoding(String address) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocationName(address, 10);
            if (addresses != null && !((List<?>) addresses).isEmpty()) {
                Address addr=addresses.get(0);
                double lat = addr.getLatitude();
                double lon=addr.getLongitude();
                List<Double> doubleList = new ArrayList<>();
                doubleList.add(lat);
                doubleList.add(lon);

                //et_result에 표시
                String latLonText = "위도: " + lat + "경도: " + lon;
                et_result.setText(latLonText);

                //리스트 반환
                return doubleList;



            } else {
                et_result.setText("올바른 정보를 입력하시오");

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



    //    카메라 이동
    private void moveCameraToLocation(MapView mapView, double latitude, double longitude) {
        MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(latitude, longitude);
        mapView.setMapCenterPoint(mapPoint, true);
        mapView.setZoomLevel(3, true);

    }

    //마커 추가

    private void addMarker(MapView mapView, double latitude, double longitude, String title) {

        MapPOIItem marker = new MapPOIItem();
        marker.setItemName(title);
        marker.setTag(0);
        marker.setMapPoint(MapPoint.mapPointWithGeoCoord(latitude, longitude));
        marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 마커 아이콘 설정
        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);

        mapView.addPOIItem(marker);
    }

    //길찾기 카카오맵 호출
    public void showMap(Uri geoLocation) {
        Intent intent;

        try {
            Toast.makeText(getApplicationContext(), "카카오맵으로 길찾기를 시도합니다.", Toast.LENGTH_SHORT).show();
            intent = new Intent(Intent.ACTION_VIEW, geoLocation);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "길찾기에는 카카오맵이 필요합니다. 다운받아주시길 바랍니다.", Toast.LENGTH_SHORT).show();
            intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://play.google.com/store/apps/details?id=net.daum.android.map&hl=ko"));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }





}



