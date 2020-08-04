package com.example.datn_2020.view.notification;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.datn_2020.R;
import com.example.datn_2020.adapter.notification.NotificationAdapter;
import com.example.datn_2020.repository.model.CurrentUser;
import com.example.datn_2020.repository.model.DeleteTripModel;
import com.example.datn_2020.repository.model.NotificationModel;
import com.example.datn_2020.repository.model.NotificationUpdateModel;
import com.example.datn_2020.repository.model.NotificationUpdateStatusModel;
import com.example.datn_2020.repository.model.SocketIo;
import com.example.datn_2020.view.container.ContainerActivity;
import com.example.datn_2020.viewmodel.ContainerVM;
import com.github.nkzawa.emitter.Emitter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;


public class NotificationFragment extends Fragment implements NotificationAdapter.ItemClickListener {

    private final String TAG = "NotificationFragment";

    private RecyclerView rvNotification;
    private NotificationAdapter mNotificationAdapter;
    private ArrayList<NotificationModel> listNotifications = new ArrayList<>();
    private FragmentActivity mFragmentActivity;

    private Context mContext;

    private ContainerVM notificationVM;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        Log.i(TAG, "onCreate");
        if (getActivity() != null) {
            mFragmentActivity = getActivity();
        }

        notificationVM = new ViewModelProvider(mFragmentActivity).get(ContainerVM.class);
        registerViews(view);
        registerRecyclerView();

        notificationVM.loadNotification(CurrentUser.getInstance().id);
        notificationVM.getNotificationResponses().observe(getViewLifecycleOwner(), new Observer<ArrayList<NotificationModel>>() {
            @Override
            public void onChanged(ArrayList<NotificationModel> notificationModels) {
                listNotifications = notificationModels;
                Collections.reverse(listNotifications);

                mNotificationAdapter.setItems(listNotifications);
            }
        });

        SocketIo.getInstance().on("new notification", onNewMessage);
//        SocketIo.getInstance().connect();

        return view;
    }

    private void registerViews(View view) {
        rvNotification = view.findViewById(R.id.rvNotification);
    }

    private void registerRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvNotification.setLayoutManager(linearLayoutManager);
        mNotificationAdapter = new NotificationAdapter(getActivity(), listNotifications, this);
        rvNotification.setAdapter(mNotificationAdapter);
    }

    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            mFragmentActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    NotificationModel getNotificationModel = new NotificationModel();
                    try {
                        getNotificationModel.setIdTripPlace(data.getInt("idTrip"));
                        getNotificationModel.setNameTripPlace(data.getString("name"));
                        getNotificationModel.setImageAccount(data.getString("imageUrl"));
                        getNotificationModel.setNameAccount(data.getString("usernameSend"));
                        getNotificationModel.setType(data.getString("type"));
                        getNotificationModel.setStatus(data.getInt("status"));
                        getNotificationModel.setTime(data.getString("time"));

                    } catch (JSONException e) {
                        return;
                    }

                    // add the message to view
                    mNotificationAdapter.addItem(getNotificationModel);
                }
            });
        }
    };

    @Override
    public void onAgreeClick(int idTrip,int idNotification) {

        notificationVM.joinTrip(new NotificationUpdateModel(CurrentUser.getInstance().id,idNotification,idTrip));
        notificationVM.getConfirmTripUser().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean result) {
                if(result){
                    notificationVM.setNewTrip(true);
                }
            }
        });
    }

    @Override
    public void onRejectClick(int idNotification) {
        notificationVM.rejectTrip(new NotificationUpdateModel(idNotification));
    }

    @Override
    public void onItemClick(int idNotification) {
        notificationVM.updateStatusNotification(new NotificationUpdateStatusModel(idNotification));
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        mContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        mContext = null;
    }
}
