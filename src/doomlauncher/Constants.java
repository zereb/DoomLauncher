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
    
    public static final String[] SKILLS={
        "I'm too young to die","Hey, not too rough","Hurt me plenty","Ultra-Violence","Nightmare!"};
    public static final String[] FALLING_DAMAGE={
        "None", "Falling damage (Old)","Falling damage (Hexen)","Falling damage (Strife)"};

    
    
    public static final int FILE_CHOOSE_IWAD=1;
    public static final int FILE_CHOOSE_PWAD=2;
    public static final int FILE_CHOOSE_ENGINE=3;
    
    
    public static final int DMFLAGS_NUM=3;
    public static final int DMFLAGS=0;
    public static final int DMFLAGS2=1;
    public static final int DMFLAGS3=2;
 
    public static final int DLJCCHECK_DMFLAGS=1;
    public static final int DLJCCHECK_COMPAT=2;
    
    

    
    
    
    
    
    
    public static String[] ENGINE={"zandronum"};
    public static final String[] IWAD_NAMES={
        "DOOM.WAD","DOOM2.WAD","HERETIC.WAD","HEXEN.WAD","STRIFE1.WAD"};
    
   public static final String[] DMFLAGS_NAMES={
       "","","","Fast monstres","Allow jump","Allow crouch"};
   
    
    public static final int[] DMFLAGS_VALUE={
        8,          //0 faling damadge old
        16,         //1 faling damadge hexen
        24,         //2 faling damadge strife
        32768,      //3 fast monstres
        536870912,  //4 Allow jumping
        1073741824  //5 Allow crouch    
        
        
    };
    
     public static final String[] COMPAT_NAMES={
         "Find shortest textures like Doom"
     };
     public static final String[] COMPAT_DISCRIPTION={
         "In Doom, AASHITTY in Doom II or AASTINKY in Doom was created to fill in the place of texture index 0, normally treated by the Doom engine as no texture at all. When this is enabled, the \"raise platform by lower texture\" line special and MAP07 floor raise considers AASHITTY or AASTINKY as a valid texture and uses it's height when raising a platform. If disabled, these textures aren't considered valid and are ignored."
     };
      
    public static final int[] COMPAT_VALUE={
        1
        
        
    };
    
    
   
}
