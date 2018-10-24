package com.example.themarceneiro.genius;

        import android.content.Context;
        import android.content.Intent;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.widget.Button;
        import android.view.View;
        import android.widget.Toast;

public class Menu extends AppCompatActivity {
    ColorBtn botao = new ColorBtn(this);
    private Button redBtn;
    private Button blueBtn;
    private Button greenBtn;
    private Button yellowBtn;
    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        redBtn = (Button) findViewById(R.id.button4);
        blueBtn = (Button) findViewById(R.id.button2);
        greenBtn = (Button) findViewById(R.id.button5);
        yellowBtn = (Button) findViewById(R.id.button6);

        //envia a ação dde click dos botoes do jogo

        redBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v){
                botao.redBtn();
            }
        });

        blueBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v){
                botao.blueBtn();
            }
        });

        greenBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v){
                botao.greenBtn();
            }
        });

        yellowBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View v){
                botao.yellowBtn();
            }
        });
    }
}
