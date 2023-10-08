package de.exware.gwtswing.sample;

import de.exware.gplatform.GPlatform;
import de.exware.gwtswing.Constants;
import de.exware.gwtswing.PartitionedPanel;
import de.exware.gwtswing.StringRenderer;
import de.exware.gwtswing.awt.GBorderLayout;
import de.exware.gwtswing.awt.GCardLayout;
import de.exware.gwtswing.awt.GColor;
import de.exware.gwtswing.awt.GDimension;
import de.exware.gwtswing.awt.GGridBagConstraints;
import de.exware.gwtswing.awt.GGridBagLayout;
import de.exware.gwtswing.awt.GGridLayout;
import de.exware.gwtswing.awt.event.GActionEvent;
import de.exware.gwtswing.awt.event.GActionListener;
import de.exware.gwtswing.awt.event.GMouseAdapter;
import de.exware.gwtswing.awt.event.GMouseEvent;
import de.exware.gwtswing.swing.GAbstractAction;
import de.exware.gwtswing.swing.GButton;
import de.exware.gwtswing.swing.GButtonGroup;
import de.exware.gwtswing.swing.GCheckBox;
import de.exware.gwtswing.swing.GComboBox;
import de.exware.gwtswing.swing.GComponent;
import de.exware.gwtswing.swing.GDefaultListCellRenderer;
import de.exware.gwtswing.swing.GFrame;
import de.exware.gwtswing.swing.GImageIcon;
import de.exware.gwtswing.swing.GLabel;
import de.exware.gwtswing.swing.GList;
import de.exware.gwtswing.swing.GMenu;
import de.exware.gwtswing.swing.GMenuBar;
import de.exware.gwtswing.swing.GOptionPane;
import de.exware.gwtswing.swing.GPanel;
import de.exware.gwtswing.swing.GPasswordField;
import de.exware.gwtswing.swing.GProgressBar;
import de.exware.gwtswing.swing.GRadioButton;
import de.exware.gwtswing.swing.GScrollPane;
import de.exware.gwtswing.swing.GSlider;
import de.exware.gwtswing.swing.GSplitPane;
import de.exware.gwtswing.swing.GTabbedPane;
import de.exware.gwtswing.swing.GTable;
import de.exware.gwtswing.swing.GTextArea;
import de.exware.gwtswing.swing.GTextField;
import de.exware.gwtswing.swing.GToggleButton;
import de.exware.gwtswing.swing.GTree;
import de.exware.gwtswing.swing.GUtilities;
import de.exware.gwtswing.swing.border.GBevelBorder;
import de.exware.gwtswing.swing.border.GBorderFactory;
import de.exware.gwtswing.swing.border.GEtchedBorder;
import de.exware.gwtswing.swing.border.GLineBorder;
import de.exware.gwtswing.swing.border.GTitledBorder;
import de.exware.gwtswing.swing.border.SelectiveLineBorder;
import de.exware.gwtswing.swing.event.GChangeEvent;
import de.exware.gwtswing.swing.event.GChangeListener;
import de.exware.gwtswing.swing.event.GTreeSelectionEvent;
import de.exware.gwtswing.swing.event.GTreeSelectionListener;
import de.exware.gwtswing.swing.table.GTableManager;
import de.exware.gwtswing.swing.tree.GDefaultMutableTreeNode;
import de.exware.gwtswing.swing.tree.GDefaultTreeModel;
 
public class SwingSet extends GFrame
{
    public SwingSet()
    {
        createViews();
    }
    
    private void createViewsDebug()
    {
        GFrame frame = new GFrame();
        frame.setUndecorated(true);
        frame.setLayout(new GGridBagLayout());
        
        GGridBagConstraints gbc = new GGridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        frame.add(new GSlider(GSlider.VERTICAL), gbc);
        GUtilities.addToBody(frame);
        frame.setBounds(0, 0, GPlatform.getWin().getClientWidth() -1, GPlatform.getWin().getClientHeight()-1);
        frame.revalidate();
    }
    
