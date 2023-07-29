package com.example.newanimals.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newanimals.R;
import com.example.newanimals.adapter.AddressAutoAdapter;
import com.example.newanimals.network.response.DaDataResponse;
import com.example.newanimals.presenter.AddressPresenter;
import com.example.newanimals.view.AddressView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.List;

public class AddreessDialog  extends BottomSheetDialogFragment implements AddressView {
    public static final String TAG = AddreessDialog.class.getSimpleName();
    private static final String TITLE = "title";
    public static AddreessDialog newInstance(SuggestSelectDialogCallback callback, String title) {
        AddreessDialog d = new AddreessDialog(callback);
        Bundle b = new Bundle();
        b.putString(TITLE, title);
        d.setArguments(b);
        return d;
    }

    public static final int DEPARTURE_ADDRESS = 1;

    @Override
    public void errorMessage(@Nullable String message) {

    }

    @Override
    public void getAddress(List<DaDataResponse.DaDataResult> data) {
        if(adapter != null) {
            adapter.setData(data);
        }
    }

    public interface SuggestSelectDialogCallback {
        void onSuggestSelected(int type, String address);
    }

    private AddressPresenter presenter;
    private AddressAutoAdapter adapter;
    private SuggestSelectDialogCallback callback;
    private FrameLayout emptyFrame;

    public AddreessDialog(SuggestSelectDialogCallback callback) {
        this.callback = callback;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.adress_auto_layout, container, false);
        presenter = new AddressPresenter(this);
        TextView titleTxt = v.findViewById(R.id.title_txt);
        titleTxt.setText(getArguments().getString(TITLE, "Адрес"));
        EditText addressEdit = v.findViewById(R.id.adress_edit);
        addressEdit.setOnEditorActionListener((v12, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                dismiss();
                return true;
            }
            return false;
        });
        ImageView removeImg = v.findViewById(R.id.remove_img);
        removeImg.setOnClickListener(v1 -> {
            callback.onSuggestSelected(DEPARTURE_ADDRESS, addressEdit.getText().toString());
        });
        emptyFrame = v.findViewById(R.id.empty_frame);
        emptyFrame.setVisibility(View.VISIBLE);
        RecyclerView recView = v.findViewById(R.id.rec_view);
        adapter = new AddressAutoAdapter(getActivity(), mainAddress -> {
            if(addressEdit.isFocused()) {
                addressEdit.setText(mainAddress);
                callback.onSuggestSelected(DEPARTURE_ADDRESS, mainAddress);
            }
        });
        recView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recView.setAdapter(adapter);
        addressEdit.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override public void afterTextChanged(Editable s) {
                emptyFrame.setVisibility(View.GONE);
                String str = s.toString();
                presenter.getFullNameAddress(str);
            }
        });
        return v;
    }

    @NonNull @Override public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override public void onShow(DialogInterface dialogInterface) {
                BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) dialogInterface;
                setupRatio(bottomSheetDialog);
            }
        });
        return dialog;
    }

    private void setupRatio(BottomSheetDialog bottomSheetDialog) {
        FrameLayout bottomSheet = (FrameLayout) bottomSheetDialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);
        BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
        ViewGroup.LayoutParams layoutParams = bottomSheet.getLayoutParams();
        layoutParams.height = getBottomSheetDialogDefaultHeight();
        bottomSheet.setLayoutParams(layoutParams);
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    private int getBottomSheetDialogDefaultHeight() {
        return getWindowHeight() * 90 / 100;
    }

    private int getWindowHeight() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }
}

