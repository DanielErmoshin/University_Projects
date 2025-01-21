#ifndef DISJOINT_SET_HH
#define DISJOINT_SET_HH

#include <vector>
#include <set>
#include <string>
#include <map>

using namespace std;

struct node {
	int parent;
	int rank;
};

class DisjointSet {
public:
    //Constructor que funciona com la crida Makeset de Union-Find
    DisjointSet(const map<int, set<int>>& AdjList, int n);

    int find(int v);

    void unionSets(int x,int y);

    pair<int, int> countComponents(int n);

private:
    //vector de nodes, representant els vertexs de cada graf
    vector<node> dsuf;
    
    //connecta dos conjunts disjunts utilitzant "union by rank"
    void link(int x, int y);
};

#endif 
