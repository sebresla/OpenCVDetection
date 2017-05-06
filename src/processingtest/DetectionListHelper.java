
package processingtest;

import Core.GlobalData;
import Core.LogsHelper;
import java.util.LinkedList;
import java.util.List;

public class DetectionListHelper 
{
    /*  ==========================================  *
     *  PUBLIC METHODS
     *  ==========================================  */
    public static List<Detections> GetDetectionsList()
    {
        List<Detections> response = new LinkedList<>();
        try
        {
            switch (GlobalData.client)
            {
                case "Faces": default: 
                    response.add(new Detections("haarcascade_frontalface_alt.xml")); 
                    break;
                case "FullBody":
                    response.add(new Detections("haarcascade_fullbody.xml")); 
                    break;
                case "LowerBody":
                    response.add(new Detections("haarcascade_fullbody.xml")); 
                    response.add(new Detections("haarcascade_lowerbody.xml")); 
                    break;
                case "UpperBody":
                    response.add(new Detections("haarcascade_fullbody.xml")); 
                    response.add(new Detections("haarcascade_upperbody.xml")); 
                    break;
                case "Body":
                    response.add(new Detections("haarcascade_fullbody.xml")); 
                    response.add(new Detections("haarcascade_lowerbody.xml"));
                    response.add(new Detections("haarcascade_upperbody.xml"));
                    break;
                case "Full":
                    response.add(new Detections("haarcascade_frontalface_alt.xml")); 
                    response.add(new Detections("haarcascade_fullbody.xml")); 
                    response.add(new Detections("haarcascade_lowerbody.xml"));
                    response.add(new Detections("haarcascade_upperbody.xml"));
                    break;
            }
            return response;
        }
        catch (Exception ex)
        {
            LogsHelper.Log(ex);
            return response;
        }
    }
}