/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package doomlauncher;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author loludaed
 */
public class FileChoose implements Constants{
    File folder;
     
    
    FileChoose(Files files, int mode)
    { 
        if (mode == FILE_CHOOSE_IWAD) {
            JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.setFileSelectionMode(jFileChooser.DIRECTORIES_ONLY);
            jFileChooser.setSelectedFile(new File(files.readIWADConfig(IWAD_FOLDER_CONFIG)));
            int ret = jFileChooser.showOpenDialog(null);
            if (ret == JFileChooser.APPROVE_OPTION) {
                folder = new File(jFileChooser.getSelectedFile().getAbsolutePath());
                files.setConfig(folder.getAbsolutePath(), IWAD_FOLDER_CONFIG);
                files.allFiles = folder.listFiles();

            }

        }
        if (mode == FILE_CHOOSE_PWAD) {
            FileNameExtensionFilter wadsFileFilter=new FileNameExtensionFilter("Wads, pk3, pk7 files", "wad","pk7","pk3");
            JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.setFileSelectionMode(jFileChooser.FILES_ONLY);
            jFileChooser.setSelectedFile(new File(files.readIWADConfig(PWAD_FOLDER_CONFIG)));
            jFileChooser.setFileFilter(wadsFileFilter);
            
            jFileChooser.setMultiSelectionEnabled(true);
            int ret = jFileChooser.showOpenDialog(null);
            if (ret == JFileChooser.APPROVE_OPTION) {
                files.setConfig(jFileChooser.getCurrentDirectory().getAbsolutePath(), PWAD_FOLDER_CONFIG);
                files.addPWADS(jFileChooser.getSelectedFiles());
                

            }

        }
        if (mode == FILE_CHOOSE_ENGINE) {
            JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.setFileSelectionMode(jFileChooser.FILES_ONLY);
            jFileChooser.setSelectedFile(new File(files.readIWADConfig(ENGINE_FOLDER_CONFIG)));
            
            jFileChooser.setMultiSelectionEnabled(false);
            int ret = jFileChooser.showOpenDialog(null);
            if (ret == JFileChooser.APPROVE_OPTION) {
                files.setConfig(jFileChooser.getSelectedFile().getAbsolutePath(), ENGINE_FOLDER_CONFIG);
                files.setEngine(jFileChooser.getSelectedFile().getAbsolutePath());
                

            }

        }
        
        
    }
    
   
}
