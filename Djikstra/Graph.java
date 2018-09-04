/**
 * @author:Vishak Lakshman SanjeeviKani Murugesh
 * @email:vsanjeev@uncc.edu
 * @studentid:800985356
 * */


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.TreeSet;




//VERTEX CLASS REPRESENTS THE NODES IN A GRAPH

class Vertex implements Comparable<Vertex>
{
    public String nodeName;
    public int vertexId;
    public Boolean vertexStatus;
    public List<Edge> sharedEdges;

    public Vertex(String nodeName,int vertexId)
    { 
    	this.nodeName = nodeName; 
    	this.vertexId=vertexId;
        sharedEdges = new LinkedList<Edge>();
        this.vertexStatus=Boolean.TRUE;
    }
    
    public Vertex(Vertex v)
    {
    	this.nodeName = v.nodeName;
    	this.vertexId=v.vertexId;
        this.sharedEdges=v.sharedEdges;
        this.vertexStatus=v.vertexStatus;   	
    }

	@Override
	public int compareTo(Vertex o) {
		return nodeName.compareTo(o.nodeName);
	}
		
}

//EDGE CLASS REPRESENTS THE CONNECTION BETWEEN TWO NODES

class Edge implements Comparable<Edge>
{
	public Vertex leftNode;
	public Vertex rightNode;
	public double weight;
	public boolean edgeStatus;
	
	public Edge(Vertex leftNode,Vertex rightNode,double weight)
	{
		this.leftNode=leftNode;
		this.rightNode=rightNode;
		this.weight=weight;
		this.edgeStatus=Boolean.TRUE;
	}

	@Override
	public int compareTo(Edge o) {
		return rightNode.nodeName.compareTo(o.rightNode.nodeName);
	}
}

/*
 * GRAPH CLASS
 * 
 * CONTRUCTION : with no parameters
 * FUNCTIONS : Build graph
 *             Graph modifying functionalities like addedge,removeedge,edgedown,edgeup,vertexdown,vertexup
 *             Finding shortest path between two nodes using Dijkstra's Algorithm
 *             Displaying the online nodes and the possible nodes reachable from them
 * ERROR HANDLING : User input errors and runtime exceptions are handled for all the basic functionalities 
 * 
 * */

public class Graph {
	
	/**
	 * Part I - Data Members instantiation and initialization
	 * 
	 * */
	
	//constants
	private final int maxnodes = 200000;
	private final int maxedges = 1000000;
	private final int INT_MAX=Integer.MAX_VALUE;
	
	//vertex sets
	Set<Vertex> reachableVertices = new TreeSet<Vertex>();
	Set<Vertex> onlineVertices = new TreeSet<Vertex>();
	
	//vertex maps
	private Map<String,Vertex> vertexMap= new TreeMap<String,Vertex>();
	private Map<Integer,String> vertexIdMap= new TreeMap<Integer,String>();
	
	Scanner sc=new Scanner(System.in);
	
	//graph specific data members
	private int vertexcount;
	private int vertexonline;
	private int edges;
	private int[] last=new int[maxnodes];
	private int[] head=new int[maxedges];
	private int[] previous=new int[maxedges];
	private double[] len=new double[maxedges];
	private double[] prio=new double[maxnodes];
	private int[] prev=new int[maxnodes];
	private LinkedList<Integer> adj[];
	
	
	//heap specific data members
	private double[] h=new double[maxnodes];
	private int[] pos2Id=new int[maxnodes];
	private int[] id2Pos=new int[maxnodes];
	private int hsize;
	
	/**
	 * Part II - Building the graph
	 * 
	 * */
	
	//clears the existing graph 
	private void graphClear() {
	    java.util.Arrays.fill(last, -1);
	    java.util.Arrays.fill(len, 0);
	    java.util.Arrays.fill(previous, 0);
	    java.util.Arrays.fill(head, 0);
	    java.util.Arrays.fill(prio, 0);
	    java.util.Arrays.fill(prev, 0);
	    java.util.Arrays.fill(h, 0);
	    java.util.Arrays.fill(pos2Id, 0);
	    java.util.Arrays.fill(id2Pos, 0);
	    edges = 0;
	    vertexonline=0;
	    vertexcount=0;
	    initGraph(maxnodes);
	}
	
	//initialise the graph
	private void initGraph(int v)
	{
		adj=new LinkedList[v];
		for (int i=0; i<v; ++i)
            adj[i] = new LinkedList();
	}
	
