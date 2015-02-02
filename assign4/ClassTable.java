import java.io.PrintStream;
import java.util.*;

/** This class may be used to contain the semantic information such as
 * the inheritance graph.  You may use it or not as you like: it is only
 * here to provide a container for the supplied methods.  */
class ClassTable {
    private int semantErrors;
    private PrintStream errorStream;
    private Node<AbstractSymbol> coolTree; 
    private Classes allClasses,cls;

    /** Creates data structures representing basic Cool classes (Object,
     * IO, Int, Bool, String).  Please note: as is this method does not
     * do anything useful; you will need to edit it to make if do what
     * you want.
     * */
    private void installBasicClasses() {
	AbstractSymbol filename 
	    = AbstractTable.stringtable.addString("<basic class>");
	
	// The following demonstrates how to create dummy parse trees to
	// refer to basic Cool classes.  There's no need for method
	// bodies -- these are already built into the runtime system.

	// IMPORTANT: The results of the following expressions are
	// stored in local variables.  You will want to do something
	// with those variables at the end of this method to make this
	// code meaningful.

	// The Object class has no parent class. Its methods are
	//        cool_abort() : Object    aborts the program
	//        type_name() : Str        returns a string representation 
	//                                 of class name
	//        copy() : SELF_TYPE       returns a copy of the object

	class_c Object_class = 
	    new class_c(0, 
		       TreeConstants.Object_, 
		       TreeConstants.No_class,
		       new Features(0)
			   .appendElement(new method(0, 
					      TreeConstants.cool_abort, 
					      new Formals(0), 
					      TreeConstants.Object_, 
					      new no_expr(0)))
			   .appendElement(new method(0,
					      TreeConstants.type_name,
					      new Formals(0),
					      TreeConstants.Str,
					      new no_expr(0)))
			   .appendElement(new method(0,
					      TreeConstants.copy,
					      new Formals(0),
					      TreeConstants.SELF_TYPE,
					      new no_expr(0))),
		       filename);
	
	// The IO class inherits from Object. Its methods are
	//        out_string(Str) : SELF_TYPE  writes a string to the output
	//        out_int(Int) : SELF_TYPE      "    an int    "  "     "
	//        in_string() : Str            reads a string from the input
	//        in_int() : Int                "   an int     "  "     "

	class_c IO_class = 
	    new class_c(0,
		       TreeConstants.IO,
		       TreeConstants.Object_,
		       new Features(0)
			   .appendElement(new method(0,
					      TreeConstants.out_string,
					      new Formals(0)
						  .appendElement(new formalc(0,
								     TreeConstants.arg,
								     TreeConstants.Str)),
					      TreeConstants.SELF_TYPE,
					      new no_expr(0)))
			   .appendElement(new method(0,
					      TreeConstants.out_int,
					      new Formals(0)
						  .appendElement(new formalc(0,
								     TreeConstants.arg,
								     TreeConstants.Int)),
					      TreeConstants.SELF_TYPE,
					      new no_expr(0)))
			   .appendElement(new method(0,
					      TreeConstants.in_string,
					      new Formals(0),
					      TreeConstants.Str,
					      new no_expr(0)))
			   .appendElement(new method(0,
					      TreeConstants.in_int,
					      new Formals(0),
					      TreeConstants.Int,
					      new no_expr(0))),
		       filename);
         

	// The Int class has no methods and only a single attribute, the
	// "val" for the integer.

	class_c Int_class = 
	    new class_c(0,
		       TreeConstants.Int,
		       TreeConstants.Object_,
		       new Features(0)
			   .appendElement(new attr(0,
					    TreeConstants.val,
					    TreeConstants.prim_slot,
					    new no_expr(0))),
		       filename);

	// Bool also has only the "val" slot.
	class_c Bool_class = 
	    new class_c(0,
		       TreeConstants.Bool,
		       TreeConstants.Object_,
		       new Features(0)
			   .appendElement(new attr(0,
					    TreeConstants.val,
					    TreeConstants.prim_slot,
					    new no_expr(0))),
		       filename);

	// The class Str has a number of slots and operations:
	//       val                              the length of the string
	//       str_field                        the string itself
	//       length() : Int                   returns length of the string
	//       concat(arg: Str) : Str           performs string concatenation
	//       substr(arg: Int, arg2: Int): Str substring selection

	class_c Str_class =
	    new class_c(0,
		       TreeConstants.Str,
		       TreeConstants.Object_,
		       new Features(0)
			   .appendElement(new attr(0,
					    TreeConstants.val,
					    TreeConstants.Int,
					    new no_expr(0)))
			   .appendElement(new attr(0,
					    TreeConstants.str_field,
					    TreeConstants.prim_slot,
					    new no_expr(0)))
			   .appendElement(new method(0,
					      TreeConstants.length,
					      new Formals(0),
					      TreeConstants.Int,
					      new no_expr(0)))
			   .appendElement(new method(0,
					      TreeConstants.concat,
					      new Formals(0)
						  .appendElement(new formalc(0,
								     TreeConstants.arg, 
								     TreeConstants.Str)),
					      TreeConstants.Str,
					      new no_expr(0)))
			   .appendElement(new method(0,
					      TreeConstants.substr,
					      new Formals(0)
						  .appendElement(new formalc(0,
								     TreeConstants.arg,
								     TreeConstants.Int))
						  .appendElement(new formalc(0,
								     TreeConstants.arg2,
								     TreeConstants.Int)),
					      TreeConstants.Str,
					      new no_expr(0))),
		       filename);

	/* Do somethind with Object_class, IO_class, Int_class,
           Bool_class, and Str_class here */
    coolTree = new Node<AbstractSymbol>(TreeConstants.Object_);
    coolTree.addChild(TreeConstants.IO); 
    coolTree.addChild(TreeConstants.Int); 
    coolTree.addChild(TreeConstants.Bool); 
    coolTree.addChild(TreeConstants.Str); 
    
    allClasses.addElement(Object_class);
    allClasses.addElement(IO_class);
    allClasses.addElement(Int_class);
    allClasses.addElement(Bool_class);
    allClasses.addElement(Str_class);
    }
	
