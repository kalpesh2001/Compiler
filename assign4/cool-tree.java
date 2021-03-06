// -*- mode: java -*- 
//
// file: cool-tree.m4
//
// This file defines the AST
//
//////////////////////////////////////////////////////////

import java.util.Enumeration;
import java.io.PrintStream;
import java.util.*;


/** Defines simple phylum Program */
abstract class Program extends TreeNode {
    protected Program(int lineNumber) {
        super(lineNumber);
    }
    public abstract void dump_with_types(PrintStream out, int n);
    public abstract void semant();

}


/** Defines simple phylum Class_ */
abstract class Class_ extends TreeNode {
    protected Class_(int lineNumber) {
        super(lineNumber);
    }
    public abstract void dump_with_types(PrintStream out, int n);

}


/** Defines list phylum Classes
    <p>
    See <a href="ListNode.html">ListNode</a> for full documentation. */
class Classes extends ListNode {
    public final static Class elementClass = Class_.class;
    /** Returns class of this lists's elements */
    public Class getElementClass() {
        return elementClass;
    }
    protected Classes(int lineNumber, Vector elements) {
        super(lineNumber, elements);
    }
    /** Creates an empty "Classes" list */
    public Classes(int lineNumber) {
        super(lineNumber);
    }
    /** Appends "Class_" element to this list */
    public Classes appendElement(TreeNode elem) {
        addElement(elem);
        return this;
    }
    public TreeNode copy() {
        return new Classes(lineNumber, copyElements());
    }
}


/** Defines simple phylum Feature */
abstract class Feature extends TreeNode {
    protected Feature(int lineNumber) {
        super(lineNumber);
    }
    public abstract void dump_with_types(PrintStream out, int n);
    public abstract void initialize(Node<AbstractSymbol> node);
    public abstract AbstractSymbol checkType(SymbolTable env,Node<AbstractSymbol>node,class_c aclass);
    public abstract void addAttr(SymbolTable env,Node<AbstractSymbol>node,class_c aclass);

}


/** Defines list phylum Features
    <p>
    See <a href="ListNode.html">ListNode</a> for full documentation. */
class Features extends ListNode {
    public final static Class elementClass = Feature.class;
    /** Returns class of this lists's elements */
    public Class getElementClass() {
        return elementClass;
    }
    protected Features(int lineNumber, Vector elements) {
        super(lineNumber, elements);
    }
    /** Creates an empty "Features" list */
    public Features(int lineNumber) {
        super(lineNumber);
    }
    /** Appends "Feature" element to this list */
    public Features appendElement(TreeNode elem) {
        addElement(elem);
        return this;
    }
    public TreeNode copy() {
        return new Features(lineNumber, copyElements());
    }
}


/** Defines simple phylum Formal */
abstract class Formal extends TreeNode {
    protected Formal(int lineNumber) {
        super(lineNumber);
    }
    public abstract void dump_with_types(PrintStream out, int n);

}


/** Defines list phylum Formals
    <p>
    See <a href="ListNode.html">ListNode</a> for full documentation. */
class Formals extends ListNode {
    public final static Class elementClass = Formal.class;
    /** Returns class of this lists's elements */
    public Class getElementClass() {
        return elementClass;
    }
    protected Formals(int lineNumber, Vector elements) {
        super(lineNumber, elements);
    }
    /** Creates an empty "Formals" list */
    public Formals(int lineNumber) {
        super(lineNumber);
    }
    /** Appends "Formal" element to this list */
    public Formals appendElement(TreeNode elem) {
        addElement(elem);
        return this;
    }
    public TreeNode copy() {
        return new Formals(lineNumber, copyElements());
    }
}


/** Defines simple phylum Expression */
abstract class Expression extends TreeNode {
    protected Expression(int lineNumber) {
        super(lineNumber);
    }
    private AbstractSymbol type = null;                                 
    public AbstractSymbol get_type() { return type; }           
    public Expression set_type(AbstractSymbol s) { type = s; return this; } 
    public abstract void dump_with_types(PrintStream out, int n);
    public void dump_type(PrintStream out, int n) {
        if (type != null)
            { out.println(Utilities.pad(n) + ": " + type.getString()); }
        else
            { out.println(Utilities.pad(n) + ": _no_type"); }
    }
   public abstract AbstractSymbol checkType(SymbolTable env,Node<AbstractSymbol> node,class_c aclass);
}


/** Defines list phylum Expressions
    <p>
    See <a href="ListNode.html">ListNode</a> for full documentation. */
class Expressions extends ListNode {
    public final static Class elementClass = Expression.class;
    /** Returns class of this lists's elements */
    public Class getElementClass() {
        return elementClass;
    }
    protected Expressions(int lineNumber, Vector elements) {
        super(lineNumber, elements);
    }
    /** Creates an empty "Expressions" list */
    public Expressions(int lineNumber) {
        super(lineNumber);
    }
    /** Appends "Expression" element to this list */
    public Expressions appendElement(TreeNode elem) {
        addElement(elem);
        return this;
    }
    public TreeNode copy() {
        return new Expressions(lineNumber, copyElements());
    }
}


/** Defines simple phylum Case */
abstract class Case extends TreeNode {
    protected Case(int lineNumber) {
        super(lineNumber);
    }
    public abstract void dump_with_types(PrintStream out, int n);
    public abstract AbstractSymbol checkType(SymbolTable env,Node<AbstractSymbol>node,class_c aclass);

}


/** Defines list phylum Cases
    <p>
    See <a href="ListNode.html">ListNode</a> for full documentation. */
class Cases extends ListNode {
    public final static Class elementClass = Case.class;
    /** Returns class of this lists's elements */
    public Class getElementClass() {
        return elementClass;
    }
    protected Cases(int lineNumber, Vector elements) {
        super(lineNumber, elements);
    }
    /** Creates an empty "Cases" list */
    public Cases(int lineNumber) {
        super(lineNumber);
    }
    /** Appends "Case" element to this list */
    public Cases appendElement(TreeNode elem) {
        addElement(elem);
        return this;
    }
    public TreeNode copy() {
        return new Cases(lineNumber, copyElements());
    }
}


