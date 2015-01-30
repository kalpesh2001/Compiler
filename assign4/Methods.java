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

public boolean validateSig(AbstractSymbol name, LinkedList<AbstractSymbol> input_params, AbstractSymbol ret,Node<AbstractSymbol> node) {
     LinkedList<AbstractSymbol> inlist = param.get(name);
     for (element : inlist) {
        for (element_input : input_params) {
           element_node = node.findNode(element);
           element_input_node = node.findNode(element_input);
         if (!element_node.typeJoin(element_input_node)) return(false); 
           }
       } 
  /*repeat for ret type */       
  }
/* Check existance of a method for a class. This is done to provide error message to end user */

public boolean methodExists(AbstractSymbol name) {
   if (param.containsKey(name) && rettype.containsKey(name)) return (true);
   else
   return(false);
}

public boolean checkCount(AbstractSymbol name, int count) {
   if (count != param.get(name).sizeof()) return (false) else
    return (true);
}

}
        
