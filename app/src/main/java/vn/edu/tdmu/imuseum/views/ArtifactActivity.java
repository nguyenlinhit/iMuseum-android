package vn.edu.tdmu.imuseum.views;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import vn.edu.tdmu.imuseum.adapter.ArtifactAdapter;
import vn.edu.tdmu.imuseum.model.Artifact;
import vn.edu.tdmu.imuseum.ultils.Server;
import vn.edu.tdmu.imuseum.ultils.UnixEpochDateTypeAdapter;

public class ArtifactActivity extends AppCompatActivity {

    private ListView lvArtifact;
    private ArtifactAdapter artifactAdapter;
    public static final String SERVER_URL = Server.ARTIFACT;
    private List<Artifact> artifactList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artifact);

        lvArtifact = (ListView) findViewById(R.id.lvArtifact);

        ArtifactFetcher artifactFetcher = new ArtifactFetcher();
        artifactFetcher.execute(SERVER_URL);




    }

    private void handleArtifactList(List<Artifact> artifacts) {

        //this. =artifacts;
        /*runOnUiThread(new Runnable() {
            @Override
            public void run() {
                for (Artifact artifact : MainActivity.this.artifacts) {
                    Toast.makeText(MainActivity.this, artifact.getMedias().get(0).getMedia()+ "", Toast.LENGTH_LONG).show();
                }
            }
        });*/
    }

    private void failLoadingPosts() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ArtifactActivity.this, "Failed to load Artifacts. Have a look at LogCat.", Toast.LENGTH_LONG).show();
            }
        });
    }

    private class ArtifactFetcher extends AsyncTask<String, Artifact, List<Artifact>> {

        private static final String TAG = "ArtifactFetcher";


        @Override
        protected List<Artifact> doInBackground(String... strings) {

            String link = strings[0];
            List<Artifact> artifacts = new ArrayList<Artifact>();
            try {
                URL url = new URL(link);
                InputStreamReader reader = new InputStreamReader(url.openStream(),"UTF-8");

                GsonBuilder gsonBuilder = new GsonBuilder();
                Gson gson = gsonBuilder.serializeNulls().registerTypeAdapter(Date.class, UnixEpochDateTypeAdapter.getUnixEpochDateTypeAdapter()).create();

                artifacts = Arrays.asList(gson.fromJson(reader, Artifact[].class));

                handleArtifactList(artifacts);
            } catch (IOException e) {
                e.printStackTrace();
                failLoadingPosts();
            }
            return artifacts;
        }

        @Override
        protected void onPostExecute(List<Artifact> artifacts) {
            super.onPostExecute(artifacts);
            artifactList.clear();
            artifactList.addAll(artifacts);

        }
    }
}
