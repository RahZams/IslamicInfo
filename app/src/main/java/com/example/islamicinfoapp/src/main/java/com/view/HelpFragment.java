package com.example.islamicinfoapp.src.main.java.com.view;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.islamicinfoapp.BuildConfig;
import com.example.islamicinfoapp.R;
import com.example.islamicinfoapp.src.main.java.com.model.HelpItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class HelpFragment extends Fragment {

    @BindView(R.id.listview)
    ListView mListView;

    int mHelpImages[] = {R.drawable.ic_help_contact, R.drawable.ic_feedback, R.drawable.ic_preg_info};
    ArrayList<HelpItem> mHelpTextList;
    String[] mHelpText;


    public HelpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_help, container, false);
        ButterKnife.bind(this, view);
        mHelpTextList = new ArrayList<>();
        mHelpText = getResources().getStringArray(R.array.help_item_name);
        for (int i = 0; i < mHelpText.length; i++) {
            mHelpTextList.add(new HelpItem(mHelpText[i], mHelpImages[i]));
        }

        HelpAdapter helpAdapter = new HelpAdapter(getContext(), R.layout.help_item, mHelpTextList);
        mListView.setAdapter(helpAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NavController navController = Navigation.findNavController(view);
                NavigationUI.setupActionBarWithNavController((AppCompatActivity) getActivity(), navController);
                switch (position) {
                    case 0:
                        navController.navigate(R.id.action_helpFragment_to_contactUsFragment);
                        break;
                    case 1:
                        //navController.navigate(R.id.action_helpFragment_to_feedbackFragment);
                        createFeedbackDialog();
                        break;
                    case 2:
                        navController.navigate(R.id.action_helpFragment_to_appInfoFragment);
                        break;
                    default:
                }
            }
        });

        return view;
    }

    private void createFeedbackDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setIcon(R.drawable.ic_feedback_dialog_icon);
        builder.setTitle(R.string.feedback_dialog_title);
        builder.setMessage(R.string.feedback_dialog_message);
        builder.setCancelable(false);
        builder.setPositiveButton(R.string.feedback_positive_text, (dialog, which) -> {
            Toast.makeText(getActivity(), "send feedback", Toast.LENGTH_SHORT).show();
            Uri uri = Uri.parse("mailto:" + getResources().getString(R.string.feedback_email));
            Intent intent = new Intent(Intent.ACTION_SENDTO,uri);
            //intent.setData(Uri.parse("mailto:"));
            //intent.setType("text/plain");
            //intent.putExtra(Intent.EXTRA_EMAIL,"rahathzamar92@gmail.com");
            intent.putExtra(Intent.EXTRA_SUBJECT,"Feedback on " + getResources().getString(R.string.app_name)
                    + " " + BuildConfig.VERSION_NAME);
            try{
                startActivity(Intent.createChooser(intent,
                        getResources().getString(R.string.feedback_positive_text)));
            }
            catch(ActivityNotFoundException e){
                Toast.makeText(getActivity(), getResources().getString(R.string.feedback_app_not_found)
                        + " " + getResources().getString(R.string.activity_not_found_text), Toast.LENGTH_SHORT).show();

            }
        });
        builder.setNegativeButton(R.string.feedback_negative_text,
                (dialog, which) -> {
                    //alertDialog.dismiss();
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}
