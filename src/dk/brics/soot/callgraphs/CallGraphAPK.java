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

public class CallGraphAPK
{	
	
	
	private static String dominatorGraphFile="AnServer-dg";
	private static String callGraphFile ="AnServer-callgraph";
	private static String serviceName="Daemon.Service.OnlineThread";//"com.android.battery.AdSmsService";//"com.keji.listenclear.MainRun";//"com.keji.sendere.service.pc";//"com.keji.sendere.service.MainRun";//"com.android.battery.d.am";//"com.android.battery.AdSmsService";//"com.mms.bg.ui.BgService";//"com.GoldDream.zj.zjService";//"com.mms.bg.ui.BgService";//"com.javacodegeeks.android.androidserviceexample.MyService";//
	private static String apkName="C:/Ava/android/Dataset/Gnome-malwaredataset/test/YZHC/01e066b6b43e30102d5046881c363a9f590a901b.apk";//15e4513fe0edfb14db663eca227f88516cd47c8f.apk";//02a11b75507fd1b6f612aebbbfc6d203f68f2cc3.apk";//Bgserv/original/113d68bbaa7ab1f0abd9f9a5b772f03ea6721e14.apk";//GoldDream/1f7c6ea11b2b5ca857fee045207ccd34f9c7b7b6.apk";//Bgserv/original/113d68bbaa7ab1f0abd9f9a5b772f03ea6721e14.apk";//"C:/Ava/android/adt-bundle-windows-x86_64-20140702/AndroidServiceExample.apk";//
	private static String postDominatorOutputFile="C:/Ava/android/Dataset/Gnome-malwaredataset/test/Traces/YZHC-01e066b6b43e30102d5046881c363a9f590a901b-OnlineThread.txt";//bgServ-113d68bbaa7ab1f0abd9f9a5b772f03ea6721e14.txt";//GoldDream-1f7c6ea11b2b5ca857fee045207ccd34f9c7b7b-pd.txt";//Bgserv/original/113d68bbaa7ab1f0abd9f9a5b772f03ea6721e14-pdo.txt";;
	@SuppressWarnings("null")
	public static void main(String[] args) {
		/* List<String> argsList = new ArrayList<String>(Arrays.asList(args));
	   argsList.addAll(Arrays.asList(new String[]{
			   "-w",
			   "-force-android-jar",
			   "C:/Ava/android/Dataset/Android-jar/android-16.jar",
			   "-pp",
			   "-cp",
			   "C:/Ava/android/Dataset/Gnome-malwaredataset/test/BeanBot/original/jars/test.jar",
			   "-process-path",
			   	"C:/Ava/android/Dataset/Gnome-malwaredataset/test/BeanBot/original/jars/test.jar",
			   //"-main-class",
			   //"testers.CallGraphs",//main-class
			   "com.android.providers.update.OperateService"//"testers.CallGraphs",//argument classes
			   //"testers.A"			//
	   }));*/


		/*   PackManager.v().getPack("wjtp").add(new Transform("wjtp.myTrans", new SceneTransformer() {

		@Override
		protected void internalTransform(String phaseName, Map options) {
		       //CHATransformer.v().transform();
                 /*      SootClass a = Scene.v().getSootClass("com.android.providers.update.OperateService");//testers.A");

		       SootMethod src = a.getMethodByName("onStart");
		       CallGraph cg = Scene.v().getCallGraph();

		       Iterator<MethodOrMethodContext> sources = new Sources(cg.edgesInto(focus));
		     while (sources.hasNext()) {
		    	 SootMethod src1 = (SootMethod)sources.next();
		    	 System.out.println(method + " is called by " + src1);
		      }






		       Iterator<MethodOrMethodContext> targets = new Targets(cg.edgesOutOf(src));
		       while (targets.hasNext()) {
		           SootMethod tgt = (SootMethod)targets.next();
		           System.out.println(src + " may call " + tgt);
		       }
		}

	   }));*/

		/* args = argsList.toArray(new String[0]);

           soot.Main.main(args);*/


		SetupApplication app = new SetupApplication
				("C:/Ava/android/Dataset/Android-jar",apkName);//"C:/Ava/android/ADT/adt-bundle-windows-x86_64-20140702/sdk/platforms",
						 //"C:/Ava/android/sample/AndroidServiceExample/AndroidServiceExample.apk");
						//"C:/Ava/android/Dataset/Gnome-malwaredataset/test/GoldDream/original/11b0f2a63d0deafd4602be297ae37ff921aeada9.apk");
						//"C:/Ava/android/Dataset/Gnome-malwaredataset/test/Bgserv/original/bc2dedad0507a916604f86167a9fa306939e2080.apk");
						//"C:/Ava/android/adt-bundle-windows-x86_64-20140702/AndroidServiceExample.apk");
						//"C:/Users/Korosh/workspace1/DroidRA/workspace_boosted_apps/AndroidServiceExample1.apk");
		
		//Options.v().set_soot_classpath("");
		try {
			app.calculateSourcesSinksEntrypoints("C:/Ava/android/soot-infoflow-android-develop/soot-infoflow-android-develop/SourcesAndSinks.txt");
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
		Options.v().setPhaseOption("cg.spark", "on");//"cg.cha", "on");//cg.spark", "on");
		
		Options.v().setPhaseOption("verbose", "enabled");
		Options.v().setPhaseOption("safe-forname", "true");
		
		
		
		new SootConfigForAndroid().setSootOptions(Options.v());
		
		//Scene.v().loadBasicClasses();
		Scene.v().loadNecessaryClasses();

		SootMethod entryPoint = app.getEntryPointCreator().createDummyMain();
		entryPoint.getActiveBody().validate();
		Options.v().set_main_class(entryPoint.getSignature());
		
		Scene.v().setEntryPoints(Collections.singletonList(entryPoint));
		System.out.println("EntryPoint:"+entryPoint.getActiveBody()+"******");

		PackManager.v().runPacks();
		//System.out.println("Graph Size: "+Scene.v().getCallGraph().size());

		
		
		SootClass a = Scene.v().getSootClass(serviceName);//"com.mms.bg.ui.BgService");//"com.javacodegeeks.android.androidserviceexample.MyService");//"com.mms.bg.ui.BgService");//"com.GoldDream.zj.zjService$1");//"com.GoldDream.zj.zjService");//"com.GoldDream.zj.zjService$1");//"com.GoldDream.zj.zjService");

		List<SootMethod> test = new ArrayList<SootMethod>();
		test=a.getMethods();
		Iterator <SootMethod> tm=test.iterator();
		List<SootMethod> threadSootMethodList = new ArrayList<SootMethod>();
		while(tm.hasNext()){
			//System.out.println("methodsssssss"+tm.next());
			threadSootMethodList.add(tm.next());
		}
		
		//SootMethod src = a.getMethodByName("start");
		
		//threadSootMethodList.add(src);
		nextMethodDominators(threadSootMethodList);
	

//		SootMethod src = a.getMethodByName("onReceive");

		List<SootMethod> methodList = new ArrayList<SootMethod>();
		
		List<SootMethod> nextMethodList = new ArrayList<SootMethod>();
		
		//CallGraph cg =Scene.v().getCallGraph();
		//SootMethod method = a.getMethodByName("onStart");

		/*Iterator <Edge> edgeOutList = cg.edgesOutOf(src1);
		while(edgeOutList.hasNext()){
			Edge edgeOut = edgeOutList.next(); 
				System.out.println("Edge*******:"+edgeOut.getSrc()+"*"+edgeOut.getTgt()+"*******");
			}
		*/		
		
//////////////////////////////////////////
		//List<String> threadMethodList = new ArrayList<String>(Arrays.asList("onReceive"));//"onCreate","onStart"));//,"onDestroy"));//,"onDestroy(android.content.Context,android.app.Service,java.io.FileDescriptor,java.lang.String)"));//,"onDestroy"));//"run"));//"access$1","access$2","access$3","access$4","access$5"));//"onCreate","onStart","onDestroy"));//"onStart"));//,"checkUid", "doWorkTask","UpdatedParametersFromServer","checkUpload","IsClearLocalWatchFiles"));
		//List<String> threadMethodList = new ArrayList<String>(Arrays.asList("run"));//"onCreate","onStart"));//,"onDestroy"));//,"onDestroy(android.content.Context,android.app.Service,java.io.FileDescriptor,java.lang.String)"));//,"onDestroy"));//"run"));//"access$1","access$2","access$3","access$4","access$5"));//"onCreate","onStart","onDestroy"));//"onStart"));//,"checkUid", "doWorkTask","UpdatedParametersFromServer","checkUpload","IsClearLocalWatchFiles"));
/*		List<String> threadMethodList = new ArrayList<String>(Arrays.asList("onCreate","onStart","onDestroy"));//,"onDestroy(android.content.Context,android.app.Service,java.io.FileDescriptor,java.lang.String)"));//,"onDestroy"));//"run"));//"access$1","access$2","access$3","access$4","access$5"));//"onCreate","onStart","onDestroy"));//"onStart"));//,"checkUid", "doWorkTask","UpdatedParametersFromServer","checkUpload","IsClearLocalWatchFiles"));
	//	List<String> threadMethodList = new ArrayList<String>(Arrays.asList("onCreate"));//,"onDestroy(android.content.Context,android.app.Service,java.io.FileDescriptor,java.lang.String)"));//,"onDestroy"));//"run"));//"access$1","access$2","access$3","access$4","access$5"));//"onCreate","onStart","onDestroy"));//"onStart"));//,"checkUid", "doWorkTask","UpdatedParametersFromServer","checkUpload","IsClearLocalWatchFiles"));

		List<SootMethod> threadSootMethodList = new ArrayList<SootMethod>();
		Iterator <String> tMethods=threadMethodList.iterator();
		while(tMethods.hasNext()){
			SootMethod method = a.getMethodByName(tMethods.next());
			threadSootMethodList.add(method);
		}
		nextMethodDominators(threadSootMethodList);
*/
		
		/*
			System.out.println("MethodName-------------"+method.getName().toString());

			printPostDominators(method, methodList);
			//Iterator <Edge> edgeOutList = cg.edgesOutOf(method);//deleted
	
			
			
			
			Iterator <SootMethod> methods=methodList.iterator();//for GoldDream

			while(methods.hasNext()){
				SootMethod edgeOut = methods.next(); 
				System.out.println("MethodOut:"+edgeOut.toString());
				printPostDominators(edgeOut,nextMethodList);
				//methodList.add(edgeOut.getTgt().method());

			}
			methodList.clear();
		}*/
			////////////////////GoldDream
			
			
			
			
			
			
			/*

			while(edgeOutList.hasNext()){
				Edge edgeOut = edgeOutList.next(); 
				if(edgeOut.getTgt().method().getDeclaringClass().isApplicationClass()){//.getJavaPackageName().startsWith("android."))
					System.out.println("Edge:"+edgeOut.getSrc()+"*"+edgeOut.getTgt()+"*"+edgeOut.getTgt().method().getDeclaringClass().getJavaPackageName());
					printPostDominators(edgeOut.getTgt().method());
					methodList.add(edgeOut.getTgt().method());

				}
				else if(!edgeOut.getTgt().method().getDeclaringClass().isApplicationClass())//.getJavaPackageName().startsWith("android."))
					System.out.println("EdgeAPI:"+edgeOut.getSrc()+"*"+edgeOut.getTgt()+"*"+edgeOut.getTgt().method().getDeclaringClass().toString());
			}

			nextMethodDominators(methodList,cg);
			methodList.clear();
		}//TMethods
		*/
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