	//searches for an existing vertex in the vertexmap based the vertex name
	//returns the vertex if found else creates a new vertex
	private Vertex getVertex(String vertexName)
    {
        Vertex v = vertexMap.get(vertexName);
        if(v == null)
        {
            v = new Vertex(vertexName,vertexcount);
            vertexMap.put(vertexName,v);
            vertexIdMap.put(vertexcount, vertexName);
            vertexcount++;
        }
        return v;
    }
	
	//to check if a particular vertex is present in the graph or not
	//returns true if present else false
	private Boolean checkVertex(String vertexName)
	{
		Vertex v=vertexMap.get(vertexName);
		if(v==null)
			return Boolean.FALSE;
		else
			return Boolean.TRUE;
			
	}
	
        //search for a vertex using its ID
	private Vertex getVertexById(int n)
	{
		String name=vertexIdMap.get(n);
		Vertex v=vertexMap.get(name);
		return v;
	}
    
	//establishes connection between the various vertices and edges
	private void makeEdge(int u, int v, double length) {
	    head[edges] = v;
	    len[edges] = length;
	    previous[edges] = last[u];
	    last[u] = edges++;
	    head[edges] = u;
	    len[edges] = length;
	    previous[edges] = last[v];
	    last[v] = edges++;
	    adj[u].add(v);
	}
	
	//constructs the graph based on vertices and edges that are created and are online
	public void buildGraph()
	{
		graphClear();
		for(Map.Entry<String, Vertex> entry: vertexMap.entrySet())
		{
			Vertex v=entry.getValue();
			if(v.vertexStatus)
			{
				for(int i=0;i<v.sharedEdges.size();i++)
				{
					if(v.sharedEdges.get(i).edgeStatus)
					  makeEdge(v.sharedEdges.get(i).leftNode.vertexId,v.sharedEdges.get(i).rightNode.vertexId,v.sharedEdges.get(i).weight);
				}	
			}
			vertexonline++;
			Collections.sort(v.sharedEdges);
		}
	}
	
	/**
	 * Part III - Graph Modification Functionalities
	 * 
	 * */
	
	//function to add a new edge to the graph
	public void addEdge(String source,String destination,double weight)
	{
		Vertex sourceVertex=new Vertex(getVertex(source));
		Vertex destinationVertex=new Vertex(getVertex(destination));
		Edge e=new Edge(sourceVertex,destinationVertex,weight);
		sourceVertex.sharedEdges.add(e);
		e=new Edge(destinationVertex,sourceVertex,weight);
		destinationVertex.sharedEdges.add(e);
		makeEdge(sourceVertex.vertexId, destinationVertex.vertexId, weight);
		Collections.sort(sourceVertex.sharedEdges);
	}
	
	//deletes a particular edge
	//returns true  if success else false
	public Boolean deleteEdge(String source, String destination)
	{
		if(checkVertex(source) && checkVertex(destination))
		{
		Vertex v=vertexMap.get(source);
		for(int i=0;i<v.sharedEdges.size();i++)
		{
		  if(v.sharedEdges.get(i).rightNode.nodeName.equals(destination))
		  {
		       v.sharedEdges.remove(i);
		  }
		}
		v=vertexMap.get(destination);
		for(int i=0;i<v.sharedEdges.size();i++)
		{	
		  if(v.sharedEdges.get(i).rightNode.nodeName.equals(source))	
		       v.sharedEdges.remove(i);
		}
		buildGraph();
		return Boolean.TRUE;
		}
		else
			return Boolean.FALSE;
	}
	
	//makes the selected edge offline and cannot be used for network transmission
	//returns true  if success else false
	public Boolean edgeDown(String source,String destination)
	{
		if(checkVertex(source) && checkVertex(destination))
		{
		Vertex v=vertexMap.get(source);
		for(int i=0;i<v.sharedEdges.size();i++)
		{
		  if(v.sharedEdges.get(i).rightNode.nodeName.equals(destination))	
		       v.sharedEdges.get(i).edgeStatus=Boolean.FALSE;
		}
		v=vertexMap.get(destination);
		for(int i=0;i<v.sharedEdges.size();i++)
		{
		  if(v.sharedEdges.get(i).rightNode.nodeName.equals(source))	
		       v.sharedEdges.get(i).edgeStatus=Boolean.FALSE;
		}
		buildGraph();
		return Boolean.TRUE;
		}
		else
			return Boolean.FALSE;
	}
	
