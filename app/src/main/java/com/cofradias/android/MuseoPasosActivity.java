package com.cofradias.android;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import static android.widget.Toast.LENGTH_SHORT;

/**
 * Created by alaria on 16/06/2016.
 */
public class MuseoPasosActivity extends AppCompatActivity {

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.museo_pasos_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_museo);
        toolbar.setLogo(R.drawable.logo);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mWebView = (WebView) findViewById(R.id.webView);

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.getSettings().setDisplayZoomControls(false);

        mWebView.setWebViewClient(new MyWebViewClient());
        mWebView.loadUrl("http://www.museodepasosbilbao.com");

    }


    @Override
    public void onBackPressed() {

        if (mWebView.canGoBack())
            mWebView.goBack();
        else
            super.onBackPressed();
    }


    private class MyWebViewClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);

            Toast.makeText(getApplicationContext(),
                    "Cargando la página...", LENGTH_SHORT).show();
        }
    }
}
