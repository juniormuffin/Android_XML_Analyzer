/*
 * <manifest>      
 * <uses-permission />     
 * <permission />     
 * <permission-tree />     
 * <permission-group />     
 * <instrumentation />     
 * <uses-sdk />     
 * <uses-configuration />       
 * <uses-feature />      
 * <supports-screens />       
 * <compatible-screens />       
 * <supports-gl-texture />        
 * 
 */
package andoridmanifest_analyzer;

import javax.swing.table.DefaultTableModel;
import manifest.ManifestReader;
import permissions.*;
import de.javasoft.plaf.synthetica.SyntheticaBlackMoonLookAndFeel; 
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeModel;
import manifest.application.Activity;
import manifest.application.ActivityAlias;
import manifest.application.Application;
import manifest.application.ApplicationComponent;
import manifest.application.Provider;
import manifest.application.Receiver;
import manifest.application.Service;
import manifest.application.intent.Action;
import manifest.application.intent.Category;
import manifest.application.intent.Data;
import manifest.application.intent.Intent;
import manifest.application.metaData.MetaData;
import manifest.application.permission.GrantUriPermission;
import manifest.application.permission.PathPermission;

/**
 *
 * @author Administrator
 */
public class MainFrame extends javax.swing.JFrame {

    private Object[] columnNamesPermissions = {"No.", "Permission Name", "Type", "Description", "Score", "Remarks"};
    private Object[] columnNamesApplications = {"No.", "Application Name", "Type", "Enabled", "Intents", "Action", "Category", "Data", "Meta Data"};
    private PermissionLoader pload = new PermissionLoader();
    private ManifestReader reader;

