package com.community.jboss.visitingcard.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {
        VisitingCard.class
}, version = 1, exportSchema = false)
public abstract class CardsDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "visitingcards.db";

    private static volatile CardsDatabase sInstance;

    public static synchronized CardsDatabase getInstance(Context context) {
        if (sInstance == null) {
            sInstance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    CardsDatabase.class,
                    DATABASE_NAME
                    // TODO: Disallow main thread queries
            ).allowMainThreadQueries().build();
        }
        return sInstance;
    }

    public abstract VisitingCardDao getVisitingCardDao();

}