/** Defines AST constructor 'programc'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class programc extends Program {
    protected Classes classes;
    /** Creates "programc" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for classes
      */
    public programc(int lineNumber, Classes a1) {
        super(lineNumber);
        classes = a1;
    }
    public TreeNode copy() {
        return new programc(lineNumber, (Classes)classes.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "programc\n");
        classes.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_program");
        for (Enumeration e = classes.getElements(); e.hasMoreElements(); ) {
            // sm: changed 'n + 1' to 'n + 2' to match changes elsewhere
	    ((Class_)e.nextElement()).dump_with_types(out, n + 2);
        }
    }
    /** This method is the entry point to the semantic checker.  You will
        need to complete it in programming assignment 4.
	<p>
        Your checker should do the following two things:
	<ol>
	<li>Check that the program is semantically correct
	<li>Decorate the abstract syntax tree with type information
        by setting the type field in each Expression node.
        (see tree.h)
	</ol>
	<p>
	You are free to first do (1) and make sure you catch all semantic
    	errors. Part (2) can be done in a second stage when you want
	to test the complete compiler.
    */
    public void semant() {
	/* ClassTable constructor may do some semantic analysis */
	ClassTable classTable = new ClassTable(classes);
        SymbolTable env = new SymbolTable();
        if (!classTable.detectDuplicate()) System.out.println("Found duplicates");
        if(!classTable.validateParent()) System.out.println("Some class has undeclared parent");
        Node<AbstractSymbol> node = classTable.getTree();
        Classes allClasses = classTable.getClasses();

        HashMap<AbstractSymbol,class_c> lookup  = new HashMap<AbstractSymbol,class_c>();
        for (Enumeration e = allClasses.getElements();e.hasMoreElements(); ) {
           class_c element = (class_c)e.nextElement(); 
           lookup.put(element.getName(),element);
        }
/* Code to add attributes for each class in hierarchy go here */
/* Find ancestors of a class and execute method to add its attributes to the symbol table */
/* addGlobal only calls for attributes */

     for (Enumeration e = classes.getElements();e.hasMoreElements(); ) { 
       class_c element = (class_c)e.nextElement(); 
       env.enterScope();
     LinkedList<Node<AbstractSymbol>> ancestors = node.ancestorList(node.findNode(element.getName()));
      for (Node<AbstractSymbol> eachname : ancestors) {
       lookup.get(eachname.getData()).addAttr(env,node,lookup.get(eachname.getData())); 
         if (lookup.get(eachname.getData()).errors()) {
	    System.err.println("Compilation halted due to static semantic errors.");
	    System.exit(1);
          }
       }
      element.checkType(env,node,element); 
/*
       We should pass Class table as parameter and also class as parameter. use following ideom to print error message.
       element.semantError().println("this is test for class");
*/
       env.exitScope();
       if (element.errors()) {
	 System.err.println("Compilation halted due to static semantic errors.");
	 System.exit(1);
       }
   }	

 }

}


/** Defines AST constructor 'class_c'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class class_c extends Class_ {
    protected AbstractSymbol name;
    protected AbstractSymbol parent;
    protected Features features;
    protected AbstractSymbol filename;
    private PrintStream errorStream = System.err;
    private int semantErrors = 0; 
    /** Creates "class_c" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for name
      * @param a1 initial value for parent
      * @param a2 initial value for features
      * @param a3 initial value for filename
      */
    public class_c(int lineNumber, AbstractSymbol a1, AbstractSymbol a2, Features a3, AbstractSymbol a4) {
        super(lineNumber);
        name = a1;
        parent = a2;
        features = a3;
        filename = a4;
    }
    public TreeNode copy() {
        return new class_c(lineNumber, copy_AbstractSymbol(name), copy_AbstractSymbol(parent), (Features)features.copy(), copy_AbstractSymbol(filename));
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "class_c\n");
        dump_AbstractSymbol(out, n+2, name);
        dump_AbstractSymbol(out, n+2, parent);
        features.dump(out, n+2);
        dump_AbstractSymbol(out, n+2, filename);
    }

    
    public AbstractSymbol getFilename() { return filename; }
    public AbstractSymbol getName()     { return name; }
    public AbstractSymbol getParent()   { return parent; }

    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_class");
        dump_AbstractSymbol(out, n + 2, name);
        dump_AbstractSymbol(out, n + 2, parent);
        out.print(Utilities.pad(n + 2) + "\"");
        Utilities.printEscapedString(out, filename.getString());
        out.println("\"\n" + Utilities.pad(n + 2) + "(");
        for (Enumeration e = features.getElements(); e.hasMoreElements();) {
	    ((Feature)e.nextElement()).dump_with_types(out, n + 2);
        }
        out.println(Utilities.pad(n + 2) + ")");
    }

  public PrintStream semantError() {
     return semantError((filename),this);
  }
  
  public PrintStream semantError(AbstractSymbol filename, TreeNode t) {
      errorStream.print(filename + ":" + t.getLineNumber() + ":" );
      semantErrors++;
      return(errorStream);
   }

  public boolean errors() {
    return (semantErrors != 0);
  }
  public void initialize(Node<AbstractSymbol> node) {
     for (Enumeration e = features.getElements(); e.hasMoreElements();) {
         ((Feature)e.nextElement()).initialize(node);
       }
    }
  public void addAttr(SymbolTable env,Node<AbstractSymbol>node,class_c aclass) {
     for (Enumeration e = features.getElements(); e.hasMoreElements();) {
         ((Feature)e.nextElement()).addAttr(env,node,aclass);
       }
    }
     
  public AbstractSymbol checkType(SymbolTable env,Node<AbstractSymbol> node,class_c aclass) {
      env.enterScope();
      env.addId(name,(Object)name);
      for (Enumeration e = features.getElements(); e.hasMoreElements();) {
	    ((Feature)e.nextElement()).checkType(env,node,aclass);
        }
/* If everything goes fine, return class name */
    env.exitScope();
    return(name);
  }
}


