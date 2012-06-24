package com.bright.translt;


import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.Charset;

/**
 *
 * @author dkhaburdzania
 * email: dato7077@gmail.com
 */

class Translator{
    public static Socket socket;
    
    public static String translate(String word) throws Exception {
         String answer = "";
         if(socket == null || socket.isClosed())
             socket = new Socket(InetAddress.getByName("translate.ge"), 80);
         
         BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
         PrintWriter writer = new PrintWriter( socket.getOutputStream() );
         String request = "GET " + "http://beta.translate.ge/Main/Translate?text=" + word +
                 "&lang=en" + " HTTP/1.0\r\n\n";
         writer.write(request);
         writer.flush();
         String line = ""; String json="";
         
         //read json string
         while( (line=reader.readLine()) != null){
             if(line.trim().length() == 0){
                 while( (line=reader.readLine()) != null){
                     json += line;
                 }
                 break;
             }
         } 
         answer = Translator.JsonToString(json);
         
         socket.close();
         reader.close();
         writer.close();
         return answer;
    }
    
    public static String JsonToString(String json){
        String result = "";
        Gson gson = new Gson();
        Dict[] d = gson.fromJson(json, Dict[].class);
        result = d[0].toString();
        return result;
    }
}

class Dict{
    String Text;
    String Word;
    transient Integer DictType;
    transient Integer id;
    String ReqText;

    @Override
    public String toString() {
        String[] tr = Text.split(",");
        String result = "";
        for (int i = 0; i <4; i++) {
            if(tr.length <= i) break;
            result += tr[i] + "\n";
        }
        return result;
    }
    
}