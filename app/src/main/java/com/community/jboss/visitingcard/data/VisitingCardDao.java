package com.community.jboss.visitingcard.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface VisitingCardDao {

    @Insert
    void insertAll(VisitingCard... visitingCards);

    @Delete
    void delete(VisitingCard visitingCard);

    @Query("SELECT * FROM visitingcard")
    List<VisitingCard> getAllCards();

}
