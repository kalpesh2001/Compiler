import java.util.*;

public class Methods {

private HashMap<AbstractSymbol,LinkedList<AbstractSymbol>> param; 
private HashMap<AbstractSymbol,AbstractSymbol> rettype; 

public Methods() {
      param = new HashMap<AbstractSymbol,LinkedList<AbstractSymbol>>();
      rettype = new HashMap<AbstractSymbol,AbstractSymbol>();
  }

public boolean add(AbstractSymbol name, LinkedList<AbstractSymbol> params, AbstractSymbol ret) {
/* Procedure of same name exists */
       if (param.containsKey(name)) return (false);
       param.put(name,params);
       rettype.put(name,ret);
       return (true);
  }
/* This method validates signature. Relies on equal method of the list */

public boolean validateSig(AbstractSymbol name, LinkedList<AbstractSymbol> input_param, Node<AbstractSymbol> node) {
     LinkedList<AbstractSymbol> inlist = param.get(name);
     AbstractSymbol element,element_input;
     for (int i = 0;i < inlist.size();i++) {
           element = inlist.get(i);
           element_input = input_param.get(i);
           Node<AbstractSymbol> element_node = node.findNode(element);
           Node<AbstractSymbol> element_input_node = node.findNode(element_input);
         if (!element_input_node.typeConform(element_node)) return(false); 
        }
         return(true);
  }
/* Check existance of a method for a class. This is done to provide error message to end user */

public boolean methodExists(AbstractSymbol name) {
/*
   System.out.println("param has no method:" + param.containsKey(name) + name.toString());
   System.out.println("rettype has no method:" + rettype.containsKey(name) + name.toString());
*/
   if (param.containsKey(name) && rettype.containsKey(name)) return (true);
   else
   return(false);
}

public int checkCount(AbstractSymbol name) {
   return (param.get(name).size());
}

public AbstractSymbol findRet(AbstractSymbol name) {
   return (rettype.get(name));
  } 
}
        
