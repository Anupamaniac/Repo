
public class BstToDll {

	
	public static void main (String args[]){
		BinaryTree treeObj = createBTree();
		Node tempHead = treeObj.generateTree();
        System.out.println("inorder traversal of list with head = ("+tempHead.data+")gives follows");
        treeObj.inorder(tempHead);
        Node listHead = treeObj.bstToDll(tempHead);
        while(listHead!=null){
        	System.out.println(" "+listHead.data);
        	listHead = listHead.next;
        }
	}
	 public static BinaryTree createBTree()
	    {
	        return new BinaryTree();
	    }
}
