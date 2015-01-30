import java.util.*;
public class Node<T> {

    T data;
    Node<T> parent;
    List<Node<T>> children;
    Methods methods;

    public Node(T data) {
        this.data = data;
        this.children = new LinkedList<Node<T>>();
        this.methods = new Methods();
    }

    public Node<T> addChild(T child) {
        Node<T> childNode = new Node<T>(child);
        childNode.parent = this;
        this.children.add(childNode);
        return childNode;
    }
    public T getData() {
      return this.data;
   }
   public Methods getMethod() {
        return this.methods;
  }

 private void allChildren(HashSet<Node<T>> input) {
   for (Node<T> e : this.children) {
       input.add(e); 
       e.allChildren(input);
     }
  }
    

   public void printtree() {
    for (Node<T> e : children) {
       System.out.println("Node: "+ e.getData().toString());
       e.printtree();
   }
  }
/* is Parameter parent of This node. this <= Parameter*/

public boolean typeConform(Node<T> type) {
     Node<T> temp = this.parent;
     if (this == type) return true;
     while (temp != null) {
        if (temp == type) 
             return true;     
         else
             temp  = temp.parent;
        }
       return false;
   }
/* Find common ancestor for nodes in the linked list */
/* Use of hashset should remove duplicate. Does that produce error? */
          
 public Node<T> typeJoin(LinkedList<Node<T>> types) {
   if (types.size() ==1)  
      return(types.getFirst());

   for (Node<T> e: types) 
     if (e.parent == null) return (e);
     
   HashSet<Node<T>> original = new HashSet<Node<T>>(types);
   HashSet<Node<T>> temp =  new HashSet<Node<T>>();
    Node<T> arbitrary = types.getFirst(); 
    Node<T> saveNode;
    do {
        HashSet<Node<T>> param = new HashSet<Node<T>>();      
        arbitrary.allChildren(param);
         temp.addAll(param);
/*
       for (Node<T> e: temp)
        System.out.println("For Node: Content: "+ arbitrary.getData().toString() +" " + e.next().getData().toString()); 
*/
        saveNode = arbitrary;
         if (arbitrary.parent == null) {
           return (arbitrary);
         }
           arbitrary = arbitrary.parent;
      }
    while (!temp.containsAll(original)); 
   return(saveNode);
}
/*Takes class name as parameter and checks for existance of class */

public boolean exists(T child) {

if (this.data == child) return (true);

 ListIterator<Node<T>> coolIterator = children.listIterator();
  while(coolIterator.hasNext()){
  Node<T> element = coolIterator.next();
  if (element.data == child) 
    return(true);
 System.out.println("Node: "+ element.getData().toString());
   return(element.exists(element.data));
 }
 return(false);
}

/* Given name of node, returns the node. Only checks in this and all childeren and so on. Make sure to supply root if you want to check from root down. */
public Node<T> findNode(T child) {

if (data == child)  
   return (this);

 HashSet<Node<T>> e = new HashSet<Node<T>>();
 this.allChildren(e);
  for (Node<T> element : e) { 
     if (element.data == child) return (element);
   }
  return (null);
}

/* Given a node, give all its ancestors in reverse order. Root first. */
/* Chnage on 1/26: Add input as one of the ancestor */

public LinkedList<Node<T>> ancestorList(Node<T> type) {
   LinkedList<Node<T>> ancList = new LinkedList<Node<T>>();
   ancList.add(type);
   if (type.parent == null) return (ancList);  
     Node<T> ancestor = type.parent;
 do {
     ancList.add(ancestor);
     ancestor = ancestor.parent;
   } while (ancestor != null);
 Iterator<Node<T>> reverse = ancList.descendingIterator();
 LinkedList<Node<T>> retList = new LinkedList<Node<T>>();
   while(reverse.hasNext())  
      retList.add(reverse.next());
   return(retList);
}
 
/*
public static void main(String args[]) {
   AbstractSymbol root = AbstractTable.stringtable.addString("ROOT");
   AbstractSymbol leaf1 = AbstractTable.stringtable.addString("leaf1");
   AbstractSymbol leaf2 = AbstractTable.stringtable.addString("leaf2");
   AbstractSymbol leaf3 = AbstractTable.stringtable.addString("leaf3");
   AbstractSymbol leaf4 = AbstractTable.stringtable.addString("leaf4");
   AbstractSymbol leaf5 = AbstractTable.stringtable.addString("leaf5");
   AbstractSymbol leaf6 = AbstractTable.stringtable.addString("leaf6");
   AbstractSymbol leaf7 = AbstractTable.stringtable.addString("leaf7");
   AbstractSymbol blah = AbstractTable.stringtable.addString("blah..blah");
   
  Node<AbstractSymbol> cooltree;
   cooltree = new Node<AbstractSymbol>(root);

  Node<AbstractSymbol> Leaf1 = cooltree.addChild(leaf1);
  Node<AbstractSymbol> Leaf2 = Leaf1.addChild(leaf2);
  Node<AbstractSymbol> Leaf3 = Leaf2.addChild(leaf3);
  Node<AbstractSymbol> Leaf4 = Leaf3.addChild(leaf4);
  Node<AbstractSymbol> Leaf5 = Leaf3.addChild(leaf5);
  Node<AbstractSymbol> Leaf6 = Leaf1.addChild(leaf6);
  Node<AbstractSymbol> Leaf7 = cooltree.addChild(leaf7);
  
  if (Leaf4.findNode(leaf1) != null)
  System.out.println("leaf1 found in "+ Leaf4.findNode(leaf1).getData().toString()); 
   else 
  System.out.println("leaf1 not found");

  LinkedList<Node<AbstractSymbol>> test = cooltree.ancestorList(cooltree);
  Iterator <Node<AbstractSymbol>> coolIterator = test.listIterator(); 
  while(coolIterator.hasNext()) 
   System.out.println("Ancestor:" + coolIterator.next().getData().toString());
  cooltree.printtree();
  LinkedList<Node<AbstractSymbol>> test = new LinkedList();
  test.add(Leaf6);
  test.add(Leaf4);
  Node<AbstractSymbol> out  = cooltree.typeJoin(test);
  System.out.println("Common ancestor is: " + out.getData().toString());

  System.out.println("Blah exists in the tree: "+ cooltree.exists(blah));   
  System.out.println("ROOT exists in the tree: "+ cooltree.exists(root));   
  System.out.println("leaf7 exists in the tree: "+ cooltree.exists(leaf7));   
  
  if(Leaf3.typeConform(Leaf4))
    System.out.println("leaf3 is child of leaf4");
  else
    System.out.println("leaf3 is not child of leaf4");

  if(Leaf3.typeConform(cooltree))
    System.out.println("leaf3 is child of root");
  else
    System.out.println("leaf3 is not child of root");

  if(cooltree.typeConform(Leaf5))
    System.out.println("cooltree is child of leaf5");
  else
    System.out.println("cooltree is not child of leaf5");
 }
*/
}
