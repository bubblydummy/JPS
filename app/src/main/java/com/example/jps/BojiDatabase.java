package com.example.jps;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Boji.class}, version = 2, exportSchema = false)
public abstract class BojiDatabase extends RoomDatabase {

    private static BojiDatabase instance;

    public abstract BojiDao bojiDao();

    public static synchronized BojiDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            BojiDatabase.class, "boji_daegu")
                    .fallbackToDestructiveMigration() // 데이터베이스 스키마 변경 시 마이그레이션을 위해
                    .build();
        }
        return instance;
    }

}