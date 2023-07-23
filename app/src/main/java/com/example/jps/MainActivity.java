package com.example.jps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private UserDAO mUserDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        // -----------------<User database 활용 함수 선언>--------------------------- //Roomdb의 동작이 어떻게 이루어지는지 알기위한것 //실제 구현은 회원가입 Activity 에서 이루어질것

        UserDatabase database = Room.databaseBuilder(getApplicationContext(),UserDatabase.class,"User_db")
                .fallbackToDestructiveMigration() //스키마(db) 버전 변경가능
                .allowMainThreadQueries() //Main thread에서 DB에 입출력을 가능하게에 함
                .build();

        mUserDao=database.userDao();  //인터페이스 mUserDao 객체 할당으로 인해 사용가능

        User user =new User();
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