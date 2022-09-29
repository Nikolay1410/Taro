package com.example.tarogame.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tarogame.Card;
import com.example.tarogame.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class InfoAdapter extends RecyclerView.Adapter<InfoAdapter.InfoViewHolder> {
    private ArrayList<Card> cards;

    public InfoAdapter() {cards = new ArrayList<>();}

    @NonNull
    @Override
    public InfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_info_card, parent, false);
        return new InfoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InfoViewHolder holder, int position) {
        Card card = cards.get(position);
        holder.textViewTitleCardInfo.setText(card.getName());
         Picasso.get().load(card.getImg()).into(holder.imageViewCardInfo);
        if (card.getContentPr()!= null) {
            holder.textViewDescriptionCardInfo.setText(card.getContentPr());
        }
        if (card.getContentPer()!=null) {
            holder.textViewDescriptionCardInfo.setText(card.getContentPer());
        }
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    static class InfoViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imageViewCardInfo;
        private final TextView textViewTitleCardInfo;
        private final TextView textViewDescriptionCardInfo;
        public InfoViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewCardInfo = itemView.findViewById(R.id.imageViewCardInfo);
            textViewTitleCardInfo = itemView.findViewById(R.id.textViewTitleCardInfo);
            textViewDescriptionCardInfo = itemView.findViewById(R.id.textViewDescriptionCardInfo);
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
        notifyDataSetChanged();
    }
}
