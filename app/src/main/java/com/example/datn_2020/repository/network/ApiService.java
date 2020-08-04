package com.example.datn_2020.repository.network;

import com.example.datn_2020.repository.model.AddCommentModel;
import com.example.datn_2020.repository.model.AllUserResponse;
import com.example.datn_2020.repository.model.ChangeRating;
import com.example.datn_2020.repository.model.CommentResponse;
import com.example.datn_2020.repository.model.DeleteComment;
import com.example.datn_2020.repository.model.DeleteTripModel;
import com.example.datn_2020.repository.model.EditTripModel;
import com.example.datn_2020.repository.model.FavouritePlaceCheckModel;
import com.example.datn_2020.repository.model.InformationAccountModel;
import com.example.datn_2020.repository.model.NewPostModel;
import com.example.datn_2020.repository.model.NotificationResponse;
import com.example.datn_2020.repository.model.NotificationUpdateModel;
import com.example.datn_2020.repository.model.NotificationUpdateStatusModel;
import com.example.datn_2020.repository.model.PlaceInTripResponse;
import com.example.datn_2020.repository.model.PostInfoResponse;
import com.example.datn_2020.repository.model.RatingResponse;
import com.example.datn_2020.repository.model.TripByIdPlaceResponse;
import com.example.datn_2020.repository.model.TripInformationResponse;
import com.example.datn_2020.repository.model.TripModel;
import com.example.datn_2020.repository.model.TripResponse;
import com.example.datn_2020.repository.model.ApiResponse;
import com.example.datn_2020.repository.model.ChangePasswordModel;
import com.example.datn_2020.repository.model.InformationAccountResponse;
import com.example.datn_2020.repository.model.ListPlaceResponse;
import com.example.datn_2020.repository.model.LoginModel;
import com.example.datn_2020.repository.model.LoginResponse;
import com.example.datn_2020.repository.model.PlaceDetailHomeResponse;
import com.example.datn_2020.repository.model.SignUpModel;
import com.example.datn_2020.repository.model.UpdateLikeCommentModel;
import com.example.datn_2020.repository.model.UpdatePlaceInTripModel;
import com.example.datn_2020.repository.model.UpdateTimeTripModel;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    //Login
    @POST("/login")
    Observable<LoginResponse> postLogin(@Body LoginModel mLogin);

    @POST("/signup")
    Observable<ApiResponse> postSignUp(@Body SignUpModel mSignUp);

    //Home
    @GET("/getallplaceinfo")
    Observable<ListPlaceResponse> getAllPlaceInfo(@Query("idUser") int idUser);

    @GET("/getratingbyid")
    Observable<ListPlaceResponse> getRatingById(@Query("idUser") int idUser);

    @GET("/getinfoaccount")
    Observable<InformationAccountResponse> getInfoAccountRequest(@Query("idUser") int idUser);

    @POST("/updatefavourite")
    Observable<ApiResponse> postUpdateFavourite(@Body FavouritePlaceCheckModel favouritePlaceCheckModel);

    //Place
    @GET("/getplacebytype")
    Observable<ListPlaceResponse> getPlaceByType(@Query("idUser") int idUser,@Query("typePlace") String typePlace);

    @GET("/loadratingbyid")
    Observable<RatingResponse> loadRatingById(@Query("idUser") int idUser, @Query("idPlace") int idPlace);

    @POST("/updaterating")
    Observable<ApiResponse> updateRating(@Body ChangeRating updateRating);

    @POST("/dislikepost")
    Observable<ApiResponse> dislikePost(@Body UpdatePlaceInTripModel dislike);

    @POST("/likepost")
    Observable<ApiResponse> likePost(@Body UpdatePlaceInTripModel like);

    @POST("/addrating")
    Observable<ApiResponse> addRating(@Body ChangeRating addRating);

    @GET("/loadcomments")
    Observable<CommentResponse> loadComments(@Query("idUser") int idUser, @Query("idPost") int idPost);

    @POST("/addcomment")
    Observable<CommentResponse> addComment(@Body AddCommentModel addCommentModel);

    @POST("/deletecomment")
    Observable<ApiResponse> deleteComment(@Body DeleteComment deleteComment);

    @POST("/deletepost")
    Observable<ApiResponse> deletePost(@Body DeleteComment deleteComment);

    @POST("/addpost")
    Observable<ApiResponse> addPost(@Body NewPostModel newPostModel);

    //UpdatePlaceInTripModel: có sử dụng 2 id nên sử dụng luôn
    @POST("/getinfopost")
    Observable<PostInfoResponse> getInfoPost(@Body UpdatePlaceInTripModel updatePlaceInTripModel);

    //cap nhat luot like
    @POST("/updatelikecomment")
    Observable<ApiResponse> updateLikeComment(@Body UpdateLikeCommentModel updateLikeCommentModel);

    //Account
    @POST("/changepassword")
    Observable<ApiResponse> postChangePass(@Body ChangePasswordModel mChangePasswordModel);

    @POST("/changeinformationaccount")
    Observable<ApiResponse> postChangeInformationAccount(@Body InformationAccountModel informationAccountModel);

    //Trip
    @GET("/getplacebyid")
    Observable<PlaceDetailHomeResponse> getPlaceById(@Query("id") int idPlace);

    @GET("/gettrip")
    Observable<TripResponse> getTrip(@Query("idUser") int idUser);

    @POST("/postAddTrip")
    Observable<TripResponse> addTrip(@Body TripModel tripModel);

    @GET("/loadfavouriteplace")
    Observable<ListPlaceResponse> getFavouritePlace(@Query("idUser") int idUser);

    @GET("/getinformationtrip")
    Observable<TripInformationResponse> getInformationTrip(@Query("idTrip") int idTrip);

    @GET("/getplaceintrip")
    Observable<PlaceInTripResponse> getPlaceInTrip(@Query("idTrip") int idTrip, @Query("idUser") int idUser);

    @GET("/getallusername")
    Observable<AllUserResponse> getAllUsername();

    @POST("/postedittrip")
    Observable<ApiResponse> updateEditTrip(@Body EditTripModel editTripModel);

    @POST("/updatetimetrip")
    Observable<ApiResponse> updateTimeTrip(@Body UpdateTimeTripModel updateTimeTripModel);

    @POST("/deleteplaceintrip")
    Observable<ApiResponse> deletePlaceInTrip(@Body UpdatePlaceInTripModel updatePlaceInTripModel);

    @POST("/addPlaceInTrip")
    Observable<ApiResponse> addPlaceInTrip(@Body UpdatePlaceInTripModel updatePlaceInTripModel);

    @POST("/deletetrip")
    Observable<ApiResponse> deleteTrip(@Body DeleteTripModel deleteTripModel);

    @POST("/deletetripmember")
    Observable<ApiResponse> deleteTripMember(@Body DeleteTripModel deleteTripModel);

    @POST("/addusertrip")
    Observable<ApiResponse> addUserTrip(@Body DeleteTripModel deleteTripModel);

    @GET("/loadtripbyidplace")
    Observable<TripByIdPlaceResponse> loadTripByIdPlace(@Query("idUser") int idUser,@Query("idPlace") int idPlace);

    //Notification

    @POST("/jointrip")
    Observable<ApiResponse> joinTrip(@Body NotificationUpdateModel notificationUpdateModel);

    @POST("/rejecttrip")
    Observable<ApiResponse> rejectTrip(@Body NotificationUpdateModel notificationUpdateModel);

    @POST("/updatestatusnotification")
    Observable<ApiResponse> updateStatusNotification(@Body NotificationUpdateStatusModel notificationUpdateStatusModel);

    @GET("/loadnotification")
    Observable<NotificationResponse> loadNotification(@Query("idUser") int idUser);
}



