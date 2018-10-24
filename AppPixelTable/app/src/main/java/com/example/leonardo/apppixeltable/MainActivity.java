package com.example.leonardo.apppixeltable;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {


    Button btnConexao, btnLed1, btnLed2, btnLed3;

    private static final int SOLICITA_ATIVACAO = 1;
    private static final int SOLICITA_CONEXAO = 2;
    private static final int MESSAGE_READ = 3;

    ConnectedThread connectedThread;

    Handler mHandler;
    StringBuilder dadosBluetooth = new StringBuilder();

    BluetoothAdapter meuBluetoothAdapter = null;
    BluetoothDevice meuDevice = null;
    BluetoothSocket meuSocket = null;

    boolean conexao = false;

    private static String MAC = null;

    UUID MEU_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnConexao = (Button)findViewById(R.id.btnConexao);
        btnLed1 = (Button)findViewById(R.id.btnLed1);
        btnLed2 = (Button)findViewById(R.id.btnLed2);
        btnLed3 = (Button)findViewById(R.id.btnLed3);

        meuBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (meuBluetoothAdapter == null) {
            Toast.makeText(getApplicationContext(), "Seu dispositivo não possui bluetooth", Toast.LENGTH_LONG ).show();
        } else if (!meuBluetoothAdapter.isEnabled()) {
            Intent ativaBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(ativaBluetooth, SOLICITA_ATIVACAO);
        }
        btnConexao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (conexao){
                    try{
                        meuSocket.close();
                        conexao = false;
                        btnConexao.setText("Conectar");
                        Toast.makeText(getApplicationContext(), "Bluetooth foi desconectado ", Toast.LENGTH_LONG ).show();
                    }catch (IOException erro) {
                        Toast.makeText(getApplicationContext(), "Ocorreu um erro ", Toast.LENGTH_LONG ).show();
                    }
                }else {
                    Intent abreLista = new Intent(MainActivity.this, ListaDispositivos.class);
                    startActivityForResult(abreLista, SOLICITA_CONEXAO);
                }
            }
        });

        btnLed1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (conexao) {
                    connectedThread.enviar("R");
                }else {
                    Toast.makeText(getApplicationContext(), "Bluetooth não está conectado ", Toast.LENGTH_LONG ).show();
                }
            }
        });

        btnLed2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (conexao) {
                    connectedThread.enviar("Y");
                }else {
                    Toast.makeText(getApplicationContext(), "Bluetooth não está conectado ", Toast.LENGTH_LONG ).show();
                }
            }
        });

        btnLed3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (conexao) {
                    connectedThread.enviar("G");
                }else {
                    Toast.makeText(getApplicationContext(), "Bluetooth não está conectado ", Toast.LENGTH_LONG ).show();
                }
            }
        });

        mHandler = new Handler() {

            @Override
            public void handleMessage(Message msg) {

                if (msg.what == MESSAGE_READ){
                    String recebidos = (String) msg.obj;

                    dadosBluetooth.append(recebidos);

                    int fimInformacao = dadosBluetooth.indexOf("}");

                    if (fimInformacao >0) {
                        String dadosCompletos = dadosBluetooth.substring(0, fimInformacao);
                        int tamInformacao = dadosCompletos.length();

                        if (dadosBluetooth.charAt(0) == '{') {
                            String dadosFinais = dadosBluetooth.substring(1, tamInformacao);

                            Log.d("Recebidos", dadosFinais);

                            if (dadosFinais.contains("l1on")) {
                                btnLed1.setText("LED 1 LIGADO");
                                Log.d("LED1", "ligado");
                            }else if(dadosFinais.contains("l1of")) {
                                btnLed1.setText("LED 1 DESLIGADO");
                                Log.d("LED1", "desligado");
                            }

                            if (dadosFinais.contains("l2on")) {
                                btnLed2.setText("LED 2 LIGADO");
                            }else if(dadosFinais.contains("l2of")) {
                                btnLed2.setText("LED 2 DESLIGADO");
                            }

                            if (dadosFinais.contains("l3on")) {
                                btnLed3.setText("LED 3 LIGADO");
                            }else if(dadosFinais.contains("l3of")) {
                                btnLed3.setText("LED 3 DESLIGADO");
                            }
                        }

                        dadosBluetooth.delete(0, dadosBluetooth.length());
                    }
                }
            }
        };
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode)
        {
            case SOLICITA_ATIVACAO:
                if (resultCode == Activity.RESULT_OK) {
                    Toast.makeText(getApplicationContext(), "O bluetooth foi ativado", Toast.LENGTH_LONG ).show();
                }else {
                    Toast.makeText(getApplicationContext(), "O bluetooth não foi ativado, o app será encerrado", Toast.LENGTH_LONG ).show();
                    finish();
                }
                break;

            case SOLICITA_CONEXAO:
                if (resultCode == Activity.RESULT_OK){
                    MAC = data.getExtras().getString(ListaDispositivos.ENDERECO_MAC);

                   // Toast.makeText(getApplicationContext(), "MAC FINAL: " + MAC, Toast.LENGTH_LONG ).show();
                    meuDevice = meuBluetoothAdapter.getRemoteDevice(MAC);

                    try {
                        meuSocket = meuDevice.createRfcommSocketToServiceRecord(MEU_UUID);

                        meuSocket.connect();

                        conexao = true;

                        connectedThread = new ConnectedThread(meuSocket);
                        connectedThread.start();

                        btnConexao.setText("Desconectar");

                        Toast.makeText(getApplicationContext(), "Voce foi conectado com: " + MAC, Toast.LENGTH_LONG ).show();
                    }catch(IOException erro){

                        conexao = false;
                        Toast.makeText(getApplicationContext(), "ocorreu um erro: " + erro, Toast.LENGTH_LONG ).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Falha ao obter o MAC", Toast.LENGTH_LONG ).show();
                }
        }
    }

    private class ConnectedThread extends Thread {
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket) {
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            // Get the input and output streams, using temp objects because
            // member streams are final
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) { }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run() {
            byte[] buffer = new byte[1024];  // buffer store for the stream
            int bytes; // bytes returned from read()

            // Keep listening to the InputStream until an exception occurs
            while (true) {
                try {
                    // Read from the InputStream
                    bytes = mmInStream.read(buffer);

                    String dadosBt = new String(buffer, 0, bytes);

                    // Send the obtained bytes to the UI activity
                    //mHandler.obtainMessage(MESSAGE_READ, bytes, -1, dadosBt).sendToTarget();
                } catch (IOException e) {
                    break;
                }
            }
        }

        /* Call this from the main activity to send data to the remote device */
        public void enviar(String dadosEnviar) {
            byte[] msgBuffer = dadosEnviar.getBytes();
            try {
                mmOutStream.write(msgBuffer);
            } catch (IOException e) { }
        }
    }
}
