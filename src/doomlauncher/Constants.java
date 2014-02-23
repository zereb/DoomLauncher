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
    public static final int FILE_ADD_ENGINE=4;
    
    
    public static final int DMFLAGS_NUM=3;
    public static final int DMFLAGS=0;
    public static final int DMFLAGS2=1;
    public static final int DMFLAGS3=2;
 
    public static final int DLJCCHECK_DMFLAGS=1;
    public static final int DLJCCHECK_COMPAT=2;
    
    

    
    
    
    
    
    
    public static String[] ENGINE={"zandronum"};
    public static final String[] IWAD_NAMES={
        "doom1.wad","freedoom1.wad","doom2.wad","tnt.wad","plutonia.wad",
         "freedm.wad","freedm.wad","freedoom2.wad","doom2f.wad","heretic1.wad","heretic.wad","hexen.wad",
         "hexdd.wad","strife0.wad","strife1.wad","chex.wad","chex3.wad","action2.wad","harm1.wad","hacx.wad","hacx2.wad",
         "strife.wad","hexendemo.wad","hexdemo.wad","blasphem.wad","blasphemer.wad","doom2bfg.wad","bfgdoom.wad","doomu.wad"
    };
    
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
         "Find shortest textures like Doom","Use buggier stair building",
         "Limit pain elementals to 20 lost souls","Don't let others hear pickups","Actors are infinitely tall","Allow silent BFG trick",
         "Enable wallrunning","Spawn item drops on the floor","All special lines can block use lines",
         "Disable Boom door light effect","Raven's scrollers use original speed","Use sector based sound target code",
         "Limit deh.MaxHealth to health bonus","Trace ign. lines w. s. sec on b. sides","No monster dropoff move",
         "Scrolling sectors are additve","Monsters see semi-invisible players","Limited movement in the air",
         "Plasma bump bug","Allow instant respawn","Disable taunts",
         "Original sound curve","Use old intermission screens/music","Disable stealth monsters",
         "old radius damage","Disable crosshair","Old weapon switch",
         "Silent instant floors","Sector sounds","Doom missile clip",
         "Monster drop off"
     };
     public static final String[] COMPAT_DISCRIPTION={
         "In Doom, AASHITTY in Doom II or AASTINKY in Doom was created to fill in the place of texture index 0, normally treated by the Doom engine as no texture at all. When this is enabled, the \"raise platform by lower texture\" line special and MAP07 floor raise considers AASHITTY or AASTINKY as a valid texture and uses it's height when raising a platform. If disabled, these textures aren't considered valid and are ignored.",
         "Causes doom to calculate heights of stairs when stairbuilding from the last (topmost) step, instead of the first (bottom-most) step.",
         "Prevents pain elementals from spawning lost souls if there are 20 or more on the map.",
         "This prevents players from hearing other players picking up items, weapons, or powerups in netgames.",
         "This causes all objects to occupy an infinite vertical space from the ceiling to the floor, which prevents monsters or the player from being able to pass over or under other solid objects, regardless of whether they are touching each other on the Z plane or not.\n" +
            "WARNING: Enabling this will break ZDoom-style thing bridges and objects that rely on height sensitivity.",
         "Forces all actors to play all sounds only on one channel, cutting off any previous sounds the actor has made. Only enable this if you can't live without the silent BFG trick, as it will cripple the sound system and prevent actors from playing any more than one sound at a time.",
         "This uses a buggier method of collision detection with walls that allows a player to run double average speed when pressing against a vertically-aligned wall.",
         "Items dropped by monsters appear on the floor below the center of the monster, instead of being tossed from the monster's corpse. May cause items to get stuck in walls or appear at the bottom of pits.",
         "Any lines with line specials prevent the player from triggering a usable line behind it, regardless of whether the frontmost line can be used itself or not.",
         "Don't do the BOOM local door light effect",
         "Raven's scrollers use their original carrying speed",
         "Use sector based sound target code.",
         "Players can not exceed over the 200% health, as you can with just the normal MaxHealth bonuses.",
         "Trace ignores lines with the same sector on both sides",
         "Monsters cannot move when hanging over a dropoff",
         "Scrolling sectors are additive like in Boom",
         "Monsters will be able to see the player even if the player has a partial invisibility sphere.",
         "By default, Zandronum gives players more maneuverability in the air than ZDoom. When this is true, ZDoom’s method is used. See Air Control for more precise details.",
         "Allows players to pick up items on the other side of solid walls by running into the wall at a specific angle. This flag should only be used for old vanilla Doom maps that really need it; it can have serious side effects on bridges and scrolling floors!",
         "Allows players to respawn instantly after being killed by holding down the \"use\" key when being hit.",
         "Prevents players from using the \"taunt\" command.",
         "Enabling this uses the original Doom sound curve, which causes sounds to fade away at a shorter distance. When disabled, the Hexen sound curve is used instead, which allows players to hear sounds louder, farther away than usual.",
         "Use doom2.exe's original intermission screens/music.",
         "Disable stealth monsters, since doom2.exe didn't have them.",
         "If this is enabled, the original Doom radius damage code is used, i.e. explosions have infinite vertical range.",
         "Players will not be able to use crosshair.",
         "Players will be forced to switch weapons every time they pick up a new weapon.",
         "Instantly moving floors are not silent",
         "Sector sounds use original method for sound origin.",
         "Use original Doom heights for clipping against projectiles",
         "Use original Doom heights for clipping against projectiles",
        
         
     };
      
    public static final int[] COMPAT_VALUE={
        1,2,4,16,32,64,128,256,512,1024,4096,16384,32768,65536,131072,262144,524288,1048576,2097152,4194304,8388608,16777216,33554432,67108864,134217728,268435456,536870912,1073741824
        
        
    };
    
    public static final String[] COMPAT2_DESCRIPTION={
    "Uses Skulltag's old behavior when (NET) ACS Scripts are used. This is disabled by default, for compatibility among other ZDoom wads. If the WAD file is designed for Skulltag, and uses NET script advantages, then you would enable this feature.",
    "This needs to be enabled to make the ACS function GetPlayerInput work on server side scripts with buttons other than BT_ATTACK, BT_USE, BT_JUMP, BT_CROUCH, BT_TURN180, BT_ALTATTACK, BT_RELOAD and BT_ZOOM. Because it increases net traffic, it should only be activated if needed.",
    "If this is enabled, players are not allowed to use the land CCMD.",
    "If this is enabled, the original Doom random table is used to generate random integers in [0,255], which should make for instance the SSG cause a little more damage.",
    "This compatflag gives the specified spheres the NOGRAVITY flag, but only when they are spawned by the map.\n" +
        "Invulnerability Sphere\n" +
        "Mega Sphere\n" +
        "Soul Sphere\n" +
        "Blur Sphere",
    "If this is enabled, the ACS scripts with a player as activator are not terminated when this player disconnects.",
    "Enables the original (greater) horizontal explosion thrust.",
    "If this is enabled, non-SOLID things (like flags) fall through invisible bridges.",
    "Enables the old ZDoom 1.23b33 jump physics.",
    "This flag disallows the ability to switch weapons while in the middle of A_Raise. Once the selected weapon starts its raising sequence, it cannot be cancelled.",
    
    };
    
    public static final String[] COMPAT2_NAMES={
        "Client side scripts (NET)","clients send full button info",
        "No land","Old random generator",
        "NOGRAVITY spheres","Don't stop player scripts on disconnect",
        "Old explosion thrust","Old bridge drops",
        "Old jump physics","No weapon switch cancellation"

    };
    
    public static final int[] COMPAT2_VALUE={
        1,2,4,16,32,64,128,256,512,
        
        
    };
   
    
   
}
