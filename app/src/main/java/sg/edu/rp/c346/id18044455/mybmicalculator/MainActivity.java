package sg.edu.rp.c346.id18044455.mybmicalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    EditText wT;
    EditText hT;
    Button btnC;
    Button btnRD;
    TextView tvLCD;
    TextView tvLCB;
    TextView tvO;
    boolean clicked = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wT = findViewById(R.id.etW);
        wT.requestFocus();
        hT = findViewById(R.id.etH);
        btnC = findViewById(R.id.btnC);
        btnRD = findViewById(R.id.btnRD);
        tvLCD = findViewById(R.id.tvLCD);
        tvLCB = findViewById(R.id.tvBMI);
        tvO = findViewById(R.id.tV0);
        Calendar now = Calendar.getInstance();  //Create a Calendar object with current date and time
        final String datetime = now.get(Calendar.DAY_OF_MONTH) + "/" +
                (now.get(Calendar.MONTH)+1) + "/" +
                now.get(Calendar.YEAR) + " " +
                now.get(Calendar.HOUR_OF_DAY) + ":" +
                String.format("%02d", now.get(Calendar.MINUTE));


        btnC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Double weight = Double.parseDouble(wT.getText().toString());
                Double height = Double.parseDouble(hT.getText().toString());
                Double bmiV = weight / (height * height);
                String bmi = String.format("%.3f",   bmiV);
                tvLCD.setText(getString(R.string.LCD) + datetime);
                tvLCB.setText(getString(R.string.LCBMI) + bmi);

                if(bmiV < 18.5){
                    tvO.setText(R.string.Underweight);
                }
                else if(bmiV >= 18.5 && bmiV <= 24.9){
                    tvO.setText(R.string.Normal);
                }
                else if(bmiV >= 25 && bmiV <= 29.9){
                    tvO.setText(R.string.Overweight);
                }
                else{
                    tvO.setText(R.string.Obese);
                }
                wT.setText("");
                hT.setText("");
            }
        });

        btnRD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wT.setText("");
                hT.setText("");
                tvLCD.setText(R.string.LCD);
                tvLCB.setText(R.string.LCBMI);
                tvO.setText("");
                clicked = true;
            }
        });

    }//end of main method

    @Override
    protected void onPause() {
        super.onPause();

        String pD = tvLCD.getText().toString();
        String pBMI = tvLCB.getText().toString();
        SharedPreferences prefsD = PreferenceManager.getDefaultSharedPreferences(this);

        SharedPreferences.Editor prefsDEdit = prefsD.edit();

        prefsDEdit.putString("date", pD);
        prefsDEdit.putString("bmi", pBMI);

        prefsDEdit.commit();

        if(clicked == true){
            prefsDEdit.clear();
            prefsDEdit.commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefsD = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences prefsB = PreferenceManager.getDefaultSharedPreferences(this);

        String pDMSG = prefsD.getString("date", getString(R.string.LCD));
        String bMSG = prefsD.getString("bmi",  getString(R.string.LCBMI));
        tvLCD.setText(pDMSG);
        tvLCB.setText(bMSG);
    }
}//end of class