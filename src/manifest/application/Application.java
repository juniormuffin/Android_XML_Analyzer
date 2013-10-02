/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package manifest.application;

import java.util.ArrayList;

/**
 *
 * @author Administrator
 */
public class Application {
    private boolean allowTaskReparenting = false;
    private boolean allowBackup = true;
    private String backupAgent = null;
    private boolean debuggable = false;
    private String description = null;
    private boolean enabled = true;
    private boolean hasCode = true;
    private boolean hardwareAccelerated = true;
    private String icon = null;    
    private boolean killAfterRestore = true;
    private boolean largeHeap = false;
    private String label = null;
    private String logo = null;
    private String manageSpaceActivity = null;
    private String name = null;
    private String permission = null;
    private boolean persistent = false;
    private String process = null;
    private boolean restoreAnyVersion = false;
    private String requiredAccountType = null;
    private String restrictedAccountType = null;
    private boolean supportsRtl = false;
    private String taskAffinity = null;
    private boolean testOnly = false;
    private String theme = null;

    public enum UiOptions {
        none, splitActionBarWhenNarrow;
    }
    private UiOptions uiOptions;
    private boolean vmSafeMode = false;
    private ArrayList<ApplicationComponent> applicationsComponent;
    private ArrayList<Library> libraries;
    
    public Application()
    {
        applicationsComponent = new ArrayList();
        libraries = new ArrayList();
    }
    
    /**
     * @return the libraries
     */
    public ArrayList<Library> getLibraries() {
        return libraries;
    }

    /**
     * @param libraries the libraries to set
     */
    public void setLibraries(ArrayList<Library> libraries) {
        this.libraries = libraries;
    }
    
    /**
     * @return the activities
     */
    public ArrayList<ApplicationComponent> getApplicationComponents() {
        return applicationsComponent;
    }

    /**
     * @param applications the applications to set
     */
    public void setApplicationComponents(ArrayList<ApplicationComponent> applications) {
        this.applicationsComponent = applications;
    }

    /**
     * @return the allowBackup
     */
    public boolean isAllowBackup() {
        return allowBackup;
    }

    /**
     * @param allowBackup the allowBackup to set
     */
    public void setAllowBackup(boolean allowBackup) {
        this.allowBackup = allowBackup;
    }

    /**
     * @return the backupAgent
     */
    public String getBackupAgent() {
        return backupAgent;
    }

    /**
     * @param backupAgent the backupAgent to set
     */
    public void setBackupAgent(String backupAgent) {
        this.backupAgent = backupAgent;
    }

    /**
     * @return the debuggable
     */
    public boolean isDebuggable() {
        return debuggable;
    }

