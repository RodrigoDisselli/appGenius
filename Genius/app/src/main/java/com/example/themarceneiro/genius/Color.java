package com.example.themarceneiro.genius;

import java.util.Random;

public class Color {
    public static int click = 0;
    private int[] colorArray = {0,1,2,3,4,5};

    public static String stringColor = "";

    public void sendColor(){
        //transforma o array em string
        for(int i=0; i<6; i++){
            switch (colorArray[i]) {
                case 0:
                    stringColor += "R";
                    break;
                case 1:
                    stringColor += "B";
                    break;
                case 2:
                    stringColor += "G";
                    break;
                case 3:
                    stringColor += "Y";
                    break;
                default:
            }
        }

        System.out.print(" | String: " + stringColor);

    }

    public int[] makeArray(){
        Random rand = new Random();
        System.out.print("\nmake Array ");

        for(int i=0; i<6; i++){
            colorArray[i] = rand.nextInt(4);
            //gera o numero aleatorio e o insere no vetor
            System.out.print(colorArray[i]);
        }
        sendColor();
        //depois de gerar o array o transforma em string
        System.out.print(" \n");
        return colorArray;
    }

    public String clearString(){
        return stringColor = "";
    }

    public int[] getColorArray(){
        colorArray = makeArray();
        // recebe dentro do array o resultado do metodo que gera os valores aleatorios
        System.out.print(" \n");
        return colorArray;
    }

    public int colorClick(){
        //metodo que adiciona ao contador de clicks
        return click++;
    }

}
