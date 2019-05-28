package com.alercelik.ezanvakitleri.Model;


import android.graphics.Color;

public class Theme {

    public int getColorAsInt(int r, int g, int b) {
        int colorAsInt;
        String colorAsString;

        colorAsString = "#" + intToHexSubstring(r) + intToHexSubstring(g) + intToHexSubstring(b);
        colorAsInt = Color.parseColor(colorAsString);

        return colorAsInt;
    }

    public int getColorAsInt(int alpha, int r, int g, int b) {
        int colorAsInt;
        String colorAsString;

        colorAsString = "#" + intToHexSubstring(alpha) + intToHexSubstring(r) + intToHexSubstring(g) + intToHexSubstring(b);
        colorAsInt = Color.parseColor(colorAsString);

        return colorAsInt;
    }

    public int getColorAsHex(String r, String g, String b) {
        r = hexToHexSubstring(r);
        g = hexToHexSubstring(g);
        b = hexToHexSubstring(b);

        String colorAsString = "#" + r + g + b;
        int colorAsInt = Color.parseColor(colorAsString);

        return colorAsInt;
    }

    public int getColorAsHex(String alpha, String r, String g, String b) {
        alpha = hexToHexSubstring(alpha);
        r = hexToHexSubstring(r);
        g = hexToHexSubstring(g);
        b = hexToHexSubstring(b);

        String colorAsString = "#" + alpha + r + g + b;
        int colorAsInt = Color.parseColor(colorAsString);

        return colorAsInt;
    }



    private static String hexToHexSubstring(String hex) {
        String hexSubstring;

        if(hex.length() == 1)
            hexSubstring = "0" + hex;
        else
            hexSubstring = hex;

        return hexSubstring;
    }

    private static String intToHexSubstring(int number) {
        String hexSubstring;

        if (number < 16){
            hexSubstring = "0";
            hexSubstring += Integer.toHexString(number);
        }	else {
            hexSubstring = Integer.toHexString(number);
        }

        return hexSubstring;
    }

}
