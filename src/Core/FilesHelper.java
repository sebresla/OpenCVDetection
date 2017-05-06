
package Core;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

public class FilesHelper 
{
    /*  ==========================================  *
     *  VARIABLES AND CONSTANST
     *  ==========================================  */
    private BufferedWriter bufferedWriter;

    /*  ==========================================  *
     *  PUBLIC METHODS
     *  ==========================================  */
    public boolean FileExits(String pathReadFile)
    {
    	try	
    	{
            if (pathReadFile == null || pathReadFile.equals(""))
                throw new IllegalArgumentException("The file to read is null or empty");

            return Files.exists(Paths.get(pathReadFile));
        }
        catch (Exception ex)
        {
            System.out.println("Error: " + ex);
            return false;
        }	
    }
    
    public boolean SaveFile(String fileWritePath, String dataToSave, boolean noWriteOver)
    {
    	try	
    	{
            System.out.println("Call: " + new Date() + ": " + fileWritePath);        	
            if (dataToSave == null)
                throw new Exception("No data to save in file");

            bufferedWriter = new BufferedWriter(new FileWriter(fileWritePath, true));
            bufferedWriter.write(String.valueOf(dataToSave));
            bufferedWriter.newLine();
            bufferedWriter.close();
            return true;
        }
        catch (Exception ex)
        {
            System.out.println("Error: " + ex);
            return false;
        }	
    }
}