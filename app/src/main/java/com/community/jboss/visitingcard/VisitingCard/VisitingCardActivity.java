package com.community.jboss.visitingcard.VisitingCard;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.community.jboss.visitingcard.Maps.MapsActivity;
import com.community.jboss.visitingcard.Networking.Service;
import com.community.jboss.visitingcard.Networking.VisitingCard;
import com.community.jboss.visitingcard.R;
import com.community.jboss.visitingcard.SettingsActivity;
import com.google.gson.Gson;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VisitingCardActivity extends AppCompatActivity implements View.OnClickListener {

    private CircularImageView mAvatar;
    private TextInputEditText mName;
    private TextInputEditText mEmail;
    private TextInputEditText mNumber;

    private String mGithub;
    private String mLinkedIn;
    private String mTwitter;

    private Service mService;

    private final int RC_GALLERY = 123;
    private final int RC_CAMERA = 1234;
    private final int CAMERA_PERMISSION_CODE = 100;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visiting_card);
        initViews();
        initRetrofit();
    }

    void initViews() {
        mAvatar = findViewById(R.id.card_avatar);
        mName = findViewById(R.id.card_name);
        mEmail = findViewById(R.id.card_email);
        mNumber = findViewById(R.id.card_number);

        mAvatar.setOnClickListener(this);
        mName.setOnClickListener(this);
        mEmail.setOnClickListener(this);
        mNumber.setOnClickListener(this);
        findViewById(R.id.fab).setOnClickListener(this);
        findViewById(R.id.fab_save).setOnClickListener(this);
        findViewById(R.id.card_github).setOnClickListener(this);
        findViewById(R.id.card_linkedin).setOnClickListener(this);
        findViewById(R.id.card_twitter).setOnClickListener(this);
    }

    void initRetrofit() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:4000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mService = retrofit.create(Service.class);
        mService.get().enqueue(new Callback<VisitingCard>() {
            @Override
            public void onResponse(@NonNull Call<VisitingCard> call, @NonNull Response<VisitingCard> response) {
                VisitingCard card = response.body();
                if (card != null) {
                    if (card.getPhoto() != null)
                        Glide.with(getApplicationContext()).load(bytesToBitmap(card.getPhoto())).into(mAvatar);
                    mName.setText(card.getName());
                    mEmail.setText(card.getEmail());
                    mNumber.setText(card.getNumber());
                    mGithub = card.getGithub();
                    mLinkedIn = card.getLinkedin();
                    mTwitter = card.getTwitter();
                } else
                    Toast.makeText(VisitingCardActivity.this, R.string.error_upload, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(@NonNull Call<VisitingCard> call, @NonNull Throwable t) {
                Toast.makeText(VisitingCardActivity.this, R.string.error_upload, Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
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
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @SuppressLint("InflateParams")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.card_avatar:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                View view1 = LayoutInflater.from(getApplicationContext()).inflate(R.layout.dialog_camera, null);

                final AlertDialog dialog = builder.setView(view1).create();
                dialog.show();

                view1.findViewById(R.id.dialog_gallery).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivityForResult(new Intent(Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), RC_GALLERY);
                        dialog.cancel();
                    }
                });

                view1.findViewById(R.id.dialog_camera).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                                requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
                            else {
                                startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), RC_CAMERA);
                            }
                        } else
                            startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), RC_CAMERA);
                        dialog.cancel();
                    }
                });
                break;
            case R.id.fab:
                Snackbar.make(view, "Proceed to Maps Activity", Snackbar.LENGTH_LONG)
                        .setAction("Yes", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent toVisitingCard = new Intent(VisitingCardActivity.this, MapsActivity.class);
                                startActivity(toVisitingCard);
                            }
                        }).show();
                break;
            case R.id.card_github:
                showCustomDialog(0);
                break;
            case R.id.card_linkedin:
                showCustomDialog(1);
                break;
            case R.id.card_twitter:
                showCustomDialog(2);
                break;
            case R.id.fab_save:
                try {
                    VisitingCard visitingCard = new VisitingCard(
                            mName.getText().toString(),
                            mEmail.getText().toString(),
                            mNumber.getText().toString(),
                            mGithub,
                            mLinkedIn,
                            mTwitter,
                            bitmapToBytes(((BitmapDrawable) mAvatar.getDrawable()).getBitmap()));
                    System.out.println(new Gson().toJson(visitingCard));
                    mService.post(visitingCard).enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                            if (response.body() != null && response.body().isEmpty())
                                Toast.makeText(VisitingCardActivity.this, R.string.error_upload, Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(VisitingCardActivity.this, R.string.saved, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                            Toast.makeText(VisitingCardActivity.this, R.string.error_upload, Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private byte[] bitmapToBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    private Bitmap bytesToBitmap(byte[] bytes) {
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    void showCustomDialog(final int a) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.card_dialog, null);
        builder.setView(view);
        final EditText link = view.findViewById(R.id.card_dialog);
        AlertDialog dialog = builder.setPositiveButton(getString(R.string.save), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (a == 0) mGithub = link.getText().toString();
                else if (a == 1) mLinkedIn = link.getText().toString();
                else if (a == 2) mTwitter = link.getText().toString();
                dialogInterface.cancel();
            }
        }).setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        }).create();
        dialog.show();
        link.setText(a == 0 ? mGithub : a == 1 ? mLinkedIn : mTwitter);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent cameraIntent = new
                        Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, RC_CAMERA);
            }
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK)
            if (requestCode == RC_CAMERA) {
                try {
                    Bitmap photo = (Bitmap) data.getExtras().get("data");
                    Glide.with(getApplicationContext()).load(photo).into(mAvatar);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Error taking picture.", Toast.LENGTH_SHORT).show();
                }
            } else if (requestCode == RC_GALLERY) {
                Uri uri = data.getData();
                Bitmap bitmap;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), uri);
                    Glide.with(getApplicationContext()).load(bitmap).into(mAvatar);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
    }

}
