package com.moaazfathyelneshawy.marvelapp.View;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.moaazfathyelneshawy.marvelapp.Adapters.ImagesAdapters;
import com.moaazfathyelneshawy.marvelapp.Models.Item;
import com.moaazfathyelneshawy.marvelapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ImageSheet extends BottomSheetDialogFragment {

    private List<Item> items;

    private ImageSheet(List<Item> items) {
        this.items = items;
    }


    @BindView(R.id.sheet_counter)
    AppCompatTextView sheetCounterTV;

    @BindView(R.id.sheet_rv)
    RecyclerView sheetRV;

    @SuppressLint("NewApi")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_bottom_sheet, container, false);
        ButterKnife.bind(this, view);

        sheetCounterTV.setText("" + 1 + " / " + items.size());

        ImagesAdapters adapters = new ImagesAdapters(items);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        sheetRV.setLayoutManager(linearLayoutManager);
        sheetRV.setAdapter(adapters);

        sheetRV.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int pos = linearLayoutManager.findLastVisibleItemPosition();
                sheetCounterTV.setText("" + (pos + 1) + " / " + items.size());
            }
        });

        return view;
    }


    public static ImageSheet getInstance(List<Item> items) {
        return new ImageSheet(items);
    }


    @OnClick(R.id.sheet_close)
    void closeSheet() {
        dismiss();
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();

        if (dialog != null) {
            View bottomSheet = dialog.findViewById(R.id.design_bottom_sheet);
            bottomSheet.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;

            View view = getView();
            view.post(() -> {
                View parent = (View) view.getParent();
                CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) (parent).getLayoutParams();
                CoordinatorLayout.Behavior behavior = params.getBehavior();
                BottomSheetBehavior bottomSheetBehavior = (BottomSheetBehavior) behavior;
                bottomSheetBehavior.setPeekHeight(view.getMeasuredHeight());
                ((View) bottomSheet.getParent()).setBackgroundColor(Color.TRANSPARENT);

            });
        }
    }

}
