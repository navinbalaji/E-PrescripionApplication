package com.example.splashtexttospeech;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class SplashSecondActivity extends AppCompatActivity {

    private TextView tvShow;
        ImageView shareBtn;
        ImageView clearBtn;
        ImageView speaker;
        ImageView QRImage;
        TextToSpeech textToSpeech;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_splash_second);
            tvShow=(TextView) findViewById(R.id.tvShow);
            shareBtn=(ImageView) findViewById(R.id.scanQR);
            speaker=(ImageView) findViewById(R.id.speaker);
            clearBtn=(ImageView)findViewById(R.id.clearBtn);
            QRImage=(ImageView)findViewById(R.id.QRImage);
            shareBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent shareintent=new Intent();
                    shareintent.setAction(Intent.ACTION_SEND);
                    shareintent.putExtra(Intent.EXTRA_TEXT,tvShow.getText().toString());
                    shareintent.setType("text/plain");
                    startActivity(shareintent);
                }
            });


            textToSpeech =new TextToSpeech(getApplicationContext(),
                    new TextToSpeech.OnInitListener() {
                        @Override
                        public void onInit(int status) {
                            if(status ==TextToSpeech.SUCCESS)
                            {
                                int lang=textToSpeech.isLanguageAvailable(Locale.getDefault());
                            }
                        }
                    });

            speaker.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String str= tvShow.getText().toString();

                    int speech=textToSpeech.speak(str,TextToSpeech.QUEUE_FLUSH,null);
                }
            });

            clearBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tvShow.setText("");
                }
            });


            QRImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String data=tvShow.getText().toString();
                    if(data.length()>0) {
                        Intent intent = new Intent(SplashSecondActivity.this, StActivity.class);
                        intent.putExtra("key", data);
                        startActivity(intent);
                        finish();
                    }else
                    {
                        Toast.makeText(SplashSecondActivity.this,"Please provide any input",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        public void getTheSpeech(View view) {

            Intent intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, Locale.getDefault());

            if(intent.resolveActivity(getPackageManager())!=null) {
                startActivityForResult(intent, 10);
            }
            else
            {
                Toast.makeText(this,"YOUR DEVICE DOESN'T SUPPORT SPEECH FEATURE",Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            switch(requestCode)
            {
                case 10:
                    if(resultCode == RESULT_OK && data != null)
                    {
                        ArrayList<String> result= data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                        tvShow.setText(result.get(0));
                        tvShow.setMovementMethod(new ScrollingMovementMethod());
                    }
                    break;
            }
        }
    }


