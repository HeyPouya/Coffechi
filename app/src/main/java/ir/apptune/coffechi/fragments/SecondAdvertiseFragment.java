package ir.apptune.coffechi.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ir.apptune.coffechi.R;

public class SecondAdvertiseFragment extends Fragment {


    public static SecondAdvertiseFragment newInstance() {


        SecondAdvertiseFragment fragment = new SecondAdvertiseFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_second_advertise, container, false);
    }

}
