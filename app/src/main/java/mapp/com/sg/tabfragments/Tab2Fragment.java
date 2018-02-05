package mapp.com.sg.tabfragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;

/**
 * Created by User on 22/1/2018.
 */



public class Tab2Fragment extends Fragment {

    private FloatingActionButton fab;

    private static final String TAG = "Tab2Fragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab2_fragment, container, false);


        fab = (FloatingActionButton) view.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v) {
                if (v == fab) {
                    startActivity(new Intent(getActivity(), UploadActivity.class));
                }
            }
        });

        return view;
    }
}