    private void createViews()
    {
        final GPanel panel = new GPanel();
        
        panel.setLayout(new GBorderLayout());

        panel.setBorder(GBorderFactory.createEmptyBorder(10, 10, 10, 10));
        GSplitPane split = new GSplitPane();

        GPanel content = new GPanel();
        content.setBorder(GBorderFactory.createEmptyBorder(5, 5, 5, 5));
        content.setLayout(new GCardLayout());
        panel.add(split, GBorderLayout.CENTER);
        split.setRightComponent(content);
        content.add(createLayoutManagers(), "Layout Manager");
        content.add(createButtons(), "Buttons");
        content.add(createTree(), "Tree");
        content.add(createTable(), "Table");
        content.add(createTabbedPane(), "TabbedPane");
        content.add(createOther(), "Other");
        content.add(createBorders(), "Borders");
        content.add(createInput(), "Input");
        content.add(createSplitPane(), "Split");

        GTree navigation = createNavigation();        
        GScrollPane spane = new GScrollPane(navigation);
        split.setLeftComponent(spane);
        navigation.addTreeSelectionListener(new GTreeSelectionListener()
        {
            @Override
            public void valueChanged(GTreeSelectionEvent e)
            {
                GDefaultMutableTreeNode node = (GDefaultMutableTreeNode) e.getPath().getLastPathComponent();
                Object value = node.getUserObject();
                GCardLayout cl = (GCardLayout) content.getLayout();
                cl.show(content, (String) value);
                content.revalidate();
            }
        });
        split.setDividerLocation(navigation.getPreferredSize().width);

        final GLabel helptitle = new GLabel();
        helptitle.setText(
            "<html>This is an demonstration of GWTSwing Features. <BR>Use the Navigation on the left side to select the different Demos");
        helptitle.setBorder(new SelectiveLineBorder(GColor.GRAY, 0, 0, 2, 0));
        PartitionedPanel headpanel = new PartitionedPanel(1);
        headpanel.setIndentSize(0);
        GMenuBar menubar = new GMenuBar();
        GMenu menu = new GMenu("File");
        menu.add(new AboutAction());
        menu.add(new ExitAction());
        menubar.add(menu);
        GMenu editmenu = new GMenu("Edit");
        editmenu.add(new AboutAction());
                menubar.add(editmenu);
        GMenu helpmenu = new GMenu("Help");
        menubar.add(helpmenu);
        panel.add(headpanel, GBorderLayout.NORTH);
        helpmenu.add(new AboutAction());
        
        headpanel.add(helptitle);
        headpanel.add(menubar);

        GUtilities.addToBody(panel);
        panel.setBounds(0, 0, GPlatform.getWin().getClientWidth() -1, GPlatform.getWin().getClientHeight()-1);
        panel.revalidate();
    }
    
    class AboutAction extends GAbstractAction
    {
        public AboutAction()
        {
            super("About");
        }
        
        @Override
        public void actionPerformed(GActionEvent evt)
        {
            super.actionPerformed(evt);
            GOptionPane.showMessageDialog((GComponent) evt.getSource(), "<html>This is GWTSwing.");
        }
    }
    
    class ExitAction extends GAbstractAction
    {
        public ExitAction()
        {
            super("Exit");
        }
        
        @Override
        public void actionPerformed(GActionEvent evt)
        {
            super.actionPerformed(evt);
            GOptionPane.showMessageDialog((GComponent) evt.getSource(), "<html>This is a Demo.<BR>Will not really exit.");
        }
    }
    
