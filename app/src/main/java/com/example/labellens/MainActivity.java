package com.example.labellens;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileStore;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private Button button;
    private Button buttonGallery;
    private Uri photoUri;
    private File photoFile;
    private TextView resultText;
    private TextView pointsText;
    private TextView initialText;
    private File createImageFile() throws IOException{
        String fileName = "captured_image_" + System.currentTimeMillis();
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(fileName, ".jpg", storageDir);
    }

    private ActivityResultLauncher<Uri> takePictureLauncher =
            registerForActivityResult(new ActivityResultContracts.TakePicture(), result -> {
                if (result){
                    String imagePath = photoFile.getAbsolutePath();
                    resultText.setTextColor(Color.WHITE);
                    resultText.setText("Analisando...");
                    imageView.setImageURI(photoUri);
                    new Thread(() -> {
                        try{
                            ServiceApi serviceApi = new ServiceApi(imagePath);
                            FoodNutrients nutrients = serviceApi.getFoodNutrients();

                            runOnUiThread(() -> {
                               Log.d("API", nutrients.toString());
                               Double qualityScore = calculateQualityScore(nutrients);
                               String quality;
                               resultText.setText("Score de Qualidade do Alimento: " + String.format("%.1f", qualityScore) + "/100" + "\n");
                               if(qualityScore >= 75){
                                   quality = "A - Excelente";
                                   resultText.setTextColor(Color.parseColor("#228B22"));
                               } else if(qualityScore >= 50){
                                   quality = "B - Bom";
                                   resultText.setTextColor(Color.parseColor("#BA8E23"));
                               } else if(qualityScore >= 25){
                                   quality = "C - Regular";
                                   resultText.setTextColor(Color.parseColor("#BA8E23"));
                               } else{
                                   quality = "D - Consumir com moderação";
                                   resultText.setTextColor(Color.RED);
                               }
                               resultText.setText(resultText.getText() + "Classificação: " + quality + "\nCalorias por 100g: " + nutrients.getValorEnergetico().getPor100g());

                                if(parse100g(nutrients.getProteinas()) >= 4){
                                    pointsText.setText(pointsText.getText() + "\n" + "Boa quantidade de proteínas: " + nutrients.getProteinas().getPor100g());
                                }
                                if(parse100g(nutrients.getFibrasAlimentares()) >= 3){
                                    pointsText.setText(pointsText.getText() + "\n" + "Boa quantidade de fibras: " + nutrients.getFibrasAlimentares().getPor100g());
                                }
                                if(parse100g(nutrients.getCalcio()) >= 100){
                                    pointsText.setText(pointsText.getText() + "\n" + "Boa quantidade de cálcio: " + nutrients.getCalcio().getPor100g());
                                }
                            });
                        } catch (Exception e){
                            runOnUiThread(() -> {
                                resultText.setText("Erro ao analisar a imagem");
                                resultText.setTextColor(Color.BLACK);
                            });
                        }
                    }).start();
                }
            });

    private ActivityResultLauncher<String> galleryLauncher =
            registerForActivityResult(new ActivityResultContracts.GetContent(), uri -> {
                if (uri != null) {
                    resultText.setTextColor(Color.WHITE);
                    resultText.setText("Analisando...");
                    imageView.setImageURI(uri);
                    String imagePath = getRealPathFromUri(uri);
                    if (imagePath != null) {
                        new Thread(() -> {
                            try{
                                ServiceApi serviceApi = new ServiceApi(imagePath);
                                FoodNutrients nutrients = serviceApi.getFoodNutrients();

                                runOnUiThread(() -> {
                                    Log.d("API", nutrients.toString());
                                    Double qualityScore = calculateQualityScore(nutrients);
                                    String quality;
                                    resultText.setText("Score de Qualidade do Alimento: " + String.format("%.1f", qualityScore) + "/100" + "\n");
                                    if(qualityScore >= 75){
                                        quality = "A - Excelente";
                                        resultText.setTextColor(Color.parseColor("#228B22"));
                                    } else if(qualityScore >= 50){
                                        quality = "B - Bom";
                                        resultText.setTextColor(Color.parseColor("#BA8E23"));
                                    } else if(qualityScore >= 25){
                                        quality = "C - Regular";
                                        resultText.setTextColor(Color.parseColor("#BA8E23"));
                                    } else{
                                        quality = "D - Consumir com moderação";
                                        resultText.setTextColor(Color.RED);
                                    }
                                    resultText.setText(resultText.getText() + "Classificação: " + quality + "\nCalorias por 100g: " + nutrients.getValorEnergetico().getPor100g());

                                    if(parse100g(nutrients.getProteinas()) >= 4){
                                        pointsText.setText(pointsText.getText() + "\n" + "Boa quantidade de proteínas: " + nutrients.getProteinas().getPor100g());
                                    }
                                    if(parse100g(nutrients.getFibrasAlimentares()) >= 3){
                                        pointsText.setText(pointsText.getText() + "\n" + "Boa quantidade de fibras: " + nutrients.getFibrasAlimentares().getPor100g());
                                    }
                                    if(parse100g(nutrients.getCalcio()) >= 100){
                                        pointsText.setText(pointsText.getText() + "\n" + "Boa quantidade de cálcio: " + nutrients.getCalcio().getPor100g());
                                    }
                                });
                            } catch (Exception e){
                                runOnUiThread(() -> {
                                    resultText.setText("Erro ao analisar a imagem");
                                    resultText.setTextColor(Color.BLACK);
                                });
                            }
                        }).start();
                    }
                }
            });

    private void openCamera(){
        try {
            photoFile = createImageFile();
            photoUri = FileProvider.getUriForFile(
                    this,
                    getPackageName() + ".provider",
                    photoFile
            );
            takePictureLauncher.launch(photoUri);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.capturedImage);
        button = findViewById(R.id.openCamera);
        buttonGallery = findViewById(R.id.openGallery);
        pointsText = findViewById(R.id.pointsText);
        resultText = findViewById(R.id.resultsText);
        initialText = findViewById(R.id.initialText);
        pointsText.setTextColor(Color.parseColor("#228B22"));

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openCamera();
                initialText.setText("Pontos positivos:\n(100g)");
                pointsText.setText("");
            }
        });

        buttonGallery.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                galleryLauncher.launch("image/*");
                initialText.setText("Pontos positivos:\n(100g)");
                pointsText.setText("");
            }
        });
    }

    private double calculateQualityScore(FoodNutrients food){
        double scoreFibras = Math.min(parseVd(food.getFibrasAlimentares()), 100) * 3;
        double scoreCalcio = Math.min(parseVd(food.getCalcio()), 100);
        double scoreProteinas = Math.min(parseVd(food.getProteinas()), 100) * 2;

        double scoreValEnerg = Math.max(0, 100 - parseVd(food.getValorEnergetico())) * 2;
        double scoreAcucaresAdd = Math.max(0, 100 - parseVd(food.getAcucaresAdicionados())) * 3;
        double scoreAcucaresTot = Math.max(0, 100 - parseVd(food.getAcucaresTotais())) * 2;
        double scoreSodio = Math.max(0, 100 - parseVd(food.getSodio())) * 2;
        double scoreGordSat = Math.max(0, 100 - parseVd(food.getGordurasSaturadas())) * 2;
        double scoreGordTrans = Math.max(0, 100 - parseVd(food.getGordurasTrans())) * 3;

        return Math.round(((scoreFibras + scoreCalcio + scoreProteinas + scoreValEnerg + scoreAcucaresAdd + scoreAcucaresTot + scoreSodio + scoreGordSat + scoreGordTrans) / 19) * 100) /100;
    }

    private double parseVd(NutrientDetail nutrient){
        if(nutrient == null || nutrient.getVdPorcao() == null || nutrient.getVdPorcao().isEmpty()){
            return 0.0;
        }
        try {
            String vdString = nutrient.getVdPorcao().replaceAll("[^0-9,]", "").replace(",",".");
            if (!vdString.isEmpty()){
            return Double.parseDouble(vdString);
            }
            return 0.0;
        } catch (NumberFormatException e){
            return 0.0;
        }
    }

    private double parse100g(NutrientDetail nutrient){
        if(nutrient == null || nutrient.getPor100g() == null || nutrient.getPor100g().isEmpty()){
            return 0.0;
        }
        try{
            String string100g = nutrient.getPor100g().replaceAll("[^0-9,]", "").replace(",", ".");
            if(!string100g.isEmpty()){
                return Double.parseDouble(string100g);
            }
            return 0.0;
        } catch (NumberFormatException e){
            return 0.0;
        }
    }

    private String getRealPathFromUri(Uri contentUri){
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);

        if (cursor == null){
            return null;
        }

        int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String path = cursor.getString(columnIndex);

        cursor.close();
        return path;
    }

}
