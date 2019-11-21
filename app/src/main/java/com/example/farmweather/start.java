package com.example.farmweather;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;


public class start extends AppCompatActivity {
    EditText GetTown,Latitude,Longtitude;
    Switch VisibilityChanger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        final ImageView enter = findViewById(R.id.enter);
        GetTown =  findViewById(R.id.GetTown);
        Latitude = findViewById(R.id.Latitude);
        Longtitude = findViewById(R.id.Longitude);
        VisibilityChanger = findViewById(R.id.VisibilityChanger);


        VisibilityChanger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (VisibilityChanger.isChecked()) {
                    GetTown.setVisibility(View.GONE);
                    Latitude.setVisibility(View.VISIBLE);
                    Longtitude.setVisibility(View.VISIBLE);
                }
                else {
                    GetTown.setVisibility(View.VISIBLE);
                    Latitude.setVisibility(View.GONE);
                    Longtitude.setVisibility(View.GONE);
                }
            }
        });

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(GetTown.getText().toString().isEmpty() && VisibilityChanger.isChecked()==false){
                    new AlertDialog.Builder(start.this)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("Προσοχή!!")
                            .setMessage("Παρακαλώ καταχωρήστε πόλη")
                            .setNegativeButton("Ok",null)
                            .show();
                }else if(GetTown.getText().toString()!="" && VisibilityChanger.isChecked()==false) {
                    String GetGud = GetTown.getText().toString().trim();
                    Intent intent = new Intent(start.this, MainActivity.class);
                    intent.putExtra("Town", GetGud);
                    startActivity(intent);
                    GetTown.setText("");}

               else if((Latitude.getText().toString().isEmpty() || Longtitude.getText().toString().isEmpty()) && VisibilityChanger.isChecked()){
                    new AlertDialog.Builder(start.this)
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("Προσοχή!!")
                            .setMessage("Παρακαλώ καταχωρήστε συντεταγμένες")
                            .setNegativeButton("Ok",null)
                            .show();

                }else{
                    String GetLat = Latitude.getText().toString();
                    String GetLong = Longtitude.getText().toString();
                    Intent intent = new Intent(start.this , MainActivity.class);
                    intent.putExtra("Lat",GetLat);
                    intent.putExtra("Long",GetLong);
                    startActivity(intent);
                    Latitude.setText("");
                    Longtitude.setText("");
                }
            }

        });
    }
}
