package com.community.jboss.visitingcard.savedcards;

import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.community.jboss.visitingcard.R;
import com.community.jboss.visitingcard.data.CardsDatabase;
import com.community.jboss.visitingcard.data.VisitingCard;
import com.community.jboss.visitingcard.data.VisitingCardDao;
import com.google.gson.Gson;

import static com.community.jboss.visitingcard.savedcards.SavedCardsAdapter.bytesToBitmap;

public class PopUpActivity extends AppCompatActivity {

    private ImageView mStar;
    private ImageButton mProfileImg;
    private TextView mName, mNumber, mEmail, mLinkedIn, mTwitter, mGitHub;
    private VisitingCardDao mDao;
    private VisitingCard mVisitingCard;
    private int mFavourite = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up);
        mInitializeViews();
        mSetData();
    }

    private void mInitializeViews() {
        mProfileImg = findViewById(R.id.profile_img_pop_up);
        mName = findViewById(R.id.et_name_card_pop_up);
        mNumber = findViewById(R.id.et_number_card_pop_up);
        mEmail = findViewById(R.id.et_email_card_pop_up);
        mLinkedIn = findViewById(R.id.et_linkedin_card_pop_up);
        mTwitter = findViewById(R.id.et_twitter_card_pop_up);
        mGitHub = findViewById(R.id.et_github_card_pop_up);
        mStar = findViewById(R.id.btn_favourite_pop_up);

        findViewById(R.id.fab_save_pop_up).setOnClickListener(view -> {
            if (mFavourite == 1 && mDao.getAllFavouriteCards().size() == 5) {
                Toast.makeText(this, "You can't have more than 5 favourite cards.", Toast.LENGTH_SHORT).show();
            } else {
                mVisitingCard.setFavourite(mFavourite);
                mDao.updateCard(mVisitingCard);
            }
            setResult(RESULT_OK);
            finish();
        });

        mStar.setOnClickListener(view -> {
            if (mFavourite == 1) {
                mFavourite = 0;
                mStar.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_24dp));
            } else {
                mFavourite = 1;
                mStar.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_filled_24dp));
            }
        });
    }

    private void mSetData() {
        mDao = CardsDatabase.getInstance(getApplicationContext())
                .getVisitingCardDao();
        
        if (getIntent().getStringExtra("id") == null)
            mVisitingCard = new Gson().fromJson(getIntent().getStringExtra("card"), VisitingCard.class);
        else mVisitingCard = mDao.getCard(
                getIntent().getStringExtra("id"));

        mName.setText(mVisitingCard.getName());
        mNumber.setText(mVisitingCard.getNumber());
        mEmail.setText(mVisitingCard.getEmail());
        mLinkedIn.setText(mVisitingCard.getLinkedin());
        mTwitter.setText(mVisitingCard.getTwitter());
        mGitHub.setText(mVisitingCard.getGithub());
        if (mVisitingCard.getPhoto() != null)
            Glide.with(this).load(bytesToBitmap(mVisitingCard.getPhoto())).into(mProfileImg);

        mFavourite = mVisitingCard.getFavourite();
        if (mFavourite == 1)
            mStar.setImageDrawable(getResources().getDrawable(R.drawable.ic_star_filled_24dp));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(RESULT_OK);
        finish();
    }

}
