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
import com.example.tarogame.adapter.MyCardAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ThreeCards extends AppCompatActivity {
    private List<Card> cards;
    private ArrayList<Card> desc;
    private ArrayList<Card> descPlus;
    private ArrayList<Integer> cardId;
    private ArrayList<Integer> cardContent;
    private ImageView imageViewThree1;
    private ImageView imageViewThree2;
    private ImageView imageViewThree3;
    private TextView textViewCardSelection1;
    private TextView textViewCardSelection2;
    private TextView textViewCardSelection3;
    private CardAdapter cardAdapter;
    private int number = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three_cards);
        imageViewThree1 = findViewById(R.id.imageViewThree1);
        imageViewThree2 = findViewById(R.id.imageViewThree2);
        imageViewThree3 = findViewById(R.id.imageViewThree3);
        CardsDataBase dataBase = CardsDataBase.getInstance(this);
        cards = new ArrayList<>();
        desc = new ArrayList<>();
        descPlus = new ArrayList<>();
        ArrayList<Card> myCard = new ArrayList<>();
        cardId = new ArrayList<>();
        cardContent = new ArrayList<>();
        List<Card> cardsDB = dataBase.cardDao().getAllCards();
        cards.clear();
        cards.addAll(cardsDB);
        cardAdapter = new CardAdapter();
        MyCardAdapter myCardAdapter = new MyCardAdapter();
        RecyclerView recyclerViewThreeMyCards = findViewById(R.id.recyclerViewThreeMyCards);
        textViewCardSelection1 = findViewById(R.id.textViewCardSelection1);
        textViewCardSelection2 = findViewById(R.id.textViewCardSelection2);
        textViewCardSelection3 = findViewById(R.id.textViewCardSelection3);
        Button buttonThreeResult = findViewById(R.id.buttonThreeResult);

        recyclerViewThreeMyCards.setLayoutManager(new GridLayoutManager(this, 7));

        deckCards();
        cardAdapter.setCards(desc);
        myCardAdapter.setCards(myCard);
        recyclerViewThreeMyCards.setAdapter(cardAdapter);

        cardAdapter.setOnDescClickListener(position -> {
            Card card = cardAdapter.getCards().get(position);
            if (number<3){
                switch (number){
                    case 0:
                        int positionCard1 = orientation();
                        cardId.add(card.getId());
                        cardContent.add(positionCard1);
                        textViewCardSelection1.setText(card.getName());
                        if (positionCard1!=0){
                            imageViewThree1.setRotation(180);
                        }else {imageViewThree1.setRotation(0);}
                        Picasso.get().load(desc.get(position).getImg()).into(imageViewThree1);
                        imageViewThree1.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        int positionCard2 = orientation();
                        cardId.add(card.getId());
                        cardContent.add(positionCard2);
                        textViewCardSelection2.setText(card.getName());
                        if (positionCard2!=0){
                            imageViewThree2.setRotation(180);
                        }else {imageViewThree2.setRotation(0);}
                        Picasso.get().load(desc.get(position).getImg()).into(imageViewThree2);
                        imageViewThree2.setVisibility(View.VISIBLE);

                        break;
                    case 2:
                        int positionCard3 = orientation();
                        cardId.add(card.getId());
                        cardContent.add(positionCard3);
                        textViewCardSelection3.setText(card.getName());
                        if (positionCard3!=0){
                            imageViewThree3.setRotation(180);
                        }else {imageViewThree3.setRotation(0);}
                        Picasso.get().load(desc.get(position).getImg()).into(imageViewThree3);
                        imageViewThree3.setVisibility(View.VISIBLE);
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
        buttonThreeResult.setOnClickListener(view -> {
            if (cardId.size()==3){
                Intent intent = new Intent(getApplicationContext(), InfoActivity.class);
                intent.putIntegerArrayListExtra("id", cardId);
                intent.putIntegerArrayListExtra("content", cardContent);
                startActivity(intent);
                finishAndRemoveTask();
            }else {
                Toast.makeText(ThreeCards.this, "Сделайте ваш выбор", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void deckCards(){
        Collections.shuffle(cards);
        for (int i = 0; i<21; i++) {
            desc.add(cards.get(i));
        }
        for (int i = 21; i<24; i++) {
            descPlus.add(cards.get(i));
        }
    }
    private int orientation(){
        return (int) (Math.random() * 2);
    }
}