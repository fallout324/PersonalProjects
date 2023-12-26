//Student Name: Michael Song
//Student Number: 251101048

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DisjointSetsLinkedList {

   static int size = 0;
	
	Node[] nodeAddress;

	public DisjointSetsLinkedList(int n) {
  // public void wandf(int n) {

		nodeAddress = new Node[n]; 
		for(int i = 0; i < n; i++) {
			make_set(i);
         size++;
		}
	}
	
	public void make_set(int a) {
	    // Create a new Set
	    LinkedSet newSet = new LinkedSet();
	  
	    // Create a new linked list node
	    // to store given key
	    newSet.head = new Node();
	  
	    // Initialize head and tail
	    newSet.tail = newSet.head;
	    newSet.size = 1;
	    nodeAddress[a]=newSet.head;
	  
	    // Create a new set
	    newSet.head.value = a;
	    newSet.head.inSet = newSet;
	    newSet.head.next = null;		
	}
	
	public LinkedSet find_set(int key) {
	    Node ptr = nodeAddress[key];
	    return ptr.inSet;	
	}
	
	// union function for joining two subsets
	// of a universe.  Merges set2 into set1
	// and deletes set1.
	void union_sets(LinkedSet set1, LinkedSet set2) {
		   Node cur = set2.head;
		    while (cur != null)
		    {
		        cur.inSet = set1;
		        cur = cur.next;
		    }
		  
		    // Join the tail of the set to head
		    // of the input set
		    set1.tail.next = set2.head;
		    set1.tail = set2.tail;
		    set1.size += set2.size;
		    
		    //Make set2 a orphan for garbage collection
		    set2.head=null;
		    set2.tail=null;
		    
	}
	
	void smartUnion(LinkedSet set1, LinkedSet set2) {
		if (set1.size >= set2.size) {
			union_sets(set1, set2);
		} else {
			union_sets(set2, set1);
		}
	}
   
   int final_set() {
      return size;
      
   }
	
	void printComponent(List<Node> nodes, int numberOfCols) {
		
		Collections.sort(nodes);
		
		int minRow = nodes.get(0).value / numberOfCols;
		int maxRow = nodes.get(nodes.size()-1).value / numberOfCols + 1;
		
		int totalNodes = numberOfCols * (maxRow - minRow);
		int[] nodeArr = new int[totalNodes];
		
		for (Node node: nodes) {
			int index = node.value - (minRow * numberOfCols);
			nodeArr[index] = 1;
		}
		
		for (int i = 0; i < totalNodes; i++) {
			if ( (i+1) % numberOfCols == 0 ) {
				System.out.println();
			} else {
				if (nodeArr[i] == 1) {
					System.out.print('+');
				} else {
					System.out.print(' ');
				}
			}
			
		}
	}
	
	class Node implements Comparable<Node> {
		int value;
		Node next;
		LinkedSet inSet;
		
		@Override
		public int compareTo(Node n) {
			return this.value - n.value;
		}
	}
	
	class LinkedSet implements Comparable<LinkedSet>{
		Node head;
		Node tail;
		int size;
		
		@Override
		public int compareTo(LinkedSet s) {
			return this.size - s.size;
		}	
	}
	
	class LinkedSetComparator implements Comparator<LinkedSet> {

		@Override
		public int compare(LinkedSet s1, LinkedSet s2) {
			return s1.size - s2.size;
		}
		
	}
	
	public static void main(String[] args) throws IOException
	{
	      int countPlus = 0;
	      int trackArray = 0;
	      int allChar[] = new int[5184];
	      int curValue = 0;
	     // String fileName = "C:\\Users\\Bill\\Documents\\girl.img";
        //String fileName = "girl.img";
        String fileName = "infile";


	      //String fileName = "C:\\Users\\hongyan.song\\Downloads\\girl.img";
         System.out.println("answer 1:");
         
	      try (FileReader fileReader = new FileReader(fileName)) {
	         //int curValue = 0;
	         int singleCharInt;
	         char singleChar;
	         while((singleCharInt = fileReader.read()) != -1 && trackArray < 5184) {
	            singleChar = (char) singleCharInt;
	            if (singleChar == '+') {
		               allChar[trackArray++] = 1;

		            }
	            else {
	            	 //include space and line return
	            	 allChar[trackArray++] = 0;
	            	//System.out.println("Unknown character: " + singleCharInt);
	            }
           
	       
	            //display one character at a time
	            System.out.print(singleChar);
	         }
	         
	         //System.out.println(countPlus);
	         DisjointSetsLinkedList action = new DisjointSetsLinkedList(5184);
            //wandf action = new wandf(5184);

	       
	         for (int i = 0; i < 5184; i++) {
	            //if (allChar[i] == 1) {
	              // countPlus--;
	            //}
	            if (allChar[i] == 1 && allChar[i + 1] == 1) {
	            	LinkedSet pi = action.find_set(i);
	            	LinkedSet pi1 = action.find_set(i + 1);
	            	if ( pi != pi1) {
	            		action.smartUnion(pi, pi1);
	            	}
	            }
	            if (i + 72 < 5184) {
		            if (allChar[i] == 1 && allChar[i + 72] == 1) {
		                  LinkedSet pj = action.find_set(i);
		                  LinkedSet pj72 = action.find_set(i + 72);
			            	if ( pj != pj72) {
			            		action.smartUnion(pj, pj72);
			            	}
		             } 
		        }

	            
	         }

	         Map<LinkedSet, List<Node>> pMap = new HashMap<>();
	         Node[] nodeAddress = action.nodeAddress;

	         for (int i = 0; i < 5184; i++) {
	        	 if (allChar[i] == 1) {
	        	 if (pMap.containsKey(nodeAddress[i].inSet)) {
	        		 pMap.get(nodeAddress[i].inSet).add(nodeAddress[i]);
	        	 } else {
	        		 List<Node> lSet =new ArrayList<>();
	        		 lSet.add(nodeAddress[i]);
	        		 pMap.put(nodeAddress[i].inSet, lSet);
	        	 }
	        	 }
	         }
	         System.out.println("Number of Sets: " + pMap.size());
            size = pMap.size();
	         
	         ArrayList<LinkedSet> sortedKeys
	            = new ArrayList<>(pMap.keySet());
	 
	        Collections.sort(sortedKeys);
           
            int j = 0;
	         int index = (int) 'a';
            
            System.out.println("Answer 2:");


            for(LinkedSet i : sortedKeys) {
               
               System.out.print( "" + ( (char) (index + j)) + ": ");
               action.printComponent(pMap.get(i), 72);
               j++;
	         }

	        
	        j = 0;
	        index = (int) 'a';
           System.out.println("Answer 3:");

	        for(LinkedSet i : sortedKeys) {
	        		 System.out.print( "" + ( (char) (index + j)) + ": " + pMap.get(i).size() );
	        		 for (Node k: pMap.get(i)) {
	        			 System.out.print(", " + k.value);
	        		 }
	        		 System.out.println();
	        		// action.printComponent(pMap.get(i), 72);
	        		 System.out.println();
	        		 j++;
	         }
            
           // j = 0;
	        // index = (int) 'a';
            
           // System.out.println("Answer 2:");


           // for(LinkedSet i : sortedKeys) {
               
             //  System.out.print( "" + ( (char) (index + j)) + ": ");
              // action.printComponent(pMap.get(i), 72);
              // j++;
	         //}
            
            j = 0;
	         index = (int) 'a';

            System.out.println("Answer 4:");
            for(LinkedSet i : sortedKeys) {
               if (pMap.get(i).size() > 4) {
               System.out.print( "" + ( (char) (index + j)) + ": ");
               action.printComponent(pMap.get(i), 72);
               }
               j++;
	         }

	      }
	      
	    
	}
}
