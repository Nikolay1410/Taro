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

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder> {
    private List<Card> cards;
    private OnDescClickListener onDescClickListener;

    public CardAdapter() {
        cards = new ArrayList<>();
    }

    public interface OnDescClickListener{
        void onDescClick(int position);
    }

    public void setOnDescClickListener(OnDescClickListener onDescClickListener) {
        this.onDescClickListener = onDescClickListener;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_card, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        Picasso.get().load(R.drawable.revers).into(holder.imageViewCard);
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    class CardViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageViewCard;
        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewCard = itemView.findViewById(R.id.imageViewCard);
            itemView.setOnClickListener(view -> {
                if (onDescClickListener!=null){
                    onDescClickListener.onDescClick(getAdapterPosition());
                }
            });
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
