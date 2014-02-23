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
    int id=0;
    int mode;
    Files files;
    
    public FileChoose(Files files, int mode, int id) {
        this.files=files;
        this.mode=mode;
        this.id=id;
        gui();
    }
     
    
    FileChoose(Files files, int mode){ 
        this.files=files;
        this.mode=mode;
        gui();
    }
    public void gui()
    {
        
       
        if (mode == FILE_CHOOSE_IWAD) {
            JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.setFileSelectionMode(jFileChooser.DIRECTORIES_ONLY);
            jFileChooser.setSelectedFile(new File(files.readIWADConfig(IWAD_FOLDER_CONFIG,0,1)));
            int ret = jFileChooser.showOpenDialog(null);
            if (ret == JFileChooser.APPROVE_OPTION) {
                folder = new File(jFileChooser.getSelectedFile().getAbsolutePath());
                files.setConfig(folder.getAbsolutePath(), IWAD_FOLDER_CONFIG,0);
                files.allFiles = folder.listFiles();

            }

        }
        if (mode == FILE_CHOOSE_PWAD) {
            FileNameExtensionFilter wadsFileFilter=new FileNameExtensionFilter("Wads, pk3, pk7 files", "wad","pk7","pk3");
            JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.setFileSelectionMode(jFileChooser.FILES_ONLY);
            jFileChooser.setSelectedFile(new File(files.readIWADConfig(PWAD_FOLDER_CONFIG,0,1)));
            jFileChooser.setFileFilter(wadsFileFilter);
            
            jFileChooser.setMultiSelectionEnabled(true);
            int ret = jFileChooser.showOpenDialog(null);
            if (ret == JFileChooser.APPROVE_OPTION) {
                files.setConfig(jFileChooser.getCurrentDirectory().getAbsolutePath(), PWAD_FOLDER_CONFIG,0);
                files.addPWADS(jFileChooser.getSelectedFiles());
                

            }

        }
        if (mode == FILE_CHOOSE_ENGINE) {
            JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.setFileSelectionMode(jFileChooser.FILES_ONLY);
            jFileChooser.setMultiSelectionEnabled(false);
            jFileChooser.setSelectedFile(files.engine[id]);
           
            int ret = jFileChooser.showOpenDialog(null);
            if (ret == JFileChooser.APPROVE_OPTION) {
                System.err.println(id);
                files.changeEngine(id, jFileChooser.getSelectedFile().getAbsolutePath());
                

            }
        }
        if (mode == FILE_ADD_ENGINE) {
            JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.setFileSelectionMode(jFileChooser.FILES_ONLY);
            jFileChooser.setSelectedFile(new File(files.readIWADConfig(ENGINE_FOLDER_CONFIG,0,2)));
            
            jFileChooser.setMultiSelectionEnabled(false);
            int ret = jFileChooser.showOpenDialog(null);
            if (ret == JFileChooser.APPROVE_OPTION) {

                files.addEngine(jFileChooser.getSelectedFile().getAbsolutePath());

            }

        }
        
        
    }
    
   
}