    public ClassTable(Classes cls) {
      this.cls = cls;
      allClasses = new Classes(1,cls.copyElements());
      installBasicClasses();
      semantErrors = 0;
      errorStream = System.err;
      
      Vector<class_c> copyClass = cls.copyElements();
      int delta = copyClass.size();
      while (delta > 0) {
      int initial = copyClass.size();
      ListIterator<class_c>classIter = copyClass.listIterator();      
      while (classIter.hasNext()) {
        class_c element = classIter.next();
        if(element.getName() == TreeConstants.SELF_TYPE) {
         System.out.println("Class name cannot be SELF_TYPE exiting");
         System.exit(1);
        }
        Node<AbstractSymbol> treenode = coolTree.findNode(element.getParent()); 
          if (treenode != null) {
            treenode.addChild(element.getName()); 
            System.out.println("Added class with parent:" + element.getName().toString() +" " +element.getParent().toString());
            classIter.remove();
       }
     }
         int end = copyClass.size(); delta = initial - end;
    }
    coolTree.printtree();
/* If there is anything left in copyclass that is an error. There is cycle in the graph */
     if (copyClass.size() > 0) 
     System.out.println("There is a cycle in the class graph");
/* Hierarchy is well formed */
     else 
      addGlobal(allClasses); 
  }

public void addGlobal(Classes cls) { 

  Vector<class_c> copyClass = cls.copyElements();
  ListIterator<class_c>classIter = copyClass.listIterator();      
   while (classIter.hasNext()) {
     class_c element = classIter.next();
     Node<AbstractSymbol> treenode = coolTree.findNode(element.getName()); 
/*
     element.initialize(coolTree);
*/
    element.initialize(treenode);
   }
}

public Node<AbstractSymbol> getTree() {
      return(coolTree);
}
public Classes getClasses () {
     return (allClasses);
}
  
/* This routine assumes that hierarchy is well formed */

    /** Prints line number and file name of the given class.
     *
     * Also increments semantic error count.
     *
     * @param c the class
     * @return a print stream to which the rest of the error message is
     * to be printed.
     *
     * */
    public PrintStream semantError(class_c c) {
	return semantError(c.getFilename(), c);
    }

    /** Prints the file name and the line number of the given tree node.
     *
     * Also increments semantic error count.
     *
     * @param filename the file name
     * @param t the tree node
     * @return a print stream to which the rest of the error message is
     * to be printed.
     *
     * */
    public PrintStream semantError(AbstractSymbol filename, TreeNode t) {
	errorStream.print(filename + ":" + t.getLineNumber() + ": ");
	return semantError();
    }

    /** Increments semantic error count and returns the print stream for
     * error messages.
     *
     * @return a print stream to which the error message is
     * to be printed.
     *
     * */
    public PrintStream semantError() {
	semantErrors++;
	return errorStream;
    }

    /** Returns true if there are any static semantic errors. */
    public boolean errors() {
	return semantErrors != 0;
    }


public boolean detectDuplicate() {
			  
 for (Enumeration e = cls.getElements();e.hasMoreElements(); ) {
     class_c element = (class_c)e.nextElement();
       int count = 0; 
    for (Enumeration ein = cls.getElements();ein.hasMoreElements(); ) {
     class_c elementin = (class_c)ein.nextElement();
       if (element.getName() == elementin.getName()) count++;
      }
    if (count > 1) return (false);
  }
return(true); 
}
/* Check that classes defined in this file has a parent in this file. Only works if no duplicate class */
public boolean validateParent() {

 for (Enumeration e = cls.getElements();e.hasMoreElements(); ) {
   boolean match = false;
   class_c element = (class_c)e.nextElement();
   AbstractSymbol parent = element.getParent();
 for (Enumeration ein = allClasses.getElements();ein.hasMoreElements(); ) {
     class_c elementin = (class_c)ein.nextElement();
     if (elementin.getName() == parent) match = true;
   }
  if (!match) return(match);
 }
  return(true); 
 }
}