    private GComponent createOther()
    {
        GPanel panel = new GPanel();
        panel.setLayout(new GGridBagLayout());
        GGridBagConstraints gbc = new GGridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets.right = 10;
        gbc.insets.bottom = 10;
        GLabel label = new GLabel("GLabel");
        label.addMouseListener(new GMouseAdapter()
        {
            @Override
            public void mouseClicked(GMouseEvent evt)
            {
                super.mouseClicked(evt);
                GPlatform.getInstance().alert("BUTTON " + evt.getButton());
            }
        });
        panel.add(label, gbc);
        gbc.gridx++;
        gbc.gridwidth = 2;
        label = new GLabel("GLabel with Image");
        label.setIcon(new GImageIcon(GUtilities.getResource(Constants.PLUGIN_ID, "/icons/file.svg"), 30, 30));
        panel.add(label, gbc);
        gbc.gridwidth = 1;
        
        gbc.gridy ++;
        gbc.gridx = 1;
        
        label = new GLabel("ProgressBar");
        panel.add(label, gbc);
        gbc.gridx ++;
        GProgressBar pbar = new GProgressBar(0,100);
        pbar.setValue(10);
        pbar.setString("10 %");
        panel.add(pbar, gbc);
        gbc.gridx ++;
        GButton bt = new GButton("+");
        bt.addActionListener(new GActionListener()
        {
            
            @Override
            public void actionPerformed(GActionEvent evt)
            {
                pbar.setValue(pbar.getValue() + 10);
                pbar.setString(pbar.getValue() + " %");
            }
        });
        panel.add(bt, gbc);
        
        gbc.gridy ++;
        gbc.gridx = 1;
        
        label = new GLabel("Slider");
        panel.add(label, gbc);

        gbc.gridx ++;
        gbc.gridwidth = 2;
        gbc.fill = GGridBagConstraints.HORIZONTAL;
        GSlider slider = new GSlider();
        slider.setValue(90);
        slider.addChangeListener(new GChangeListener()
        {
            
            @Override
            public void stateChanged(GChangeEvent evt)
            {
                GPlatform.getInstance().alert("value: " + slider.getValue());
            }
        });
        panel.add(slider, gbc);
        gbc.gridy ++;
        gbc.gridx = 1;
        GSlider vslider = new GSlider(GSlider.VERTICAL,0,10,2);
        vslider.addChangeListener(new GChangeListener()
        {
            
            @Override
            public void stateChanged(GChangeEvent evt)
            {
                GPlatform.getInstance().alert("value: " + vslider.getValue());
            }
        });
        panel.add(vslider, gbc);
        return panel;
    }

    private GComponent createInput()
    {
        PartitionedPanel panel = new PartitionedPanel(2);
        panel.addSeparator("TextFields");
        GLabel label = new GLabel("GTextField");
        panel.add(label);
        GTextField tf = new GTextField(10);
        panel.add(tf);
        label = new GLabel("GTextField");
        panel.add(label);
        tf = new GTextField(15);
        tf.setText("with Text");
        panel.add(tf);
        label = new GLabel("GPasswordField");
        panel.add(label);
        GPasswordField pf = new GPasswordField(15);
        panel.add(pf);
        label = new GLabel("GTextArea");
        panel.add(label);
        GTextArea ta = new GTextArea("Hello World");
        panel.add(ta);
        panel.addSeparator("Lists");
        label = new GLabel("GComboBox");
        panel.add(label);
        final GComboBox<String> cb = new GComboBox<>();
        cb.addItem("Red");
        cb.addItem("White");
        cb.addItem("Blue");
        cb.addItem("Green");
        cb.addItem("Orange");
        panel.add(cb);
        label = new GLabel("Same Elements but with Renderer");
        panel.add(label);
        final GComboBox<String> cb2 = new GComboBox<>();
        cb2.setRenderer(new StringRenderer<String>()
        {
            @Override
            public String toString(String object)
            {
                return object + " Item";
            }
        });
        cb2.addItem("Red");
        cb2.addItem("White");
        cb2.addItem("Blue");
        cb2.addItem("Green");
        cb2.addItem("Orange");
        panel.add(cb2);
        label = new GLabel("GList");
        panel.add(label);
        GList list = new GList(
            new String[] { "White", "Red", "Blue", "Green", "Orange", "Yellow", "Pink", "Brown", "Black" });
        GScrollPane spane = new GScrollPane(list);
        spane.setBorder(GBorderFactory.createLineBorder(GColor.DARK_GRAY, 1));
        panel.add(spane);
        label = new GLabel("GList with Renderer");
        panel.add(label);
        list = new GList(
            new String[] { "White", "Red", "Blue", "Green", "Orange", "Yellow", "Pink", "Brown", "Black" });
        list.setCellRenderer(new GDefaultListCellRenderer()
        {
            @Override
            public GComponent getListCellRendererComponent(GList list, Object value, boolean selected)
            {
                GLabel comp = (GLabel) super.getListCellRendererComponent(list, value, selected);
                comp.setText(comp.getText() + " Item");
                return comp;
            }
        });
        spane = new GScrollPane(list);
        spane.setBorder(GBorderFactory.createLineBorder(GColor.DARK_GRAY, 1));
        panel.add(spane);
        return panel;
    }

