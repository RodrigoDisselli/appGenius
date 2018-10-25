package com.example.themarceneiro.genius;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.content.Intent;
import android.widget.Toast;
import android.view.View.OnClickListener;

import java.io.IOException;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private Button connectBtn;
    private Button startBtn;
    private Button enabledBluetooth;

    Start startStage = new Start();

    //declaração para o bluetooth
    BluetoothAdapter myBluetooth = null;
    BluetoothDevice myDevice = null;
    BluetoothSocket mySocket = null;

    UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");

    private static final int ENABLED_BLUETOOTH = 1;
    private static final int CONECTION_BLUETOOTH = 2;
    boolean connection = false;

    private static String MAC;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connectBtn = (Button) findViewById(R.id.button);
        startBtn = (Button) findViewById(R.id.button3);
        enabledBluetooth = (Button) findViewById(R.id.enabledBluetooth);

        //area do codigo bluetooth
        myBluetooth = BluetoothAdapter.getDefaultAdapter();

        turnOnBluetooth();

        connectBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v){
                System.out.print("\n\nCONECTADO!!!!!!!!!");
                Toast.makeText(getApplicationContext(), "O dispositivo está conectado!", Toast.LENGTH_SHORT).show();
                //bluetooth sendo checado
                enabledConection();
            }
        });


        enabledBluetooth.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v){
                turnOnBluetooth();
            }
        });

        startBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v){
                if(startStage.getState()== 1){

                    if(myBluetooth.isEnabled()){
                        startStage.changeState();
                        //metodo que confirma o status ddo app para verificar se o botao conectar ja foi clicado
                        openMain();
                    }else {
                        Toast.makeText(getApplicationContext(), "Conecte o dispositivo para começar! ", Toast.LENGTH_SHORT).show();
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

    //metodo para mostrar dispositivos disponiveis para conexão
    public void enabledConection(){
        if(connection){

            try {

                mySocket.close();
                connection = false;

            } catch (IOException erro) {

            }

        }else {
            Intent openListaDipositivos = new Intent(MainActivity.this, ListaDispositivos.class);
            startActivityForResult(openListaDipositivos, CONECTION_BLUETOOTH);
        }
    }

    public void turnOnBluetooth(){
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
            //abre a janela de ativação do bluetooth do aparelho
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode)
        {
            case CONECTION_BLUETOOTH:
                if (resultCode == Activity.RESULT_OK) {
                    MAC = data.getExtras().getString(ListaDispositivos.ENDERECO_MAC);
                    Toast.makeText(getApplicationContext(), String.valueOf(MAC), Toast.LENGTH_SHORT).show();
                    myDevice = myBluetooth.getRemoteDevice(MAC);

                    try {

                        mySocket = myDevice.createRfcommSocketToServiceRecord(MY_UUID);

                        mySocket.connect();

                        connection = true;

                        connectBtn.setText("Disconnect");

                        Toast.makeText(getApplicationContext(), "Você está conectado com " + MAC, Toast.LENGTH_SHORT).show();

                    } catch (IOException erro) {
                        Toast.makeText(getApplicationContext(), "Erro ao conectar", Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(getApplicationContext(), "Falha ao obter o MAC!", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}