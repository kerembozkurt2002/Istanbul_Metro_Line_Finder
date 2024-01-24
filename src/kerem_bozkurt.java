import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.Color;
import java.awt.Font;


/**
 * Program finds the path between two stations and makes an animation with StdDraw Library
 * @author Kerem Bozkurt
 * @since Date: 27.03.2023
 */
public class kerem_bozkurt {
    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("C:\\Users\\Kerem\\IdeaProjects\\Metro\\Text\\coordinates.txt");
        Scanner textReading = new Scanner(file);
        String text = "";
        int line = 0;
        while (textReading.hasNextLine()) {
            text += textReading.nextLine();
            text += "\n";
            line += 1;
        }
        // Takes the input from "coordinates.text" and keep them in text string

        String [] allInfoOneArray= text.strip().split("\n");
        String [][] totalMetroLinesArray= new String[10][];
        String[][] totalMetroArrayPauses = new String[10][];
        int num=0;
        // Makes lines, stations and coordinates Arrays (When I have started to make the assignment I don't know I can use Arraylists so I made Arrays to keep data in beginnin)
        for (int i =0; i<line-7;i++) {
            if (i % 2 == 0) {
                String[] tempt = allInfoOneArray[i].split(" ");
                totalMetroLinesArray[num] = tempt.clone();
            } else {
                String[] tempt = allInfoOneArray[i].split(" ");
                totalMetroArrayPauses[num] = tempt.clone();
                num += 1;
            }
        }

;
        String [] onlyMetroLinesNames= new String[10];
        for (int i=0;i<totalMetroLinesArray.length;i++){
            onlyMetroLinesNames[i]=totalMetroLinesArray[i][0];
        }


        String [][] onlyPausesNames=new String[10][];
        for (int i =0; i<totalMetroArrayPauses.length;i++){
            int numOfPauses= (totalMetroArrayPauses[i].length)/2;
            onlyPausesNames[i]= new String[numOfPauses];
            for (int i2=0; i2<numOfPauses;i2++){
                String tempt= totalMetroArrayPauses[i][i2*2];
                if (tempt.startsWith("*")){
                    String tempt2 =tempt.substring(1,tempt.length());
                    onlyPausesNames[i][i2]=tempt2;
                }
                else {
                    onlyPausesNames[i][i2]=totalMetroArrayPauses[i][i2*2];
                }
            }
        }


        String[][] onlyCoordinates=new String[10][];
        for (int i =0; i<totalMetroArrayPauses.length;i++){
            int numOfPauses= (totalMetroArrayPauses[i].length)/2;
            onlyCoordinates[i]= new String[numOfPauses];
            for (int i2=0; i2<numOfPauses;i2++){
                onlyCoordinates[i][i2]= totalMetroArrayPauses[i][i2*2+1];
            }
        }

        // Makes breakpoint Array
        String [][] breakpoints= new String[7][];
        for (int i=20;i<line;i++){
            String [] tempt =allInfoOneArray[i].split(" ");
            breakpoints[i-20]=tempt.clone();
        }

        //Takes station inputs
        Scanner input = new Scanner(System.in);
        String startPause=input.nextLine();
        String endPause=input.nextLine();

        // Controls the stations whether they are valid or not
        boolean controlStart=isStationValid(onlyPausesNames,startPause);
        boolean controlEnd=isStationValid(onlyPausesNames,endPause);


