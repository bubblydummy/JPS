package com.example.jps;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDAO {

    @Insert//삽입
    void setInsertUser(User user);

    @Update
    void setUpdateUser(User user);

    @Delete
    void setDeletUser(User user);


    @Query("SELECT * FROM User")// sql문(다방면으로 활용가능, 이것은 전체조회 기능)
    List<User> getUserAll();

    @Query("SELECT * FROM User LIMIT 1")
    User getFirstUser();


    @Query("SELECT * FROM USER WHERE employment_type = :job_type") //
    List<User> findByJobType(String job_type);


}
