
package processingtest;

import Core.GlobalData;
import Core.LogsHelper;
import Core.XmlHelper;
import javax.swing.JOptionPane;
import org.opencv.core.Core;

public class ProcesingTest
{
    private static boolean Loaded = false;
    
    /*  ==========================================  *
     *  MAIN
     *  ==========================================  */
    public static void main(String[] args) throws Throwable
    {
        try 
        {
            LoadAppConfig();
            if (!Loaded)
                return;
            
            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
            ProcessingApplet.main("processingtest.ProcessingApplet");
            
            String hola = "prueba commit";
        }
        catch (Exception ex)
        {
            LogsHelper.Log(ex);
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    /*  ==========================================  *
     *  PRIVATE METHODS
     *  ==========================================  */
    private static void LoadAppConfig() throws Exception
    {
        try
        {
            XmlHelper xmlHelper = new XmlHelper();
            xmlHelper.setPathXml("App.config.xml");
            GlobalData.path = xmlHelper.GetValue("/configuration/path");
            GlobalData.client = xmlHelper.GetValue("/configuration/client");
            GlobalData.oneThread = xmlHelper.GetValue("/configuration/oneThread");
            Loaded = true;
        }
        catch (Exception ex)
        {
            LogsHelper.Log(ex);
            JOptionPane.showMessageDialog(null, ex);
        }
    }
}