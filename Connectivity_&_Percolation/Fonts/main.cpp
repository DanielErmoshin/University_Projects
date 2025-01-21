#include "Graph.h"

#include <cstdlib>
#include <ctime> 
#include <fstream>
#include <utility>

using namespace std;

//Passar a format csv per després poder procesar les dades amb R
void pasar_a_csv(const vector<pair<double,double>>& results, string file_name) 
{
    ofstream file(file_name);
    if (file.is_open()) {
        file << "Valor,Indice\n";
        for (const auto& result : results) {
            file << result.first << "," << result.second << "\n";
        }
        file.close();
        cout << "Datos guardados en results.csv" << endl;
    } else {
        cerr << "No se pudo abrir el archivo para escribir." << endl;
    }
}


int main() {
    vector<int> n_list = {50, 100, 250, 500, 1000};
    srand(time(0));
    for (int n: n_list) {
        vector<int> l1;
        vector<int> l2;
        Graph g;
        cout << "Generant graf de " << n << "x" << n << endl;
        vector<pair<double,double>> result;
        int N = n*n;
        for (double q = 0.000; q < 1.001; q += 0.101) {
            cout << "Percolant amb q = " << q << endl;

            //per a percolar amb diferents tipus de grafs decomenteu les següents seccions de codi: per defecte es fa el càlcul per a grafs triangulars
            
            //Per a la percolació d'arestes per a grafs rectangulars
            // g.generatePercolatedRectangularGraphEdges(n, q);
            // g.printVisualRectGrid(n);

            //Per a la percolació de vertex per a grafs rectangulars
            // g.generatePercolatedRectangularGraphVertex(n, q);
            // g.printVisualRectGrid(n);

            //Per a la percolació d'arestes per a grafs triangulars
            g.generatePercolatedTriangularGraphEdges(n, q);
            g.printVisualTriGrid(n);

            //Per a la percolació de vertex per a grafs triangulars
            // g.generatePercolatedTriangularGraphVertex(n, q);
            // g.printVisualTriGrid(n);

            //Per a la percolació d'arestes per a grafs aleatòris
            pair<int,int> ccDS = g.numConnectedComponents();
            cout << "   Nombre de components connexes:  " << ccDS.first << endl;
            cout << "Mida del cluster més gran   " << ccDS.second << endl;
            l2.push_back(ccDS.first);
        }
    }
    return 0;
}
