package com.yaratech.yaratube.ui.aboutus;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yaratech.yaratube.R;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutFragment extends Fragment {


    public AboutFragment() {
        // Required empty public constructor
    }

    public static AboutFragment newInstance() {

        Bundle args = new Bundle();

        AboutFragment fragment = new AboutFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Element versionElement = new Element();
        versionElement.setTitle("Version 1.0.0");
        return new AboutPage(getContext())
                .isRTL(true)
                .setImage(R.drawable.logo)
                .setDescription("This application has been developed during my internship at Yara Information Technology")
                .addItem(versionElement)
                .addGroup("Connect with us")
                .addEmail("mohammad.sianaki@ut.ac.ir")
                .addWebsite("https://github.com/MohammadSianaki")
                .addGroup("Developer : Mohammad Hosseini Sianaki")
                .create();
    }

}
