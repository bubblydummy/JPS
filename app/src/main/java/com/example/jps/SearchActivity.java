package com.example.jps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.GlobalScope;

public class SearchActivity extends AppCompatActivity {

//    //----------<Firebase , Room db관련 변수 초기화>
    private FirebaseAuth mFirebaseAuth;
    private UserDAO mUserDao;
    private TextView resultTextView;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);



//-------------------<비동기 처리를 위해 추가한부분 제대로 동작하지 않음>---------
//        resultTextView = findViewById(R.id.resultTextView);
//        ViewModel myViewModel = new ViewModelProvider(this).get(MyViewModel.class);
//
//        // LiveData를 관찰하고, 데이터가 변경되면 UI에 자동으로 반영됩니다.
//        ((MyViewModel) myViewModel).getDbResultLiveData().observe(this, result -> {
//            // DB 작업 결과를 UI에 반영합니다.
//            resultTextView.setText(result);
//        });
//
//        // 백그라운드에서 DB 작업 실행
//        ((MyViewModel) myViewModel).performDbOperationInBackground();


//-------------------<데이터 베이스 가져오는 부분 제대로 동작하지 않음>---------
//        UserDatabase database = Room.databaseBuilder(this, UserDatabase.class, "mycsv_db")
//                .allowMainThreadQueries()
//                .createFromAsset("database/mycsv.db")
//                .build();
//
//
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }




//--------<전송된 데이터 받아서 추출>

        Intent intent=getIntent();
        if(intent!=null){
            String data_job= intent.getStringExtra("data_job");
            String data_region= intent.getStringExtra("data_region");
            String data_job_type= intent.getStringExtra("data_job_type");

            Toast.makeText(SearchActivity.this,data_job + data_region + data_job_type,Toast.LENGTH_SHORT).show();


        }





//------------<로그아웃버튼>

        mFirebaseAuth= FirebaseAuth.getInstance();// Firebase 관련 초기화

        Button btn_logout=findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //로그아웃 버튼 클릭시 로그아웃
                mFirebaseAuth.signOut();
                //mFirebaseAuth.getCurrentUser().delete();//탈퇴 처리
            }

        });
//---------------<홈버튼>
        Button BtH=findViewById(R.id.btn_home);
        BtH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 클릭 이벤트 발생시 실행되는 코드
                // (A,B) -->A액티비티에서 B엑티비티로 전환
                Intent intent = new Intent(SearchActivity.this, MainActivity.class);
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
                Intent intent = new Intent(SearchActivity.this, SettingActivity.class);
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

                Intent intent = new Intent(SearchActivity.this, ScrapActivity.class);
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
                Intent intent = new Intent(SearchActivity.this, ApplyActivity.class);
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
                Intent intent = new Intent(SearchActivity.this, Log_inActivity.class);
                startActivity(intent);
            }
        });




    }
}