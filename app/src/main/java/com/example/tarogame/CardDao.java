package com.example.tarogame;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CardDao {
    @Query("SELECT * FROM cardstaro ORDER BY id")
    List<Card> getAllCards();

    @Insert
    void insertCard(Card card);

    @Query("DELETE FROM cardstaro")
    void deleteAllCards();
}
