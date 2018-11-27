package com.community.jboss.visitingcard.About;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.community.jboss.visitingcard.R;

public class OrganizationFragment extends Fragment implements View.OnClickListener {

    public OrganizationFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_organization, container, false);
        ((TextView) view.findViewById(R.id.tv_fragment_organization)).setText(Html.fromHtml("<h3>JBoss Community is a community of open source projects primarily written in Java.</h3>\n\n" +
                "JBoss Community is a community of open source projects. The community hosts a large number of projects that are written in various programming languages. The primary language is Java. But there are also projects that are written in Ruby, PHP, Node and other languages.\n\n" +
                "Project categories range from better testing support over IDEs, application servers, application and performance monitoring to micro-services.\n\n" +
                "<b>Primary Open Source License:</b> Apache License 2.0 (Apache-2.0)"));
        view.findViewById(R.id.github).setOnClickListener(this);
        view.findViewById(R.id.twitter).setOnClickListener(this);
        view.findViewById(R.id.gitter).setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        Intent myIntent = null;
        switch (view.getId()) {
            case R.id.github:
                myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/JBossOutreach/"));
                break;
            case R.id.twitter:
                myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/jboss"));
                break;
            case R.id.gitter:
                myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://gitter.im/JBossOutreach/"));
                break;
        }
        startActivity(myIntent);
    }

}
