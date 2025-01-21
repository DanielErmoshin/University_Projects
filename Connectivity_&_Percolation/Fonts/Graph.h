#ifndef GRAPH_H
#define GRAPH_H

#include <iostream>
#include <map>
#include <set>
#include <vector>
#include <utility> // Par a std::pair
#include <cmath>   // Per a sqrt
#include "DisjointSet.h"

using namespace std;
 
class Graph {
private:

    enum tipus {
            Random,
            Rect,
            Triangle, 
        };

public:

    int numVertex;               // Nombre de vertexs en el graf
    int numEdges;                // Nombre d'arestes en el graf
    map<int, set<int>> AdjList;  // Llista d'adjacència

    tipus forma;
    
    Graph();

    void addEdge(int u, int v);

    void removeEdge(int u, int v);

    void removeVertex(int v);

    bool existsVertex(int v) const;

    void printVisualTriGrid(int n);

    void printVisualRectGrid(int n);

    //Calcula nombre de components connexes
    pair<int,int> numConnectedComponents() const;
    
    //Generació de grafs rectangulars percolats amb probabilitat q
    void generatePercolatedRectangularGraphEdges(int n, double q);
    void generatePercolatedRectangularGraphVertex(int n, double q);

    //Generació de grafs triangulars percolats amb probabilitat q
    void generatePercolatedTriangularGraphVertex(int n, double q);
    void generatePercolatedTriangularGraphEdges(int n, double q);

    //Generació de grafs aleatòris percolats amb probabilitat q
    void generatePercolatedRandomGeometricGraphEdges(int n, double r, double q);



};

#endif // GRAPH_H