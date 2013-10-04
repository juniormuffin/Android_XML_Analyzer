/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package manifest;

import manifest.application.Application;

/**
 *
 * @author ongzy-001PC
 */
public class Permission {
    private String description;
    private String icon;
    private String label;
    private String name;
    private String permissionGroup;

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
     * @return the permissionGroup
     */
    public String getPermissionGroup() {
        return permissionGroup;
    }

    /**
     * @param permissionGroup the permissionGroup to set
     */
    public void setPermissionGroup(String permissionGroup) {
        this.permissionGroup = permissionGroup;
    }

    /**
     * @return the protectionLevel
     */
    public Permission.ProtectionLevel getProtectionLevel() {
        return protectionLevel;
    }

    /**
     * @param protectionLevel the protectionLevel to set
     */
    public void setProtectionLevel(Permission.ProtectionLevel protectionLevel) {
        this.protectionLevel = protectionLevel;
    }
    
    public enum ProtectionLevel {
        normal,dangerous,signature,signatureOrSystem;
    }
    private Permission.ProtectionLevel protectionLevel;
}