/** Defines AST constructor 'method'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class method extends Feature {
    protected AbstractSymbol name;
    protected Formals formals;
    protected AbstractSymbol return_type;
    protected Expression expr;
    /** Creates "method" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for name
      * @param a1 initial value for formals
      * @param a2 initial value for return_type
      * @param a3 initial value for expr
      */
    public method(int lineNumber, AbstractSymbol a1, Formals a2, AbstractSymbol a3, Expression a4) {
        super(lineNumber);
        name = a1;
        formals = a2;
        return_type = a3;
        expr = a4;
    }
    public TreeNode copy() {
        return new method(lineNumber, copy_AbstractSymbol(name), (Formals)formals.copy(), copy_AbstractSymbol(return_type), (Expression)expr.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "method\n");
        dump_AbstractSymbol(out, n+2, name);
        formals.dump(out, n+2);
        dump_AbstractSymbol(out, n+2, return_type);
        expr.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_method");
        dump_AbstractSymbol(out, n + 2, name);
        for (Enumeration e = formals.getElements(); e.hasMoreElements();) {
	    ((Formal)e.nextElement()).dump_with_types(out, n + 2);
        }
        dump_AbstractSymbol(out, n + 2, return_type);
	expr.dump_with_types(out, n + 2);
    }
  /* This procedure will add methods to global structure for later verification. Corrosponding mehtod for attr will do nothing */
 
  public void initialize(Node<AbstractSymbol> node) {
    boolean verify = true;
/*    if(node.findNode(return_type) == null) verify = false; */

     LinkedList<AbstractSymbol>methodparam = new LinkedList<AbstractSymbol>();
     for (Enumeration e = formals.getElements();e.hasMoreElements();) {
           AbstractSymbol paramtype = ((formalc)e.nextElement()).getMethodType(); 
           methodparam.add(paramtype);
/*
           if (node.findNode(paramtype) != null) 
              methodparam.add(paramtype);
           else verify = false;
*/
        }
     if (verify == true) {
     Methods methods = node.getMethod();
     methods.add(name,methodparam,return_type);
     System.out.println("Add method: to class: " + name.toString() + node.getData().toString());
     }
    else 
      System.out.println("Method not added: " + name.toString());
  }

  public void addAttr(SymbolTable env,Node<AbstractSymbol> node,class_c aclass) {
    }
   
  public AbstractSymbol checkType(SymbolTable env,Node<AbstractSymbol> node,class_c aClass) {
     boolean verify = true;
     env.enterScope();
/*
     env.addId(TreeConstants.self,(Object)(aClass.getName()));
*/
     env.addId(TreeConstants.self,(Object)(TreeConstants.SELF_TYPE));
     for (Enumeration e = formals.getElements();e.hasMoreElements();){
        AbstractSymbol parameter = ((formalc)e.nextElement()).checkType(env,node,aClass);
       if(parameter == TreeConstants.typeError) {
         env.exitScope();
         return(TreeConstants.typeError);
       }
     }
   
    if (expr.checkType(env,node,aClass) == TreeConstants.SELF_TYPE) 
      {
       env.exitScope();
        return(TreeConstants.SELF_TYPE);
     }
     Node<AbstractSymbol> ret_node= node.findNode(expr.checkType(env,node,aClass));
     Node<AbstractSymbol> formal_return = node.findNode(return_type); 
     if (ret_node == null || formal_return == null) verify = false;
     if (verify && (ret_node.typeConform(formal_return))) {
       env.exitScope();
       return(return_type);
       }
      else { 
      env.exitScope();
      return (TreeConstants.typeError);
      }
   }
 }

/** Defines AST constructor 'attr'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class attr extends Feature {
    protected AbstractSymbol name;
    protected AbstractSymbol type_decl;
    protected Expression init;
    /** Creates "attr" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for name
      * @param a1 initial value for type_decl
      * @param a2 initial value for init
      */
    public attr(int lineNumber, AbstractSymbol a1, AbstractSymbol a2, Expression a3) {
        super(lineNumber);
        name = a1;
        type_decl = a2;
        init = a3;
    }
    public TreeNode copy() {
        return new attr(lineNumber, copy_AbstractSymbol(name), copy_AbstractSymbol(type_decl), (Expression)init.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "attr\n");
        dump_AbstractSymbol(out, n+2, name);
        dump_AbstractSymbol(out, n+2, type_decl);
        init.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_attr");
        dump_AbstractSymbol(out, n + 2, name);
        dump_AbstractSymbol(out, n + 2, type_decl);
	init.dump_with_types(out, n + 2);
    }

  public void addAttr(SymbolTable env,Node<AbstractSymbol>node,class_c aClass) {

  /* System.out.println("Called to add: Type: variable Name " + type_decl.toString() + "......" + name.toString());*/
 
   if (name == TreeConstants.self) {
      aClass.semantError().println("Attribute 'self' cannot be added");
      return;
   }
   if(node.findNode(type_decl) != null) {
        if(env.lookup(name) != null) {
          aClass.semantError().println("Variable is already declared:" + name.getString());
         }
        else 
         {
          env.addId(name,(Object)type_decl);
        /*  System.out.println("Variable added: "+ name.getString());*/
         }
       }
   else 
      aClass.semantError().println("Type does not exist:"+ type_decl.toString());
   }
  public AbstractSymbol checkType(SymbolTable env,Node<AbstractSymbol> node,class_c aClass) {
/* Add check for type_decl to be a valid class */
   if(node.findNode(type_decl) == null) { 
    aClass.semantError().println("Class: " + type_decl.toString() + "of variable: " + name.toString() + "not declared");
     return (TreeConstants.typeError);
   }
    AbstractSymbol expr_ret = init.checkType(env,node,aClass);
     if(expr_ret == TreeConstants.No_type) return (type_decl);
     if(expr_ret == TreeConstants.typeError) return (TreeConstants.typeError);
    Node<AbstractSymbol> formal_node = node.findNode(type_decl);
    Node<AbstractSymbol> expr_node = node.findNode(expr_ret); 
     if (expr_node.typeConform(formal_node)) 
       return(expr_ret);
    else
       {
      aClass.semantError().println("Type of: " + name.toString() + " does not conform with: " + expr_ret.toString()); 
      return(TreeConstants.typeError);
      }
  }
  public void initialize(Node<AbstractSymbol> node) {
  }
}

/** Defines AST constructor 'formalc'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class formalc extends Formal {
    protected AbstractSymbol name;
    protected AbstractSymbol type_decl;
    /** Creates "formalc" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for name
      * @param a1 initial value for type_decl
      */
    public formalc(int lineNumber, AbstractSymbol a1, AbstractSymbol a2) {
        super(lineNumber);
        name = a1;
        type_decl = a2;
    }
    public TreeNode copy() {
        return new formalc(lineNumber, copy_AbstractSymbol(name), copy_AbstractSymbol(type_decl));
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "formalc\n");
        dump_AbstractSymbol(out, n+2, name);
        dump_AbstractSymbol(out, n+2, type_decl);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_formal");
        dump_AbstractSymbol(out, n + 2, name);
        dump_AbstractSymbol(out, n + 2, type_decl);
    }
   public AbstractSymbol getMethodType() {
       return (type_decl);
   }
  public AbstractSymbol checkType(SymbolTable env,Node<AbstractSymbol> node,class_c aClass) {
     if(type_decl == TreeConstants.SELF_TYPE) {
        aClass.semantError().println("Formal parameter cannot have SELF_TYPE");
        return(TreeConstants.typeError);
     }
     if(env.probe(name) != null || (node.findNode(type_decl) == null)) {
        aClass.semantError().println("Found method parameter already declared or declared type not found:" + name);
        return (TreeConstants.typeError);
       }
    else {
       env.addId(name,(Object)type_decl);
       /*System.out.println("Added method parameter: "+ name); */
       return(type_decl);
     }
   }
}


/** Defines AST constructor 'branch'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class branch extends Case {
    protected AbstractSymbol name;
    protected AbstractSymbol type_decl;
    protected Expression expr;
    /** Creates "branch" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for name
      * @param a1 initial value for type_decl
      * @param a2 initial value for expr
      */
    public branch(int lineNumber, AbstractSymbol a1, AbstractSymbol a2, Expression a3) {
        super(lineNumber);
        name = a1;
        type_decl = a2;
        expr = a3;
    }
    public TreeNode copy() {
        return new branch(lineNumber, copy_AbstractSymbol(name), copy_AbstractSymbol(type_decl), (Expression)expr.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "branch\n");
        dump_AbstractSymbol(out, n+2, name);
        dump_AbstractSymbol(out, n+2, type_decl);
        expr.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_branch");
        dump_AbstractSymbol(out, n + 2, name);
        dump_AbstractSymbol(out, n + 2, type_decl);
	expr.dump_with_types(out, n + 2);
    }
  public AbstractSymbol checkType(SymbolTable env,Node<AbstractSymbol> node,class_c aclass) {
   AbstractSymbol ret_type = TreeConstants.typeError;
   if(node.findNode(type_decl) != null) {
    env.addId(name,(Object)type_decl);
    ret_type = expr.checkType(env,node,aclass);
    return(ret_type);
    } 
   aclass.semantError().println("Type not declared in case branch: " + type_decl.toString());
   return(ret_type);
  }
}


