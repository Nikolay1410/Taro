package com.example.tarogame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.tarogame.adapter.InfoAdapter;

import java.util.ArrayList;
import java.util.List;

public class InfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        Intent intent  = getIntent();
        ArrayList<Card> cardsInfo = new ArrayList<>();
        CardsDataBase dataBase = CardsDataBase.getInstance(this);
        List<Card> cardsDB = dataBase.cardDao().getAllCards();
        InfoAdapter adapter = new InfoAdapter();
        RecyclerView recyclerViewInfo = findViewById(R.id.recyclerViewInfo);
        Button buttonExit = findViewById(R.id.buttonExit);
        recyclerViewInfo.setLayoutManager(new GridLayoutManager(this, 1));
        recyclerViewInfo.setAdapter(adapter);


        if(intent.hasExtra("id")&&intent.hasExtra("content")) {
            ArrayList<Integer> id = intent.getIntegerArrayListExtra("id");
            ArrayList<Integer> content = intent.getIntegerArrayListExtra("content");
            for (int i=0; i<id.size(); i++) {
                Card card;
                if(content.get(i)>0){
                    card = new Card(cardsDB.get(id.get(i)).getId(), cardsDB.get(id.get(i)).getName(), cardsDB.get(id.get(i)).getImg(), cardsDB.get(id.get(i)).getUrl(), null, cardsDB.get(id.get(i)).getContentPer());
                }else {
                    card = new Card(cardsDB.get(id.get(i)).getId(), cardsDB.get(id.get(i)).getName(), cardsDB.get(id.get(i)).getImg(), cardsDB.get(id.get(i)).getUrl(), cardsDB.get(id.get(i)).getContentPr(), null);
                }
                cardsInfo.add(card);
                adapter.setCards(cardsInfo);
            }
        }
        buttonExit.setOnClickListener(view -> {
            Intent intentExit = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intentExit);
            finishAndRemoveTask();
        });
    }
}