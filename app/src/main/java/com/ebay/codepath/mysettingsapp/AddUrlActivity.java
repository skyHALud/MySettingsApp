package com.ebay.codepath.mysettingsapp;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddUrlActivity extends AppCompatActivity {

  public static final String URL_EXTRA = "URL";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add_url);
  }

  public void onAddUrl(View b) {
    Toast.makeText(this, "Adding URL", Toast.LENGTH_SHORT).show();
    Intent data = new Intent();
    EditText tfUrl = (EditText) findViewById(R.id.etUrl);
    data.putExtra(URL_EXTRA, tfUrl.getText().toString());
    setResult(RESULT_OK, data);
    finish();
  }
}
