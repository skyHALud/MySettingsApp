package com.ebay.codepath.mysettingsapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int ADD_URL_REQUEST_CODE = 1;

    private ListView lvUrls;
    private ArrayAdapter<String> urlAdapter;
    private List<String> urls = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // TODO Hack
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Thread.currentThread().sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                String msg;
                URL url = null;
                try {
                    url = new URL("http://www.google.com");
                    URLConnection conn = url.openConnection();
                    conn.connect();
                    InputStream in = conn.getInputStream();

                    String content = IOUtils.toString(in);

                    msg = content.substring(0, 20);
                } catch (IOException e) {
                    e.printStackTrace();
                    msg = "Error: " + e.getLocalizedMessage();
                }

                Snackbar.make(view, msg, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            }
        });

        lvUrls = (ListView) findViewById(R.id.lvUrls);
        urlAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, urls);
        lvUrls.setAdapter(urlAdapter);

        lvUrls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(urls.get(position)));
                startActivity(browserIntent);
            }
        });
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

        if(id == R.id.mnuAdd) {
            onAdd(item);
        }

        return super.onOptionsItemSelected(item);
    }

    public void onAdd(MenuItem item) {
        Toast.makeText(this, "Adding item", Toast.LENGTH_SHORT).show();

        Intent iAdd = new Intent(this, AddUrlActivity.class);
        startActivityForResult(iAdd, ADD_URL_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK && requestCode == ADD_URL_REQUEST_CODE) {
            String url = data.getStringExtra(AddUrlActivity.URL_EXTRA);

            Toast.makeText(this, "Adding URL: " + url, Toast.LENGTH_SHORT).show();

            urlAdapter.add(url);
        }
    }
}
