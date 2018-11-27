package com.community.jboss.visitingcard.About;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.community.jboss.visitingcard.R;

public class AboutActivity extends AppCompatActivity {

    public static boolean b = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_about, new InfoFragment()).commit();

        Toolbar toolbar = findViewById(R.id.toolbar_about);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (b) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_about, new InfoFragment()).commit();
                    b = false;
                } else finish();
            }
        });
        toolbar.setTitle(R.string.about);

    }

    @Override
    public void onBackPressed() {
        if (b) {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_about, new InfoFragment()).commit();
            b = false;
        } else super.onBackPressed();
    }

}
