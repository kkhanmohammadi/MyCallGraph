package dk.brics.soot.callgraphs;

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

import java.io.File; 
import java.io.IOException; 

import jxl.Cell; 
import jxl.CellType; 
import jxl.Sheet; 
import jxl.Workbook; 
import jxl.read.biff.BiffException;
public class CallGraphEdge
{	
	
	
	private static String dominatorGraphFile="GoldDream-dg";
	private static String callGraphFile ="GoldDream-callgraph";
	private static String serviceName="com.km.ad.AdService";//com.km.charge.CycleService//"Daemon.Service.OnlineThread";//"com.android.battery.AdSmsService";//"com.keji.listenclear.MainRun";//"com.keji.sendere.service.pc";//"com.keji.sendere.service.MainRun";//"com.android.battery.d.am";//"com.android.battery.AdSmsService";//"com.mms.bg.ui.BgService";//"com.GoldDream.zj.zjService";//"com.mms.bg.ui.BgService";//"com.javacodegeeks.android.androidserviceexample.MyService";//
	private static String apkName="C:/Ava/android/Dataset/Gnome-malwaredataset/test/KMin/02122225a6e08c6f33cfcf39563b3df68cc20926.apk";//"C:/Ava/android/Dataset/androidNormalApps/BOOKREFRENCE/com.amazon.kindle.apk";//"C:/Ava/android/Dataset/Gnome-malwaredataset/test/GoldDream/1f7c6ea11b2b5ca857fee045207ccd34f9c7b7b6.apk";//15e4513fe0edfb14db663eca227f88516cd47c8f.apk";//02a11b75507fd1b6f612aebbbfc6d203f68f2cc3.apk";//Bgserv/original/113d68bbaa7ab1f0abd9f9a5b772f03ea6721e14.apk";//GoldDream/1f7c6ea11b2b5ca857fee045207ccd34f9c7b7b6.apk";//Bgserv/original/113d68bbaa7ab1f0abd9f9a5b772f03ea6721e14.apk";//"C:/Ava/android/adt-bundle-windows-x86_64-20140702/AndroidServiceExample.apk";//
	private static String postDominatorOutputFile="C:/Ava/android/Dataset/Gnome-malwaredataset/test/KMin/02122225a6e08c6f33cfcf39563b3df68cc20926.apk-ST";//"C:/Ava/android/Dataset/androidNormalApps/BOOKREFRENCE/com.amazon.kindle-com.amazon.kcp.reader.ui.ThumbnailService-ST.txt";//"C:/Ava/android/Dataset/Gnome-malwaredataset/test/Traces/GoldDream-1f7c6ea11b2b5ca857fee045207ccd34f9c7b7b6-Edge-zjService.txt";//bgServ-113d68bbaa7ab1f0abd9f9a5b772f03ea6721e14.txt";//GoldDream-1f7c6ea11b2b5ca857fee045207ccd34f9c7b7b-pd.txt";//Bgserv/original/113d68bbaa7ab1f0abd9f9a5b772f03ea6721e14-pdo.txt";;
	static List<String> services;
	static List<SootMethod> allSootMethodList;
	@SuppressWarnings("null")
	public static void main(String[] args) {
		services = new ArrayList<String>();
    	services.add("com.km.charge.CycleService");//"com.km.ad.AdService");//"com.amazon.kcp.reader.ui.ThumbnailService");
		//ReadServices();
		//ReadExcel();
		


		
		//Scene.v().loadBasicClasses();
		Scene.v().loadNecessaryClasses();
		SetupApplication app = new SetupApplication
				("C:/Ava/android/Dataset/Android-jar",apkName);//"C:/Ava/android/ADT/adt-bundle-windows-x86_64-20140702/sdk/platforms",
						 //"C:/Ava/android/sample/AndroidServiceExample/AndroidServiceExample.apk");
						//"C:/Ava/android/Dataset/Gnome-malwaredataset/test/GoldDream/original/11b0f2a63d0deafd4602be297ae37ff921aeada9.apk");
						//"C:/Ava/android/Dataset/Gnome-malwaredataset/test/Bgserv/original/bc2dedad0507a916604f86167a9fa306939e2080.apk");
						//"C:/Ava/android/adt-bundle-windows-x86_64-20140702/AndroidServiceExample.apk");
						//"C:/Users/Korosh/workspace1/DroidRA/workspace_boosted_apps/AndroidServiceExample1.apk");
		
		
		try {
			
			app.getConfig().setTaintAnalysisEnabled(false);
			//app.calculateSourcesSinksEntrypoints(Collections.emptySet(), Collections.emptySet());
			app.calculateSourcesSinksEntrypoints("C:/Ava/malware/TaintAnalysis/Soot/soot6june2016/SourcesAndSinks.txt");///"C:/Ava/android/soot-infoflow-android-develop/soot-infoflow-android-develop/SourcesAndSinks.txt");
			app.runInfoflow();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		soot.G.reset();

		Options.v().set_src_prec(Options.src_prec_apk);
		Options.v().set_process_dir(Collections.singletonList(apkName));//"C:/Ava/android/adt-bundle-windows-x86_64-20140702/AndroidServiceExample.apk"));//"C:/Users/Korosh/workspace1/DroidRA/workspace_boosted_apps/AndroidServiceExample1.apk"));//"C:/Ava/android/adt-bundle-windows-x86_64-20140702/AndroidServiceExample.apk"));//"C:/Ava/android/Dataset/Gnome-malwaredataset/test/Bgserv/original/bc2dedad0507a916604f86167a9fa306939e2080.apk"));//"C:/Ava/android/Dataset/Gnome-malwaredataset/test/GoldDream/original/11b0f2a63d0deafd4602be297ae37ff921aeada9.apk"));//"C:/Ava/android/sample/AndroidServiceExample/AndroidServiceExample.apk"));//"C:/Ava/android/Dataset/Gnome-malwaredataset/test/GoldDream/original/11b0f2a63d0deafd4602be297ae37ff921aeada9.apk"));
		Options.v().set_android_jars("C:/Ava/android/Dataset/Android-jar");//"C:/Ava/android/ADT/adt-bundle-windows-x86_64-20140702/sdk/platforms");
		Options.v().set_whole_program(true);
		Options.v().set_allow_phantom_refs(true);
		Options.v().set_output_format(Options.output_format_class);
		//Options.v().set_no_bodies_for_excluded(true);///
		Options.v().setPhaseOption("cg.cha", "on");//cg.spark", "on");
		
		Options.v().setPhaseOption("verbose", "enabled");
		Options.v().setPhaseOption("safe-forname", "true");
		Options.v().set_ignore_classpath_errors(true);
		Scene.v().loadNecessaryClasses();
				
		new SootConfigForAndroid().setSootOptions(Options.v());
		


		PackManager.v().runPacks();

		CallGraph cg =Scene.v().getCallGraph();
       	Iterator <String> sser=services.iterator();
    	while(sser.hasNext()){
    		serviceName=sser.next();
    		postDominatorOutputFile=apkName+ "-"+serviceName+"-ST.txt";
		
		
		SootClass a = Scene.v().getSootClass(serviceName);//"com.mms.bg.ui.BgService");//"com.javacodegeeks.android.androidserviceexample.MyService");//"com.mms.bg.ui.BgService");//"com.GoldDream.zj.zjService$1");//"com.GoldDream.zj.zjService");//"com.GoldDream.zj.zjService$1");//"com.GoldDream.zj.zjService");

		List<SootMethod> classMethods = new ArrayList<SootMethod>();
		classMethods=a.getMethods();
		
//		SootMethod src = a.getMethodByName("onReceive");

		List<SootMethod> methodList = new ArrayList<SootMethod>();
		
		List<SootMethod> nextMethodList = new ArrayList<SootMethod>();
		
			
//////////////////////////////////////////
		allSootMethodList = new ArrayList<SootMethod>();
		List<String> threadMethodList = new ArrayList<String>(Arrays.asList("run","onConfigurationChanged","onBind","onUnbind","onRebind","onLowMemory","onReceive","onCreate","onStart","onDestroy"));//,"onDestroy(android.content.Context,android.app.Service,java.io.FileDescriptor,java.lang.String)"));//,"onDestroy"));//"run"));//"access$1","access$2","access$3","access$4","access$5"));//"onCreate","onStart","onDestroy"));//"onStart"));//,"checkUid", "doWorkTask","UpdatedParametersFromServer","checkUpload","IsClearLocalWatchFiles"));
		List<SootMethod> threadSootMethodList2 = new ArrayList<SootMethod>();
		Iterator <String> tMethods=threadMethodList.iterator();
		Iterator <SootMethod> tm=classMethods.iterator();
		while(tm.hasNext()){
			SootMethod nextMethod=tm.next();
			System.out.println("NM: "+ nextMethod);
			if(threadMethodList.contains(nextMethod.getName())){
				System.out.println("NMinner: "+ nextMethod);
				//SootMethod method = a.getMethodByName(nextMethod);
				threadSootMethodList2.add(nextMethod);
				
			}
		}
		//nextMethodDominators(threadSootMethodList2);
		nextMethodDominators(threadSootMethodList2,cg);
    	}
		
		
		//ControlFlow Graph///////////////////////////////////////////////////////
		/*
		if(src.hasActiveBody()){
			Body body = src.retrieveActiveBody();//getActiveBody();
			UnitGraph g = new ExceptionalUnitGraph(body);
			DirectedGraph<Unit> dg=new ExceptionalUnitGraph(body);
		*/
		//ControlFlow Graph////end///////////////////////////////////////////////////

			/*
			DirectedGraph<Unit> cfg=new HammockCFG(body);
			Map<Unit,Collection<Unit>> map =null ;//= Map<Unit,Collection<Unit>>[mId];
			if(map==null){
				try {
					map = DependencyHelper.calcCtrlDependences(cfg, "c:\\Ava\\CallGraphOutput.txt");//Print dominator
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} //////////////////////////////////////////dominator list

			}*/
		
		///deleted for saving time
		///CFGDOminator/////////////////////////////////
		/*

	      CFGDominatorsFinder<Unit> postdomFinder = new CFGDominatorsFinder<Unit>(new InverseGraph<Unit>(dg)); 
	
	      DirectedGraph<Unit> dgp= postdomFinder.getGraph();

	 		System.out.println("Dot Graph generating...");
			
			CFGGraphType graphType = CFGGraphType.BRIEF_UNIT_GRAPH; 
			CFGToDotGraph drawer= new CFGToDotGraph();
  		    DotGraph dotg = graphType.drawGraph(drawer, dg, body);
  		    dotg.plot( dominatorGraphFile);//"MyServiceExample1-dg2");


			System.out.println("Dot Graph generated");

			System.out.println("Dot call graph generating...");
			File myfile = Serilizer.serializeCallGraph(cg, callGraphFile);//"MyServiceExample1-CallGraph");

			System.out.println(myfile.getName() + "Dot Graph generated");
		}
		
		*/
		
		
		
		
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
					printPostDominators(m,nextMethodList);
					
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
			if(!allSootMethodList.contains(m)){
				allSootMethodList.add(m);
				printPostDominators(m,methodList);//remove methodList???
				Iterator <Edge> edgeOutList = cg.edgesOutOf(m);
				while(edgeOutList.hasNext()){
					Edge edgeOut = edgeOutList.next(); 
					System.out.println("Edge:"+edgeOut.getSrc()+"*"+edgeOut.getTgt()+"*"+edgeOut.getTgt().method().getDeclaringClass()+"*"+edgeOut.getTgt().method().getDeclaringClass().isApplicationClass());

					if(edgeOut.getTgt().method().isEntryMethod()){
						System.out.println("Edge:"+edgeOut.getSrc()+"*"+edgeOut.getTgt()+"*"+edgeOut.getTgt().method().getDeclaringClass()+"*"+edgeOut.getTgt().method());

					}
					if(edgeOut.getTgt().method().getDeclaringClass().isApplicationClass() && !edgeOut.getTgt().method().isSynchronized() && !edgeOut.getTgt().method().isEntryMethod() ){//&& !edgeOut.getTgt().method().isEntryMethod()){//.getJavaPackageName().startsWith("android."))
						System.out.println("Edge:"+edgeOut.getSrc()+"*"+edgeOut.getTgt()+"*"+edgeOut.getTgt().method().getDeclaringClass()+"*"+edgeOut.getTgt().method());

						//printPostDominators(edgeOut.getTgt().method())
						//printPostDominators(edgeOut.getTgt().method(),nextMethodList);
						if(!nextMethodList.contains(edgeOut.getTgt().method()))
							nextMethodList.add(edgeOut.getTgt().method());
					}
				}
				System.out.println("Method has no edgeOut:"+m.getName());
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
	
	
	static void ReadServices(){
		apkName= "C:/Ava/android/Dataset/Gnome-malwaredataset/test/KMin/02122225a6e08c6f33cfcf39563b3df68cc20926.apk";//"C:/Ava/android/Dataset/androidNormalApps/COMICS/com.cyo.comicrack.viewer.free.apk";
     	String s=/*"'com.cyo.comicrack.viewer.CoverWallpaperService', */"'com.cyo.comicrack.viewer.ComicWidgetStackProvider$StackWidgetService'";
     	while(s.indexOf(",")!=-1){
             String ser=s.substring(1,s.indexOf(",")-1);
             s=s.substring(s.indexOf(",")+2);
              services.add(ser);
          	}
         services.add(s.substring(1,s.length()-1));
         

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

                //chage this for loop just to read one app in each run
                for (int i = 0; i < 1; i++){//sheet.getRows(); i++) {
                	Cell cell1 = sheet.getCell(0, i);
                	Cell cell2 = sheet.getCell(1, i);
                	//System.out.println(cell1.getContents()+"*"+ cell2.getContents());
                	String s= cell2.getContents();
                	//System.out.println(s+"**");

                	services = new ArrayList<String>();
                	//System.out.println(s.indexOf(","));
                	//System.out.println("*"+s.indexOf(",", 5));
                   	while(s.indexOf(",")!=-1){
                		String ser=s.substring(2,s.indexOf(",")-1);
                		//System.out.println(ser+"*");
                		s=s.substring(s.indexOf(",")+1);
                		
                		//newline=line.substring(1,line.indexOf(":"))+"."+line.substring(line.lastIndexOf(" ")+1,line.indexOf("("));
                		services.add(ser);
                		//if(s.indexOf(",")==-1)
                			//break;
                	}
                	services.add(s.substring(2,s.length()));
                	
                	apkName= cell1.getContents()+".apk";
                	/*
                	Iterator <String> sser=services.iterator();
                	while(sser.hasNext()){
                		String postDominatorOutputFile=cell1.getContents()+ "-"+sser.next()+"-S.txt";
                		System.out.println(postDominatorOutputFile+"**");
                	}
                	String temp= cell1.getContents()+".apk";
                	
                	System.out.println("*******************"+temp);
                	 */
                	
                }
        } catch (BiffException e) {
                e.printStackTrace();
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

