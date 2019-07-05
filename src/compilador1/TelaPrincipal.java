/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador1;

import analises1.AnaliseSintatica;
import java.awt.Dimension;
import java.awt.Toolkit;

/**
 *
 * @author valter
 */
public class TelaPrincipal extends javax.swing.JFrame {

    /**
     * Creates new form TelaPrincipal
     */
    public TelaPrincipal() {
        initComponents();
        
        setSize(540, 480);
        Dimension tela = Toolkit.getDefaultToolkit().getScreenSize();
        int larguraDaTela = tela.width;
        int alturaDaTela = tela.height;
        this.setLocation((larguraDaTela - 540) / 2, (alturaDaTela - 480) / 2);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpCodigo = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtaCodigo = new javax.swing.JTextArea();
        jbCompilar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setSize(new java.awt.Dimension(800, 800));

        jpCodigo.setLayout(new java.awt.BorderLayout());

        jtaCodigo.setColumns(20);
        jtaCodigo.setRows(5);
        jtaCodigo.setInheritsPopupMenu(true);
        jScrollPane1.setViewportView(jtaCodigo);

        jpCodigo.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        getContentPane().add(jpCodigo, java.awt.BorderLayout.CENTER);

        jbCompilar.setText("Compilar");
        jbCompilar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbCompilarActionPerformed(evt);
            }
        });
        getContentPane().add(jbCompilar, java.awt.BorderLayout.SOUTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jbCompilarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbCompilarActionPerformed
        AnaliseSintatica as = new AnaliseSintatica(jtaCodigo.getText());
    }//GEN-LAST:event_jbCompilarActionPerformed

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
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jbCompilar;
    private javax.swing.JPanel jpCodigo;
    private javax.swing.JTextArea jtaCodigo;
    // End of variables declaration//GEN-END:variables
}