/** Defines AST constructor 'assign'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class assign extends Expression {
    protected AbstractSymbol name;
    protected Expression expr;
    /** Creates "assign" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for name
      * @param a1 initial value for expr
      */
    public assign(int lineNumber, AbstractSymbol a1, Expression a2) {
        super(lineNumber);
        name = a1;
        expr = a2;
    }
    public TreeNode copy() {
        return new assign(lineNumber, copy_AbstractSymbol(name), (Expression)expr.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "assign\n");
        dump_AbstractSymbol(out, n+2, name);
        expr.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_assign");
        dump_AbstractSymbol(out, n + 2, name);
	expr.dump_with_types(out, n + 2);
	dump_type(out, n);
    }

  public AbstractSymbol checkType(SymbolTable env,Node<AbstractSymbol> node,class_c aclass) {
     AbstractSymbol formal_decl = (AbstractSymbol)env.lookup(name);
       if(formal_decl == TreeConstants.typeError || formal_decl == null) {
        set_type(TreeConstants.typeError);
        return(TreeConstants.typeError);
       }
    AbstractSymbol expr_ret = expr.checkType(env,node,aclass);
       if(expr_ret == TreeConstants.typeError) {
        set_type(TreeConstants.typeError);
         return (TreeConstants.typeError);
       }
    Node<AbstractSymbol> formal_node = node.findNode(formal_decl);
     Node<AbstractSymbol> expr_node = node.findNode(expr_ret); 
     if (expr_node.typeConform(formal_node)) {
       set_type(expr_ret); 
       return(expr_ret);
     }
    else
       {
      aclass.semantError().println("Type of: " + name.toString() + " does not conform with: " + expr_ret.toString()); 
        set_type(TreeConstants.typeError);
      return(TreeConstants.typeError);
      }
  }
}


/** Defines AST constructor 'static_dispatch'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class static_dispatch extends Expression {
    protected Expression expr;
    protected AbstractSymbol type_name;
    protected AbstractSymbol name;
    protected Expressions actual;
    /** Creates "static_dispatch" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for expr
      * @param a1 initial value for type_name
      * @param a2 initial value for name
      * @param a3 initial value for actual
      */
    public static_dispatch(int lineNumber, Expression a1, AbstractSymbol a2, AbstractSymbol a3, Expressions a4) {
        super(lineNumber);
        expr = a1;
        type_name = a2;
        name = a3;
        actual = a4;
    }
    public TreeNode copy() {
        return new static_dispatch(lineNumber, (Expression)expr.copy(), copy_AbstractSymbol(type_name), copy_AbstractSymbol(name), (Expressions)actual.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "static_dispatch\n");
        expr.dump(out, n+2);
        dump_AbstractSymbol(out, n+2, type_name);
        dump_AbstractSymbol(out, n+2, name);
        actual.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_static_dispatch");
	expr.dump_with_types(out, n + 2);
        dump_AbstractSymbol(out, n + 2, type_name);
        dump_AbstractSymbol(out, n + 2, name);
        out.println(Utilities.pad(n + 2) + "(");
        for (Enumeration e = actual.getElements(); e.hasMoreElements();) {
	    ((Expression)e.nextElement()).dump_with_types(out, n + 2);
        }
        out.println(Utilities.pad(n + 2) + ")");
	dump_type(out, n);
    }

  public AbstractSymbol checkType(SymbolTable env,Node<AbstractSymbol> node,class_c aclass) {
      AbstractSymbol called_class = expr.checkType(env,node,aclass);
      Node<AbstractSymbol> called_class_node = node.findNode(called_class);
      Node<AbstractSymbol> static_class_node = node.findNode(type_name);
     
     if (!called_class_node.typeConform(static_class_node)) {
       aclass.semantError().println("Type of invoked object: "+ called_class.toString() +" does not conform to static type: " + type_name.toString());
       set_type(TreeConstants.typeError);
       return(TreeConstants.typeError);
      }
         
    /* Find method matching name 
    /* for each parameter in the method make sure that supplied parameter Conforms to the method parameter.
    */
    AbstractSymbol ret_type;
    LinkedList<AbstractSymbol> param = new LinkedList<AbstractSymbol>(); 
    for (Enumeration e = actual.getElements(); e.hasMoreElements();) {
     ret_type = ((Expression)e.nextElement()).checkType(env,node,aclass);
      if (ret_type != TreeConstants.typeError) 
        param.add(ret_type); 
      else 
       {
     /*    System.out.println("One method parameter returned error: "  + name.toString() + ret_type.toString()); */
         set_type(TreeConstants.typeError);
         return(TreeConstants.typeError);
       }
      }
    LinkedList<Node<AbstractSymbol>> ancestors = node.ancestorList(static_class_node);
    Iterator<Node<AbstractSymbol>> reverse = ancestors.descendingIterator();

    while(reverse.hasNext()) {
    Node<AbstractSymbol> ancestor = reverse.next();
/*
    System.out.println("Trying to match methods for class:" + ancestor.getData()); 
*/
    Methods method_handle = ancestor.getMethod();

    if (method_handle.methodExists(name) && (method_handle.checkCount(name) == actual.getLength()) && method_handle.validateSig(name,param,node)) { 
         if (method_handle.findRet(name) == TreeConstants.SELF_TYPE) {
            set_type(type_name); /* changed to type name as it will be higher in hierarchy than called class. Class class conforms to static class */
            return(type_name);
         }
        else {
          set_type(method_handle.findRet(name));
          return(method_handle.findRet(name));
         }
      }
    }
    aclass.semantError().println("No matching method:" +" " + name.toString()+ " " + "found in the class hierarchy");
    set_type(TreeConstants.typeError);
    return(TreeConstants.typeError);
  }
}


