package com.example.tarogame;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
@Database(entities = Card.class, version = 1, exportSchema = false)
public abstract class CardsDataBase extends RoomDatabase {
    private static CardsDataBase dataBase;
    private static final String DB_NAME = "cardstaro.db";
    private static final Object LOCK = new Object();

    public static CardsDataBase getInstance(Context context){
        synchronized (LOCK) {
            if (dataBase == null) {
                dataBase = Room.databaseBuilder(context, CardsDataBase.class, DB_NAME).allowMainThreadQueries().build();
            }
        }
      return dataBase;
    }
    public abstract CardDao cardDao();
}
