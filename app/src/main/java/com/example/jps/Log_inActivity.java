package com.example.jps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Log_inActivity extends AppCompatActivity {


    private FirebaseAuth mFirebaseAuth; //파이어베이스 인증
    private DatabaseReference mDatabaseRef; //실시간 데이터 베이스
    //   로그인 항목들
    private EditText mEtEmail, mEtPwd;
    private Button mBtnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in); //하나의 xml만 연동 가능

//-----------<Firebase 요소들>-----
        mFirebaseAuth=FirebaseAuth.getInstance();
        mDatabaseRef= FirebaseDatabase.getInstance().getReference("JPS");

        mEtEmail=findViewById(R.id.make_email);
        mEtPwd=findViewById(R.id.make_password);


        // ------------<세팅버튼>
        Button BtSetting=findViewById(R.id.btn_setting);
        BtSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 클릭 이벤트 발생시 실행되는 코드
                // (A,B) -->A액티비티에서 B엑티비티로 전환
                Intent intent = new Intent(Log_inActivity.this, SettingActivity.class);
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
                Intent intent = new Intent(Log_inActivity.this, ScrapActivity.class);
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
                Intent intent = new Intent(Log_inActivity.this, ApplyActivity.class);
                startActivity(intent);
            }
        });


        // ------------<홈버튼>
        Button BtHome=findViewById(R.id.btn_home);
        BtHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 클릭 이벤트 발생시 실행되는 코드
                // (A,B) -->A액티비티에서 B엑티비티로 전환
                Intent intent = new Intent(Log_inActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

// ------------<회원가입버튼>
        Button BtSing_up=findViewById(R.id.btn_goto_signup);
        BtSing_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 클릭 이벤트 발생시 실행되는 코드
                // (A,B) -->A액티비티에서 B엑티비티로 전환
                Intent intent = new Intent(Log_inActivity.this, Sign_upActivity.class);
                startActivity(intent);
            }
        });

//------------<Firebase와 연동한 로그인 관련 설정>---------
        Button BtLogin=findViewById(R.id.btn_goto_login);
        BtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //-----로그인 클릭 했을때 처리동작
                String strEmail= mEtEmail.getText().toString();
                String strPwd=mEtPwd.getText().toString();

                mFirebaseAuth.signInWithEmailAndPassword(strEmail,strPwd).addOnCompleteListener(Log_inActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            Toast.makeText(Log_inActivity.this,"로그인에 성공하셨습니다.",Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(Log_inActivity.this, MainActivity.class);
                            intent.putExtra("email", strEmail);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(Log_inActivity.this,"로그인에 실패하셨습니다.",Toast.LENGTH_SHORT).show();

                        }
                    }
                });



            }
        });



    }

    //btn_goto_signup




}