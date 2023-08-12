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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Sign_upActivity extends AppCompatActivity {

//-----------<Firebase 연동을 위한 요소들 정의>
    private FirebaseAuth mFirebaseAuth; //파이어베이스 인증
    private DatabaseReference mDatabaseRef; //실시간 데이터 베이스
//    회원가입 항목들
    private EditText mEtEmail, mEtPwd;
    private Button mBtnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up);

        mFirebaseAuth=FirebaseAuth.getInstance();
        mDatabaseRef= FirebaseDatabase.getInstance().getReference("JPS");

        mEtEmail=findViewById(R.id.make_email);
        mEtPwd=findViewById(R.id.make_password);
        mBtnRegister=findViewById(R.id.btn_goto_login);

        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                String strEmail= mEtEmail.getText().toString();
                String strPwd=mEtPwd.getText().toString();

                //Firebase Auth 진행
                mFirebaseAuth.createUserWithEmailAndPassword(strEmail, strPwd).addOnCompleteListener(Sign_upActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) //회원가입 성공 여부가 task에 담겨 있음
                    {
                        //-----------<성공 여부 나눔>
                        if (task.isSuccessful())
                        {
                            FirebaseUser firebaseUser=mFirebaseAuth.getCurrentUser(); //회원가입이 성공한 유저를 가져온다.
                            UserAccount account=new UserAccount();
                            account.setIdToken(firebaseUser.getUid());// Uid <---고유 아이디
                            account.setEmailId(firebaseUser.getEmail());//로그인이 된 정확한 유저 이메일을 가져와야하기 때문이다.
                            account.setPassword(strPwd);

                            //setValue(account) : 데이터베이스 account를 삽입
                            mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).setValue(account);

                            Toast.makeText(Sign_upActivity.this,"회원가입에 성공하셨습니다.",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Sign_upActivity.this, Log_inActivity.class);
                            startActivity(intent);

                        }
                        else{
                            Toast.makeText(Sign_upActivity.this,"회원가입에 실패하셨습니다.",Toast.LENGTH_SHORT).show();
                        }

                    }
                });


            }
        });


    }
}