/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 *//*
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
    int engineId=0;
    int mode;
    int iwadId;
    String[] flags;
    String[] configString;
    public boolean succesfullyReadConfig;
    File[] pwads;
    Files files;

    public FileChoose(Files files, int mode, int engineId) {
        this.succesfullyReadConfig = false;
        this.files=files;
        this.mode=mode;
        this.engineId=engineId;
        gui();
    }
    public FileChoose(Files files, int mode, int engineId, int iwadId, File[] pwads, int[] flags) {
        this.succesfullyReadConfig = false;
        this.files=files;
        this.mode=mode;
        this.engineId=engineId;
        this.iwadId=iwadId;
        this.pwads=pwads;
        String[] buff=new String[flags.length];
        for (int i = 0; i < buff.length; i++) {
            buff[i]=String.valueOf(flags[i]);
        }
        this.flags=buff;
        gui();
    }



    FileChoose(Files files, int mode){
        this.succesfullyReadConfig = false;
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
            jFileChooser.setSelectedFile(files.engine[engineId]);

            int ret = jFileChooser.showOpenDialog(null);
            if (ret == JFileChooser.APPROVE_OPTION) {
                System.err.println(engineId);
                files.changeEngine(engineId, jFileChooser.getSelectedFile().getAbsolutePath());


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
        if (mode == FILE_SAVE) {
            JFileChooser jFileChooser = new JFileChooser();
            FileNameExtensionFilter wadsFileFilter = new FileNameExtensionFilter("Config files","cnf");
            jFileChooser.setFileSelectionMode(jFileChooser.FILES_ONLY);
            jFileChooser.setMultiSelectionEnabled(false);
            jFileChooser.setSelectedFile(new File("MyConfig"));
            String[] config=new String[flags.length+3+pwads.length];
            config[0]=files.engine[engineId].getAbsolutePath();
            config[1]=files.iwads[iwadId].getAbsolutePath();
            int confId=2;
            for (int i = 0; i < flags.length; i++) {
                config[confId]=flags[i];
                confId++;

            }
            config[confId]=String.valueOf(pwads.length);
            confId++;
            for (int i = 0; i < pwads.length; i++) {
                config[confId]=pwads[i].getAbsolutePath();
                confId++;
            }
            int ret = jFileChooser.showSaveDialog(null);
            if (ret == JFileChooser.APPROVE_OPTION) {

                files.writeConfig(jFileChooser.getSelectedFile()+".cfg", config);

            }

        }
        if (mode == FILE_LOAD) {
            JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.setFileSelectionMode(jFileChooser.FILES_ONLY);
            jFileChooser.setMultiSelectionEnabled(false);

            int ret = jFileChooser.showOpenDialog(null);
            if (ret == JFileChooser.APPROVE_OPTION) {
                try {
                    Printer.print("Reading config: " + jFileChooser.getSelectedFile().getAbsolutePath());
                    configString = new String[Integer.parseInt(files.readConfig(jFileChooser.getSelectedFile().getAbsolutePath(), 1))];
                    for (int i = 0; i < configString.length; i++) {
                        configString[i] = files.readConfig(jFileChooser.getSelectedFile().getAbsolutePath(), 2 + i);

                        succesfullyReadConfig=true;
                    }
                    Printer.print("Done: "+ jFileChooser.getSelectedFile().getAbsolutePath());
                } catch (NumberFormatException e) {
                    configString = new String[0];
                    Printer.print("Erorr can't read config: " + e);
                }

            }

        }


    }


}
