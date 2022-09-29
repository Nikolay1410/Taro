package com.example.tarogame.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tarogame.Card;
import com.example.tarogame.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MyCardAdapter extends RecyclerView.Adapter<MyCardAdapter.MyCardViewHolder> {
    private List<Card> cards;

    public MyCardAdapter() {
        cards = new ArrayList<>();
    }

    @NonNull
    @Override
    public MyCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_my_card, parent, false);
        return new MyCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyCardViewHolder holder, int position) {
        Card card = cards.get(position);
        Picasso.get().load(card.getImg()).into(holder.imageViewMyCard);
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    public static class MyCardViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageViewMyCard;
        public MyCardViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewMyCard = itemView.findViewById(R.id.imageViewCard);
        }
    }

    public List<Card> getCards() {
        return cards;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setCards(List<Card> cards) {
        this.cards = cards;
        notifyDataSetChanged();
    }
}
