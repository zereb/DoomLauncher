/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package doomlauncher;

import doomlauncher.DoomLauncher.Misc;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author loludaed
 */


public class CompatFlags extends JPanel implements Constants{

    DLJCheckBox compatCheckBox;
    JTextArea discriptionArea;
    public CompatFlags(Misc misc) {
        super();
        setLayout(new BorderLayout());
        JPanel compatCheckBoxes=new JPanel();
        for (int i = 0; i < COMPAT_VALUE.length; i++) {
            compatCheckBox=new DLJCheckBox(COMPAT_NAMES[i], misc, i, DLJCCHECK_COMPAT);
            compatCheckBoxes.add(compatCheckBox);
        }
        
       
        discriptionArea = new JTextArea();
        discriptionArea.setLineWrap(true);
        discriptionArea.setWrapStyleWord(true);
        discriptionArea.setEditable(false); 
        JScrollPane discriptioScrollPanen=new JScrollPane(discriptionArea);
        JPanel discriptionJPanel = new JPanel();
        discriptionJPanel.setPreferredSize(new Dimension(300,120));
        discriptionJPanel.setBorder(BorderFactory.createTitledBorder("description"));
        discriptionJPanel.setLayout(new BorderLayout());
        discriptionJPanel.add(discriptioScrollPanen,BorderLayout.CENTER);
        
        
        add(compatCheckBoxes, BorderLayout.CENTER);
        add(discriptionJPanel, BorderLayout.SOUTH);
    }
    
    public void setTextArea(String text){
        discriptionArea.setText(text);
    }
    
    
}
