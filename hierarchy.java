class hierarchy extends ListNode {                                                                                                                                                                  
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

 public static void main(String args[])