package tech.gamedev.coronavirusscanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.Random;

public class ResultActivity extends AppCompatActivity {
    AdView adViewResult;
    TextView resultPercentage;
    int result;
    Random rnd = new Random();
    Button scanAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        adViewResult = findViewById(R.id.ad_result);
        scanAgain = findViewById(R.id.scan_again_btn);
        resultPercentage = findViewById(R.id.actual_result_percentage);
        setresult();


        MobileAds.initialize(this,"ca-app-pub-7226067785329539/9122195177");
        AdRequest adRequest = new AdRequest.Builder().build();
        adViewResult.loadAd(adRequest);



    }
    public void setresult(){
        result = rnd.nextInt(100) - 1;
       resultPercentage.setText("" + result+"%");
        if(result>50) {
            resultPercentage.setTextColor(Color.RED);
        }else{
            resultPercentage.setTextColor(Color.GREEN);
        }

        scanAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

    }
}
