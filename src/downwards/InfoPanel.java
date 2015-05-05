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

    private Frame frame;
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

    public void init() {

    }

    public void setConstraints() {
        turnc.gridheight = 2;
        turnc.gridwidth = 1;
        turnc.gridx = 0;
        turnc.gridy = 0;
        turnc.anchor = GridBagConstraints.FIRST_LINE_START;
        turnc.weightx = 0;
        turnc.weighty= 0;
        
        healthc.gridheight = 2;
        healthc.gridwidth = 1;
        healthc.gridx = 0;
        healthc.gridy = 0;
        healthc.weightx = 0;
        healthc.weighty = 0;
        healthc.anchor = GridBagConstraints.LINE_START;

        combatc.gridheight = 4;
        combatc.gridwidth = 4;
        combatc.gridx = 2;
        combatc.gridy = 0;
        combatc.anchor = GridBagConstraints.CENTER;
        combatc.weightx = 0.5;
        combatc.weighty = 0.5;

        inspectc.gridheight = 2;
        inspectc.gridwidth = 1;
        inspectc.gridx = 7;
        inspectc.gridy = 0;
        inspectc.weighty = 0.5;
        inspectc.weightx = 0.2;
        inspectc.anchor = GridBagConstraints.LINE_END;

        equipc.gridheight = 2;
        equipc.gridwidth = 1;
        equipc.gridx = 7;
        equipc.gridy = 2;
        equipc.weightx = 0.2;
        equipc.weighty = 0.5;
        equipc.anchor = GridBagConstraints.LINE_END;

        inventoryc.gridheight = 4;
        inventoryc.gridwidth = 2;
        inventoryc.gridx = 9;
        inventoryc.gridy = 0;
        inventoryc.weighty = 0.5;
        inventoryc.weightx = 0.1;
        inventoryc.anchor = GridBagConstraints.LINE_START;

    }

    public void addButtonStuff() {
        inspect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (inventory.getSelectedIndex() != -1) {
                    String total = "";
                    String desc = "";
                    Object o = (Object) frame.getGame().player.inventory.get(inventory.getSelectedIndex());
                    if (o instanceof Item) {
                        Item i = (Item) o;
                        desc = i.getDescription();
                        total = desc;
                    }
                    if (o instanceof Weapon) {
                        Weapon w = (Weapon) o;
                        desc = w.getDescription() + "\n" + w.getDice() + " damage.";
                        total = desc;
                    }
                    JOptionPane.showMessageDialog(null, total);
                }
            }
        }
        );
        equip.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e
                    ) {
                        if (inventory.getSelectedIndex() != -1) {
                            Object o = frame.getGame().player.inventory.get(inventory.getSelectedIndex());
                            if (o instanceof Weapon) {
                                frame.getGame().player.equipWeapon((Weapon) (frame.getGame().player.inventory.get(inventory.getSelectedIndex())));
                            }
                        }
                    }
                }
        );
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
