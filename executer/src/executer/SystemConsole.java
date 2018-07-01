package executer;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SystemConsole 
{
   final Charset chrset = Charset.availableCharsets().get("IBM866");
   
   final String COMMAND_FILE = "task.bat";
   final String USER_DIR = System.getProperty("user.dir")+"\\";
   final String OUTPUT_FILE = USER_DIR + "task.txt";


   
   public void $write( String file, String text )
   throws IOException{
      String path = USER_DIR + file;
       
      File f = new File( path );
      if( !f.exists() ){
         if( !f.createNewFile() )
            throw new IOException( "File has not created. " + file );
      }else{
         if( !f.delete() )
            if( !f.createNewFile() )
               throw new IOException( "File has not created. " + file );
      }
      try(
         FileOutputStream fos = new FileOutputStream( f );
         OutputStreamWriter writer = new OutputStreamWriter( fos );
         PrintWriter printer = new PrintWriter( writer );
      ){
         printer.write( text );
         printer.flush();
      }
   }
   
   
   public String $readIMB( String path )
   throws FileNotFoundException,IOException {
      String result = "";
      byte[] bytes = null;
      
      File file = new File( path );
      if( !file.canRead() )
         throw new IOException( "File has not created. " + file );      
      try(
         FileInputStream is = new FileInputStream( file );
      ){
         if( is!=null ){
             bytes = new byte[is.available()];
         }
         is.read(bytes);
      }catch( Exception ex ){
         ex.printStackTrace();
      }

       
      
      result = new String(bytes, chrset);        
      return result;
   }   
   
   
   
   public String $read( Process process )
   throws IOException{   
      String text = "";
      String line;
      try( 
         InputStream is = process.getInputStream();
         InputStreamReader reader = new InputStreamReader( is );
         BufferedReader beffered = new BufferedReader( reader );
      ){
         while( (line=beffered.readLine())!=null ){
            text += line;
         }      
      }
      return text;
   }
  

   
   public String $exec( String cmd )
   throws IOException, InterruptedException{
      String s = cmd + " > " + OUTPUT_FILE;
      $write(  
         COMMAND_FILE ,
         cmd + " > " + OUTPUT_FILE
      );
      Process process = Runtime.getRuntime().exec( 
         USER_DIR+COMMAND_FILE );
      //System.out.println( process.waitFor() );
//      if( process.exitValue()!=0 ){
//         process.waitFor();
//         throw new RuntimeException( "Process has been terminated. " );    
//      }
      process.waitFor();
      process.destroy();
      s += $read( process );
      s += $readIMB( OUTPUT_FILE );
      return s;
   }
   
  
   public String $read(){
       String line = "";
       try{
         int ch = -1;
         while( (ch = System.in.read()) != 10 ){
           char c = (char)ch;
           //System.out.print( c );
           line += c;
         }
       }catch( IOException ex ){
         ex.printStackTrace();
       }    
       return line;
   }
  
   
   
  
  
   public static void main( String[] args ){

      System.out.println( "\n\n" + "enter command:" );
      SystemConsole console = new SystemConsole();

      String line;
      //if( (line=console.$read())!="\n" ){
         try{
            System.out.println(  
               console.$exec( "DIR" )
            );
            System.out.println(  
               console.$exec( "HELP" )
            );
         }catch( Exception ex ){
            Logger.getLogger( SystemConsole.class.getName() ).log( Level.SEVERE, null, ex );
         } 
      //}

   }
  
}
