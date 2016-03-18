package com.cs428.dit.diabetestracker.helpers;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.cs428.dit.diabetestracker.LoginActivity;
import java.util.HashMap;
import java.util.Map;
/**
 * Created by QiZhang on 2/14/16.
 * Code cited: http://www.androidhive.info/2012/08/android-session-management-using-shared-preferences
 */
public class SessionManager {
    //Sharedpreferences email key
    public static final String KEY_EMAIL = "email";
    //sharedpref file name
    private static final String PREF_NAME = "DITPref";
    private static final String IS_LOGIN = "IsLoggedIn";
    //Shared preferences
    private final SharedPreferences pref;
    //Editor for shared preferences
    private final Editor editor;
    //Application context
    private final Context _context;
    public SessionManager(Context _context) {
        this._context = _context;
        pref = _context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }
    /**
     * Create a login session. Store user email. Remember login status.
     *
     * @param email user email
     * @param userDetails map containing the user's information
     */
    public void createLoginSession(String email, Map<String, Object> userDetails) {
        editor.putString(KEY_EMAIL, email);
        editor.putBoolean(IS_LOGIN, true);
        if (!userDetails.isEmpty()) {

            editor.putInt("age", (int) (long) userDetails.get("age"));
            editor.putFloat("waistline", (float) (double) userDetails.get("waistline"));
            editor.putFloat("BMI", (float) (double) userDetails.get("BMI"));
            editor.putInt("bloodPressure", (int) (long) userDetails.get("bloodPressure"));
            editor.putBoolean("familyHistory", (boolean) userDetails.get("familyHistory"));
            editor.putBoolean("gender", (boolean)userDetails.get("gender"));

            Object temp = userDetails.get("sedentaryJob");
            if( temp != null ){
                editor.putBoolean("sedentaryJob", (boolean) temp);
            }

            temp = userDetails.get("exerciseT");
            if( temp != null ){
                editor.putInt("exerciseT", (int) (long) temp);
            }

            temp = userDetails.get("diagnosedD");
            if( temp != null ){
                editor.putBoolean("diagnosedD", (boolean) temp);
            }

            temp = userDetails.get("GDM");
            if( temp != null ){
                editor.putBoolean("GDM", (boolean) temp);
            }

            temp = userDetails.get("weightB");
            if( temp != null ){
                editor.putInt("weightB", (int) (long) temp);
            }

            temp = userDetails.get("CCVD");
            if( temp != null ){
                editor.putBoolean("CCVD", (boolean) temp);
            }

            temp = userDetails.get("PCOS");
            if( temp != null ){
                editor.putBoolean("PCOS", (boolean) temp);
            }

            temp = userDetails.get("psychotropic");
            if( temp != null ){
                editor.putBoolean("psychotropic", (boolean) temp);
            }

            temp = userDetails.get("HDL_C");
            if( temp != null ){
                editor.putFloat("HDL_C", (float)(double)temp );
            }

            temp = userDetails.get("TG");
            if( temp != null ){
                editor.putFloat("TG", (float)(double)temp );
            }

        }
        //commit changes to sharedpreferences
        editor.commit();
    }
    /**
     * Update the user's information.
     *
     * @param userDetails map containing the user's information
     */
    public void updateUserDetails(Map<String, Object> userDetails){
        editor.putInt("age", (int) userDetails.get("age"));
        editor.putFloat("waistline", (float) (double) userDetails.get("waistline"));
        editor.putFloat("BMI", (float) (double) userDetails.get("BMI"));
        editor.putInt("bloodPressure", (int) userDetails.get("bloodPressure"));
        editor.putBoolean("familyHistory", (boolean) userDetails.get("familyHistory"));
        editor.putBoolean("gender", (boolean) userDetails.get("gender"));
        Object temp = userDetails.get("sedentaryJob");
        if( temp != null ){
            editor.putBoolean("sedentaryJob", (boolean) temp);
        }

        temp = userDetails.get("exerciseT");
        if( temp != null ){
            editor.putInt("exerciseT", (int)  temp);
        }

        temp = userDetails.get("diagnosedD");
        if( temp != null ){
            editor.putBoolean("diagnosedD", (boolean) temp);
        }

        temp = userDetails.get("GDM");
        if( temp != null ){
            editor.putBoolean("GDM", (boolean) temp);
        }

        temp = userDetails.get("weightB");
        if( temp != null ){
            editor.putInt("weightB", (int) temp);
        }

        temp = userDetails.get("CCVD");
        if( temp != null ){
            editor.putBoolean("CCVD", (boolean) temp);
        }

        temp = userDetails.get("PCOS");
        if( temp != null ){
            editor.putBoolean("PCOS", (boolean) temp);
        }

        temp = userDetails.get("psychotropic");
        if( temp != null ){
            editor.putBoolean("psychotropic", (boolean) temp);
        }

        temp = userDetails.get("HDL_C");
        if( temp != null ){
            editor.putFloat("HDL_C", (float)(double)temp );
        }

        temp = userDetails.get("TG");
        if( temp != null ){
            editor.putFloat("TG", (float)(double)temp );
        }
        editor.commit();
    }
    /**
     * Get session data of the user
     *
     * @return user, a hash map containing user data.
     */
    public HashMap<String, Object> getUserDetails() {
        HashMap<String, Object> user = new HashMap<>();
        Map<String, ?> temp = pref.getAll();
        //user email
        user.put(KEY_EMAIL, temp.get(KEY_EMAIL));
        user.put("age", temp.get("age"));
        user.put("waistline", temp.get("waistline"));
        user.put("bloodPressure", temp.get("bloodPressure"));
        user.put("BMI", temp.get("BMI"));
        user.put("familyHistory", temp.get("familyHistory"));
        user.put("gender", temp.get("gender"));
        user.put("sedentaryJob", temp.get("sedentaryJob"));
        user.put("exerciseT", temp.get("exerciseT"));
        user.put("diagnosedD", temp.get("diagnosedD"));
        user.put("GDM", temp.get("GDM"));
        user.put("weightB", temp.get("weightB"));
        user.put("CCVD", temp.get("CCVD"));
        user.put("PCOS", temp.get("PCOS"));
        user.put("psychotropic", temp.get("psychotropic"));
        user.put("HDL_C", temp.get("HDL_C"));
        user.put("TG", temp.get("TG"));
        return user;
    }
    /**
     * Quick check for login
     *
     * @return True if logged in, otherwise, false.
     */
    public Boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false); //by default, not logged in.
    }
    /**
     * Set email in SharedPreferences
     */
    public void setEmailInSession(String email) {
        editor.putString(KEY_EMAIL, email);
        editor.commit();
    }
    /**
     * Clear session details
     */
    public void logoutUser() {
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();
        // After logout redirect user to Login Activity
        Intent login = new Intent(_context, LoginActivity.class);
        // Closing all the Activities
        login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // Add new Flag to start new Activity
        login.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // Starting Login Activity
        _context.startActivity(login);
    }
    public void eraseSharedPreference() {
        editor.clear();
        editor.commit();
    }
}