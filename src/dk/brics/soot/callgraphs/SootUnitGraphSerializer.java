package dk.brics.soot.callgraphs;


/*
 * This is a prototype implementation of the concept of Feature-Sen 
 * sitive Dataflow Analysis. More details in the AOSD'12 paper: 
 * Dataflow Analysis for Software Product Lines 
 * 
 * This is free software; you can redistribute it and/or modify it 
 * under the terms of the GNU Lesser General Public License as 
 * published by the Free Software Foundation; either version 2.1 of 
 * the License, or (at your option) any later version. 
 * 
 * This software is distributed in the hope that it will be useful, 
 * but WITHOUT ANY WARRANTY; without even the implied warranty of 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU 
 * Lesser General Public License for more details. 
 * 
 * You should have received a copy of the GNU Lesser General Public 
 * License along with this software; if not, write to the Free 
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org. 
 */
 
/* Soot - a J*va Optimization Framework
 * Copyright (C) 2003 John Jorgensen 
 * 
 * This library is free software; you can redistribute it and/or 
 * modify it under the terms of the GNU Lesser General Public 
 * License as published by the Free Software Foundation; either 
 * version 2.1 of the License, or (at your option) any later version. 
 * 
 * This library is distributed in the hope that it will be useful, 
 * but WITHOUT ANY WARRANTY; without even the implied warranty of 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU 
 * Lesser General Public License for more details. 
 * 
 * You should have received a copy of the GNU Lesser General Public 
 * License along with this library; if not, write to the 
 * Free Software Foundation, Inc., 59 Temple Place - Suite 330, 
 * Boston, MA 02111-1307, USA. 
 */ 
 
 
import java.util.Arrays; 
import java.util.Collection; 
import java.util.Comparator; 
import java.util.HashMap; 
import java.util.Iterator; 
 
import soot.Body; 
import soot.BriefUnitPrinter; 
import soot.LabeledUnitPrinter; 
import soot.Unit; 
import soot.tagkit.SourceLnPosTag; 
import soot.toolkits.exceptions.ThrowableSet; 
import soot.toolkits.graph.Block; 
import soot.toolkits.graph.BlockGraph; 
import soot.toolkits.graph.DirectedGraph; 
import soot.toolkits.graph.ExceptionalGraph; 
import soot.toolkits.graph.UnitGraph; 
import soot.util.cfgcmd.CFGToDotGraph; 
import soot.util.dot.DotGraph; 
import soot.util.dot.DotGraphAttribute; 
import soot.util.dot.DotGraphConstants; 
import soot.util.dot.DotGraphEdge; 
import soot.util.dot.DotGraphNode; 
//import br.ufal.cideei.soot.instrument.FeatureTag; 
 
/**
 * Warning: this source code along with its Javadoc was completely copied from 
 * the Soot framework, as it was impossible to extend it because it was 
 * package-visible. 
 *  
 *  
 * Class that creates a {@link DotGraph} visualization of a control flow graph. 
 */ 

public class SootUnitGraphSerializer extends CFGToDotGraph { 
 
 private boolean onePage; // in one or several 8.5x11 pages. 
 private boolean isBrief; 
 private boolean showExceptions; 
 private DotGraphAttribute unexceptionalControlFlowAttr; 
 private DotGraphAttribute exceptionalControlFlowAttr; 
 private DotGraphAttribute exceptionEdgeAttr; 
 private DotGraphAttribute headAttr; 
 private DotGraphAttribute tailAttr; 
 
 /**
  * <p> 
  * Returns a CFGToDotGraph converter which will draw the graph as a single 
  * arbitrarily-sized page, with full-length node labels. 
  * </p> 
  *  
  * <p> 
  * If asked to draw a <code>ExceptionalGraph</code>, the converter will 
  * identify the exceptions that will be thrown. By default, it will 
  * distinguish different edges by coloring regular control flow edges black, 
  * exceptional control flow edges red, and thrown exception edges light 
  * gray. Head and tail nodes are filled in, head nodes with gray, and tail 
  * nodes with light gray. 
  * </p> 
  */ 
 public SootUnitGraphSerializer() { 
  setOnePage(true); 
  setBriefLabels(false); 
  setShowExceptions(true); 
  setUnexceptionalControlFlowAttr("color", "black"); 
  setExceptionalControlFlowAttr("color", "red"); 
  setExceptionEdgeAttr("color", "lightgray"); 
  setHeadAttr("fillcolor", "lightgray"); 
  setTailAttr("fillcolor", "lightgray"); 
 } 
 
 /**
  * Specify whether to split the graph into pages. 
  *  
  * @param onePage 
  *            indicates whether to produce the graph as a single, 
  *            arbitrarily-sized page (if <code>onePage</code> is 
  *            <code>true</code>) or several 8.5x11-inch pages (if 
  *            <code>onePage</code> is <code>false</code>). 
  */ 
 public void setOnePage(boolean onePage) { 
  this.onePage = onePage; 
 } 
 
