package com.tianarai;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AMMenuFragment extends Fragment implements View.OnClickListener {

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addClick(R.id.button_theme_1);
        addClick(R.id.button_theme_2);
        addClick(R.id.button_theme_3);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_theme_1:
                changeFragment(R.string.htmlAMCleansingText);
                break;
            case R.id.button_theme_2:
                changeFragment(R.string.htmlAMCleansingText_1);
                break;
            case R.id.button_theme_3:
                changeFragment(R.string.htmlAMCleansingText_2);
                break;
        }
    }

    protected void changeFragment(int id) {
        Bundle args = new Bundle();
        args.putInt("resId", id);

        Fragment fragment = new AMTextFragment();
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container, fragment);
        fragment.setArguments(args);
        ft.commit();
    }

    protected void addClick(int id) {
        try {
            getView().findViewById(id).setOnClickListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}