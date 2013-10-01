/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package manifest;

import application.Activity;
import java.util.ArrayList;

/**
 *
 * @author Administrator
 */
public class Manifest {
    private ArrayList<String> permissions;
    private ArrayList<Activity> activities;
    private int score = 0;
    private String packageName;
    private String sdkVersion;
    private String appLabel;
    
    public Manifest()
    {
        permissions = new ArrayList();
        activities = new ArrayList();
    }

    /**
     * @return the permissions
     */
    public ArrayList<String> getPermissions() {
        return permissions;
    }

    /**
     * @param permissions the permissions to set
     */
    public void setPermissions(ArrayList<String> permissions) {
        this.permissions = permissions;
    }

    /**
     * @return the score
     */
    public int getScore() {
        return score;
    }

    /**
     * @param score the score to set
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * @return the packageName
     */
    public String getPackageName() {
        return packageName;
    }

    /**
     * @param packageName the packageName to set
     */
    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    /**
     * @return the sdkVersion
     */
    public String getSdkVersion() {
        return sdkVersion;
    }

    /**
     * @param sdkVersion the sdkVersion to set
     */
    public void setSdkVersion(String sdkVersion) {
        this.sdkVersion = sdkVersion;
    }

    /**
     * @return the appLabel
     */
    public String getAppLabel() {
        return appLabel;
    }

    /**
     * @param appLabel the appLabel to set
     */
    public void setAppLabel(String appLabel) {
        this.appLabel = appLabel;
    }

    /**
     * @return the activities
     */
    public ArrayList<Activity> getActivities() {
        return activities;
    }

    /**
     * @param activities the activities to set
     */
    public void setActivities(ArrayList<Activity> activities) {
        this.activities = activities;
    }
}
