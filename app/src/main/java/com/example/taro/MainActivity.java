package com.example.taro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private TextView textViewAskQuestion;
    private TextView textViewСhooseСards;
    private TextView textViewInterpretation;
    private TextView textViewNumberOfCards;
    private EditText editTextTexQuestion;
    private Button buttonQuestion;
    private Button buttonReset;
    private Button buttonShuffle;
    private RecyclerView recyclerViewMyCards;
    private RecyclerView recyclerViewCardSelection;
    private RecyclerView recyclerViewCardInterpretation;

    private String url = "https://v-kosmose.com/gadanie-onlajn/znachenie-i-tolkovanie-kazhdoj-karty-taro/";
    private ArrayList<String> urls;
    private ArrayList<String> names;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewAskQuestion =findViewById(R.id.textViewAskQuestion);
        textViewСhooseСards =findViewById(R.id.textViewСhooseСards);
        textViewInterpretation =findViewById(R.id.textViewInterpretation);
        textViewNumberOfCards =findViewById(R.id.textViewNumberOfCards);
        editTextTexQuestion =findViewById(R.id.editTextTexQuestion);
        buttonQuestion =findViewById(R.id.buttonQuestion);
        buttonReset =findViewById(R.id.buttonReset);
        buttonShuffle =findViewById(R.id.buttonShuffle);
        recyclerViewMyCards =findViewById(R.id.recyclerViewMyCards);
        recyclerViewCardSelection =findViewById(R.id.recyclerViewCardSelection);
        recyclerViewCardInterpretation =findViewById(R.id.recyclerViewCardInterpretation);
        urls = new ArrayList<>();
        names = new ArrayList<>();

        getContent();

    }

    private void getContent(){
        DownloadContentTask task = new DownloadContentTask();
        try {
            String content = task.execute(url).get();
            String start = "<a style=\"color: #3366ff;\" href=\"https://v-kosmose.com/gadanie-onlajn/znachenie-karty-shut-v-taro/";
            String finish = "<p style=\"text-align: justify;\">Получить больше информации о Младших Арканах вы можете благодаря нашей статье";
            Pattern pattern = Pattern.compile(start+"(.*?)"+finish);
            Matcher matcher = pattern.matcher(content);
            String splitContent = "";
            while (matcher.find()){
                splitContent = matcher.group(1);
            }
            Pattern patternImg = Pattern.compile("\" src=\"(.*?)\" alt=\"");
            Pattern patternName = Pattern.compile("Карты Таро: (.*?)\" width=\"");
            Matcher matcherImg = patternImg.matcher(splitContent);
            Matcher matcherName = patternName.matcher(splitContent);
            while (matcherImg.find()){
                urls.add(matcherImg.group(1));
            }
            while (matcherName.find()){
                names.add(matcherName.group(1));
            }

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static class DownloadContentTask extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... strings) {
            URL url = null;
            HttpURLConnection urlConnection = null;
            StringBuilder result = new StringBuilder();
            try {
                url = new URL(strings[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = urlConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String line = reader.readLine();
                while (line != null){
                    result.append(line);
                    line = reader.readLine();
                }
                return result.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null){
                    urlConnection.disconnect();
                }
            }
            return null;
        }
    }

    private static class DownloadImageTask extends AsyncTask<String, Void, Bitmap>{
        @Override
        protected Bitmap doInBackground(String... strings) {
            URL url = null;
            HttpURLConnection urlConnection = null;
            StringBuilder result = new StringBuilder();
            try {
                url = new URL(strings[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = urlConnection.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                return bitmap;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null){
                    urlConnection.disconnect();
                }
            }
            return null;
        }
    }
}