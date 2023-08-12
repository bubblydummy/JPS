package com.example.jps;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {User.class},version = 1)
public abstract class UserDatabase extends RoomDatabase {
    public abstract UserDAO userDao();



    //-----------비동기 작업을 위해 추가함
    private static UserDatabase instance;


    public static synchronized UserDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), UserDatabase.class, "mycsv_db") // 여기서 "database_name"은 실제 데이터베이스 파일의 이름에 맞게 변경되어야 합니다.
                    .createFromAsset("database/mycsv.db") // 미리 생성된 데이터베이스를 assets 폴더에서 가져옴
                    .build();
        }
        return instance;
    }
}
