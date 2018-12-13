package com.abrarkotwal.socialapp.utils.RoomDB;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM User where email = :username AND password =:password")
    List<User> checkLogin(String username, String password);

    @Insert
    void insert(User user);

}
