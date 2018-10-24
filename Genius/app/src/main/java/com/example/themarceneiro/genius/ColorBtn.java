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
    }

    public void getColorCode(int code){

        if(color.click <=5){
            System.out.print(" Click:" + color.click + " | code:" + code);

            if (codeList[color.click] == code){
                System.out.print(" OK\n");
                Toast.makeText(this.menuActivity, String.valueOf(color.click + 1), Toast.LENGTH_SHORT).show();

                color.colorClick();
                if(color.click == 6){
                    returnToMainActivity();
                }

            }else{
                System.out.print(" ERROR");
                returnToMainActivity();

                //Toast.makeText(ColorBtn.this.context, "Error!", Toast.LENGTH_SHORT).show();
                //ColorBtn.this.context.startActivity(new Intent(ColorBtn.this.context,MainActivity.class));

            }

        }else{
            finishGame();
        }


    }

    public void returnToMainActivity(){
        color.stringColor = "";
        color.click = 0;
        Intent intent = new Intent(this.menuActivity, MainActivity.class);
        this.menuActivity.startActivity(intent);
    }

    public void finishGame(){
        System.out.print("\nThe game will Restart\n\n");
        color.stringColor = "";
        color.click = 0;
        color.makeArray();


    }

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
