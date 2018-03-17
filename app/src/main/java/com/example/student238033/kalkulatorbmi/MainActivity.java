package com.example.student238033.kalkulatorbmi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import static android.app.PendingIntent.getActivity;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        android.support.v7.widget.Toolbar myToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        // Get from the SharedPreferences

        SharedPreferences settings = getApplicationContext().getSharedPreferences("PREFS_NAME", 0);
        String waga = settings.getString("saved_weight", "");
        String wzrost = settings .getString("saved_height", "");
        EditText weight = (EditText) findViewById(R.id.wpisz_mase);
        EditText height = (EditText) findViewById(R.id.wpisz_wzrost);
        if(!waga.isEmpty())
        {
            weight.setText(waga);
        }
        if(!wzrost.isEmpty())
        {
            height.setText(wzrost);
        }

        Switch s = (Switch) findViewById(R.id.switch1);
        s.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    Intent i = new Intent(MainActivity.this, OtherMain.class);
                    startActivity(i);

                    Toast.makeText(MainActivity.this, "Zmieniono jednostki!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        final Button button = (Button) findViewById(R.id.przycisk_licz);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText weight = (EditText) findViewById(R.id.wpisz_mase);
                EditText height = (EditText) findViewById(R.id.wpisz_wzrost);

                double mass = -0.1;
                try {
                    mass = Double.parseDouble(weight.getText().toString());
                } catch (Exception e) {
                }

                double tall = -0.1;
                try {
                    tall = Double.parseDouble(height.getText().toString());
                } catch (Exception e) {
                }

                if (mass < 0 || mass > 350 || tall < 0 || tall > 3) {
                    Intent i = new Intent(MainActivity.this, Error.class);
                    startActivity(i);
                } else {
                    double result = countBMI(mass, tall);

                    if (result < 18.5) {
                        Intent i = new Intent(MainActivity.this, Underweight.class).putExtra("result", String.format("%.3f", result));
                        startActivity(i);
                    } else if (result > 18.5 && result < 25) {
                        Intent i = new Intent(MainActivity.this, Correct.class).putExtra("result", String.format("%.3f", result));
                        startActivity(i);
                    } else {
                        Intent i = new Intent(MainActivity.this, Overweight.class).putExtra("result", String.format("%.3f", result));
                        startActivity(i);
                    }
                }


            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater myInflater = getMenuInflater();
        myInflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.action_save)
        {
            EditText weight = (EditText) findViewById(R.id.wpisz_mase);
            EditText height = (EditText) findViewById(R.id.wpisz_wzrost);

            SharedPreferences settings = getApplicationContext().getSharedPreferences("PREFS_NAME", 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("saved_weight", weight.getText().toString());
            editor.putString("saved_height", height.getText().toString());
            editor.apply();

            Toast.makeText(MainActivity.this, "Dane zostały zapisane!", Toast.LENGTH_SHORT).show();
        }
        if(item.getItemId()==R.id.action_about_me)
        {
            Intent i = new Intent(MainActivity.this, AboutMe.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    public double squared(double height)
    {
        return height*height;
    }
    public double countBMI(double mass, double height) throws IllegalArgumentException
    {
        if(mass<=0 || height<=0) throw new IllegalArgumentException();
        else {
            double result = mass / squared(height);
            return result;
        }
    }
}