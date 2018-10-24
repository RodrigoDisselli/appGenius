package com.example.themarceneiro.genius;

import android.widget.Toast;
import android.content.Intent;
import android.content.Context;

public class ColorBtn  {


    private int colorCode;
    Color color = new Color();
    private int[] codeList = color.getColorArray();
    public String ok = "";
    private static Context context;
    private Menu menuActivity;



    public ColorBtn (Menu main){
        this.menuActivity = main;
        //recebe o context da activity Menu nessa não Activity
    }

    public void getColorCode(int code){
        //verifica se o botao clickado eh o correto
        if(color.click <= color.stringColor.length() - 1){
            //verifica se o a string toda nao foi percorrida
            System.out.print(" Click:" + color.click + " | code:" + code);

            if (codeList[color.click] == code){
                //se o botao eh o correto
                System.out.print(" OK\n");
                //Toast.makeText(this.menuActivity, String.valueOf(color.click + 1), Toast.LENGTH_SHORT).show();
                //mostra menssagem contando o numero de clicks

                color.colorClick();
                if(color.click == color.stringColor.length()){
                    //se a string toda ja foi percorrida
                    Toast.makeText(this.menuActivity, "Parabéns", Toast.LENGTH_SHORT).show();
                    returnToMainActivity();
                    //retorna a pagina inicial
                }

            }else{
                //se o botao clicado eh incorreto
                Toast.makeText(this.menuActivity, ":(", Toast.LENGTH_SHORT).show();
                System.out.print(" ERROR");
                returnToMainActivity();
                //retorna a pagina inicial
            }

        }else{
            returnToMainActivity();
            //retorna a pagina inicial
        }


    }

    public void returnToMainActivity(){
        //retorna a pagina inicial
        color.stringColor = "";
        color.click = 0;
        Intent intent = new Intent(this.menuActivity, MainActivity.class);
        this.menuActivity.startActivity(intent);
    }

    //pega os valores que identificam o botao clickado

    public void redBtn(){
        getColorCode(0);
    }

    public void blueBtn(){
        getColorCode(1);
    }

    public void greenBtn(){
        getColorCode(2);
    }

    public void yellowBtn(){
        getColorCode(3);
    }
}
