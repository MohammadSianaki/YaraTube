package com.yaratech.yaratube.ui.contactus;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import mehdi.sakout.aboutpage.AboutPage;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactWithUsFragment extends Fragment {


    public ContactWithUsFragment() {
        // Required empty public constructor
    }

    public static ContactWithUsFragment newInstance() {

        Bundle args = new Bundle();

        ContactWithUsFragment fragment = new ContactWithUsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return new AboutPage(getContext())
                .isRTL(true)
                .setDescription("Developed by Mohammad Hosseini Sianaki at Yara Information Technology")
                .addGroup("Connect with us")
                .addEmail("mohammad.sianaki@ut.ac.ir")
                .addWebsite("yaramobile.com")
                .addGitHub("mohammadsianaki")
                .addInstagram("mohammad_sianaki97")
                .create();
    }

}
