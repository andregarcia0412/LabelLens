package com.example.labellens;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.io.InterruptedIOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttp;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ServiceApi {
    public FoodNutrients foodNutrients = new FoodNutrients();
    private String apiUrl = "https://leitor-rotulos-api.onrender.com/analyse_label";
    private String imagePath;

    ServiceApi(String imagePath){
        this.imagePath = imagePath;
    }

    public FoodNutrients getFoodNutrients() throws IOException, InterruptedException{
        OkHttpClient client = new OkHttpClient();
        File imageFile = new File(imagePath);

        RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart("image", imageFile.getName(), RequestBody.create(imageFile, MediaType.parse("image/png"))).build();
        Request request = new Request.Builder().url(apiUrl).post(requestBody).build();

        try(Response response = client.newCall(request).execute()){
            String responseBodyString = response.body().string();

            ObjectMapper mapper = new ObjectMapper();
            foodNutrients = mapper.readValue(responseBodyString, FoodNutrients.class);
        }
        return foodNutrients;
    }
}
