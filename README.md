# Istanbul_Metro_Line_Finder

This Java code reads data from a text file containing the coordinates, pauses and breakpoints of a metro line  network. It then uses this data to build a graph of the metro network and implements a breadth-first search to find the path between two stations. BFS stands for Breadth-First Search, which is a graph traversal algorithm that explores all the vertices of a graph in breadth-first order. Breadth First Search visits vertices in increasing distance from the source.  
<pre>
Given a graph with N vertices and a selected vertex s:  
for (i = 1; there are unvisited vertices ; i++)  
    Visit all unvisited vertices at distance i  
Note that i is the length of the shortest path between s and currently processed vertices.  
Store source vertex S in a queue and mark as processed  
while queue is not empty   
    Read vertex v from the queue    
    for all neighbors w:  
    If w is not processed  
        Mark w as processed  
        Enqueue w in the queue  
        Record the parent of w to be v  
</pre>  

    
* As for my code, it can be summarized as follows:    
* Import necessary classes from Java libraries.     
* Create a main method that throws a FileNotFoundException.         
* Declare a File object that represents the text file containing the metro network data.         
* Create a Scanner object to read data from the text file.         
* Read the text file line by line and concatenate the lines into a single string.         
* Split the concatenated string into an array of strings using the newline character as the delimiter.         
* Create a two-dimensional string array to store the names of metro stations for each metro line.         
* Create a two-dimensional string array to store the pauses (i.e., stops) for each metro line.      
* Parse the text data into the two-dimensional arrays using loops and conditionals.            
* Create a one-dimensional string array to store the names of metro lines.            
* Create a two-dimensional string array to store the names of the pauses for each metro line.            
* Create a two-dimensional string array to store the coordinates of each pause for each metro line.            
* Create a two-dimensional string array to store the breakpoints between metro lines.         
* Check whether the start and end stations are valid by comparing them with the names of all stations.         
* Create an ArrayList object to store the graph representation of the metro network.            
* Create another ArrayList object to store the nodes in the graph that have been visited during the search.            
* Create yet another ArrayList object to store the parent-child relationship between nodes.         
* Create an ArrayList object to store the names of the breakpoints.         
* Create an ArrayList object to store the names of all stations.         
* Create an ArrayList object to store the check status (visited or not) of each station.            
* Add the end station to the queue and mark it as visited.         
* While the queue is not empty, remove the first element from the queue.            
* For the current element, find all its neighbors (i.e., stations that can be reached directly from it).         
* For each neighbor, if it has not been visited, mark it as visited, add it to the queue, and record its parent.         
* If the start station is found, terminate the search.         
* If the queue is empty and the start station has not been found, return an error message.          
* Othwevise, write the path of two stations and make an animation with StdDraw.         
* Put the background image and adds the lines, stations and the names of stations which should be written.      
* Marks the path step by step in animatio            

Example of the created map:


![image](https://github.com/kerembozkurt2002/Istanbul_Metro_Line_Finder/assets/157289283/ee55f161-b543-4b84-8c30-f8c94225e033)



