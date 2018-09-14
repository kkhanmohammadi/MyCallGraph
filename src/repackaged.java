import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class repackaged {
	final static String folder_name="C:/Ava/android/Dataset/AndrozooRepackaged";
	private static final int BUFFER_SIZE = 4096;
    private static final String API_Key= "a222a1ac3914ff140307ad1e3b2189843e721c19920233a359d3cedad70d7ba4";
    private static int LINE_NO = 0;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		downloadApps();
		//completeData();//3563 apps are read //3563*85715CD54BECC21EE91D95B341B41D7308749E80551A1A3F653AF1173FEB54F6,42e8211c4b7780dde1d74fcbb25da76148a14c8e,ec5bebaf344da41046e0cc073d3886f3,2014-01-13 13:24:32,25720741,"com.teamil.bankheistescape",1,8,2014-05-14 19:57:23,2145900,play.google.com
//15297*FFDD408901CC0AE02A946718F02585438D86371E391FE0A7054F898D99D9BCF1,f2b4ccc0c748397adda32bbec6987a11e4628f72,2073fc8e6e133c863b965abd73535799,2014-07-07 13:44:14,3520930,"com.chinda_property.ecmobile",20140707,0,2014-07-16 05:38:35,2175848,anzhi

		/*
		String ofile="C:/Ava/android/Dataset/theZoo/originalAppsDetails.csv";
		BufferedReader br=null;
		try{
			br = new BufferedReader(new FileReader(ofile));
			String line=br.readLine();//read header
			while(line!=null){
				System.out.println(line);
				System.out.println("*");

				line=br.readLine();//read header

			}
		}catch(Exception e){
			
			
		}*/



	}
	public static void completeData(){
		String pairsFile= "C:/Ava/android/Dataset/AndrozooRepackaged/repackaging_pairs.txt";
		String latestFile="C:/Ava/android/Dataset/AndrozooRepackaged/latest.csv";
		String ofile="C:/Ava/android/Dataset/AndrozooRepackaged/originalAppsDetails.csv";
		String rfile="C:/Ava/android/Dataset/AndrozooRepackaged/repackagedAppsDetails.csv";
		BufferedReader br=null;
		BufferedReader lbr=null;
		BufferedWriter obw=null;
		BufferedWriter rbw=null;
		String line="";
		String csvSplitBy=",";
		try{
			br = new BufferedReader(new FileReader(pairsFile));
			lbr = new BufferedReader(new FileReader(latestFile));
			obw= new BufferedWriter(new FileWriter(ofile));
			rbw= new BufferedWriter(new FileWriter(rfile));
	
			line=br.readLine();//read header
			//line=br.readLine();// first line
			for(int i=0;i<3576;i++){ //3563
				line=br.readLine();
				LINE_NO++;
			}
			line=br.readLine();// first line
			do{//the number should be changed???????????????????
				
				LINE_NO++;
				String[] pair=line.split(csvSplitBy);
				String originalAppSHA = pair[0];
               	String repackagedAppSHA = pair[1];//sha256
               	//System.out.println(originalAppSHA);
               //	System.out.println("*"+repackagedAppSHA);

               	//find apps in latest and copy that lines to pairesdetails.csv
               	//findInLatest(originalAppSHA,repackagedAppSHA, ofile, rfile);
               	//findInLatest(repackagedAppSHA, rfile);
               	String latestLine=lbr.readLine();
               	boolean oFound=false;
               	boolean rFound=false;
               	while(latestLine!=null){
               		latestLine=lbr.readLine();
    				//LINE_NO++;

                   	//System.out.println(latestLine);

               		if(!oFound && latestLine!=null && latestLine.contains(originalAppSHA)){
                       	System.out.println(	LINE_NO+"*" + latestLine);
                       	
               			obw.append(latestLine);
               			obw.newLine();
               			obw.flush();
               			oFound=true;
               		}
               		if(!rFound && latestLine!=null && latestLine.contains(repackagedAppSHA)){
                       	System.out.println(	LINE_NO+"*" +latestLine);
               			rbw.append(latestLine);
               			obw.newLine();
               			rbw.flush();
               			rFound=true;
               		}
               		if(rFound && oFound)
               			break;
               	}
               		lbr.close();
               		lbr = new BufferedReader(new FileReader(latestFile));
               		line=br.readLine();
               	}while(line!=null);
			obw.close();
			rbw.close();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}


	}

	public static void downloadApps(){
		String filename= "C:/Ava/android/Dataset/AndrozooRepackaged/repackaging_pairs.txt";
		String outputfile="C:/Ava/android/Dataset/AndrozooRepackaged/latest.csv";
		//System.out.println(args[0]+" ");
		//int r=Integer.parseInt(args[0]);
		//readLine(filename,r);
		BufferedReader br=null;
		BufferedWriter bw=null;
		String line="";
		String csvSplitBy=",";
		int filenumbers= 15297*2;
		try{
			br = new BufferedReader(new FileReader(filename));
			bw= new BufferedWriter(new FileWriter(outputfile));
			for(int i=0;i<14875;i++){//File downloaded.Line number=14875//3576//2438//the number should be changed???????????????????
				line=br.readLine();//read header
				LINE_NO++;
			}
			for(int i=14875;i<filenumbers;i++){//the number should be changed???????????????????
				line=br.readLine();//read header
				LINE_NO++;
				String[] pair=line.split(csvSplitBy);
				String originalAppSHA = pair[0];
               	String repackagedAppSHA = pair[1];//sha256
               	String originalAppName=i+"_"+originalAppSHA+".apk";
               	String repackagedAppName=i+"_"+repackagedAppSHA+"_r.apk";

               	//System.out.println("folder_name = " + folder_name);
               	//System.out.println("apk_name = " + apk_name);
               	
            	//https://androzoo.uni.lu/api/download?apikey=${APIKEY}&sha256=${SHA256}
               	String fileURL="https://androzoo.uni.lu/api/download?apikey="+API_Key+"&sha256="+originalAppSHA;
               	downloadFile(fileURL, folder_name, originalAppName);
               	fileURL="https://androzoo.uni.lu/api/download?apikey="+API_Key+"&sha256="+repackagedAppSHA;
               	downloadFile(fileURL, folder_name, repackagedAppName);


				}
			
			
			
		}catch(Exception e){
			System.out.println(e.getMessage());
		}

        System.out.println("done!");
		
	}
	public static void downloadFile(String fileURL, String saveDir, String fileName)
            throws IOException {
        URL url = new URL(fileURL);
        HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
        int responseCode = httpConn.getResponseCode();
 
        // always check HTTP response code first
        if (responseCode == HttpURLConnection.HTTP_OK) {
           
            String contentType = httpConn.getContentType();
            int contentLength = httpConn.getContentLength();
 

            //System.out.println("Content-Type = " + contentType);
            //System.out.println("Content-Length = " + contentLength);
            //System.out.println("fileName = " + fileName);
 
            // opens input stream from the HTTP connection
            InputStream inputStream = httpConn.getInputStream();
            String saveFilePath = saveDir + File.separator + fileName;
             
            // opens an output stream to save into file
            FileOutputStream outputStream = new FileOutputStream(saveFilePath);
 
            int bytesRead = -1;
            byte[] buffer = new byte[BUFFER_SIZE];
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
 
            outputStream.close();
            inputStream.close();
 
            System.out.println("File downloaded."+ "Line number="+ LINE_NO);
        } else {
            System.out.println("No file to download. Server replied HTTP code: " + responseCode+" in LineNumber="+ LINE_NO);
        }
        httpConn.disconnect();
    }

}
