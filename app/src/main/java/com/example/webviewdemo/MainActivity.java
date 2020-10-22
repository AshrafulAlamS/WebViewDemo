package com.example.webviewdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView=findViewById(R.id.webViewId);
        CustomWebViewClient client = new CustomWebViewClient(this);
        webView.setWebViewClient(client);

        webView.setWebViewClient(new WebViewClient(){
            //API Level >=24
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request){    //to solve 'go to external browser' problem
                boolean isLocalUrl = false;
                try {
                    String requstUrl = request.getUrl().toString();
                    URL givenUrl = new URL(requstUrl);
                    String host = givenUrl.getHost();

                    if (host.contains("github.com"))
                        isLocalUrl = true;
                }catch (MalformedURLException e){

                }

                if (isLocalUrl){
                    return super.shouldOverrideUrlLoading(view, request);
                    //return false;
                }else {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(request.getUrl().toString()));
                    startActivity(intent);
                    return true;
                }
            }
        });


        WebSettings webSettings = webView.getSettings();
        webView.loadUrl("https://github.com/AshrafulAlamS/");    //to load website
        webView.getSettings().setJavaScriptEnabled(true);     //It enable javaScript


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event){      //to prevent backbutton exit (without homePage)
        if (keyCode == KeyEvent.KEYCODE_BACK && this.webView.canGoBack()){  //it can go to previous page if available
            this.webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);     // so it can exit from home page




    }
}

class CustomWebViewClient extends WebViewClient {
    private Activity activity;

    public CustomWebViewClient(Activity activity) {
        this.activity = activity;
    };

    //API Level < 24
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url){
           // if(url != null && (url.startsWith("http://") || url.startsWith("https://"))){   // to access http & https both
                boolean isLocalUrl = false;
                try {
                    URL givenUrl = new URL(url);
                    String host = givenUrl.getHost();

                    if (host.contains("github.com"))
                        isLocalUrl = true;
                }catch (MalformedURLException e){

                }

                if (isLocalUrl){
                    return super.shouldOverrideUrlLoading(view, url);
                    //return false;
                }else {
                    view.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                    return true;
                }

            /*}else {
                return false;
            }*/
        }

    //API Level >=24
  /*
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        boolean isLocalUrl = false;
        try {
            String requstUrl = request.getUrl().toString();
            URL givenUrl = new URL(requstUrl);
            String host = givenUrl.getHost();

            if (host.contains("github.com"))
                isLocalUrl = true;
        } catch (MalformedURLException e) {

        }

        if (isLocalUrl) {
            return super.shouldOverrideUrlLoading(view, request);
            //return false;
        } else {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(request.getUrl().toString()));
            startActivity(intent);
            return true;
        }
    }
    //});
*/

}