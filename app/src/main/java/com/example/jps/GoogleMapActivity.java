package com.example.jps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.database.Cursor;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
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
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import net.daum.mf.map.api.CalloutBalloonAdapter;
import net.daum.mf.map.api.MapCircle;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.io.IOException;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class GoogleMapActivity extends AppCompatActivity  {

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



        //------------<csv파일 읽기-비동기>-----
        new GoogleMapActivity.ReadCSVFileTask().execute("boji_daegu.csv");




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



        Intent intent = getIntent();
        String message = intent.getStringExtra("address");
        etaddr.setText(message);


        //-----------------<도착지 정보를 지오코딩(주소를 위도와 경도로 변환)
        convertbtn.setOnClickListener(v -> {
            String addressInput = etaddr.getText().toString();

            String companyInput= intent.getStringExtra("company");

            if (!addressInput.isEmpty()) {
                destination = GeoCoding(addressInput);

                //지도이동+ 마커 표시
                moveCameraToLocation(mapView, destination.get(0), destination.get(1));
                addMarker(mapView, destination.get(0), destination.get(1),companyInput, addressInput);




                // 원의 반경과 중심 좌표 설정
                int circleRadius = 1000; // 원의 반경 (미터)
                MapPoint centerMapPoint = MapPoint.mapPointWithGeoCoord(destination.get(0), destination.get(1)); // 중심 좌표
                int circleStrokeColor = ContextCompat.getColor(this, R.color.circleStrokeColor);

                // 원 생성 및 지도에 추가
                circle = new MapCircle(centerMapPoint, circleRadius,circleStrokeColor,circleStrokeColor);
                mapView.addCircle(circle);

                double kmToDegrees = (1.0 / 111.0)/ Math.sqrt(2);


                // 원의 반경 내에 있는 데이터베이스 정보를 가져와 출력
                double minLat = destination.get(0) - kmToDegrees;
                double maxLat = destination.get(0) + kmToDegrees;
                double minLng = destination.get(1) -kmToDegrees / Math.cos(Math.toRadians(destination.get(0)));
                double maxLng = destination.get(1) + kmToDegrees / Math.cos(Math.toRadians(destination.get(0)));




                Log.d("Circle Range", "Min Latitude: " + minLat);
                Log.d("Circle Range", "Max Latitude: " + maxLat);
                Log.d("Circle Range", "Min Longitude: " + minLng);
                Log.d("Circle Range", "Max Longitude: " + maxLng);


                // BojiDao 인스턴스 생성
                BojiDao bojiDao = BojiDatabase.getInstance(getApplicationContext()).bojiDao();

                // 원의 반경 내에 있는 데이터베이스 정보를 쿼리//비동기처리 시켜야함
                BojiAsyncTask bojiAsyncTask = new BojiAsyncTask(bojiDao, minLat, maxLat, minLng, maxLng,mapView);
                bojiAsyncTask.execute();


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





        //다중 경유지 길찾기 시스템 개발 하여 카카오맵과 연동하기


        //다중 출발지 길찾기

        //다중 목적지 길찾기


        //내위치와 도착지 정보가 찾아지면 길찾기(대중교통용)
        btn_find=(Button)findViewById(R.id.btn_find);
        btn_find.setOnClickListener(v -> {

            if (my_position!=null && destination!=null) {
                //길찾기

                String uri = "kakaomap://route?sp=" + my_position.get(0) + "," + my_position.get(1) +
                    "&ep=" + destination.get(0) + "," + destination.get(1) +
                    "&by=TRANSIT";
                Toast.makeText(getApplicationContext(), uri, Toast.LENGTH_SHORT).show();
                showMap(Uri.parse(uri));


            } else {
                Toast.makeText(getApplicationContext(), "위치 정보를 받아오지 못하였습니다.", Toast.LENGTH_SHORT).show();
            }
        });









    }



    //비동기적으로 처리하는 DB 조회 함수

    private class BojiAsyncTask extends AsyncTask<Void, Void, List<Boji>> {

        private BojiDao bojiDao;
        private double minLat;
        private double maxLat;
        private double minLng;
        private double maxLng;

        private List<Boji> dataListWithinCircle;

        private MapView mapView;


        public BojiAsyncTask(BojiDao bojiDao, double minLat, double maxLat, double minLng, double maxLng, MapView mapView) {
            this.bojiDao = bojiDao;
            this.minLat = minLat;
            this.maxLat = maxLat;
            this.minLng = minLng;
            this.maxLng = maxLng;
            this.mapView = mapView;

        }

        @Override
        protected List<Boji> doInBackground(Void... voids) {


            dataListWithinCircle = bojiDao.getBojiWithinCircle(minLat, maxLat, minLng, maxLng);

            return dataListWithinCircle;


        }

        @Override
        protected void onPostExecute(List<Boji> result) {
            if (dataListWithinCircle != null) {

                for (Boji boji : dataListWithinCircle) {
                    Log.d("haha", "Facility Name: " + boji.facility_name + ", Category: " + boji.category);
                    // Add markers to the map based on the boji data
                    double latitude = Double.parseDouble(boji.LATITUDE);
                    double longitude = Double.parseDouble(boji.LONGITUDE);
                    String title = boji.facility_name;
                    String snippet = boji.category;

                    addMarker(mapView, latitude, longitude, title, snippet);
                }
            }
        }
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

    private void addMarker(MapView mapView, double latitude, double longitude, String title,String snippet) {


        MapPOIItem marker = new MapPOIItem();
        marker.setItemName(title);
        marker.setMarkerType(MapPOIItem.MarkerType.CustomImage);
        marker.setTag(0);
        marker.setMapPoint(MapPoint.mapPointWithGeoCoord(latitude, longitude));
        marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 마커 아이콘 설정
        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);


        mapView.addPOIItem(marker);

        // 사용자 정의 정보 창 설정

        mapView.setCalloutBalloonAdapter(new CustomBalloonAdapter(getLayoutInflater()));  // 커스텀 말풍선 등록


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


    private class ReadCSVFileTask extends AsyncTask<String, Void, Void> {
        List<String[]> allData = new ArrayList<>();
        @Override
        protected Void doInBackground(String... filenames) {

            try {
                InputStreamReader inputStreamReader = new InputStreamReader(getAssets().open(filenames[0]), "UTF-8");
                CSVReader csvReader = new CSVReader(inputStreamReader);

                // 헤더 행 스킵
                csvReader.readNext();

                String[] nextLine;
                while ((nextLine = csvReader.readNext()) != null) {
                    allData.add(nextLine);
                    Boji boji=new Boji();
                    boji.facility_name=nextLine[0];
                    boji.category=nextLine[1];
                    boji.location=nextLine[2];
                    boji.LATITUDE=nextLine[3];
                    boji.LONGITUDE=nextLine[4];


                    // Room 데이터베이스에 데이터 삽입
                    BojiDatabase.getInstance(getApplicationContext()).bojiDao().insertBoji(boji);

                }
                csvReader.close();
            } catch (IOException | CsvValidationException e) {
                e.printStackTrace();
            }

            return null;
        }

        protected void onPostExecute(Void aVoid) {

            for (String[] rowData : allData) {
                StringBuilder rowString = new StringBuilder();
                for (String cell : rowData) {
                    rowString.append(cell).append(", ");
                }
                Log.d("CSV Data", rowString.toString());
            }

        }
    }

    //room db 조회 비동기처리






    // 커스텀 말풍선 클래스
    class CustomBalloonAdapter implements CalloutBalloonAdapter {
        private final View mCalloutBalloon;
        private final TextView name;
        private final TextView address;

        public CustomBalloonAdapter(LayoutInflater inflater) {
            mCalloutBalloon = inflater.inflate(R.layout.custom_info, null);
            name = mCalloutBalloon.findViewById(R.id.txt_title);
            address = mCalloutBalloon.findViewById(R.id.txt_addr);
        }

        @Override
        public View getCalloutBalloon(MapPOIItem poiItem) {
            // 마커 클릭 시 나오는 말풍선
            name.setText(poiItem.getItemName());   // 해당 마커의 정보 이용 가능
            Intent intent = getIntent();
            String message = intent.getStringExtra("address");
            address.setText(message);
            return mCalloutBalloon;
        }

        @Override
        public View getPressedCalloutBalloon(MapPOIItem poiItem) {
            // 말풍선 클릭 시
            return mCalloutBalloon;
        }
    }










}



