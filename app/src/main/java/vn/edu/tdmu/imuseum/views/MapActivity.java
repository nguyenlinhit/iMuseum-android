package vn.edu.tdmu.imuseum.views;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;


public class MapActivity extends AppCompatActivity {


    TextView btnPicture1;
    TextView btnPicture2;
    TextView btnPicture3;
    TextView btnPicture4;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        /*webView = findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.loadUrl("http://203.162.76.251:8035/Visit/Visit?imgId=1");
        webView.setWebViewClient(new WebViewClient());*/

        btnPicture1 = findViewById(R.id.btnPicture1);
        btnPicture2 = findViewById(R.id.btnPicture2);
        btnPicture3 = findViewById(R.id.btnPicture3);
        btnPicture4 = findViewById(R.id.btnPicture4);

        btnPicture1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MapActivity.this, DetailMapActivity.class);
                i.putExtra("link","anhchinhba.jpg");
                startActivity(i);
            }
        });

        btnPicture2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MapActivity.this, DetailMapActivity.class);
                i.putExtra("link","anhchinhhai.jpg");
                startActivity(i);
            }
        });

        btnPicture3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MapActivity.this, DetailMapActivity.class);
                i.putExtra("link","PANO_20171121_152626_0.jpg");
                startActivity(i);
            }
        });

        btnPicture4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MapActivity.this, DetailMapActivity.class);
                i.putExtra("link","anhchinhba.jpg");
                startActivity(i);
            }
        });


    }

/*    public void onBackPressed() {
        if(webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }*/

}
