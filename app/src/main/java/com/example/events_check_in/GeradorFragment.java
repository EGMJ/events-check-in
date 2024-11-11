package com.example.events_check_in;

import static androidx.core.content.ContextCompat.getSystemService;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;


import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class GeradorFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflar o layout para o fragment
        View view = inflater.inflate(R.layout.fragment_gerador, container, false);

        // Button para gerar o QR code
        Button btnGenerate = view.findViewById(R.id.btnGenerate);
        // Campo de texto para entrada do texto do QR code
        EditText etText = view.findViewById(R.id.etText);
        // ImageView para mostrar o QR code gerado
        ImageView imageCode = view.findViewById(R.id.imageCode);

        btnGenerate.setOnClickListener(v -> {
            // Obtendo o texto do campo de entrada
            String myText = etText.getText().toString().trim();
            // Inicializando MultiFormatWriter para QR code
            MultiFormatWriter mWriter = new MultiFormatWriter();
            try {
                // Criando a BitMatrix para codificar o texto inserido e definindo altura e largura
                BitMatrix mMatrix = mWriter.encode(myText, BarcodeFormat.QR_CODE, 400, 400);
                BarcodeEncoder mEncoder = new BarcodeEncoder();
                Bitmap mBitmap = mEncoder.createBitmap(mMatrix); // Criando o bitmap do código
                imageCode.setImageBitmap(mBitmap); // Definindo o QR code gerado no ImageView

                // Para ocultar o teclado
                InputMethodManager manager = (InputMethodManager) requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                manager.hideSoftInputFromWindow(etText.getWindowToken(), 0);
            } catch (WriterException e) {
                e.printStackTrace();
            }
        });

        return view;
    }
}
