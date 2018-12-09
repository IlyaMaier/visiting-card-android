package com.community.jboss.visitingcard.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface VisitingCardDao {

    @Insert
    void insertAll(VisitingCard... visitingCards);

    @Update
    void updateCard(VisitingCard visitingCard);

    @Delete
    void delete(VisitingCard visitingCard);

    @Query("SELECT * FROM visitingcard")
    List<VisitingCard> getAllCards();

    @Query("SELECT * FROM visitingcard WHERE id = :id")
    VisitingCard getCard(String id);

}
