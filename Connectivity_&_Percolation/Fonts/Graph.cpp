#include "Graph.h"

Graph::Graph() 
{
    numVertex = 0; 
    forma = Random;
}

void Graph::addEdge(int u, int v) 
{
    AdjList[u].insert(v);
    AdjList[v].insert(u); 
}

void Graph::removeEdge(int u, int v) 
{
    AdjList[u].erase(v);
    AdjList[v].erase(u);
}

void Graph::removeVertex(int v) 
{
    for (int adj : AdjList[v]) {
        AdjList[adj].erase(v);
    }
    AdjList.erase(v);
}

bool Graph::existsVertex(int v) const 
{
    return AdjList.find(v) != AdjList.end();
}

//Per a imprimir visualització gràfica del graf rectangular
void Graph::printVisualRectGrid(int n) {
    for (int i = 0; i < n; ++i) {
        for (int j = 0; j < n; ++j) {
            int currentNode = i * n + j;
            if (existsVertex(currentNode)) {
                cout << currentNode;
                if (j < n - 1 && AdjList[currentNode].find(currentNode + 1) != AdjList[currentNode].end()) {
                cout << " - "; 
                } else if (j < n - 1){
                    cout << "   "; 
                }
            } else {
                int chars = to_string(currentNode).length();
                for (int k = 0; k < chars; ++k) cout << 'x';
                cout << "   "; 
            }
        }
        cout << endl;
        if (i < n - 1) {
            for (int j = 0; j < n; ++j) {
                int currentNode = i * n + j;
                if (existsVertex(currentNode)) {
                    if (AdjList[currentNode].find(currentNode + n) != AdjList[currentNode].end()) {
                        cout << "|";  
                    } else {
                        cout << " ";  
                    }
                    if (j < n - 1) {
                        cout << "   ";
                    }
                } else {
                    cout << "    ";  
                }
            }
            cout << endl;
        }
    }
}

//Per a imprimir visualització gràfica del graf triangular
void Graph::printVisualTriGrid(int h) {
    for (int i = 0; i < h; ++i) {
        for (int k = 0; k < h - i - 1; ++k) {
            cout << "  ";
        }
        for (int j = 0; j <= i; ++j) {
            int currentNode = i * (i + 1) / 2 + j;
            if (existsVertex(currentNode)) {
                cout << currentNode;
                if (j < i && AdjList[currentNode].find(currentNode + 1) != AdjList[currentNode].end()) {
                    cout << " - "; 
                }
                else if (j < i) {
                    cout << "   ";  
                }
            }
            else {
                int chars = to_string(currentNode).length();
                for (int k = 0; k < chars; ++k) cout << 'x';
                cout << "   ";
            }
        }
        cout << endl;
        if (i < h - 1) {
            for (int k = 0; k < h - i - 2; ++k) {
                cout << "  ";
            }

            for (int j = 0; j <= i; ++j) {
                int currentNode = i * (i + 1) / 2 + j;
                if (existsVertex(currentNode)) {
                    if (AdjList[currentNode].find((i + 1) * (i + 2) / 2 + j) != AdjList[currentNode].end()) 
                    {
                        cout << "/ ";  
                    }
                    else 
                    {
                        cout << "  ";
                    }
                    if (AdjList[currentNode].find((i + 1) * (i + 2) / 2 + j + 1) != AdjList[currentNode].end()) {
                        cout << "\\"; 
                    }
                    else {
                        cout << " ";  
                    }
                }
                if (j < i) {
                    cout << "   ";
                }
            }
            cout << endl;
        }
    }
}


pair<int,int> Graph::numConnectedComponents() const {
    DisjointSet dsu(AdjList, numVertex);
    for (const auto& par : AdjList) // Iterar sobre les arestes i unir els nodes
    {
        int u = par.first;
        for (int v : par.second) {
            dsu.unionSets(u, v);  // Unir els dos nodes
        }
    }
    return dsu.countComponents(numVertex);  // Comptar els conjunts disjunts, es a dir, les components connexes
}



void Graph::generatePercolatedRectangularGraphEdges(int n, double q)
{
    AdjList.clear();
    numVertex = n * n;
    forma = Rect;
    for (int i = 0; i < n; ++i) {
        for (int j = 0; j < n; ++j) {
            int currentNode = i * n + j;
            AdjList.insert({ currentNode, set<int>() }); //aixo no fa res si currentNode ja existeix a la llista d'adjacència
            if (j < n - 1) {
                int rightNode = i * n + (j + 1);
                double random_value = (double)rand() / RAND_MAX;
                if (random_value >= (1 - q))
                {
                    addEdge(currentNode, rightNode);
                }
            }
            if (i < n - 1) {
                int bottomNode = (i + 1) * n + j;
                double random_value = (double)rand() / RAND_MAX;
                if (random_value >= (1 - q))
                {
                    addEdge(currentNode, bottomNode);
                }
            }
        }
    }
}