/** Defines AST constructor 'dispatch'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class dispatch extends Expression {
    protected Expression expr;
    protected AbstractSymbol name;
    protected Expressions actual;
    /** Creates "dispatch" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for expr
      * @param a1 initial value for name
      * @param a2 initial value for actual
      */
    public dispatch(int lineNumber, Expression a1, AbstractSymbol a2, Expressions a3) {
        super(lineNumber);
        expr = a1;
        name = a2;
        actual = a3;
    }
    public TreeNode copy() {
        return new dispatch(lineNumber, (Expression)expr.copy(), copy_AbstractSymbol(name), (Expressions)actual.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "dispatch\n");
        expr.dump(out, n+2);
        dump_AbstractSymbol(out, n+2, name);
        actual.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_dispatch");
	expr.dump_with_types(out, n + 2);
        dump_AbstractSymbol(out, n + 2, name);
        out.println(Utilities.pad(n + 2) + "(");
        for (Enumeration e = actual.getElements(); e.hasMoreElements();) {
	    ((Expression)e.nextElement()).dump_with_types(out, n + 2);
        }
        out.println(Utilities.pad(n + 2) + ")");
	dump_type(out, n);
    }

  public AbstractSymbol checkType(SymbolTable env,Node<AbstractSymbol> node,class_c aclass) {
      AbstractSymbol called_class = expr.checkType(env,node,aclass);
      System.out.println("Return from expression: " + called_class.toString());
     if (called_class == TreeConstants.SELF_TYPE) 
         called_class = aclass.getName();
      if (called_class == TreeConstants.typeError) {
      set_type(TreeConstants.typeError);
      return(TreeConstants.typeError);
      }
     
    /* Find method matching name 
    /* for each parameter in the method make sure that supplied parameter Conforms to the method parameter.
    */
    AbstractSymbol ret_type;
    LinkedList<AbstractSymbol> param = new LinkedList<AbstractSymbol>(); 
    for (Enumeration e = actual.getElements(); e.hasMoreElements();) {
       ret_type = ((Expression)e.nextElement()).checkType(env,node,aclass);
        if (ret_type != TreeConstants.typeError) 
          param.add(ret_type); 
        else 
          {
            /*System.out.println("One method parameter returned error: "  + name.toString() + ret_type.toString()); */
            set_type(TreeConstants.typeError);
            return(TreeConstants.typeError);
         }
      }
    Node<AbstractSymbol> called_node = node.findNode(called_class);
    LinkedList<Node<AbstractSymbol>> ancestors = node.ancestorList(called_node);
    Iterator<Node<AbstractSymbol>> reverse = ancestors.descendingIterator();

    while(reverse.hasNext()) {
    Node<AbstractSymbol> ancestor = reverse.next();
/*
    System.out.println("Trying to match methods for class:" + ancestor.getData()); 
*/
    Methods method_handle = ancestor.getMethod();

    if (method_handle.methodExists(name) && (method_handle.checkCount(name) == actual.getLength()) && method_handle.validateSig(name,param,node)) { 
         if (method_handle.findRet(name) == TreeConstants.SELF_TYPE) {
            set_type(called_class);
            return(called_class);
         }
        else {
          set_type(method_handle.findRet(name));
          return(method_handle.findRet(name));
         }
      }
    }
    aclass.semantError().println("No matching method:" +" " + name.toString()+ " " + "found in the class hierarchy");
    set_type(TreeConstants.typeError);
    return(TreeConstants.typeError);
  }
}


/** Defines AST constructor 'cond'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class cond extends Expression {
    protected Expression pred;
    protected Expression then_exp;
    protected Expression else_exp;
    /** Creates "cond" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for pred
      * @param a1 initial value for then_exp
      * @param a2 initial value for else_exp
      */
    public cond(int lineNumber, Expression a1, Expression a2, Expression a3) {
        super(lineNumber);
        pred = a1;
        then_exp = a2;
        else_exp = a3;
    }
    public TreeNode copy() {
        return new cond(lineNumber, (Expression)pred.copy(), (Expression)then_exp.copy(), (Expression)else_exp.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "cond\n");
        pred.dump(out, n+2);
        then_exp.dump(out, n+2);
        else_exp.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_cond");
	pred.dump_with_types(out, n + 2);
	then_exp.dump_with_types(out, n + 2);
	else_exp.dump_with_types(out, n + 2);
	dump_type(out, n);
    }
  public AbstractSymbol checkType(SymbolTable env,Node<AbstractSymbol> node,class_c aclass) {
      if (pred.checkType(env,node,aclass) != TreeConstants.Bool) return(TreeConstants.typeError);
        AbstractSymbol then_type = then_exp.checkType(env,node,aclass);
        AbstractSymbol else_type = else_exp.checkType(env,node,aclass);
        if (then_type == TreeConstants.typeError || else_type == TreeConstants.typeError) {
            set_type(TreeConstants.typeError);
            return(TreeConstants.typeError);
         }     
        if (then_type == else_type) {
         set_type(then_type);
         return (then_type);
        }
        LinkedList<Node<AbstractSymbol>> cond = new LinkedList<Node<AbstractSymbol>>();
        Node<AbstractSymbol> then_type_node = node.findNode(then_type);
        cond.add(then_type_node);
        Node<AbstractSymbol> else_type_node = node.findNode(else_type);
        cond.add(else_type_node);
        set_type(node.typeJoin(cond).getData());
        return(node.typeJoin(cond).getData());
    }
        
}


/** Defines AST constructor 'loop'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class loop extends Expression {
    protected Expression pred;
    protected Expression body;
    /** Creates "loop" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for pred
      * @param a1 initial value for body
      */
    public loop(int lineNumber, Expression a1, Expression a2) {
        super(lineNumber);
        pred = a1;
        body = a2;
    }
    public TreeNode copy() {
        return new loop(lineNumber, (Expression)pred.copy(), (Expression)body.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "loop\n");
        pred.dump(out, n+2);
        body.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_loop");
	pred.dump_with_types(out, n + 2);
	body.dump_with_types(out, n + 2);
	dump_type(out, n);
    }
  public AbstractSymbol checkType(SymbolTable env,Node<AbstractSymbol> node,class_c aclass) {
    if (pred.checkType(env,node,aclass) != TreeConstants.Bool) { 
      aclass.semantError().println("Predicate for while loop does not return boolean"); 
      set_type(TreeConstants.typeError);
      return(TreeConstants.typeError);
     }
    if (body.checkType(env,node,aclass) != TreeConstants.typeError) {
   /*   System.out.println("Body for while loop has error");  */
      set_type(TreeConstants.typeError);
      return(TreeConstants.typeError);
     }
    else {
      set_type(TreeConstants.Object_);
      return(TreeConstants.Object_);
    } 
  }

}


