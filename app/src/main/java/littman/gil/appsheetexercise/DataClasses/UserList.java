package littman.gil.appsheetexercise.DataClasses;

import java.util.ArrayList;
import java.util.List;

import littman.gil.appsheetexercise.DataClasses.ServerModels.UsersListResponseSM;

/**
 * Created by Gil on 09/01/2018.
 */

public class UserList {
    private List<Integer> mUserIds;
    private boolean mIsDone;

    public UserList() {
        mUserIds = new ArrayList<>();
    }

    public void addUserIds(UsersListResponseSM sm) {
        for (int userId : sm.result) {
            mUserIds.add(userId);
        }
        mIsDone = sm.token == null;
    }

    public boolean isDone() {
        return mIsDone;
    }

    public List<Integer> getUserIds() {
        return mUserIds;
    }

    @Override
    public String toString() {
        return  (mIsDone ? "" : "in")
                + "complete List: "
                + mUserIds;
    }
}
