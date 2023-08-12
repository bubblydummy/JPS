package com.example.jps;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.room.Room;
//비동기 작업을 위해 추가한클래스

public class MyViewModel extends ViewModel {

    private final Context context;
    private MutableLiveData<String> dbResultLiveData = new MutableLiveData<>();

    public LiveData<String> getDbResultLiveData() {
        return dbResultLiveData;
    }

    public MyViewModel(Context context) {this.context = context;}



    public void performDbOperationInBackground() {
        // 백그라운드에서 DB 작업을 수행하는 코드를 여기에 작성합니다.
        // 예를 들면 AsyncTask, Thread, Coroutine 등을 사용하여 처리할 수 있습니다.
        // 여기서는 단순히 시뮬레이션을 위해 2초 후에 완료된 것으로 가정합니다.




        new Thread(() -> {

            try {
                UserDAO userDao = UserDatabase.getInstance(context).userDao();


                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // 결과를 LiveData에 설정하여 UI에 전달
            dbResultLiveData.postValue("DB 작업이 완료되었습니다.");
        }).start();
    }
}
