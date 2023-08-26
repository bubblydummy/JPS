package com.example.jps;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface BojiDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertBoji(Boji boji);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMultipleBojis(List<Boji> bojiList);

    @Delete
    void deleteBoji(Boji boji);

    @Update
    void updateBoji(Boji boji);

    @Query("SELECT * FROM Boji")
    LiveData<List<Boji>> getAllBojis();

    @Query("SELECT * FROM Boji WHERE " +
            "LATITUDE BETWEEN :minLat AND :maxLat AND " +
            "LONGITUDE BETWEEN :minLng AND :maxLng")
    List<Boji> getBojiWithinCircle(double minLat, double maxLat, double minLng, double maxLng);


    //장애인 주간보호시설, 근로사업장, 보호작업장,거주시설, 중증장애인거주시설,장애인 공동생활가정,실비장애시설, 정신요양시설, 재활시설, 등등이 있음
}
