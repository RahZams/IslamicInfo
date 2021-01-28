package com.example.islamicinfoapp.src.main.java.com.view;

import android.webkit.WebView;
import android.webkit.WebViewClient;

class DuasViewClient extends WebViewClient {
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
    }
}
