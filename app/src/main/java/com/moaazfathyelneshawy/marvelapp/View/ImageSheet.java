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
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.moaazfathyelneshawy.marvelapp.R;
import com.squareup.picasso.Picasso;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ImageSheet extends BottomSheetDialogFragment {

    private String url;
    private String title;
    private BottomSheetBehavior mBehavior;

    private ImageSheet(String url, String title) {
        this.url = url;
        this.title = title;
    }

    @BindView(R.id.sheet_poster)
    AppCompatImageView posterIV;
    @BindView(R.id.sheet_title)
    AppCompatTextView titleTV;

    @SuppressLint("NewApi")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_bottom_sheet, container, false);
        ButterKnife.bind(this, view);
        Picasso.get()
                .load(url)
                .placeholder(getActivity().getDrawable(R.drawable.placeholder))
                .into(posterIV);
        titleTV.setText(title);
        return view;
    }

    public static ImageSheet getInstance(String url, String title) {
        return new ImageSheet(url, title);
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
