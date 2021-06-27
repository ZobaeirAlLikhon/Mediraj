package com.example.mediraj.localdb;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {DiagnosticService.class,PathologyServices.class,SurgicalService.class}, version =2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public static AppDatabase INSTANCE;

    public static AppDatabase getDbInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),AppDatabase.class,"mediraj_db")
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build();
        }

        return INSTANCE;
    }

    public abstract DiagnosticServiceDao diagnosticServiceDao();
    public abstract PathologyServicesDao pathologyServicesDao();
    public abstract SurgicalServiceDao surgicalServiceDao();

}
