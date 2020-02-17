package ir.apptune.coffechi.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ir.apptune.coffechi.R;


public class FirstAdvertiseFragment extends Fragment {
   public static FirstAdvertiseFragment newInstance() {

        FirstAdvertiseFragment fragment = new FirstAdvertiseFragment();
        return fragment;
    }
    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_first_advertise,container,false);
    }
}


