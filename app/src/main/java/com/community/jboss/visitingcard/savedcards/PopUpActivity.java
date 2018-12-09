package com.community.jboss.visitingcard.savedcards;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.community.jboss.visitingcard.R;
import com.community.jboss.visitingcard.data.CardsDatabase;
import com.community.jboss.visitingcard.data.VisitingCard;
import com.community.jboss.visitingcard.data.VisitingCardDao;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import static com.community.jboss.visitingcard.savedcards.SavedCardsAdapter.bytesToBitmap;
import static com.community.jboss.visitingcard.visitingcard.VisitingCardActivity.bitmapToBytes;

public class PopUpActivity extends AppCompatActivity {

    private ImageButton mProfileImg;
    private EditText mName, mNumber, mEmail, mLinkedIn, mTwitter, mGitHub;
    private VisitingCardDao mDao;
    private VisitingCard mVisitingCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up);
        mInitializeViews();
        mSetData();
        findViewById(R.id.fab_save_pop_up).setOnClickListener(view -> {
            mVisitingCard.setName(mName.getText().toString());
            mVisitingCard.setNumber(mNumber.getText().toString());
            mVisitingCard.setEmail(mEmail.getText().toString());
            mVisitingCard.setLinkedin(mLinkedIn.getText().toString());
            mVisitingCard.setTwitter(mTwitter.getText().toString());
            mVisitingCard.setGithub(mGitHub.getText().toString());
            mVisitingCard.setPhoto(bitmapToBytes(((BitmapDrawable) mProfileImg.getDrawable()).getBitmap()));
            mDao.updateCard(mVisitingCard);
            Toast.makeText(this, "Saved.", Toast.LENGTH_SHORT).show();
        });
    }

    private void mInitializeViews() {
        mProfileImg = findViewById(R.id.profile_img_pop_up);
        mName = findViewById(R.id.et_name_card_pop_up);
        mNumber = findViewById(R.id.et_number_card_pop_up);
        mEmail = findViewById(R.id.et_email_card_pop_up);
        mLinkedIn = findViewById(R.id.et_linkedin_card_pop_up);
        mTwitter = findViewById(R.id.et_twitter_card_pop_up);
        mGitHub = findViewById(R.id.et_github_card_pop_up);
    }

    private void mSetData() {
        mDao = CardsDatabase.getInstance(getApplicationContext())
                .getVisitingCardDao();
        mVisitingCard = mDao.getCard(
                getIntent().getStringExtra("id"));
        mName.setText(mVisitingCard.getName());
        mNumber.setText(mVisitingCard.getNumber());
        mEmail.setText(mVisitingCard.getEmail());
        mLinkedIn.setText(mVisitingCard.getLinkedin());
        mTwitter.setText(mVisitingCard.getTwitter());
        mGitHub.setText(mVisitingCard.getGithub());
        Glide.with(this).load(bytesToBitmap(mVisitingCard.getPhoto())).into(mProfileImg);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                mProfileImg.setImageURI(result.getUri());
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(this, "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
            }
        }
    }

    public void mSelectImg(View view) {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setActivityTitle("Select image")
                .setCropShape(CropImageView.CropShape.RECTANGLE)
                .setCropMenuCropButtonTitle("Done")
                .setRequestedSize(144, 144)
                .start(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(RESULT_OK);
        finish();
    }

}
