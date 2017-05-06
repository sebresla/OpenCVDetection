
package Core;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;

public class XmlHelper
{
    /*  ==========================================  *
     *  VARIABLES AND CONSTANST
     *  ==========================================  */
    private String pathXml = "";
    
    /*  ==========================================  *
     *  PROPERTIES
     *  ==========================================  */
    public void setPathXml(String value) { pathXml = value; }
        
    /*  ==========================================  *
     *  PUBLIC METHODS
     *  ==========================================  */
    public String GetValue(String value) 
    {
        try
        {
            File file = new File(pathXml);
            XPathFactory xPathfactory = XPathFactory.newInstance();
            XPath xpath = xPathfactory.newXPath();

            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(file);
            document.getDocumentElement().normalize();

            return xpath.compile(value).evaluate(document);
        }
        catch (Exception ex)
        {
            System.out.println(ex);
            return "";
        }
    }
}