	//makes the edge online and available for transmission
	//returns true  if success else false
	public Boolean edgeUp(String source,String destination)
	{
		if(checkVertex(source) && checkVertex(destination))
		{
		Vertex v=vertexMap.get(source);
		for(int i=0;i<v.sharedEdges.size();i++)
		{
		  if(v.sharedEdges.get(i).rightNode.nodeName.equals(destination))	
		       v.sharedEdges.get(i).edgeStatus=Boolean.TRUE;
		}
		v=vertexMap.get(destination);
		for(int i=0;i<v.sharedEdges.size();i++)
		{
		  if(v.sharedEdges.get(i).rightNode.nodeName.equals(source))	
		       v.sharedEdges.get(i).edgeStatus=Boolean.TRUE;
		}
		buildGraph();
		return Boolean.TRUE;
		}
		else
			return Boolean.FALSE;
	}
	
	//makes a particular vertex offline and unavailable for transmission
	//returns true  if success else false
	public Boolean vertexDown(String vertex)
	{
		if(checkVertex(vertex))
		{
		Vertex v=vertexMap.get(vertex);
		v.vertexStatus=Boolean.FALSE;
		for(Map.Entry<String, Vertex> entry: vertexMap.entrySet())
		{
			Vertex v1=entry.getValue();
			for(int i=0;i<v1.sharedEdges.size();i++)
			{
				if(v1.sharedEdges.get(i).rightNode.nodeName.equals(v.nodeName))
					v1.sharedEdges.get(i).edgeStatus=Boolean.FALSE;
			}	
		}
		buildGraph();
		return Boolean.TRUE;
		}
		else
			return Boolean.FALSE;
	}
	
	//makes a particular vertex online and available for transmission
	//returns true  if success else false
	public Boolean vertexUp(String vertex)
	{
		if(checkVertex(vertex))
		{
		Vertex v=vertexMap.get(vertex);
		v.vertexStatus=Boolean.TRUE;
		for(Map.Entry<String, Vertex> entry: vertexMap.entrySet())
		{
			Vertex v1=entry.getValue();
			for(int i=0;i<v1.sharedEdges.size();i++)
			{
				if(v1.sharedEdges.get(i).rightNode.nodeName.equals(v.nodeName))
					v1.sharedEdges.get(i).edgeStatus=Boolean.TRUE;
			}	
		}
		buildGraph();
		return Boolean.TRUE;
		}
		else
			return Boolean.FALSE;
	}
	
	/**
	 * Part IV - Binary Heap implementaion of Priority Queues for finding the shortest path
	 *                       (Dikstra's Algorithm)
	 * 
	 * */
	
	//heap specific functions
	private void swap(int a,int b)
	{
		a=a+b;
		b=a-b;
		a=a-b;
	}
	private void swap(double a,double b)
	{
		a=a+b;
		b=a-b;
		a=a-b;
	}
	
	private double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
	
	private void hswap(int i, int j) {
	    swap(h[i], h[j]);
	    swap(pos2Id[i], pos2Id[j]);
	    swap(id2Pos[pos2Id[i]], id2Pos[pos2Id[j]]);
	}
	
	private void moveUp(int pos) {
	    while (pos > 0) {
	        int parent = (pos - 1);//
	        if (h[pos] >= h[parent]) {
	            break;
	        }
	        hswap(pos, parent);
	        pos = parent;
	    }
	}
	
	private void add(int id, double prio) {
	    h[hsize] = prio;
	    pos2Id[hsize] = id;
	    id2Pos[id] = hsize;
	    moveUp(hsize++);
	}
	
	private void increasePriority(int id, double prio) {
	    int pos = id2Pos[id];
	    h[pos] = prio;
	    moveUp(pos);
	}
	
	private void moveDown(int pos) {
	    while (pos < (hsize)) {//
	        int child = 2 * pos + 1;
	        if (child + 1 < hsize && h[child + 1] < h[child]) {
	            ++child;
	        }
	        if (h[pos] <= h[child]) {
	            break;
	        }
	        hswap(pos, child);
	        pos = child;
	    }
	}
	
	private int removeMin() {
	    int res = pos2Id[0];
	    double lastNode = h[--hsize];
	    if (hsize > 0) {
	        h[0] = lastNode;
	        int id = pos2Id[hsize];
	        id2Pos[id] = 0;
	        pos2Id[0] = id;
	        moveDown(0);
	    }
	    return res;
	}
	
