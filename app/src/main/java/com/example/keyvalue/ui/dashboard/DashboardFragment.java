package com.example.keyvalue.ui.dashboard;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.keyvalue.R;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;

    public int counter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        final TextView textView = root.findViewById(R.id.text_dashboard);
        dashboardViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        Button button = root.findViewById(R.id.timed_button);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
                int defaultValue = getResources().getInteger(R.integer.saved_times_pressed_default_key);
                final int timesPressed = sharedPref.getInt(getString(R.string.saved_button_press_count_key_timed), defaultValue);

                int newTimesPressed = timesPressed + 1;

                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt(getString(R.string.saved_button_press_count_key_timed), newTimesPressed);
                editor.commit();
                new CountDownTimer(30000,1000) {

                    public void onTick(long millisUntilFinished) {

                        textView.setText(String.valueOf(counter));
                        counter++;
                    }



                    //Do something
                    public void onFinish() {
                        textView.setText("Button has been pressed " + Integer.toString(timesPressed) + " times");
                    }
                }.start();


            }
        });



        return root;
    }


}