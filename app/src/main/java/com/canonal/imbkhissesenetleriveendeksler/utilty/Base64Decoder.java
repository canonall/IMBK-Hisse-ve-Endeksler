package com.canonal.imbkhissesenetleriveendeksler.utilty;

import android.util.Base64;
import android.util.Log;

import java.io.UnsupportedEncodingException;

public class Base64Decoder {

    public static String decoder(String encodedText) {


        byte[] decodedByte = Base64.decode(encodedText, Base64.DEFAULT);
        String decodedText="";

        try {
            decodedText = new String(decodedByte, "UTF-8");

            Log.d("HANDSHAKE RESPOND", "DECODED: " + decodedText);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return decodedText;
    }
}
