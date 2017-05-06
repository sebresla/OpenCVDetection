
package processingtest;

import Core.GlobalData;
import Core.LogsHelper;
import java.util.Date;
import java.util.HashMap;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.highgui.Highgui;
import org.opencv.objdetect.CascadeClassifier;
import processing.core.PApplet;

@SuppressWarnings("serial")
public class Detections
{
    /*  ==========================================  *
     *  VARIABLES AND CONSTANST
     *  ==========================================  */
    protected CascadeClassifier detector;
    protected HashMap<String, Object> response;
    protected String name = "";
    
    /*  ==========================================  *
     *  CONSTRUCTOR
     *  ==========================================  */
    public Detections(String xmlName) throws Exception
    {
        try
        {
            response = new HashMap<>();
            name = xmlName;
            detector = new CascadeClassifier(GlobalData.path + name);
        }
        catch (Exception ex)
        {
            LogsHelper.Log(ex);
            throw new Exception("", ex);
        }
    }
    
    /*  ==========================================  *
     *  PUBLIC METHODS
     *  ==========================================  */
    public HashMap<String, Object> Analyze(HashMap<String, Object> data) throws Exception
    {
        MatOfRect detections = new MatOfRect(); 
        try
        {
            System.out.println("3. S. Analize: " + name + GlobalData.simpleDateFormat.format(new Date()));
            response.clear();
            if (!data.containsKey("PATH"))
                throw new Exception("It's missing the path of the image in data");
            
            Mat mat = Highgui.imread(data.get("PATH").toString());
            detector.detectMultiScale(mat, detections);
            for (Rect rect : detections.toArray()) 
                ((PApplet)data.get("PAPPLET")).rect(rect.x, rect.y, rect.width, rect.height);
            response.put("DETECTIONS", detections.toArray());
            System.out.println("4. E. Analize: " + name + GlobalData.simpleDateFormat.format(new Date()));
            return response;
        }
        catch (Exception ex)
        {
            LogsHelper.Log(ex);
            throw new Exception("", ex);
        }
    }
}