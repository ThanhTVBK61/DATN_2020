package com.example.datn_2020.view.home.tabs_place_detail;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.datn_2020.R;
import com.example.datn_2020.adapter.home.CommentAdapter;
import com.example.datn_2020.repository.model.AddCommentModel;
import com.example.datn_2020.repository.model.Comment;
import com.example.datn_2020.repository.model.CommentModel;
import com.example.datn_2020.repository.model.CurrentUser;
import com.example.datn_2020.repository.model.DeleteComment;
import com.example.datn_2020.repository.model.Report;
import com.example.datn_2020.repository.model.SocketIo;
import com.example.datn_2020.repository.model.UpdateLikeCommentModel;
import com.example.datn_2020.view.account.AccountFragment;
import com.example.datn_2020.view.home.HomeFragment;
import com.example.datn_2020.view.trip.TripFragment;
import com.example.datn_2020.viewmodel.placedetail.CommentVM;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CommentFragment extends Fragment implements CommentAdapter.ItemCommentClickListener, ReportDialogFragment.SelectReportDialogInterface {

    private final String TAG = "CommentFragment";

    private Toolbar tbComment;
    private HomeFragment homeFragment;
    private TripFragment tripFragment;
    private AccountFragment accountFragment;
    private RecyclerView rvListComment;
    private EditText etInputComment;
    private ImageButton ibSend;
    private boolean isGuest = false;
    private Context mContext;
    private ArrayList<CommentModel> commentModels = new ArrayList<>();
    private CommentAdapter commentAdapter;
    private CommentVM commentVM;
    private FragmentActivity fragmentActivity;
    private int idPost;
    private int idPlace;
    private int idAdminPost;
    private String adminPost="";
    private String namePlace="";
    private String nameParent="";
    private String nameComment ="";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comment, container, false);
        Bundle mBundle = getArguments();
        if (mBundle != null) {
            idPost = mBundle.getInt("idPost");
            idPlace = mBundle.getInt("idPlace");
            idAdminPost = mBundle.getInt("idAdmin");
            adminPost = mBundle.getString("username");
            namePlace = mBundle.getString("namePlace");
            nameParent = mBundle.getString("nameParent");
            if (nameParent != null) {
                switch (nameParent) {
                    case "com/example/datn_2020/viewmodel/trip":
                        tripFragment = (TripFragment) getParentFragment();
                        break;
                    case "home":
                        homeFragment = (HomeFragment) getParentFragment();
                        break;
                    case "com/example/datn_2020/viewmodel/account":
                        accountFragment = (AccountFragment) getParentFragment();
                        break;
                }
            }
        }

        if (getActivity() != null) {
            fragmentActivity = getActivity();
        }

        registerViews(view);
        registerBackToolbar();

        if (CurrentUser.getInstance().id == -1) {
            isGuest = true;
        }

        registerRecyclerView();
        commentVM = new ViewModelProvider(this).get(CommentVM.class);

        commentVM.loadComments(CurrentUser.getInstance().id, idPost);
        Log.i(TAG, "loadComments");
        commentVM.getCommentModels().observe(getViewLifecycleOwner(), new Observer<ArrayList<CommentModel>>() {
            @Override
            public void onChanged(ArrayList<CommentModel> result) {
                commentModels = result;
                commentAdapter.setItems(commentModels);
            }
        });

        ibSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isGuest) {
                    AlertDialog alertDialog = new AlertDialog.Builder(mContext)
                            .setTitle("Thông báo")
                            .setMessage("Đăng nhập để bình luận")
                            .setNegativeButton("Đóng", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            }).create();
                    alertDialog.show();
                    Button negative = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                    negative.setTextColor(negative.getResources().getColor(R.color.colorBlue700));
                } else {
                    String comment = etInputComment.getText().toString();
                    etInputComment.getText().clear();
                    SimpleDateFormat formatter = new SimpleDateFormat("HH:mm dd/MM/yyyy");
                    Date date = new Date();
                    String time = formatter.format(date);
                    if (!comment.equals("")) {
                        if (CurrentUser.getInstance().username.equals(adminPost)) {
//                            commentVM.addComment(new AddCommentModel(CurrentUser.getInstance().id, idPost, comment, time));
//                            commentVM.getAddCommentResponse().observe(getViewLifecycleOwner(), new Observer<CommentModel>() {
//                                @Override
//                                public void onChanged(CommentModel commentModel) {
//                                    commentAdapter.addItems(commentModel);
//                                }
//                            });

                            commentVM.addComment(new AddCommentModel(CurrentUser.getInstance().id, idPost, comment, time)).observe(getViewLifecycleOwner(), new Observer<CommentModel>() {
                                @Override
                                public void onChanged(CommentModel commentModel) {
                                    commentAdapter.addItems(commentModel);
                                }
                            });

                        } else {
                            Comment newComment = new Comment();
                            newComment.setIdPlace(idPlace);
                            newComment.setContent(comment);
                            newComment.setTime(time);
                            newComment.setIdUserSend(CurrentUser.getInstance().id);
                            newComment.setIdUserReceive(idAdminPost);
                            newComment.setNamePlace(namePlace);
                            newComment.setUsernameSend(CurrentUser.getInstance().username);
                            newComment.setUsernameAdminPost(adminPost);
                            newComment.setType("Bình luận");

                            Gson gson = new Gson();
                            String mMessage = gson.toJson(newComment);
                            Log.i("Message comment", " " + mMessage);
                            SocketIo.getInstance().emit("new comment", mMessage);

//                            commentVM.addComment(new AddCommentModel(CurrentUser.getInstance().id, idPost, comment, time));
//                            commentVM.getAddCommentResponse().observe(getViewLifecycleOwner(), new Observer<CommentModel>() {
//                                @Override
//                                public void onChanged(CommentModel commentModel) {
//                                    commentAdapter.addItems(commentModel);
//                                }
//                            });

                            commentVM.addComment(new AddCommentModel(CurrentUser.getInstance().id, idPost, comment, time)).observe(getViewLifecycleOwner(), new Observer<CommentModel>() {
                                @Override
                                public void onChanged(CommentModel commentModel) {
                                    commentAdapter.addItems(commentModel);
                                }
                            });
                        }
                    }
                }
            }
        });

        return view;
    }

    private void registerViews(View view) {
        tbComment = view.findViewById(R.id.tbComment);
        rvListComment = view.findViewById(R.id.rvListComment);
        ibSend = view.findViewById(R.id.ibSend);
        etInputComment = view.findViewById(R.id.etInputComment);
    }

    private void registerRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvListComment.setLayoutManager(linearLayoutManager);
        commentAdapter = new CommentAdapter(getActivity(), isGuest, commentModels, this);
        rvListComment.setAdapter(commentAdapter);
    }

    private void registerBackToolbar() {
        tbComment.setNavigationIcon(R.drawable.ic_back);
        tbComment.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (nameParent) {
                    case "com/example/datn_2020/viewmodel/trip":
                        tripFragment.backTripStack();
                        break;
                    case "home":
                        homeFragment.backHomeStack();
                        break;
                    case "com/example/datn_2020/viewmodel/account":
                        accountFragment.backStack();
                        break;
                }
            }
        });
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

    @Override
    public void onLikeComment(int idComment, boolean like) {
        commentVM.updateLikeComment(new UpdateLikeCommentModel(CurrentUser.getInstance().id, idComment, like)).observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean res) {
                if (res) {
                    Log.i(TAG, "true");
                } else {
                    Log.i(TAG, "false");
                }
            }
        });
    }

    @Override
    public void onMoreOptionClick(final CommentModel commentModel, final int position) {
        nameComment = commentModel.getUsername();
        if (CurrentUser.getInstance().id == commentModel.getIdUser()) {
            AlertDialog alertDialog = new AlertDialog.Builder(mContext)
                    .setTitle("Thông báo")
                    .setMessage("Bạn có muốn xóa bình luận?")
                    .setNegativeButton("Đóng", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    }).setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            commentVM.deleteComment(new DeleteComment(commentModel.getIdComment()));
                            commentModels.remove(position);
                            commentAdapter.notifyDataSetChanged();
                            dialog.cancel();
                        }
                    }).create();
            alertDialog.show();

            Button negative = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
            Button positive = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
            negative.setTextColor(negative.getResources().getColor(R.color.colorGray700));
            positive.setTextColor(negative.getResources().getColor(R.color.colorBlue700));


        } else {
            ReportDialogFragment reportDialogFragment = new ReportDialogFragment();
            reportDialogFragment.setSelectReportDialogInterface(this);
            reportDialogFragment.show(getChildFragmentManager(), null);
        }
    }


    @Override
    public void selectReport(String report) {

        String filter = report.replaceAll("&","");
        String myReport = nameComment+"&"+ adminPost +"&"+ namePlace+"&"+report;

        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm dd/MM/yyyy");
        Date date = new Date();
        String time = formatter.format(date);

        Report newReport = new Report();
        newReport.setIdUserSend(CurrentUser.getInstance().id);

        newReport.setIdPlace(idPlace);
        newReport.setReport(myReport);
        newReport.setType("Phản hồi bình luận");
        newReport.setTime(time);
        newReport.setNameUserSend(CurrentUser.getInstance().username);

        Gson gson = new Gson();
        String mMessage = gson.toJson(newReport);
        Log.i("Report", " " + mMessage);
        SocketIo.getInstance().emit("new report", mMessage);
    }
}
