package littman.gil.appsheetexercise;

import littman.gil.appsheetexercise.DataClasses.ServerModels.UserDetailsResponseSM;
import littman.gil.appsheetexercise.DataClasses.ServerModels.UsersListResponseSM;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Gil on 09/01/2018.
 */

public interface AppSheetSampleService {
    @GET("list")
    Call<UsersListResponseSM> getUsersList(@Query("token") String token);

    @GET("detail/{user_id}")
    Call<UserDetailsResponseSM> getUserDetails(@Path("user_id") int userId);
}