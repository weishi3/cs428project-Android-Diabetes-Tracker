package com.cs428.dit.diabetestracker.helpers;

import android.app.Activity;

import com.github.amlcurran.showcaseview.ShowcaseView;

/**
 * Created by QiZhang on 4/15/16.
 * This class contains all the information the ShowcaseView needs.
 */
public class ShowcaseParam {
    private final Activity mainActivity; // The activity on which the ShowcaseView is shown
    private final int id; // The View that needs a tooltip
    private final String contentTitle; // Tooltip title
    private final String contentText; // Tooltip content
    private final int shotId; // A unique ID for a ShowcaseView, use this ensure the ShowcaseView is shown once
    private ShowcaseView.Builder showcaseView; // The ShowcaseView

    /**
     * Constructor
     *
     * @param mainActivity The activity on which the ShowcaseView shows
     * @param id           The View that needs a tooltip
     * @param contentTitle Tooltip title
     * @param contentText  Tooltip content
     * @param shotId       A unique ID for a ShowcaseView, use this ensure the ShowcaseView is shown once
     * @param showcaseView The ShowcaseView
     */
    public ShowcaseParam(Activity mainActivity, int id, String contentTitle, String contentText, int shotId, ShowcaseView.Builder showcaseView) {
        this.mainActivity = mainActivity;
        this.id = id;
        this.contentTitle = contentTitle;
        this.contentText = contentText;
        this.shotId = shotId;
        this.showcaseView = showcaseView;
    }

    /**
     * @return Activity This returns the Activity on which the tooltip is shown
     */
    public Activity getMainActivity() {
        return mainActivity;
    }

    /**
     * Return View ID
     *
     * @return int The view ID
     */
    public int getId() {
        return id;
    }

    /**
     * @return String Tooltip title
     */
    public String getContentTitle() {
        return contentTitle;
    }

    /**
     * @return String Tooltip content
     */
    public String getContentText() {
        return contentText;
    }

    /**
     * @return int A unique ID for the ShowcaseView
     */
    public int getShotId() {
        return shotId;
    }

    /**
     * @return ShowcaseView.Builder A class used to show ShowcaseView
     */
    public ShowcaseView.Builder getShowcaseView() {
        return showcaseView;
    }

    /**
     * @param showcaseView The ShowcaseView to set to
     */
    public void setShowcaseView(ShowcaseView.Builder showcaseView) {
        this.showcaseView = showcaseView;
    }
}
