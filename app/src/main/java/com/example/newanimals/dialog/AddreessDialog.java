package com.example.newanimals.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.newanimals.R;
import com.example.newanimals.adapter.AddressAutoAdapter;
import com.example.newanimals.presenter.YandexGeocoderPresenter;
import com.example.newanimals.view.YandexGeocoderView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;

public class AddreessDialog extends BottomSheetDialogFragment implements YandexGeocoderView {
    private ArrayList<String> addressList;
    private AddressAutoAdapter adapter;
    private ArrayAdapter<String> addressAdapter;
    @BindView(R.id.au_comp)
    AutoCompleteTextView autoCompleteTextView;
    @BindView(R.id.list_address)
    ListView listView;
    private OnAddressSelected views;
    private YandexGeocoderPresenter presenter;


    public static AddreessDialog newInstance() {
        return new AddreessDialog();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, androidx.appcompat.R.style.Base_Theme_AppCompat_Dialog);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnAddressSelected){
            views = (OnAddressSelected) context;
        } else throw new RuntimeException(context.toString());
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.address_dialog, null);
        builder.setView(v)
                .setTitle("Введите адрес")
                .setNegativeButton("Отмена", null);

        addressList =  new ArrayList<>();
        addressAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, addressList);
        listView.setAdapter(addressAdapter);
        presenter = new YandexGeocoderPresenter(this);

        autoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                addressList.clear();;
                addressList.addAll(getAddressSuggestions(charSequence.toString()));
                addressAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selecteds = addressList.get(i);
                views.selectedView(selecteds);
                dismiss();
            }
        });
        return builder.create();
    }

    private ArrayList<String> getAddressSuggestions(String toString) {
            presenter.getTextAddress(toString);
            return addressList;
    }


    @Override
    public void addressText(List<String> address) {
        addressList.clear();
        addressList.addAll(address);
        addressAdapter.notifyDataSetChanged();
    }

    @Override
    public void meessageError(String str) {
        Toast.makeText(getContext(), str, Toast.LENGTH_SHORT).show();
    }
    public interface OnAddressSelected {
        void selectedView(String address);
    }

}


