package com.isaiko.hosnyorder.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.isaiko.hosnyorder.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SettingsFragment extends Fragment {
    @BindView(R.id.tv_profile_settings)
    TextView profileSettingsTextView;
    @BindView(R.id.tv_choose_language)
    TextView chooseLanguageTextView;
    @BindView(R.id.tv_report_problem)
    TextView reportProblemTextView;
    @BindView(R.id.tv_privacy_policy)
    TextView privacyPolicyTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view =  inflater.inflate(R.layout.fragment_settings,container,false);
        ButterKnife.bind(this,view);
        profileSettingsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(view, "Profile Settings", Snackbar.LENGTH_SHORT).show();
            }
        });

        chooseLanguageTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Choose Language")
                        .setSingleChoiceItems(R.array.array_languages, -1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .setPositiveButton("Change", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ListView lw = ((AlertDialog)dialog).getListView();
                                int position = lw.getCheckedItemPosition();
                                if(position == 0){
                                    Snackbar.make(view,"Change to English",Snackbar.LENGTH_SHORT).show();
                                }else if(position == 1){
                                    Snackbar.make(view,"Change to Arabic",Snackbar.LENGTH_SHORT).show();
                                }
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).create().show();
            }
        });

        reportProblemTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        privacyPolicyTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });


        return view;
    }
}
