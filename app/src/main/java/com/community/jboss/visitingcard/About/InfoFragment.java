package com.community.jboss.visitingcard.About;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.community.jboss.visitingcard.BuildConfig;
import com.community.jboss.visitingcard.R;

public class InfoFragment extends Fragment implements View.OnClickListener {

    TextView version;

    public InfoFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_info, container, false);

        version = view.findViewById(R.id.version);
        version.setText(getString(R.string.version_s, BuildConfig.VERSION_NAME));

        view.findViewById(R.id.fl_about).setOnClickListener(this);
        view.findViewById(R.id.fl_organization).setOnClickListener(this);
        view.findViewById(R.id.fl_contributors).setOnClickListener(this);
        view.findViewById(R.id.fl_help).setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.fl_help) {
            Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://gitter.im/JBossOutreach/visiting-card"));
            startActivity(myIntent);
            return;
        }

        Fragment fragment = null;
        switch (view.getId()) {
            case R.id.fl_about:
                fragment = new AboutFragment();
                break;
            case R.id.fl_organization:
                fragment = new OrganizationFragment();
                break;
            case R.id.fl_contributors:
                fragment = new ContributorsFragment();
                break;
        }
        if (getFragmentManager() != null && fragment != null) {
            getFragmentManager().beginTransaction().replace(R.id.frame_about, fragment).commit();
            AboutActivity.b = true;
        }
    }

}
