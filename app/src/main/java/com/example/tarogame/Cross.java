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

public class Cross extends AppCompatActivity {
    private ImageView imageViewCard1;
    private ImageView imageViewCard2;
    private ImageView imageViewCard3;
    private ImageView imageViewCard4;
    private TextView textViewCard1;
    private TextView textViewCard2;
    private TextView textViewCard3;
    private TextView textViewCard4;
    private List<Card> cards;
    private ArrayList<Integer> cardId;
    private ArrayList<Integer> cardContent;
    private ArrayList<Card> desc;
    private ArrayList<Card> descPlus;
    private CardAdapter cardAdapter;
    private int number = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cross);
        CardsDataBase dataBase = CardsDataBase.getInstance(this);
        cards = new ArrayList<>();
        desc = new ArrayList<>();
        descPlus = new ArrayList<>();
        cardId = new ArrayList<>();
        cardContent = new ArrayList<>();
        List<Card> cardsDB = dataBase.cardDao().getAllCards();
        cards.clear();
        cards.addAll(cardsDB);
        Button buttonCrossResult = findViewById(R.id.buttonCrossResult);
        RecyclerView recyclerViewCrossMyCards = findViewById(R.id.recyclerViewCrossMyCards);
        imageViewCard1 = findViewById(R.id.imageViewCrossCard1);
        imageViewCard2 = findViewById(R.id.imageViewCrossCard2);
        imageViewCard3 = findViewById(R.id.imageViewCrossCard3);
        imageViewCard4 = findViewById(R.id.imageViewCrossCard4);
        textViewCard1= findViewById(R.id.textViewCrossCard1);
        textViewCard2= findViewById(R.id.textViewCrossCard2);
        textViewCard3= findViewById(R.id.textViewCrossCard3);
        textViewCard4= findViewById(R.id.textViewCrossCard4);
        cardAdapter = new CardAdapter();
        recyclerViewCrossMyCards.setLayoutManager(new GridLayoutManager(this, 7));
        deckCards();
        cardAdapter.setCards(desc);
        recyclerViewCrossMyCards.setAdapter(cardAdapter);
        cardAdapter.setOnDescClickListener(position -> {
            Card card = cardAdapter.getCards().get(position);
            if (number<4){
                switch (number){
                    case 0:
                        int positionCard1 = orientation();
                        cardId.add(card.getId());
                        cardContent.add(positionCard1);
                        textViewCard1.setText(card.getName());
                        if (positionCard1!=0){
                            imageViewCard1.setRotation(180);
                        }else {imageViewCard1.setRotation(0);}
                        Picasso.get().load(desc.get(position).getImg()).into(imageViewCard1);
                        imageViewCard1.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        int positionCard2 = orientation();
                        cardId.add(card.getId());
                        cardContent.add(positionCard2);
                        textViewCard2.setText(card.getName());
                        if (positionCard2!=0){
                            imageViewCard2.setRotation(180);
                        }else {imageViewCard2.setRotation(0);}
                        Picasso.get().load(desc.get(position).getImg()).into(imageViewCard2);
                        imageViewCard2.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        int positionCard3 = orientation();
                        cardId.add(card.getId());
                        cardContent.add(positionCard3);
                        textViewCard3.setText(card.getName());
                        if (positionCard3!=0){
                            imageViewCard3.setRotation(180);
                        }else {imageViewCard3.setRotation(0);}
                        Picasso.get().load(desc.get(position).getImg()).into(imageViewCard3);
                        imageViewCard3.setVisibility(View.VISIBLE);
                        break;
                    case 3:
                        int positionCard4 = orientation();
                        cardId.add(card.getId());
                        cardContent.add(positionCard4);
                        textViewCard4.setText(card.getName());
                        if (positionCard4!=0){
                            imageViewCard4.setRotation(180);
                        }else {imageViewCard4.setRotation(0);}
                        Picasso.get().load(desc.get(position).getImg()).into(imageViewCard4);
                        imageViewCard4.setVisibility(View.VISIBLE);
                        break;
                }
                desc.get(position).setId(descPlus.get(number).getId());
                desc.get(position).setName(descPlus.get(number).getName());
                desc.get(position).setImg(descPlus.get(number).getImg());
                desc.get(position).setUrl(descPlus.get(number).getUrl());
                desc.get(position).setContentPr(descPlus.get(number).getContentPr());
                desc.get(position).setContentPer(descPlus.get(number).getContentPer());
                number++;

            }
        });
        buttonCrossResult.setOnClickListener(view -> {
            if (cardId.size()==4){
                Intent intent = new Intent(getApplicationContext(), InfoActivity.class);
                intent.putIntegerArrayListExtra("id", cardId);
                intent.putIntegerArrayListExtra("content", cardContent);
                startActivity(intent);
                finishAndRemoveTask();
            }else {
                Toast.makeText(Cross.this, "Сделайте ваш выбор", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void deckCards(){
        Collections.shuffle(cards);
        for (int i = 0; i<14; i++) {
            desc.add(cards.get(i));
        }
        for (int i = 14; i<18; i++) {
            descPlus.add(cards.get(i));
        }
    }
    private int orientation(){
        return (int) (Math.random() * 2);
    }
}