package com.example.farmweather;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;



public class start extends AppCompatActivity {
    EditText GetTown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        final ImageView enter = findViewById(R.id.enter);
        GetTown = (EditText) findViewById(R.id.GetTown);


        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(GetTown.getText().toString().equals("")){
                    new AlertDialog.Builder(start.this)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("Προσοχή!!")
                            .setMessage("Παρακαλώ καταχωρήστε πόλη")
                            .setNegativeButton("Ok",null)
                            .show();
                }else {
                    String GetGud = GetTown.getText().toString().trim();
                    Intent intent = new Intent(start.this, MainActivity.class);
                    intent.putExtra("Town", GetGud);
                    startActivity(intent);
                    GetTown.setText("");}
            }

        });
    }
}