    private GComponent createTabbedPane()
    {
        PartitionedPanel panel = new PartitionedPanel(2);
        panel.addSeparator("Vertical GTabbedPane");
        GLabel label = new GLabel("Tabs right");
        panel.add(label);
        GTabbedPane vertical = new GTabbedPane(GTabbedPane.RIGHT);
        vertical.addTab("First",
            new GLabel(
                "The first tabbed component. <br>Click on the other Tabs to show them<br>Some more Lines<br>Some more Lines<br>Some more Lines<br>Some more Lines<br>Some more Lines"),
            false);
        GLabel l = new GLabel("The second tabbed component. <br>Click on the other Tabs to show them");
        vertical.addTab("Second", l, false);
        vertical.setBorder(GBorderFactory.createLineBorder(GColor.DARK_GRAY, 1));
        panel.add(vertical);
        
        label = new GLabel("Tabs left");
        panel.add(label);
        vertical = new GTabbedPane(GTabbedPane.LEFT);
        vertical.addTab("First",
            new GLabel(
                "The first tabbed component. <br>Click on the other Tabs to show them<br>Some more Lines<br>Some more Lines<br>Some more Lines<br>Some more Lines<br>Some more Lines"),
            false);
        l = new GLabel("The second tabbed component. <br>Click on the other Tabs to show them");
        vertical.addTab("Second", l, false);
        vertical.setBorder(GBorderFactory.createLineBorder(GColor.DARK_GRAY, 1));
        panel.add(vertical);

        panel.addSeparator("Horizontal GTabbedPane");
        label = new GLabel("Tabs Top");
        panel.add(label);
        GTabbedPane horizontal = new GTabbedPane(GTabbedPane.TOP);
        horizontal.addTab("First",
            new GLabel(
                "The first tabbed component. <br>Click on the other Tabs to show them<br>Some more Lines<br>Some more Lines<br>Some more Lines<br>Some more Lines<br>Some more Lines"),
            false);
        l = new GLabel("The second tabbed component. <br>Click on the other Tabs to show them");
        horizontal.addTab("Second", l, false);
        panel.add(horizontal);
        horizontal.setBorder(GBorderFactory.createLineBorder(GColor.DARK_GRAY, 1));

        label = new GLabel("Tabs Bottom");
        panel.add(label);
        horizontal = new GTabbedPane(GTabbedPane.BOTTOM);
        horizontal.addTab("First",
            new GLabel(
                "The first tabbed component. <br>Click on the other Tabs to show them<br>Some more Lines<br>Some more Lines<br>Some more Lines<br>Some more Lines<br>Some more Lines"),
            false);
        l = new GLabel("The second tabbed component. <br>Click on the other Tabs to show them");
        horizontal.addTab("Second", l, false);
        panel.add(horizontal);
        horizontal.setBorder(GBorderFactory.createLineBorder(GColor.DARK_GRAY, 1));
        return panel;
    }

