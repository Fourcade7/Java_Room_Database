package com.pr7.java_room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User... users);

    @Query("SELECT * FROM user")
    List<User> readallusers();

    @Update
    void update(User... users);

    @Delete
    void delete(User ...users);

}
