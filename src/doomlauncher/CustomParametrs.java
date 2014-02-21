/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package doomlauncher;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author loludaed
 */
public class CustomParametrs {
    private  JPanel panel;
    private  JTextArea textArea;
    
    public CustomParametrs(JFrame frame){
        panel =new  JPanel();
        textArea=new JTextArea();
        JScrollPane scrollPane=new JScrollPane(textArea);
        
        
        panel.add(scrollPane);
        
    }
    
    public JPanel getCustomParametrs(){
        return panel;
    }
    
}
