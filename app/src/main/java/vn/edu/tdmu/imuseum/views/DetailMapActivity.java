package vn.edu.tdmu.imuseum.views;



import android.content.res.AssetManager;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.vr.sdk.widgets.pano.VrPanoramaView;

import java.io.InputStream;


public class DetailMapActivity extends AppCompatActivity {

    VrPanoramaView vrPanoramaView;
    WebView webView;
    String link;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_map);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        //webView = findViewById(R.id.webView);

        vrPanoramaView = findViewById(R.id.vrPanoramaView);

        Bundle bundle = getIntent().getExtras();
        link = bundle.getString("link");

        /*webView = findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl(link);
        webView.setWebViewClient(new WebViewClient());*/
        loadPhotoShephe(link);


    }

    public void loadPhotoShephe(String image){
        VrPanoramaView.Options options = new VrPanoramaView.Options();

        InputStream inputStream;

        AssetManager assetManager = getAssets();
        try {
            inputStream = assetManager.open(image);
            options.inputType = VrPanoramaView.Options.TYPE_MONO;
            vrPanoramaView.loadImageFromBitmap(BitmapFactory.decodeStream(inputStream), options);

            inputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

        public void onBackPressed() {
        if(webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        vrPanoramaView.pauseRendering();
    }

    @Override
    protected void onResume() {
        super.onResume();
        vrPanoramaView.resumeRendering();
    }

    @Override
    protected void onDestroy() {
        vrPanoramaView.shutdown();
        super.onDestroy();
    }

}
