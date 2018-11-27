package com.community.jboss.visitingcard.About;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.community.jboss.visitingcard.R;

public class AboutFragment extends Fragment {

    public AboutFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, container, false);
        TextView textView = view.findViewById(R.id.tv_fragment_about);
        textView.setText(Html.fromHtml("<h1>Visiting Card Android\n\n" +
                "<h2>Quick Insight</h2>\n" +
                "An Android application that helps to virtually exchange digital visiting cards while attending meetups and conferences.\n\n"));
        return view;
    }

}