    private GComponent createBorders()
    {
        GPanel panel = new GPanel();
        panel.setLayout(new GGridBagLayout());
        GGridBagConstraints gbc = new GGridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets.right = 10;
        gbc.insets.bottom = 10;
        GLabel label = new GLabel("No GBorder");
        panel.add(label, gbc);
        gbc.gridx++;
        label = new GLabel("1px GLineBorder");
        label.setBorder(GBorderFactory.createLineBorder(GColor.BLUE, 1));
        panel.add(label, gbc);
        gbc.gridx++;
        label = new GLabel("5px GLineBorder")
        {
            @Override
            public GDimension getPreferredSize()
            {
                return super.getPreferredSize();
            }
        };
        label.setBorder(GBorderFactory.createLineBorder(GColor.RED, 5));
        panel.add(label, gbc);
        gbc.gridx = 1;
        gbc.gridy++;
        label = new GLabel("5px GEmptyBorder");
        label.setBorder(GBorderFactory.createEmptyBorder(5, 5, 5, 5));
        panel.add(label, gbc);
        gbc.gridx++;
        label = new GLabel("10px GEmptyBorder");
        label.setBorder(GBorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(label, gbc);
        gbc.gridx = 1;
        gbc.gridy++;
        label = new GLabel("GEtchedBorder lowered");
        label.setBorder(GBorderFactory.createEtchedBorder());
        panel.add(label, gbc);
        gbc.gridx++;
        label = new GLabel("GEtchedBorder raised");
        label.setBorder(GBorderFactory.createEtchedBorder(GEtchedBorder.RAISED));
        panel.add(label, gbc);
        gbc.gridx = 1;
        gbc.gridy++;
        label = new GLabel("GBevelBorder lowered");
        label.setBorder(GBorderFactory.createBevelBorder(GBevelBorder.LOWERED));
        panel.add(label, gbc);
        gbc.gridx++;
        label = new GLabel("GBevelBorder raised");
        label.setBorder(GBorderFactory.createBevelBorder(GBevelBorder.RAISED));
        panel.add(label, gbc);
        gbc.gridx = 1;
        gbc.gridy++;
        label = new GLabel("2px GBorder on Bottom");
        label.setBorder(new SelectiveLineBorder(GColor.GREEN, 0, 0, 2, 0));
        panel.add(label, gbc);
        gbc.gridx++;
        label = new GLabel("2px GBorder on Left and Top");
        label.setBorder(new SelectiveLineBorder(GColor.GREEN, 2, 2, 0, 0));
        panel.add(label, gbc);
        gbc.gridx++;
        label = new GLabel("5px GBorder on Right");
        label.setBorder(new SelectiveLineBorder(GColor.GREEN, 0, 0, 0, 5));
        panel.add(label, gbc);
        gbc.gridx = 1;
        gbc.gridy++;
        label = new GLabel("GTitledBorder");
        label.setBorder(new GTitledBorder("Title"));
        panel.add(label, gbc);
        gbc.gridx++;
        label = new GLabel("GTitledBorder Bevel Raised");
        label.setBorder(new GTitledBorder(new GBevelBorder(GBevelBorder.RAISED), "Title"));
        panel.add(label, gbc);
        gbc.gridx++;
        label = new GLabel("GTitledBorder Etched Raised");
        label.setBorder(new GTitledBorder(new GEtchedBorder(GEtchedBorder.RAISED), "Title"));
        panel.add(label, gbc);
        gbc.gridx++;
        label = new GLabel("GTitledBorder LineBorder");
        label.setBorder(new GTitledBorder(new GLineBorder(GColor.RED, 4), "Title"));
        panel.add(label, gbc);
        return panel;
    }

    private GComponent createTree()
    {
        GTree tree = new GTree();
        tree.expandRow(0);
        tree.expandRow(2);
        GScrollPane spane = new GScrollPane(tree);
        spane.setBorder(GBorderFactory.createLineBorder(GColor.DARK_GRAY, 1));
        return spane;
    }

    private GComponent createTable()
    {
        PartitionedPanel panel = new PartitionedPanel(1);
        GLabel label = new GLabel("Left-Click Headers to sort.");
        panel.add(label);
        label = new GLabel("Right-Click Headers to filter.");
        panel.add(label);
        GTable table = new GTable<>();
        table.setModel(new CSVTableModel("a;b;c\r\n1;2;3\r\n4;5;6"));
        GScrollPane spane = new GScrollPane(table);
        spane.setBorder(GBorderFactory.createLineBorder(GColor.DARK_GRAY, 1));
        table.setSortable(true);
        GTableManager tm = new GTableManager(table, "demoTable");
        tm.setAllPossibleValuesFilter(0);
        tm.setAllPossibleValuesFilter(1);
        tm.setAllPossibleValuesFilter(2);
        tm.setAllPossibleValuesFilter(3);
        panel.add(spane, GGridBagConstraints.BOTH, 1, 1, 1, 1);
        return panel;
    }

    private GComponent createSplitPane()
    {
        GSplitPane spane = new GSplitPane();
        GLabel label = new GLabel("Left");
        spane.setLeftComponent(label);
        GSplitPane rspane = new GSplitPane(GSplitPane.VERTICAL_SPLIT);
        label = new GLabel("Right Top");
        rspane.setLeftComponent(label);
        label = new GLabel("Right Bottom");
        rspane.setRightComponent(label);
        spane.setRightComponent(rspane);
        return spane;
    }

    private GComponent createButtons()
    {
        GPanel panel = new GPanel();
        panel.setLayout(new GGridBagLayout());
        GGridBagConstraints gbc = new GGridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets.right = 10;
        gbc.insets.bottom = 10;
        GButton bt = new GButton("GButton");
        bt.addActionListener(new GActionListener()
        {
            @Override
            public void actionPerformed(GActionEvent evt)
            {
                GOptionPane.showMessageDialog(null, "Hello World");
            }
        });
        panel.add(bt, gbc);
        gbc.gridx++;
        GButton ibt = new GButton("GButton with Image");
        ibt.setIcon(new GImageIcon(GUtilities.getResource(Constants.PLUGIN_ID, "/icons/file.svg"), 30, 30));
        panel.add(ibt, gbc);
        gbc.gridx = 1;
        gbc.gridy++;
        GButtonGroup bgroup = new GButtonGroup();
        GRadioButton rbt = new GRadioButton("GRadioButton 1");
        bgroup.add(rbt);
        gbc.gridy++;
        panel.add(rbt, gbc);
        rbt = new GRadioButton("GRadioButton 2");
        rbt.setSelected(true);
        bgroup.add(rbt);
        gbc.gridx++;
        panel.add(rbt, gbc);
        GCheckBox cbt = new GCheckBox("GCheckBox 1");
        gbc.gridx = 1;
        gbc.gridy++;
        panel.add(cbt, gbc);
        cbt = new GCheckBox("GCheckBox 2");
        cbt.setSelected(true);
        gbc.gridx++;
        panel.add(cbt, gbc);
        GToggleButton tb = new GToggleButton("GToggleButton");
        gbc.gridx++;
        panel.add(tb, gbc);
        
        return panel;
    }

    private GComponent createLayoutManagers()
    {
        GPanel panel = new GPanel();
        panel.setLayout(new GGridBagLayout());
        GGridBagConstraints gbc = new GGridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets.right = 10;
        gbc.insets.bottom = 10;
        GLabel label = new GLabel("BorderLayout");
        panel.add(label, gbc);
        GPanel borderLayout = new GPanel();
        borderLayout.setBorder(GBorderFactory.createLineBorder(GColor.BLACK, 3));
        borderLayout.setLayout(new GBorderLayout());
        GLabel west = new GLabel("West");
        west.setBackground(new GColor(255, 200, 200));
        borderLayout.add(west, GBorderLayout.WEST);
        GLabel north = new GLabel("North");
        north.setBackground(new GColor(200, 255, 200));
        borderLayout.add(north, GBorderLayout.NORTH);
        GLabel center = new GLabel("Center");
        center.setBackground(new GColor(200, 200, 150));
        borderLayout.add(center, GBorderLayout.CENTER);
        GLabel south = new GLabel("South");
        south.setBackground(new GColor(150, 200, 200));
        borderLayout.add(south, GBorderLayout.SOUTH);
        GLabel east = new GLabel("East");
        east.setBackground(new GColor(200, 150, 200));
        borderLayout.add(east, GBorderLayout.EAST);
        gbc.gridx++;
        panel.add(borderLayout, gbc);

        gbc.gridx++;
        label = new GLabel("with 5 Pixel gap");
        panel.add(label, gbc);
        borderLayout = new GPanel();
        borderLayout.setLayout(new GBorderLayout(5, 5));
        borderLayout.setBorder(GBorderFactory.createLineBorder(GColor.BLACK, 3));
        west = new GLabel("West");
        west.setBackground(new GColor(255, 200, 200));
        borderLayout.add(west, GBorderLayout.WEST);
        north = new GLabel("North");
        north.setBackground(new GColor(200, 255, 200));
        borderLayout.add(north, GBorderLayout.NORTH);
        center = new GLabel("Center");
        center.setBackground(new GColor(200, 200, 150));
        borderLayout.add(center, GBorderLayout.CENTER);
        south = new GLabel("South");
        south.setBackground(new GColor(150, 200, 200));
        borderLayout.add(south, GBorderLayout.SOUTH);
        east = new GLabel("East");
        east.setBackground(new GColor(200, 150, 200));
        borderLayout.add(east, GBorderLayout.EAST);
        gbc.gridx++;
        panel.add(borderLayout, gbc);

        gbc.gridx = 1;
        gbc.gridy++;
        label = new GLabel("GridLayout");
        panel.add(label, gbc);
        GPanel gridLayout = new GPanel();
        gridLayout.setBorder(GBorderFactory.createLineBorder(GColor.BLACK, 3));
        gridLayout.setLayout(new GGridLayout(3, 2));
        for (int i = 0; i < 6; i++)
        {
            GLabel lab = new GLabel("<html>Component <BR>" + i);
            lab.setBackground(new GColor(255 - i * 40, 200, 0 + i * 40));
            gridLayout.add(lab);
        }
        gbc.gridx++;
        panel.add(gridLayout, gbc);

        gbc.gridx++;
        label = new GLabel("with 5 Pixel gap");
        panel.add(label, gbc);
        gridLayout = new GPanel();
        gridLayout.setBorder(GBorderFactory.createLineBorder(GColor.BLACK, 3));
        gridLayout.setLayout(new GGridLayout(3, 2, 5, 5));
        for (int i = 0; i < 6; i++)
        {
            GLabel lab = new GLabel("<html>Component <BR>" + i);
            lab.setBackground(new GColor(255 - i * 40, 200, 0 + i * 40));
            gridLayout.add(lab);
        }
        gbc.gridx++;
        panel.add(gridLayout, gbc);

        gbc.gridx = 1;
        gbc.gridy++;
        label = new GLabel("GridBagLayout");
        panel.add(label, gbc);
        GPanel gridBagLayout = new GPanel();
        gridBagLayout.setBorder(GBorderFactory.createLineBorder(GColor.BLACK, 3));
        gridBagLayout.setLayout(new GGridBagLayout());
        GGridBagConstraints gbc2 = new GGridBagConstraints();
        GLabel lab = new GLabel("Small Column Component");
        lab.setBackground(new GColor(150, 200, 200));
        gridBagLayout.add(lab, gbc2);
        lab = new GLabel("Large Column Component");
        lab.setBackground(new GColor(200, 150, 200));
        gbc2.gridx++;
        gridBagLayout.add(lab, gbc2);
        lab = new GLabel("Second Line Comp 1");
        lab.setBackground(new GColor(200, 200, 150));
        gbc2.gridx = 0;
        gbc2.gridy++;
        gridBagLayout.add(lab, gbc2);
        lab = new GLabel("Comp 2");
        lab.setBackground(new GColor(200, 200, 250));
        gbc2.gridx++;
        gridBagLayout.add(lab, gbc2);
        lab = new GLabel("3rd Component");
        lab.setBackground(new GColor(200, 250, 200));
        gbc2.gridx++;
        gridBagLayout.add(lab, gbc2);
        lab = new GLabel("Third Line Fill");
        lab.setBackground(new GColor(250, 200, 200));
        gbc2.gridx = 0;
        gbc2.fill = gbc2.BOTH;
        gbc2.gridy++;
        gridBagLayout.add(lab, gbc2);
        lab = new GLabel("Large");
        lab.setBackground(new GColor(250, 200, 150));
        gbc2.gridx++;
        gridBagLayout.add(lab, gbc2);
        lab = new GLabel("Large 2");
        lab.setBackground(new GColor(150, 200, 150));
        gbc2.gridx++;
        gridBagLayout.add(lab, gbc2);
        gbc.gridx++;
        gbc.gridwidth = 3;
        panel.add(gridBagLayout, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 4;
        gbc.fill = gbc.BOTH;
        label = new GLabel(
            "<html>CardLayout is used for the different demos. <br>Just change selection in the tree to see it in action.");
        panel.add(label, gbc);
        return panel;
    }

    private GTree createNavigation()
    {
        GTree nav = new GTree();
        nav.setPreferredSize(new GDimension(200, 400));
        nav.setRootVisible(false);
        GDefaultMutableTreeNode root = new GDefaultMutableTreeNode("UI Components");
        GDefaultMutableTreeNode layouts = new GDefaultMutableTreeNode("Layout Manager");
        root.add(layouts);
        GDefaultMutableTreeNode buttons = new GDefaultMutableTreeNode("Buttons");
        root.add(buttons);
        GDefaultMutableTreeNode borders = new GDefaultMutableTreeNode("Borders");
        root.add(borders);
        GDefaultMutableTreeNode tree = new GDefaultMutableTreeNode("Tree");
        root.add(tree);
        GDefaultMutableTreeNode table = new GDefaultMutableTreeNode("Table");
        root.add(table);
        GDefaultMutableTreeNode tabbedPane = new GDefaultMutableTreeNode("TabbedPane");
        root.add(tabbedPane);
        GDefaultMutableTreeNode input = new GDefaultMutableTreeNode("Input");
        root.add(input);
        GDefaultMutableTreeNode other = new GDefaultMutableTreeNode("Other");
        root.add(other);
        GDefaultMutableTreeNode split = new GDefaultMutableTreeNode("Split");
        root.add(split);
        GDefaultTreeModel model = new GDefaultTreeModel(root);
        nav.setModel(model);
        return nav;
    }
}
