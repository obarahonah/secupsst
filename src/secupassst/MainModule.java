
/*
 * The MIT License
 *
 * Copyright 2020 obarahonah. https://github.com/obarahonah
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package secupassst;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author obarahonah
 */
public class MainModule extends javax.swing.JFrame {

    /**
     * Creates new form mainscreen
     */
    protected DefaultTableModel model;
    protected File file;
    protected File file_ctrl;
    protected String state;

    public MainModule() throws IOException {
        initComponents();
        model = (DefaultTableModel) table_data.getModel();
        file = new File("data");
        file_ctrl = new File("control");
        //Create the files if they dont exist
        if (file.createNewFile()) {
            file_ctrl.createNewFile();
            state = "el";
            label_statusm.setText("A new file was created.");
        } else {
            state = (String) loaddata(file_ctrl);
            if (state != null && state.compareToIgnoreCase("")!=0) {
                label_statusm.setText("Encrypted file was loaded.");
                //disabling buttons
                btn_add.setEnabled(false);
                btn_save.setEnabled(false);
                btn_remove.setEnabled(false);
                btn_copy.setEnabled(false);
                btn_copy1.setEnabled(false);
            } else {
                state = "el";
                label_statusm.setText("A new file was created.");
            }
        }
    }

    //load data from file
    public Object loaddata(File input) {
        try {
            FileInputStream fileIn = new FileInputStream(input);
            ObjectInputStream OIn = new ObjectInputStream(fileIn);
            Object obj = OIn.readObject();
            //System.out.println("Cargando..");
            OIn.close();
            return obj;
        } catch (IOException | ClassNotFoundException ex) {
            return null;
        }
    }

    //save matrix to file
    public void savedata(String data[][]) throws IOException {
        FileOutputStream fwriter = new FileOutputStream(file);
        ObjectOutputStream oos = new ObjectOutputStream(fwriter);
        oos.writeObject(data);
    }

    //save the flag archive
    public void savedata_ctrl(String data) throws IOException {
        FileOutputStream fwriter = new FileOutputStream(file_ctrl);
        ObjectOutputStream oos = new ObjectOutputStream(fwriter);
        oos.writeObject(data);
    }

    //cleaning the jtable ui
    private void cleantable() {
        int l = model.getRowCount();
        for (int i = 0; i < l; i++) {
            model.removeRow(0);
        }
    }

    //load a matrix to jtable
    private int matrixtotable(String[][] data) {
        int l = data.length;
        for (int i = 0; i < l; i++) {
            model.addRow(new Object[]{data[i][0], data[i][1], data[i][2]});
        }
        return 1;
    }

    //Remove selected rows from the in memory table(not from file)
    private void removeselected() {
        int numRows = table_data.getSelectedRows().length;
        for (int i = 0; i < numRows; i++) {

            model.removeRow(table_data.getSelectedRow());
        }
    }

