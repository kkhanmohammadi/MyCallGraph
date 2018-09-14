package dk.brics.soot.callgraphs;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import soot.MethodOrMethodContext;
import soot.PackManager;
import soot.Scene;
import soot.SceneTransformer;
import soot.SootClass;
import soot.SootMethod;
import soot.Transform;
import soot.jimple.toolkits.callgraph.CHATransformer;
import soot.jimple.toolkits.callgraph.CallGraph;
import soot.jimple.toolkits.callgraph.Targets;


public class CallGraphExample
{	
	static List apks= new ArrayList<String>();
	public static void main(String[] args) {
	 /*  List<String> argsList = new ArrayList<String>(Arrays.asList(args));
	   argsList.addAll(Arrays.asList(new String[]{
			   "-w",
			   "-pp",
			   "-cp",
			   "C:/Users/Korosh/workspace1/MyCallGraph/bin/",
			   "-main-class",
			   "testers.CallGraphs",//main-class
			   "testers.CallGraphs",//argument classes
			   "testers.A"			//
	   }));
	

	   PackManager.v().getPack("wjtp").add(new Transform("wjtp.myTrans", new SceneTransformer() {

		@Override
		protected void internalTransform(String phaseName, Map options) {
		       //CHATransformer.v().transform();
                       SootClass a = Scene.v().getSootClass("testers.A");

		       SootMethod src = Scene.v().getMainClass().getMethodByName("doStuff");
		       CallGraph cg = Scene.v().getCallGraph();

		       Iterator<MethodOrMethodContext> targets = new Targets(cg.edgesOutOf(src));
		       while (targets.hasNext()) {
		           SootMethod tgt = (SootMethod)targets.next();
		           System.out.println(src + " may call " + tgt);
		       }
		}
		   
	   }));

           args = argsList.toArray(new String[0]);
           
           soot.Main.main(args);*/

		
		//ReadExcel();
		

		
		String sourceURL="https://f-droid.org/repo/subreddit.android.appstore_6000.apk";
		String targetDirectory="C:/Ava/android/Dataset/F-Droid/";
		ReadXML();
		Iterator<String> apkname = apks.iterator();
		while (apkname.hasNext()) {
			sourceURL="https://f-droid.org/repo/"+apkname.next();
		try{
			
		download(sourceURL,targetDirectory);
		}
		catch(IOException e){
			System.out.println("IO Exception"+e.getMessage());	
			}
		}
		
	}
	
	@SuppressWarnings("unused")
	private static void download(String sourceURL, String targetDirectory) throws IOException
	{
	    URL url = new URL(sourceURL);
	    String fileName = sourceURL.substring(sourceURL.lastIndexOf('/') + 1, sourceURL.length());
	    Path targetPath = new File(targetDirectory + File.separator + fileName).toPath();
	    Files.copy(url.openStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

	    
	}
	
	static void ReadXML(){
	    try {

	    	File fXmlFile = new File("C:/Ava/android/Dataset/F-Droid/index.xml");
	    	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	    	DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	    	Document doc = dBuilder.parse(fXmlFile);

	    	doc.getDocumentElement().normalize();

	    	System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

	    	NodeList nList = doc.getElementsByTagName("application");

	    	System.out.println("----------------------------"+nList.getLength());

	    	for (int temp = 0; temp <10; temp++) {

	    		Node nNode = nList.item(temp);

	    		//System.out.println("\nCurrent Element :" + nNode.getNodeName());

	    		if (nNode.getNodeType() == Node.ELEMENT_NODE) {

	    			Element eElement = (Element) nNode;

	    			//System.out.println("application id : " + eElement.getAttribute("id"));
	    			//System.out.println("source Name : " + eElement.getElementsByTagName("source").item(0).getTextContent());
	    			//System.out.println("package Name : " + eElement.getElementsByTagName("package").item(0).getTextContent());
	    			NodeList pList=eElement.getElementsByTagName("package");
	    			Node package0=pList.item(0);
	    			Element pElement = (Element) package0;
	    			System.out.println("apk name:"+ pElement.getElementsByTagName("apkname").item(0).getTextContent());
	    			apks.add(pElement.getElementsByTagName("apkname").item(0).getTextContent());

	    		}
	    	}
	        } catch (Exception e) {
	    	e.printStackTrace();
	        }
	}
	
	static void ReadExcel(){
		String inputFile="C:/Ava/android/Dataset/androidNormalApps/NormalAppData.xls";
        File inputWorkbook = new File(inputFile);
        Workbook w;
        try {
                w = Workbook.getWorkbook(inputWorkbook);
                // Get the first sheet
                Sheet sheet = w.getSheet(0);
                // Loop over first 10 column and lines

                for (int i = 0; i < 1;i++){//sheet.getRows(); i++) {
                	Cell cell1 = sheet.getCell(0, i);
                	Cell cell2 = sheet.getCell(1, i);
                	//System.out.println(cell1.getContents()+"*"+ cell2.getContents());
                	String s= "'com.mobisystems.office.GoPremium.SamsungCheckPurchasedService', 'com.sonymobile.smartwear.swr30.ExtensionService', 'com.sonymobile.smartconnect.extension.officesuite.Sw2ExtensionService', 'com.mobisystems.wifi_direct.FileSenderService', 'com.mobisystems.wifi_direct.FileReceiverService', 'com.mobisystems.libfilemng.search.EnumerateFilesService', 'com.mobisystems.cache.DailyPruneService', 'com.mobisystems.libfilemng.copypaste.ModalTaskServiceImpl', 'com.mobisystems.office.pdf.fonts.PdfFontsDownloadService', 'com.mobisystems.services.FileDownloadService', 'com.mobisystems.pdfconverter.PDFConverterService', 'com.mobisystems.office.recentFiles.widget.Service', 'com.mobisystems.office.recentFiles.RecentFilesService', 'com.mobisystems.office.powerpoint.slideshowshare.SlideShowShareService', 'com.mobisystems.mediastore.MediaStoreUpdater', 'com.mobisystems.office.excel.DrawChartService', 'com.mobisystems.office.excel.DrawXlsChartService', 'com.google.analytics.tracking.android.CampaignTrackingService', 'com.mobisystems.pdf.yotaphone.PdfBSActivity'";//cell2.getContents();
                	//System.out.println(s+"**");

                	List<String> services = new ArrayList<String>();
                	//System.out.println(s.indexOf(","));
                	//System.out.println("*"+s.indexOf(",", 5));
                	while(s.indexOf(",")!=-1){
                		String ser=s.substring(1,s.indexOf(",")-1);//(2 in excel
                		//System.out.println(ser+"*");
                		s=s.substring(s.indexOf(",")+2);//(1 in excel

                		//newline=line.substring(1,line.indexOf(":"))+"."+line.substring(line.lastIndexOf(" ")+1,line.indexOf("("));
                		services.add(ser);
                		//if(s.indexOf(",")==-1)
                		//break;
                	}
                	services.add(s.substring(1,s.length()-1));//(2 in excel
                	Iterator <String> sser=services.iterator();
                	while(sser.hasNext()){
                		String postDominatorOutputFile=cell1.getContents()+ "-"+sser.next()+"-S.txt";
                		System.out.println(postDominatorOutputFile+"**");
                	}
                	String temp= cell1.getContents()+".apk";

                	System.out.println("*******************"+temp);


                }
        } catch (BiffException e) {
                e.printStackTrace();
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

