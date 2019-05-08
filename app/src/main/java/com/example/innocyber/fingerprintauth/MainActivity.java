package com.example.innocyber.fingerprintauth;

import android.Manifest;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.biometrics.BiometricPrompt;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView firstTv, secondTv;
    private ImageView fingerprintImage;

    private FingerprintManager fingerprintManager;
    private KeyguardManager keyguardManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstTv = (TextView) findViewById(R.id.firstTv);
        secondTv = (TextView) findViewById(R.id.secongTv);
        fingerprintImage = (ImageView) findViewById(R.id.fingerprintImage);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            fingerprintManager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);
            keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);

            if (!fingerprintManager.isHardwareDetected()) {
                secondTv.setText("Fingerprint not detected");

            } else if (ContextCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {

                secondTv.setText("Permission not granted to use fingerprint");
            } else if (!keyguardManager.isKeyguardSecure()) {
                secondTv.setText("Add fingerprint to your device");
            } else if (!fingerprintManager.hasEnrolledFingerprints()){
                secondTv.setText("You should add atleast one fingerprint to use this feature");
            } else {
                secondTv.setText("Place your finger on the scanner to add fingerprint");

                FingerprintHandler fingerprintHandler = new FingerprintHandler(this);
                fingerprintHandler.startAuth(fingerprintManager,null);
            }

        }


    }
}