	//Shortest path: Dijkstra's algorithm
	//Complexity: O(E*logV) where E-Edges and V-Vertices
	private void dijkstra(int s) {
	    java.util.Arrays.fill(prev, -1);
	    java.util.Arrays.fill(prio, INT_MAX);
	    prio[s] = 0;
	    hsize = 0;
	    add(s, 0);

	    while (hsize > 0) {
	        int u = removeMin();
	        for (int e = last[u]; e >= 0; e = previous[e]) {
	            int v = head[e];
	            double nprio = prio[u] + len[e];
	            nprio=round(nprio,2);
	            if (prio[v] > nprio) {
	                if (prio[v] == INT_MAX)
	                    add(v, nprio);
	                else
	                    increasePriority(v, nprio);
	                prio[v] = nprio;
	                prev[v] = u;
	            }
	        }
	    }
	}
	
	/**
	 * Part V - Print Reachable
	 *          Algorithm to print only the online nodes and their plausible destinations
	 *          The graph is built and DFS traversal will provide all the vertices that are reachable from a particular source
	 *          Complexity : O ( E + V ) 
	 * */
		
	void findOnlineVertices()
	{
		onlineVertices.removeAll(onlineVertices);
		reachableVertices.removeAll(reachableVertices);
		for(Map.Entry<String, Vertex> m:vertexMap.entrySet())
		{
			Vertex v=m.getValue();
			if(v.vertexStatus)
			{
				onlineVertices.add(v);
			}
			else
				continue;
		}
	}
	
	void DFSUtil(int v,boolean visited[])
    {
        // Mark the current node as visited and print it
        visited[v] = true;
        if(getVertexById(v).vertexStatus)
           reachableVertices.add(getVertexById(v));
 
        // Recur for all the vertices adjacent to this vertex
        Iterator<Integer> i = adj[v].listIterator();
        while (i.hasNext())
        {
            int n = i.next();
            if (!visited[n])
                DFSUtil(n, visited);
        }
    }
 
    // The function to do DFS traversal. It uses recursive DFSUtil()
    void DFS(int v)
    {
        // Mark all the vertices as not visited(set as
        // false by default in java)
        boolean visited[] = new boolean[vertexonline];
 
        // Call the recursive helper function to print DFS traversal
        DFSUtil(v, visited);
    }
	
	
	/**
	 * Part VI - Printing functionalities
	 * 
	 * */
	
	//Evaluting the shortest path and printing the path and distance
	public void printShortestPath(String source,String destination)
	{
		if(checkVertex(source) && checkVertex(destination))
		{	
		Vertex sourceVertex=new Vertex(getVertex(source));
		Vertex destinationVertex=new Vertex(getVertex(destination));
		dijkstra(sourceVertex.vertexId);
	  	List<Integer> a=new ArrayList<Integer>();
	  	a.add(destinationVertex.vertexId);	  	
	  	for(int e=prev[destinationVertex.vertexId];e!=sourceVertex.vertexId;)
	  	{
	  		a.add(e);
	  		e=prev[e];
	  	}
	  	a.add(sourceVertex.vertexId);
	  	for(int i=a.size()-1;i>=0;i--)
	  	{
	  		System.out.print(getVertexById(a.get(i)).nodeName+" ");
	  		if(i==0)
	  			System.out.print(prio[destinationVertex.vertexId]+"\n");
	  	}
		}
		else
			System.out.println("Nodes don't exist");
	  	
	}
	
	//print only the online nodes and their plausible destinations
	public void printReachable()
	{
		findOnlineVertices();
		Iterator<Vertex> online=onlineVertices.iterator();
		while(online.hasNext())
		{
			buildGraph();
		    Vertex source=vertexMap.get(online.next().nodeName);
		    DFS(source.vertexId);
		    Iterator<Vertex> it=reachableVertices.iterator();
		    System.out.println(source.nodeName);
		    while(it.hasNext())
		    {
			  Vertex v=it.next();
			  if(source.nodeName.equals(v.nodeName))
				 continue;
			  else
				  System.out.println("\t"+v.nodeName);
		  }
		}
	}
	
	
	
	//printing the entire graph after completion of its contruction
	public void printGraph()
	{
		System.out.println("Number of vertices: " + vertexMap.size());
		for(Map.Entry<String,Vertex> entry: vertexMap.entrySet())
		{
			Vertex v=entry.getValue();
            if(v.vertexStatus)
            {	
			System.out.println(v.nodeName);
			for(Edge e:v.sharedEdges)
			  {	
				if(e.edgeStatus)
			     System.out.println("\t"+e.rightNode.nodeName+" "+e.weight);
				else
				 System.out.println("\t"+e.rightNode.nodeName+" DOWN");
			  }
            }
            else
            	System.out.println(v.nodeName+"\tDOWN");
		}
	}
	
