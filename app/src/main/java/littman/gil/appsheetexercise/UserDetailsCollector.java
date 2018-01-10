package littman.gil.appsheetexercise;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import littman.gil.appsheetexercise.DataClasses.UserDetails;
import littman.gil.appsheetexercise.DataClasses.UserList;

/**
 * Created by Gil on 09/01/2018.
 */

public class UserDetailsCollector {
    private static final String TAG = UserDetailsCollector.class.getSimpleName();

    private static final int NUMBER_OF_YOUNGEST_TO_KEEP = 5;
    private int mTotalUsers;
    private int userCounter = 0;
    private boolean hasFailed = false;
    private List<UserDetails> mUserDetailsList;

    public UserDetailsCollector(UserList userList) {
        if (!userList.isDone()) throw new RuntimeException("Calling UserDetailsCollector Ctor with an incomplete userList");
        mTotalUsers = userList.getUserIds().size();
        mUserDetailsList = new ArrayList<>();
    }

    public void addUserDetails(UserDetails userDetails) {
        ++userCounter;
        if (userDetails.isPhoneNumberValid()) {
            sortDetailsByAge();
            if (isYoungEnough(userDetails)) {
                if (mUserDetailsList.size() == NUMBER_OF_YOUNGEST_TO_KEEP) {
                    mUserDetailsList.remove(mUserDetailsList.size() - 1);
                }
                mUserDetailsList.add(userDetails);
            }
        }
    }

    public void addFailureResponse() {
        hasFailed = true;
        ++userCounter;
    }

    private boolean isYoungEnough(UserDetails user) {
        return mUserDetailsList.size() < NUMBER_OF_YOUNGEST_TO_KEEP || user.getAge() < mUserDetailsList.get(mUserDetailsList.size() - 1).getAge();
    }

    public boolean isDone() {
        return userCounter == mTotalUsers;
    }

    public boolean isSuccess() {
        return !hasFailed;
    }

    public List<UserDetails> getYoungestSortedByName() {
        List<UserDetails> sortedByName = new ArrayList<>(mUserDetailsList);
        Collections.sort(sortedByName, new Comparator<UserDetails>() {
            @Override
            public int compare(UserDetails userDetails, UserDetails t1) {
                return userDetails.getName().compareTo(t1.getName());
            }
        });

        return sortedByName;
    }

    private void sortDetailsByAge() {
        Collections.sort(mUserDetailsList, new Comparator<UserDetails>() {
            @Override
            public int compare(UserDetails userDetails, UserDetails t1) {
                return userDetails.getAge() - t1.getAge();
            }
        });
    }
}