        if (controlStart&&controlEnd) {

            // Making arrayLists to use in algorithm, the lists' explanations are in below
            ArrayList<ArrayList<String>>graph=new ArrayList<ArrayList<String>>();
            ArrayList<String>myQueue=new ArrayList<>();
            ArrayList<ArrayList<String>>checkList=new ArrayList<ArrayList<String>>();
            ArrayList<ArrayList<String>>parentList=new ArrayList<ArrayList<String>>(); // first element is child, second is parent,

            ArrayList<String>breakpointsNames=new ArrayList<>();
            for (String[] breakpoint : breakpoints) {
                breakpointsNames.add(breakpoint[0]);
            }



            // Makes stations Arraylist to keep all stations in one list
            ArrayList<String>stations=new ArrayList<>();
            for (int i=0;i<onlyPausesNames.length;i++){
                for (int j=0;j<onlyPausesNames[i].length;j++){
                    if(!(stations.contains(onlyPausesNames[i][j]))){
                        stations.add(onlyPausesNames[i][j]);
                    }
                }
            }

            //Makes a checkList to control, it is a multidimensional and for every station it tooks station Name and ”no” only  for the end station it tooks “yes”
            for (int i=0;i<stations.size();i++){
                ArrayList<String>temptChech=new ArrayList<>();
                if(stations.get(i).equals(endPause)){
                    temptChech.add(stations.get(i));
                    temptChech.add("yes");
                    checkList.add(temptChech);
                }
                else {
                    temptChech.add(stations.get(i));
                    temptChech.add("no");
                    checkList.add(temptChech);
                }
            }

            // It is same with the checkList only difference is it tooks "no" for end station too
            for (int i=0;i<stations.size();i++){
                ArrayList<String>temptChech=new ArrayList<>();
                temptChech.add(stations.get(i));
                temptChech.add("no");
                parentList.add(temptChech);
            }

            //Adds end station to Queue
            myQueue.add(endPause);



            // Creating the graph, it tooks all stations with neihgbours of that station
            for (int i=0;i<onlyPausesNames.length;i++){
                String [] stationList=onlyPausesNames[i];
                for(int j=0;j<stationList.length;j++){
                    String station=stationList[j];
                    if ((breakpointsNames.contains(station))&&(graphContains(station,graph))){
                        for (int k=0; k<graph.size();k++){
                            if(station.equals(graph.get(k).get(0))){

                                if (j == 0) {
                                    graph.get(k).add(stationList[j + 1]);
                                } else if (j == stationList.length-1) {
                                    graph.get(k).add(stationList[j - 1]);
                                } else {
                                    graph.get(k).add(stationList[j - 1]);
                                    graph.get(k).add(stationList[j + 1]);
                                }
                            }
                        }

                    }
                    else {
                        ArrayList<String>vertex=new ArrayList<>();
                        vertex.add(station);
                        if (j==0){
                            vertex.add(stationList[j+1]);
                        }
                        else if (j==stationList.length-1){
                            vertex.add(stationList[j-1]);
                        }
                        else {
                            vertex.add(stationList[j-1]);
                            vertex.add(stationList[j+1]);
                        }
                        graph.add(vertex);
                    }

                }
            }
            //While the queue is not empty, remove the first element from the queue.
            while (!(myQueue.isEmpty())){
                //For the current element, find all its neighbors (stations that can be reached directly from it).
                ArrayList<String> vertex =findVertex(graph,myQueue.get(0));
                myQueue.remove(0);

                //For each neighbor, if it has not been visited, mark it as visited in checkList, add it to the queue, and record its parent
                for(int j=1;j<vertex.size();j++){
                    ArrayList<Integer> info =marked(checkList,vertex.get(j));
                    if(info.get(0)==0) {
                        ArrayList<String> temptArraylist = new ArrayList<>();
                        temptArraylist.add(vertex.get(j));
                        temptArraylist.add("yes");
                        checkList.set(info.get(1), temptArraylist);

                        myQueue.add(vertex.get(j));
                        ArrayList<String> temptArrayList2=new ArrayList<>();
                        temptArrayList2.add(vertex.get(j));
                        temptArrayList2.add(vertex.get(0));
                        parentList.set(info.get(1),temptArrayList2);
                    }
                }
            }

            ArrayList<String>finalPath=new ArrayList<>();
            String destination=startPause;
            finalPath.add(startPause);
            int validPath=1;
            //If the start station is found, terminate the search
            while (!(destination.equals(endPause))){
                destination=findParent(parentList,destination);
                if(destination.equals("no")){
                    validPath=0;
                    break;
                }
                finalPath.add(destination);

            }
            //Writes the path of two stations to console
            for (int i=0;i<finalPath.size();i++){
                System.out.println(finalPath.get(i));
            }
            //Makes an animation with StdDraw Library
            if (validPath == 1) {
                for (int i1 = 0; i1 < finalPath.size(); i1++) {
                        StdDraw.setCanvasSize(1024, 482);
                        // Organizes the canvas and enables double buffering to make faster animations
                        StdDraw.setXscale(0, 1024);
                        StdDraw.setYscale(0, 482);
                        StdDraw.enableDoubleBuffering();
                        int counter = 0;
                        int pauseDuration = 300;
                        ArrayList<ArrayList<String>> usedStations = new ArrayList<ArrayList<String>>();

                        while (true) {
                            StdDraw.clear();
                            StdDraw.picture(512, 242, "background.jpg"); //taking pivture for background
                            for (int i = 0; i < onlyPausesNames.length; i++) {
                                //Finds the line's RGB values
                                String[] temptRGB = totalMetroLinesArray[i][1].split(",");
                                int red = Integer.parseInt(temptRGB[0]);
                                int green = Integer.parseInt(temptRGB[1]);
                                int blue = Integer.parseInt(temptRGB[2]);
                                Color myLineColor = new Color(red, green, blue);
                                for (int i2 = 0; i2 < onlyPausesNames[i].length - 1; i2++) {
                                    //Makes the every metro lines with their special colour
                                    String[] temptCoordinatesFirst = onlyCoordinates[i][i2].split(",");
                                    String[] temptCoordinatesSecond = onlyCoordinates[i][i2 + 1].split(",");
                                    int x1 = Integer.parseInt(temptCoordinatesFirst[0]);
                                    int y1 = Integer.parseInt(temptCoordinatesFirst[1]);
                                    int x2 = Integer.parseInt(temptCoordinatesSecond[0]);
                                    int y2 = Integer.parseInt(temptCoordinatesSecond[1]);
                                    StdDraw.setPenRadius(0.012);
                                    StdDraw.setPenColor(myLineColor);
                                    StdDraw.line(x1, y1, x2, y2);
                                }
                            }
                            for (int i = 0; i < onlyPausesNames.length; i++) {
                                for (int i2 = 0; i2 < onlyPausesNames[i].length; i2++) {
                                    //Finds the coordinates of stations and makes the stations as circle in animation
                                    String[] temptCoordinates = onlyCoordinates[i][i2].split(",");
                                    int x = Integer.parseInt(temptCoordinates[0]);
                                    int y = Integer.parseInt(temptCoordinates[1]);
                                    StdDraw.setPenRadius(0.01);
                                    StdDraw.setPenColor(Color.white);
                                    StdDraw.filledCircle(x, y, 2);
                                }
                            }


                            for (int i = 0; i < totalMetroArrayPauses.length; i++) {
                                for (int j = 0; j < totalMetroArrayPauses[i].length; j++) {
                                    if (totalMetroArrayPauses[i][j].startsWith("*")) {
                                        // Finds the stations which should be written in animation and writes them
                                        String[] temptCoordinat = onlyCoordinates[i][j / 2].split(",");
                                        int x = Integer.parseInt(temptCoordinat[0]);
                                        int y = Integer.parseInt(temptCoordinat[1]) + 5;
                                        StdDraw.setPenColor(Color.black);
                                        StdDraw.setFont(new Font("Helvetica", Font.BOLD, 8));
                                        String tempt = totalMetroArrayPauses[i][j];
                                        String tempt2 = tempt.substring(1, tempt.length());
                                        StdDraw.text(x, y, tempt2);

                                    }
                                }
                            }
                            int x = 0;
                            int y = 0;
                            for (int i = 0; i < onlyPausesNames.length; i++) {
                                for (int j = 0; j < onlyPausesNames[i].length; j++) {
                                    if (onlyPausesNames[i][j].equals(finalPath.get(counter))) {
                                        // Collects the coordinates of stations which have been visited until current station
                                        String[] tempt = onlyCoordinates[i][j].split(",");
                                        x = Integer.parseInt(tempt[0]);
                                        y = Integer.parseInt(tempt[1]);
                                        ArrayList<String> tempt2 = new ArrayList<>();
                                        tempt2.add(tempt[0]);
                                        tempt2.add(tempt[1]);
                                        usedStations.add(tempt2);
                                        break;
                                    }
                                }
                            }
                            StdDraw.setPenRadius(0.02);
                            StdDraw.setPenColor(StdDraw.PRINCETON_ORANGE);
                            for (int i = 0; i < usedStations.size(); i++) {
                                //  Draws the visited stations until current
                                int xIndex = Integer.parseInt(usedStations.get(i).get(0));
                                int yIndex = Integer.parseInt(usedStations.get(i).get(1));
                                StdDraw.filledCircle(xIndex, yIndex, 2);
                            }
                            // Draws the current station and continues to next step
                            StdDraw.setPenRadius(0.02);
                            StdDraw.setPenColor(StdDraw.PRINCETON_ORANGE);
                            StdDraw.filledCircle(x, y, 5);
                            StdDraw.show();
                            StdDraw.pause(pauseDuration);
                            if (counter < finalPath.size() - 1) {
                                counter += 1;

                        }
                    }
                }
            }
            else {
                // If two stations are not connected it prints the statement
                System.out.println("These two stations are not connected");
            }
        }
        else {
            // If one or both of the stations are not valid stations it prints the statement
            System.out.println("The station names provided are not present in this map.");
        }
    }



    private static boolean isStationValid (String [][] stationList,String station){
        // Controls the station whether is valid or not
        for (int i=0;i<stationList.length;i++){
            for (int j=0;j<stationList[i].length;j++){
                if(station.equals(stationList[i][j])){
                    return true;
                }
            }
        }
        return false;
    }
    private static String findParent(ArrayList<ArrayList<String>> parentList,String child){
        // If the child(station) has parents collects the parent or gives "no"
        for (int i=0; i<parentList.size();i++){
            if(parentList.get(i).get(0).equals(child)){
                return parentList.get(i).get(1);
            }
        }
        return "no";
    }
    private static ArrayList<String> findVertex(ArrayList<ArrayList<String>> graph, String element){
        //Finds the vertex (station and neighbours of that station)
        for(int i=0;i<graph.size();i++){
            if(graph.get(i).get(0).equals(element)){
                return graph.get(i);
            }
        }
        return graph.get(0);
    }
    private static ArrayList<Integer> marked(ArrayList<ArrayList<String>>checkList,String element){
        // Looks whether the station is visited or not
        ArrayList<Integer>myArrayList=new ArrayList<>();
        myArrayList.add(0);
        myArrayList.add(0);
        for (int i=0;i<checkList.size();i++) {
            if (element.equals(checkList.get(i).get(0))) {
                myArrayList.set(1,i);
                if (checkList.get(i).get(1).equals("yes")) {
                    myArrayList.set(0,1);
                }
                break;
            }
        }
        return myArrayList;
    }
    private static boolean graphContains(String element, ArrayList<ArrayList<String>> graph){
        // Looks the station is in graph or not
        boolean flag=false;
        for (int k=0; k<graph.size();k++){
            String temp=graph.get(k).get(0);
            if(temp.equals(element)){
                flag=true;
                break;
            }
        }
        return flag;
    }
}