// Genera graph percolat per vertex rectangular amb probabilitat q
void Graph::generatePercolatedRectangularGraphVertex(int n, double q)
{
    AdjList.clear();
    numVertex = n * n;
    forma = Rect;
    for (int i = 0; i < n; ++i) {
        for (int j = 0; j < n; ++j) {
            int currentNode = i * n + j;
            double random_value = (double)rand() / RAND_MAX;
            if (random_value >= (1 - q))
            {
                AdjList.insert({ currentNode,set<int>() });
                if (i > 0 and AdjList.find(currentNode - n) != AdjList.end())
                {
                    addEdge(currentNode - n, currentNode);
                }
                if (j > 0 and AdjList.find(currentNode - 1) != AdjList.end())
                {
                    addEdge(currentNode - 1, currentNode);
                }
            }
        }
    }
}


// Genera graph percolat per arestes triangular amb probabilitat q
void Graph::generatePercolatedTriangularGraphEdges(int h, double q)
{
    AdjList.clear();
    numVertex = h * (h + 1) / 2;
    forma = Triangle;
    for (int i = 0; i < h; ++i) {
        for (int j = 0; j <= i; ++j) {
            int currentNode = i * (i + 1) / 2 + j;
            AdjList.insert({ currentNode, set<int>() }); //aixo no fa res si currentNode ja existeix a la llista d'adjacència
            if (j < i) // Conectar horizontalment amb el seguent node a la mateixa fila
            {
                int rightNode = currentNode + 1;
                double random_value = (double)rand() / RAND_MAX;
                if (random_value >= (1 - q))
                {
                    addEdge(currentNode, rightNode);
                }
            }
            if (i < h - 1) // Conectar amb els nodes de la fila inferior
            {
                int bottomLeftNode = (i + 1) * (i + 2) / 2 + j;
                double random_value = (double)rand() / RAND_MAX;
                if (random_value >= (1 - q))
                {
                    addEdge(currentNode, bottomLeftNode);
                }
                int bottomRightNode = (i + 1) * (i + 2) / 2 + j + 1;
                random_value = (double)rand() / RAND_MAX;
                if (random_value >= (1 - q))
                {
                    addEdge(currentNode, bottomRightNode);
                }
            }
        }
    }
}

// Genera graph percolat per vertex triangular amb probabilitat q
void Graph::generatePercolatedTriangularGraphVertex(int h, double q)
{
    AdjList.clear();
    numVertex = h * (h + 1) / 2;
    forma = Triangle;
    for (int i = 0; i < h; ++i) {
        for (int j = 0; j <= i; ++j) {
            int currentNode = i * (i + 1) / 2 + j;
            double random_value = (double)rand() / RAND_MAX;
            if (random_value >= (1 - q))
            {
                AdjList.insert({ currentNode,set<int>() });
                // Connectar con el nodo de la izquierda
                if (j > 0 and AdjList.find(currentNode - 1) != AdjList.end())
                {  
                    addEdge(currentNode, currentNode - 1);
                }
                // Conectar con los nodos de la fila superior
                if (j > 0 and AdjList.find(currentNode - i - 1) != AdjList.end())
                {
                    addEdge(currentNode, currentNode - i - 1);
                }
                if (j < i and AdjList.find(currentNode - i) != AdjList.end())
                {
                    addEdge(currentNode, currentNode - i);
                }
            }
        }
    }
}

// Genera graph aleatori percolat per arestes amb probabilitat q
void Graph::generatePercolatedRandomGeometricGraphEdges(int n, double r, double q) 
{
    AdjList.clear();
    numVertex = n;
    forma = Random; 
    vector<pair<double, double>> coords(n); // Crear un vector de pares de coordenadas (x, y) para cada nodo
    for (int i = 0; i < n; ++i) // Genera les coordenades aleatories dels nodes
    {
        coords[i] = make_pair((double)rand() / RAND_MAX, (double)rand() / RAND_MAX);
    }                               
    for (int i = 0; i < n; ++i) // Calcular les distancies entre els dos nodes y conectarlos si están a una distancia menor que r
    {
        for (int j = i + 1; j < n; ++j) {
            double random_value = (double)rand() / RAND_MAX;
            if (random_value >= (1 - q))
            {
                double dist = sqrt(pow(coords[i].first - coords[j].first, 2) + pow(coords[i].second - coords[j].second, 2));
                if (dist < r) {
                    addEdge(i, j);
                }
            }
        }
    }
}


