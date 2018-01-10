package littman.gil.appsheetexercise;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.List;

import littman.gil.appsheetexercise.DataClasses.ServerModels.UserDetailsResponseSM;
import littman.gil.appsheetexercise.DataClasses.ServerModels.UsersListResponseSM;
import littman.gil.appsheetexercise.DataClasses.UserDetails;
import littman.gil.appsheetexercise.DataClasses.UserList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Gil on 09/01/2018.
 */

public class DataManager {
    private static final String TAG = DataManager.class.getSimpleName();

    private static DataManager INSTANCE;

    private AppSheetSampleService mService;
    private static final String BASE_URL = "https://appsheettest1.azurewebsites.net/sample/";
    private UserList mUsersList;
    private GetYoungestValidUsersCallbacks mCallbacks;

    private DataManager() {

    }

    public static DataManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DataManager();
        }

        return INSTANCE;
    }

    public void getYoungestValidUsers(GetYoungestValidUsersCallbacks callbacks) {
        if (mService == null) {
            initService();
        }

        mUsersList = null;
        mCallbacks = callbacks;
        getUsersList(null);
    }

    private void getUsersList(@Nullable String token) {
        Call<UsersListResponseSM> usersListCall = mService.getUsersList(token);
        usersListCall.enqueue(new Callback<UsersListResponseSM>() {
            @Override
            public void onResponse(@NonNull Call<UsersListResponseSM> call, @NonNull Response<UsersListResponseSM> response) {
                UsersListResponseSM usersListResponseSM = response.body();
                if (usersListResponseSM == null) {
                    if (mCallbacks != null) {
                        mCallbacks.onGetYoungestValidUsersFailure("null response from sever");
                    }
                    return;
                }

                if (mUsersList == null) {
                    mUsersList = new UserList();
                }
                mUsersList.addUserIds(usersListResponseSM);

                if (mUsersList.isDone()) {
                    getAllUserDetails();
                } else {
                    getUsersList(usersListResponseSM.token);
                }
            }

            @Override
            public void onFailure(@NonNull Call<UsersListResponseSM> call, @NonNull Throwable t) {
                Log.e(TAG, "userList onFailure: " + t.getMessage());
                if(mCallbacks != null) {
                    mCallbacks.onGetYoungestValidUsersFailure(t.getMessage());
                }

            }
        });
    }

    private void getAllUserDetails() {
        final UserDetailsCollector collector = new UserDetailsCollector(mUsersList);
        for (int userId : mUsersList.getUserIds()) {
            Call<UserDetailsResponseSM> userDetailsCall = mService.getUserDetails(userId);
            userDetailsCall.enqueue(new Callback<UserDetailsResponseSM>() {
                @Override
                public void onResponse(Call<UserDetailsResponseSM> call, Response<UserDetailsResponseSM> response) {
                    UserDetailsResponseSM sm = response.body();
                    if (sm == null) {
                        Log.e(TAG, "null response from sever");
                        return;
                    }

                    collector.addUserDetails(new UserDetails(sm));
                    if (collector.isDone() && mCallbacks != null) {
                        if (collector.isSuccess()) {
                            mCallbacks.onGetYoungestValidUsersSuccess(collector.getYoungestSortedByName());
                        } else {
                            mCallbacks.onGetYoungestValidUsersFailure("Failed to get all user details");
                        }
                    }

                }

                @Override
                public void onFailure(Call<UserDetailsResponseSM> call, Throwable t) {
                    Log.e(TAG, "userDetails onFailure: " + t.getMessage());
                    collector.addFailureResponse();
                }
            });
        }
    }

    private void initService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(Utils.getHttpClient())      //logging
                .build();

        mService = retrofit.create(AppSheetSampleService.class);
    }


    public interface GetYoungestValidUsersCallbacks {
        void onGetYoungestValidUsersSuccess(List<UserDetails> youngestUsers);
        void onGetYoungestValidUsersFailure(String msg);
    }
}
