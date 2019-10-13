/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automater.ui.view;

import automater.Strings;
import automater.TextValue;
import static automater.TextValue.*;
import automater.utilities.Callback;
import automater.utilities.Description;
import automater.utilities.Resources;
import automater.utilities.SimpleCallback;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.List;

/**
 *
 * @author Bytevi
 */
public class OpenMacroForm extends javax.swing.JFrame implements BaseView {
    // UI callbacks
    public SimpleCallback onSwitchToPlayButtonCallback = SimpleCallback.createDoNothing();
    public Callback<Integer> onSelectItem = Callback.createDoNothing();
    public Callback<Integer> onClickItem = Callback.createDoNothing();
    public Callback<Integer> onDoubleClickItem = Callback.createDoNothing();
    public Callback<Integer> onOpenItem = Callback.createDoNothing();
    public Callback<Integer> onEditItem = Callback.createDoNothing();
    public Callback<Integer> onDeleteItem = Callback.createDoNothing();
    
    /**
     * Creates new form PlayForm
     */
    public OpenMacroForm() {
        initComponents();
        setup();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        switchToRecordMacrosButton = new javax.swing.JButton();
        headerText = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        macrosList = new javax.swing.JList<>();
        openMacroButton = new javax.swing.JButton();
        editMacroButton = new javax.swing.JButton();
        deleteMacroButton = new javax.swing.JButton();
        macroNameLabel = new javax.swing.JLabel();
        macroDescriptionLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        switchToRecordMacrosButton.setText("RECORD >");
        switchToRecordMacrosButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                switchToRecordMacrosButtonActionPerformed(evt);
            }
        });

        headerText.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        headerText.setText("Recorded macros");

        macrosList.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        macrosList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        macrosList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                macrosListMouseClicked(evt);
            }
        });
        macrosList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                macrosListValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(macrosList);

        openMacroButton.setText("Open");
        openMacroButton.setEnabled(false);
        openMacroButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openMacroButtonActionPerformed(evt);
            }
        });

        editMacroButton.setText("Edit");
        editMacroButton.setEnabled(false);
        editMacroButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editMacroButtonActionPerformed(evt);
            }
        });

        deleteMacroButton.setText("Delete");
        deleteMacroButton.setEnabled(false);
        deleteMacroButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteMacroButtonActionPerformed(evt);
            }
        });

        macroNameLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        macroNameLabel.setText("Select macro from the list");

        macroDescriptionLabel.setText("Description");
        macroDescriptionLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(headerText)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 236, Short.MAX_VALUE)
                        .addComponent(switchToRecordMacrosButton))
                    .addComponent(macroNameLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(openMacroButton, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(editMacroButton, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(deleteMacroButton, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(macroDescriptionLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(switchToRecordMacrosButton)
                    .addComponent(headerText))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(macroNameLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(macroDescriptionLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 63, Short.MAX_VALUE)
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(openMacroButton)
                    .addComponent(editMacroButton)
                    .addComponent(deleteMacroButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void switchToRecordMacrosButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_switchToRecordMacrosButtonActionPerformed
        onSwitchToPlayButtonCallback.perform();
    }//GEN-LAST:event_switchToRecordMacrosButtonActionPerformed

    private void openMacroButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openMacroButtonActionPerformed
        onOpenItem.perform(getSelectionIndex());
    }//GEN-LAST:event_openMacroButtonActionPerformed

    private void macrosListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_macrosListValueChanged
        selectedMacroAt(macrosList.getSelectedIndex());
    }//GEN-LAST:event_macrosListValueChanged

    private void editMacroButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editMacroButtonActionPerformed
        onEditItem.perform(getSelectionIndex());
    }//GEN-LAST:event_editMacroButtonActionPerformed

    private void deleteMacroButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteMacroButtonActionPerformed
        onDeleteItem.perform(getSelectionIndex());
    }//GEN-LAST:event_deleteMacroButtonActionPerformed

    private void macrosListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_macrosListMouseClicked
        int selectedIndex = macrosList.getSelectedIndex();
        
        selectedMacroAt(selectedIndex);
        
        // Click event
        if (evt.getClickCount() == 1)
        {
            onClickItem.perform(_selectedIndex);
        }
        
        // Double click event
        if (evt.getClickCount() == 2)
        {
            onDoubleClickItem.perform(_selectedIndex);
        }
    }//GEN-LAST:event_macrosListMouseClicked

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
            java.util.logging.Logger.getLogger(OpenMacroForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(OpenMacroForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(OpenMacroForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(OpenMacroForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new OpenMacroForm().setVisible(true);
            }
        });
    }

    private void setup() {
        ViewUtilities.setAppIconForFrame(this);
        
        this.setTitle(TextValue.getText(TextValue.Open_FormTitle));
        
        headerText.setText(TextValue.getText(TextValue.Open_HeaderText));
        
        switchToRecordMacrosButton.setText(TextValue.getText(TextValue.Open_SwitchToRecordButtonTitle));
        switchToRecordMacrosButton.setToolTipText(TextValue.getText(TextValue.Open_SwitchToRecordButtonTip));
    }
    
    // # GenericView
    
    @Override
    public void onViewStart()
    {
        macrosList.setModel(StandardDescriptionsDataSource.createGeneric());
    }
    
    @Override
    public void onViewSuspended()
    {
        
    }
    
    @Override
    public void onViewResume()
    {
        
    }
    
    @Override
    public void onViewTerminate()
    {
        
    }
    
    @Override
    public void reloadData()
    {
        
    }
    
    // # Public
    
    public void setListDataSource(StandardDescriptionsDataSource dataSource)
    {
        _dataSource = dataSource;
        
        macrosList.setModel(_dataSource);
        
        macrosList.clearSelection();
        disableMacroFunctionality();
    }
    
    public int getSelectionIndex()
    {
        return _selectedIndex;
    }
    
    // # Private
    
    private void selectedMacroAt(int index)
    {
        if (_selectedIndex == index)
        {
            return;
        }
        
        _selectedIndex = index;
        
        if (index != -1)
        {
            enableMacroFunctionality();
            updateSelectedMacroInfo(index);
        }
        else
        {
            disableMacroFunctionality();
        }
        
        onSelectItem.perform(_selectedIndex);
    }
    
    private void enableMacroFunctionality()
    {
        openMacroButton.setEnabled(true);
        editMacroButton.setEnabled(true);
        deleteMacroButton.setEnabled(true);
    }
    
    private void disableMacroFunctionality()
    {
        openMacroButton.setEnabled(false);
        editMacroButton.setEnabled(false);
        deleteMacroButton.setEnabled(false);
        
        macroNameLabel.setText(TextValue.getText(Open_DefaultMacroNameText));
        macroDescriptionLabel.setText("");
    }
    
    private void updateSelectedMacroInfo(int index)
    {
        if (_dataSource == null)
        {
            return;
        }
        
        List<Description> data = _dataSource.data;
        
        if (index < 0 || index >= data.size())
        {
            return;
        }
        
        Description macro = data.get(index);
        
        macroNameLabel.setText(macro.getName());
        macroDescriptionLabel.setText(macro.getVerbose());
        macroDescriptionLabel.setToolTipText(macro.getVerbose());
        
        Rectangle b = macroDescriptionLabel.getBounds();
        Rectangle descriptionBoundsNew = new Rectangle(b.x, b.y,macroNameLabel.getBounds().width, b.height);
        macroDescriptionLabel.setBounds(descriptionBoundsNew);
    }
    
    // Private
    private int _selectedIndex = -1;
    private StandardDescriptionsDataSource _dataSource;
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton deleteMacroButton;
    private javax.swing.JButton editMacroButton;
    private javax.swing.JLabel headerText;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel macroDescriptionLabel;
    private javax.swing.JLabel macroNameLabel;
    private javax.swing.JList<String> macrosList;
    private javax.swing.JButton openMacroButton;
    private javax.swing.JButton switchToRecordMacrosButton;
    // End of variables declaration//GEN-END:variables
}