/** Defines AST constructor 'typcase'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class typcase extends Expression {
    protected Expression expr;
    protected Cases cases;
    /** Creates "typcase" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for expr
      * @param a1 initial value for cases
      */
    public typcase(int lineNumber, Expression a1, Cases a2) {
        super(lineNumber);
        expr = a1;
        cases = a2;
    }
    public TreeNode copy() {
        return new typcase(lineNumber, (Expression)expr.copy(), (Cases)cases.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "typcase\n");
        expr.dump(out, n+2);
        cases.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_typcase");
	expr.dump_with_types(out, n + 2);
        for (Enumeration e = cases.getElements(); e.hasMoreElements();) {
	    ((Case)e.nextElement()).dump_with_types(out, n + 2);
        }
	dump_type(out, n);
    }

  public AbstractSymbol checkType(SymbolTable env,Node<AbstractSymbol> node,class_c aclass) {
    if (expr.checkType(env,node,aclass) == TreeConstants.typeError) {
       set_type(TreeConstants.typeError);
      return(TreeConstants.typeError);
   }
   LinkedList<Node<AbstractSymbol>>casetype = new LinkedList<Node<AbstractSymbol>>();
   AbstractSymbol ret_type;
   env.enterScope();
   for (Enumeration e = cases.getElements(); e.hasMoreElements();) {
	   ret_type =  ((Case)e.nextElement()).checkType(env,node,aclass);
      if (ret_type == TreeConstants.typeError) {
        env.exitScope();
        set_type(TreeConstants.typeError);
        return(TreeConstants.typeError);
       }
       casetype.add(node.findNode(ret_type));
     }
      env.exitScope();
     set_type(node.typeJoin(casetype).getData());
     return(node.typeJoin(casetype).getData());
    }
   
 }


/** Defines AST constructor 'block'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class block extends Expression {
    protected Expressions body;
    /** Creates "block" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for body
      */
    public block(int lineNumber, Expressions a1) {
        super(lineNumber);
        body = a1;
    }
    public TreeNode copy() {
        return new block(lineNumber, (Expressions)body.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "block\n");
        body.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_block");
        for (Enumeration e = body.getElements(); e.hasMoreElements();) {
	    ((Expression)e.nextElement()).dump_with_types(out, n + 2);
        }
	dump_type(out, n);
    }

  public AbstractSymbol checkType(SymbolTable env,Node<AbstractSymbol> node,class_c aclass) {
/*
      Vector<Expression> all_expr = body.copyElements();
      set_type(all_expr.lastElement().checkType(env,node));
      return(all_expr.lastElement().checkType(env,node));
*/
      AbstractSymbol ret = TreeConstants.typeError;
   for (Enumeration e = body.getElements(); e.hasMoreElements();) {
	   ret =  ((Expression)e.nextElement()).checkType(env,node,aclass);
           
    }
   set_type(ret);
   return(ret);
   }
}


/** Defines AST constructor 'let'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class let extends Expression {
    protected AbstractSymbol identifier;
    protected AbstractSymbol type_decl;
    protected Expression init;
    protected Expression body;
    /** Creates "let" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for identifier
      * @param a1 initial value for type_decl
      * @param a2 initial value for init
      * @param a3 initial value for body
      */
    public let(int lineNumber, AbstractSymbol a1, AbstractSymbol a2, Expression a3, Expression a4) {
        super(lineNumber);
        identifier = a1;
        type_decl = a2;
        init = a3;
        body = a4;
    }
    public TreeNode copy() {
        return new let(lineNumber, copy_AbstractSymbol(identifier), copy_AbstractSymbol(type_decl), (Expression)init.copy(), (Expression)body.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "let\n");
        dump_AbstractSymbol(out, n+2, identifier);
        dump_AbstractSymbol(out, n+2, type_decl);
        init.dump(out, n+2);
        body.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_let");
	dump_AbstractSymbol(out, n + 2, identifier);
	dump_AbstractSymbol(out, n + 2, type_decl);
	init.dump_with_types(out, n + 2);
	body.dump_with_types(out, n + 2);
	dump_type(out, n);
    }
  public AbstractSymbol checkType(SymbolTable env,Node<AbstractSymbol> node,class_c aclass) {
    AbstractSymbol ret_type = init.checkType(env,node,aclass);
    if(ret_type != TreeConstants.No_type) {
     Node<AbstractSymbol> ret_node= node.findNode(ret_type);
     Node<AbstractSymbol> formal_decl = node.findNode(type_decl); 
     if (ret_node.typeConform(formal_decl)) {
       env.enterScope();
       env.addId(identifier,(Object)type_decl);
      }
    else 
       {
      set_type(TreeConstants.typeError);
      aclass.semantError().println("Type of initialization does not conform to declared type: " + ret_type.toString() + " " + type_decl.toString());
      return(TreeConstants.typeError);
       }
   }
    else {
        env.enterScope();
        env.addId(identifier,(Object)type_decl);
     }
    AbstractSymbol return_type = body.checkType(env,node,aclass);
   if (return_type == TreeConstants.typeError) {
      env.exitScope();
      set_type(TreeConstants.typeError);
      return(TreeConstants.typeError);
    }
   else {
       env.exitScope();
      set_type(return_type);
       return(return_type);
    }  
     
 } 
}


/** Defines AST constructor 'plus'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class plus extends Expression {
    protected Expression e1;
    protected Expression e2;
    /** Creates "plus" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for e1
      * @param a1 initial value for e2
      */
    public plus(int lineNumber, Expression a1, Expression a2) {
        super(lineNumber);
        e1 = a1;
        e2 = a2;
    }
    public TreeNode copy() {
        return new plus(lineNumber, (Expression)e1.copy(), (Expression)e2.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "plus\n");
        e1.dump(out, n+2);
        e2.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_plus");
	e1.dump_with_types(out, n + 2);
	e2.dump_with_types(out, n + 2);
	dump_type(out, n);
    }
  public AbstractSymbol checkType(SymbolTable env,Node<AbstractSymbol> node,class_c aclass) {
       if (e1.checkType(env,node,aclass) == TreeConstants.Int && e2.checkType(env,node,aclass) == TreeConstants.Int) {
           set_type(TreeConstants.Int); 
           return (TreeConstants.Int);
        }
       else {
         aclass.semantError().println("plus has expression other than Integer:");
           set_type(TreeConstants.typeError); 
         return(TreeConstants.typeError);
      }
   }

}


/** Defines AST constructor 'sub'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class sub extends Expression {
    protected Expression e1;
    protected Expression e2;
    /** Creates "sub" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for e1
      * @param a1 initial value for e2
      */
    public sub(int lineNumber, Expression a1, Expression a2) {
        super(lineNumber);
        e1 = a1;
        e2 = a2;
    }
    public TreeNode copy() {
        return new sub(lineNumber, (Expression)e1.copy(), (Expression)e2.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "sub\n");
        e1.dump(out, n+2);
        e2.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_sub");
	e1.dump_with_types(out, n + 2);
	e2.dump_with_types(out, n + 2);
	dump_type(out, n);
    }
  public AbstractSymbol checkType(SymbolTable env,Node<AbstractSymbol> node,class_c aclass) {
       if (e1.checkType(env,node,aclass) == TreeConstants.Int && e2.checkType(env,node,aclass) == TreeConstants.Int) {
           set_type(TreeConstants.Int); 
           return (TreeConstants.Int);
        }
       else {
         aclass.semantError().println("substraction has expression other than Integer:");
           set_type(TreeConstants.typeError); 
         return(TreeConstants.typeError);
      }
   }

}


