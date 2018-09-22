package com.isaiko.hosnyorder.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.isaiko.hosnyorder.Activity.MainActivity;
import com.isaiko.hosnyorder.Activity.ProfileSettingsActivity;
import com.isaiko.hosnyorder.R;
import com.isaiko.hosnyorder.Utils.LocaleHelper;

import java.util.Locale;

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
                Intent profileSettingsIntent = new Intent(getContext(), ProfileSettingsActivity.class);
                getContext().startActivity(profileSettingsIntent);
            }
        });

        chooseLanguageTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(getResources().getString(R.string.label_choose_language))
                        .setSingleChoiceItems(R.array.array_languages, Locale.getDefault().getLanguage().equals("en")?0:1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .setPositiveButton(getResources().getString(R.string.label_change), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ListView lw = ((AlertDialog)dialog).getListView();
                                int position = lw.getCheckedItemPosition();
                                if(position == 0){
                                    if (!Locale.getDefault().getLanguage().equals("en"))
                                        updateViews("en");
                                }else if(position == 1){
                                    if (!Locale.getDefault().getLanguage().equals("ar"))
                                        updateViews("ar");
                                }
                            }
                        }).setNegativeButton(getResources().getString(R.string.label_cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
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

    private void updateViews(String languageCode) {
        LocaleHelper.setLocale(getContext(), languageCode);
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
