package vn.edu.tdmu.imuseum.views;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

import vn.edu.tdmu.imuseum.listener.TaskCompleted;
import vn.edu.tdmu.imuseum.manager.DBManager;
import vn.edu.tdmu.imuseum.model.Artifact;
import vn.edu.tdmu.imuseum.model.Language;
import vn.edu.tdmu.imuseum.model.Survey;
import vn.edu.tdmu.imuseum.ultils.RequestUtils;
import vn.edu.tdmu.imuseum.ultils.Server;
import vn.edu.tdmu.imuseum.ultils.SharePrefs;
import vn.edu.tdmu.imuseum.ultils.Utils;

public class ArtifactDetailActivity extends AppCompatActivity {

    CarouselView carouselView;
    ImageView imAudio;
    ImageView imgClose;
    ImageView imgFavorite;
    TextView txtName;
    TextView txtTitle;
    TextView txtName2;
    TextView txtDes;
    int idArtifact;
    int major;
    Artifact artifact;
    TextToSpeech textToSpeech;
    Bitmap img = null;

    DBManager dbManager;
    RatingBar ratingBar;
    String name1 = null;
    String title1 = null;
    String des1 = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artifact_detailhai);

        ControlView();
        Language language = SharePrefs.getInstance().get(SharePrefs.LANGUAGE, Language.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        dbManager = new DBManager(ArtifactDetailActivity.this);
        Bundle bundle = getIntent().getExtras();
        idArtifact = bundle.getInt("idArtifact");
        major = bundle.getInt("major");
        try {
            artifact = RequestUtils.getInstance().doGetArtifactInfoByBeaconIdDeTail(major, idArtifact);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        if (Objects.equals(language.getmCode(), "en")){
            txtName.setText(artifact.getNameArtifact());
            name1 = artifact.getNameArtifact();
        } else{
            new Task(new TaskCompleted() {
                @Override
                public void onTaskComplete(String result) {
                    txtName.setText(result);
                    name1 = result;
                }
            }, ArtifactDetailActivity.this).execute(artifact.getNameArtifact(),"en",language.getmCode());

        }

        if (Objects.equals(language.getmCode(), "en")){
            txtTitle.setText(artifact.getTitleArtifact());
            title1 = artifact.getTitleArtifact();
        } else{
            new Task(new TaskCompleted() {
                @Override
                public void onTaskComplete(String result) {
                    txtTitle.setText(result);
                    title1 = result;
                }
            }, ArtifactDetailActivity.this).execute(artifact.getTitleArtifact(),"en",language.getmCode());

        }

        if (Objects.equals(language.getmCode(), "en")){
            txtName2.setText(artifact.getNameArtifact());
        } else{
            new Task(new TaskCompleted() {
                @Override
                public void onTaskComplete(String result) {
                    txtName2.setText(result);
                }
            }, ArtifactDetailActivity.this).execute(artifact.getNameArtifact(),"en",language.getmCode());

        }
        if (Objects.equals(language.getmCode(), "en")){
            txtDes.setText(artifact.getDescription());
            des1 = artifact.getDescription();
        } else{
            new Task(new TaskCompleted() {
                @Override
                public void onTaskComplete(String result) {
                    txtDes.setText(result);
                    des1 = result;
                }
            }, ArtifactDetailActivity.this).execute(artifact.getDescription(),"en",language.getmCode());

        }
        carouselView.setPageCount(artifact.getMedias().size());
        Log.e("IMAGE", Server.LOCALHOST + "/" + artifact.getMedias().get(0).getMedia());
        carouselView.setImageListener(new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                Picasso.with(ArtifactDetailActivity.this).load(Server.LOCALHOST + "/" + artifact.getMedias().get(position).getMedia())
                        .placeholder(R.drawable.ic_home)
                        .error(R.drawable.ic_home)
                        .rotate(0)
                        .into(imageView);
            }
        });
        imAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textToSpeech = new TextToSpeech(ArtifactDetailActivity.this, new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int i) {
                        if (i != TextToSpeech.ERROR) {
                            textToSpeech.setLanguage(new Locale("vi_VN"));
                            textToSpeech.speak(txtDes.getText().toString(), TextToSpeech.QUEUE_FLUSH, null);
                        }
                    }
                });
            }
        });

        imgFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!dbManager.isExits(artifact.getIdArtifact())) {
                    ProgressDialog progressdialog = new ProgressDialog(ArtifactDetailActivity.this);
                    progressdialog.setMessage("Please Wait....");
                    img = getBitmapFromURL(Server.LOCALHOST + "/" + artifact.getMedias().get(0).getMedia());
                    int id = artifact.getIdArtifact();
                    String name = name1;
                    String title = title1;
                    String description = des1;

                    String[] parts = artifact.getMedias().get(0).getMedia().split("/");
                    int length = parts.length;

                    String image = saveToInternalStorage(img) + "/" + parts[length - 1];
                    Log.e("IMAGE_PATH", saveToInternalStorage(img) + "/" + parts[length - 1]);

                    dbManager.addArtifactSave(id, title, name, description, image);
                    imgFavorite.setVisibility(View.INVISIBLE);
                    progressdialog.dismiss();
                } else {
                    imgFavorite.setVisibility(View.INVISIBLE);
                }
            }
        });

        if (dbManager.isExits(artifact.getIdArtifact())) {
            imgFavorite.setVisibility(View.INVISIBLE);
        } else {
            imgFavorite.setVisibility(View.VISIBLE);
        }

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                DialogSurvey(v, artifact.getIdArtifact());
            }
        });

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("EXIT", "Success!!!");
                finish();
            }
        });
    }

    private void ControlView() {
        carouselView = findViewById(R.id.carouselView);
        imAudio = findViewById(R.id.imgAudio);
        imgClose = findViewById(R.id.imgClose);
        imgFavorite = findViewById(R.id.imgFavorite);
        txtName = findViewById(R.id.txtName);
        txtName2 = findViewById(R.id.txtName2);
        txtTitle = findViewById(R.id.txtTitle);
        txtDes = findViewById(R.id.txtDes);
        ratingBar = findViewById(R.id.ratingBar);
    }

    @Override
    protected void onPause() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onPause();
    }


    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            return BitmapFactory.decodeStream(input);
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }

    private String saveToInternalStorage(Bitmap bitmapImage) {
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);

        String[] parts = artifact.getMedias().get(0).getMedia().split("/");
        int length = parts.length;
        String image = parts[length - 1];
        // Create imageDir
        File mypath = new File(directory, image);
        Log.e("TAG", "Success");
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                assert fos != null;
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }

    public void DialogSurvey(final float rating, final int artifactID) {

        final Dialog dialogsurvey = new Dialog(ArtifactDetailActivity.this);
        dialogsurvey.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialogsurvey.setContentView(R.layout.dialog_feedback);
        final EditText edt_comment = dialogsurvey.findViewById(R.id.edt_comment);
        Button btn_submit = dialogsurvey.findViewById(R.id.btn_submit);
        Button btn_cencal = dialogsurvey.findViewById(R.id.btn_cancel);
        ImageView img_love = dialogsurvey.findViewById(R.id.img_love);
        ImageView img_normal = dialogsurvey.findViewById(R.id.img_nomal);
        ImageView img_bad = dialogsurvey.findViewById(R.id.img_bad);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String coment_user = edt_comment.getText().toString().trim();
                if (coment_user.isEmpty()) {
                    Toast.makeText(ArtifactDetailActivity.this, "Comment is not empty...!!!", Toast.LENGTH_SHORT).show();
                } else {
                    Survey survey = new Survey(artifactID, coment_user, (int) rating);
                    Gson survetgs = new Gson();
                    String survetjson = survetgs.toJson(survey);
                    //Toast.makeText(ArtifactDetailActivity.this, survetjson, Toast.LENGTH_SHORT).show();
                    new RantingActionView().execute(Server.surveyAPI_POST, survetjson);
                    dialogsurvey.cancel();
                    //new UpdateRantingActionview().execute();
                }
            }
        });

        img_love.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ArtifactDetailActivity.this, "Thank you...", Toast.LENGTH_LONG).show();
            }
        });

        img_normal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ArtifactDetailActivity.this, "Thank you...", Toast.LENGTH_LONG).show();
            }
        });
        img_bad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ArtifactDetailActivity.this, "Thank you...", Toast.LENGTH_LONG).show();
            }
        });
        btn_cencal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogsurvey.dismiss();
            }
        });
        dialogsurvey.show();
    }

    //Handle RantingBar POST Rank from USER
    private class RantingActionView extends AsyncTask<String, Void, Boolean> {
        ProgressDialog dialog = new ProgressDialog(ArtifactDetailActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("Post ....");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected Boolean doInBackground(String... params) {
            boolean result = false;

            try {
                URL url = new URL(params[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(6000);
                connection.setReadTimeout(6000);
                connection.setDoOutput(true);
                connection.setDoInput(true);
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");
                //handle json format to byte
                String jsonRank = params[1];
                OutputStream os = connection.getOutputStream();
                os.write(jsonRank.getBytes());
                os.close();

                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    result = true;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            dialog.dismiss();
            if (aBoolean) {
                Toast.makeText(ArtifactDetailActivity.this, "Thank You !!!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(ArtifactDetailActivity.this, "Have some error...!!!", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private class Task extends AsyncTask<String, Void,String> {
        private TaskCompleted callback;
        private ProgressDialog dialog;

        private Task(TaskCompleted callback, Context context) {
            this.callback = callback;
            dialog = new ProgressDialog(context);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            this.dialog.setMessage("Loading...");
            this.dialog.setCancelable(false);
            this.dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            return Utils.translate(strings[0], strings[1], strings[2]);
        }

        @Override
        protected void onPostExecute(String s) {
            callback.onTaskComplete(s);
            this.dialog.dismiss();
        }
    }

}
