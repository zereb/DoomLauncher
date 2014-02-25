/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doomlauncher;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Observable;
import java.util.Observer;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

/**
 *
 * @author loludaed
 */
public class DoomLauncher implements Observer, Constants, Runnable {

    /**
     * @param args the command line arguments
     */
    public ProcessBuilder processBuilder;
    public static boolean LAF = false;

    JFrame frame;
    JTextArea jTextArea;
    Document document;
    JComboBox<String> jComboBoxEngines;
    JComboBox<String> jComboBoxIwads;
    JList<File> jListPwads;
    JPanel changeJPanel;
    JTabbedPane jTabbedPane;
    JTextArea customParamJTtextArea;

    Misc misc = new Misc();
    String[] engines = ENGINE;

    FileChoose fileChoose;
    static Files files;

    ProcessBuilderD processBuilderD;

    public int WIDTH = 840;
    public int HEIGHT = 580;

    String[] argsPB;
    String[] customParametersArg;
    Thread t;
    int[] compat = new int[2];

    public DoomLauncher() {
        for (int i = 0; i < compat.length; i++) {
            compat[i] = 0;
        }

        t = new Thread(this);


        engines = files.engineName;

        frame = new JFrame("Doom Launcher");
        frame.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        frame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        CustomParametrs customParametrs = new CustomParametrs(frame);
        //List
        jComboBoxEngines = new JComboBox<String>(engines);
        JLabel enginesJLabel = new JLabel("Engine: ");
        jComboBoxIwads = new JComboBox<String>(files.iwadsNames);
        JLabel iwadsJLabel = new JLabel("Iwad: ");

        JButton jButtonIwad = new JButton("...");
        jButtonIwad.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                files.IWADCountReset();
                fileChoose = new FileChoose(files, FILE_CHOOSE_IWAD);
                files.initIWADS();
                jComboBoxIwads.removeAllItems();
                for (int i = 0; i < files.getIWADCount(); i++) {
                    jComboBoxIwads.addItem(files.iwadsNames[i]);
                }

                //initChoosePanel();
            }

        });
        JButton jButtonEngine = new JButton("...");
        jButtonEngine.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selected = jComboBoxEngines.getSelectedIndex();
                fileChoose = new FileChoose(files, FILE_CHOOSE_ENGINE, selected);
                jComboBoxEngines.removeAllItems();
                for (int i = 0; i < files.engineName.length; i++) {
                    jComboBoxEngines.addItem(files.engineName[i]);
                }
                jComboBoxEngines.setSelectedIndex(selected);

            }

        });
        JButton jButtonAddEngine = new JButton("Add new");
        jButtonAddEngine.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                fileChoose = new FileChoose(files, FILE_ADD_ENGINE);
                jComboBoxEngines.removeAllItems();
                for (int i = 0; i < files.engineName.length; i++) {
                    jComboBoxEngines.addItem(files.engineName[i]);
                }

            }

        });
        JButton jButtonPwad = new JButton("Additional files");
        jButtonPwad.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (files.IWADSelected() > 0) {
                    fileChoose = new FileChoose(files, FILE_CHOOSE_PWAD);
                    jListPwads.setListData(files.pwad);
                } else {
                    showError();
                }

            }

        });
        JButton jButtonPwadRemove = new JButton("Remove");
        jButtonPwadRemove.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                files.removePwad(jListPwads.getSelectedIndex());
                jListPwads.setListData(files.pwad);
            }

        });
        JButton jButtonPwadRemoveAll = new JButton("Remove all");
        jButtonPwadRemoveAll.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                files.removePwad(-666);
                jListPwads.setListData(files.pwad);
            }

        });
        JButton jButtonLaunch = new JButton("Launch");
        jButtonLaunch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                if (files.IWADSelected() > 0) {
                    initLaunch();
                    processBuilderD = new ProcessBuilderD(argsPB);
                    processBuilderD.addObserver(DoomLauncher.this);
                } else {
                    showError();
                }

            }

        });
        JButton jButtonCommandLine = new JButton("Command Line");
        jButtonCommandLine.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                commadLineShow();

            }

        });
        JButton jButtonSwitchLAF = new JButton("Switch LAF");
        jButtonSwitchLAF.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String lafString;
                if (!LAF) {
                    lafString ="System";
                    LAF=false;
                } else {
                    lafString = "Nimbus";
                    LAF=true;
                }
                files.writeConfig("laf", lafString);
                Printer.print("You chose "+lafString+" laf");
                Printer.print("Need restart DoomLauncher");
                popOut("You chose "+lafString+" laf\n"+
                        "Need restart DoomLauncher");


            }

        });
        JButton jButtonClose = new JButton("Close");
        jButtonClose.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);

            }

        });
        JButton jButtonsSave = new JButton("Save");
        jButtonsSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fileChoose = new FileChoose(files, FILE_SAVE, jComboBoxEngines.getSelectedIndex(), jComboBoxIwads.getSelectedIndex(), files.pwad, misc.compat);

            }

        });
        jListPwads = new JList<File>(files.pwad);
        jListPwads.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane jScrollPanePwads = new JScrollPane(jListPwads);
        JButton jButtonLoad = new JButton("Load");
        jButtonLoad.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fileChoose = new FileChoose(files, FILE_LOAD);
                if (fileChoose.succesfullyReadConfig) {
                    try {
                        String[] config = fileChoose.configString;
                        for (int i = 0; i < config.length; i++) {
                            System.out.println(config[i]);
                        }
                        files.addEngine(config[0]);
                        files.addIwad(config[1], "IWAD from loaded Config: ");
                        jComboBoxIwads.removeAllItems();
                        for (int i = 0; i < files.getIWADCount(); i++) {
                            jComboBoxIwads.addItem(files.iwadsNames[i]);
                        }
                        jComboBoxIwads.setSelectedIndex(jComboBoxIwads.getItemCount() - 1);

                        misc.compat[0] = Integer.parseInt(config[2]);
                        misc.compat[1] = Integer.parseInt(config[3]);
                        misc.compatUpdate(misc.compat[0], 1);
                        misc.compatUpdate(misc.compat[1], 2);
                        File[] pwadsFiles = new File[Integer.parseInt(config[4])];
                        int confId = 5;
                        for (int i = 0; i < pwadsFiles.length; i++) {
                            pwadsFiles[i] = new File(config[confId]);
                            confId++;
                        }
                        files.addPWADS(pwadsFiles);
                        jListPwads.setListData(files.pwad);

                    } catch (NumberFormatException ex) {
                        Printer.print("Error parse config: " + ex);
                    }
                }

            }

        });

        //panel pwads
        JPanel pwadsJPanel = new JPanel();
        pwadsJPanel.setBorder(BorderFactory.createTitledBorder("Aditional Files:"));
        pwadsJPanel.setLayout(new BorderLayout());
        pwadsJPanel.add(jScrollPanePwads);
        JPanel pwadsButtonJPanel = new JPanel();
        pwadsButtonJPanel.setLayout(new FlowLayout());
        pwadsButtonJPanel.add(jButtonPwad);
        pwadsButtonJPanel.add(jButtonPwadRemove);
        pwadsButtonJPanel.add(jButtonPwadRemoveAll);
        pwadsJPanel.add(pwadsButtonJPanel, BorderLayout.SOUTH);

        //панель вывода
        jTextArea = new JTextArea();
        document = jTextArea.getDocument();
        JScrollPane jScrollPane = new JScrollPane(jTextArea);

        jTextArea.setEditable(false);

        JPanel outEXJPanel = new JPanel();
        outEXJPanel.setPreferredSize(new Dimension(300, 180));
        outEXJPanel.setBorder(BorderFactory.createTitledBorder("Out"));
        outEXJPanel.setLayout(new BorderLayout());
        outEXJPanel.add(jScrollPane, BorderLayout.CENTER);

        //custom param
        JPanel customParamJPanel = new JPanel();
        customParamJTtextArea = new JTextArea();
        JScrollPane customParamJScrollPane = new JScrollPane(customParamJTtextArea);
        JLabel customParamJLabel = new JLabel("Custom parameters (ENTER or SPACEBAR separated):");
        customParamJPanel.setLayout(new BorderLayout());
        customParamJPanel.add(customParamJLabel, BorderLayout.NORTH);
        customParamJPanel.add(customParamJScrollPane, BorderLayout.CENTER);

        //панель выбора
        changeJPanel = new JPanel();
        changeJPanel.add(enginesJLabel);
        changeJPanel.add(jComboBoxEngines);
        changeJPanel.add(jButtonEngine);
        changeJPanel.add(jButtonAddEngine);
        changeJPanel.add(iwadsJLabel);
        changeJPanel.add(jComboBoxIwads);
        changeJPanel.add(jButtonIwad);

        JPanel bigJPanel = new JPanel();
        bigJPanel.setLayout(new BorderLayout());
        bigJPanel.add(changeJPanel, BorderLayout.NORTH);
        bigJPanel.add(pwadsJPanel, BorderLayout.CENTER);
        bigJPanel.add(outEXJPanel, BorderLayout.SOUTH);

        //buttons
        JPanel buttonsJPanel = new JPanel();
        buttonsJPanel.add(jButtonLoad);
        buttonsJPanel.add(jButtonsSave);
        buttonsJPanel.add(jButtonLaunch);
        buttonsJPanel.add(jButtonCommandLine);
        buttonsJPanel.add(jButtonClose);
        buttonsJPanel.add(jButtonSwitchLAF);

        jTabbedPane = new JTabbedPane();
        jTabbedPane.addTab("General", bigJPanel);
        jTabbedPane.addTab("Misc", misc.miscJPanel);
        jTabbedPane.addTab("Custom Parametres", customParamJPanel);

        //установка компановщика и добавление пнелей
        frame.setLayout(new BorderLayout());
        frame.add(jTabbedPane, BorderLayout.CENTER);
        frame.add(buttonsJPanel, BorderLayout.SOUTH);
        frame.pack();
        frame.setVisible(true);

        t.start();
    }

    private void initLaunch() {
        misc.changes();
        String customParam = customParamJTtextArea.getText() + " " + misc.miscParam;
        // Printer.print(customParam.length());
        if (customParam.length() > 0) {
            customParametersArg = customParam.split(" ");
        }

        argsPB = new String[calculateARGSLength()];
        argsPB[LAUNCH_CMD_ARG_ENGINE] = files.engine[jComboBoxEngines.getSelectedIndex()].getAbsolutePath();
        if (files.getIWADCount() > 0) {
            argsPB[LAUNCH_CMD_ARG_IWAD] = "-iwad";
            argsPB[LAUNCH_CMD_ARG_IWADPATH] = files.iwads[jComboBoxIwads.getSelectedIndex()].getAbsolutePath();
        }

        if (files.getPWADCount() > 0) {
            int pwadID = 0;
            argsPB[LAUNCH_CMD_ARG_FILE] = "-file";
            for (int i = LAUNCH_CMD_ARG_FILEPATH; i < LAUNCH_CMD_ARG_FILEPATH + files.pwad.length; i++) {
                argsPB[i] = files.pwad[pwadID].getAbsolutePath();
                Printer.print(i + " pwad " + pwadID);
                pwadID++;

            }
        }
        if (customParam.length() > 0) {
            String[] buffer = argsPB;
            // Printer.print(customParametersArg.length);
            argsPB = new String[calculateARGSLength() + customParametersArg.length];
            for (int i = 0; i < argsPB.length; i++) {
                if (i < buffer.length) {
                    argsPB[i] = buffer[i];
                } else {
                    argsPB[i] = customParametersArg[i - buffer.length];
                }
            }
        }

    }

    public int calculateARGSLength() {
        return 1 + files.IWADSelected() + files.PWADSelected();
    }

    public String getCommandString() {

        initLaunch();
        String cmd = "";
        for (int i = 0; i < argsPB.length; i++) {
            cmd = cmd.concat(argsPB[i]) + " ";
        }

        return cmd;
    }

    public void update(Observable o, Object arg) {
        document = jTextArea.getDocument();
        try {
            document.insertString(document.getLength(), processBuilderD.getOutEXELine(), null);
        } catch (BadLocationException ex) {
            ;
        }

    }

    public void showError() {
        JOptionPane.showMessageDialog(frame, "You must set engine and iwad first", "Error", JOptionPane.ERROR_MESSAGE);

    }

      public void popOut(String msg) {
        JOptionPane.showMessageDialog(frame, msg, "Info", JOptionPane.INFORMATION_MESSAGE);

    }

    public void commadLineShow() {
        JDialog commandLineJFrame = new JDialog(frame, "Command Line");

        commandLineJFrame.setMinimumSize(new Dimension(WIDTH / 2, HEIGHT / 2));
        commandLineJFrame.setPreferredSize(new Dimension(WIDTH / 2, HEIGHT / 2));
        commandLineJFrame.setLocationRelativeTo(frame);

        JTextArea commandLineJTextArea = new JTextArea();
        commandLineJTextArea.setLineWrap(true);
        commandLineJTextArea.setWrapStyleWord(true);
        commandLineJTextArea.setEditable(true);
        JScrollPane commandLineJScrollPane = new JScrollPane(commandLineJTextArea);

        commandLineJTextArea.setText(getCommandString());
        commandLineJFrame.add(commandLineJScrollPane);
        commandLineJFrame.setVisible(true);

    }

    public static void setNimbusLookAndFill() {
        for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {

            if ("Nimbus".equals(info.getName())) {
                try {
                    UIManager.setLookAndFeel(info.getClassName());
                    Printer.print("Was set Nimbus");

                    break;
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                    Printer.print("Cant set laf: "+ex);
                }
            }
        }
    }

    public static void setSystemLookAndFill() {
        try {

             UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
              Printer.print("Was set System");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            Printer.print("Cant set laf: "+e);
        }
    }

    public static void main(String[] args) {
        files = new Files();
        if (!new  File("laf").exists()) {

            files.writeConfig("laf", "Nimbus");
        }
        String s=files.readConfig("laf", 2);
        if (s.equals("Nimbus")) {

                setNimbusLookAndFill();
                LAF=false;


        }
        if (s.equals("System")) {
            setSystemLookAndFill();
            LAF=true;
        }
        Printer.print("Hello!");

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new DoomLauncher();
            }
        });

    }

    public void run() {
        while (true) {
            try {

                document.insertString(document.getLength(), Printer.getLogStr(), null);

            } catch (BadLocationException ex) {
                Printer.print(ex.toString());
            }
            try {
                t.sleep(500);
            } catch (InterruptedException ex) {
                Printer.print(ex.toString());
            }
        }
    }

    public class Misc {

        JPanel miscJPanel;

        JComboBox skillJComboBox;
        JTextField mapJTextField;
        JTextField[] dmFlagJTextFields = new JTextField[DMFLAGS_NUM];
        JTextField[] compatJTextFields = new JTextField[2];
        public CompatFlags compatFlags;
        public CompatFlags compatFlags2;
        JComboBox falingDamageJComboBox;
        JCheckBox svCheatsCheckBox;
        Document[] documents;
        JPanel southPanel1;
        JPanel southPanel2;
        JTabbedPane jTabbedPane;

        String miscParam;
        String[] miscArgs = new String[256];
        int[] dmFlags = new int[DMFLAGS_NUM];
        String[] dmFlagsNames = new String[DMFLAGS_NUM];
        boolean[] dmFlagsOn = new boolean[DMFLAGS_VALUE.length];

        int[] compat = new int[2];
        String[] compatTextFieldsNames = new String[2];

        JPanel southPanel;

        public Misc() {
            init();

            miscJPanel = new JPanel();
            JLabel skillJLabel = new JLabel("Skill: ");
            skillJComboBox = new JComboBox<String>(SKILLS);

            JLabel mapJLabel = new JLabel("Map: ");
            mapJTextField = new JTextField(15);

            JLabel fallingDamgeJLabel = new JLabel("Falling damage: ");

            falingDamageJComboBox = new JComboBox<String>(FALLING_DAMAGE);
            falingDamageJComboBox.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    switch (falingDamageJComboBox.getSelectedIndex()) {
                        case 0:
                            dmFlagsOn[0] = false;
                            dmFlagsOn[1] = false;
                            dmFlagsOn[2] = false;
                            break;
                        case 1:
                            dmFlagsOn[0] = true;
                            dmFlagsOn[1] = false;
                            dmFlagsOn[2] = false;
                            break;
                        case 2:
                            dmFlagsOn[1] = true;
                            dmFlagsOn[0] = false;
                            dmFlagsOn[2] = false;
                            break;
                        case 3:
                            dmFlagsOn[2] = true;
                            dmFlagsOn[0] = false;
                            dmFlagsOn[1] = false;
                            break;
                    }
                    calcDmFlags(1);
                }
            });

            JLabel[] dmFlagsJLabels = new JLabel[DMFLAGS_NUM];
            for (int i = 0; i < dmFlags.length; i++) {
                if (i != 0) {
                    dmFlagsNames[i] = "+dmflags" + (i + 1) + " ";
                    dmFlagsJLabels[i] = new JLabel("DMFlags" + i);
                } else {
                    dmFlagsJLabels[i] = new JLabel("DMFlags");
                    dmFlagsNames[i] = "+dmflags ";
                }

                dmFlagJTextFields[i] = new JTextField(10);

            }

            JLabel[] compatJLabels = new JLabel[2];
            documents = new Document[2];
            for (int i = 0; i < compat.length; i++) {
                if (i != 0) {
                    compatTextFieldsNames[i] = "+compatFlags" + (i + 1) + " ";
                    compatJLabels[i] = new JLabel("CompatFlags" + i);
                } else {
                    compatJLabels[i] = new JLabel("DcompatFlags");
                    compatTextFieldsNames[i] = "+compatFlags ";
                }

                compatJTextFields[i] = new JTextField(10);

                documents[i] = compatJTextFields[i].getDocument();

            }

            documents[0].addDocumentListener(new DocumentListener() {
                public void changedUpdate(DocumentEvent e) {
                    System.out.println("change1");
                }

                public void insertUpdate(DocumentEvent e) {
                    int[] translatedFlags = translateFlag(compatJTextFields[0].getText());
                    for (int i = 0; i < compatFlags.compatCheckBox.length; i++) {
                        if (translatedFlags[i] == 1) {
                            compatFlags.compatCheckBox[i].setSelected(true);
                        } else {
                            compatFlags.compatCheckBox[i].setSelected(false);
                        }
                    }

                }

                public void removeUpdate(DocumentEvent e) {
                    int[] translatedFlags = translateFlag(compatJTextFields[0].getText());
                    for (int i = 0; i < compatFlags.compatCheckBox.length; i++) {
                        if (translatedFlags[i] == 1) {
                            compatFlags.compatCheckBox[i].setSelected(true);
                        } else {
                            compatFlags.compatCheckBox[i].setSelected(false);
                        }
                    }
                }
            });
            documents[1].addDocumentListener(new DocumentListener() {
                public void changedUpdate(DocumentEvent e) {

                }

                public void insertUpdate(DocumentEvent e) {
                    int[] translatedFlags = translateFlag(compatJTextFields[1].getText());
                    for (int i = 0; i < compatFlags2.compatCheckBox.length; i++) {
                        if (translatedFlags[i] == 1) {
                            compatFlags2.compatCheckBox[i].setSelected(true);
                        } else {
                            compatFlags2.compatCheckBox[i].setSelected(false);
                        }
                    }
                }

                public void removeUpdate(DocumentEvent e) {
                    int[] translatedFlags = translateFlag(compatJTextFields[1].getText());
                    for (int i = 0; i < COMPAT2_SIZE; i++) {
                        if (translatedFlags[i] == 1) {
                            compatFlags2.compatCheckBox[i].setSelected(true);
                        } else {
                            compatFlags2.compatCheckBox[i].setSelected(false);
                        }
                    }
                }
            });

            DLJCheckBox[] dmFlagsCheckBoxses = new DLJCheckBox[3];
            int dmFlagCheckBoxId = 3;
            for (int i = 0; i < dmFlagsCheckBoxses.length; i++) {
                dmFlagsCheckBoxses[i] = new DLJCheckBox(DMFLAGS_NAMES[dmFlagCheckBoxId], this, dmFlagCheckBoxId, DLJCCHECK_DMFLAGS);
                dmFlagCheckBoxId++;
            }

            svCheatsCheckBox = new JCheckBox("cheats");

            JPanel flagsJPanel = new JPanel();
            flagsJPanel.add(fallingDamgeJLabel);
            flagsJPanel.add(falingDamageJComboBox);
            for (int i = 0; i < dmFlagsCheckBoxses.length; i++) {
                flagsJPanel.add(dmFlagsCheckBoxses[i]);
            }
            flagsJPanel.add(svCheatsCheckBox);

            southPanel1 = new JPanel();
            for (int i = 0; i < DMFLAGS_NUM; i++) {
                southPanel1.add(dmFlagsJLabels[i]);
                southPanel1.add(dmFlagJTextFields[i]);

            }

            southPanel2 = new JPanel();
            for (int i = 0; i < 2; i++) {
                southPanel2.add(compatJLabels[i]);
                southPanel2.add(compatJTextFields[i]);

            }

            southPanel = new JPanel();
            southPanel.add(southPanel1);

            compatFlags = new CompatFlags(this, COMPAT_NAMES, COMPAT_DISCRIPTION, 0);
            compatFlags2 = new CompatFlags(this, COMPAT2_NAMES, COMPAT2_DESCRIPTION, 1);

            jTabbedPane = new JTabbedPane();
            jTabbedPane.addTab("Flags", flagsJPanel);
            jTabbedPane.addTab("compatibility", compatFlags);
            jTabbedPane.addTab("compatibility2", compatFlags2);
            jTabbedPane.addChangeListener(new ChangeListener() {
                @SuppressWarnings("override")
                public void stateChanged(ChangeEvent e) {
                    if (jTabbedPane.getSelectedIndex() == 0) {
                        //Printer.print("111");
                        southPanel.removeAll();

                        southPanel.add(southPanel1);
                        frame.repaint();

                    }
                    if (jTabbedPane.getSelectedIndex() == 1 || jTabbedPane.getSelectedIndex() == 2) {
                        // Printer.print("222");
                        southPanel.removeAll();

                        southPanel.add(southPanel2);
                        frame.repaint();

                    }
                }
            });

            miscJPanel.setLayout(new BorderLayout());
            JPanel northJPanel = new JPanel();
            northJPanel.add(skillJLabel);
            northJPanel.add(skillJComboBox);

            northJPanel.add(mapJLabel);
            northJPanel.add(mapJTextField);

            miscJPanel.add(northJPanel, BorderLayout.NORTH);
            miscJPanel.add(jTabbedPane, BorderLayout.CENTER);
            miscJPanel.add(southPanel, BorderLayout.SOUTH);

        }

        public void compatUpdate(int c, int mode) {
            if (mode == 1) {
                int[] translatedFlags = translateFlag(c);
                for (int i = 0; i < COMPAT_SIZE; i++) {
                    if (translatedFlags[i] == 1) {
                        compatFlags.compatCheckBox[i].setSelected(true);
                    } else {
                        compatFlags.compatCheckBox[i].setSelected(false);
                    }
                }
            }
            if (mode == 2) {
                int[] translatedFlags = translateFlag(c);
                for (int i = 0; i < COMPAT2_SIZE; i++) {
                    if (translatedFlags[i] == 1) {
                        compatFlags2.compatCheckBox[i].setSelected(true);
                    } else {
                        compatFlags2.compatCheckBox[i].setSelected(false);
                    }
                }

            }

        }

        public void init() {
            for (int i = 0; i < dmFlags.length; i++) {
                dmFlags[i] = 0;
            }

            miscParam = new String();
            for (int i = 0; i < miscArgs.length; i++) {
                miscArgs[i] = " ";
            }

        }

        private void updateDmflags() {
            for (int i = 0; i < DMFLAGS_NUM; i++) {
                try {
                    dmFlags[i] = Integer.parseInt(dmFlagJTextFields[i].getText());
                } catch (NumberFormatException e) {
                }

            }
        }

        private void updateCompat() {
            for (int i = 0; i < compat.length; i++) {
                try {
                    compat[i] = Integer.parseInt(compatJTextFields[i].getText());
                } catch (NumberFormatException e) {
                }

            }
        }

        public void calcDmFlags(int flag) {
            init();
            Integer buff = 0;
            for (int i = 0; i < DMFLAGS_VALUE.length; i++) {
                if (dmFlagsOn[i]) {
                    buff += DMFLAGS_VALUE[i];
                }

            }
            dmFlagJTextFields[0].setText(buff.toString());

        }

        public void setFlag(int idFlag, int flag) {
            compat[idFlag] = compat[idFlag] ^ 1 << flag;
            compatJTextFields[idFlag].setText(Integer.toString(compat[idFlag]));

        }

        public int[] translateFlag(String flagValue) {
            try {
                return translateFlag(Integer.parseInt(flagValue));
            } catch (NumberFormatException e) {
                System.out.println(e);
                return translateFlag(0);
            }

        }

        public int[] translateFlag(int flagValue) {
            int[] buff = new int[31];
            for (int i = 0; i < 31; i++) {
                int mask = 0b00000001;
                buff[i] += (mask & flagValue >>> i);
                System.err.print(buff[i]);
            }
            return buff;

        }

        public void changes() {
            init();
            updateDmflags();

            int id = 0;
            if (skillJComboBox.getSelectedIndex() != 0) {
                miscArgs[0] = "-skill " + (skillJComboBox.getSelectedIndex() - 1);
            } else {
                miscArgs[0] = "";
            }

            if (mapJTextField.getText().length() > 0) {
                miscArgs[1] = "+map " + mapJTextField.getText();
            }
            id = 2;
            for (int i = 2; i < DMFLAGS_NUM + 2; i++) {
                int dmflagID = i - 2;

                if (dmFlags[dmflagID] != 0) {
                    miscArgs[id] = dmFlagsNames[dmflagID] + dmFlags[dmflagID];
                    id++;
                }
            }
            int sv_cheats = (svCheatsCheckBox.isSelected()) ? 1 : 0;
            if (sv_cheats > 0) {
                miscArgs[id] = "+sv_cheats " + (sv_cheats);
                id++;
            }
            for (int i = 0; i < compat.length; i++) {
                if (compat[i] != 0) {
                    miscArgs[id] = compatTextFieldsNames[i] + compat[i];
                    id++;
                }

            }
            for (int i = 0; i < miscArgs.length; i++) {
                miscParam += " " + miscArgs[i];
            }
        }

    }

}
