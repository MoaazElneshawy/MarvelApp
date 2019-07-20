package com.moaazfathyelneshawy.marvelapp.Adapters;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.moaazfathyelneshawy.marvelapp.Models.Character;
import com.moaazfathyelneshawy.marvelapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CharactersAdapter extends RecyclerView.Adapter<CharactersAdapter.CharactersViewHolder> {

    private List<Character> characters;
    private OnLoadMoreCharacters onLoadMoreCharacters;

    public CharactersAdapter(List<Character> characters, OnLoadMoreCharacters onLoadMoreCharacters) {
        this.characters = characters;
        this.onLoadMoreCharacters = onLoadMoreCharacters;
    }


    @NonNull
    @Override
    public CharactersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_character, parent, false);
        return new CharactersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CharactersViewHolder holder, int position) {
        holder.setViews(characters.get(position));
        if (position + 1 == characters.size()) {
            onLoadMoreCharacters.loadMore();
        }
    }

    @Override
    public int getItemCount() {
        return (characters != null && characters.size() > 0) ? characters.size() : 0;
    }

    class CharactersViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_character_img)
        AppCompatImageView posterIV;
        @BindView(R.id.item_character_title)
        AppCompatTextView titleTV;

        public CharactersViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @SuppressLint("NewApi")
        void setViews(Character character) {
            String poster_url = character.getThumbnail().getPath() + "." + character.getThumbnail().getExtension();
            Picasso.get()
                    .load(poster_url)
                    .placeholder(itemView.getContext().getDrawable(R.drawable.placeholder))
                    .into(posterIV);
            titleTV.setText(character.getName());
        }
    }

    public interface OnLoadMoreCharacters {
        void loadMore();
    }
}
