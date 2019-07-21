package com.moaazfathyelneshawy.marvelapp.Adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.moaazfathyelneshawy.marvelapp.Models.Item;
import com.moaazfathyelneshawy.marvelapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImagesAdapters extends RecyclerView.Adapter<ImagesAdapters.ImagesViewHolder> {

    private List<Item> items;

    public ImagesAdapters(List<Item> items) {
        this.items = items;
    }


    @NonNull
    @Override
    public ImagesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_image_in_sheet, parent, false);
        return new ImagesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImagesViewHolder holder, int position) {
        holder.setViews(items.get(position));
    }

    @Override
    public int getItemCount() {
        return (items != null && items.size() > 0) ? items.size() : 0;
    }

    class ImagesViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_sheet_title)
        AppCompatTextView titleTV;
        @BindView(R.id.item_sheet_poster)
        AppCompatImageView posterIV;


        public ImagesViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @SuppressLint("NewApi")
        void setViews(Item item) {
            String url = item.getThumbnail().getPath() + "." + item.getThumbnail().getExtension();
            Picasso.get()
                    .load(url)
                    .placeholder(itemView.getContext().getDrawable(R.drawable.placeholder))
                    .into(posterIV);
            titleTV.setText(item.getTitle());
        }
    }


}
