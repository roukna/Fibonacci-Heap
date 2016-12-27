import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;


public class hashtagcounter {
	
	final static Charset ENCODING = StandardCharsets.UTF_8;
	
	static Map<String, Node<Integer, String>> hashTag = new Hashtable<String, Node<Integer, String>>();
	
	static FibonacciHeap<Integer, String> fibonacciHeap = new FibonacciHeap<Integer, String>();

	public static void main(String[] args) throws IOException {
		
		BufferedReader reader = null;
		BufferedWriter writer = null;
		
		/*Input and Output files*/
		Path inputPath = Paths.get(args[0]);
		Path outputPath = Paths.get("output_file.txt");
		
		try {
			reader = Files.newBufferedReader(inputPath, ENCODING);
		} catch (IOException e1) {
			
			e1.printStackTrace();
		}
		
		try {
			writer = Files.newBufferedWriter(outputPath, ENCODING);
		} catch (IOException e1) {
			
			e1.printStackTrace();
		}
		
		int lineNo = 0;
		
		try {
		      
			String line = null;
			
			while((line = reader.readLine()) != null) {
			
			lineNo += 1;
				
		    line = line.trim();
		    line = line.toLowerCase();
		    
		    /*If line contains a hashtag*/
		    if(line.charAt(0) == '#') {
		    	
		    	String key = line.split(" ")[0];
		    	int value = 0;
		    	
		    	try {
		    		
		    		/*The frequency of hashtag*/
		    		value = Integer.parseInt(line.split(" ")[1]);
		    
		    	}
		    	catch (Exception e) {
		    		
		    		System.out.println("Not a valid integer: "+ line.split(" ")[1]);
		    		e.printStackTrace();
		    	}
		    	
		    	
		    	key = key.substring(1, key.length());
		    	
		    	/*If hashtag already present in hash table, increase the value of it's frequency, i.e., call increaseKey operation on that node with new frequency.*/ 
		    	
		    	if(hashTag.containsKey(key)) {
		    		fibonacciHeap.fibHeapIncreaseKey(hashTag.get(key), hashTag.get(key).getNodeKey() + value);
		    	
				}
		    	else {
		    		
		    		/*If hashtag not present in hash table, insert the hashtag into the hash table and the corresponding node into the Fibonacci Heap.*/
		    		
		    		Node<Integer, String> fibKey = new Node<Integer, String>(value, key);
		    		hashTag.put(key, fibKey);
		    		fibonacciHeap.fibHeapInsert(fibKey);
		    		
		    	}
		    	
		    } 
		    else if (line.compareTo("stop") == 0) {
		    	
		    	System.out.println("STOPPING...");
		    	break;
		    }
		    /*If the line contains only a number (say number n), print top n hashtag values.*/
		    else if (line.trim().matches("[0-9]+")) {
		    	
		    	ArrayList<String> removeKeys = new ArrayList<String>();
		    	
		    	int topNHashTag = Integer.parseInt(line.trim());
		    	
		    	if (topNHashTag > 20) {
		    		
		    		/*Permissible value of n, n<=20.*/
		    		System.out.println("***ERROR***Query Integer n > 20!!!!");
		    		System.exit(0);
		    	}
		    	else {
		    		
		    		int count = 1;
		    		
		    		/*Print top n hashtags*/
		    		
		    		while(count <= topNHashTag) {
		    			
		    			Node<Integer, String> maxVal = fibonacciHeap.fibHeapExtractMax();
		    			String maxValKey = maxVal.getHashNodeKey();
		    				
	    			    if (count != 1)
	    			    	writer.write(",");
	    			    writer.write(maxValKey);
		    			    
		    			removeKeys.add(maxValKey);
		    			
		    			count++;
		    			
		    		}
		    		writer.newLine();
		    	}
		    	
		    	/*Re-insert extracted nodes into Fibonacci Heap.*/
		    	for(String rKey : removeKeys) {
		    		
		    		Node<Integer, String> fibKey = hashTag.get(rKey);
		    		
		    		fibKey.setNodeChild(null);
		    		fibKey.setNodeDegree(0);
		    		fibKey.setMarked(false);
		    		fibKey.setMaximum(false);
		    		fibKey.setNodeParent(null);		    		
		    		
		    		fibonacciHeap.fibHeapInsert(fibKey);
		    		
		    	}
		    	
		    	removeKeys.clear();;
		    	
		    }
		    else {
		    	System.out.println("Invalid input at line " + lineNo + " " + line);
		    }
				
		    }      
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		try {
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		

	}

}
