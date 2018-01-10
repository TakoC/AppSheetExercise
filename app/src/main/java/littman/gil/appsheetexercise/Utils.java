package littman.gil.appsheetexercise;

import android.util.Log;

import java.util.regex.Pattern;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by Gil on 09/01/2018.
 */

public class Utils {
    private static final String TAG = Utils.class.getSimpleName();

    private static final Pattern sValidPhonePattern = Pattern.compile("^\\(?([0-9]{3})\\)?[-. ]?([0-9]{3})[-. ]?([0-9]{4})$");   //taken from https://www.safaribooksonline.com/library/view/regular-expressions-cookbook/9781449327453/ch04s02.html
    private static final String sPhonePattern = "($1) $2-$3";

    public static boolean isValidPhoneNumber(String phoneNumber) {
        return sValidPhonePattern.matcher(phoneNumber).matches();
    }

    public static String reformatValidPhoneNumber(String phoneNumber) {
        if (!isValidPhoneNumber(phoneNumber)) {
            Log.e(TAG, phoneNumber + " is not a valid phoneNumber. not doing anything");
            return phoneNumber;
        }
        return phoneNumber.replaceAll(sValidPhonePattern.pattern(), sPhonePattern);
    }

    public static OkHttpClient getHttpClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder().addInterceptor(interceptor).build();
    }
}
