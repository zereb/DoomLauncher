/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package doomlauncher;

import doomlauncher.DoomLauncher.Misc;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JCheckBox;

/**
 *
 * @author loludaed
 */
public class DLJCheckBox extends JCheckBox{

    public DLJCheckBox(String string,final Misc m, final int dmflagnum) {
        super(string);
        addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    m.dmFlagsOn[dmflagnum]=isSelected();
                    m.calcDmFlags(1);
                   
                }
            });
    
    }
}
