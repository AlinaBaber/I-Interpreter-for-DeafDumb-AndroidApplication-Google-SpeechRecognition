package com.example.alinababer.i_interpreter_application_;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.speech.tts.TextToSpeech;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;
public class I_Interpreter_Main_Activity extends AppCompatActivity {
   TextToSpeech ttsobject;
    int result;
    EditText et;
    private EditText resultText;
    String text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_i__interpreter__main_);
        et = (EditText)findViewById(R.id.inputtext);
        resultText = (EditText) findViewById(R.id.resulttext);
        ttsobject = new TextToSpeech(I_Interpreter_Main_Activity.this,new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS){
                   result= ttsobject.setLanguage(Locale.getDefault());
                }
                else {
                    Toast.makeText(getApplicationContext(),"Feature is not supported in Your Device",Toast.LENGTH_SHORT).show();
                }
            }
        }
        );
    }
    public void onButtonClick(View v){
        if (v.getId() == R.id.mic){

            promptSpeechInput();
        }
    }
    public void promptSpeechInput(){
       Intent i = new  Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE,"en");
        i.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,this.getPackageName());
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.EXTRA_PREFER_OFFLINE);
        i.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS,3);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,Locale.getDefault());
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE,Locale.getDefault());
        i.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say Something!");
        i.putExtra(RecognizerIntent.);
        try{
          startActivityForResult(i,100);
        }
        catch(ActivityNotFoundException a){
            Toast.makeText(I_Interpreter_Main_Activity.this,"Feature is not supported in Your Device",Toast.LENGTH_LONG).show();
        }
    }
    public void onActivityResult(int request_code, int result_code,Intent i){
        super.onActivityResult(request_code,result_code,i);

        switch(request_code){
            case 100: if(result_code == RESULT_OK && i!= null){
                ArrayList<String> result = i.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                resultText.setText(result.get(0));
            }
                break;
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_i__interpreter__main_, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void doSomething(View v){
        switch (v.getId()){
            case R.id.speak:
                if(result== TextToSpeech.LANG_NOT_SUPPORTED || result== TextToSpeech.LANG_MISSING_DATA){
                    Toast.makeText(getApplicationContext(),"Feature is not supported in Your Device",Toast.LENGTH_SHORT).show();
                }else{
                    text = et.getText().toString();
                    ttsobject.speak(text,TextToSpeech.QUEUE_FLUSH,null);
                }
                break;
            case R.id.stopspeak:
                if(ttsobject != null) {
                    ttsobject.stop();
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(ttsobject != null) {
            ttsobject.stop();
            ttsobject.shutdown();
        }
    }
}
