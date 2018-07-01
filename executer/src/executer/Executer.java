/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package executer;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author admin
 */
public class Executer {

   /**
    * @param args the command line arguments
    */
   public static void main(String[] args) {
      SystemConsole console = new SystemConsole();
      String cmd ="";
      for( String arg: args ){
         cmd += arg+" ";
      }
      if( args.length > 0 ){
         try{
            System.out.println(
               console.$exec(  cmd.substring( 0,cmd.length()-1 ) )
            );
         }catch( InterruptedException ex ){
            Logger.getLogger(Executer.class.getName()).log(Level.SEVERE, null, ex);
         }catch( IOException ex ){
            Logger.getLogger(Executer.class.getName()).log(Level.SEVERE, null, ex);
         } 
         
      }
   }
   
}
