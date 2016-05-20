package com.dreamanchor.dreamanchor;

import android.app.Dialog;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import data.Clipart;
import data.Payload;
import data.Svg;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import services.ClipartService;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private final String ENDPOINT = "https://openclipart.org/";
    private String imageIds = "";
    private List<Svg> imageUrls = null;
    private WebView wV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        imageIds = getRandomNumbers();
        getRandomClipArt(imageIds);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    public String getRandomNumbers() {
        StringBuilder sb = new StringBuilder();
        Random rand = new Random();
        for (int i = 0; i < 100; i++) {

            int n = (rand.nextInt(100000)-1);
            if(i==0){
                sb.append("?byids=");
            }
            if (i == 99) {
                sb.append(n);
                sb.append("&amount=100");
            } else {
                sb.append(String.format("%s,",n));
            }
        }
        return sb.toString();
    }

    public void getRandomClipArt(String imageIds) {
        imageUrls = new ArrayList<Svg>();
        ClipartService api = new RestAdapter.Builder().setEndpoint(ENDPOINT).build().create(ClipartService.class);
        try {
            api.getClipArt(imageIds, new Callback<Clipart>() {
                @Override
                public void success(Clipart cliparts, Response response) {
                    for(Payload payload : cliparts.payload){
                        imageUrls.add(payload.svg);
                    }

                    updateImageWebView(imageUrls);
                }

                @Override
                public void failure(RetrofitError error) {
                    String _error = error.getMessage();
                    imageUrls = new ArrayList<Svg>();
                    Svg svg = new Svg();
                    svg.url = "https://openclipart.org/download/248394/1463055000.svg";
                    imageUrls.add(svg);
                    updateImageWebView(imageUrls);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**********************************************
     *      Web View Methods
     *      Called to update
     */

    public void updateImageWebView(List<Svg> images) {
        wV = (WebView) findViewById(R.id.webView);
        StringBuilder html = new StringBuilder();
        html.append("<html>" +
                "       <head>" +
                "           <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">" +
                "           <style>" +
                "               body{width:99%;}.images{width: 90%; margin-left:auto; margin-right:auto;}</style></head><body><div class='images'>");
        for (int i = 0; i < images.size(); i++) {
            String imageTag = String.format("<img src='%s' alt='not loading' /><span> </span>", images.get(i).png_thumb);
            html.append(imageTag);
        }
        html.append("</div></body></html>");
        wV.invalidate();
        wV.setInitialScale(1);
        wV.getSettings().setLoadWithOverviewMode(true);
        wV.getSettings().setUseWideViewPort(true);
        wV.getSettings().setJavaScriptEnabled(true);
        wV.loadDataWithBaseURL("", html.toString(), "text/html", "utf-8", "");
    }

    /****************************************************
     * On click Methods
     *****************************************************/

    public void refresh(MenuItem item){
        String imageIds = getRandomNumbers();
        getRandomClipArt(imageIds);
    }

    public void newNote(MenuItem item){
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.new_entry_form);

        Button saveButton = (Button) dialog.findViewById(R.id.new_entry_save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void viewNotes(MenuItem item){

    }
}
