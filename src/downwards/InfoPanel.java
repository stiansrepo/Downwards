package downwards;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.DefaultCaret;

/**
 * @author DesktopStian
 */
public class InfoPanel extends JPanel {

    private String res = "";
    private JLabel turn;
    private JLabel health;
    private JList inventory;
    private DefaultListModel invListModel;
    private JTextArea combat;
    private JScrollPane jsp;
    private JScrollPane jspinv;

    public InfoPanel() {

        GridBagConstraints turnc = new GridBagConstraints();
        GridBagConstraints healthc = new GridBagConstraints();
        GridBagConstraints combatc = new GridBagConstraints();
        GridBagConstraints inventoryc = new GridBagConstraints();
        turnc.gridheight = 1;
        turnc.gridwidth = 1;
        turnc.gridx = 0;
        turnc.gridy = 0;
        turnc.anchor = GridBagConstraints.FIRST_LINE_START;
        turnc.weightx = 0;
        healthc.gridheight = 1;
        healthc.gridwidth = 1;
        healthc.gridx = 0;
        healthc.gridy = 1;
        healthc.weightx = 0;
        healthc.anchor = GridBagConstraints.LINE_START;

        inventoryc.gridheight = 2;
        inventoryc.gridwidth = 1;
        inventoryc.gridx = 5;
        inventoryc.gridy = 0;
        inventoryc.weightx = 0;
        inventoryc.anchor = GridBagConstraints.FIRST_LINE_END;

        combatc.gridheight = 2;
        combatc.gridwidth = 2;
        combatc.gridx = 2;
        combatc.gridy = 0;
        combatc.anchor = GridBagConstraints.PAGE_START;
        combatc.weightx = 0.5;
        GridBagLayout layout = new GridBagLayout();

        setLayout(layout);

        turn = new JLabel("Turn: 0");
        health = new JLabel("Health: 100/100");
        combat = new JTextArea("");
        invListModel = new DefaultListModel();
        inventory = new JList(invListModel);

        inventory.setSize(new Dimension(100, 80));

        combat.setEditable(false);
        combat.setSize(new Dimension(500, 80));

        DefaultCaret caret = (DefaultCaret) combat.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        jsp = new JScrollPane(combat);
        jspinv = new JScrollPane(inventory);

        jsp.setPreferredSize(new Dimension(500, 90));
        jspinv.setPreferredSize(new Dimension(200, 90));

        Font font = new Font("Verdana", Font.PLAIN, 14);
        turn.setFont(font);
        health.setFont(font);

        add(turn, turnc);
        add(health, healthc);
        add(jsp, combatc);
        add(jspinv, inventoryc);

    }

    public void updateInfo(String[] info) {
        turn.setText("Turn: " + info[0]);
        health.setText("Health: " + info[1]);
    }

    public void updateCombat(String s) {
        combat.append(s);
        combat.setCaretPosition(combat.getDocument().getLength());
        /*try {
         combat.getDocument().insertString(0, s, null);
         } catch (BadLocationException e) {
         e.printStackTrace();
         }*/
    }

    public void updateInventory(List<Item> s) {
        invListModel.clear();
        for(Item i : s){
            String ss = i.getName();
            invListModel.addElement(ss);
        }
    }
}
