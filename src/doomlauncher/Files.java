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
    
    
    public File[] engine;
    public String[] engineName;
    
    
 

    public Files() {
        if (new File(IWAD_FOLDER_CONFIG).exists()) {
            allFiles=new File(readIWADConfig(IWAD_FOLDER_CONFIG,0,1)).listFiles();
        }else{
            setConfig("/", IWAD_FOLDER_CONFIG,0);
        }
        if (new File(PWAD_FOLDER_CONFIG).exists()){
            
        }else{
            setConfig("/", PWAD_FOLDER_CONFIG,0);
        }
        if (new File(ENGINE_FOLDER_CONFIG).exists()){
            Integer numEn=0;
            try {
                numEn=Integer.parseInt(readIWADConfig(ENGINE_FOLDER_CONFIG, 0, 1));
            } catch (NumberFormatException e) {
                setConfig(ENGINE[0], ENGINE_FOLDER_CONFIG,1);
                
            }
            
            engine=new File[numEn];
            engineName=new String[numEn];
            for (int i = 0; i < engine.length; i++) {
                setEngine(readIWADConfig(ENGINE_FOLDER_CONFIG,i,2),i);
            }
            
        }else{
            engine=new File[1];
            engineName=new String[1];
            setConfig(ENGINE[0], ENGINE_FOLDER_CONFIG,1);
        }
       
        
        initIWADS();
        initPWADS();
     }
    
    
    public void setEngine(String path, int id){
       
        engine[id]=new File(path);
        engineName[id]=engine[id].getName();
    }
    
    public String readConfig(String confName, Integer stringNum){
        File conf=new File(confName);
        String buff;
        try {
            BufferedReader bf=new BufferedReader(new FileReader(conf));
            try {
                for (int i = 0; i <= stringNum; i++) {
                    buff=bf.readLine();
                    if(i==stringNum)
                        return buff;
                }
            } catch (IOException ex) {
                
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Error read config "+ex);
        }
        return null;
    }
    
    public void setConfig(String path, String name, Integer str){
    
        
        File defaultFolderConfig=new File(name);
        try {
            FileWriter defaultFolderConfigWriter=new FileWriter(defaultFolderConfig);
            if(str>0){
                defaultFolderConfigWriter.append(str.toString()+"\n");
                int ideng=0;
                for (int i = 1; i <=str ; i++) {
                    if(i==str)
                        defaultFolderConfigWriter.append(path+"\n");
                    else
                        defaultFolderConfigWriter.append(engine[ideng].getAbsolutePath()+"\n");
                        ideng++;
                }
            }
            else
              defaultFolderConfigWriter.append(path+"\n");
            defaultFolderConfigWriter.flush();
            defaultFolderConfigWriter.close();
        } catch (IOException ex) {
            
        }
        
    }
    
    public void addEngine(String path){
        int engines=engine.length;
        setConfig(path, ENGINE_FOLDER_CONFIG, engines+1);
        engine=new File[engines+1];
        engineName=new String[engines+1];
            for (int i = 0; i < engine.length; i++) {
               setEngine(readIWADConfig(ENGINE_FOLDER_CONFIG,i,2),i);
            }
        
    }
    
    public String readIWADConfig(String name,int  id, int mode){
        File conf=new File(name);
        String buff;
        try {
            BufferedReader bf=new BufferedReader(new FileReader(conf));
            try {
                if(mode==1)
                    return bf.readLine();
                if(mode==2)
                    buff=bf.readLine();
                    for (int i = 0; i <=id; i++) {
                        buff=bf.readLine();
                        if (i==id) {
                            return buff;
                        }
                    }
                
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
