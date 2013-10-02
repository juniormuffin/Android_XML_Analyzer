/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package manifest;

import manifest.application.Application;
import manifest.application.ApplicationComponent;
import java.util.ArrayList;

public class Manifest {
    private int score = 0;
    
    private ArrayList<String> permissions;
    private Application application;
    
    private String packageName;
    private String sharedUserId, sharedUserLabel, versionName;
    private int versionCode;
    private int sdkMinVersion, sdkMaxVersion, sdkTargetVersion;
    private InstallLocation installLocation;

    /**
     * @return the application
     */
    public Application getApplication() {
        return application;
    }

    /**
     * @param application the application to set
     */
    public void setApplication(Application application) {
        this.application = application;
    }
    
    public enum InstallLocation {
        auto,internalOnly,preferExternal;
    }
    
    /**
     * @return the sharedUserId
     */
    public String getSharedUserId() {
        return sharedUserId;
    }

    /**
     * @param sharedUserId the sharedUserId to set
     */
    public void setSharedUserId(String sharedUserId) {
        this.sharedUserId = sharedUserId;
    }

    /**
     * @return the sharedUserLavel
     */
    public String getSharedUserLabel() {
        return sharedUserLabel;
    }

    /**
     * @param sharedUserLavel the sharedUserLavel to set
     */
    public void setSharedUserLabel(String sharedUserLabel) {
        this.sharedUserLabel = sharedUserLabel;
    }

    /**
     * @return the versionName
     */
    public String getVersionName() {
        return versionName;
    }

    /**
     * @param versionName the versionName to set
     */
    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    /**
     * @return the versionCode
     */
    public int getVersionCode() {
        return versionCode;
    }

    /**
     * @param versionCode the versionCode to set
     */
    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    /**
     * @return the installLocation
     */
    public InstallLocation getInstallLocation() {
        return installLocation;
    }

    /**
     * @param installLocation the installLocation to set
     */
    public void setInstallLocation(String installLocation) {
        this.installLocation = InstallLocation.valueOf(installLocation);
    }
    
    public Manifest()
    {
        permissions = new ArrayList();
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
     * @return the sdkMinVersion
     */
    public int getSdkMinVersion() {
        return sdkMinVersion;
    }

    /**
     * @param sdkMinVersion the sdkMinVersion to set
     */
    public void setSdkMinVersion(int sdkMinVersion) {
        this.sdkMinVersion = sdkMinVersion;
    }

    /**
     * @return the sdkMaxVersion
     */
    public int getSdkMaxVersion() {
        return sdkMaxVersion;
    }

    /**
     * @param sdkMaxVersion the sdkMaxVersion to set
     */
    public void setSdkMaxVersion(int sdkMaxVersion) {
        this.sdkMaxVersion = sdkMaxVersion;
    }

    /**
     * @return the sdkTargetVersion
     */
    public int getSdkTargetVersion() {
        return sdkTargetVersion;
    }

    /**
     * @param sdkTargetVersion the sdkTargetVersion to set
     */
    public void setSdkTargetVersion(int sdkTargetVersion) {
        this.sdkTargetVersion = sdkTargetVersion;
    }
}
