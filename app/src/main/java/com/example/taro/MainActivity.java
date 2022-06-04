package com.example.taro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private TextView textViewAskQuestion;
    private TextView textViewСhooseСards;
    private TextView textViewNumberOfCards;
    private EditText editTextTexQuestion;
    private Button buttonQuestion;
    private Button buttonReset;
    private Button buttonShuffle;
    private RecyclerView recyclerViewMyCards;
    private RecyclerView recyclerViewCardSelection;

    private CardAdapter cardAdapter;

    private String url = "https://v-kosmose.com/gadanie-onlajn/znachenie-i-tolkovanie-kazhdoj-karty-taro/";

    private ArrayList<Card> cards = new ArrayList<>();

    private ArrayList<String> urls;
    private ArrayList<String> names;
    private ArrayList<String> urlCard;

    private ArrayList<Integer> numberCards;
    private ArrayList<Integer> numberMyCards;
    private ArrayList<Integer> selectedCards;

    private int i = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewAskQuestion =findViewById(R.id.textViewAskQuestion);
        textViewСhooseСards =findViewById(R.id.textViewСhooseСards);
        textViewNumberOfCards =findViewById(R.id.textViewNumberOfCards);
        editTextTexQuestion =findViewById(R.id.editTextTexQuestion);
        buttonQuestion =findViewById(R.id.buttonQuestion);
        buttonReset =findViewById(R.id.buttonReset);
        buttonShuffle =findViewById(R.id.buttonShuffle);
        recyclerViewMyCards =findViewById(R.id.recyclerViewMyCards);
        recyclerViewCardSelection =findViewById(R.id.recyclerViewCardSelection);
        urls = new ArrayList<>();
        names = new ArrayList<>();
        urlCard = new ArrayList<>();
        numberCards = new ArrayList<>();
        numberMyCards = new ArrayList<>();
        selectedCards = new ArrayList<>();
        cardAdapter = new CardAdapter();

        recyclerViewCardSelection.setLayoutManager(new GridLayoutManager(this, 4));
        recyclerViewMyCards.setLayoutManager(new StaggeredGridLayoutManager(3, RecyclerView.HORIZONTAL));

        recyclerViewCardSelection.setAdapter(cardAdapter);
        recyclerViewMyCards.setAdapter(cardAdapter);


        buttonQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = editTextTexQuestion.getText().toString();
                if (text != null && !text.isEmpty()) {
                    textViewAskQuestion.setVisibility(View.INVISIBLE);
                    editTextTexQuestion.setVisibility(View.INVISIBLE);
                    buttonQuestion.setVisibility(View.INVISIBLE);
                    playGame();
                }else {
                    Toast.makeText(MainActivity.this, R.string.your_cvestions, Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void getContent(){
        DownloadContentTask task = new DownloadContentTask();
        try {
            String content = task.execute(url).get();
            String start = "<a style=\"color: #3366ff;\" href=\"";
            String finish = "<p style=\"text-align: justify;\">Получить больше информации о Младших Арканах вы можете благодаря нашей статье";
            Pattern pattern = Pattern.compile(start+"(.*?)"+finish);
            Matcher matcher = pattern.matcher(content);
            String splitContent = "";
            while (matcher.find()){
                splitContent = matcher.group(1);
            }
            Pattern patternImg = Pattern.compile("\" src=\"(.*?)\" alt=\"");
            Pattern patternName = Pattern.compile("Карты Таро: (.*?)\" width=\"");
            Pattern patternUrl = Pattern.compile("https://v-kosmose.com/gadanie-onlajn/znachenie-karty(.*?)\"><img loading");
            Matcher matcherImg = patternImg.matcher(splitContent);
            Matcher matcherName = patternName.matcher(splitContent);
            Matcher matcherUrl = patternUrl.matcher(splitContent);
            while (matcherImg.find()){
                urls.add(matcherImg.group(1));

            }
            while (matcherName.find()){
                names.add(matcherName.group(1));

            }
            while (matcherUrl.find()){
                String kkk = "https://v-kosmose.com/gadanie-onlajn/znachenie-karty"+matcherUrl.group(1);
                urlCard.add(kkk);
                i++;
                numberCards.add(i);
                Card card = new Card(i, matcherName.group(1), matcherImg.group(1), kkk);
                cards.add(card);
            }


        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void playGame() {
        generateMyCards();

    }

    private void generateMyCards() {
        int myCard = 0;
        for(int i=0; i<5; i++){
           myCard = (int) (Math.random()*names.size());
           numberMyCards.add(myCard);
           numberCards.remove(myCard);
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