package com.moaazfathyelneshawy.marvelapp.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.chootdev.recycleclick.RecycleClick;
import com.moaazfathyelneshawy.marvelapp.Adapters.CharactersAdapter;
import com.moaazfathyelneshawy.marvelapp.Models.Character;
import com.moaazfathyelneshawy.marvelapp.R;
import com.moaazfathyelneshawy.marvelapp.ViewModel.CharactersViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CharactersActivity extends AppCompatActivity implements CharactersAdapter.OnLoadMoreCharacters {


    @BindView(R.id.characters_rv)
    RecyclerView charactersRV;
    @BindView(R.id.characters_pb)
    ProgressBar loadingProgressBar;
    @BindView(R.id.character_search_iv)
    AppCompatImageView searchIV;

    private static final String TAG = "CharactersActivity";
    private CharactersViewModel charactersViewModel;
    private CharactersAdapter adapter;
    private int offset = 0;
    private int limit = 15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_characters);
        ButterKnife.bind(this);
        init();

        charactersViewModel.loadCharacters(offset, limit);
        observe();

    }

    private void observe() {

        charactersViewModel.getCharacters().observe(this, new Observer<List<Character>>() {
            @Override
            public void onChanged(List<Character> characters) {
                if (characters.size() > 0) {
                    loadingProgressBar.setVisibility(View.GONE);
                    setAdapterAndRecycler(characters);
                    adapter.notifyDataSetChanged();
                }
            }
        });
        charactersViewModel.getCharactersWithSearch().observe(this, new Observer<List<Character>>() {
            @Override
            public void onChanged(List<Character> characters) {
                if (characters.size() > 0) {
                    loadingProgressBar.setVisibility(View.GONE);
                    setAdapterAndRecycler(characters);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void init() {
        charactersViewModel = ViewModelProviders.of(this).get(CharactersViewModel.class);
    }


    private void setAdapterAndRecycler(List<Character> characters) {
        adapter = new CharactersAdapter(characters, CharactersActivity.this);
        charactersRV.setLayoutManager(new LinearLayoutManager(CharactersActivity.this));
        charactersRV.setAdapter(adapter);

        RecycleClick.addTo(charactersRV).setOnItemClickListener(new RecycleClick.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int i, View view) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(getString(R.string.character), characters.get(i));
                Intent toDetails = new Intent(CharactersActivity.this, DetailsActivity.class);
                toDetails.putExtra(getString(R.string.bundleToDetails), bundle);
                startActivity(toDetails);
            }
        });
    }

    @OnClick(R.id.character_search_iv)
    void goToSearch() {
        startActivity(new Intent(CharactersActivity.this, SearchActivity.class));
    }

    @Override
    public void loadMore() {
        offset += 5;
        charactersViewModel.loadCharacters(offset, limit);
    }
}
