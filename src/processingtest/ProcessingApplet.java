
package processingtest;

import Core.GlobalData;
import Core.LogsHelper;
import Core.RunnableHelper;
import SimpleOpenNI.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;
import javax.swing.JOptionPane;
import processing.core.PApplet;
import processing.core.PImage;

@SuppressWarnings("serial")
public class ProcessingApplet extends PApplet 
{    
    /*  ==========================================  *
     *  VARIABLES AND CONSTANST
     *  ==========================================  */
    private int count = 0;
    private SimpleOpenNI context;
    private HashMap<String, Object> data;
    private List<Detections> listDetection;
    private List<Callable<HashMap<String, Object>>> tasks;
    
    /*  ==========================================  *
     *  PUBLIC METHODS
     *  ==========================================  */
    // Implement and Override from PApplet
    @Override public void setup() 
    {
        try 
        {
            size(600 , 480); 
            frame.setTitle("PROCESSING + KINECT + OPENCV + JAVA");
            context = new SimpleOpenNI(this);
            context.enableRGB();
            context.enableDepth();
            data = new HashMap<String, Object>();
            tasks = new ArrayList<Callable<HashMap<String, Object>>>();
            listDetection = DetectionListHelper.GetDetectionsList();
            System.out.println("Size List: " + listDetection.size());
        }
        catch (Exception ex)
        {
            LogsHelper.Log(ex);
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    @Override public void draw() 
    {
        try
        {
            System.out.println("ITEM-" + count);
            System.out.println("1. Start: " + GlobalData.simpleDateFormat.format(new Date()));
            context.update(); 
            PImage image = context.rgbImage();
            PImage depth = context.depthImage();
            if (image == null || depth == null)
                return;
            image(image, 0, 0); // Draw RGB image
            //image(context.depthImage(), 0, 0); // Draw DEPTH image
            
            data.clear();
            data.put("PATH", GlobalData.path + "//IMAGES//IMAGE" + count + ".jpg");
            data.put("DEPTH", GlobalData.path + "//IMAGES//DEPTH" + count + ".jpg");
            data.put("PAPPLET", this);
            image.save(GlobalData.path + "//IMAGES//IMAGE" + count + ".jpg");
            depth.save(GlobalData.path + "//IMAGES//DEPTH" + count + ".jpg");
            
            noFill();
            stroke(404, 202, 0);
            System.out.println("2. List: " + GlobalData.simpleDateFormat.format(new Date()));
            RunDetections();
            System.out.println("5. End: " + GlobalData.simpleDateFormat.format(new Date()));
            System.out.println(" ");
            count++;
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
    private void RunDetections() 
    {
        try
        {
            if (GlobalData.oneThread.toUpperCase().equals("TRUE"))
            {
                for (Detections item : listDetection)
                    item.Analyze(data);                    
                return;
            }
            
            tasks.clear();
            for (Detections item : listDetection)
                tasks.add(() -> { return item.Analyze(data); });
            RunnableHelper.WaitAll(tasks);
        }
        catch (Exception ex)
        {
            LogsHelper.Log(ex);
        }
    }
}