/** Defines AST constructor 'mul'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class mul extends Expression {
    protected Expression e1;
    protected Expression e2;
    /** Creates "mul" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for e1
      * @param a1 initial value for e2
      */
    public mul(int lineNumber, Expression a1, Expression a2) {
        super(lineNumber);
        e1 = a1;
        e2 = a2;
    }
    public TreeNode copy() {
        return new mul(lineNumber, (Expression)e1.copy(), (Expression)e2.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "mul\n");
        e1.dump(out, n+2);
        e2.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_mul");
	e1.dump_with_types(out, n + 2);
	e2.dump_with_types(out, n + 2);
	dump_type(out, n);
    }

  public AbstractSymbol checkType(SymbolTable env,Node<AbstractSymbol> node,class_c aclass) {
       if (e1.checkType(env,node,aclass) == TreeConstants.Int && e2.checkType(env,node,aclass) == TreeConstants.Int) {
           set_type(TreeConstants.Int); 
           return (TreeConstants.Int);
        }
       else {
         aclass.semantError().println("Multiplication has expression other than Integer:");
           set_type(TreeConstants.typeError); 
         return(TreeConstants.typeError);
      }
   }
}


/** Defines AST constructor 'divide'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class divide extends Expression {
    protected Expression e1;
    protected Expression e2;
    /** Creates "divide" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for e1
      * @param a1 initial value for e2
      */
    public divide(int lineNumber, Expression a1, Expression a2) {
        super(lineNumber);
        e1 = a1;
        e2 = a2;
    }
    public TreeNode copy() {
        return new divide(lineNumber, (Expression)e1.copy(), (Expression)e2.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "divide\n");
        e1.dump(out, n+2);
        e2.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_divide");
	e1.dump_with_types(out, n + 2);
	e2.dump_with_types(out, n + 2);
	dump_type(out, n);
    }
  public AbstractSymbol checkType(SymbolTable env,Node<AbstractSymbol> node,class_c aclass) {
       if (e1.checkType(env,node,aclass) == TreeConstants.Int && e2.checkType(env,node,aclass) == TreeConstants.Int) {
           set_type(TreeConstants.Int);
           return (TreeConstants.Int);
        }
       else {
         aclass.semantError().println("divide has expression other than Integer:");
         return(TreeConstants.typeError);
      }
   }

}


/** Defines AST constructor 'neg'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class neg extends Expression {
    protected Expression e1;
    /** Creates "neg" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for e1
      */
    public neg(int lineNumber, Expression a1) {
        super(lineNumber);
        e1 = a1;
    }
    public TreeNode copy() {
        return new neg(lineNumber, (Expression)e1.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "neg\n");
        e1.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_neg");
	e1.dump_with_types(out, n + 2);
	dump_type(out, n);
    }
  public AbstractSymbol checkType(SymbolTable env,Node<AbstractSymbol> node,class_c aclass) {
       if (e1.checkType(env,node,aclass) == TreeConstants.Int) {
           set_type(TreeConstants.Int);
           return (TreeConstants.Int);
        }
       else {
         aclass.semantError().println("Negative has expression other than Integer:");
         set_type(TreeConstants.typeError);
         return(TreeConstants.typeError);
      }
   }

}


/** Defines AST constructor 'lt'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class lt extends Expression {
    protected Expression e1;
    protected Expression e2;
    /** Creates "lt" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for e1
      * @param a1 initial value for e2
      */
    public lt(int lineNumber, Expression a1, Expression a2) {
        super(lineNumber);
        e1 = a1;
        e2 = a2;
    }
    public TreeNode copy() {
        return new lt(lineNumber, (Expression)e1.copy(), (Expression)e2.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "lt\n");
        e1.dump(out, n+2);
        e2.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_lt");
	e1.dump_with_types(out, n + 2);
	e2.dump_with_types(out, n + 2);
	dump_type(out, n);
    }

  public AbstractSymbol checkType(SymbolTable env,Node<AbstractSymbol> node,class_c aclass) {
       if (e1.checkType(env,node,aclass) == TreeConstants.Int && e2.checkType(env,node,aclass) == TreeConstants.Int) {
           set_type(TreeConstants.Bool);
           return (TreeConstants.Bool);
         }
       else {
         aclass.semantError().println("less than has expression other than Integer:");
         set_type(TreeConstants.typeError);
         return(TreeConstants.typeError);
      }
   }
}


/** Defines AST constructor 'eq'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class eq extends Expression {
    protected Expression e1;
    protected Expression e2;
    /** Creates "eq" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for e1
      * @param a1 initial value for e2
      */
    public eq(int lineNumber, Expression a1, Expression a2) {
        super(lineNumber);
        e1 = a1;
        e2 = a2;
    }
    public TreeNode copy() {
        return new eq(lineNumber, (Expression)e1.copy(), (Expression)e2.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "eq\n");
        e1.dump(out, n+2);
        e2.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_eq");
	e1.dump_with_types(out, n + 2);
	e2.dump_with_types(out, n + 2);
	dump_type(out, n);
    }
  public AbstractSymbol checkType(SymbolTable env,Node<AbstractSymbol> node,class_c aclass) {
        AbstractSymbol ret1 = e1.checkType(env,node,aclass); 
        AbstractSymbol ret2 = e2.checkType(env,node,aclass); 
      
       if ((ret1 == TreeConstants.Bool && ret2 != TreeConstants.Bool) || 
          (ret1 == TreeConstants.Int && ret2 != TreeConstants.Int) || 
          (ret1 == TreeConstants.Str && ret2 != TreeConstants.Str)) {
           aclass.semantError().println("Incompatible types for eq: ");  
          set_type(TreeConstants.typeError);
           return (TreeConstants.typeError);
         }
       else {
         set_type(TreeConstants.Bool);
         return(TreeConstants.Bool);
      }
   }

}


/** Defines AST constructor 'leq'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class leq extends Expression {
    protected Expression e1;
    protected Expression e2;
    /** Creates "leq" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for e1
      * @param a1 initial value for e2
      */
    public leq(int lineNumber, Expression a1, Expression a2) {
        super(lineNumber);
        e1 = a1;
        e2 = a2;
    }
    public TreeNode copy() {
        return new leq(lineNumber, (Expression)e1.copy(), (Expression)e2.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "leq\n");
        e1.dump(out, n+2);
        e2.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_leq");
	e1.dump_with_types(out, n + 2);
	e2.dump_with_types(out, n + 2);
	dump_type(out, n);
    }

  public AbstractSymbol checkType(SymbolTable env,Node<AbstractSymbol> node,class_c aclass) {
       if (e1.checkType(env,node,aclass) == TreeConstants.Int && e2.checkType(env,node,aclass) == TreeConstants.Int) {
           set_type(TreeConstants.Bool);
            return (TreeConstants.Bool);
         }
       else {
         aclass.semantError().println("leq has expression other than Integer:");
           set_type(TreeConstants.typeError);
         return(TreeConstants.typeError);
      }
   }
}


