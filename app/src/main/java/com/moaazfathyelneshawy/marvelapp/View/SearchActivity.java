package com.moaazfathyelneshawy.marvelapp.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
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
import butterknife.OnTextChanged;

public class SearchActivity extends AppCompatActivity implements CharactersAdapter.OnLoadMoreCharacters {

    private static final String TAG = "SearchActivity";

    @BindView(R.id.search_rv)
    RecyclerView searchRV;
    @BindView(R.id.search_et)
    AppCompatEditText searchET;
    @BindView(R.id.search_pb)
    ProgressBar searchPB;

    private CharactersViewModel viewModel;
    private int limit = 15;
    private int offset = 0;
    private String searchKeyWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        viewModel = ViewModelProviders.of(this).get(CharactersViewModel.class);
        observe();
    }

    private void setAdapterAndRecycler(List<Character> characters) {
        CharactersAdapter adapter = new CharactersAdapter(characters, SearchActivity.this);
        searchRV.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
        searchRV.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        RecycleClick.addTo(searchRV).setOnItemClickListener(new RecycleClick.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int i, View view) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(getString(R.string.character), characters.get(i));
                Intent toDetails = new Intent(SearchActivity.this, DetailsActivity.class);
                toDetails.putExtra(getString(R.string.bundleToDetails), bundle);
                startActivity(toDetails);
            }
        });
    }

    private void observe() {
        viewModel.getCharactersWithSearch().observe(this, new Observer<List<Character>>() {
            @Override
            public void onChanged(List<Character> characters) {
                if (characters != null && characters.size() > 0) {
                    searchPB.setVisibility(View.GONE);
                    searchRV.setVisibility(View.VISIBLE);
                    setAdapterAndRecycler(characters);
                } else {
                    searchPB.setVisibility(View.VISIBLE);
                }
            }
        });
    }


    @OnClick(R.id.search_cancel_tv)
    void goBack() {
        onBackPressed();
    }

    @OnTextChanged(R.id.search_et)
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        searchPB.setVisibility(View.VISIBLE);
        searchRV.setVisibility(View.GONE);
        searchKeyWord = s.toString().trim();
        Log.e(TAG, "onTextChanged: " + searchKeyWord);
        viewModel.loadCharactersWithSearch(offset, limit, searchKeyWord);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    @Override
    public void loadMore() {
        offset += 5;
        viewModel.loadCharactersWithSearch(offset, limit, searchKeyWord);
    }
}
