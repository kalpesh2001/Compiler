import java.util.*;
public class Node<T> {

    T data;
    Node<T> parent;
    List<Node<T>> children;

    public Node(T data) {
        this.data = data;
        this.children = new LinkedList<Node<T>>();
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
   private void allChildren(HashSet<Node<T>> input) {
   for (Node<T> e : this.children) {
       input.add(e); 
       e.allChildren(input);
     }
  }

   public void printtree() {
    ListIterator<Node<T>> coolIterator = children.listIterator();
      /* System.out.println("Node: "+ this.getData().toString()); */
      while(coolIterator.hasNext()){
       Node<T> child = coolIterator.next();
/*       System.out.println("Node: "+ child.getData().toString());*/
       child.printtree();
   }
  }
          
/* Given a node, give all its ancestors in reverse order. Root first. */

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
  HashSet<Node<AbstractSymbol>> testhash = new HashSet<Node<AbstractSymbol>>();

  Leaf7.allChildren(testhash);
   for (Node<AbstractSymbol> e : testhash) 
   System.out.println("Node: <Main>"+ e.getData().toString());

/*  System.out.println("Node in tree: " + cooltree.findNode(leaf2).getData().toString());

  LinkedList<Node<AbstractSymbol>> test = cooltree.ancestorList(cooltree);
  Iterator <Node<AbstractSymbol>> coolIterator = test.listIterator(); 
  while(coolIterator.hasNext()) 
   System.out.println("Ancestor:" + coolIterator.next().getData().toString());

  LinkedList<Node<AbstractSymbol>> test = new LinkedList();
  test.add(Leaf7);
  Node<AbstractSymbol> out  = cooltree.typeJoin(test);
  System.out.println("Common ancestor is: " + out.getData().toString());

  System.out.println("Blah exists in the tree: "+ cooltree.exists(blah));   
  System.out.println("ROOT exists in the tree: "+ cooltree.exists(root));   
  System.out.println("leaf7 exists in the tree: "+ cooltree.exists(leaf7));   
  
  cooltree.printtree();
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
  */
 }
}
