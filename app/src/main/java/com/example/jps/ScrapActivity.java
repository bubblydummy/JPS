package com.example.jps;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ScrapActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private List<ScrapModel> scrapModelList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrap);





//---------<activity_search와 어뎁터간의 연결>---------
        RecyclerView recyclerView = findViewById(R.id.rv_scrap);
        RVAdapter rvAdapter = new RVAdapter(this, scrapModelList);
        recyclerView.setAdapter(rvAdapter);


        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("JPS");

        databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot dataModel : dataSnapshot.getChildren()) {
                            Log.d("firebase", "Value: " + dataModel.getValue());
                            scrapModelList.add(dataModel.getValue(ScrapModel.class));
                        }
                        rvAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e("firebase", "Error reading data", databaseError.toException());
                    }
                });


        // ------------<홈버튼>
        Button BtH=findViewById(R.id.btn_home);
        BtH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 클릭 이벤트 발생시 실행되는 코드
                // (A,B) -->A액티비티에서 B엑티비티로 전환
                Intent intent = new Intent(ScrapActivity.this, MainActivity.class);
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
                Intent intent = new Intent(ScrapActivity.this, ScrapActivity.class);
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
                Intent intent = new Intent(ScrapActivity.this, ApplyActivity.class);
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
                Intent intent = new Intent(ScrapActivity.this, Log_inActivity.class);
                startActivity(intent);
            }
        });

        // ------------<세팅버튼>
        Button BtSetting = findViewById(R.id.btn_setting);
        BtSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 클릭 이벤트 발생시 실행되는 코드
                // (A,B) -->A액티비티에서 B엑티비티로 전환
                Intent intent = new Intent(ScrapActivity.this, SettingActivity.class);
                startActivity(intent);
            }
        });



    }
}






