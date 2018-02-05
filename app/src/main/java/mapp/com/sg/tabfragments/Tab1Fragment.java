package mapp.com.sg.tabfragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.provider.MediaStore;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 22/1/2018.
 */

public class Tab1Fragment extends Fragment {

    private DatabaseReference mDatabaseRef;
    private List<ImageUpload> imgList;
    private ListView lv;
    private ImageListAdapter adapter;
    private ProgressDialog progressDialog;

    private static final String TAG = "Tab1Fragment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab1_fragment,container,false);

        imgList = new ArrayList<>();
        lv = (ListView) view.findViewById(R.id.listViewImage);

        //Show progress dialog during list image loading
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait loading list image...");
        progressDialog.show();


        mDatabaseRef = FirebaseDatabase.getInstance().getReference(UploadActivity.FB_DATABASE_PATH);

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressDialog.dismiss();

                //Fetch image data from firebase database
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    //ImageUpload class require default constructor
                    ImageUpload img = snapshot.getValue(ImageUpload.class);
                    imgList.add(img);
                }


                //Init adapter
                ImageListAdapter adapter = new ImageListAdapter(getActivity(),R.layout.tab1_fragment, imgList);
                //Set adapter for listview                                      //image_item, imgList
                lv.setAdapter(adapter);
                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        //startActivity(new Intent(getActivity(), UploadActivity.class));
                        Intent Intent3 = new Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA);
                        startActivity(Intent3);
                    }
                });
            }



            @Override
            public void onCancelled(DatabaseError databaseError) {

                progressDialog.dismiss();
            }
        });



        return view;
    }

}
