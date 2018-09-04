Author Info
------------
Name: Vishak Lakshman Sanjeevikani Murugesh
ID: 800985356
email: vsanjeev@uncc.edu

About the project
-----------------

The primary objective of the project is to implement Dijkstra's Algorithm for finding the shortest path betweem two nodes in a graph. The project is implemented specifically for a data communication network where there is periodic failure and fixing of nodes, hence the algorithm is designed in such a way that the shortest paths are adjusted as per the availability of nodes. The time complexity for calculating the shortest path using Dijkstra's algorithm is O ( E * log V ) where E and V are number of edges and vertices respectively. This is achieved by the usage of a binary min heap to implement priority queues where the condition of weight being the minimum, weight is used as a metric for priority.

Apart from finding the shortest path, the program presents graph modifying functions like addedge and deletedge which enables us to add additional edges or detele existing edges while dynamically re-routing it. Also various other functions like edgedown, edgeup, vertexup, vertexdown provide the means to denote node failures and cnnection failures in a data communciation network. The user can make use of the Command Sheet given below to perfect the syntax and perform the desired actions.

The function printreachable() displays all the nodes that are online and their plausible destinations. The algorithm used to attain this has a complexity O(E+V).This is attained by the usage of DFS traversal to find the various possible connections available. Once the vertices and edges are down, the graph is built with only the online nodes, and the DFS upon this graph gives the possible destinations for a particular source vertex.

The user input errors and runtime exeptions are handled for all the basic functionalities. 


Development Info
----------------

Language: JAVA
Compiler Version: javac 1.8.0_112

Files
-----

Only one GRAPH.JAVA file 

Installation Guide (For Windows)
-------------------

1. To run this program, you would need a JAVA Development Kit(JDK). It can be downloaded for free from http://www.oracle.com/technetwork/java/javase/downloads. 

2. Once the JDK has been downloaded, run the simple installer and complete installation of JDK.

3. Set up the JAVA HOME and PATH enivronmental variables. For detailed instructions to set up the enivronmental variable, go to http://docs.oracle.com/javase/7/docs/webnotes/install/windows/jdk-installation-windows.html

4. Once JDK setup is complete, copy the Encoder.java and Decoder.java files and paste them in the "bin" directory of your JDK folder. You can open Command Prompt in the particular path by SHIFT + RIGHT MOUSE CLICK --> OPEN COMMAND PROMPT HERE or typing cmd and pressing ENTER in the address bar. Note: If the JDK is in Primary Drive you may have to open COMMAND PROMPT as an Administrator.

5. To compile the java file, use the following commands C:/jdk/bin> javac Graph.java. This will generate the corresponding class file for the java file.

6.To run the Graph program, type the command C:/jdk/bin> java Graph "inputfilename_with_path". Once the graph is successfully built, a message will be notified and the graph will be displayed. You can start performing necessary actions. You can use the following Command Sheet for assistance.

PS: Make sure the input graph text file is in the following format:
Vertex1 Vertex2 weight1
Vertex1 Vertex3 weight2
Vertex2 Vertex3 weight3
....

Command List
-------------

1.printpath
    Function: To print the path and the distance between two vertices
    Syntax: printpath source_vertex destination_vertex

2.addedge
    Function: Adding a new edge to the graph
    Syntax: addedge source_vertex destination_vertex weight

3.deleteedge
    Function: Delete an existing edge
    Syntax: deleteedge source_vertex destination_vertex

4.edgeup
    Function: To make a particular edge online
    Syntax: edgeup source_vertex destination_vertex

5.edgedown
    Function: To make a particular edge offline
    Syntax: edgedown source_vertex destination_vertex

6.vertexup
    Function: To make a particular vertex online
    Syntax: vertexup vertex_name

7.vertexdown
    Function: To make a particular edge online
    Syntax: vertexdown vertex_name

8.printgraph
    Function: To print the complete graph
    Syntax: printgraph

9.printreachable
    Function: To print only the nodes online and their plausible destinations
    Syntax: printreachable

10.quit
    Function: To exit the program
    Syntax: quit