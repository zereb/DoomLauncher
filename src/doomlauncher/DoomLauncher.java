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
import java.text.NumberFormat;
import java.util.Observable;
import java.util.Observer;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
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

/**
 *
 * @author loludaed
 */
public class DoomLauncher  implements Observer,Constants{

    /**
     * @param args the command line arguments
     */
    
    public ProcessBuilder processBuilder;
    
    JFrame frame;
    JTextArea jTextArea;
    JComboBox<String> jComboBoxEngines;
    JComboBox<String> jComboBoxIwads;
    JList<File> jListPwads;
    JPanel changeJPanel;
    JTabbedPane jTabbedPane;
    JTextArea customParamJTtextArea;

    Misc misc=new Misc();
    String[] engines=ENGINE;
    
    FileChoose fileChoose;
    Files files;
    
    
   
    
    
    ProcessBuilderD processBuilderD;
   
    
    public int WIDTH=740;
    public int HEIGHT=480;
   
    String[] argsPB;
    String[] customParametersArg;

    
    public  DoomLauncher(){
        files=new Files();
        engines[0]=files.engineName;
        
        
      


        frame=new JFrame("Doom Launcher");
        frame.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        frame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        
        CustomParametrs customParametrs=new CustomParametrs(frame);
        //List
        jComboBoxEngines=new JComboBox<String>(engines);
        JLabel enginesJLabel=new JLabel("Engine: ");
        jComboBoxIwads=new JComboBox<String>(files.iwadsNames);
        JLabel iwadsJLabel=new JLabel("Iwad: ");
    
        JButton jButtonIwad=new JButton("...");
        jButtonIwad.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                files.IWADCountReset();
                fileChoose=new FileChoose(files, FILE_CHOOSE_IWAD);
                files.initIWADS();
                jComboBoxIwads.removeAllItems();
                for (int i = 0; i < files.getIWADCount(); i++) {
                     jComboBoxIwads.addItem(files.iwadsNames[i]);
                }
               
                //initChoosePanel();
            }

            
        });
         JButton jButtonEngine=new JButton("...");
        jButtonEngine.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               
                fileChoose=new FileChoose(files, FILE_CHOOSE_ENGINE);
                jComboBoxEngines.removeAllItems();
                for (int i = 0; i < 1; i++) {
                     jComboBoxEngines.addItem(files.engineName);
                }
             
            }

            
        });
        JButton jButtonPwad=new JButton("Additional files");
        jButtonPwad.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (files.IWADSelected()>0) {
                    fileChoose=new FileChoose(files, FILE_CHOOSE_PWAD);
                    jListPwads.setListData(files.pwad);
                }else{
                    showError();
                }
                
            }

            
        });
        JButton jButtonPwadRemove=new JButton("Remove");
        jButtonPwadRemove.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                files.removePwad(jListPwads.getSelectedIndex());
                jListPwads.setListData(files.pwad);
            }

            
        });
        JButton jButtonPwadRemoveAll=new JButton("Remove all");
        jButtonPwadRemoveAll.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                files.removePwad(-666);
                jListPwads.setListData(files.pwad);
            }

            
        });
        JButton jButtonLaunch=new JButton("Launch");
        jButtonLaunch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                if (files.IWADSelected()>0) {
                   initLaunch();
                   processBuilderD=new ProcessBuilderD(argsPB);
                   processBuilderD.addObserver(DoomLauncher.this);  
                }else{
                    showError();
                }
               
                
                
            }

            
        });
        JButton jButtonCommandLine=new JButton("Command Line");
        jButtonCommandLine.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                commadLineShow();
                
                
            }

            
        });
        JButton jButtonClose=new JButton("Close");
        jButtonClose.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
                
                
            }

            
        });
        
       
        jListPwads=new JList<File>(files.pwad);
        jListPwads.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane jScrollPanePwads=new JScrollPane(jListPwads);
        
        
        //panel pwads
        JPanel pwadsJPanel=new JPanel();
        pwadsJPanel.setBorder(BorderFactory.createTitledBorder("Aditional Files:"));
        pwadsJPanel.setLayout(new BorderLayout());
        pwadsJPanel.add(jScrollPanePwads);
            JPanel pwadsButtonJPanel=new JPanel();
            pwadsButtonJPanel.setLayout(new FlowLayout());
            pwadsButtonJPanel.add(jButtonPwad);
            pwadsButtonJPanel.add(jButtonPwadRemove);
            pwadsButtonJPanel.add(jButtonPwadRemoveAll);
        pwadsJPanel.add(pwadsButtonJPanel, BorderLayout.SOUTH);
        
        
        //панель вывода 
        jTextArea=new JTextArea();
        JScrollPane jScrollPane=new JScrollPane(jTextArea);
       
        jTextArea.setEditable(false);
        
        JPanel outEXJPanel = new JPanel();
        outEXJPanel.setPreferredSize(new Dimension(300,180));
        outEXJPanel.setBorder(BorderFactory.createTitledBorder("Out"));
        outEXJPanel.setLayout(new BorderLayout());
        outEXJPanel.add(jScrollPane,BorderLayout.CENTER);
        
        //custom param
        
        JPanel customParamJPanel =new  JPanel();
        customParamJTtextArea = new JTextArea();
        JScrollPane customParamJScrollPane=new JScrollPane(customParamJTtextArea);
        JLabel customParamJLabel=new JLabel("Custom parameters (ENTER or SPACEBAR separated):");
        customParamJPanel.setLayout(new BorderLayout());
        customParamJPanel.add(customParamJLabel,BorderLayout.NORTH);
        customParamJPanel.add(customParamJScrollPane, BorderLayout.CENTER);
        
       
        
        
        
        
        //панель выбора 
       
        changeJPanel = new JPanel();
        changeJPanel.add(enginesJLabel);
        changeJPanel.add(jComboBoxEngines);
        changeJPanel.add(jButtonEngine);
        changeJPanel.add(iwadsJLabel);
        changeJPanel.add(jComboBoxIwads);
        changeJPanel.add(jButtonIwad);
        
        
        JPanel bigJPanel=new JPanel();
        bigJPanel.setLayout(new BorderLayout());
        bigJPanel.add(changeJPanel, BorderLayout.NORTH);
        bigJPanel.add(pwadsJPanel, BorderLayout.CENTER);
        bigJPanel.add(outEXJPanel, BorderLayout.SOUTH);
        
        //buttons
        JPanel buttonsJPanel=new JPanel();
        buttonsJPanel.add(jButtonLaunch);
        buttonsJPanel.add(jButtonCommandLine);
        buttonsJPanel.add(jButtonClose);
        
          
       
        
        jTabbedPane=new JTabbedPane();
        jTabbedPane.addTab("General", bigJPanel);
        jTabbedPane.addTab("Misc", misc.miscJPanel);
        jTabbedPane.addTab("Custom Parametres", customParamJPanel);
        
        
        
        
        //установка компановщика и добавление пнелей
        frame.setLayout(new BorderLayout());
        frame.add(jTabbedPane, BorderLayout.CENTER);
        frame.add(buttonsJPanel, BorderLayout.SOUTH);
        frame.setVisible(true);
       
    }
    
   
   
   private void initLaunch() {
       misc.changes();
       String customParam=customParamJTtextArea.getText()+" "+misc.miscParam;
       System.out.println(customParam.length());
       if(customParam.length()>0){
            customParametersArg = customParam.split(" ");
       }
       
       argsPB=new String[calculateARGSLength()];
       argsPB[LAUNCH_CMD_ARG_ENGINE]=files.engine.getAbsolutePath();
       if (files.getIWADCount()>0) {
           argsPB[LAUNCH_CMD_ARG_IWAD]="-iwad";
           argsPB[LAUNCH_CMD_ARG_IWADPATH]=files.iwads[jComboBoxIwads.getSelectedIndex()].getAbsolutePath();
       }

       
       if(files.getPWADCount()>0){
           int pwadID=0;
           argsPB[LAUNCH_CMD_ARG_FILE]="-file";
           for (int i = LAUNCH_CMD_ARG_FILEPATH; i < LAUNCH_CMD_ARG_FILEPATH+files.pwad.length; i++) {
               argsPB[i]=files.pwad[pwadID].getAbsolutePath(); System.out.println(i+" pwad "+pwadID);
                   pwadID++;
                  
           }    
        }   
       if (customParam.length() > 0) {
           String[] buffer = argsPB;
           System.out.println(customParametersArg.length);
           argsPB = new String[calculateARGSLength() + customParametersArg.length];
           for (int i = 0; i < argsPB.length; i++) {
               if(i<buffer.length){
                   argsPB[i]=buffer[i];
               }else{
                   argsPB[i]=customParametersArg[i-buffer.length];
               }
           }
       }
          
      
       
  }
   
   public int calculateARGSLength(){
       return 1+files.IWADSelected()+files.PWADSelected();
   }
   
   public String getCommandString(){
       
       initLaunch();
       String cmd = "";
       for (int i = 0; i < argsPB.length; i++) {
          cmd=cmd.concat(argsPB[i])+" ";
       }
       
       
       return cmd;
   }
        
    public void update(Observable o, Object arg) {
        jTextArea.setText(processBuilderD.getOutEXE());
       
    }
    
    public void showError() {
        JOptionPane.showMessageDialog(frame,"U muse", "Error",JOptionPane.ERROR_MESSAGE);
        
    }
   
    
    public void commadLineShow(){
        JDialog commandLineJFrame=new JDialog(frame, "Command Line");
        
        commandLineJFrame.setMinimumSize(new Dimension(WIDTH/2, HEIGHT/2));
        commandLineJFrame.setPreferredSize(new Dimension(WIDTH/2, HEIGHT/2));
        commandLineJFrame.setLocationRelativeTo(frame);
  
        JTextArea commandLineJTextArea=new JTextArea();
        commandLineJTextArea.setLineWrap(true);
        commandLineJTextArea.setWrapStyleWord(true);
        JScrollPane commandLineJScrollPane=new JScrollPane(commandLineJTextArea);
        
        
        commandLineJTextArea.setText(getCommandString());
        commandLineJFrame.add(commandLineJScrollPane);
        commandLineJFrame.setVisible(true);
        
        
    }
    
    public static void main(String[] args) {
          try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
               
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
    // If Nimbus is not available, you can set the GUI to another look and feel.
        }
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new DoomLauncher();
            }
        });
        
    }

    public class Misc {
        JPanel miscJPanel;
        
        JComboBox skillJComboBox;
        JTextField mapJTextField;
        JTextField[] dmFlagJTextFields=new JTextField[DMFLAGS_NUM];
        JComboBox falingDamageJComboBox;
        
        
        String miscParam;
        String[] miscArgs=new String[256];
        int[] dmFlags=new int[DMFLAGS_NUM];
        String[] dmFlagsNames=new String[DMFLAGS_NUM];
        boolean[] dmFlagsOn=new boolean[DMFLAGS_VALUE.length];
       
        
     
        public Misc() {
            init();
            
            miscJPanel = new  JPanel();
            JLabel skillJLabel=new JLabel("Skill: ");
            skillJComboBox = new JComboBox(SKILLS);
            
            JLabel mapJLabel=new JLabel("Map: ");
            mapJTextField = new JTextField(15);
            
           
            
            
            JLabel fallingDamgeJLabel=new JLabel("Falling damage: ");
            
            falingDamageJComboBox = new JComboBox(FALLING_DAMAGE);
            falingDamageJComboBox.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    switch(falingDamageJComboBox.getSelectedIndex()){
                        case 0: dmFlagsOn[0]=false; dmFlagsOn[1]=false; dmFlagsOn[2]=false;break;
                        case 1: dmFlagsOn[0]=true; dmFlagsOn[1]=false; dmFlagsOn[2]=false; break;
                        case 2: dmFlagsOn[1]=true; dmFlagsOn[0]=false; dmFlagsOn[2]=false; break;
                        case 3: dmFlagsOn[2]=true; dmFlagsOn[0]=false; dmFlagsOn[1]=false; break;
                    }
                    calcDmFlags(1);
                }
            });
            
            JLabel[] dmFlagsJLabels=new JLabel[DMFLAGS_NUM];
            for (int i = 0; i < dmFlags.length; i++) {
                if (i!=0) {
                    dmFlagsNames[i]="dmflags"+1;
                    dmFlagsJLabels[i]=new JLabel("DMFlags"+i);
                }else{
                    dmFlagsJLabels[i]=new JLabel("DMFlags");
                    dmFlagsNames[i]="dmflags";
                }
                
                dmFlagJTextFields[i]=new JTextField(10);
//                dmFlagJTextFields[i].set
            }
            
            
            DLJCheckBox[] dmFlagsCheckBoxses=new DLJCheckBox[3];
            int dmFlagCheckBoxId=3;
            for (int i = 0; i < dmFlagsCheckBoxses.length; i++) {
                dmFlagsCheckBoxses[i]=new DLJCheckBox(DMFLAGS_NAMES[dmFlagCheckBoxId], this, dmFlagCheckBoxId);
                dmFlagCheckBoxId++;
            }
            
            
            
            JPanel flagsJPanel=new JPanel();
            flagsJPanel.add(fallingDamgeJLabel);
            flagsJPanel.add(falingDamageJComboBox);
            for (int i = 0; i < dmFlagsCheckBoxses.length; i++) {
                flagsJPanel.add(dmFlagsCheckBoxses[i]);
            }
            
            
            
            JTabbedPane jTabbedPane=new JTabbedPane();
            jTabbedPane.addTab("Flags", flagsJPanel);
           
            
            miscJPanel.setLayout(new BorderLayout());
                JPanel northJPanel=new JPanel();
                northJPanel.add(skillJLabel);
                northJPanel.add(skillJComboBox);

                northJPanel.add(mapJLabel);
                northJPanel.add(mapJTextField);
                
                
                JPanel southPanel=new JPanel();
                for (int i = 0; i < DMFLAGS_NUM; i++) {
                    southPanel.add(dmFlagsJLabels[i]);
                    southPanel.add(dmFlagJTextFields[i]);
                    
                }
                
            miscJPanel.add(northJPanel, BorderLayout.NORTH);    
            miscJPanel.add(jTabbedPane, BorderLayout.CENTER);
            miscJPanel.add(southPanel, BorderLayout.SOUTH);
            
            
         
           
            
        }
        
        public void init(){
            for (int i = 0; i < dmFlags.length; i++) {
                dmFlags[i]=0;
            }
            
            miscParam=new String();
            for(int i=0; i<miscArgs.length; i++){
                miscArgs[i]=" ";
            }
        
        }
        
        private void updateDmflags() {
            for (int i = 0; i < DMFLAGS_NUM; i++) {
                try {
                    dmFlags[i]=Integer.parseInt(dmFlagJTextFields[i].getText());
                } catch (NumberFormatException e) {
                }
                
            }
        }
        
        public void calcDmFlags(int flag){
            init();
            Integer buff=0;
            for (int i = 0; i < DMFLAGS_VALUE.length; i++) {
                if(dmFlagsOn[i])
                    buff+=DMFLAGS_VALUE[i];
                
            }
            dmFlagJTextFields[0].setText(buff.toString());
            
        }
        
        public void changes(){
            init();
            updateDmflags();
            
            miscArgs[0]="-skill "+(skillJComboBox.getSelectedIndex()+1);
            if(mapJTextField.getText().length()>0)
                miscArgs[1]="+map "+mapJTextField.getText();
            for (int i = 2; i < DMFLAGS_NUM+2; i++) {
                int dmflagID=i-2;
                System.out.println("dmflag"+dmflagID+": "+dmFlags[dmflagID]);
                if(dmFlags[dmflagID]!=0)
                    miscArgs[i]="+dmflags "+dmFlags[dmflagID];
            }
            
            
            for (int i = 0; i < miscArgs.length; i++) {
                miscParam+=" "+miscArgs[i];
            }
        }

        
    }


    
    
}
