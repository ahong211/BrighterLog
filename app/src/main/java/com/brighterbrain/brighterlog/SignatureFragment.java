package com.brighterbrain.brighterlog;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.github.gcacace.signaturepad.views.SignaturePad;

public class SignatureFragment extends Fragment {
    private SignaturePad signaturePad;
    private Button clearSignature, saveSignature;

    public SignatureFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signature, container, false);

        signaturePad = (SignaturePad) view.findViewById(R.id.signaturePad);

        signaturePad.setOnSignedListener(new SignaturePad.OnSignedListener() {
            @Override
            public void onStartSigning() {
                Toast.makeText(getContext(), "Start Signing...", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSigned() {
                clearSignature.setEnabled(true);
                saveSignature.setEnabled(true);
            }

            @Override
            public void onClear() {
                clearSignature.setEnabled(false);
                saveSignature.setEnabled(false);
            }
        });

        clearSignature = (Button) view.findViewById(R.id.clearSignature);
        saveSignature = (Button) view.findViewById(R.id.saveSignature);

        clearSignature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signaturePad.clear();
            }
        });

        saveSignature.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap signatureBitmap = signaturePad.getSignatureBitmap();
                //saveBitmapImage(signatureBitmap);
            }
        });

        return view;
    }

    private void saveBitmapImage(Bitmap signatureBMap) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("data/0/", String.valueOf(signatureBMap));
        editor.commit();
    }

}
