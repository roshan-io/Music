package com.hakpi.app;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String PREFS = "pi_prefs";
    private static final String KEY_X = "last_x";
    private static final String KEY_PORT = "last_port";

    private EditText editX, editPort;
    private Button btnConnect;
    private LinearLayout controlsContainer;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Fullscreen remove status + nav bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        controlsContainer = findViewById(R.id.controls_container);
        editX = findViewById(R.id.edit_x);
        editPort = findViewById(R.id.edit_port);
        btnConnect = findViewById(R.id.btn_connect);
        webView = findViewById(R.id.webview);

        SharedPreferences prefs = getSharedPreferences(PREFS, MODE_PRIVATE);
        editX.setText(prefs.getString(KEY_X, ""));
        editPort.setText(prefs.getString(KEY_PORT, ""));

        webView.setWebViewClient(new WebViewClient());
        WebSettings ws = webView.getSettings();
        ws.setJavaScriptEnabled(true);
        ws.setDomStorageEnabled(true);

        btnConnect.setOnClickListener(v -> {
            String x = editX.getText().toString().trim();
            String port = editPort.getText().toString().trim();

            if (x.isEmpty()) { editX.setError("Required"); return; }
            if (port.isEmpty()) { editPort.setError("Required"); return; }

            prefs.edit().putString(KEY_X, x).putString(KEY_PORT, port).apply();

            String url = "http://192.168." + x + ".104:" + port + "/";
            openWeb(url);
        });
    }

    private void openWeb(String url) {
        controlsContainer.setVisibility(View.GONE);
        webView.setVisibility(View.VISIBLE);
        enterImmersiveMode();
        webView.loadUrl(url);
    }

    private void enterImmersiveMode() {
        View decor = getWindow().getDecorView();
        decor.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                View.SYSTEM_UI_FLAG_FULLSCREEN
        );
    }

    @Override
    public void onBackPressed() {
        if (webView.getVisibility() == View.VISIBLE) {
            webView.setVisibility(View.GONE);
            controlsContainer.setVisibility(View.VISIBLE);
        } else {
            super.onBackPressed();
        }
    }
}
