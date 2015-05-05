package ui;

import items.Item;
import items.Weapon;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
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
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.text.DefaultCaret;

/**
 * @author DesktopStian
 */
public class InfoPanel extends JPanel {

    private JTextArea firstStatArea;
    private JTextArea secondStatArea;
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
    private JPanel statPanel;

    private GridBagConstraints statPanelC = new GridBagConstraints();
    private GridBagConstraints combatC = new GridBagConstraints();
    private GridBagConstraints inventoryC = new GridBagConstraints();
    private GridBagConstraints inspectC = new GridBagConstraints();
    private GridBagConstraints equipC = new GridBagConstraints();

    public InfoPanel(Frame frame) {
        this.frame = frame;
        initStatPanel();
        GridBagLayout layout = new GridBagLayout();
        setConstraints();
        setLayout(layout);

        inspect = new JButton("Inspect");
        equip = new JButton("Equip");
        addButtonStuff();
        inspect.setFocusable(false);
        equip.setFocusable(false);

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

        add(statPanel, statPanelC);
        add(jsp, combatC);
        add(jspinv, inventoryC);
        add(inspect, inspectC);
        add(equip, equipC);

    }

    public void initStatPanel() {
        statPanel = new JPanel();
        statPanel.setLayout(new GridLayout(1,1,15,0));
        firstStatArea = new JTextArea();
        secondStatArea = new JTextArea();
        
        firstStatArea.setPreferredSize(new Dimension(120,100));
        
        secondStatArea.setPreferredSize(new Dimension(120,100));
        
        Font font = new Font("Verdana", Font.BOLD, 12);
        firstStatArea.setFont(font);
        secondStatArea.setFont(font);

        String turn = Integer.toString(frame.getGame().getTurn());
        String health = Integer.toString(frame.getGame().getPlayer().getHealth());
        String maxHealth = Integer.toString(frame.getGame().getPlayer().getMaxHealth());
        String level = Integer.toString(frame.getGame().getPlayer().getStats().getLevel());
        String xp = Integer.toString(frame.getGame().getPlayer().getStats().getXp());

        firstStatArea.setText("Turn: " + turn + "\nHealth: " + health + "/" + maxHealth
                + "\nLevel: " + level + "\nXP: " + xp);

        String secondString = null;

        for (String s : frame.getGame().getPlayer().getStats().getPrimaryStats()) {
            if (secondString == null) {
                secondString = s;
            } else {
                secondString = secondString + "\n" + s;
            }
        }
        secondStatArea.setText(secondString);

        firstStatArea.setEditable(false);
        firstStatArea.setOpaque(false);
        firstStatArea.setBorder(null);

        secondStatArea.setEditable(false);
        secondStatArea.setOpaque(false);
        secondStatArea.setBorder(null);

        statPanel.add(firstStatArea);
        statPanel.add(secondStatArea);
    }

    public void setConstraints() {
        statPanelC.gridheight = 4;
        statPanelC.gridwidth = 2;
        statPanelC.gridx = 0;
        statPanelC.gridy = 0;
        statPanelC.anchor = GridBagConstraints.FIRST_LINE_START;
        statPanelC.weightx = 0.5;
        statPanelC.weighty = 0.5;

        combatC.gridheight = 4;
        combatC.gridwidth = 4;
        combatC.gridx = 2;
        combatC.gridy = 0;
        combatC.anchor = GridBagConstraints.CENTER;
        combatC.weightx = 0.5;
        combatC.weighty = 0.5;

        inspectC.gridheight = 2;
        inspectC.gridwidth = 1;
        inspectC.gridx = 7;
        inspectC.gridy = 0;
        inspectC.weighty = 0.5;
        inspectC.weightx = 0.2;
        inspectC.anchor = GridBagConstraints.LINE_END;

        equipC.gridheight = 2;
        equipC.gridwidth = 1;
        equipC.gridx = 7;
        equipC.gridy = 2;
        equipC.weightx = 0.2;
        equipC.weighty = 0.5;
        equipC.anchor = GridBagConstraints.LINE_END;

        inventoryC.gridheight = 4;
        inventoryC.gridwidth = 2;
        inventoryC.gridx = 9;
        inventoryC.gridy = 0;
        inventoryC.weighty = 0.5;
        inventoryC.weightx = 0.1;
        inventoryC.anchor = GridBagConstraints.LINE_START;
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

    public void updateInfo() {
        String turn = Integer.toString(frame.getGame().getTurn());
        String health = Integer.toString(frame.getGame().getPlayer().getHealth());
        String maxHealth = Integer.toString(frame.getGame().getPlayer().getMaxHealth());
        String level = Integer.toString(frame.getGame().getPlayer().getStats().getLevel());
        String xp = Integer.toString(frame.getGame().getPlayer().getStats().getXp());

        firstStatArea.setText("Turn: " + turn + "\nHealth: " + health + "/" + maxHealth
                + "\nLevel: " + level + "\nXP: " + xp);
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
