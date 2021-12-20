package com.islamicinfo.src.main.java.com.view;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.islamicinfo.R;
import com.islamicinfo.src.main.java.com.model.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContactUsFragment extends Fragment {

    private static final String TAG = ContactUsFragment.class.getSimpleName();
    @BindView(R.id.help_text)
    EditText mEditText;

    @BindView(R.id.help_button)
    Button mButton;

    public ContactUsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact_us, container, false);
        ButterKnife.bind(this,view);
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //Toast.makeText(getActivity(), "beforeTextChanged", Toast.LENGTH_SHORT).show();
                Log.d(Constants.HELP_TAG,TAG + " beforeTextChanged: ");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d(Constants.HELP_TAG,TAG + "onTextChanged");
                if (!s.toString().equals("") && !(s.toString().length() <= 0) && !s.toString().equals(" ")){
                    mButton.setClickable(true);
                    mButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                }
                else{
                    mButton.setClickable(false);
                    mButton.setBackgroundColor(getResources().getColor(R.color.darkGrey));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("mailto:" + getResources().getString(R.string.feedback_email));
                Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
                intent.putExtra(Intent.EXTRA_SUBJECT,
                        "User query for " + getResources().getString(R.string.app_name));
                intent.putExtra(Intent.EXTRA_TEXT,mEditText.getText().toString());
                try{
                    startActivity(Intent.createChooser(intent,getResources().
                            getString(R.string.send_query_text)));
                }
                catch(ActivityNotFoundException e){
                    Toast.makeText(getActivity(), getResources().getString(R.string.help_app_not_found)
                            + " " + getResources().getString(R.string.activity_not_found_text
                    ), Toast.LENGTH_SHORT).show();
                }
                NavController navController = Navigation.findNavController(view);
                navController.popBackStack();
            }
        });

        return view;
    }

}
