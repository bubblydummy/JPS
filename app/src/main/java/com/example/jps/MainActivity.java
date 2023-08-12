package com.example.jps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //----------<Firebase , Room db관련 변수 초기화>
    private FirebaseAuth mFirebaseAuth;
    // private UserDAO mUserDao;


    //private UserDAO mUserDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);






// ------------<로그아웃버튼>

        mFirebaseAuth=FirebaseAuth.getInstance();// Firebase 관련 초기화

        Button btn_logout=findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //로그아웃 버튼 클릭시 로그아웃
                mFirebaseAuth.signOut();
                //mFirebaseAuth.getCurrentUser().delete();//탈퇴 처리
            }

        });



//-----------<구글맵 버튼> btn_google_map

        Button Bt_google=findViewById(R.id.btn_google_map);
        Bt_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 클릭 이벤트 발생시 실행되는 코드
                // (A,B) -->A액티비티에서 B엑티비티로 전환
                Intent intent = new Intent(MainActivity.this, GoogleMapActivity.class);
                startActivity(intent);
            }
        });




// ------------<세팅버튼>
        Button BtSetting=findViewById(R.id.btn_setting);
        BtSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 클릭 이벤트 발생시 실행되는 코드
                // (A,B) -->A액티비티에서 B엑티비티로 전환
                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent);
            }
        });





// ------------<스크랩버튼>
        Button BtScrap=findViewById(R.id.btn_scrap);
        BtScrap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 클릭 이벤트 발생시 실행되는 코드
                // (A,B) -->A액티비티에서 B엑티비티로 전환

                Intent intent = new Intent(MainActivity.this, ScrapActivity.class);
                startActivity(intent);
            }
        });

// ------------<지원서버튼>
        Button BtApply=findViewById(R.id.btn_apply);
        BtApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 클릭 이벤트 발생시 실행되는 코드
                // (A,B) -->A액티비티에서 B엑티비티로 전환
                Intent intent = new Intent(MainActivity.this, ApplyActivity.class);
                startActivity(intent);
            }
        });

// ------------<로그인버튼>
        Button BtLogin=findViewById(R.id.btn_log_in);
        BtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 클릭 이벤트 발생시 실행되는 코드
                // (A,B) -->A액티비티에서 B엑티비티로 전환
                Intent intent = new Intent(MainActivity.this, Log_inActivity.class);
                startActivity(intent);
            }
        });

// ------------<검색 버튼>
        Button Btsearch=findViewById(R.id.btn_search);
        TextView et_job=findViewById(R.id.et_job);
        TextView et_region=findViewById(R.id.et_region);
        TextView et_job_type=findViewById(R.id.et_job_type);

        Btsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data_job=et_job.getText().toString();
                String data_region=et_region.getText().toString();
                String data_job_type=et_job_type.getText().toString();
                Intent intent=new Intent(getApplicationContext(),SearchActivity.class);
                intent.putExtra("data_job",data_job);
                intent.putExtra("data_region",data_region);
                intent.putExtra("data_job_type",data_job_type);

                //검색 액티비티로 이동
                startActivity(intent);

            }
        });







        // -----------------<Roomdb의 동작이 어떻게 이루어지는지 알기위한것>---------------------------------

//        UserDatabase database = Room.databaseBuilder(getApplicationContext(),UserDatabase.class,"User_db")
//                .fallbackToDestructiveMigration() //스키마(db) 버전 변경가능
//                .allowMainThreadQueries() //Main thread에서 DB에 입출력을 가능하게에 함
//                .build();
//
//        mUserDao=database.userDao();  //인터페이스 mUserDao 객체 할당으로 인해 사용가능
//
//        User user =new User();
        //-------------<데이터 삽입>  -->실행후, App inspection을 눌러 데이터베이스 확인가능
//
//        user.setEmail("Kim@naver.com");
//        user.setPassword("1111");
//
//        mUserDao.setInsertUser(user);
//
//        //-------------<데이터 조회>
//        List<User> userList=mUserDao.getUserAll();
//        for (int i = 0; i < userList.size(); i++) {
//
//            //ctrl+atl+L -->자동 정렬
//            Log.d("Test", userList.get(i).getEmail() + "\n"
//                    + userList.get(i).getPassword() + "\n");
//        }
//
//        //-------------<데이터 수정>
//        User user2 =new User();
//        user2.setSerial_num(0);
//        user2.setEmail("Lee@naver.com");
//        user2.setPassword("1111");
//        mUserDao.setUpdateUser(user2);
//
//
//        //-------------<데이터 삭제>
//        User user3=new User();
//        user3.setSerial_num(0);
//        mUserDao.setDeletUser(user3);

    }





}