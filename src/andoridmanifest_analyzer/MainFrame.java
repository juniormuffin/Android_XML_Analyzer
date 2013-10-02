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
 * <application>          
 * <activity>             
 * <intent-filter>                 
 * <action />                 
 * <category />                 
 * <data />            
 * </intent-filter>            
 * <meta-data />        
 * </activity>          
 * <activity-alias>             
 * <intent-filter> . . . </intent-filter>            
 * <meta-data />         
 * </activity-alias>          
 * <service>             
 * <intent-filter> . . . </intent-filter>             
 * <meta-data/>         
 * </service>          
 * <receiver>             
 * <intent-filter> . . . </intent-filter>             
 * <meta-data />         
 * </receiver>         
 * <provider>            
 * <grant-uri-permission />             
 * <meta-data />             
 * <path-permission />         
 * </provider>          
 * <uses-library />     
 * </application>  
 * </manifest>
 */
package andoridmanifest_analyzer;

import javax.swing.table.DefaultTableModel;
import manifest.ManifestReader;
import permissions.*;
import de.javasoft.plaf.synthetica.SyntheticaBlackMoonLookAndFeel; 
import javax.swing.ImageIcon;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import manifest.application.Activity;
import manifest.application.ActivityAlias;
import manifest.application.ApplicationComponent;
import manifest.application.Provider;
import manifest.application.Receiver;
import manifest.application.Service;

/**
 *
 * @author Administrator
 */
public class MainFrame extends javax.swing.JFrame {

    private Object[] columnNamesPermissions = {"No.", "Permission Name", "Type", "Description", "Score", "Remarks"};
    private Object[] columnNamesApplications = {"No.", "Application Name", "Type", "Enabled", "Intents", "Action", "Category", "Data", "Meta Data"};
    private PermissionLoader pload = new PermissionLoader();

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
        new DropFile(System.out, this.getContentPane(), /*dragBorder,*/ new DropFile.Listener() {
            public void filesDropped(java.io.File[] files) {
                ManifestReader reader = new ManifestReader();
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
                               dataApp[k][4] = "";
                               dataApp[k][5] = "";
                               dataApp[k][6] = "";
                               dataApp[k][7] = "";
                              
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
        tblApplications = new javax.swing.JScrollPane();
        tblApps = new javax.swing.JTable();

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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabDetails.addTab("Permissions", pnlPermissions);

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

        javax.swing.GroupLayout pnlApplicationsLayout = new javax.swing.GroupLayout(pnlApplications);
        pnlApplications.setLayout(pnlApplicationsLayout);
        pnlApplicationsLayout.setHorizontalGroup(
            pnlApplicationsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlApplicationsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tblApplications, javax.swing.GroupLayout.DEFAULT_SIZE, 818, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlApplicationsLayout.setVerticalGroup(
            pnlApplicationsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlApplicationsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tblApplications, javax.swing.GroupLayout.DEFAULT_SIZE, 448, Short.MAX_VALUE)
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
    private javax.swing.JTabbedPane tabDetails;
    private javax.swing.JScrollPane tblApplications;
    private javax.swing.JTable tblApps;
    private javax.swing.JTable tblPermissions;
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