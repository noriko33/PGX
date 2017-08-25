import java.io.*; 
import java.text.*; 
import java.util.*; 
import java.util.regex.*; 
public class ExtractAddress{ 
   private static final String[] IGNORE_ADDR_LIST = { "noriko.takada@oracle.com"} ; 
   private static final String FILE_NAME = "index.csv" ; 
   private static final String SPLITTER = ",| "; 
   private static final Pattern SPLITTER_PATTERN   =  Pattern.compile(SPLITTER)  ; 
   private static final String MAIL_REGEX  =  ".*@oracle.com" ; 
   private static final Pattern MAIL_PATTERN   =  Pattern.compile(MAIL_REGEX)  ; 

   public static void main(String[] args){ 
     try{ 
         String fileName = FILE_NAME ; 
        if ( args != null ) { 
           if( args.length == 1 ) { 
             fileName = args[0]; 
           } 
        } 
        File  file = new File(fileName); 
        FileReader fr = new FileReader(file); 
        BufferedReader br = new BufferedReader(fr); 
        String str ; 
        String[] sa ; 
        Matcher m ; 
        HashSet<String> addrs; 
        boolean ignoreFlag; 
        String addr = "" ; 
        ArrayList<String> links ; 

        while ( ( str = br.readLine() ) != null){ 
            addrs  = new HashSet<String>(); 
            sa = SPLITTER_PATTERN.split(str); 
            // System.out.println("[debug]:[source string]:" + str ); 
            for(String st: sa ){ 
              m = MAIL_PATTERN.matcher(st) ; 
              if( m.find() ){ 
                  for(int i=0; i < ( m.groupCount()+1) ; i++) { 
                      ignoreFlag = false ; 
                      // System.out.println(" [" + i + "]:"  + m.group(i) ); 
                     for(String ignore : IGNORE_ADDR_LIST ){ 
                        addr = m.group(i).replace("<","").replace("\"","").toLowerCase() ; 
                        if( ignore.equals(addr)) ignoreFlag = true; 
                     } 
                     if(!ignoreFlag ) addrs.add(addr); 
                  } 
              } 
           } 
           // if(addrs.size() >= 1) for( String a : addrs ) System.out.println("[debug]:Extracted:"  + a  ); 
           links = combination(new ArrayList<String>(addrs)); 
           for ( String link : links ) System.out.println(link ); 
        } 
     }catch(Exception e){ 
         e.printStackTrace(); 
     } 

   } 
   //  generate combination set 
   private static ArrayList<String> combination(ArrayList<String> hs ){ 
        ArrayList<String> ret = new ArrayList<String>(); 
        int counter = hs.size()-1; 
        String s1 = ""; 
        int indexCounter = 0; 
        while( hs.size() >= 2 ) { 
          s1 = hs.get(0); 
          hs.remove(0); 
          for( String str : hs ){ 
            ret.add(s1 + "," +str ) ; 
          } 
          indexCounter ++; 
        } 
        return ret; 
   } 
}
