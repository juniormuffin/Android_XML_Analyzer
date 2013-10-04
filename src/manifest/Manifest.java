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
    private ArrayList<String> supportsGLTexture;
    private Application application;
    
    private String packageName;
    private String sharedUserId, sharedUserLabel, versionName;
    private int versionCode;
    private int sdkMinVersion, sdkMaxVersion, sdkTargetVersion;
    private InstallLocation installLocation;
    private ArrayList<Permission> permission = new ArrayList();
    private ArrayList<PermissionTree> permissionTree = new ArrayList();
    private ArrayList<PermissionGroup> permissionGroup = new ArrayList();
    private ArrayList<Instrumentation> instrumentation = new ArrayList();
    private ArrayList<Configuration> configuration = new ArrayList();
    private ArrayList<SupportScreen> supportScreen = new ArrayList();
    private ArrayList<CompatibleScreen> compatibleScreen = new ArrayList();
    private ArrayList<Feature> feature = new ArrayList();

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

    /**
     * @return the permission
     */
    public ArrayList<Permission> getPermission() {
        return permission;
    }

    /**
     * @param permission the permission to set
     */
    public void setPermission(ArrayList<Permission> permission) {
        this.permission = permission;
    }

    /**
     * @return the permissionTree
     */
    public ArrayList<PermissionTree> getPermissionTree() {
        return permissionTree;
    }

    /**
     * @param permissionTree the permissionTree to set
     */
    public void setPermissionTree(ArrayList<PermissionTree> permissionTree) {
        this.permissionTree = permissionTree;
    }

    /**
     * @return the permissionGroup
     */
    public ArrayList<PermissionGroup> getPermissionGroup() {
        return permissionGroup;
    }

    /**
     * @param permissionGroup the permissionGroup to set
     */
    public void setPermissionGroup(ArrayList<PermissionGroup> permissionGroup) {
        this.permissionGroup = permissionGroup;
    }

    /**
     * @return the supportsGLTexture
     */
    public ArrayList<String> getSupportsGLTexture() {
        return supportsGLTexture;
    }

    /**
     * @param supportsGLTexture the supportsGLTexture to set
     */
    public void setSupportsGLTexture(ArrayList<String> supportsGLTexture) {
        this.supportsGLTexture = supportsGLTexture;
    }

    /**
     * @return the instrumentation
     */
    public ArrayList<Instrumentation> getInstrumentation() {
        return instrumentation;
    }

    /**
     * @param instrumentation the instrumentation to set
     */
    public void setInstrumentation(ArrayList<Instrumentation> instrumentation) {
        this.instrumentation = instrumentation;
    }

    /**
     * @return the configuration
     */
    public ArrayList<Configuration> getConfiguration() {
        return configuration;
    }

    /**
     * @param configuration the configuration to set
     */
    public void setConfiguration(ArrayList<Configuration> configuration) {
        this.configuration = configuration;
    }

    /**
     * @return the supportScreen
     */
    public ArrayList<SupportScreen> getSupportScreen() {
        return supportScreen;
    }

    /**
     * @param supportScreen the supportScreen to set
     */
    public void setSupportScreen(ArrayList<SupportScreen> supportScreen) {
        this.supportScreen = supportScreen;
    }

    /**
     * @return the compatibleScreen
     */
    public ArrayList<CompatibleScreen> getCompatibleScreen() {
        return compatibleScreen;
    }

    /**
     * @param compatibleScreen the compatibleScreen to set
     */
    public void setCompatibleScreen(ArrayList<CompatibleScreen> compatibleScreen) {
        this.compatibleScreen = compatibleScreen;
    }

    /**
     * @return the feature
     */
    public ArrayList<Feature> getFeature() {
        return feature;
    }

    /**
     * @param feature the feature to set
     */
    public void setFeature(ArrayList<Feature> feature) {
        this.feature = feature;
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
