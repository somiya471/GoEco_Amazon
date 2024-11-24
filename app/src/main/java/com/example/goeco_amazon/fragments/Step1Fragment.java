package com.example.goeco_amazon.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.goeco_amazon.R;
import com.example.goeco_amazon.activities.ConfirmationActivity;
import com.example.goeco_amazon.utils.LoginManager;


public class Step1Fragment extends Fragment {

    LoginManager loginManager;

    public Step1Fragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_step1, container, false);
        loginManager = new LoginManager(getContext());
        RadioGroup deliveryOptionsGroup = view.findViewById(R.id.delivery_options_group);
        view.findViewById(R.id.btnNext1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedOptionId = deliveryOptionsGroup.getCheckedRadioButtonId();
                if (selectedOptionId != -1) {
                    RadioButton selectedRadioButton = view.findViewById(selectedOptionId);
                    String selectedText = selectedRadioButton.getText().toString();
                    loginManager.setOption(selectedText);
//                    Toast.makeText(getContext(), "Currently selected: " + selectedText, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "No option selected", Toast.LENGTH_SHORT).show();
                }
                ((ConfirmationActivity) requireActivity()).loadNextFragment();

            }
        });


        return view;
    }
}