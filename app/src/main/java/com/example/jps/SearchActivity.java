package com.example.jps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.GlobalScope;

public class SearchActivity extends AppCompatActivity {

//    //----------<Firebase , Room db관련 변수 초기화>

    private UserDAO mUserDao;
    private TextView resultTextView;


    //---------------<리사이클러 뷰 초기화
    private RecyclerView recyclerView;
    private JobAdapter jobAdapter;

    //ViewHodler에서 현재 컨텍스트를 받아오기 위한 변수
    public static Context mContext;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mContext=this;


//---------------<리사이클러뷰 연결과 csv파일 읽기>
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Start AsyncTask to read CSV file
        new ReadCSVFileTask().execute("장애인구직정보_전처리.csv");



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


    private class ReadCSVFileTask extends AsyncTask<String, Void, List<JobData>> {

        @Override
        protected List<JobData> doInBackground(String... filenames) {
            List<JobData> jobDataList = new ArrayList<>();
            try {
                InputStreamReader inputStreamReader = new InputStreamReader(getAssets().open(filenames[0]), "UTF-8");
                CSVReader csvReader = new CSVReader(inputStreamReader);

                String[] nextLine;

                // 첫 번째 행(헤더) 읽고 무시
                try {
                    csvReader.readNext();
                } catch (CsvValidationException e) {
                    e.printStackTrace();
                }

                // 이후 행들 읽기
                try {
                    while ((nextLine = csvReader.readNext()) != null) {
                        JobData jobData = new JobData(nextLine);
                        jobDataList.add(jobData);
                    }
                } catch (CsvValidationException e) {
                    e.printStackTrace();
                }

                csvReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return jobDataList;
        }

        @Override
        protected void onPostExecute(List<JobData> jobDataList) {
            jobAdapter = new JobAdapter(jobDataList);
            recyclerView.setAdapter(jobAdapter);


        }
    }

    private List<String[]> readCSVFileFromAssets(String fileName) throws IOException {
        List<String[]> data = new ArrayList<>();

        InputStreamReader inputStreamReader = new InputStreamReader(getAssets().open(fileName), "UTF-8");
        CSVReader csvReader = new CSVReader(inputStreamReader);

        String[] nextLine;

        // 첫 번째 행(헤더) 읽고 무시
        try {
            csvReader.readNext();
        } catch (CsvValidationException e) {
            e.printStackTrace();
        }

        // 이후 행들 읽기
        try {
            while ((nextLine = csvReader.readNext()) != null) {
                data.add(nextLine);
            }
        } catch (CsvValidationException e) {
            e.printStackTrace();
        }

        csvReader.close();

        return data;
    }
    class JobViewHolder extends RecyclerView.ViewHolder {
        TextView textView1, textView2, textView3, textView4, textView5, textView6;
        CheckBox checkBox;
        JobViewHolder(View itemView) {
            super(itemView);
            textView1 = itemView.findViewById(R.id.textView1);
            textView2 = itemView.findViewById(R.id.textView2);
            textView3 = itemView.findViewById(R.id.textView3);
            textView4 = itemView.findViewById(R.id.textView4);
            textView5 = itemView.findViewById(R.id.textView5);
            textView6 = itemView.findViewById(R.id.textView6);
            checkBox = itemView.findViewById(R.id.checkBox);
        }
    }
    public static class JobData {
        private String[] data;
        private boolean isChecked;

        // static 변수로 체크된 아이템들을 저장할 리스트 추가
        public static List<JobData> checkedItems = new ArrayList<>();

        public JobData(String[] data) {
            this.data = data;
        }

        public String[] getData() {
            return data;
        }

        public boolean isChecked() {
            return isChecked;
        }

        public void setChecked(boolean checked, String[] row) {


            if (checked && !this.isChecked) {
                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                    // 로그인되어 있을 때만 아이템을 체크리스트에 추가하기
                    checkedItems.add(this);

                    // Firebase Realtime Database에 정보 추가
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("JPS");
                    databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .child(row[1])  // 예시로 회사명을 데이터베이스 노드 이름으로 사용
                            .setValue(true);


                } else {
                    // 로그인되어 있지 않은 경우에는 체크 취소 처리
                    this.isChecked = false;
                    Toast.makeText(SearchActivity.mContext, "로그인이 필요한 작업입니다.", Toast.LENGTH_SHORT).show();

                    // 로그인 화면으로 이동
                    Intent intent = new Intent(SearchActivity.mContext, Log_inActivity.class);
                    SearchActivity.mContext.startActivity(intent);


                }


            } else if (!checked && this.isChecked) {
                // 아이템을 체크 리스트에서 삭제한뒤
                checkedItems.remove(this);



                // Firebase Realtime Database에 정보 삭제
                checkedItems.remove(this);
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("checkedJobs");
                databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .child(data[0])  // 예시로 회사명을 데이터베이스 노드 이름으로 사용
                        .removeValue();
            }
            this.isChecked = checked;
        }

        // static 메서드로 체크된 아이템 리스트를 얻는 메서드 추가
        public static List<JobData> getCheckedItems() {
            return checkedItems;
        }
    }

    class JobAdapter extends RecyclerView.Adapter<JobViewHolder> {
        private final List<JobData> jobDataList;
        private SparseBooleanArray checkboxStatus = new SparseBooleanArray();





        JobAdapter(List<JobData> jobDataList) {
            this.jobDataList = jobDataList;
        }

        @Override
        public JobViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false);
            return new JobViewHolder(view);
        }

        @Override
        public void onBindViewHolder(JobViewHolder holder, int position) {            //postion 대신 holder.getAdapterPosition()을 사용했음!!
            JobData job = jobDataList.get(holder.getAdapterPosition());
            String[] row = job.getData();
            holder.textView1.setText("회사명 : " + row[1]);
            holder.textView2.setText("직종 업무 : " + row[2]);
            holder.textView3.setText("계약 구분 : " + row[3]);
            holder.textView4.setText("주소 : " + row[11]);
            holder.textView5.setText("시작일자 : " + row[20]);
            holder.textView6.setText("마감일자 : " + row[21]);



            //체크박스설정

            holder.checkBox.setChecked(checkboxStatus.get(holder.getAdapterPosition()));


            //스크롤 시에도 체크박스 유지
            holder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!holder.checkBox.isChecked())
                        checkboxStatus.put(holder.getAdapterPosition(), false);
                    else
                        checkboxStatus.put(holder.getAdapterPosition(), true);
                    notifyItemChanged(holder.getAdapterPosition());
                }
            });


            //체크박스 변경 감지

            holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> job.setChecked(isChecked,row ));
        }

        @Override
        public int getItemCount() {
            return jobDataList.size();
        }
    }




}