/** Defines AST constructor 'comp'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class comp extends Expression {
    protected Expression e1;
    /** Creates "comp" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for e1
      */
    public comp(int lineNumber, Expression a1) {
        super(lineNumber);
        e1 = a1;
    }
    public TreeNode copy() {
        return new comp(lineNumber, (Expression)e1.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "comp\n");
        e1.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_comp");
	e1.dump_with_types(out, n + 2);
	dump_type(out, n);
    }
  public AbstractSymbol checkType(SymbolTable env,Node<AbstractSymbol> node,class_c aclass) {
       if (e1.checkType(env,node,aclass) == TreeConstants.Bool) {
           set_type(TreeConstants.Bool);
           return (TreeConstants.Bool);
        }
       else {
         aclass.semantError().println("Complement has expression other than boolean:");
         set_type(TreeConstants.typeError);
         return(TreeConstants.typeError);
      }
   }

}


/** Defines AST constructor 'int_const'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class int_const extends Expression {
    protected AbstractSymbol token;
    /** Creates "int_const" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for token
      */
    public int_const(int lineNumber, AbstractSymbol a1) {
        super(lineNumber);
        token = a1;
    }
    public TreeNode copy() {
        return new int_const(lineNumber, copy_AbstractSymbol(token));
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "int_const\n");
        dump_AbstractSymbol(out, n+2, token);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_int");
	dump_AbstractSymbol(out, n + 2, token);
	dump_type(out, n);
    }

  public AbstractSymbol checkType(SymbolTable env,Node<AbstractSymbol> node,class_c aclass) {
        set_type(TreeConstants.Int);
         return(TreeConstants.Int);
   }
}


/** Defines AST constructor 'bool_const'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class bool_const extends Expression {
    protected Boolean val;
    /** Creates "bool_const" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for val
      */
    public bool_const(int lineNumber, Boolean a1) {
        super(lineNumber);
        val = a1;
    }
    public TreeNode copy() {
        return new bool_const(lineNumber, copy_Boolean(val));
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "bool_const\n");
        dump_Boolean(out, n+2, val);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_bool");
	dump_Boolean(out, n + 2, val);
	dump_type(out, n);
    }
  public AbstractSymbol checkType(SymbolTable env,Node<AbstractSymbol> node,class_c aclass) {
         set_type(TreeConstants.Bool);
         return(TreeConstants.Bool);
   }

}


/** Defines AST constructor 'string_const'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class string_const extends Expression {
    protected AbstractSymbol token;
    /** Creates "string_const" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for token
      */
    public string_const(int lineNumber, AbstractSymbol a1) {
        super(lineNumber);
        token = a1;
    }
    public TreeNode copy() {
        return new string_const(lineNumber, copy_AbstractSymbol(token));
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "string_const\n");
        dump_AbstractSymbol(out, n+2, token);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_string");
	out.print(Utilities.pad(n + 2) + "\"");
	Utilities.printEscapedString(out, token.getString());
	out.println("\"");
	dump_type(out, n);
    }

  public AbstractSymbol checkType(SymbolTable env,Node<AbstractSymbol> node,class_c aclass) {
         set_type(TreeConstants.Str);
         return(TreeConstants.Str);
   }
}


/** Defines AST constructor 'new_'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class new_ extends Expression {
    protected AbstractSymbol type_name;
    /** Creates "new_" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for type_name
      */
    public new_(int lineNumber, AbstractSymbol a1) {
        super(lineNumber);
        type_name = a1;
    }
    public TreeNode copy() {
        return new new_(lineNumber, copy_AbstractSymbol(type_name));
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "new_\n");
        dump_AbstractSymbol(out, n+2, type_name);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_new");
	dump_AbstractSymbol(out, n + 2, type_name);
	dump_type(out, n);
    }

  public AbstractSymbol checkType(SymbolTable env,Node<AbstractSymbol> node,class_c aclass) {
         if (node.findNode(type_name) != null ) {
           set_type(type_name);
            return(type_name);
        }
         else {
            aclass.semantError().println("Type not found in new: " + type_name.toString()); 
           set_type(TreeConstants.typeError);
            return(TreeConstants.typeError);
        }
    }
}


/** Defines AST constructor 'isvoid'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class isvoid extends Expression {
    protected Expression e1;
    /** Creates "isvoid" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for e1
      */
    public isvoid(int lineNumber, Expression a1) {
        super(lineNumber);
        e1 = a1;
    }
    public TreeNode copy() {
        return new isvoid(lineNumber, (Expression)e1.copy());
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "isvoid\n");
        e1.dump(out, n+2);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_isvoid");
	e1.dump_with_types(out, n + 2);
	dump_type(out, n);
    }

  public AbstractSymbol checkType(SymbolTable env,Node<AbstractSymbol> node,class_c aclass) {
         set_type(TreeConstants.Bool);
         return(TreeConstants.Bool);
  }

}


/** Defines AST constructor 'no_expr'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class no_expr extends Expression {
    /** Creates "no_expr" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      */
    public no_expr(int lineNumber) {
        super(lineNumber);
    }
    public TreeNode copy() {
        return new no_expr(lineNumber);
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "no_expr\n");
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_no_expr");
	dump_type(out, n);
    }

  public AbstractSymbol checkType(SymbolTable env,Node<AbstractSymbol> node,class_c aclass) {
         set_type(TreeConstants.No_type);
         return(TreeConstants.No_type);
   }
}


/** Defines AST constructor 'object'.
    <p>
    See <a href="TreeNode.html">TreeNode</a> for full documentation. */
class object extends Expression {
    protected AbstractSymbol name;
    /** Creates "object" AST node. 
      *
      * @param lineNumber the line in the source file from which this node came.
      * @param a0 initial value for name
      */
    public object(int lineNumber, AbstractSymbol a1) {
        super(lineNumber);
        name = a1;
    }
    public TreeNode copy() {
        return new object(lineNumber, copy_AbstractSymbol(name));
    }
    public void dump(PrintStream out, int n) {
        out.print(Utilities.pad(n) + "object\n");
        dump_AbstractSymbol(out, n+2, name);
    }

    
    public void dump_with_types(PrintStream out, int n) {
        dump_line(out, n);
        out.println(Utilities.pad(n) + "_object");
	dump_AbstractSymbol(out, n + 2, name);
	dump_type(out, n);
    }

  public AbstractSymbol checkType(SymbolTable env,Node<AbstractSymbol> node,class_c aclass) {
      if (env.lookup(name) != null) {
          set_type((AbstractSymbol)env.lookup(name));
          return((AbstractSymbol)env.lookup(name));
       }
      else
       {
          aclass.semantError().println("Object named: " + name + "cannot be found");
          set_type(TreeConstants.typeError);
          return(TreeConstants.typeError);
       }
   }

}