    //Copy select row password to clipboard
    private void copyselected() {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

        int numRows = table_data.getSelectedRows().length;
        if (numRows >= 1) {
            //String result = model.getValueAt(jTable1.getSelectedRow(),1).toString();
            //System.out.println(result);
            Transferable transferable = new StringSelection(model.getValueAt(table_data.getSelectedRow(), 1).toString());
            clipboard.setContents(transferable, null);
            label_statusm.setText("User copied to clipboard!");
        }
        //String s = model.getValueAt(jTable1.getSelectedRow(),1).toString();

    }
    private void copyPassword() {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

        int numRows = table_data.getSelectedRows().length;
        if (numRows >= 1) {
            //String result = model.getValueAt(jTable1.getSelectedRow(),1).toString();
            //System.out.println(result);
            Transferable transferable = new StringSelection(model.getValueAt(table_data.getSelectedRow(), 2).toString());
            clipboard.setContents(transferable, null);
            label_statusm.setText("Password copied to clipboard!");
        }
        //String s = model.getValueAt(jTable1.getSelectedRow(),1).toString();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        label_password = new javax.swing.JLabel();
        password_general = new javax.swing.JPasswordField();
        scrollpanel = new javax.swing.JScrollPane();
        table_data = new javax.swing.JTable();
        btn_decrypt = new javax.swing.JButton();
        text_site = new javax.swing.JTextField();
        text_sitepassword = new javax.swing.JTextField();
        btn_add = new javax.swing.JButton();
        label_status = new javax.swing.JLabel();
        btn_save = new javax.swing.JButton();
        label_statusm = new javax.swing.JLabel();
        btn_remove = new javax.swing.JButton();
        btn_copy = new javax.swing.JButton();
        text_user = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        btn_copy1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Secupsst");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setResizable(false);

        label_password.setText("Password for file :");

        password_general.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                password_generalActionPerformed(evt);
            }
        });

        table_data.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Site", "User", "Password"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        scrollpanel.setViewportView(table_data);

        btn_decrypt.setText("Decrypt file");
        btn_decrypt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_decryptActionPerformed(evt);
            }
        });

        text_site.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_siteActionPerformed(evt);
            }
        });

        text_sitepassword.setToolTipText("");

        btn_add.setText("Add");
        btn_add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_addActionPerformed(evt);
            }
        });

        label_status.setText("Status : ");

        btn_save.setText("Save file");
        btn_save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_saveActionPerformed(evt);
            }
        });

        btn_remove.setText("Remove selected rows");
        btn_remove.setToolTipText("");
        btn_remove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_removeActionPerformed(evt);
            }
        });

        btn_copy.setText("Copy user");
        btn_copy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_copyActionPerformed(evt);
            }
        });

        text_user.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                text_userActionPerformed(evt);
            }
        });

        jLabel1.setText("Site");

        jLabel2.setText("User");

        jLabel3.setText("Password");

        btn_copy1.setText("Copy password");
        btn_copy1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_copy1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btn_remove, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(14, 14, 14)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(label_password, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(label_status, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(password_general, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btn_decrypt, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(label_statusm, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(30, 30, 30)
                            .addComponent(scrollpanel, javax.swing.GroupLayout.PREFERRED_SIZE, 542, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(31, 31, 31)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(text_site, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                                .addComponent(btn_add, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
                            .addGap(6, 6, 6)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(text_user, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE))
                            .addGap(6, 6, 6)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(text_sitepassword, javax.swing.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(btn_save, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btn_copy, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btn_copy1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label_status)
                    .addComponent(label_statusm, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(label_password)
                            .addComponent(password_general, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(7, 7, 7))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_decrypt, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)))
                .addComponent(scrollpanel, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_copy)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(text_user, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(text_sitepassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addComponent(text_site, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btn_copy1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btn_add)
                            .addComponent(btn_save, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(9, 9, 9)
                .addComponent(btn_remove)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void password_generalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_password_generalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_password_generalActionPerformed
    //Add button
    private void btn_addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_addActionPerformed
        // TODO add your handling code here:
        // if a password have been set
        if (text_sitepassword.getText().compareToIgnoreCase("")!=0) {
            if (state.equals("el")) {
                model.addRow(new Object[]{text_site.getText(), text_user.getText(), text_sitepassword.getText()});
                text_site.setText("");
                text_user.setText("");
                text_sitepassword.setText("");
                label_statusm.setText("Site added, remember to save!");
            } else {
                JOptionPane.showMessageDialog(null, "Decrypt the file before adding new data.");
            }
        }else{
            JOptionPane.showMessageDialog(null, "Add a password for the site.");
        }

    }//GEN-LAST:event_btn_addActionPerformed

    private void text_siteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_siteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_siteActionPerformed
    //Save button
    private void btn_saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_saveActionPerformed
        // TODO add your handling code here:
        try {
            if (state.equals("el")) {
                //encrypt worker so the ui dont freeze
                moduleWorker2 worker = new moduleWorker2();
                worker.execute();

                savedata_ctrl("eo");
                //jLabel3.setText(file.getPath());
                state = (String) loaddata(file_ctrl);
                label_statusm.setText("Data is being encrypted...");

            } else {
                JOptionPane.showMessageDialog(null, "The file is already encrypted.");
                label_statusm.setText("Decrypt failed, check error message.");
            }

        } catch (IOException ex) {
            Logger.getLogger(MainModule.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_btn_saveActionPerformed
    //Decrypt button
    private void btn_decryptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_decryptActionPerformed
        // TODO add your handling code here:
        try {
            if (!state.equals("el")) {
                //decrypt worker
                moduleWorker worker = new moduleWorker();
                worker.execute();

                state = "el";
            } else {
                JOptionPane.showMessageDialog(null, "The file is already decrypted.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btn_decryptActionPerformed
    //Remove rows button
    private void btn_removeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_removeActionPerformed
        // TODO add your handling code here:
        removeselected();
    }//GEN-LAST:event_btn_removeActionPerformed
    //Copy to clipboard button
    private void btn_copyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_copyActionPerformed
        // TODO add your handling code here:
        copyselected();
    }//GEN-LAST:event_btn_copyActionPerformed

    private void text_userActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_text_userActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_text_userActionPerformed

    private void btn_copy1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_copy1ActionPerformed
        // TODO add your handling code here:
        copyPassword();
    }//GEN-LAST:event_btn_copy1ActionPerformed

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
            java.util.logging.Logger.getLogger(MainModule.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainModule.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainModule.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainModule.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new mainscreen().setVisible(true);
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_add;
    private javax.swing.JButton btn_copy;
    private javax.swing.JButton btn_copy1;
    private javax.swing.JButton btn_decrypt;
    private javax.swing.JButton btn_remove;
    private javax.swing.JButton btn_save;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel label_password;
    private javax.swing.JLabel label_status;
    private javax.swing.JLabel label_statusm;
    private javax.swing.JPasswordField password_general;
    private javax.swing.JScrollPane scrollpanel;
    private javax.swing.JTable table_data;
    private javax.swing.JTextField text_site;
    private javax.swing.JTextField text_sitepassword;
    private javax.swing.JTextField text_user;
    // End of variables declaration//GEN-END:variables

    //worker for decrypting data
    public class moduleWorker extends SwingWorker<Integer, Integer> {

        @Override
        protected Integer doInBackground() throws Exception {
            btn_decrypt.setEnabled(false);
            btn_add.setEnabled(false);
            btn_save.setEnabled(false);
            btn_remove.setEnabled(false);

            String pass = new String(password_general.getPassword());

            String matrix[][] = CryptoModule.file_decrypt(pass, file);
            cleantable();
            if (matrix != null) {
                matrixtotable(matrix);
            }

            btn_decrypt.setEnabled(true);
            btn_add.setEnabled(true);
            btn_save.setEnabled(true);
            btn_remove.setEnabled(true);
            btn_copy.setEnabled(true);
            btn_copy1.setEnabled(true);
            return 1;
        }

        @Override
        protected void done() {
            //System.out.println("done()");
            //jProgressBar1.setValue(70);
        }

        @Override
        protected void process(List<Integer> chunks) {
            //jProgressBar1.setValue(chunks.get(0));
            //progress_bar(chunks.get(0), chunks.get(1));
        }

    }

    //worker for saving data
    public class moduleWorker2 extends SwingWorker<Integer, Integer> {

        @Override
        protected Integer doInBackground() throws Exception {
            if(table_data.getSelectedRows().length>0){
                table_data.clearSelection();
            }
            btn_decrypt.setEnabled(false);
            btn_add.setEnabled(false);
            btn_save.setEnabled(false);
            btn_remove.setEnabled(false);
            btn_copy.setEnabled(false);
            btn_copy1.setEnabled(false);
            String matrix[][] = new String[model.getRowCount()][model.getColumnCount()];
            String pass = new String(password_general.getPassword());
            int l = model.getRowCount();

            for (int i = 0; i < l; i++) {
                matrix[i][0] = model.getValueAt(i, 0).toString();
                matrix[i][1] = model.getValueAt(i, 1).toString();
                matrix[i][2] = model.getValueAt(i, 2).toString();
                //publish(l, i);
            }
            CryptoModule.file_encrypt(matrix, pass, file);
            //cleantable();
            for (int i = 0; i < l; i++) {
                model.removeRow(0);
            }
            btn_decrypt.setEnabled(true);
            label_statusm.setText("Data was encrypted successfully");
   
            return 1;
        }

        @Override
        protected void done() {
            //System.out.println("done()");
            //jProgressBar1.setValue(70);
        }

        @Override
        protected void process(List<Integer> chunks) {
            //jProgressBar1.setValue(chunks.get(0));
            //progress_bar(chunks.get(0), chunks.get(1));
        }

    }
}
