package vn.edu.tdmu.imuseum.views;


import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import vn.edu.tdmu.imuseum.adapter.ArtifactSaveAdapter;
import vn.edu.tdmu.imuseum.manager.DBManager;
import vn.edu.tdmu.imuseum.model.ArtifactSave;

import android.support.design.widget.NavigationView;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class ArtifactSaveActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ArtifactSaveAdapter adapter;
    private List<ArtifactSave> artifactSaves;
    DBManager dbManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artifact_save);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        recyclerView = findViewById(R.id.recycler_view_save);
        artifactSaves = new ArrayList<>();
        dbManager = new DBManager(this);
        artifactSaves = dbManager.getAllFavoriteArtifact();


        //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            this.layoutManager = new LinearLayoutManager(getApplicationContext());
       // }
        this.recyclerView.setLayoutManager(this.layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            this.adapter = new ArtifactSaveAdapter(this, artifactSaves);
        //}
        this.recyclerView.setAdapter(this.adapter);
        this.adapter.notifyDataSetChanged();
    }


}
