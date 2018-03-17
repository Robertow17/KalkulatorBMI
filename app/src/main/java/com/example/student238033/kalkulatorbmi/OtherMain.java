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


public class OtherMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_main);

        android.support.v7.widget.Toolbar myToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        SharedPreferences settings = getApplicationContext().getSharedPreferences("PREFS_NAME", 0);
        String waga = settings.getString("saved_weight_ibs", "");
        String stopa = settings .getString("saved_height_feet", "");
        String cal = settings .getString("saved_height_inches", "");
        EditText weight = (EditText) findViewById(R.id.wpisz_mase);
        EditText heightf = (EditText) findViewById(R.id.editText4);
        EditText heighti = (EditText) findViewById(R.id.wpisz_cale);
        if(!waga.isEmpty())
        {
            weight.setText(waga);
        }
        if(!stopa.isEmpty())
        {
            heightf.setText(stopa);
        }
        if(!cal.isEmpty())
        {
            heighti.setText(cal);
        }


        Switch s = (Switch) findViewById(R.id.switch1);
        s.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(!b) {
                    Intent i = new Intent(OtherMain.this, MainActivity.class);
                    startActivity(i);

                    Toast.makeText(OtherMain.this, "Zmieniono jednostki!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        final Button button = (Button) findViewById(R.id.przycisk_licz);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText ibs = (EditText) findViewById(R.id.wpisz_mase);
                EditText ft = (EditText) findViewById(R.id.editText4);
                EditText ins = (EditText) findViewById(R.id.wpisz_cale);

                double mass = -0.1;
                try {
                    double weight = Double.parseDouble(ibs.getText().toString());
                    mass=convertToKg(weight);
                }
                catch(Exception e){}

                double tall=-0.1;
                int feets=-1;
                int inches=-1;
                try {
                    feets = Integer.parseInt(ft.getText().toString());
                }
                catch(Exception e){}

                try {
                    inches = Integer.parseInt(ins.getText().toString());
                }
                catch(Exception e){}

                try
                {
                    tall=convertToM(feets,inches);
                }
                catch(IllegalArgumentException e){}
                if(mass<0 || mass>350 || tall<0 || tall>3 || inches<0 || inches>=12 || feets<0 || feets>10 )
                {
                    Intent i = new Intent(OtherMain.this, Error.class);
                    startActivity(i);
                }
                else
                {
                    double result = countBMI(mass,tall);

                    if(result<18.5)
                    {
                        Intent i=new Intent(OtherMain.this, Underweight.class).putExtra("result", String.format("%.3f", result));
                        startActivity(i);
                    }
                    else if(result>18.5 && result<25)
                    {
                        Intent i=new Intent(OtherMain.this, Correct.class).putExtra("result", String.format("%.3f", result));
                        startActivity(i);
                    }
                    else
                    {
                        Intent i=new Intent(OtherMain.this, Overweight.class).putExtra("result", String.format("%.3f", result));
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
            EditText heightf = (EditText) findViewById(R.id.editText4);
            EditText heighti = (EditText) findViewById(R.id.wpisz_cale);

            SharedPreferences settings = getApplicationContext().getSharedPreferences("PREFS_NAME", 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("saved_weight_ibs", weight.getText().toString());
            editor.putString("saved_height_feet", heightf.getText().toString());
            editor.putString("saved_height_inches", heighti.getText().toString());
            editor.apply();

            Toast.makeText(OtherMain.this, "Dane zostały zapisane!", Toast.LENGTH_SHORT).show();


        }
        if(item.getItemId()==R.id.action_about_me)
        {
            Intent i = new Intent(OtherMain.this, AboutMe.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    public double squared(double height)
    {
        double result = height*height;
        return result;
    }
    public double countBMI(double mass, double height) throws IllegalArgumentException
    {
        if(mass<=0 || height<=0) throw new IllegalArgumentException();
        else {
            double result = mass / squared(height);
            return result;
        }
    }

    public double convertToKg(double mass) throws IllegalArgumentException
    {
        if(mass<=0) throw new IllegalArgumentException();
        else {
            double result = mass * 0.45359;
            return result;
        }
    }
    public double convertToM(int feets, int inches) throws IllegalArgumentException
    {
        if(feets<=0 || inches<0) throw new IllegalArgumentException();
        else
        {
            double result = feets * 0.3048 + inches * 0.0254;
            return result;
        }
    }
}
