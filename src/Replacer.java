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
    static void Replace() {
        Pattern pp = Pattern.compile("(\\s)*\\d(\\s)*");
        Matcher mm,mNext;

        allText=allText.replaceAll("\\s[О|о]дин|\\s[О|о]дн..]", " 1 ");
        allText=allText.replaceAll("\\sдв.", " 2");
        allText=allText.replaceAll("\\s[Т|т]ри|\\s[Т|т]рем.|\\s[Т|т]рё.", " 3");
        allText=allText.replaceAll("\\s([Ч|ч]етырьмя)|([Ч|ч]етыре)|([Ч|ч]етырё(\\S)?)|(\\s[С|с]оро)", " 4");
        allText=allText.replaceAll("\\sпят.", " 5");
        allText=allText.replaceAll("\\sшест.", " 6");
        allText=allText.replaceAll("\\sсем.", " 7");
        allText=allText.replaceAll("\\s[В|в]ос.м.", " 8");
        allText=allText.replaceAll("\\sдевя..", " 9");

        Pattern p = Pattern.compile("(\\dнадцат..|\\dадцат..)+");
        Matcher m = p.matcher(allText);
        while (m.find()){
            allText=allText.replace(m.group(), "1" + m.group().substring(0, 1) + " ");
        }

        p = Pattern.compile("(\\dдцат..)|(4к(\\S)*.)|(\\dдесят..)");
        m = p.matcher(allText);
        while (m.find()){
            mm = pp.matcher(m.group());
            if (!mm.find())
            {allText=allText.replace(m.group()," 10");}
            else {
                allText = allText.replace(m.group(), m.group().substring(0, 1)+"0");
            }
        }

        p = Pattern.compile("([(\\s)|(\\d)]с.?т(\\S)*\\s)");
        m = p.matcher(allText);
        while (m.find()){
            mm = pp.matcher(m.group());
            if (!mm.find())
            {allText=allText.replace(m.group()," 100");}
            else {
                allText = allText.replace(m.group(), m.group().substring(0, 1)+"00");
            }
        }

        Pattern p1 = Pattern.compile("[1-9]0[1-9]");
        Pattern p2 = Pattern.compile("[1-9]00[1-9]\\d");
        Pattern p3 = Pattern.compile("[1-9]00[1-9]\\s");
        m = p1.matcher(allText);
        while (m.find()){
            {allText=allText.replace(m.group(),m.group().substring(0,1)+m.group().substring(2, 3));}
        }
        m = p2.matcher(allText);
        while (m.find()){
            {allText=allText.replace(m.group(),m.group().substring(0,1)+m.group().substring(3,5));}
        }
        m = p3.matcher(allText);
        while (m.find()){
            {allText=allText.replace(m.group(),m.group().substring(0,1)+m.group().substring(2,5));}
        }

        p = Pattern.compile("(\\d)+[a-zA-Zа-яА-Я]");
        m = p.matcher(allText);
        while (m.find()){
            {allText=allText.replace(m.group(),m.group().substring(0,m.group().length()-1)+" "+m.group().substring(m.group().length()-1, m.group().length()));}
        }

        p = Pattern.compile("(.ысяч(\\S)*)|(.иллион(\\S)*)");
        m = p.matcher(allText);
        while (m.find()){
            {allText=allText.replace(m.group(),"");}
        }
    }

    static public void MakeReplacedFile(File file)	{
        try	    {

            if (!file.exists())    	{
                file.createNewFile();
            }

            if (!file.canRead())   	{
                throw new IllegalArgumentException("File cannot be written: " + file);
            }

            if (!file.isFile())   	{
                throw new IllegalArgumentException("Should not be a directory: " + file);
            }
            file.delete();
            file.createNewFile();
            FileOutputStream fis = new FileOutputStream(file);
            OutputStreamWriter out = new OutputStreamWriter(fis, _encoding);
            BufferedWriter output =  new BufferedWriter(out);
            try   	{
                output.append(allText);
            }

            finally    	{
                output.close();
            }
        }
        catch (FileNotFoundException ex)    {
            System.out.println("File does not exist: " + file);
        }
        catch(IllegalArgumentException ex)    {
            System.out.println(ex.getMessage());
        }
        catch (Exception ex)    {
            ex.printStackTrace();
        }
    }
}