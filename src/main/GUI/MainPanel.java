
package main.GUI;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author Michael
 */
public class MainPanel extends javax.swing.JPanel implements KeyListener {

    /**
     * Creates new form MainPanel
     */
    public MainPanel() {
        initComponents();
        //this.jSplitPane1.setDividerLocation(90);
        this.jTabbedPane2.add("Map", new MapViewer("DD2/"));
    }

    public MapViewer getCurrentView(){
        return (MapViewer)this.jTabbedPane2.getSelectedComponent();
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane2 = new javax.swing.JTabbedPane();
        jToolBar1 = new javax.swing.JToolBar();
        jButton1 = new javax.swing.JButton();

        jToolBar1.setRollover(true);

        jButton1.setText("New Structure");
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(jButton1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 539, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane2)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 417, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables

    @Override
    public void keyTyped(KeyEvent e) {
        if(this.jTabbedPane2.getSelectedComponent() instanceof KeyListener){
            ((KeyListener)this.jTabbedPane2.getSelectedComponent()).keyTyped(e);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("TEST");
        if(this.jTabbedPane2.getSelectedComponent() instanceof KeyListener){
            ((KeyListener)this.jTabbedPane2.getSelectedComponent()).keyPressed(e);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(this.jTabbedPane2.getSelectedComponent() instanceof KeyListener){
            ((KeyListener)this.jTabbedPane2.getSelectedComponent()).keyReleased(e);
        }
    }
}
