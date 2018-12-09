package com.community.jboss.visitingcard.savedcards;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.community.jboss.visitingcard.R;
import com.community.jboss.visitingcard.data.CardsDatabase;
import com.community.jboss.visitingcard.data.VisitingCard;
import com.community.jboss.visitingcard.data.VisitingCardDao;

import java.util.List;

import static com.community.jboss.visitingcard.savedcards.SavedCardsAdapter.RC_POP_UP;

public class SavedCardsActivity extends AppCompatActivity {

    private SavedCardsAdapter mAdapter;
    private boolean mFavourite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_cards);
        mFavourite = getIntent().getBooleanExtra("favourite", false);
        if(mFavourite) setTitle(R.string.favourite_cards);
        else setTitle(R.string.saved_cards);
        mInitRV();
    }

    private void mInitRV() {
        RecyclerView recyclerView = findViewById(R.id.rv_cards);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new SavedCardsAdapter();
        mAdapter.updateData(mGetData(), this,false);
        recyclerView.setAdapter(mAdapter);
    }

    private List<VisitingCard> mGetData() {
        VisitingCardDao dao = CardsDatabase.getInstance(getApplicationContext())
                .getVisitingCardDao();
        if (mFavourite)
            return dao.getAllFavouriteCards();
        else
            return dao.getAllCards();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_POP_UP && resultCode == RESULT_OK) {
            mAdapter.updateData(mGetData(), this,false);
            mAdapter.notifyDataSetChanged();
        }
    }

}
