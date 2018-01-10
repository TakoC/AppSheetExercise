package littman.gil.appsheetexercise.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import littman.gil.appsheetexercise.Adapters.UsersAdapter;
import littman.gil.appsheetexercise.DataClasses.UserDetails;
import littman.gil.appsheetexercise.DataManager;
import littman.gil.appsheetexercise.R;

public class MainActivity extends Activity implements View.OnClickListener, DataManager.GetYoungestValidUsersCallbacks {
    private static final String TAG = MainActivity.class.getSimpleName();

    private Button mGetUsersButton;
    private ProgressBar mProgressBar;
    private ListView mUsersListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        mGetUsersButton = findViewById(R.id.get_users_button);
        mProgressBar = findViewById(R.id.progress_bar);
        mUsersListView = findViewById(R.id.results_list_view);

        mGetUsersButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.get_users_button:
                mUsersListView.setAdapter(null);
                setLoading(true);
                DataManager.getInstance().getYoungestValidUsers(this);
                break;
        }
    }

    private void setLoading(boolean isLoading) {
        mGetUsersButton.setEnabled(!isLoading);
        mProgressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onGetYoungestValidUsersSuccess(List<UserDetails> youngestUsers) {
        setLoading(false);
        if (!youngestUsers.isEmpty()) {
            mUsersListView.setAdapter(new UsersAdapter(this, R.layout.activity_main, youngestUsers));
        } else {
            Toast.makeText(this, R.string.no_results_found, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onGetYoungestValidUsersFailure(String msg) {
        setLoading(false);
        Toast.makeText(this, R.string.loading_error_message, Toast.LENGTH_SHORT).show();
    }
}
