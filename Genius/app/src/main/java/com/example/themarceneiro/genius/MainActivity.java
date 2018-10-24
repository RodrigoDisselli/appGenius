package com.example.themarceneiro.genius;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.content.Intent;
import android.widget.Toast;
import android.view.View.OnClickListener;

public class MainActivity extends AppCompatActivity {

    private Button connectBtn;
    private Button startBtn;

    Start startStage = new Start();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connectBtn = (Button) findViewById(R.id.button);
        startBtn = (Button) findViewById(R.id.button3);

        connectBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v){
                System.out.print("\n\nCONECTADO!!!!!!!!!");
                Toast.makeText(getApplicationContext(), "O dispositivo está conectado!", Toast.LENGTH_SHORT).show();
                startStage.changeState();
            }
        });

        startBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v){
                if(startStage.getState()== 1){
                    openMain();
                }else{
                    Toast.makeText(getApplicationContext(), "Conecte o dispositivo para começar! ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void openMain() {
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
    }
}