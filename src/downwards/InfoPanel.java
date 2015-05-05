package downwards;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
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
    private JButton inspect;
    private JButton equip;
    private Frame frame;

    private GridBagConstraints turnc = new GridBagConstraints();
    private GridBagConstraints healthc = new GridBagConstraints();
    private GridBagConstraints combatc = new GridBagConstraints();
    private GridBagConstraints inventoryc = new GridBagConstraints();
    private GridBagConstraints inspectc = new GridBagConstraints();
    private GridBagConstraints equipc = new GridBagConstraints();

    public InfoPanel(Frame frame) {
        this.frame = frame;
        GridBagLayout layout = new GridBagLayout();
        setConstraints();
        setLayout(layout);

        inspect = new JButton("Inspect");
        equip = new JButton("Equip");
        addButtonStuff();
        inspect.setFocusable(false);
        equip.setFocusable(false);

        turn = new JLabel("Turn: 0");
        health = new JLabel("Health: 100/100");
        combat = new JTextArea("");
        invListModel = new DefaultListModel();
        inventory = new JList(invListModel);
        inventory.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        inventory.setFocusable(false);
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
        add(inspect, inspectc);
        add(equip, equipc);

    }

    public void setConstraints() {
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

        combatc.gridheight = 2;
        combatc.gridwidth = 2;
        combatc.gridx = 2;
        combatc.gridy = 0;
        combatc.anchor = GridBagConstraints.PAGE_START;
        combatc.weightx = 0.5;

        inspectc.gridheight = 1;
        inspectc.gridwidth = 1;
        inspectc.gridx = 5;
        inspectc.gridy = 1;
        inspectc.weightx = 0.74;
        inspectc.anchor = GridBagConstraints.LINE_END;

        equipc.gridheight = 1;
        equipc.gridwidth = 1;
        equipc.gridx = 5;
        equipc.gridy = 0;
        equipc.weightx = 0.74;
        equipc.anchor = GridBagConstraints.LINE_END;

        inventoryc.gridheight = 2;
        inventoryc.gridwidth = 2;
        inventoryc.gridx = 6;
        inventoryc.gridy = 0;
        inventoryc.weightx = 0.75;
        inventoryc.anchor = GridBagConstraints.FIRST_LINE_END;

    }

    public void addButtonStuff() {
        inspect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (inventory.getSelectedIndex() != -1) {
                    Item w = (Item) frame.getGame().player.inventory.get(inventory.getSelectedIndex());
                    String desc = w.getDescription();
                    String total = desc + "\n";
                    JOptionPane.showMessageDialog(null, total);
                }
            }
        });
        equip.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (inventory.getSelectedIndex() != -1) {
                    Object o = frame.getGame().player.inventory.get(inventory.getSelectedIndex());
                    if(o instanceof Weapon) {
                     frame.getGame().player.equipWeapon((Weapon)(frame.getGame().player.inventory.get(inventory.getSelectedIndex())));
                    }
                }
            }
        });
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
        for (Item i : s) {
            String ss = i.getName();
            invListModel.addElement(ss);
        }
    }
}
