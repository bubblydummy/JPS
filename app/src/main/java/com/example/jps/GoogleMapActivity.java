package com.example.jps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import net.daum.mf.map.api.MapCircle;
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

    //도착지 정보
    private EditText etaddr;
    private Button convertbtn;
    private EditText et_result;

    //내위치 정보
    private Button btn_mypos;
    private TextView txt_result;


    //길찾기 정보
    private Button btn_find;


    //도착지, 내위치 정보 초기화
    private List<Double> destination=new ArrayList<>();
    private List<Double> my_position = new ArrayList<>();


    //원
    private MapCircle circle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_map);

        MapView mapView = new MapView(this);

        ViewGroup mapViewContainer = (ViewGroup) findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);






        //------------------<해시값 받아오기(로그로 확인가능)
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

        //-----------------<도착지 정보를 지오코딩(주소를 위도와 경도로 변환)
        convertbtn.setOnClickListener(v -> {
            String addressInput = etaddr.getText().toString();

            if (!addressInput.isEmpty()) {
                destination = GeoCoding(addressInput);

                //지도이동+ 마커 표시
                moveCameraToLocation(mapView, destination.get(0), destination.get(1));
                addMarker(mapView, destination.get(0), destination.get(1), addressInput);

                // 원의 반경과 중심 좌표 설정
                int circleRadius = 1000; // 원의 반경 (미터)
                MapPoint centerMapPoint = MapPoint.mapPointWithGeoCoord(destination.get(0), destination.get(1)); // 중심 좌표
                int circleStrokeColor = ContextCompat.getColor(this, R.color.circleStrokeColor);

                // 원 생성 및 지도에 추가
                circle = new MapCircle(centerMapPoint, circleRadius,circleStrokeColor,circleStrokeColor);
                mapView.addCircle(circle);




            } else {
                et_result.setText("정보를 입력하세요");
            }
        });

        btn_mypos = (Button)findViewById(R.id.btn_mypos);
        txt_result = (TextView)findViewById(R.id.txt_result);

        final LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        //-------------------------<내위치 정보받아오기
        btn_mypos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( Build.VERSION.SDK_INT >= 23 &&
                        ContextCompat.checkSelfPermission( getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {
                    ActivityCompat.requestPermissions( GoogleMapActivity.this, new String[] {  android.Manifest.permission.ACCESS_FINE_LOCATION  },
                            0 );
                }
                else{
                    Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    double longitude = location.getLongitude();
                    double latitude = location.getLatitude();
                    double altitude = location.getAltitude();



                    // 더블형 데이터 추가
                    my_position.add(latitude);
                    my_position.add(longitude);


                    txt_result.setText(
                            "위도 : " + latitude + "\n" +
                            "경도 : " + longitude + "\n" +
                            "고도  : " + altitude);

                    lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                            1000,
                            1,
                            gpsLocationListener);
                    lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                            1000,
                            1,
                            gpsLocationListener);
                }
            }
        });



        //내위치와 도착지 정보가 찾아지면 길찾기(도보용)
        btn_find=(Button)findViewById(R.id.btn_find);
        btn_find.setOnClickListener(v -> {

            if (my_position!=null && destination!=null) {
                //길찾기

                String uri = "kakaomap://route?sp=" + my_position.get(0) + "," + my_position.get(1) +
                    "&ep=" + destination.get(0) + "," + destination.get(1) +
                    "&by=FOOT";
                Toast.makeText(getApplicationContext(), uri, Toast.LENGTH_SHORT).show();
                showMap(Uri.parse(uri));


            } else {
                Toast.makeText(getApplicationContext(), "위치 정보를 받아오지 못하였습니다.", Toast.LENGTH_SHORT).show();
            }
        });









    }

    //-------<내 위치 찾기 함수>
    final LocationListener gpsLocationListener = new LocationListener() {
        public void onLocationChanged(Location location) {

            double longitude = location.getLongitude();
            double latitude = location.getLatitude();
            double altitude = location.getAltitude();

            txt_result.setText(
                    "위도 : " + latitude + "\n" +
                    "경도 : " + longitude + "\n" +
                    "고도  : " + altitude);

        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onProviderDisabled(String provider) {
        }
    };



    //--------------<지오 코딩 함수>
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



    //--------------<카메라 이동 함수>
    private void moveCameraToLocation(MapView mapView, double latitude, double longitude) {
        MapPoint mapPoint = MapPoint.mapPointWithGeoCoord(latitude, longitude);
        mapView.setMapCenterPoint(mapPoint, true);
        mapView.setZoomLevel(3, true);

    }

    //--------------<마커 추가 함수>

    private void addMarker(MapView mapView, double latitude, double longitude, String title) {

        MapPOIItem marker = new MapPOIItem();
        marker.setItemName(title);
        marker.setTag(0);
        marker.setMapPoint(MapPoint.mapPointWithGeoCoord(latitude, longitude));
        marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 마커 아이콘 설정
        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);

        mapView.addPOIItem(marker);
    }

    //--------------<길찾기 카카오맵 호출 함수>
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



