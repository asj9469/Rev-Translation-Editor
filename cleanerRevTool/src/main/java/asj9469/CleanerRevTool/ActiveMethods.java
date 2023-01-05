package asj9469.CleanerRevTool;

import java.awt.Font;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import javax.swing.*;
import java.util.regex.*;

public class ActiveMethods{

    //method to translate the actual text
    public static String translate(String langFrom, String langTo, String originalText, int startIndex, int endIndex) throws IOException {
        //basic set up for the to-be-translated text
        String text = originalText.substring(startIndex, endIndex);
        
        String urlStr = "https://script.google.com/macros/s/AKfycbz9LQYsz_M3oSAXRKOv83k2n8PwW8glTG3SE0AJLEmQDCaz4CUE74dpsvR4CXhL7aKONA/exec"
                + "?q=" + URLEncoder.encode(text, "UTF-8")
                + "&target=" + langTo
                + "&source=" + langFrom;
        URL url = new URL(urlStr);
        StringBuilder response = new StringBuilder();
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }
    
    //method to insert the translated text in a given spot
    public static String insertTranslate(String originalString,int splitIndex, int startIndex, int endIndex) throws IOException {

        String newString = new String();

        for (int i = 0; i < originalString.length(); i++) {

            // Insert the original string character into the new string
            newString += originalString.charAt(i);

            if (i == splitIndex) {

                String translatedText = translate("en", "ko", newString, startIndex, endIndex);
                translatedText = translatedText.replaceAll("&quot;","\"");
                // Insert the string to be inserted into the new string
                newString += ("\n" + translatedText + "\n\n");
            }
        }

        // return the modified String
        return newString;
    }
    
    //changes the font for the entire UI of the program
    public static void setUIFont(Font f) {
        java.util.Enumeration keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof javax.swing.plaf.FontUIResource) {
                UIManager.put(key, f);
            }
        }
    }
    
    //finds the word in the text area and selects it
    public static void findAndSelectString(JTextArea textArea, String wordToFind) {
        Pattern pattern = Pattern.compile("\\b" + wordToFind + "\\b");
        Matcher matcher = pattern.matcher(textArea.getText()); //Where input is a TextInput class
        boolean found = matcher.find(0);
        if (found) {
            textArea.setSelectionStart(matcher.start());
            textArea.setSelectionEnd(matcher.end());
        }
    }
    
    
}
