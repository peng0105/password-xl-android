package com.passwordxl;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private WebView webView;
    private ImageView loadingImageView;

    @Override
    @SuppressLint("SetJavaScriptEnabled")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                        View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        );

        webView = findViewById(R.id.webView);

//        webView.clearCache(true);

        loadingImageView = findViewById(R.id.loadingImageView);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                // 网页加载完成，隐藏 logo 并显示 WebView
                loadingImageView.setVisibility(View.GONE);
                webView.setVisibility(View.VISIBLE);
            }
        });

        // 允许 JavaScript 执行
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setAllowContentAccess(true);

        webView.addJavascriptInterface(new WebAppInterface(getFilesDir()), "androidAPI");


        // 加载 URL
//        webView.loadUrl("https://password-xl.cn/#/");
        webView.loadUrl("file:///android_asset/index.html");
    }

    @Override
    public void onBackPressed() {
        // 如果有上一个页面，则返回上一个页面
        if (webView != null && webView.canGoBack()) {
            webView.goBack(); // 如果 WebView 有历史记录，返回上一个页面
        } else {
            super.onBackPressed(); // 否则，执行默认的返回行为
        }
    }
}