    /**
     * @param debuggable the debuggable to set
     */
    public void setDebuggable(boolean debuggable) {
        this.debuggable = debuggable;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the enabled
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * @param enabled the enabled to set
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * @return the hasCode
     */
    public boolean isHasCode() {
        return hasCode;
    }

    /**
     * @param hasCode the hasCode to set
     */
    public void setHasCode(boolean hasCode) {
        this.hasCode = hasCode;
    }

    /**
     * @return the hardwareAccelerated
     */
    public boolean isHardwareAccelerated() {
        return hardwareAccelerated;
    }

    /**
     * @param hardwareAccelerated the hardwareAccelerated to set
     */
    public void setHardwareAccelerated(boolean hardwareAccelerated) {
        this.hardwareAccelerated = hardwareAccelerated;
    }

    /**
     * @return the icon
     */
    public String getIcon() {
        return icon;
    }

    /**
     * @param icon the icon to set
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }

    /**
     * @return the killAfterRestore
     */
    public boolean isKillAfterRestore() {
        return killAfterRestore;
    }

    /**
     * @param killAfterRestore the killAfterRestore to set
     */
    public void setKillAfterRestore(boolean killAfterRestore) {
        this.killAfterRestore = killAfterRestore;
    }

    /**
     * @return the largeHeap
     */
    public boolean isLargeHeap() {
        return largeHeap;
    }

    /**
     * @param largeHeap the largeHeap to set
     */
    public void setLargeHeap(boolean largeHeap) {
        this.largeHeap = largeHeap;
    }

    /**
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * @param label the label to set
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * @return the logo
     */
    public String getLogo() {
        return logo;
    }

    /**
     * @param logo the logo to set
     */
    public void setLogo(String logo) {
        this.logo = logo;
    }

    /**
     * @return the manageSpaceActivity
     */
    public String getManageSpaceActivity() {
        return manageSpaceActivity;
    }

    /**
     * @param manageSpaceActivity the manageSpaceActivity to set
     */
    public void setManageSpaceActivity(String manageSpaceActivity) {
        this.manageSpaceActivity = manageSpaceActivity;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the permission
     */
    public String getPermission() {
        return permission;
    }

    /**
     * @param permission the permission to set
     */
    public void setPermission(String permission) {
        this.permission = permission;
    }

    /**
     * @return the persistent
     */
    public boolean isPersistent() {
        return persistent;
    }

    /**
     * @param persistent the persistent to set
     */
    public void setPersistent(boolean persistent) {
        this.persistent = persistent;
    }

    /**
     * @return the process
     */
    public String getProcess() {
        return process;
    }

    /**
     * @param process the process to set
     */
    public void setProcess(String process) {
        this.process = process;
    }

    /**
     * @return the restoreAnyVersion
     */
    public boolean isRestoreAnyVersion() {
        return restoreAnyVersion;
    }

    /**
     * @param restoreAnyVersion the restoreAnyVersion to set
     */
    public void setRestoreAnyVersion(boolean restoreAnyVersion) {
        this.restoreAnyVersion = restoreAnyVersion;
    }

    /**
     * @return the requiredAccountType
     */
    public String getRequiredAccountType() {
        return requiredAccountType;
    }

    /**
     * @param requiredAccountType the requiredAccountType to set
     */
    public void setRequiredAccountType(String requiredAccountType) {
        this.requiredAccountType = requiredAccountType;
    }

    /**
     * @return the restrictedAccountType
     */
    public String getRestrictedAccountType() {
        return restrictedAccountType;
    }

    /**
     * @param restrictedAccountType the restrictedAccountType to set
     */
    public void setRestrictedAccountType(String restrictedAccountType) {
        this.restrictedAccountType = restrictedAccountType;
    }

    /**
     * @return the supportsRtl
     */
    public boolean isSupportsRtl() {
        return supportsRtl;
    }

    /**
     * @param supportsRtl the supportsRtl to set
     */
    public void setSupportsRtl(boolean supportsRtl) {
        this.supportsRtl = supportsRtl;
    }

    /**
     * @return the taskAffinity
     */
    public String getTaskAffinity() {
        return taskAffinity;
    }

    /**
     * @param taskAffinity the taskAffinity to set
     */
    public void setTaskAffinity(String taskAffinity) {
        this.taskAffinity = taskAffinity;
    }

    /**
     * @return the testOnly
     */
    public boolean isTestOnly() {
        return testOnly;
    }

    /**
     * @param testOnly the testOnly to set
     */
    public void setTestOnly(boolean testOnly) {
        this.testOnly = testOnly;
    }

    /**
     * @return the theme
     */
    public String getTheme() {
        return theme;
    }

    /**
     * @param theme the theme to set
     */
    public void setTheme(String theme) {
        this.theme = theme;
    }

    /**
     * @return the vmSafeMode
     */
    public boolean isVmSafeMode() {
        return vmSafeMode;
    }

    /**
     * @param vmSafeMode the vmSafeMode to set
     */
    public void setVmSafeMode(boolean vmSafeMode) {
        this.vmSafeMode = vmSafeMode;
    }

    /**
     * @return the allowTaskReparenting
     */
    public boolean isAllowTaskReparenting() {
        return allowTaskReparenting;
    }

    /**
     * @param allowTaskReparenting the allowTaskReparenting to set
     */
    public void setAllowTaskReparenting(boolean allowTaskReparenting) {
        this.allowTaskReparenting = allowTaskReparenting;
    }

    /**
     * @return the uiOptions
     */
    public UiOptions getUiOptions() {
        return uiOptions;
    }

    /**
     * @param uiOptions the uiOptions to set
     */
    public void setUiOptions(String uiOptions) {
        this.uiOptions = UiOptions.valueOf(uiOptions);
    }
}
