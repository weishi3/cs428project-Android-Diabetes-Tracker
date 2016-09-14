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
            editor.putBoolean("gender", (boolean) userDetails.get("gender"));
            setBoolean(userDetails, "sedentaryJob");
            setLongInt(userDetails, "exerciseT");
            setBoolean(userDetails, "diagnosedD");
            setBoolean(userDetails, "GDM");
            setLongInt(userDetails, "weightB");
            setBoolean(userDetails, "CCVD");
            setBoolean(userDetails, "PCOS");
            setBoolean(userDetails, "psychotropic");
            setFloat(userDetails, "HDL_C");
            setFloat(userDetails, "TG");
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
        setBoolean(userDetails, "sedentaryJob");
        setInt(userDetails, "exerciseT");
        setBoolean(userDetails, "diagnosedD");
        setBoolean(userDetails, "GDM");
        setInt(userDetails, "weightB");
        setBoolean(userDetails, "CCVD");
        setBoolean(userDetails, "PCOS");
        setBoolean(userDetails, "psychotropic");
        setFloat(userDetails, "HDL_C");
        setFloat(userDetails, "TG");
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

//    Helper functions to set a specific value using the data in userDetails
    /**
     * sets a decimal number value
     * @param userDetails map containing the user's information
     * @param key the parameter we are updating
     */
    private void setFloat(Map<String, Object> userDetails, String key){
        Object temp = userDetails.get(key);
        if( temp != null ){
            editor.putFloat(key, (float)(double)temp);
        } else {
            editor.remove(key);
        }
    }

    /**
     * sets a boolean number value
     * @param userDetails map containing the user's information
     * @param key the parameter we are updating
     */
    private void setBoolean(Map<String, Object> userDetails, String key){
        Object temp = userDetails.get(key);
        if( temp != null ){
            editor.putBoolean(key, (boolean) temp);
        } else {
            editor.remove(key);
        }
    }
    /**
     * sets an integer value, this will be used only when we get
     * the int from firebase, as it is stored there as a long but
     * stored in our app as an int
     * @param userDetails map containing the user's information
     * @param key the parameter we are updating
     */
    private void setLongInt(Map<String, Object> userDetails, String key){
        Object temp = userDetails.get(key);
        if( temp != null) {
            editor.putInt(key, (int) (long) temp);
        } else {
            editor.remove(key);
        }
    }
    /**
     * sets an integer value
     * @param userDetails map containing the user's information
     * @param key the parameter we are updating
     */
    private void setInt(Map<String, Object> userDetails, String key){
        Object temp = userDetails.get(key);
        if (temp != null) {
            editor.putInt(key, (int)temp);
        } else {
            editor.remove(key);
        }
    }
}