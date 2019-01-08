package vn.edu.tdmu.imuseum.views;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


import vn.edu.tdmu.imuseum.manager.DBManager;
import vn.edu.tdmu.imuseum.model.ArtifactSave;

public class ArtifactDetailSaveActivity extends AppCompatActivity {

    ImageView imgClose;
    ImageView imgArtifact;
    TextView txtName;
    TextView txtTitle;
    TextView txtName2;
    TextView txtDes;

    ArtifactSave artifactSave;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artifact_detail_save);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        AddViews();
        AddControls();

        txtName.setText(artifactSave.getName());
        txtName2.setText(artifactSave.getName());
        txtTitle.setText(artifactSave.getTitle());
        txtDes.setText(artifactSave.getDescription());
        //Bitmap bitmap  = BitmapFactory.decodeByteArray(artifactSave.getImage(), 0, artifactSave.getImage().length);
        //imgArtifact.setImageBitmap(bitmap);
        try {
            File f=new File(artifactSave.getImage());
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
            imgArtifact.setImageBitmap(b);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    private void AddViews(){
        imgClose = findViewById(R.id.imgCloseSave);
        imgArtifact = findViewById(R.id.imageArtifactSave);
        txtName = findViewById(R.id.txtNameSave);
        txtTitle = findViewById(R.id.txtTitleSave);
        txtName2 = findViewById(R.id.txtName2Save);
        txtDes = findViewById(R.id.txtDesSave);
    }

    private void AddControls(){
        Bundle bundle = getIntent().getExtras();
        id =  bundle.getInt("idArtifact");
        final DBManager dbManager = new DBManager(this);
        artifactSave = dbManager.getOneArtifact(id);

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbManager.deleteArtifactSave(id);
                Log.e("EXIT", "Success!!!");
                Intent a = new Intent(ArtifactDetailSaveActivity.this,ArtifactSaveActivity.class);
                a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(a);
            }
        });
    }


}
