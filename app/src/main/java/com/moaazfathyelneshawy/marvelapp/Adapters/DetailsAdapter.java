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
import com.moaazfathyelneshawy.marvelapp.Models.Item;
import com.moaazfathyelneshawy.marvelapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailsAdapter extends RecyclerView.Adapter<DetailsAdapter.DetailsViewHolder> {
    private static final String TAG = "DetailsAdapter";
    private List<Item> items;

    public DetailsAdapter(List<Item> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public DetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_details, parent, false);
        return new DetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailsViewHolder holder, int position) {
        holder.setViews(items.get(position));
    }

    @Override
    public int getItemCount() {
        return (items != null && items.size() > 0) ? items.size() : 0;
    }

    class DetailsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_details_poster_iv)
        AppCompatImageView posterIV;
        @BindView(R.id.item_details_title_tv)
        AppCompatTextView titleTV;

        public DetailsViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @SuppressLint("NewApi")
        void setViews(Item item) {
            try {
                String poster_url = item.getThumbnail().getPath() + "." + item.getThumbnail().getExtension();
                Picasso.get()
                        .load(poster_url)
                        .placeholder(itemView.getContext().getDrawable(R.drawable.placeholder))
                        .into(posterIV);
                titleTV.setText(item.getTitle());
            } catch (Exception e) {
                Log.e(TAG, "setViews: " + e.getMessage());
            }
        }
    }
}
