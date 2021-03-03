package net.kunmc.lab.dtf;

import net.kunmc.lab.dtf.client.handler.ClientHandler;
import net.kunmc.lab.dtf.client.handler.RenderHandler;
import net.kunmc.lab.dtf.client.shader.WaveShader;
import net.kunmc.lab.dtf.packet.PacketHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(DevilsTuneFork.MODID)
public class DevilsTuneFork {
    public static final String MODID = "devilstunefork";

    public DevilsTuneFork() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
    }

    private void setup(final FMLCommonSetupEvent event) {
        PacketHandler.init();
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(RenderHandler.class);
        MinecraftForge.EVENT_BUS.register(ClientHandler.class);

        WaveShader.getInstance().init();
    }
}
/*
*+++++++++++++++*++++++++++*+++++++********#%%%%%%%%%%%%%%%%%%%###############*#######%%%%%@@%%%%@@@%%%%%%%%%%%%%%%%%%%###******
++++++++++*+++++*++++++++++**+++++++***#%%%%%%%%%%%%%%%%%%%############****************#####%%%%%%%%%%%%%%%%%%%%%%%%%%%%%##*****
++++++++**+++++**++++++**++++*+++**#%%%%%%%%%%%%%%%%%%############**##************************##%%%%%%%%%%%%%%%%%%%%%%%%%%##****
++++++++++++**+++++++++++++++***#%%@%%@%%%%%%%%#####################******************************##%%%%%%%%%%%%%%%%%%%%%%%##***
+++++++++++++++++++++*++***##%%%@@%%%%%%%%###############***#*###************************************##%%%%%%%%%%%%%%%@@@%%%%#**
++++++++++++++++++++++***#%%%@@%%%%%%%%%%%%%######*############*****************************************##%%%%%%%%%%%%@@@@@%%%#*
++++++++++++++++*++****#%%%%%%%%%%%%%%%%%#%%%###################*******************************************#%%%%%%%%@@@@@@@@%%%%
++++++++*+++++++++++*#%%%%@@%%%%%%%#########***###################********************++++++++++++++++++*****#%%%%%%%@@@@@@@@@%%
++++++++++*+++++++*#%%%%@@%%%############****######################*******************+++++++++++++++++++++***##%%%%%%@@@@@@@%%%
++++++++++++**++**#%%%%%###############****##########################**************+++++++++++++++++++++++++++**#%%%%%%%@@@@@@%%
++++++++++++++**##%%%#################***#############################*********++++++++++++++++++++++++++++++++***#%%%@@@@@%%@@%
+++++++++++**#%%%%%##############**##***#################*****##########**+++++++++++++++++++++++++++++++++++++++**#%%%%%%%%%@@@
++++++++**#%%%%######################**###############**********#########***++++++++++++++++++++++++++++++++++++++**#%%%%%%%@@@@
++++++**#%%%#########################****###%%%######*************########***+++++++++++++++++++=+++++++++++++++++++*#%%%%%%%%%%
+++***#%##########################*##*****###%%%%####***********+***#**###***+++++++++++++++++++=+++++++++++++++++++**##%%%%%%%%
*+**#####################*****########******##%%%%###*****+++++++++**#*******++++++++++++++=========++++++++++++++++++*##%%%%%%%
**####################************#####*******##%%%##****+++++++++++*********++++++==+=++==============++++++++++++++++**#%%%@@%
##################******************####*++****###%%%##***+++++*+++++*#*******++++======================++++++++++++++++**#%@@@@
###############*****************+++*******+++****########***+***++++++*#*****++++=======+++++=========+++=+++++++++++++++*#%%@@@
###########***#***************++++++++******+++******######***+++===++******++++++=========+++++++======++++++++++++++++++*#%@@@
############******************+++++++++**+*#*++++++*****##%***++++=+++++****+++++=++++++++++++++++++=========+++++++++++++**#%@@
######**********************+++++++++++++*++**+==+++++***##***++++++**++*****++++++++++=======+++++++==========+++++++++++**#%@@
########*******************++++++++++====+++++***++++++***#****++++++#%#******++++++++======+++++++++++==========+++++++++***%%@
######*######***********++++++++++++++===+++++++*##********###*********###*+++++++++++++=+++====++++++++=======++++++++++++**#%@
######*****************++++++++++++======+=++++++++**#######%%%######**++*##++++++++++++++++++====++++++========+++++++++++**#%@
###******************+++++++++++++=======+++++++++***+****######*******+++++===+++++++++++++++=====++++++=========+++++++++**#%@
##*******************++++++++++++=========+++++********#######****+++++++++++=+++++++++++++++++======++++====++++++++++++++**#%%
*####***************+++++++++++====++++++++********************++++===+*++++*******#***+++++++++=====+++++=++++=++++++++++++*#%@
*##***************+++++++++===++++++***###***************++++++++++=++****+++++++*********+++++++====++++++=+++++++++++++++**#%@
*****************+++++++++++++++++*#####*************++++++++===+++++********+++************+**++++++++++++++++++++++++++****%%%
*****************+*++++***+***++***#####****###****++++++++++==++++*******+++++********************++++++++++++++++++++++***#%%%
******************************++********##%%%%##***++++++=++++++++**+**++++++***********************++++++++++++++++++++++**#%%%
***************************************##%@@@@%#***+++=====+++++++*++**++++**************************+++++++++++++++++++++**#%%%
****************########****************##%%%%%#****++=======+++++++**+++********************+++******+++++++++++++++++++**#%%%%
***************#######*********************######****+++===+++++++==**=+++*#######*******+++++++==******++++++++++++++*****#%%%%
****************######******************+++*#####*****+++++++++=++==**==+***##%###****+++====---===+#****+++++++++++++++**#%%%%%
****************##########*******+++++++++++*****##%##**++++++==++==*#+=+***##%%###***++==----------+*****++++++++++*++***#%%%%%
****************##############******+++++++++**++*#@@@%*++++++==++===**+++***##%%##***+===---=-------+#***+++++++++++****#%%%%%%
**********+*#%%%%%%%%%%##########*****++++****++++#%@@%*++++++===+===+**+++***##%##***+==========----=*#**++++++++++****##%%%%%%
**********+*%@@@%@@@@@@@@%%%%%###*****+++***++++++*#%%#*++++++===++===+***+++***###****+============-:+#*++++++++++****##%%%%%%%
**%@@@@@****#%@@@%%%%%%%@@@@@@@@@@%#**+++++%@@@@@@@@#**++++++=====+++==+*#*%@@@@@@@@#**++++==+==#@@@@@@@@@@@@@%%%%%%####%%%%%%%%
**%@@@@@*****##%@%%%%%%%%@@@@@@@%%##***++++%@@@@@@@@@**++++++======++++=++%@@@@@@@@@%#**++++++==%@@@@@@@@@@@@@@@@@@@@@@%%%%%%%%%
**%@@@@@#*****##%%%%%#%%@@@@@@@@%%###***+++%@@@@@%@@@%*+++++=========++++*@@@@%@@@@@####**+++++=%@@@@@##########%%%@@@@@@@%%%###
**%@@@@@#*******#%%%%%@@@@@@%%%%%%%%##****+%@@@@@*@@@@#+==++==========++*@@@@%#@@@@@#*###**+++++%@@@@@*+++++++***###%@@@@@@%%%#*
**%@@@@@##********#%@@@@@@%####%%%%%%##***+%@@@@@+*@@@@*==++======-=====%@@@%+*@@@@@***##**+++++%@@@@@=+++++***#####%%@@@@@@%#*+
**%@@@@@###*****#%@@@@@%%##****##%%%%%##***%@@@@@++#@@@@+=========---==#@@@@*+*@@@@@*****+++++==%@@@@@%#++++**###%%%%%%@@@@@@#++
++%@@@@@####**#%@@@@@%%#####*****#%%%%###**%@@@@@==+%@@@%++=========--*@@@@*=+*@@@@@#******+****%@@@@@#+++***##%%%%%%%%@@@@@@*++
++%@@@@@####%%@@@@@%###%####******#%%%###*+%@@@@@===+@@@@#+=======---+@@@@#===*@@@@@********##**%@@@@@*++***##%%%%%%%%%@@@@@@*++
++%@@@@@##%@@@@@@%*++*#%%###*******#%%##**+%@@@@@+==+*@@@@*==========%@@@%===+*@@@@@*+++++****##%@@@@@*****#%%%%%%%%%%@@@@@@%+++
++%@@@@@%@@@@@@%#*+++**%%###********%%#**++%@@@@@+++++#@@@@*========#@@@@+==++*@@@@@*+++++++***#%@@@@@***##%%%%%%%%%%@@@@@@@*+++
++%@@@@@@@@@@@%#***+++*#%##*++++****%#**+++%@@@@@++++++%@@@%+======*@@@@*=++++*@@@@@*++++++++++*%@@@@@###%%%%%%%%%%%@@@@@@%*++++
++%@@@@@%@@@@@@%#***+++#%%##*++*****##*++++%@@@@@+++++++@@@@%+++==*@@@@#++++++*@@@@@*+++++++++**%@@@@@@@@@@@@@@@@@@@@@@@@#*+++++
++%@@@@@**%@@@@@@%#**+++#%%#********#*++*++%@@@@@+++++++*@@@@#++++%@@@%+++++++*@@@@@**+***+***##%@@@@@@@@@@@@@@@@@@@@%#*++++++++
++%@@@@@++*#%@@@@@@%**+++*###******#*++++++%@@@@@++++++++#@@@@*++%@@@%++++++++*@@@@@*********#%%@@@@@@@@%%%%%##*****++++++++++++
++%@@@@@+++**#%@@@@@@%*+++*###*******++++++%@@@@@+++++++++%@@@%*#@@@@*++++++++*@@@@@********#%%%@@@@@@%%%###**++++++++++++++++++
*+%@@@@@+==++**#%@@@@@@%****##*****++++++++%@@@@@++++++++++%@@@%@@@@#+++++++++*@@@@@#******#%%%%@@@@@@%##***++++++++++++++++++++
**%@@@@@+===+++**#%@@@@@@%***++++++++++++++%@@@@@++++++++++*@@@@@@@%++++++++++#@@@@@#****#%@@@@%@@@@@@#*++++++++++++++++++++++++
**%@@@@@+====++++**#%@@@@@@%*****+*++++++++%@@@@@+++++++++++#@@@@@%*+++++++***#@@@@@#*#%%%%@@@@%@@@@@@*+++++++++++++++++++++++++
**%@@@@@++=++++++++++*%@@@@@@#*++++++++++++%@@@@@++++++++++++++++++++++++*****#@@@@@#%%%%%@@@%%#%@@@@@*+++++++++++++++++++++++++
**%@@@@@+++++++++++++++*%@@@@@@#*++++++++++%@@@@@+++++++++++++++++++++++******#@@@@@###%%%%%#**+%@@@@@*+++++++++++++++++++++++++
**%@@@@@+++++++++++++++++*%@@@@@@#+++++++++%@@@@@+++++++++++++++++++++********#@@@@@##*****+++++%@@@@@*+++++++++++++++++++++++++
#*%@@@@@+++++++++++++++++++*%@@@@@@#*++++++%@@@@@+++++++++++++++++************#@@@@@%#**++++++++%@@@@@*+++++++++++++++++++++++++
**######*++++++++++++++++++++*#######*+++++######+++++++++++++++***********+++*#%%%%#**+++++++++######++++++++++++++++++++++++++
***********+++++++++++++**++*++++++++++++++++++++++++++++++++***********+++++*******+++++++++++++++++++++++++++++++++++++++++++*
 */