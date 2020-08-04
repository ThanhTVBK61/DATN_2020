package com.example.datn_2020.view.trip;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.datn_2020.R;
import com.example.datn_2020.repository.model.CurrentUser;
import com.example.datn_2020.repository.model.TripByIdPlaceModel;
import com.example.datn_2020.viewmodel.ContainerVM;

import java.util.ArrayList;

public class SelectFavouritePlaceDialogFragment extends DialogFragment implements View.OnClickListener {

    private TextView tvSelectFavouritePlace;
    private ImageView ivCloseSexDialog;
    private RadioGroup rgSelectFavouritePlace;
    private RadioButton child[];
    private int sum;
    private ArrayList<TripByIdPlaceModel>  mTripByIdPlaceModels = new ArrayList<>();

    private SelectTrip mSelectTrip;
    private ContainerVM containerVM;
    private int idPlace;
    private Context mContext;

    public void setSelectFavouritePlaceDialogFragment(int idPlace, SelectTrip selectTrip) {
        this.mSelectTrip = selectTrip;
        this.idPlace = idPlace;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog_select_favourite_place, container, false);
        getDialog().getWindow().setBackgroundDrawableResource(R.drawable.shape_popup_sex);
        containerVM = new ViewModelProvider(getActivity()).get(ContainerVM.class);

        rgSelectFavouritePlace = view.findViewById(R.id.rgSelectFavouritePlace);
        ivCloseSexDialog = view.findViewById(R.id.ivCloseSexDialog);
        tvSelectFavouritePlace = view.findViewById(R.id.tvSelectFavouritePlace);

        rgSelectFavouritePlace.setOrientation(LinearLayout.VERTICAL);
        mTripByIdPlaceModels.clear();
        containerVM.loadTripByIdPlace(CurrentUser.getInstance().id,idPlace).observe(getViewLifecycleOwner(), new Observer<ArrayList<TripByIdPlaceModel>>() {
            @Override
            public void onChanged(ArrayList<TripByIdPlaceModel> tripByIdPlaceModels) {
                mTripByIdPlaceModels = tripByIdPlaceModels;
                sum = tripByIdPlaceModels.size();
                child = new RadioButton[sum];
                for (int i = 0; i < sum; i++) {
                    child[i] = new RadioButton(mContext);
                    String content = tripByIdPlaceModels.get(i).getNameTrip().substring(tripByIdPlaceModels.get(i).getNameTrip().indexOf(" ") + 1);
                    child[i].setText(content);
                    rgSelectFavouritePlace.addView(child[i]);
                }
            }
        });
//        containerVM.getTripByIdPlaces().observe(getViewLifecycleOwner(), new Observer<ArrayList<TripByIdPlaceModel>>() {
//            @Override
//            public void onChanged(ArrayList<TripByIdPlaceModel> tripByIdPlaceModels) {
//                mTripByIdPlaceModels = tripByIdPlaceModels;
//                sum = tripByIdPlaceModels.size();
//                child = new RadioButton[sum];
//                for (int i = 0; i < sum; i++) {
//                    child[i] = new RadioButton(mContext);
//                    String content = tripByIdPlaceModels.get(i).getNameTrip().substring(tripByIdPlaceModels.get(i).getNameTrip().indexOf(" ") + 1);
//                    child[i].setText(content);
//                    rgSelectFavouritePlace.addView(child[i]);
//                }
//            }
//        });


        ivCloseSexDialog.setOnClickListener(this);
        tvSelectFavouritePlace.setOnClickListener(this);

        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        mContext = null;
        mSelectTrip = null;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        mContext = context;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivCloseSexDialog:
                this.dismiss();
                break;
            case R.id.tvSelectFavouritePlace:
                for (int item = 0; item < sum; item++) {
                    if(child[item].isChecked()){
                        mSelectTrip.selectTrip(mTripByIdPlaceModels.get(item),idPlace);
                        break;
                    }
                }
                this.dismiss();
                break;

        }
    }

    public interface SelectTrip {
        void selectTrip(TripByIdPlaceModel tripByIdPlaceModel, int idPlace);
    }
}
