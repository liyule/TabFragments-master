package mapp.com.sg.tabfragments;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private SectionsPageAdapter mSectionsPageAdapter;

    private ViewPager mViewPager;

    private SearchView searchView;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: Starting.");

        firebaseAuth = FirebaseAuth.getInstance();

        //if user not logged in, sent user to login page
        if(firebaseAuth.getCurrentUser() == null) {
            //profile activity here
            finish();
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        }


        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);

      TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
   tabLayout.setupWithViewPager(mViewPager);
    }


    


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        final MenuItem myActionMenuItem = menu.findItem(R.id.search);
        searchView = (SearchView) myActionMenuItem.getActionView();
        changeSearchViewTextColour(searchView);



        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextChange(String newText){
                //action

                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String query){

                return true;
            }
        });




        //((EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text)).setHintTextColor(getResources().getColor(R.color.white));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(!searchView.isIconified()) {
                    searchView.setIconified(true);
                }

                myActionMenuItem.collapseActionView();

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        //set sign out
        final MenuItem signoutItem = menu.findItem(R.id.action_signout);
        signoutItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                signout();
                return true;
            }
        });

        return true;
    }

    private void signout(){
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(this, LoginActivity.class));
    }

    private List<ImageUpload> filter (List<ImageUpload> pl, String query) {
        query = query.toLowerCase();
        final List<ImageUpload> filteredModeList = new ArrayList<>();
        for(ImageUpload model:pl) {
            final String text = model.getName().toLowerCase();
            if(text.startsWith(query)){
                filteredModeList.add(model);
            }
        }
        return filteredModeList;
    }

    private void changeSearchViewTextColour(View view) {
        if(view != null) {
            if(view instanceof TextView) {
                ((TextView) view).setTextColor(Color.WHITE);
                return;
            }
            else if (view instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) view;
                for (int i = 0; i < viewGroup.getChildCount(); i++) {
                    changeSearchViewTextColour(viewGroup.getChildAt(i));
                }
            }
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new Tab1Fragment(), "Scan");
        adapter.addFragment(new Tab2Fragment(), "Upload");

        viewPager.setAdapter(adapter);
    }

}