/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package manifest.application;

import java.util.ArrayList;
import manifest.application.metaData.MetaData;
import manifest.application.permission.GrantUriPermission;
import manifest.application.permission.PathPermission;

/**
 *
 * @author Administrator
 */
public class Provider extends ApplicationComponent{
    private String authorities = null;
    private boolean grantUriPermission = false;
    private int initOrder = 0;
    private boolean multiprocess = false;
    private String process = null;
    private String readPermission = null;
    private boolean syncable  = false;
    private String writePermission = null;

    private ArrayList<MetaData> metaDatas = new ArrayList();
    private ArrayList<GrantUriPermission> grantUriPermissions = new ArrayList();
    private ArrayList<PathPermission> pathPermissions = new ArrayList();
    /**
     * @return the authorities
     */
    public String getAuthorities() {
        return authorities;
    }

    /**
     * @param authorities the authorities to set
     */
    public void setAuthorities(String authorities) {
        this.authorities = authorities;
    }

    /**
     * @return the grantUriPermissions
     */
    public boolean isGrantUriPermission() {
        return grantUriPermission;
    }

    /**
     * @param grantUriPermissions the grantUriPermissions to set
     */
    public void setGrantUriPermission(boolean grantUriPermission) {
        this.grantUriPermission = grantUriPermission;
    }

    /**
     * @return the initOrder
     */
    public int getInitOrder() {
        return initOrder;
    }

    /**
     * @param initOrder the initOrder to set
     */
    public void setInitOrder(int initOrder) {
        this.initOrder = initOrder;
    }

    /**
     * @return the multiprocess
     */
    public boolean isMultiprocess() {
        return multiprocess;
    }

    /**
     * @param multiprocess the multiprocess to set
     */
    public void setMultiprocess(boolean multiprocess) {
        this.multiprocess = multiprocess;
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
     * @return the readPermission
     */
    public String getReadPermission() {
        return readPermission;
    }

    /**
     * @param readPermission the readPermission to set
     */
    public void setReadPermission(String readPermission) {
        this.readPermission = readPermission;
    }

    /**
     * @return the syncable
     */
    public boolean isSyncable() {
        return syncable;
    }

    /**
     * @param syncable the syncable to set
     */
    public void setSyncable(boolean syncable) {
        this.syncable = syncable;
    }

    /**
     * @return the writePermission
     */
    public String getWritePermission() {
        return writePermission;
    }

    /**
     * @param writePermission the writePermission to set
     */
    public void setWritePermission(String writePermission) {
        this.writePermission = writePermission;
    }

    /**
     * @return the metaDatas
     */
    public ArrayList<MetaData> getMetaDatas() {
        return metaDatas;
    }

    /**
     * @param metaDatas the metaDatas to set
     */
    public void setMetaDatas(ArrayList<MetaData> metaDatas) {
        this.metaDatas = metaDatas;
    }

    /**
     * @return the grantUriPermissions
     */
    public ArrayList<GrantUriPermission> getGrantUriPermissions() {
        return grantUriPermissions;
    }

    /**
     * @param grantUriPermissions the grantUriPermissions to set
     */
    public void setGrantUriPermissions(ArrayList<GrantUriPermission> grantUriPermissions) {
        this.grantUriPermissions = grantUriPermissions;
    }

    /**
     * @return the pathPermissions
     */
    public ArrayList<PathPermission> getPathPermissions() {
        return pathPermissions;
    }

    /**
     * @param pathPermissions the pathPermissions to set
     */
    public void setPathPermissions(ArrayList<PathPermission> pathPermissions) {
        this.pathPermissions = pathPermissions;
    }
}
