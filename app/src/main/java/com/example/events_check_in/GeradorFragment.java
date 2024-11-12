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

                // Definindo largura e altura do Bitmap
                int width = mMatrix.getWidth();
                int height = mMatrix.getHeight();
                Bitmap mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

                // Cores para o fundo e o QR code
                int colorBackground = 0xFF050038; // Azul claro
                int colorQRCode = 0xFF000000; // Preto

                // Percorrendo os pixels do BitMatrix e aplicando as cores
                for (int x = 0; x < width; x++) {
                    for (int y = 0; y < height; y++) {
                        mBitmap.setPixel(x, y, mMatrix.get(x, y) ? colorQRCode : colorBackground);
                    }
                }

                // Definindo o QR code gerado no ImageView
                imageCode.setImageBitmap(mBitmap);

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