    /**
     * Creates new form MainFrame
     */
    public MainFrame() {
        try {
            UIManager.setLookAndFeel(new SyntheticaBlackMoonLookAndFeel());
        } catch (Exception e) {
            e.printStackTrace();
        }
        initComponents();
        this.setIconImage(new ImageIcon("./res/icon.png").getImage());
        try {
            pload.loadPermission("groupPermission.txt", 1);
            pload.loadPermission("permission.txt", 2);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        tblPermissions.setModel(new CustomTableModel(null, columnNamesPermissions));
        tblApps.setModel(new CustomTableModel(null, columnNamesApplications));
        tblApps.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        treeComponents.addTreeSelectionListener(new TreeSelectionListener(){
            public void valueChanged(TreeSelectionEvent e){
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) e.getPath().getLastPathComponent();
                System.out.println("You selected " + node);
                if(tblApps.getRowCount() > 0 && tblApps.getValueAt(tblApps.getSelectedRow(), 1).toString() != null){
                    String text = "<b>You selected: </b> Index " + tblApps.getValueAt(tblApps.getSelectedRow(), 0).toString() +" - "+ node+"<br><hr>";
                    int selectedIndex = Integer.parseInt(tblApps.getValueAt(tblApps.getSelectedRow(), 0).toString())-1;
                    
                    Application app = reader.getManifest().getApplication();
                    ApplicationComponent appComp = app.getApplicationComponents().get(selectedIndex);
                    
                    if (node.isRoot()) {
                        text += "<b>AllowTaskReparenting: </b>" + app.isAllowTaskReparenting() + "<br>";
                        text += "<b>AllowBackup: </b>" + app.isAllowBackup()+ "<br>";
                        text += "<b>BackupAgent: </b>" + app.getBackupAgent()+ "<br>";
                        text += "<b>Debuggable: </b>" + app.isDebuggable() + "<br>";
                        text += "<b>Description: </b>" + app.getDescription()+ "<br>";
                        text += "<b>Enabled: </b>" + app.isEnabled() + "<br>";
                        text += "<b>HasCode: </b>" + app.isHasCode() + "<br>";
                        text += "<b>HardwareAccelerated: </b>" + app.isHardwareAccelerated()+ "<br>";
                        text += "<b>Icon: </b>" + app.getIcon() + "<br>";
                        text += "<b>KillAfterRestore: </b>" + app.isKillAfterRestore()+ "<br>";
                        text += "<b>LargeHeap: </b>" + app.isLargeHeap()+ "<br>";
                        text += "<b>Label: </b>" + app.getLabel()+ "<br>";
                        text += "<b>Logo: </b>" + app.getLogo()+ "<br>";
                        text += "<b>ManageSpaceActivity: </b>" + app.getManageSpaceActivity()+ "<br>";
                        text += "<b>Name: </b>" + app.getName()+ "<br>";
                        text += "<b>Permission: </b>" + app.getPermission()+ "<br>";
                        text += "<b>Persistent: </b>" + app.isPersistent()+ "<br>";
                        text += "<b>Process: </b>" + app.getProcess()+ "<br>";
                        text += "<b>RestoreAnyVersion: </b>" + app.isRestoreAnyVersion()+ "<br>";
                        text += "<b>RequiredAccountType: </b>" + app.getRequiredAccountType()+ "<br>";
                        text += "<b>RestrictedAccountType: </b>" + app.getRestrictedAccountType()+ "<br>";
                        text += "<b>SupportsRtl: </b>" + app.isSupportsRtl()+ "<br>";
                        text += "<b>TaskAffinity: </b>" + app.getTaskAffinity()+ "<br>";
                        text += "<b>TestOnly: </b>" + app.isTestOnly()+ "<br>";
                        text += "<b>Theme: </b>" + app.getTheme()+ "<br>";
                        text += "<b>UIOptions: </b>" + app.getUiOptions()+ "<br>";
                        text += "<b>VmSafeMode: </b>" + app.isVmSafeMode()+ "<br>";
                    }else{        
                        DefaultMutableTreeNode node2 = (DefaultMutableTreeNode)node.getParent();
                        if(node2.isRoot()){
                            if (appComp instanceof Activity) {
                                Activity activity = (Activity) appComp;
                                text += "<b>Component: </b>Activity<br>";
                                text += "<b>AllowTaskReparenting: </b>" + activity.isAllowTaskReparenting() + "<br>";
                                text += "<b>alwaysRetainTaskState: </b>" + activity.isAlwaysRetainTaskState()+ "<br>";
                                text += "<b>ClearTaskOnLaunch: </b>" + activity.isClearTaskOnLaunch()+ "<br>";
                                text += "<b>ConfigChanges: </b>" + activity.getConfigChanges()+ "<br>";
                                text += "<b>Enabled: </b>" + activity.isEnabled() + "<br>";
                                text += "<b>ExcludeFromRecents: </b>" + activity.isExcludeFromRecents()+ "<br>";
                                text += "<b>Exported: </b>" + activity.isExported() + "<br>";
                                text += "<b>FinishOnTaskLaunch: </b>" + activity.isFinishOnTaskLaunch()+ "<br>";
                                text += "<b>HardwareAccelerated: </b>" + activity.isHardwareAccerlerated()+ "<br>";
                                text += "<b>Icon: </b>" + activity.getIcon() + "<br>";
                                text += "<b>Label: </b>" + activity.getLabel() + "<br>";
                                text += "<b>LaunchMode: </b>" + activity.getLaunchmode()+ "<br>";
                                text += "<b>Multiprocess: </b>" + activity.isMultiprocess()+ "<br>";
                                text += "<b>Name: </b>" + activity.getName() + "<br>";
                                text += "<b>NoHistory: </b>" + activity.isNoHistory() + "<br>";
                                text += "<b>ParentActivityName: </b>" + activity.getParentActivityName()+ "<br>";
                                text += "<b>Permisssion: </b>" + activity.getPermission() + "<br>";
                                text += "<b>Process: </b>" + activity.getProcess() + "<br>";
                                text += "<b>ScreenOrientation: </b>" + activity.getScreenOrientation() + "<br>";
                                text += "<b>StateNotNeeded: </b>" + activity.isStateNotNeeded()+ "<br>";
                                text += "<b>TaskAffinity: </b>" + activity.getTaskAffinity() + "<br>";
                                text += "<b>Theme: </b>" + activity.getTheme() + "<br>";
                                text += "<b>UIOptions: </b>" + activity.getUiOptions()+ "<br>";
                                text += "<b>WindowSoftInputMode: </b>" + activity.getWindowSoftInputMode()+ "<br>";
                            } else if (appComp instanceof ActivityAlias) {
                                ActivityAlias activityAlias = (ActivityAlias) appComp;
                                text += "<b>Component: </b>ActivityAlias<br>";
                                text += "<b>Enabled: </b>" + activityAlias.isEnabled() + "<br>";
                                text += "<b>Exported: </b>" + activityAlias.isExported()+ "<br>";
                                text += "<b>Icon: </b>" + activityAlias.getIcon() + "<br>";
                                text += "<b>Label: </b>" + activityAlias.getLabel() + "<br>";
                                text += "<b>Name: </b>" + activityAlias.getName() + "<br>";
                                text += "<b>Permission: </b>" + activityAlias.getPermission() + "<br>";
                                text += "<b>TargetActivity: </b>" + activityAlias.getTargetActivity()+ "<br>";
                            } else if (appComp instanceof Service) {
                                text += "<b>Component: </b>Service<br>";
                                Service service = (Service) appComp;
                                text += "<b>Enabled: </b>" + service.isEnabled() + "<br>";
                                text += "<b>Exported: </b>" + service.isExported()+ "<br>";
                                text += "<b>Icon: </b>" + service.getIcon() + "<br>";
                                text += "<b>IsolatedProcess: </b>" + service.isIsolatedProcess()+ "<br>";
                                text += "<b>Label: </b>" + service.getLabel() + "<br>";
                                text += "<b>Name: </b>" + service.getName() + "<br>";
                                text += "<b>Permission: </b>" + service.getPermission() + "<br>";
                                text += "<b>Process: </b>" + service.getProcess() + "<br>";

                            } else if (appComp instanceof Receiver) {
                                text += "<b>Component: </b>Receiver<br>";
                                Receiver receiver = (Receiver) appComp;
                                text += "<b>Enabled: </b>" + receiver.isEnabled() + "<br>";
                                text += "<b>Exported: </b>" + receiver.isExported()+ "<br>";
                                text += "<b>Icon: </b>" + receiver.getIcon() + "<br>";
                                text += "<b>Label: </b>" + receiver.getLabel() + "<br>";
                                text += "<b>Name: </b>" + receiver.getName() + "<br>";
                                text += "<b>Permission: </b>" + receiver.getPermission() + "<br>";
                                text += "<b>Process: </b>" + receiver.getProcess() + "<br>";

                            } else if (appComp instanceof Provider) {
                                text += "<b>Component: </b>Provider<br>";
                                Provider provider = (Provider) appComp;
                                text += "<b>Authorities: </b>" + provider.getAuthorities()+ "<br>";
                                text += "<b>Enabled: </b>" + provider.isEnabled() + "<br>";
                                text += "<b>Exported: </b>" + provider.isExported()+ "<br>";
                                text += "<b>GrantUriPermission: </b>" + provider.isGrantUriPermission()+ "<br>";
                                text += "<b>Icon: </b>" + provider.getIcon() + "<br>";
                                text += "<b>InitOrder: </b>" + provider.getInitOrder()+ "<br>";
                                text += "<b>Label: </b>" + provider.getLabel() + "<br>";
                                text += "<b>MultiProcess: </b>" + provider.isMultiprocess()+ "<br>";
                                text += "<b>Name: </b>" + provider.getName() + "<br>";
                                text += "<b>Permission: </b>" + provider.getPermission() + "<br>";
                                text += "<b>Process: </b>" + provider.getProcess() + "<br>";
                                text += "<b>ReadPermission: </b>" + provider.getReadPermission()+ "<br>";
                                text += "<b>Syncable: </b>" + provider.isSyncable()+ "<br>";
                                text += "<b>WritePermission: </b>" + provider.getWritePermission() + "<br>";
                            }
                        }
                    }
                    txtComponent.setText(text);  
                    txtComponent.setCaretPosition(0);
                }
            }            
        });
        
        tblApps.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                System.out.println(tblApps.getValueAt(tblApps.getSelectedRow(), 2).toString());
                txtComponent.setText("");
                //treeComponents.setModel(new TreeModel());
                if(!tblApps.getValueAt(tblApps.getSelectedRow(), 2).toString().equalsIgnoreCase("provider")){
                    int selectedIndex = Integer.parseInt(tblApps.getValueAt(tblApps.getSelectedRow(), 0).toString()) - 1;
                    ApplicationComponent appComp = reader.getManifest().getApplication().getApplicationComponents().get(selectedIndex);
                    DefaultMutableTreeNode treeNodeRoot = new DefaultMutableTreeNode("Application");
                    DefaultMutableTreeNode treeNodeAppComponent = new DefaultMutableTreeNode(tblApps.getValueAt(tblApps.getSelectedRow(), 1).toString());
                                       
                    if (appComp instanceof Activity) {
                        Activity activity = (Activity) appComp;
                        ArrayList<Intent> intents = activity.getIntents();
                        ArrayList<MetaData> metaData = activity.getMetaDatas();
                        loadData(treeNodeAppComponent, intents, metaData);
                    } else if (appComp instanceof ActivityAlias) {
                        ActivityAlias activityAlias = (ActivityAlias) appComp;
                        ArrayList<Intent> intents = activityAlias.getIntents();
                        ArrayList<MetaData> metaData = activityAlias.getMetaDatas();
                        loadData(treeNodeAppComponent, intents, metaData);
                    } else if (appComp instanceof Service) {
                        Service service = (Service) appComp;
                        ArrayList<Intent> intents = service.getIntents();
                        ArrayList<MetaData> metaData = service.getMetaDatas();
                        loadData(treeNodeAppComponent, intents, metaData);
                    } else if (appComp instanceof Receiver) {
                        Receiver receiver = (Receiver) appComp;
                        ArrayList<Intent> intents = receiver.getIntents();
                        ArrayList<MetaData> metaData = receiver.getMetaDatas();
                        loadData(treeNodeAppComponent, intents, metaData);
                    }
                    treeNodeRoot.add(treeNodeAppComponent);
                    treeComponents.setModel(new javax.swing.tree.DefaultTreeModel(treeNodeRoot));

                }else{
                    int selectedIndex = Integer.parseInt(tblApps.getValueAt(tblApps.getSelectedRow(), 0).toString()) - 1;
                    ApplicationComponent appComp = reader.getManifest().getApplication().getApplicationComponents().get(selectedIndex);
                    DefaultMutableTreeNode treeNodeRoot = new DefaultMutableTreeNode(tblApps.getValueAt(tblApps.getSelectedRow(), 1).toString());
                    Provider provider = (Provider) appComp;
                    
                    ArrayList<GrantUriPermission> grantUris = provider.getGrantUriPermissions();
                    ArrayList<MetaData> metaDatas = provider.getMetaDatas();
                    ArrayList<PathPermission> pathPermissions = provider.getPathPermissions();
                    for (int i = 0; i < grantUris.size(); i++) {
                        DefaultMutableTreeNode treeUri = new javax.swing.tree.DefaultMutableTreeNode("Grant-Uri-Permission");
                        treeNodeRoot.add(treeUri);
                    }
                    for (int i = 0; i < metaDatas.size(); i++) {
                        DefaultMutableTreeNode treeMeta = new javax.swing.tree.DefaultMutableTreeNode("Meta-data");
                        treeNodeRoot.add(treeMeta);
                    }
                    for (int i = 0; i < pathPermissions.size(); i++) {
                        DefaultMutableTreeNode treePath = new javax.swing.tree.DefaultMutableTreeNode("Path-Permission");
                        treeNodeRoot.add(treePath);
                    }
                    treeComponents.setModel(new javax.swing.tree.DefaultTreeModel(treeNodeRoot));
                }
            }
        });

        new DropFile(System.out, this.getContentPane(), /*dragBorder,*/ new DropFile.Listener() {
            public void filesDropped(java.io.File[] files) {
                reader = new ManifestReader();
                for (int i = 0; i < files.length; i++) {
                    Object[][] data = null;
                    Object[][] dataApp = null;
                    try {
                        System.out.println(files[i].getCanonicalPath());
                        reader.readManifest(files[i].getCanonicalPath());
                        reader.calculateScore(PermissionLoader.arrPermissions);
                        System.out.println("Score: " + reader.getManifest().getScore());
                        lblPackageName2.setText(reader.getManifest().getPackageName());
                        lblSDK2.setText("Min: "+reader.getManifest().getSdkMinVersion()
                                + " Max: "+reader.getManifest().getSdkMaxVersion()
                                + " Target: "+reader.getManifest().getSdkTargetVersion());
                        lblAppLabel2.setText(reader.getManifest().getApplication().getLabel());
                        lblNumPermissions2.setText("" + reader.getManifest().getPermissions().size());
                        lblNumActivities2.setText("" + reader.getManifest().getApplication().getApplicationComponents().size());
                        if (reader.getManifest().getScore() / 150 < 100) {
                            progressPermissionsScoreMatrix.setValue((int) ((reader.getManifest().getScore() / 150.0) * 100));
                        } else {
                            progressPermissionsScoreMatrix.setValue(100);
                        }
                        
                        data = new Object[reader.getManifest().getPermissions().size()][columnNamesPermissions.length];
                        for (int k = 0; k < reader.getManifest().getPermissions().size(); k++) {
                            data[k][0] = k + 1;
                            data[k][1] = reader.getManifest().getPermissions().get(k);
                            data[k][2] = "Permission Used";
                            data[k][3] = getDescription(pload, reader.getManifest().getPermissions().get(k));
                            data[k][4] = String.format("%02d", getScore(pload, reader.getManifest().getPermissions().get(k)));
                            data[k][5] = getRemarks(pload, reader.getManifest().getPermissions().get(k));
                        }
                        dataApp = new Object[reader.getManifest().getApplication().getApplicationComponents().size()][columnNamesApplications.length];
                        for (int k = 0; k < reader.getManifest().getApplication().getApplicationComponents().size(); k++) {
                           //{"No.", "Application Name", "Type", "Enabled", "Priority", "Action", "Category", "Data", "Meta Data"};
                            
                            dataApp[k][0] = k + 1;
                            dataApp[k][1] = reader.getManifest().getApplication().getApplicationComponents().get(k).getName();
                            
                            dataApp[k][3] = reader.getManifest().getApplication().getApplicationComponents().get(k).isEnabled();
                            dataApp[k][4] = "0";
                            dataApp[k][5] = "0";
                            dataApp[k][6] = "0";
                            dataApp[k][7] = "0";
                            dataApp[k][8] = "0";
                                    
                            ApplicationComponent appComp = reader.getManifest().getApplication().getApplicationComponents().get(k);
                            if(reader.getManifest().getApplication().getApplicationComponents().get(k) instanceof Activity){
                               dataApp[k][2] = "Activity";
                               
                                dataApp[k][4] = ((Activity)appComp).getIntents().size();// requires reading of intent filter
                                if(((Activity)appComp).getIntents().size() > 0){
                                    dataApp[k][5] = ((Activity)appComp).getIntents().get(0).getActions().size();//reader.getManifest().getActivities().get(k).getAction();
                                    dataApp[k][6] = ((Activity)appComp).getIntents().get(0).getCategories().size();//reader.getManifest().getActivities().get(k).getCategory();
                                    dataApp[k][7] = ((Activity)appComp).getIntents().get(0).getData().size();//reader.getManifest().getActivities().get(k).getData();
                                }
                                dataApp[k][8] = ((Activity)appComp).getMetaDatas().size();//reader.getManifest().getActivities().get(k).getMetaData();
                            }
                            else if(reader.getManifest().getApplication().getApplicationComponents().get(k) instanceof ActivityAlias){
                               dataApp[k][2] = "Activity-Alias";
                               dataApp[k][4] = ((ActivityAlias)appComp).getIntents().size();// requires reading of intent filter
                                if(((ActivityAlias)appComp).getIntents().size() > 0){
                                    dataApp[k][5] = ((ActivityAlias)appComp).getIntents().get(0).getActions().size();//reader.getManifest().getActivities().get(k).getAction();
                                    dataApp[k][6] = ((ActivityAlias)appComp).getIntents().get(0).getCategories().size();//reader.getManifest().getActivities().get(k).getCategory();
                                    dataApp[k][7] = ((ActivityAlias)appComp).getIntents().get(0).getData().size();//reader.getManifest().getActivities().get(k).getData();
                                }
                                dataApp[k][8] = ((ActivityAlias)appComp).getMetaDatas().size();//reader.getManifest().getActivities().get(k).getMetaData();
                            }
                            else if(reader.getManifest().getApplication().getApplicationComponents().get(k) instanceof Service){
                               dataApp[k][2] = "Service";
                               dataApp[k][4] = ((Service)appComp).getIntents().size();// requires reading of intent filter
                               if(((Service)appComp).getIntents().size() > 0){
                                    dataApp[k][5] = ((Service)appComp).getIntents().get(0).getActions().size();//reader.getManifest().getActivities().get(k).getAction();
                                    dataApp[k][6] = ((Service)appComp).getIntents().get(0).getCategories().size();//reader.getManifest().getActivities().get(k).getCategory();
                                    dataApp[k][7] = ((Service)appComp).getIntents().get(0).getData().size();//reader.getManifest().getActivities().get(k).getData();
                               }
                               dataApp[k][8] = ((Service)appComp).getMetaDatas().size();//reader.getManifest().getActivities().get(k).getMetaData();
                            }
                            else if(reader.getManifest().getApplication().getApplicationComponents().get(k) instanceof Receiver){
                               dataApp[k][2] = "Receiver";
                               dataApp[k][4] = ((Receiver)appComp).getIntents().size();// requires reading of intent filter
                               if(((Receiver)appComp).getIntents().size() > 0){
                                    dataApp[k][5] = ((Receiver)appComp).getIntents().get(0).getActions().size();//reader.getManifest().getActivities().get(k).getAction();
                                    dataApp[k][6] = ((Receiver)appComp).getIntents().get(0).getCategories().size();//reader.getManifest().getActivities().get(k).getCategory();
                                    dataApp[k][7] = ((Receiver)appComp).getIntents().get(0).getData().size();//reader.getManifest().getActivities().get(k).getData();
                               }
                               dataApp[k][8] = ((Receiver)appComp).getMetaDatas().size();//reader.getManifest().getActivities().get(k).getMetaData();
                            }
                            else if(reader.getManifest().getApplication().getApplicationComponents().get(k) instanceof Provider){
                               dataApp[k][2] = "Provider";
                               dataApp[k][4] = "0";
                               dataApp[k][5] = "0";
                               dataApp[k][6] = "0";
                               dataApp[k][7] = "0";
                              
                               dataApp[k][8] = ((Provider)appComp).getMetaDatas().size();//reader.getManifest().getActivities().get(k).getMetaData();
                            }
                        }
                    } 
                    catch (java.io.IOException e) {
                        e.printStackTrace();
                    } finally {
                        tblPermissions.setModel(new CustomTableModel(data, columnNamesPermissions));
                        tblApps.setModel(new CustomTableModel(dataApp, columnNamesApplications));
                    }
                }  
            }  
        }); 
    }
    
    private void loadData(DefaultMutableTreeNode treeNodeRoot, ArrayList<Intent> intents, ArrayList<MetaData> metaData)
    {
        DefaultMutableTreeNode treeNodeIntent;
        DefaultMutableTreeNode treeNodeAction;
        DefaultMutableTreeNode treeNodeCategory;
        DefaultMutableTreeNode treeNodedata;
        DefaultMutableTreeNode treeNodeMeta;
        
        for (int i = 0; i < intents.size(); i++) {
            treeNodeIntent = new DefaultMutableTreeNode("Intent-filter");
            treeNodeRoot.add(treeNodeIntent);

            ArrayList<Action> actionList = intents.get(i).getActions();
            for (int j = 0; j < actionList.size(); j++) {
                treeNodeAction = new DefaultMutableTreeNode("action");
                treeNodeIntent.add(treeNodeAction);
            }

            ArrayList<Category> catList = intents.get(i).getCategories();
            for (int j = 0; j < catList.size(); j++) {
                treeNodeAction = new DefaultMutableTreeNode("category");
                treeNodeIntent.add(treeNodeAction);
            }

            ArrayList<Data> dataList = intents.get(i).getData();
            for (int j = 0; j < dataList.size(); j++) {
                treeNodeAction = new DefaultMutableTreeNode("data");
                treeNodeIntent.add(treeNodeAction);
            }
        }
        for (int i = 0; i < metaData.size(); i++) {
            treeNodeMeta = new DefaultMutableTreeNode("Meta-data");
            treeNodeRoot.add(treeNodeMeta);
        }
    }

    private int getScore(PermissionLoader pload, String name) {
        int score = 0;
        for (int i = 0; i < pload.getArrPermissions().size(); i++) {
            if (name.toUpperCase().trim().endsWith(pload.getArrPermissions().get(i).getName().toUpperCase().trim())) {
                score = pload.getArrPermissions().get(i).getScore();
            }
        }
        return score;
    }

    private String getRemarks(PermissionLoader pload, String name) {
        String remarks = null;
        for (int i = 0; i < pload.getArrPermissions().size(); i++) {
            if (name.toUpperCase().trim().endsWith(pload.getArrPermissions().get(i).getName().toUpperCase().trim())) {
                remarks = pload.getArrPermissions().get(i).getRemarks();
            }
        }
        return remarks;
    }

    private String getDescription(PermissionLoader pload, String name) {
        String description = null;
        for (int i = 0; i < pload.getArrPermissions().size(); i++) {
            if (name.toUpperCase().trim().endsWith(pload.getArrPermissions().get(i).getName().toUpperCase().trim())) {
                description = pload.getArrPermissions().get(i).getDescription();
            }
        }
        return description;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlAPK = new javax.swing.JPanel();
        lblPackageName = new javax.swing.JLabel();
        lblPackageName2 = new javax.swing.JLabel();
        lblSDK = new javax.swing.JLabel();
        lblSDK2 = new javax.swing.JLabel();
        lblAppLabel = new javax.swing.JLabel();
        lblAppLabel2 = new javax.swing.JLabel();
        lblNumPermissions = new javax.swing.JLabel();
        lblNumPermissions2 = new javax.swing.JLabel();
        lblNumActivities = new javax.swing.JLabel();
        lblNumActivities2 = new javax.swing.JLabel();
        tabDetails = new javax.swing.JTabbedPane();
        pnlPermissions = new javax.swing.JPanel();
        lblScore = new javax.swing.JLabel();
        progressPermissionsScoreMatrix = new javax.swing.JProgressBar();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPermissions = new javax.swing.JTable();
        pnlApplications = new javax.swing.JPanel();
        split1 = new javax.swing.JSplitPane();
        tblApplications = new javax.swing.JScrollPane();
        tblApps = new javax.swing.JTable();
        split2 = new javax.swing.JSplitPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        treeComponents = new javax.swing.JTree();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtComponent = new javax.swing.JTextPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Andorid Manifest Analyzer");

        pnlAPK.setBorder(javax.swing.BorderFactory.createTitledBorder("APK Details"));

        lblPackageName.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblPackageName.setText("Package Name:");

        lblPackageName2.setText("-");

        lblSDK.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblSDK.setText("SDK Version:");

        lblSDK2.setText("-");

        lblAppLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblAppLabel.setText("App Label:");

        lblAppLabel2.setText("-");

        lblNumPermissions.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblNumPermissions.setText("No. of Permisssions:");

        lblNumPermissions2.setText("-");

        lblNumActivities.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblNumActivities.setText("No. of Activities:");

        lblNumActivities2.setText("-");

        javax.swing.GroupLayout pnlAPKLayout = new javax.swing.GroupLayout(pnlAPK);
        pnlAPK.setLayout(pnlAPKLayout);
        pnlAPKLayout.setHorizontalGroup(
            pnlAPKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAPKLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlAPKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlAPKLayout.createSequentialGroup()
                        .addGroup(pnlAPKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblPackageName)
                            .addComponent(lblSDK)
                            .addComponent(lblAppLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(pnlAPKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblAppLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 675, Short.MAX_VALUE)
                            .addComponent(lblSDK2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblPackageName2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlAPKLayout.createSequentialGroup()
                        .addGroup(pnlAPKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNumActivities)
                            .addComponent(lblNumPermissions))
                        .addGap(25, 25, 25)
                        .addGroup(pnlAPKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNumPermissions2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblNumActivities2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        pnlAPKLayout.setVerticalGroup(
            pnlAPKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAPKLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlAPKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblPackageName)
                    .addComponent(lblPackageName2, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlAPKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblSDK, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblSDK2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlAPKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblAppLabel)
                    .addComponent(lblAppLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlAPKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblNumPermissions)
                    .addComponent(lblNumPermissions2, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlAPKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblNumActivities, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblNumActivities2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(24, 24, 24))
        );

        lblScore.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblScore.setText("Score Matrix:");

        progressPermissionsScoreMatrix.setValue(50);

        tblPermissions.setAutoCreateRowSorter(true);
        tblPermissions.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tblPermissions);

        javax.swing.GroupLayout pnlPermissionsLayout = new javax.swing.GroupLayout(pnlPermissions);
        pnlPermissions.setLayout(pnlPermissionsLayout);
        pnlPermissionsLayout.setHorizontalGroup(
            pnlPermissionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPermissionsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlPermissionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 818, Short.MAX_VALUE)
                    .addGroup(pnlPermissionsLayout.createSequentialGroup()
                        .addComponent(lblScore)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(progressPermissionsScoreMatrix, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnlPermissionsLayout.setVerticalGroup(
            pnlPermissionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPermissionsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlPermissionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(progressPermissionsScoreMatrix, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblScore))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 447, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabDetails.addTab("Permissions", pnlPermissions);

        split1.setDividerLocation(150);
        split1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        split1.setPreferredSize(new java.awt.Dimension(454, 733));

        tblApps.setAutoCreateRowSorter(true);
        tblApps.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblApplications.setViewportView(tblApps);

        split1.setTopComponent(tblApplications);

        split2.setDividerLocation(250);

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("Application");
        javax.swing.tree.DefaultMutableTreeNode treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Application Component");
        javax.swing.tree.DefaultMutableTreeNode treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Intent-filter");
        javax.swing.tree.DefaultMutableTreeNode treeNode4 = new javax.swing.tree.DefaultMutableTreeNode("action");
        treeNode3.add(treeNode4);
        treeNode4 = new javax.swing.tree.DefaultMutableTreeNode("category");
        treeNode3.add(treeNode4);
        treeNode4 = new javax.swing.tree.DefaultMutableTreeNode("data");
        treeNode3.add(treeNode4);
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Meta-data");
        treeNode1.add(treeNode2);
        treeComponents.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        jScrollPane2.setViewportView(treeComponents);

        split2.setLeftComponent(jScrollPane2);

        txtComponent.setContentType("text/html"); // NOI18N
        jScrollPane3.setViewportView(txtComponent);

        split2.setRightComponent(jScrollPane3);

        split1.setRightComponent(split2);

        javax.swing.GroupLayout pnlApplicationsLayout = new javax.swing.GroupLayout(pnlApplications);
        pnlApplications.setLayout(pnlApplicationsLayout);
        pnlApplicationsLayout.setHorizontalGroup(
            pnlApplicationsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlApplicationsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(split1, javax.swing.GroupLayout.DEFAULT_SIZE, 818, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlApplicationsLayout.setVerticalGroup(
            pnlApplicationsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlApplicationsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(split1, javax.swing.GroupLayout.DEFAULT_SIZE, 467, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabDetails.addTab("Applications", pnlApplications);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(tabDetails)
                    .addComponent(pnlAPK, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlAPK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabDetails)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblAppLabel;
    private javax.swing.JLabel lblAppLabel2;
    private javax.swing.JLabel lblNumActivities;
    private javax.swing.JLabel lblNumActivities2;
    private javax.swing.JLabel lblNumPermissions;
    private javax.swing.JLabel lblNumPermissions2;
    private javax.swing.JLabel lblPackageName;
    private javax.swing.JLabel lblPackageName2;
    private javax.swing.JLabel lblSDK;
    private javax.swing.JLabel lblSDK2;
    private javax.swing.JLabel lblScore;
    private javax.swing.JPanel pnlAPK;
    private javax.swing.JPanel pnlApplications;
    private javax.swing.JPanel pnlPermissions;
    private javax.swing.JProgressBar progressPermissionsScoreMatrix;
    private javax.swing.JSplitPane split1;
    private javax.swing.JSplitPane split2;
    private javax.swing.JTabbedPane tabDetails;
    private javax.swing.JScrollPane tblApplications;
    private javax.swing.JTable tblApps;
    private javax.swing.JTable tblPermissions;
    private javax.swing.JTree treeComponents;
    private javax.swing.JTextPane txtComponent;
    // End of variables declaration//GEN-END:variables
}

class CustomTableModel extends DefaultTableModel {

    Object rowData[][];

    public CustomTableModel(Object rowData[][], Object columnNames[]) {
        super(rowData, columnNames);
        this.rowData = rowData;
    }

    public boolean isCellEditable(int row, int col) {
        return false;
    }

    public Object getValueAt(int row, int col) {
        return rowData[row][col];
    }
}