	//user command processing and the execution of the requests
	public void execute()
	{
		System.out.println("Graph built successfully....");
		Map<String,Integer> CommandList=new TreeMap<String,Integer>();
		CommandList.put("printpath",1);
		CommandList.put("addedge",2);
		CommandList.put("deleteedge",3);
		CommandList.put("edgeup",4);
		CommandList.put("edgedown",5);
		CommandList.put("vertexup",6);
		CommandList.put("vertexdown",7);
		CommandList.put("printreachable",8);
		CommandList.put("printgraph",9);
		CommandList.put("quit",10);
		printGraph();
		while(Boolean.TRUE)
		{
			String command=sc.nextLine();
			StringTokenizer st=new StringTokenizer(command);
			Integer choice=CommandList.get(st.nextToken());
			if(choice == null)
			{	
		       System.out.println("Syntax error: Invalid Command");
		       continue;
			}
			switch(choice)
			{
			case 1:if(st.countTokens() != 2)
			       {
				      System.out.println("Syntax error: Invalid number of arguments");
				      break;
			       }
			       printShortestPath(st.nextToken(),st.nextToken());
			       break;
			
			case 2:if(st.countTokens() != 3)
			       {
				      System.out.println("Syntax error: Invalid number of arguments");
			          break;
			       }
			       addEdge(st.nextToken(),st.nextToken(),Double.parseDouble(st.nextToken()));
			       System.out.println("Edge added successfully");
			       break;
			       
			case 3:if(st.countTokens() != 2)
		           {
			        System.out.println("Syntax error: Invalid number of arguments");
		            break;
		           }
		           if(deleteEdge(st.nextToken(),st.nextToken()))
		              System.out.println("Edge deleted successfully");
		           else
		        	   System.out.println("Node doesn't exist");
		           break;
			
			case 4:if(st.countTokens() != 2)
	                {
		              System.out.println("Syntax error: Invalid number of arguments");
	                  break;
	                }
	                if(edgeUp(st.nextToken(),st.nextToken()))
	                  System.out.println("The selected edge is now online");
	                else
	                	System.out.println("Node doesn't exist");
	                break;
			
			case 5:if(st.countTokens() != 2)
                   {
                     System.out.println("Syntax error: Invalid number of arguments");
                     break;
                   }
                   if(edgeDown(st.nextToken(),st.nextToken()))
                        System.out.println("The selected edge is now offline");
                   else
	                	System.out.println("Node doesn't exist");
                   break;
			
			case 6:if(st.countTokens() != 1)
                    {
                    System.out.println("Syntax error: Invalid number of arguments");
                    break;
                    }
                    if(vertexUp(st.nextToken()))
                        System.out.println("The selected vertex is now online");
                    else
	                	System.out.println("Node doesn't exist");
                    break;
			
			case 7:if(st.countTokens() != 1)
                    {
                      System.out.println("Syntax error: Invalid number of arguments");
                      break;
                    }
                    if(vertexDown(st.nextToken()))
                        System.out.println("The selected vertex is now offline");
                    else
	                	System.out.println("Node doesn't exist");
                    break;
			
			case 8:if(st.countTokens() != 0)
                    {
                     System.out.println("Syntax error: Invalid number of arguments");
                     break;
                    }
			        printReachable();
			        break;
			
			case 9:if(st.countTokens() != 0)
                   {
                    System.out.println("Syntax error: Invalid number of arguments");
                    break;
                   }
				   printGraph();
			       break;
			       
			case 10:if(st.countTokens() != 0)
                    {
                     System.out.println("Syntax error: Invalid number of arguments");
                     break;
                    }
				    System.exit(0);
				
			}
			
			
		}
	}		
	
	/**
	 * Part VII - Main Function
	 *           Reads the input file and retrieves the data that are needed for graph construction
	 *      
	 * */
	
	public static void main(String args[])
	{
		Graph g=new Graph();
		g.graphClear();
		try {
			FileReader inputFile=new FileReader(args[0]);
		    Scanner readFile=new Scanner(inputFile);
		    StringTokenizer token;
		    while(readFile.hasNext())
		    {
		       String line=readFile.nextLine();
		       try
		       {
		       token=new StringTokenizer(line);
		       if(token.countTokens() !=3)
		       {
		    	   System.err.println("Input format mismatch");
		    	   continue;
		       }
		       g.addEdge(token.nextToken(),token.nextToken(),Double.parseDouble(token.nextToken())); 	
		       }
		       catch(Exception e)
		       {
		         e.printStackTrace();	   
		       }
		    }
		    readFile.close();
		    g.execute();
		    		
		
		} catch (FileNotFoundException e) {
			System.out.println("File not found");
		}

	}


}



