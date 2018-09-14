package dk.brics.soot.callgraphs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


import org.xmlpull.v1.XmlPullParserException;

import soot.Body;
import soot.EntryPoints;
import soot.MethodOrMethodContext;
import soot.PackManager;
import soot.PhaseOptions;
import soot.PointsToAnalysis;
import soot.Scene;
import soot.SceneTransformer;
import soot.SootClass;
import soot.SootMethod;
import soot.Transform;
import soot.Type;
import soot.Unit;
import soot.jimple.Stmt;
import soot.jimple.infoflow.android.SetupApplication;
import soot.jimple.infoflow.android.config.SootConfigForAndroid;
import soot.jimple.spark.pag.PAG;
import soot.jimple.toolkits.callgraph.CHATransformer;
import soot.jimple.toolkits.callgraph.CallGraph;
import soot.jimple.toolkits.callgraph.CallGraphBuilder;
import soot.jimple.toolkits.callgraph.Edge;
import soot.jimple.toolkits.callgraph.Targets;
import soot.options.Options;
import soot.options.SparkOptions;
import soot.toolkits.graph.DominatorTree;
import soot.toolkits.graph.ExceptionalGraph;
import soot.toolkits.graph.ExceptionalUnitGraph;
import soot.toolkits.graph.InverseGraph;
import soot.toolkits.graph.UnitGraph;
import soot.util.queue.QueueReader;
import soot.jimple.toolkits.callgraph.ReachableMethods;
import soot.toolkits.graph.DirectedGraph;
import soot.jimple.toolkits.thread.mhp.PegCallGraphToDot;
import soot.jimple.toolkits.thread.mhp.pegcallgraph.*;
import soot.util.cfgcmd.CFGGraphType;
import soot.util.cfgcmd.CFGToDotGraph;
import soot.util.dot.DotGraph;
import soot.util.dot.DotGraphCommand;
import jqian.util.graph.*;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class CallGraphMethod
{	
	
	
	private static String dominatorGraphFile="AnServer-dg";
	private static String callGraphFile ="AnServer-callgraph";
	private static String serviceName="com.km.ad.AdTask";//com.km.charge.CycleService//"Daemon.Service.OnlineThread";//"com.android.battery.AdSmsService";//"com.keji.listenclear.MainRun";//"com.keji.sendere.service.pc";//"com.keji.sendere.service.MainRun";//"com.android.battery.d.am";//"com.android.battery.AdSmsService";//"com.mms.bg.ui.BgService";//"com.GoldDream.zj.zjService";//"com.mms.bg.ui.BgService";//"com.javacodegeeks.android.androidserviceexample.MyService";//
	private static String apkName="";//"C:/Ava/android/Dataset/Gnome-malwaredataset/test/KMin/02122225a6e08c6f33cfcf39563b3df68cc20926.apk";//"C:/Ava/android/Dataset/androidNormalApps/BOOKREFRENCE/com.amazon.kindle.apk";//"C:/Ava/android/Dataset/Gnome-malwaredataset/test/GoldDream/1f7c6ea11b2b5ca857fee045207ccd34f9c7b7b6.apk";//15e4513fe0edfb14db663eca227f88516cd47c8f.apk";//02a11b75507fd1b6f612aebbbfc6d203f68f2cc3.apk";//Bgserv/original/113d68bbaa7ab1f0abd9f9a5b772f03ea6721e14.apk";//GoldDream/1f7c6ea11b2b5ca857fee045207ccd34f9c7b7b6.apk";//Bgserv/original/113d68bbaa7ab1f0abd9f9a5b772f03ea6721e14.apk";//"C:/Ava/android/adt-bundle-windows-x86_64-20140702/AndroidServiceExample.apk";//
	private static String postDominatorOutputFile="";//C:/Ava/android/Dataset/Gnome-malwaredataset/test/KMin/02122225a6e08c6f33cfcf39563b3df68cc20926-adtask-ST";//"C:/Ava/android/Dataset/androidNormalApps/BOOKREFRENCE/com.amazon.kindle-com.amazon.kcp.reader.ui.ThumbnailService-ST.txt";//"C:/Ava/android/Dataset/Gnome-malwaredataset/test/Traces/GoldDream-1f7c6ea11b2b5ca857fee045207ccd34f9c7b7b6-Edge-zjService.txt";//bgServ-113d68bbaa7ab1f0abd9f9a5b772f03ea6721e14.txt";//GoldDream-1f7c6ea11b2b5ca857fee045207ccd34f9c7b7b-pd.txt";//Bgserv/original/113d68bbaa7ab1f0abd9f9a5b772f03ea6721e14-pdo.txt";;
	static List<String> services;
	static List<SootMethod> allSootMethodList;
	@SuppressWarnings("null")

	public static void main(String[] args){
		services = new ArrayList<String>();
		
		
		//readLineCSV("C:/Ava/android/Dataset/theZoo/SRList-micom2.csv", 1);

		
		
		OutputStream outputStream = null;
		try {
			//outputStream = new FileOutputStream(new File("C:/Ava/F-Droid/ErrorFile.txt"), true);
			outputStream = new FileOutputStream(new File("C:/Ava/android/Dataset/theZoo/ErrorFile.txt"), true);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}//"c:\\Ava\\CallGraphOutput.txt");
		PrintStream  errorwriter = new PrintStream(outputStream);
		
		
		
		
		
		//readLineCSV("C:/Ava/android/Dataset/theZoo/SRList-micom2.csv", 1);
		
		
		String[] inputFiles={"SRList-anzhi14"};//SRList-anzhi18SRList-anzhi17,SRList-anzhi16,SRList-anzhi15 for 2015,SRList-anzhi13 SRList-anzhi12 SRList-anzhi11anzhi10 SRList-anzhi9, SRList-anzhi8, SRList-anzhi7 for 2014-SRList-SRList-anzhi6 anzhi5 SRList-anzhi4, SRList-anzhi3,anzhi2 SRList-anzhi1 for 2013--SRList-anzhi0
		int[]infn={500};
		int inn=0;
		//readLineCSV("C:/Ava/android/Dataset/theZoo/SRList-micom2.csv", 1);
		while(inn<500 ){

			//String inputFile="C:/Ava/F-Droid/"+in.next()+".xls";
			String inputFile="C:/Ava/android/Dataset/theZoo/"+inputFiles[inn]+".csv";
			
			int rowNo=infn[inn];//ReadExcelRows(inputFile);
			inn++;
			System.out.println(inputFile+" rownum: "+rowNo);
			

			for(int i=300;i<rowNo;i++){ //read from line i
				//ReadExcel(inputFile, i);
				readLineCSV(inputFile, i);
				
				System.out.println(" rownum: "+i+apkName);
				if(!services.isEmpty()){
					Iterator <String> sser=services.iterator();
					while(sser.hasNext()){
						String postDominatorOutputFile=apkName+ "-"+sser.next()+"-S.txt";

						System.out.println("postDominatorOutputFile:"+postDominatorOutputFile);
						//System.out.println("ser"+services.iterator().next());

						extractAPI(apkName,services,errorwriter);
					}
				}
				services.clear();
			}
		}  

	}
	
	static void extractAPI(String apkName,List<String> services, PrintStream  errorwriter){

		try{

		SetupApplication app = new SetupApplication
				("C:/Ava/android/Android-jar",apkName);//"C:/Ava/android/ADT/adt-bundle-windows-x86_64-20140702/sdk/platforms",
		//app.setSootConfig(new SetConfigForInstrument());
		try {
			System.out.println("source and sink");
			app.calculateSourcesSinksEntrypoints("C:/Ava/android/soot6june2016/SourcesAndSinks.txt");
			System.out.println("source and sink1");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("source and sink io exception");
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			System.out.println("source and sink xml exception");
			e.printStackTrace();
		}
		soot.G.reset();

		Options.v().set_src_prec(Options.src_prec_apk);
		Options.v().set_process_dir(Collections.singletonList(apkName));//"C:/Ava/android/adt-bundle-windows-x86_64-20140702/AndroidServiceExample.apk"));//"C:/Users/Korosh/workspace1/DroidRA/workspace_boosted_apps/AndroidServiceExample1.apk"));//"C:/Ava/android/adt-bundle-windows-x86_64-20140702/AndroidServiceExample.apk"));//"C:/Ava/android/Dataset/Gnome-malwaredataset/test/Bgserv/original/bc2dedad0507a916604f86167a9fa306939e2080.apk"));//"C:/Ava/android/Dataset/Gnome-malwaredataset/test/GoldDream/original/11b0f2a63d0deafd4602be297ae37ff921aeada9.apk"));//"C:/Ava/android/sample/AndroidServiceExample/AndroidServiceExample.apk"));//"C:/Ava/android/Dataset/Gnome-malwaredataset/test/GoldDream/original/11b0f2a63d0deafd4602be297ae37ff921aeada9.apk"));
		Options.v().set_android_jars("C:/Ava/android/Android-jar1/android-platforms-master");//"C:/Ava/android/ADT/adt-bundle-windows-x86_64-20140702/sdk/platforms");
		Options.v().set_whole_program(true);
		Options.v().set_allow_phantom_refs(true);
		Options.v().set_output_format(Options.output_format_class);
		//Options.v().set_no_bodies_for_excluded(true);///
		Options.v().setPhaseOption("cg.spark", "on");//"cg.cha", "on");//cg.spark", "on");
		
		Options.v().setPhaseOption("verbose", "enabled");
		Options.v().setPhaseOption("safe-forname", "true");
		
		
		
		new SootConfigForAndroid().setSootOptions(Options.v());
		 //app.setSootConfig(new SetConfigForInstrument().);
		
		Scene.v().loadBasicClasses();
		Scene.v().loadNecessaryClasses();
		System.out.println("create dummy main");

		SootMethod entryPoint = app.getEntryPointCreator().createDummyMain();
		entryPoint.getActiveBody().validate();
		Options.v().set_main_class(entryPoint.getSignature());
		
		Scene.v().setEntryPoints(Collections.singletonList(entryPoint));
		//System.out.println("EntryPoint:"+entryPoint.getActiveBody()+"******");

		PackManager.v().runPacks();
		//System.out.println("Graph Size: "+Scene.v().getCallGraph().size());

       	Iterator <String> sser=services.iterator();
       	while(sser.hasNext()){
       		serviceName=sser.next();
       		postDominatorOutputFile=apkName+ "-"+serviceName+"-STM2.txt";
       		//System.out.println(postDominatorOutputFile);

       		SootClass a = Scene.v().getSootClass(serviceName);//"com.mms.bg.ui.BgService");//"com.javacodegeeks.android.androidserviceexample.MyService");//"com.mms.bg.ui.BgService");//"com.GoldDream.zj.zjService$1");//"com.GoldDream.zj.zjService");//"com.GoldDream.zj.zjService$1");//"com.GoldDream.zj.zjService");

       		List<SootMethod> test = new ArrayList<SootMethod>();
       		test=a.getMethods();
       		if(!test.isEmpty()){
       			System.out.println("------------------------------"+serviceName);
       			Iterator <SootMethod> tm=test.iterator();
       			List<SootMethod> threadSootMethodList = new ArrayList<SootMethod>();
       			while(tm.hasNext()){
       			//System.out.println("methodsssssss"+tm.next());
       				threadSootMethodList.add(tm.next());
       			}

       			allSootMethodList = new ArrayList<SootMethod>();

       			nextMethodDominators(threadSootMethodList);
       		
       			allSootMethodList.clear();
       		//List<SootMethod> methodList = new ArrayList<SootMethod>();
       		//List<SootMethod> nextMethodList = new ArrayList<SootMethod>();
       		
       		}
       	}
		}
		catch(OutOfMemoryError e){
			errorwriter.println("OutofMemoryError in "+ apkName+" "+e.getMessage());
		}
		catch(RuntimeException e){
			errorwriter.println("Runtime Exception in "+ apkName+" "+e.getMessage());

		}
		catch(Exception e){
			errorwriter.println("Exception in "+ apkName+" "+e.getMessage());
			
		}finally{
			errorwriter.println("Exception in "+ apkName);
			errorwriter.flush();

		}

	}
	static int ReadExcelRows(String inputFile){
		File inputWorkbook = new File(inputFile);
        Workbook w=null;
        int row=1;
        try {
        	w = Workbook.getWorkbook(inputWorkbook);
        	// Get the first sheet
        	Sheet sheet = w.getSheet(0);
        	/*
        	boolean end=false;
        	
        	while(!end){
        		try{
        		Cell c = sheet.getCell(0, row);
        		if (c == null || c.getContents() =="") {
        			// This cell is empty
        			end=true;
        		}
        		row++;
        		}catch(java.lang.ArrayIndexOutOfBoundsException e){
        			end=true;
        			System.out.println(e.getMessage());
        		}
        	}*/
        	row=sheet.getRows();
        	
        	//inputWorkbook.deleteOnExit();
        	

        } catch (BiffException e) {
        	e.printStackTrace();
        } catch (IOException e) {
        	// TODO Auto-generated catch block
        	e.printStackTrace();
        }
        finally{
        	if (w != null) {
                w.close();
                
            }
        }
        return row;
	}
	static void ReadExcel(String inputFile, int row){
		//String inputFile="C:/Ava/android/Dataset/androidNormalApps/NormalAppData.xls";
        File inputWorkbook = new File(inputFile);
        Workbook w=null;
        try {
                w = Workbook.getWorkbook(inputWorkbook.getAbsoluteFile());
                // Get the first sheet
                Sheet sheet = w.getSheet(0);
               	Cell cell1 = sheet.getCell(0, row);
               	Cell cell2 = sheet.getCell(1, row);
                	//System.out.println(cell1.getContents()+"*"+ cell2.getContents());
                String s= cell2.getContents();//"'com.mobisystems.office.GoPremium.SamsungCheckPurchasedService', 'com.sonymobile.smartwear.swr30.ExtensionService', 'com.sonymobile.smartconnect.extension.officesuite.Sw2ExtensionService', 'com.mobisystems.wifi_direct.FileSenderService', 'com.mobisystems.wifi_direct.FileReceiverService', 'com.mobisystems.libfilemng.search.EnumerateFilesService', 'com.mobisystems.cache.DailyPruneService', 'com.mobisystems.libfilemng.copypaste.ModalTaskServiceImpl', 'com.mobisystems.office.pdf.fonts.PdfFontsDownloadService', 'com.mobisystems.services.FileDownloadService', 'com.mobisystems.pdfconverter.PDFConverterService', 'com.mobisystems.office.recentFiles.widget.Service', 'com.mobisystems.office.recentFiles.RecentFilesService', 'com.mobisystems.office.powerpoint.slideshowshare.SlideShowShareService', 'com.mobisystems.mediastore.MediaStoreUpdater', 'com.mobisystems.office.excel.DrawChartService', 'com.mobisystems.office.excel.DrawXlsChartService', 'com.google.analytics.tracking.android.CampaignTrackingService', 'com.mobisystems.pdf.yotaphone.PdfBSActivity'";//cell2.getContents();
            	String temp= cell1.getContents();
            	apkName=temp;
              	System.out.println(apkName);
                System.out.println("services:"+s+"**");
                //System.out.println("*"+s.indexOf(",", 5));
                while(s.indexOf(",")!=-1){
                	String ser=s.substring(2,s.indexOf(",")-1);//(2 in excel
                	System.out.println("*"+ser+"*");
                	s=s.substring(s.indexOf(",")+1);//(1 in excel
               		services.add(ser);

                	}
                	//services.add(s.substring(1,s.length()-1));//(2 in excel

        } catch (BiffException e) {
                e.printStackTrace();
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        finally{
        	if (w != null) {
                w.close();
                
            }}
	}
	static void readLineCSV(String csvFile, int row){
		BufferedReader br=null;
		String line="";
		//It needs to change delimator to ; in control Panel>Region and Language>Additional Settings>List separator 
		String csvSplitBy=";";
		try{
			br = new BufferedReader(new FileReader(csvFile));
			line=br.readLine();//read header
			//LINE_NO++;
			for(int f=1;f<row;f++){
				line=br.readLine();
			}
			//LINE_NO=row;
			//for(int i=row; i<1048576; i++){//
				line=br.readLine();//!=null
				//LINE_NO++;
				String[] apk=line.split(csvSplitBy);
				//System.out.println(apk[0]+"**** "+apk[1]+"* ");
               
            	apkName=apk[0];
            	String s=apk[1];
              	System.out.println(apkName);
                //System.out.println("services:"+s+"**");
                //System.out.println("*"+s.indexOf(",", 5));
                while(s.indexOf(",")!=-1){
                	String ser=s.substring(2,s.indexOf(",")-1);//(2 in excel
                	System.out.println("*"+ser+"*");
                	s=s.substring(s.indexOf(",")+1);//(1 in excel
               		services.add(ser);

                	}
			
			
		}catch(FileNotFoundException e){
			e.printStackTrace();
			System.out.println("FileNotFoundException line number="+ row);
		}catch(IOException e){
			e.printStackTrace();
			System.out.println("IOException line number="+ row);
		}finally{
			System.out.println("Finally line number="+ row);
			if(br!=null){
				try{
					br.close();
				}catch(IOException e){
					e.printStackTrace();
				}
			}
		}
		
	}
	static void ReadServicesfromFile(){
		apkName="";
				String s="";
     	while(s.indexOf(",")!=-1){
            String ser=s.substring(1,s.indexOf(",")-1);
            s=s.substring(s.indexOf(",")+2);
             services.add(ser);
         	}
        services.add(s.substring(1,s.length()-1));
	};

	static void ReadServices(){
		apkName="C:/Ava/android/Dataset/androidNormalApps/HealthFitness/com.fitbit.FitbitMobile.apk";//com.excelatlife.worrybox.apk";
	//com.td.apk";//com.scotiabank.mobile.apk";//com.rbc.mobile.android.apk";//com.paypal.android.p2pmobile.apk";//com.moneybookers.skrillpayments.apk";//com.maximya.oilprice.apk";//com.cibc.android.mobi.apk";//ca.bnc.android.apk";
				//"C:/Ava/android/Dataset/androidNormalApps/Entertainment/com.gotv.crackle.handset.apk";//com.netflix.mediaclient.apk";//com.panasonic.pavc.viera.vieraremote2.apk";//com.zentertain.freemusic.apk";//com.fivemobile.cineplex.apk";//com.digidust.elokence.akinator.paid.apk";//be.intotheweb.cyprien.apk";
				//"C:/Ava/android/Dataset/androidNormalApps/Communication/com.hushed.release.apk";//com.facebook.orca.apk";//com.cloudmosa.puffin.apk";//com.apusapps.browser.apk";
		//com.marvel.unlimited.apk";//com.marvel.comics.apk";//com.iconology.comics.apk";	//"C:/Ava/android/Dataset/androidNormalApps/COMICS/com.dccomics.comics.apk";//"C:/Ava/android/Dataset/androidNormalApps/COMICS/com.cyo.comicrack.viewer.free.apk";
     	String s="'com.fitbit.runtrack.ExerciseLocationService', 'com.fitbit.runtrack.SpeechService', 'com.fitbit.FitbitMobile.GCMIntentService', 'com.fitbit.data.bl.SyncService', 'com.fitbit.galileo.service.GalileoSyncService_', 'com.fitbit.pedometer.service.PedometerService_', 'com.fitbit.widget.WidgetUpdaterService_', 'com.fitbit.pedometer.google.GoogleStepsService', 'com.fitbit.bluetooth.connection.BluetoothConnectionService', 'com.fitbit.dncs.service.DncsSendNotificationService', 'com.fitbit.dncs.service.DncsPairingService', 'com.htc.blinkfeed.service.BlinkFeedPluginService', 'com.fitbit.synclair.ForegroundService', 'com.google.android.gms.analytics.CampaignTrackingService'";//"'com.excelatlife.generallibrary.MediaService', 'com.excelatlife.generallibrary.ScheduleService', 'com.excelatlife.generallibrary.NotifyService'";
     	//"'com.td.mobile.wearable.TDMessageService', 'com.enstream.framework.EnGCMMainService', 'com.enstream.framework.EnGCMIntentService', 'com.td.mobilewallet.services.TDOffHostApduService'";//'mobi.scotiabank.scotiabankgearprovider.service.GearCommunicationService', 'mobi.scotiabank.scotiabankwear.service.WearRequestService', 'mobi.scotiabank.scotiabankpebble.service.PebbleSenderService', 'com.enstream.framework.EnGCMIntentService', 'com.enstream.framework.EnGCMMainService'";//'com.enstream.framework.EnGCMMainService', 'com.enstream.framework.EnGCMIntentService', 'com.rbc.mobile3a.android.alerts.GCMIntentService'";//'com.paypal.android.p2pmobile.core.controllers.ViewKitServiceFramework', 'com.paypal.android.p2pmobile.services.AtoService', 'com.paypal.android.p2pmobile.gcm.GcmIntentService', 'com.paypal.android.lib.logmonitor.wallet.LogMonitoringService', 'com.paypal.android.p2pmobile.services.LoyaltyCardService', 'com.paypal.android.p2pmobile.services.WalletService', 'com.paypal.android.lib.authenticator.AuthenticationService'";//'com.openx.ad.mobile.sdk.OpenUDID_service', 'com.xtify.sdk.location.LocationUpdateService', 'com.xtify.sdk.c2dm.C2DMIntentService', 'com.xtify.sdk.alarm.MetricsIntentService', 'com.xtify.sdk.alarm.TagIntentService', 'com.xtify.sdk.alarm.RegistrationIntentService', 'com.xtify.sdk.alarm.LocationIntentService'";//'com.maximya.oilprice.OilPriceUpdateService'";//'ca.bnc.android.receivers.AlarmReceiver'";
     			//"'com.sessionm.sdk.SessionMService', 'com.medialets.advertising.AdManagerService', 'com.google.sample.castcompanionlibrary.notification.VideoCastNotificationService'";//"'com.netflix.mediaclient.GCMIntentService', 'com.netflix.mediaclient.service.NetflixService'";//"'com.panasonic.pavc.viera.service.DlnaService'";//"'com.zentertain.freemusic.player.PlaybackService'";//"'com.digidust.elokence.akinator.services.BackgroundMusicService', 'com.digidust.elokence.akinator.services.BackgroundSoundsService', 'com.digidust.elokence.akinator.services.GCMIntentService'";//"'com.parse.PushService'";
     			//"'com.hushed.base.services.DataService', 'com.twilio.client.TwilioClientService', 'com.hushed.base.services.NotificationService', 'com.google.analytics.tracking.android.CampaignTrackingService'";//"'com.facebook.rtc.fbwebrtc.WebrtcIncallNotificationService', 'com.facebook.common.errorreporting.memory.MemoryDumpUploadService', 'com.facebook.orca.prefs.notifications.NotificationPrefsSyncService', 'com.facebook.orca.chatheads.service.ChatHeadService', 'com.facebook.contacts.service.ContactLocaleChangeService', 'com.facebook.orca.chatheads.service.ChatHeadsBooterService', 'com.facebook.orca.notify.MessagesNotificationService', 'com.facebook.analytics.service.AnalyticsService', 'com.facebook.analytics2.logger.LollipopUploadService', 'com.facebook.analytics2.logger.PreLollipopUploadService', 'com.facebook.conditionalworker.ConditionalWorkerService', 'com.facebook.delayedworker.DelayedWorkerService', 'com.facebook.fbservice.service.DefaultBlueService', 'com.facebook.graphql.executor.GraphQLMutationService', 'com.facebook.nodex.startup.crashloop.FixCrashLoopService', 'com.facebook.push.fbpushdata.FbPushDataHandlerService', 'com.facebook.push.adm.ADMRegistrarService', 'com.facebook.push.adm.ADMService', 'com.facebook.push.adm.ADMBroadcastReceiver', 'com.facebook.push.nna.NNAService', 'com.facebook.push.c2dm.C2DMService', 'com.facebook.push.registration.RegistrarHelperService', 'com.facebook.push.fbnslite.FbnsLitePushNotificationHandler', 'com.facebook.push.crossapp.PackageRemoveReceiverInManifestService', 'com.facebook.push.mqtt.service.MqttPushService', 'com.facebook.push.mqtt.service.MqttPushServiceInSeperateProcess', 'com.facebook.push.mqtt.receiver.MqttReceiver', 'com.facebook.push.mqtt.service.MqttPushHelperService', 'com.facebook.reportaproblem.base.bugreport.BugReportUploadService', 'com.facebook.rti.push.service.FbnsService', 'com.facebook.rti.config.ConfigProxyService', 'com.facebook.selfupdate.SelfUpdateFetchService', 'com.facebook.wearlistener.DataLayerListenerService'";//"'com.cloudmosa.puffin.DownloadService'";//"'com.apusapps.browser.service.CoreService'";
     			//"'com.notabasement.mangarock.android.lib.downloads.BackgroundService', 'com.parse.PushService', 'com.google.analytics.tracking.android.CampaignTrackingService', 'com.paypal.android.sdk.payments.PayPalService'";//"'com.marvel.unlimited.services.LibAndOfflineWorkerService'";//'com.iconology.client.push.GcmIntentService', 'com.fiksu.asotracking.InstallTracking', 'com.iconology.client.push.GcmBroadcastReceiver'";//'com.iconology.client.push.GcmIntentService', 'com.iconology.client.push.GcmBroadcastReceiver'";//"'com.iconology.client.push.GcmBroadcastReceiver'";//"'com.iconology.client.push.GcmIntentService'";//"'com.cyo.comicrack.viewer.CoverWallpaperService', 'com.cyo.comicrack.viewer.ComicWidgetStackProvider$StackWidgetService'";
     	while(s.indexOf(",")!=-1){
             String ser=s.substring(1,s.indexOf(",")-1);
             s=s.substring(s.indexOf(",")+2);
              services.add(ser);
          	}
         services.add(s.substring(1,s.length()-1));
         

	}
	static void printPostDominators(SootMethod src,List<SootMethod> methodList){
		if(src.hasActiveBody()){
			Body body = src.retrieveActiveBody();//getActiveBody();
			//UnitGraph g = new ExceptionalUnitGraph(body);
			//DirectedGraph<Unit> dg=new ExceptionalUnitGraph(body);
			
				OutputStream outputStream = null;
				try {
					outputStream = new FileOutputStream(new File(postDominatorOutputFile), true);
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}//"c:\\Ava\\CallGraphOutput.txt");
				PrintStream  writer = new PrintStream(outputStream);
				writer.println(" ");

				writer.println("Method:"+ src.getSignature());
			
			
			DirectedGraph<Unit> cfg=new HammockCFG(body);
			Map<Unit,Collection<Unit>> map =null ;//= Map<Unit,Collection<Unit>>[mId];
			if(map==null){
				try {
					map = DependencyHelper.calcCtrlDependences(cfg, postDominatorOutputFile, methodList);//Print post dominators
				} catch (IOException e) {
					// TODO Auto-generated catch block
					//writer.close();
					e.printStackTrace();
				}


			}
			writer.close();
		}
	}
	////change
	static void nextMethodDominators(List<SootMethod> methodList){
		List<SootMethod> nextMethodList = new ArrayList<SootMethod>();
		Iterator<SootMethod> methods= methodList.iterator();
		while(methods.hasNext()){
			SootMethod m = methods.next();
			if(m.getDeclaringClass().isApplicationClass()){//.getJavaPackageName().startsWith("android."))
					System.out.println("Edge:"+m);
					if(!allSootMethodList.contains(m)){
						allSootMethodList.add(m);
						printPostDominators(m,nextMethodList);
					}
					
				}
			}
		if(!nextMethodList.isEmpty())
			nextMethodDominators(nextMethodList);
	}
	
	
	static void nextMethodDominators(List<SootMethod> methodList, CallGraph cg){
		List<SootMethod> nextMethodList = new ArrayList<SootMethod>();
		Iterator<SootMethod> methods= methodList.iterator();
		while(methods.hasNext()){
			SootMethod m = methods.next();
			Iterator <Edge> edgeOutList = cg.edgesOutOf(m);
			while(edgeOutList.hasNext()){
				 Edge edgeOut = edgeOutList.next(); 
				if(edgeOut.getTgt().method().getDeclaringClass().isApplicationClass()){//.getJavaPackageName().startsWith("android."))
					System.out.println("Edge:"+edgeOut.getSrc()+"*"+edgeOut.getTgt()+"*"+edgeOut.getTgt().method().getDeclaringClass().getJavaPackageName());
					
					//printPostDominators(edgeOut.getTgt().method())
					printPostDominators(edgeOut.getTgt().method(),methodList);
					
					nextMethodList.add(edgeOut.getTgt().method());
				}
			}
		}
		if(!nextMethodList.isEmpty())
			nextMethodDominators(nextMethodList,cg);
	}
	
	static void Travers(CallGraph g,Edge edge){
		SootClass a = edge.tgt().getDeclaringClass();//Scene.v().getSootClass("com.GoldDream.zj.zjService$1");//"com.GoldDream.zj.zjService");
		SootMethod src = a.getMethodByName(edge.tgt().getName());//"run");//onStart");
		//CallGraph cg =Scene.v().getCallGraph();

		Iterator<Edge> targets = g.edgesOutOf(src);
		while (targets.hasNext()){
			Edge e= targets.next();
			//Edge e=targets.next();
			//System.out.println("e: "+e.getTgt()+"***"+e.toString());
			//e.getTgt().method().hasActiveBody()

			if(!e.getTgt().method().isJavaLibraryMethod() && e.getTgt().method().getDeclaringClass().getName().contains("android")&& !e.getTgt().method().getDeclaringClass().getName().contentEquals("android.app.Service")){
				//SootClass declaringClass = e.getTgt().method().getDeclaringClass();
				System.out.println("Android API:"+e.getTgt().method().toString()+ "*"+ e.getTgt().method().getDeclaringClass().getName());
			}else
				if(e.getTgt().method().isJavaLibraryMethod() && !e.getTgt().method().getDeclaringClass().getName().contentEquals("java.lang.Thread"))
				{
					System.out.println("Java:"+e.getTgt().method().toString()+"*"+e.getTgt().method().getDeclaringClass().getName());
					//e.isClinit()+e.isExplicit()+e.isInstance()+e.isInstance()+e.isSpecial()+e.isStatic()+e.isThreadRunCall()+e.isVirtual());

				}else
				{//false true true true true falsefalsefalse
					System.out.println("Method:"+e.getTgt().method().toString());
					if( g.edgesOutOf(e.getTgt().method()).hasNext() ){ //e.getTgt().method().hasActiveBody() &&
						Iterator<Edge> t = g.edgesOutOf(e.getTgt().method());
						while (t.hasNext()){
							Edge e1= t.next();

						System.out.println("Method1:"+e1.getTgt().method().toString());
						}
					}
					if(!e.getTgt().method().isConstructor()){
					Travers(g,e);
					}

				}



		}
	}
}

