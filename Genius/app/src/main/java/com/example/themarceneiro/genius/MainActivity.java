package com.example.themarceneiro.genius;

import android.bluetooth.BluetoothAdapter;
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

    //declaração para o bluetooth
    BluetoothAdapter myBluetooth = null;
    int ENABLED_BLUETOOTH = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connectBtn = (Button) findViewById(R.id.button);
        startBtn = (Button) findViewById(R.id.button3);


        //area do codigo bluetooth
        myBluetooth = BluetoothAdapter.getDefaultAdapter();



        connectBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v){
                System.out.print("\n\nCONECTADO!!!!!!!!!");
                Toast.makeText(getApplicationContext(), "O dispositivo está conectado!", Toast.LENGTH_SHORT).show();
                //bluetooth sendo checado

                if(myBluetooth == null){
                    Toast.makeText(getApplicationContext(), "O dispositivo não possuí bluetooth!", Toast.LENGTH_SHORT).show();
                } else if(myBluetooth.isEnabled()){
                    startStage.changeState();
                    }
                    if(!myBluetooth.isEnabled()){
                    startStage.changeState();
                    //metodo que confirma o status ddo app para verificar se o botao conectar ja foi clicado
                    Intent enabledBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(enabledBtIntent, ENABLED_BLUETOOTH);
                }
            }
        });

        startBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v){
                if(startStage.getState()== 1){
                    Toast.makeText(getApplicationContext(), "Conecte o dispositivo para começar! ", Toast.LENGTH_SHORT).show();

                    if(myBluetooth.isEnabled()){
                        startStage.changeState();
                        //metodo que confirma o status ddo app para verificar se o botao conectar ja foi clicado
                        openMain();
                    }
                    //chama a tela do jogo
                }else{
                    Toast.makeText(getApplicationContext(), "Conecte o dispositivo para começar! ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void openMain() {
        //muda para pagina do jogo
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
    }
}