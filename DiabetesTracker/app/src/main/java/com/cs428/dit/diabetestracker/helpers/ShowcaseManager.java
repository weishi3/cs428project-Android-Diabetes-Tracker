package com.cs428.dit.diabetestracker.helpers;

import android.app.Activity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.cs428.dit.diabetestracker.R;
import com.github.amlcurran.showcaseview.OnShowcaseEventListener;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ViewTarget;

import java.util.ArrayList;

/**
 * Created by QiZhang on 4/15/16.
 * This class is used manage all ShowcaseViews that are shown.
 */
public class ShowcaseManager {
    private ArrayList<ShowcaseParam> showcases = new ArrayList<>(); // A list of ShowcaseViews
    private int nextIndex = 0; // The index of the next ShowcaseView to be shown

    /**
     * Add a new ShowcaseView
     *
     * @param showcaseParam A class that contain information about what to be shown
     */
    public void addNextShowcaseParam(ShowcaseParam showcaseParam) {
        showcases.add(showcaseParam);
    }

    /**
     * This method shows all showcases the showcaseManager has
     */
    public void buildAllShowcases() {
        showNext();
    }

    /**
     * Show the showcase at nextIndex
     */
    public void showNext() {
        if (nextIndex < showcases.size()) {
            //     showcases.get(nextIndex) != null
            showShowCaseView(showcases.get(nextIndex));
            nextIndex++;
        }
        return;

    }

    /**
     * Request focus on the view with id viewId
     * This method is helpful in ScrollView where some views are hidden
     * @param activity The activity that has the view
     * @param viewId The id of the view to be focused on
     */
    private void requestFocus(Activity activity, int viewId){
        View view =  activity.findViewById(viewId);
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        view.requestFocus(View.FOCUS_DOWN);
    }

    /**
     * Show the showcase given by showcaseParam
     *
     * @param showcaseParam It contains information about what to be shown for a showcase
     */
    public void showShowCaseView(ShowcaseParam showcaseParam) {
        requestFocus(showcaseParam.getMainActivity(), showcaseParam.getId());

        RelativeLayout.LayoutParams lps = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
// This aligns button to the bottom left side of screen
        lps.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        lps.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
// Set margins to the button, we add 16dp margins here
        int margin = ((Number) (showcaseParam.getMainActivity().getResources().getDisplayMetrics().density * 16)).intValue();
        lps.setMargins(margin, margin, margin, margin);


        showcaseParam.setShowcaseView(new ShowcaseView.Builder(showcaseParam.getMainActivity())
                .withMaterialShowcase()
                .setStyle(R.style.ShowcaseViewStyle)
                .setTarget(new ViewTarget(showcaseParam.getId(), showcaseParam.getMainActivity()))
                .setContentTitle(showcaseParam.getContentTitle())
                .setContentText(showcaseParam.getContentText())
                .blockAllTouches()
                .singleShot(showcaseParam.getShotId())
                .setShowcaseEventListener(new OnShowcaseEventListener() {
                    @Override
                    public void onShowcaseViewHide(ShowcaseView showcaseView) {

                    }

                    @Override
                    public void onShowcaseViewDidHide(ShowcaseView showcaseView) {
                        // When this showcaseView is dismissed, show the next showcase
                        showNext();
                    }

                    @Override
                    public void onShowcaseViewShow(ShowcaseView showcaseView) {

                    }

                    @Override
                    public void onShowcaseViewTouchBlocked(MotionEvent motionEvent) {

                    }
                }));
        ShowcaseView sv = showcaseParam.getShowcaseView().build();


        sv.setButtonPosition(lps);



    }
}
