package com.moaazfathyelneshawy.marvelapp.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chootdev.recycleclick.RecycleClick;
import com.moaazfathyelneshawy.marvelapp.Adapters.DetailsAdapter;
import com.moaazfathyelneshawy.marvelapp.Models.Character;
import com.moaazfathyelneshawy.marvelapp.Models.Item;
import com.moaazfathyelneshawy.marvelapp.R;
import com.moaazfathyelneshawy.marvelapp.ViewModel.DetailsViewModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DetailsActivity extends AppCompatActivity {

    private int id;
    private Character character;
    private DetailsViewModel viewModel;
    private DetailsAdapter adapter;

    @BindView(R.id.details_poster_iv)
    AppCompatImageView posterIV;
    @BindView(R.id.details_loading_pb)
    ProgressBar loadingPG;
    @BindView(R.id.details_name_tv)
    AppCompatTextView nameTV;
    @BindView(R.id.details_description_tv)
    AppCompatTextView descriptionTV;
    @BindView(R.id.details_description_header_tv)
    AppCompatTextView descriptionHeaderTV;
    @BindView(R.id.details_comics_header_tv)
    AppCompatTextView comicsHeaderTV;
    @BindView(R.id.details_comics_rv)
    RecyclerView comicsRV;
    @BindView(R.id.details_series_header_tv)
    AppCompatTextView seriesHeaderTV;
    @BindView(R.id.details_series_rv)
    RecyclerView seriesRV;
    @BindView(R.id.details_events_header_tv)
    AppCompatTextView eventsHeaderTV;
    @BindView(R.id.details_events_rv)
    RecyclerView eventsRV;
    @BindView(R.id.details_stories_header_tv)
    AppCompatTextView storiesHeaderTV;
    @BindView(R.id.details_stories_rv)
    RecyclerView storiesRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        viewModel = ViewModelProviders.of(this).get(DetailsViewModel.class);

        Intent intent = this.getIntent();
        character = (Character) intent.getBundleExtra(getString(R.string.bundleToDetails)).getSerializable(getString(R.string.character));
        setViews(character);

        observe();
    }

    private void setViews(Character character) {

        String poster_url = character.getThumbnail().getPath() + "." + character.getThumbnail().getExtension();
        Picasso.get()
                .load(poster_url)
                .into(posterIV);

        nameTV.setText(character.getName());
        if (character.getDescription() != null || !character.getDescription().isEmpty()) {
            descriptionTV.setText(character.getDescription());
        } else {
            descriptionTV.setVisibility(View.GONE);
            descriptionHeaderTV.setVisibility(View.GONE);
        }

        viewModel.loadComics(character.getId());
        viewModel.loadSeries(character.getId());
        viewModel.loadEvents(character.getId());
        viewModel.loadStories(character.getId());
    }


    private void observe() {
        viewModel.getComics().observe(DetailsActivity.this, new Observer<List<Item>>() {
            @Override
            public void onChanged(List<Item> items) {
                if (items.size() > 0) {
                    loadingPG.setVisibility(View.GONE);
                    comicsHeaderTV.setVisibility(View.VISIBLE);
                    comicsRV.setVisibility(View.VISIBLE);
                    setComicsAdapter(items);
                }
            }
        });

        viewModel.getStories().observe(DetailsActivity.this, new Observer<List<Item>>() {
            @Override
            public void onChanged(List<Item> items) {
                if (items.size() > 0) {
                    loadingPG.setVisibility(View.GONE);
                    storiesHeaderTV.setVisibility(View.VISIBLE);
                    storiesRV.setVisibility(View.VISIBLE);
                    setStoriesAdapter(items);
                }
            }
        });

        viewModel.getEvents().observe(DetailsActivity.this, new Observer<List<Item>>() {
            @Override
            public void onChanged(List<Item> items) {
                if (items.size() > 0) {
                    loadingPG.setVisibility(View.GONE);
                    eventsHeaderTV.setVisibility(View.VISIBLE);
                    eventsRV.setVisibility(View.VISIBLE);
                    setEventsAdapter(items);
                }
            }
        });

        viewModel.getSeries().observe(DetailsActivity.this, new Observer<List<Item>>() {
            @Override
            public void onChanged(List<Item> items) {
                if (items.size() > 0) {
                    loadingPG.setVisibility(View.GONE);
                    seriesHeaderTV.setVisibility(View.VISIBLE);
                    seriesRV.setVisibility(View.VISIBLE);
                    setSeriesAdapter(items);
                }
            }
        });


    }

    private void setComicsAdapter(List<Item> comics) {
        DetailsAdapter adapter = new DetailsAdapter(comics);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DetailsActivity.this);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        comicsRV.setLayoutManager(linearLayoutManager);
        comicsRV.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        RecycleClick.addTo(comicsRV).setOnItemClickListener(new RecycleClick.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int i, View view) {
                ImageSheet imageSheet = ImageSheet.getInstance(comics);
                imageSheet.show(getSupportFragmentManager(), "image_sheet");
            }
        });
    }

    private void setSeriesAdapter(List<Item> series) {
        DetailsAdapter adapter = new DetailsAdapter(series);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DetailsActivity.this);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        seriesRV.setLayoutManager(linearLayoutManager);
        seriesRV.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        RecycleClick.addTo(seriesRV).setOnItemClickListener(new RecycleClick.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int i, View view) {
                ImageSheet imageSheet = ImageSheet.getInstance(series);
                imageSheet.show(getSupportFragmentManager(), "image_sheet");
            }
        });
    }

    private void setStoriesAdapter(List<Item> stories) {
        DetailsAdapter adapter = new DetailsAdapter(stories);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DetailsActivity.this);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        storiesRV.setLayoutManager(linearLayoutManager);
        storiesRV.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        RecycleClick.addTo(storiesRV).setOnItemClickListener(new RecycleClick.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int i, View view) {
                ImageSheet imageSheet = ImageSheet.getInstance(stories);
                imageSheet.show(getSupportFragmentManager(), "image_sheet");
            }
        });
    }

    private void setEventsAdapter(List<Item> events) {
        DetailsAdapter adapter = new DetailsAdapter(events);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DetailsActivity.this);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        eventsRV.setLayoutManager(linearLayoutManager);
        eventsRV.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        RecycleClick.addTo(eventsRV).setOnItemClickListener(new RecycleClick.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int i, View view) {
                ImageSheet imageSheet = ImageSheet.getInstance(events);
                imageSheet.show(getSupportFragmentManager(), "image_sheet");
            }
        });
    }

    @OnClick(R.id.details_poster_iv)
    public void backToCharacters() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}
