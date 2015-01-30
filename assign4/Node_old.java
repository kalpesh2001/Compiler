import java.util.*;
public class Node<T>
{
private List<Node<T>> children = new ArrayList<Node<T>>();
private Node<T> parent = null;
private T data = null;

public Node(T data)
{
    this.data = data;
}
public Node(T data, Node<T> parent)
{
    this.data = data;
    this.parent = parent;
}

public List<Node<T>> getChildren()
{
    return children;
}

public void setParent(Node<T> parent)
{
    this.parent = parent;
}

public void addChild(T data)
{
    Node<T> child = new Node<T>(data);
    child.setParent(this);
    this.children.add(child);
}

public void addChild(Node<T> child)
{
    child.setParent(this);
    this.children.add(child);
}

public T getData()
{
    return this.data;
}

public void setData(T data)
{
    this.data = data;
}

public boolean isRoot()
{
    return (this.parent==null);
}

public boolean isLeaf()
{
    if(this.children.size() == 0) 
        return true;
    else 
        return false;
}
public void removeParent()
{
    this.parent = null;
}

public static void main(String args[]) {
   AbstractSymbol root = AbstractTable.stringtable.addString("ROOT");
   AbstractSymbol leaf1 = AbstractTable.stringtable.addString("leaf1");
   AbstractSymbol leaf2 = AbstractTable.stringtable.addString("leaf2");
   AbstractSymbol leaf3 = AbstractTable.stringtable.addString("leaf3");
   AbstractSymbol leaf4 = AbstractTable.stringtable.addString("leaf4");
   
  Node<AbstractSymbol> cooltree;
  cooltree = new Node<AbstractSymbol>(root,null);

  cooltree.addChild(leaf1);
/*
  Node<AbstractSymbol> Leaf1 = new Node<AbstractSymbol>(leaf1,cooltree);
  Leaf1.addChild(leaf2);
  Leaf1.addChild(leaf4);
*/

  Node<AbstractSymbol> Leaf1 = new Node<AbstractSymbol>(leaf1,cooltree);
  cooltree.setParent(Leaf1);
  cooltree.addChild(leaf2);
  cooltree.addChild(leaf3);

  
  ListIterator<Node<AbstractSymbol>> coolIterator = cooltree.getChildren().listIterator();

  while(coolIterator.hasNext()){
     System.out.println("Node: "+ coolIterator.next().getData().getString());
   }
}
}
