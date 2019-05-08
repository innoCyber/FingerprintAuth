package com.example.innocyber.fingerprintauth;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;
import android.widget.TextView;

@TargetApi(Build.VERSION_CODES.M)
public class FingerprintHandler extends FingerprintManager.AuthenticationCallback {

    private Context context;

    public  FingerprintHandler(Context context){

        this.context = context;
    }

    public void startAuth(FingerprintManager fingerprintManager, FingerprintManager.CryptoObject cryptoObject){
        CancellationSignal cancellationSignal = new CancellationSignal();
        fingerprintManager.authenticate(cryptoObject,cancellationSignal,0,this,null);
    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
        this.update("Authentication succeeded", false);
    }

    @Override
    public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
        this.update("Error " + helpString, false);
    }

    @Override
    public void onAuthenticationFailed() {
        this.update("Auth failed", false);
    }

    @Override
    public void onAuthenticationError(int errorCode, CharSequence errString) {
        this.update("There was an auth error" + errString , false);
    }

    private void update(String s, boolean b) {

        TextView secondTv = (TextView) ((Activity)context).findViewById(R.id.secongTv);

        ImageView fingerprintImage = (ImageView)((Activity)context).findViewById(R.id.fingerprintImage);

        secondTv.setText(s);

        if(b == false){

            secondTv.setTextColor(ContextCompat.getColor(context,R.color.red));

        }else {

            fingerprintImage.setImageResource(R.drawable.ic_done_black_24dp);

            secondTv.setTextColor(ContextCompat.getColor(context,R.color.blue));
        }
    }
}
