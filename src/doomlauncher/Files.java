/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package doomlauncher;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author loludaed
 */
public class Files implements Constants{
    
    public File defaultFolder=new File("/");
    public File[] allFiles=defaultFolder.listFiles();
    private int iwadCount=0;
    
     
    public File[] iwads;
    public String[] iwadsNames;
    
    public File[] pwad;
    private int pwadCount=0;
    
    
    public File engine;
    public String engineName;
    
    
 

    public Files() {
        if (new File(IWAD_FOLDER_CONFIG).exists()) {
            allFiles=new File(readIWADConfig(IWAD_FOLDER_CONFIG)).listFiles();
        }else{
            setConfig("/", IWAD_FOLDER_CONFIG);
        }
        if (new File(PWAD_FOLDER_CONFIG).exists()){
            
        }else{
            setConfig("/", PWAD_FOLDER_CONFIG);
        }
        if (new File(ENGINE_FOLDER_CONFIG).exists()){
            setEngine(readIWADConfig(ENGINE_FOLDER_CONFIG));
        }else{
            setConfig(ENGINE[0], ENGINE_FOLDER_CONFIG);
        }
       
        
        initIWADS();
        initPWADS();
     }
    
    
    public void setEngine(String path){
        engine=new File(path);
        engineName=engine.getName();
    }
    
    
    public void setConfig(String path, String name){
        File defaultFolderConfig=new File(name);
        try {
            FileWriter defaultFolderConfigWriter=new FileWriter(defaultFolderConfig);
            defaultFolderConfigWriter.append(path);
            defaultFolderConfigWriter.flush();
            defaultFolderConfigWriter.close();
        } catch (IOException ex) {
            
        }
        
    }
    
    public String readIWADConfig(String name){
        File conf=new File(name);
        try {
            BufferedReader bf=new BufferedReader(new FileReader(conf));
            try {
                return bf.readLine();
            } catch (IOException ex) {
                
            }
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        }
        return null;
    }
    
    
    public void initIWADS(){
        int iwadID=0;
        for (int i = 0; i < IWAD_NAMES.length; i++) {
            for (int j = 0; j < allFiles.length; j++) {
                if(IWAD_NAMES[i].equals(allFiles[j].getName())){
                    iwadCount++;
                }
            }
        }
        if (iwadCount > 0) {
            iwads = new File[iwadCount];;
            iwadsNames = new String[iwads.length];
            for (int i = 0; i < IWAD_NAMES.length; i++) {
                for (int j = 0; j < allFiles.length; j++) {
                    if (IWAD_NAMES[i].equals(allFiles[j].getName())) {
                        
                        iwads[iwadID] = allFiles[j];
                        iwadsNames[iwadID] = iwads[iwadID].getName();
                        iwadID++;
                    }

                }
            }
        }else{
            iwads=new File[0];
            iwadsNames=new String[0];
        }
        
        
    
    }

    private void initPWADS() {
        pwad=new File[pwadCount];
    }
    
    public int getPWADCount(){
        return pwadCount;
    }
    public int getIWADCount(){
        return iwadCount;
    }
    public void IWADCountReset(){
        iwadCount=0;
    }
    
    public int PWADSelected(){
        if (IWADSelected()==2 && getPWADCount()>0) {
            return getPWADCount()+1;
        } else {
            return 0;
        }
    }
    public int IWADSelected(){
        if (iwadCount>0) {
            return 2;
        } else {
            return 0;
        }
    }
    
    public void addPWADS(File[] file){
        int id=0;
        pwadCount+=file.length;
        File[] buffer=pwad;
        initPWADS();
        for (int i = 0; i < buffer.length; i++) {
            pwad[i]=buffer[i];
        }
        for (int i = buffer.length; i < pwadCount; i++) {
            pwad[i]=file[id];
            id++;
        }
        
        //removeEqalsPwad();
        
    }
    
    public void removeEqalsPwad(){
        for (int i = 0; i < pwad.length; i++) {
            for (int j = i+1; j < pwad.length; j++) {
                if(pwad[i].getAbsolutePath().equals(pwad[j].getAbsolutePath())){
                    removePwad(j);
                }
            }
        }
    }
    
    public void removePwad(int id){
        if (id > -1) {
            
            File[] buffer1 = new File[id];
            File[] buffer2 = new File[pwadCount-(id+1)];
            
            
            int newStart=id+1;
            System.out.println(newStart);
            for (int i = 0; i < buffer1.length; i++) {
               buffer1[i] = pwad[i]; 
            }
            
            for (int i = 0; i < buffer2.length; i++) {
               buffer2[i] = pwad[newStart];
               newStart++;
              
            }
            
          
            pwadCount=0;
            initPWADS();
            addPWADS(buffer1);
            addPWADS(buffer2);
        }
        
        if(id==-666){
            pwadCount=0;
            initPWADS();
        }
        
            
        
    }
     
    
    
}
