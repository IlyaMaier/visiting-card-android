package com.community.jboss.visitingcard.visitingcard;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.community.jboss.visitingcard.about.AboutActivity;
import com.community.jboss.visitingcard.data.CardsDatabase;
import com.community.jboss.visitingcard.data.VisitingCard;
import com.community.jboss.visitingcard.data.VisitingCardDao;
import com.community.jboss.visitingcard.maps.MapsActivity;
import com.community.jboss.visitingcard.R;
import com.community.jboss.visitingcard.SettingsActivity;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class VisitingCardActivity extends AppCompatActivity {

    private ImageButton mProfileImg;
    private EditText mName, mNumber, mEmail, mLinkedIn, mTwitter, mGitHub;

    public static final String PREF_DARK_THEME = "dark_theme";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visiting_card);

        mInitializeViews();

        // TODO: On Click on FAB should make a network call to store the entered information in the cloud using POST method(Do this in NetworkUtils class)
        findViewById(R.id.fab).setOnClickListener(view -> Snackbar.make(view, "Proceed to Maps Activity", Snackbar.LENGTH_LONG)
                .setAction("Yes", view1 -> {
                    Intent toVisitingCard = new Intent(VisitingCardActivity.this, MapsActivity.class);
                    startActivity(toVisitingCard);
                }).show());

        findViewById(R.id.fab_save).setOnClickListener(view -> {
            VisitingCardDao dao = CardsDatabase.getInstance(getApplicationContext())
                    .getVisitingCardDao();
            dao.insertAll(new VisitingCard(
                    mName.getText().toString(),
                    mNumber.getText().toString(),
                    mEmail.getText().toString(),
                    mLinkedIn.getText().toString(),
                    mTwitter.getText().toString(),
                    mGitHub.getText().toString(),
                    bitmapToBytes(((BitmapDrawable) mProfileImg.getDrawable()).getBitmap())
            ));
            Toast.makeText(this, "Saved.", Toast.LENGTH_SHORT).show();
        });
    }

    public static byte[] bitmapToBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    private void mInitializeViews() {
        mProfileImg = findViewById(R.id.profile_img);
        mName = findViewById(R.id.et_name_card);
        mNumber = findViewById(R.id.et_number_card);
        mEmail = findViewById(R.id.et_email_card);
        mLinkedIn = findViewById(R.id.et_linkedin_card);
        mTwitter = findViewById(R.id.et_twitter_card);
        mGitHub = findViewById(R.id.et_github_card);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                Intent intent = new Intent(VisitingCardActivity.this, SettingsActivity.class);
                startActivity(intent);
                return true;
            case R.id.about:
                Intent aboutIntent = new Intent(VisitingCardActivity.this, AboutActivity.class);
                startActivity(aboutIntent);
                return true;
            case R.id.darktheme:
                SharedPreferences preferences = android.preference.PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                boolean useDarkTheme = preferences.getBoolean(AboutActivity.PREF_DARK_THEME, false);
                if (!useDarkTheme) {
                    SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
                    editor.putBoolean(PREF_DARK_THEME, true);
                    editor.apply();
                } else {
                    SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
                    editor.putBoolean(PREF_DARK_THEME, false);
                    editor.apply();
                }
                Intent restarter = getIntent();
                finish();
                startActivity(restarter);
            default:
                return super.onOptionsItemSelected(item);
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

}
