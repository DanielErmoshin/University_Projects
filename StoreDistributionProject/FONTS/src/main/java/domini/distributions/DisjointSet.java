package domini.distributions;
import java.util.HashMap;
import domini.models.Producte;

/**
 * Aquesta classe implementa l'estructura de dades Union-Find
 */
public class DisjointSet
{
    /**
     *  Array que representa l'estructura del conjunt disjunt
     */
    int[] ds;

    /**
     * Convertim la llista de productes en un conjunt disjunt
     * @param llista_productes Llista de productes
     * @return HashMap amb la correspondència entre l'identificador del producte i la seva posició en l'estructura de dades
     */
    public HashMap<Integer, Integer> makeSet(HashMap<Integer, Producte> llista_productes) {
        HashMap<Integer, Integer> productIndexMap = new HashMap<>();
        ds = new int[llista_productes.size()];
        int i = 0;
        for (Integer productId : llista_productes.keySet()) {
            ds[i] = -1;
            productIndexMap.put(productId, i++);
        }
        return productIndexMap;
    }

    /**
     * Retorna el conjunt al que pertany l'element
     * @param index Identificador de l'element
     * @return Identificador del conjunt al que pertany l'element
     */
    public int find(Integer index) {
        if (ds[index] <= -1)
            return index;
        else
        {
            int parent =  find(ds[index]);
            ds[index] = parent; //path compression
            return parent;
        }
    }

    /**
     * Uneix dos conjunts
     * @param a Identificador del primer conjunt
     * @param b Identificador del segon conjunt
     * @return Cert si s'ha realitzat la unió, fals altrament
     */
    public boolean merge(Integer a, Integer b) {
        int setA = find(a);
        int setB = find(b);
        if (setA != setB)
        {   //union by rank
            if (ds[setA] <= ds[setB])
            {
                ds[setA] += ds[setB];
                ds[setB] = setA;
            }
            else
            {
                ds[setB] += ds[setA];
                ds[setA] = setB;
            }
            return true;
        }
        else
            return false;
    }
}
