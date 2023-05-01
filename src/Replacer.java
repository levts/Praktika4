import java.io.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Replacer
{
    private static String _encoding;
    private static String allText="";
    private static int num;

    public static void main(String[] args)
    {

        File testFile = new File("doc.txt");
        testFile.getAbsolutePath();
        _encoding = "utf-8";

        GetContents(testFile);

        System.out.print(allText+"\r\n");
        System.out.println("--------------------------------------------");

        Replace();

        System.out.print(allText+"\r\n");

        File newFile = new File("doc2.txt");
        newFile.getAbsolutePath();
        MakeReplacedFile(newFile);
    }


    static public void GetContents(File file)	{
        try	    {
            if (file == null)	    	{
                throw new IllegalArgumentException("File should not be null.");
            }
            if (!file.exists())	    	{
                throw new FileNotFoundException();
            }
            if (!file.canRead())	    	{
                throw new IllegalArgumentException("File cannot be written: " + file);
            }
            if (!file.isFile())	    	{
                throw new IllegalArgumentException("Should not be a directory: " + file);
            }

            FileInputStream fis = new FileInputStream(file);
            InputStreamReader in = new InputStreamReader(fis, _encoding);
            BufferedReader input =  new BufferedReader(in);

            try	   	{
                String line = null;
                while ((line = input.readLine()) != null)		{
                    allText=allText+" "+ line;
                }
            }
            finally    	{
                input.close();
            }
        }
        catch (FileNotFoundException ex)	    {
            System.out.println("File does not exist: " + file);
        }
        catch(IllegalArgumentException ex)	    {
            System.out.println(ex.getMessage());
        }
        catch (Exception ex)	    {
            ex.printStackTrace();
        }

    }