package com.example.tarogame;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

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

public class AddContent extends AppCompatActivity {
    private  ArrayList<Card> cardsFromDB;

    private ArrayList<Card> cards;
    private ArrayList<String> urls;
    private ArrayList<String> names;
    private ArrayList<String> urlCard;
    private ArrayList<String> strobPr;
    private ArrayList<String> strobPer;

    private CardsDataBase dataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_content);
        cardsFromDB = new ArrayList<>();

        cards = new ArrayList<>();
        names = new ArrayList<>();
        urls = new ArrayList<>();
        urlCard = new ArrayList<>();
        strobPr = new ArrayList<>();
        strobPer = new ArrayList<>();
        dataBase = CardsDataBase.getInstance(this);

//        getContent();
//       getContentCards();
//        Log.i("jfjmmvmfj", strobPr.get(8));
//
//        for (int i = 0; i<urls.size(); i++){
//            Card card = new Card(i, names.get(i), urls.get(i), urlCard.get(i), strobPr.get(i), strobPer.get(i));
//            cards.add(card);
//        }
//        dataBase.cardDao().deleteAllCards();
//        for (Card card:cards) {
//            dataBase.cardDao().insertCard(card);
//        }
//List<Card> cardsDB = dataBase.cardDao().getAllCards();
//                cardsFromDB.clear();
//                cardsFromDB.addAll(cardsDB);
//        String jjjj = ""+cardsFromDB.size();
//        Log.i("jfjbbfj", jjjj);
   }
    private void getContent(){
        String url = "https://v-kosmose.com/gadanie-onlajn/znachenie-i-tolkovanie-kazhdoj-karty-taro/";
        AddContent.DownloadContentTask task = new AddContent.DownloadContentTask();
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
                String thesImg = matcherImg.group(1);
                urls.add(thesImg);
            }
            while (matcherName.find()){
                String thesName = matcherName.group(1);
                names.add(thesName);
            }
            while (matcherUrl.find()){
                String thesURL = "https://v-kosmose.com/gadanie-onlajn/znachenie-karty"+matcherUrl.group(1);
                urlCard.add(thesURL);
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private void getContentCards(){
        for (String urlM : urlCard) {
            AddContent.DownloadContentTask task = new AddContent.DownloadContentTask();
            ArrayList<String> descr = new ArrayList<>();
            ArrayList<String> descrPer = new ArrayList<>();
            StringBuilder obPr = new StringBuilder();
            StringBuilder obPer = new StringBuilder();
            try {
                String content = task.execute(urlM).get();
                String start = "<ins>Прямое положение</ins>";
                String finish = "<ins>Перевернутое положение</ins>";
                Pattern pattern = Pattern.compile(start + "(.*?)" + finish);
                Matcher matcher = pattern.matcher(content);
                String splitContent = "";
                while (matcher.find()) {
                    splitContent = matcher.group(1);
                    descr.add(splitContent);
                }
                Pattern patIm = Pattern.compile("<p style=\"text-align: justify;\">(.*?)</p>");
                Matcher matIm = patIm.matcher(descr.get(0));
                String mmm = "";
                while (matIm.find()) {
                    mmm = matIm.group(1);
                    obPr.append(mmm + "\n");
                }

                String startPer = "<ins>Перевернутое положение</ins>";
                String finishPer = "<table style=\"width: 100%;\" width=\"100%\" cellspacing=\"0\" align=\"center\"";
                Pattern patternPer = Pattern.compile(startPer + "(.*?)" + finishPer);
                Matcher matcherPer = patternPer.matcher(content);
                String splitContentPer = "";
                while (matcherPer.find()) {
                    splitContentPer = matcherPer.group(1);
                    descrPer.add(splitContentPer);
                }
                Pattern patImPer = Pattern.compile("<p style=\"text-align: justify;\">(.*?)</p>");
                Matcher matImPer = patImPer.matcher(descrPer.get(0));
                String zzz = "";
                while (matImPer.find()) {
                    zzz = matImPer.group(1);
                    obPer.append(zzz + "\n");
                }
                Log.i("hdbbzzhdh", obPer.toString());
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            strobPr.add(obPr.toString());
            strobPer.add(obPer.toString());
        }
    }
//    private void getDownloadImg(){
//        for(String str:urls){
//            DownloadImageTask task = new DownloadImageTask();
//            Bitmap bitmap = null;
//            try {
//                bitmap = task.execute(str).get();
//                img.add(bitmap);
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//        }
//    }
    private static class DownloadContentTask extends AsyncTask<String, Void, String> {
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