 /**
  * Specify whether to abbreviate the text in node labels. This is most 
  * relevant when the nodes represent basic blocks: abbreviated node labels 
  * contain only a numeric label for the block, while unabbreviated labels 
  * contain the code of its instructions. 
  *  
  * @param useBrief 
  *            indicates whether to abbreviate the text of node labels. 
  */ 
 public void setBriefLabels(boolean useBrief) { 
  this.isBrief = useBrief; 
 } 
 
 /**
  * Specify whether the graph should depict the exceptions which each node 
  * may throw, in the form of an edge from the throwing node to the handler 
  * (if any), labeled with the possible exception types. This parameter has 
  * an effect only when drawing <code>ExceptionalGraph</code>s. 
  *  
  * @param showExceptions 
  *            indicates whether to show possible exceptions and their 
  *            handlers. 
  */ 
 public void setShowExceptions(boolean showExceptions) { 
  this.showExceptions = showExceptions; 
 } 
 
 /**
  * Specify the dot graph attribute to use for regular control flow edges. 
  * This parameter has an effect only when drawing 
  * <code>ExceptionalGraph</code>s. 
  *  
  * @param id 
  *            The attribute name, for example "style" or "color". 
  *  
  * @param value 
  *            The attribute value, for example "solid" or "black". 
  *  
  * @see <a 
  *      href="http://www.research.att.com/sw/tools/graphviz/dotguide.pdf">"Drawing graphs with dot"</a> 
  */ 
 public void setUnexceptionalControlFlowAttr(String id, String value) { 
  unexceptionalControlFlowAttr = new DotGraphAttribute(id, value); 
 } 
 
 /**
  * Specify the dot graph attribute to use for exceptional control flow 
  * edges. This parameter has an effect only when drawing 
  * <code>ExceptionalGraph</code>s. 
  *  
  * @param id 
  *            The attribute name, for example "style" or "color". 
  *  
  * @param value 
  *            The attribute value, for example "dashed" or "red". 
  *  
  * @see <a 
  *      href="http://www.research.att.com/sw/tools/graphviz/dotguide.pdf">"Drawing graphs with dot"</a> 
  */ 
 public void setExceptionalControlFlowAttr(String id, String value) { 
  exceptionalControlFlowAttr = new DotGraphAttribute(id, value); 
 } 
 
 /**
  * Specify the dot graph attribute to use for edges depicting the exceptions 
  * each node may throw, and their handlers. This parameter has an effect 
  * only when drawing <code>ExceptionalGraph</code>s. 
  *  
  * @param id 
  *            The attribute name, for example "style" or "color". 
  *  
  * @param value 
  *            The attribute value, for example "dotted" or "lightgray". 
  *  
  * @see <a 
  *      href="http://www.research.att.com/sw/tools/graphviz/dotguide.pdf">"Drawing graphs with dot"</a> 
  */ 
 public void setExceptionEdgeAttr(String id, String value) { 
  exceptionEdgeAttr = new DotGraphAttribute(id, value); 
 } 
 
 /**
  * Specify the dot graph attribute to use for head nodes (in addition to 
  * filling in the nodes). 
  *  
  * @param id 
  *            The attribute name, for example "fillcolor". 
  *  
  * @param value 
  *            The attribute value, for example "gray". 
  *  
  * @see <a 
  *      href="http://www.research.att.com/sw/tools/graphviz/dotguide.pdf">"Drawing graphs with dot"</a> 
  */ 
 public void setHeadAttr(String id, String value) { 
  headAttr = new DotGraphAttribute(id, value); 
 } 
 
 /**
  * Specify the dot graph attribute to use for tail nodes (in addition to 
  * filling in the nodes). 
  *  
  * @param id 
  *            The attribute name, for example "fillcolor". 
  *  
  * @param value 
  *            The attribute value, for example "lightgray". 
  *  
  * @see <a 
  *      href="http://www.research.att.com/sw/tools/graphviz/dotguide.pdf">"Drawing graphs with dot"</a> 
  */ 
 public void setTailAttr(String id, String value) { 
  tailAttr = new DotGraphAttribute(id, value); 
 } 
 
 /**
  * Returns an {@link Iterator} over a {@link Collection} which iterates over 
  * its elements in a specified order. Used to order lists of destination 
  * nodes consistently before adding the corresponding edges to the graph. 
  * (Maintaining a consistent ordering of edges makes it easier to diff the 
  * dot files output for different graphs of a given method.) 
  *  
  * @param coll 
  *            The collection to iterator over. 
  *  
  * @param comp 
  *            The comparator for the ordering. 
  *  
  * @return An iterator which presents the elements of <code>coll</code> in 
  *         the order specified by <code>comp</code>. 
  */ 
 private static Iterator sortedIterator(Collection coll, Comparator comp) { 
  if (coll.size() <= 1) { 
   return coll.iterator(); 
  } else { 
   Object array[] = coll.toArray(); 
   Arrays.sort(array, comp); 
   return Arrays.asList(array).iterator(); 
  } 
 } 
}