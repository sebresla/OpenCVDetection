
package Core;

import java.util.Date;

public class LogsHelper 
{
    /*  ==========================================  *
     *  VARIABLES AND CONSTANST
     *  ==========================================  */
    private static FilesHelper filesHelper;
    
    /*  ==========================================  *
     *  PUBLIC METHODS
     *  ==========================================  */
    public static void Log(Exception exception)
    {
        try 
        {
            if (filesHelper == null)
                filesHelper = new FilesHelper();
            
            System.out.println("ERROR: " + exception);
            filesHelper.SaveFile(GlobalData.path + "Log.txt", 
                    "------------------------------------------------------------", false);
            filesHelper.SaveFile(GlobalData.path + "Log.txt", 
                    GlobalData.simpleDateFormat.format(new Date()), false);
            filesHelper.SaveFile(GlobalData.path + "Log.txt", exception.toString(), false);
        }
        catch (Exception ex)
        {
            System.out.println(ex);
        }
    }
}