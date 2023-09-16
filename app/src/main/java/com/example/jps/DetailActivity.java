package com.example.jps;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.jps.SearchActivity.JobData;

public class DetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // 이전 액티비티로부터 데이터 가져오기
        JobData jobdata = (JobData) getIntent().getSerializableExtra("jobData");

        // 기업 이름
        TextView nameTextView = findViewById(R.id.text_name);
        nameTextView.setText(jobdata.getData()[0]);

        // 직종, 계약구분, 임금형태+임금, 입사형태
        TextView TextView_1 = findViewById(R.id.textView_1);
        TextView_1.setText("직종 : " + jobdata.getData()[1]
                + "\n" + "계약 구분 : " + jobdata.getData()[2]
                + "\n" + "임금 : " + jobdata.getData()[10] + " " + jobdata.getData()[11]);

        /*
        구인신청일자,사업장명,모집직종,고용형태,임금형태,임금,입사형태,
        요구경력,요구학력,전공계열,요구자격증,사업장 주소,기업형태,담당기관,
        등록일,연락처,인증구분,등급,인증발급일,인증만료일,시작일자,종료일자,시군구명
         */


        // 주소
        TextView TextView_addr = findViewById(R.id.textView_addr);
        TextView_addr.setText("주소 : " + jobdata.getData()[3]);

        // 기업 형태
        TextView TextView_type = findViewById(R.id.textView_type);
        TextView_type.setText("기업 형태 : " + jobdata.getData()[12]);

        // 연락처
        TextView TextView_phone = findViewById(R.id.textView_phone);
        TextView_phone.setText("연락처 : " + jobdata.getData()[13]);


        // 요구 경력
        TextView TextView_carrer = findViewById(R.id.textView_carrer);
        TextView_carrer.setText("요구 경력 : " + jobdata.getData()[6]);

        // 학력
        TextView TextView_edu = findViewById(R.id.textView_edu);
        TextView_edu.setText("요구 학력 : " + jobdata.getData()[7]);

        // 담당 기관
        TextView TextView_dam = findViewById(R.id.textView_damdang);
        TextView_dam.setText("담당 기관 : " + jobdata.getData()[14]);

        // 인증
        TextView TextView_in = findViewById(R.id.textView_in);
        TextView_in.setText("인증 구분 : " + jobdata.getData()[8]);

        // 등급
        TextView TextView_rate = findViewById(R.id.textView_rate);
        TextView_rate.setText("등급 : " + jobdata.getData()[15]);

        // 시작-종료
        TextView TextView_startend = findViewById(R.id.textView_startend);
        TextView_startend.setText("시작~종료 일자 : " + jobdata.getData()[4] + " ~ " + jobdata.getData()[5]);


        //-----------<구글맵 버튼> btn_google_map

        Button Bt_kakao = findViewById(R.id.btn_kakaomap);
        Bt_kakao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 클릭 이벤트 발생시 실행되는 코드
                // (A,B) -->A액티비티에서 B엑티비티로 전환
                Intent intent = new Intent(DetailActivity.this, GoogleMapActivity.class);

                intent.putExtra("address", jobdata.getData()[3]);
                intent.putExtra("company",jobdata.getData()[0]);
                startActivity(intent);
            }
        });

    }
}
