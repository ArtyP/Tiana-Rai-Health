package com.tianarai;

import android.app.DatePickerDialog;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.tianarai.R;

import java.text.DateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class AMAlarmFragment extends Fragment implements View.OnClickListener {

    boolean[] alarms;
    Alarm alarm;
    DatePickerDialog.OnDateSetListener setDateListener;
    Calendar startDate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_alarm, container, false);
    }


    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        alarms = new boolean[9];
        Arrays.fill(alarms, true);
        alarm = new Alarm();

        addClick(R.id.button_set_date);
        addClick(R.id.button_set_alarm);

        addClick(R.id.button_1800);
        addClick(R.id.button_2000);
        addClick(R.id.button_2200);
        addClick(R.id.button_0600);
        addClick(R.id.button_0800);
        //addClick(R.id.checkbox_2200);

        startDate = Calendar.getInstance();
        Date currentTime = startDate.getTime();
        Date deadLine = startDate.getTime();
        deadLine.setHours(14);

        if (new Date().after(deadLine)) {
            startDate.add(Calendar.DAY_OF_MONTH, 1);
        }

        setDateText();

        setDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                //Calendar c = Calendar.getInstance();
                startDate.set(Calendar.YEAR, year);
                startDate.set(Calendar.MONTH, month);
                startDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                setDateText();
            }
        };
    }

    protected void addClick(int id) {
        try {
            getView().findViewById(id).setOnClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onClick(View v) {
        Button button = (Button)v;
        switch (v.getId()) {
            case R.id.button_set_alarm:
                //Toast.makeText(v.getContext(), R.string.am_alarm_set, 5000).show();
                alarm.setAlarm(v.getContext());
                break;
            case R.id.button_set_date:
                DatePickerDialog dpDialog = new DatePickerDialog(
                        v.getContext(),
                        R.style.DialogTheme,
                        setDateListener,
                        startDate.get(Calendar.YEAR),
                        startDate.get(Calendar.MONTH),
                        startDate.get(Calendar.DAY_OF_MONTH)
                );
                dpDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

                dpDialog.show();
                break;
            case R.id.button_1800: toggleAlarm(button, 0); break;
            case R.id.button_2000: toggleAlarm(button, 1); break;
            case R.id.button_2200: toggleAlarm(button, 2); break;
            case R.id.button_0600: toggleAlarm(button, 3); break;
            case R.id.button_0800: toggleAlarm(button, 4); break;

        }
    }

    private void toggleAlarm(Button button, int index) {
        alarms[index] = !alarms[index];
        if (alarms[index])
            button.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_volume_up_24, 0);
        else
            button.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_baseline_volume_off_24, 0);
    }

    public void setDateText() {
        String startStr = DateFormat.getDateInstance().format(startDate.getTime());
        Button setDateBtn = getView().findViewById(R.id.button_set_date);
        setDateBtn.setText(startStr);

        Calendar endDate = (Calendar) startDate.clone();
        endDate.add(Calendar.DAY_OF_MONTH, 1);

        String endStr = DateFormat.getDateInstance().format(endDate.getTime());
        Button endDateBtn = getView().findViewById(R.id.button_second_date);
        endDateBtn.setText(endStr);
    }

}