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
public class DLJCheckBox extends JCheckBox implements Constants{

    public DLJCheckBox(String string,final Misc m, final int dmflagnum, int mode) {
        super(string);
        if(mode==DLJCCHECK_DMFLAGS){
            addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        m.dmFlagsOn[dmflagnum]=isSelected();
                        m.calcDmFlags(1);

                    }
                });
        }
        if(mode==DLJCCHECK_COMPAT){
            addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        m.compatOn[dmflagnum]=isSelected();
                        m.calcCompat();
                        m.compatFlags.setTextArea(COMPAT_DISCRIPTION[dmflagnum]);

                    }
                });
        }
    }
}
