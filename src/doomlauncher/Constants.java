/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package doomlauncher;

/**
 *
 * @author loludaed
 */
public interface Constants{
    public static final int LAUNCH_CMD_ARG_ENGINE=0;
    public static final int LAUNCH_CMD_ARG_IWAD=1;
    public static final int LAUNCH_CMD_ARG_IWADPATH=2;
    public static final int LAUNCH_CMD_ARG_FILE=3;
    public static final int LAUNCH_CMD_ARG_FILEPATH=4;
    
    public static final String IWAD_FOLDER_CONFIG="iwad.cfg";
    public static final String PWAD_FOLDER_CONFIG="wad.cfg";
    public static final String ENGINE_FOLDER_CONFIG="engine.cfg";

    
    
    public static final int FILE_CHOOSE_IWAD=1;
    public static final int FILE_CHOOSE_PWAD=2;
    public static final int FILE_CHOOSE_ENGINE=3;
    
    
    
    
    
    
    public static String[] ENGINE={"zandronum"};
    public static final String[] IWAD_NAMES={"DOOM.WAD","DOOM2.WAD","HERETIC.WAD","HEXEN.WAD","STRIFE1.WAD"};
    
    
}
