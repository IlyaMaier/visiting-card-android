package com.community.jboss.visitingcard.VisitingCard;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.community.jboss.visitingcard.LoginActivity;
import com.community.jboss.visitingcard.Maps.MapsActivity;
import com.community.jboss.visitingcard.R;
import com.community.jboss.visitingcard.SettingsActivity;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.io.IOException;

public class VisitingCardActivity extends AppCompatActivity implements View.OnClickListener {

    private CircularImageView avatar;
    private final int RC_GALLERY = 123;
    private final int RC_CAMERA = 1234;
    private final int CAMERA_PERMISSION_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visiting_card);

        // TODO: Add a ImageView and a number of EditText to get his/her Visiting Card details (Currently authenticated User)

        avatar = findViewById(R.id.avatar_card);
        avatar.setOnClickListener(this);

        // TODO: On Click on FAB should make a network call to store the entered information in the cloud using POST method(Do this in NetworkUtils class)
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Proceed to Maps Activity", Snackbar.LENGTH_LONG)
                        .setAction("Yes", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent toVisitingCard = new Intent(VisitingCardActivity.this, MapsActivity.class);
                                startActivity(toVisitingCard);
                            }
                        }).show();
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
            case R.id.avatar_card:
                View view1 =
                        LayoutInflater.from(getApplicationContext()).inflate(R.layout.dialog_camera, null);

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
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
        }
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
                    Glide.with(getApplicationContext()).load(photo).into(avatar);
                } catch (NullPointerException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Error taking picture.", Toast.LENGTH_SHORT).show();
                }
            } else if (requestCode == RC_GALLERY) {
                Uri uri = data.getData();
                Bitmap bitmap;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), uri);
                    Glide.with(getApplicationContext()).load(bitmap).into(avatar);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
    }

}
