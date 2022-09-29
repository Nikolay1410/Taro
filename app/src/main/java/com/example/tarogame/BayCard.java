package com.example.tarogame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tarogame.adapter.CardAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BayCard extends AppCompatActivity {
    private List<Card> cards;
    private ArrayList<Card> desc;
    private ArrayList<Integer> cardId;
    private ArrayList<Integer> cardContent;
    private TextView textViewCardSelection;
    private ImageView imageViewBay;
    private CardAdapter cardAdapter;
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bay_card);
        Button buttonBayResult = findViewById(R.id.buttonBayResult);
        textViewCardSelection = findViewById(R.id.textViewCardSelection);
        imageViewBay = findViewById(R.id.imageViewBay);
        CardsDataBase dataBase = CardsDataBase.getInstance(this);
        cards = new ArrayList<>();
        desc = new ArrayList<>();
        cardId = new ArrayList<>();
        cardContent = new ArrayList<>();
        List<Card> cardsDB = dataBase.cardDao().getAllCards();
        cards.clear();
        cards.addAll(cardsDB);
        cardAdapter = new CardAdapter();
        RecyclerView recyclerViewBayMyCards = findViewById(R.id.recyclerViewBayMyCards);
        recyclerViewBayMyCards.setLayoutManager(new GridLayoutManager(this, 7));

        deckCards();
        cardAdapter.setCards(desc);
        recyclerViewBayMyCards.setAdapter(cardAdapter);

        cardAdapter.setOnDescClickListener(position -> {
            Card card = cardAdapter.getCards().get(position);
            if (count==0){
                int positionCard = orientation();
                cardId.add(card.getId());
                cardContent.add(positionCard);
                textViewCardSelection.setText(card.getName());
                Picasso.get().load(desc.get(position).getImg()).into(imageViewBay);
                imageViewBay.setVisibility(View.VISIBLE);
                if (positionCard!=0){
                    imageViewBay.setRotation(180);
                }else {imageViewBay.setRotation(0);}
            }
            count++;
        });

        buttonBayResult.setOnClickListener(view -> {
            if (cardId.size()>0){
                Intent intent = new Intent(getApplicationContext(), InfoActivity.class);
                intent.putIntegerArrayListExtra("id", cardId);
                intent.putIntegerArrayListExtra("content", cardContent);
                startActivity(intent);
                finishAndRemoveTask();
            }else {
                Toast.makeText(BayCard.this, "Сделайте ваш выбор", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void deckCards(){
        Collections.shuffle(cards);
        for (int i = 0; i<21; i++) {
            desc.add(cards.get(i));
        }
    }
    private int orientation(){
        return (int) (Math.random() * 2);
    }
}