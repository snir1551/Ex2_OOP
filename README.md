# Ex2

# Description
This project is devided into two:
first part concern of building a weighted and directed graph,
the second part focuses on a game which uses the first part.

* [Part 1](#p1)
* [Part 2](#p2)
* [Running The Game](#run)

<a name="p1"></a>
## Part 1:
At this project we 7 interfaces: directed_weighted_graph,dw_graph_algorithms,edge_data,edge_location,game_service,node_data,node_location,geo_location.
and the Classses on this Project is:
1. [ NodeData ](#nodedata)
2. [ EdgeData ](#edge)
3. [ Point3D](#geo)
4. [DWGraph_DS](#DWGraph_DS)
5. [DWGraph_Algo](#DWGraph_Algo)
6. [ NodeLocation](#NodeLocation)
7. [ DirectedWeightedGraphJsonWrapper](#DirectedWeightedGraphJsonWrapper)
8. [ EdgeDataJsonWrapper](#EdgeDataJsonWrapper)
9. [ GeoLocationJsonWrapper](#GeoLocationJsonWrapper)
10. [ NodeDataJsonWrapper](#NodeDataJsonWrapper)

<a name="nodedata"></a>
## 1. NodeData 
This class implements node_data interface , that represents a node in a graph. Each node has its own weight, id and location in a 2 dimensional space. 


<a name="edge"></a>
## 2. EdgeData 
This class implements edge_data interface,representsan edge in a graph. Each EdgeData object has a source and destination node and weight.


<a name="geo"></a>
## 3. Point3D
This class implements geo_location interface
A Point3D object has 3 parameters: x, y, and z. this parameters help locating a node in the 3 dimensional space (in this assignment, 2 dimensional space). 

<a name="DWGraph_DS"></a>
## 4. DWGraph_DS
This class implements directed_weighted_graph interface
This class represents a directional weighted graph.
Each graph builds from nodes that are connected by edges objects. Implements directed_weighted_graph interface.
 
<a name="DWGraph_Algo"></a>
## 5. DWGraph_Algo
This class implements dw_graph_algorithms interface,
This class have some futnction and algorithms on the graph.
Example of futnction and algorithms : save and load,dfs, tarjan and more.

<a name="NodeLocation"></a>
## 6. NodeLocation
This class implements node_location interface
A NodeLocation Class have function that compare x, y and return minX,maxX,minY,maxY.

<a name="DirectedWeightedGraphJsonWrapper"></a>
## 7. DirectedWeightedGraphJsonWrapper
This class 


<a name="EdgeDataJsonWrapper"></a>
## 8. EdgeDataJsonWrapper
This class 


<a name="GeoLocationJsonWrapper"></a>
## 9. GeoLocationJsonWrapper
This class 


<a name="NodeDataJsonWrapper"></a>
## 10. NodeDataJsonWrapper
This class 









<a name="p2"></a>
## Part 2:Pokemon Game

What Is The Pokemon Game?

The game board is built from a graph directed wighted graph, pokemons of two kinds and a agent that have to catch them.
There are 24 levels and 6 differnet types of graphs!
The goal is for the agent/s to find the best way to catch as many pockemons as they can!
to each pokemon has a different value, and as your agent/s eat more pokemons they become faster



1. [ Arena](#ar)
2. [ Agent](#ag)
3. [ Pokemon](#pok)
4. [Ex2](#ex)
5. [Client](#Client)
6. [MyGameFrame](#gfr)
7. [MyGamePanel](#gpl)
8. [WelcomeGUI](#lbl)

<a name="ar"></a>
## 1. Arena
This class represents a multi Agents Arena which move on a graph and grabs Pokemons. Given class from OOP course.


<a name="ag"></a>
## 2. Agent
This class represents an agent that runs on the graph and catches pokemons. 


<a name="pok"></a>
## 3. Pokemon
This class represents a pokemon in the game.


<a name="ex"></a>
## 4. Ex2
This class is the main class in the second part, which its main method the game starts.

<a name="Client"></a>
## 5. Client
This class represents a 


<a name="gfr"></a>
## 6. MyGameFrame
This class represents a specific adapted JFrame to this project.



<a name="gpl"></a>
## 7. MyGamePanel
This class represents a specific adapted JPanel to this project.



<a name="lbl"></a>
## 8. WelcomeGUI
This class represents a number of functions that relate to the game's start menu and part of the GUI






<a name="run"></a>

## Running the game:

How To Use?
* [Option 1](#Option1)
* [Option 2](#Option2)


<a name="Option1"></a>
## Option 1:
1)First thing you have to do is enter you ID(only 9 numbers- if you enter less or more you will not enter to the game-(you have 3 chance to enter id )) and it would link and connect you to the server to save your score.

LIKE THIS:

<a href="http://www.siz.co.il/"><img src="http://up419.siz.co.il/up1/wgzwokzyjgmz.png" border="0" alt="ID" /></a>




2)Choose a the leve, you want to play
LIKE THIS:



<a href="http://www.siz.co.il/"><img src="http://up419.siz.co.il/up3/tt1hzannzdgi.png" border="0" alt="בחירת שלב" /></a>



3)A screen will open and the fun will begin!
  You will be able to check out your score , how much score the agent/s did, your moves , and the time
  
<a href="http://www.siz.co.il/"><img src="http://up419.siz.co.il/up3/mmyjzlywztmw.png" border="0" alt="agent" /></a>
<a href="http://www.siz.co.il/"><img src="http://up419.siz.co.il/up2/02zymoijuzvt.jpg" border="0" alt="size" /></a>






<a name="Option2"></a>
## Option 2:
Another way to do it is directly from the command line.

to write : java -jar ex2.jar *******(you id)      *(lvl between 0-20)

Example:
<a href="http://www.siz.co.il/"><img src="http://up419.siz.co.il/up3/zzykjnwhymr2.png" border="0" alt="CMD" /></a>












