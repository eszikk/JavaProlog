/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import entity.City;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import main.Controller;
import main.ValueListener;

/**
 *
 * @author Krisztian
 */
public class CityPanel extends javax.swing.JPanel implements ValueListener {

    private final Integer height = 500;
    private final Integer width = 200;
    private List<ValueListener> listeners = new ArrayList<ValueListener>();
    List<City> list;

    /**
     * Creates new form CityPanel
     */
    public CityPanel() {
        this.setSize(width, height);
        initComponents();
        rbtnNew.setSelected(true);
        SetGUI();
    }

    private void SetGUI() {
        if (rbtnNew.isSelected()) {
            cmbCity.setEnabled(false);
            txtName.setEnabled(true);
            txtCoordX.setEnabled(false);
            txtCoordY.setEnabled(false);

        }
        if (rbtnModDel.isSelected()) {
            cmbCity.setEnabled(true);
            txtName.setEnabled(false);
            txtCoordX.setEnabled(false);
            txtCoordY.setEnabled(false);
            SetCityToPass((City) cmbCity.getSelectedItem());

        }
        SetComboBox();
        ClearTextBoxes();

    }

    private void ClearTextBoxes() {
        txtName.setText("");
        txtCoordX.setText("");
        txtCoordY.setText("");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnGrp = new javax.swing.ButtonGroup();
        rbtnNew = new javax.swing.JRadioButton();
        rbtnModDel = new javax.swing.JRadioButton();
        cmbCity = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        txtCoordX = new javax.swing.JTextField();
        jlabel2 = new javax.swing.JLabel();
        txtCoordY = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        btnSave = new javax.swing.JButton();

        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnGrp.add(rbtnNew);
        rbtnNew.setText("New");
        rbtnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnNewActionPerformed(evt);
            }
        });
        add(rbtnNew, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 7, -1, -1));

        btnGrp.add(rbtnModDel);
        rbtnModDel.setText("Delete");
        rbtnModDel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtnModDelActionPerformed(evt);
            }
        });
        add(rbtnModDel, new org.netbeans.lib.awtextra.AbsoluteConstraints(75, 7, -1, -1));

        cmbCity.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbCityActionPerformed(evt);
            }
        });
        add(cmbCity, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 63, 158, -1));

        jLabel1.setText("Name:");
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, -1, -1));

        txtName.setText("jTextField1");
        add(txtName, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 158, -1));

        txtCoordX.setText("jTextField1");
        add(txtCoordX, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, 158, -1));

        jlabel2.setText("Coord X:");
        add(jlabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, -1, -1));

        txtCoordY.setText("jTextField1");
        add(txtCoordY, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 230, 158, -1));

        jLabel3.setText("Coord Y:");
        add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, -1, -1));

        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });
        add(btnSave, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 284, 158, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void rbtnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnNewActionPerformed
        SetGUI();
    }//GEN-LAST:event_rbtnNewActionPerformed

    private void rbtnModDelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtnModDelActionPerformed
        SetGUI();
    }//GEN-LAST:event_rbtnModDelActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed

        if (rbtnNew.isSelected()) {
            List<City> list = Controller.GetCities();
            try {
                for (City c : list) {
                    if (c.getName().equals(txtName.getText())) {
                        throw new Exception("Name must not be duplicated");
                    }
                }

                if (txtName.getText().isEmpty()) {
                    throw new Exception("Name must not be empty");
                }
                Integer Y = Integer.parseInt(txtCoordY.getText());
                Integer X = Integer.parseInt(txtCoordX.getText());
                Controller.SaveCity(txtName.getText(), X, Y);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e, "WARNING", JOptionPane.ERROR_MESSAGE);
            }
        }
        if (rbtnModDel.isSelected()) {
            Controller.DeleteCity((City) cmbCity.getSelectedItem());
        }
        notifyListeners();
        SetComboBox();
    }//GEN-LAST:event_btnSaveActionPerformed

    private void cmbCityActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbCityActionPerformed
        SetCityToPass((City) cmbCity.getSelectedItem());
    }//GEN-LAST:event_cmbCityActionPerformed

    private void SetComboBox() {
        DefaultComboBoxModel model = new DefaultComboBoxModel();
        List<City> list = Controller.GetCities();
        for (City c : list) {
            model.addElement(c);
        }
        cmbCity.setModel(model);

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup btnGrp;
    private javax.swing.JButton btnSave;
    private javax.swing.JComboBox cmbCity;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jlabel2;
    private javax.swing.JRadioButton rbtnModDel;
    private javax.swing.JRadioButton rbtnNew;
    private javax.swing.JTextField txtCoordX;
    private javax.swing.JTextField txtCoordY;
    private javax.swing.JTextField txtName;
    // End of variables declaration//GEN-END:variables

    @Override
    public void OnSubmitted(Integer tab) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void PassCoordinates(Integer X, Integer Y) {
        if (rbtnNew.isSelected()) {
            txtCoordX.setText(X.toString());
            txtCoordY.setText(Y.toString());
            SetCityToPass(new City(X, Y, " "));

        }
    }

    private void SetCityToPass(City city) {
        Controller.setForCityPanel(city);
        notifyListeners();

    }

    public void addListener(ValueListener listener) {
        listeners.add(listener);
    }

    private void notifyListeners() {
        for (ValueListener listener : listeners) {
            listener.OnSubmitted(1);
        }
    }
}
