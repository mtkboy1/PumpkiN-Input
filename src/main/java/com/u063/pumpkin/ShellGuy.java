package com.u063.pumpkin;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ShellGuy {
    public String createInput(String s){
        try {
            Process emu = Runtime.getRuntime().exec(s);

            BufferedReader bf = new BufferedReader(new InputStreamReader(emu.getInputStream()));
            String l;
            String str="";
            for(int i=0; i<5; i++){
                str+=bf.readLine();
                if((l=bf.readLine())==null){
                    break;
                }
            }
            return str;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
