package littman.gil.appsheetexercise.DataClasses;

import java.util.Locale;

import littman.gil.appsheetexercise.DataClasses.ServerModels.UserDetailsResponseSM;
import littman.gil.appsheetexercise.Utils;

/**
 * Created by Gil on 09/01/2018.
 */

public class UserDetails {
    private int mId;
    private String mName;
    private int mAge;
    private String mPhoneNumber;

    public UserDetails(UserDetailsResponseSM sm) {
        mId = sm.id;
        mName = sm.name;
        mAge = sm.age;
        mPhoneNumber = Utils.reformatValidPhoneNumber(sm.number);
    }

    public boolean isPhoneNumberValid() {
        return Utils.isValidPhoneNumber(mPhoneNumber);
    }

    public String getName() {
        return mName;
    }

    public int getAge() {
        return mAge;
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    @Override
    public String toString() {
        return String.format(Locale.US, "user id: %d\t name: %s\tage: %d\t phone number: %s", mId, mName, mAge, mPhoneNumber);
    }
}
