package com.tianarai;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
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

    private static final int ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE = 255;
    private static final int CAMERA_PERMISSION_CODE = 100;
    private static final int STORAGE_PERMISSION_CODE = 101;

    boolean[] alarms;
    Alarm alarm;
    DatePickerDialog.OnDateSetListener setDateListener;
    Calendar startDate;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
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
                //RequestPermission();
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

    private void RequestPermission() {
        // Check if Android M or higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Show alert dialog to the user saying a separate permission is needed
            // Launch the settings activity if the user prefers
            /* 1
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getActivity().getPackageName()));
            startActivityForResult(intent, ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE);*/

            /* 2
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getActivity().getPackageName()));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getActivity().startActivity(intent);*/

            // Requesting the permission
            ActivityCompat.requestPermissions(getActivity(),
                    new String[] { Manifest.permission.SYSTEM_ALERT_WINDOW }, ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!Settings.canDrawOverlays(getContext())) {
                    //PermissionDenied();
                } else {
                    //Permission Granted-System will work
                }

            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults)
    {
        super
                .onRequestPermissionsResult(requestCode,
                        permissions,
                        grantResults);

        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getActivity(),
                        "Camera Permission Granted",
                        Toast.LENGTH_SHORT)
                        .show();
            }
            else {
                Toast.makeText(getActivity(),
                        "Camera Permission Denied",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        }
        else if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getActivity(),
                        "Storage Permission Granted",
                        Toast.LENGTH_SHORT)
                        .show();
            }
            else {
                Toast.makeText(getActivity(),
                        "Storage Permission Denied",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

}