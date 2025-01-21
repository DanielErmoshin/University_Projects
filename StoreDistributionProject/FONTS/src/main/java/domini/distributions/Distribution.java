package domini.distributions;

import java.util.*;

import domini.models.Prestatgeria;
import domini.models.Producte;
import exceptions.MyException;


/**
 * Aquesta classe representa una distribució de productes en una prestatgeria.
 */
public abstract class Distribution{

    /**
     * Llista amb la distribució de productes.
     */
    protected List<Producte> dist;

    /**
     * Hashmap amb els productes.
     */
    protected final HashMap<Integer, Producte> llistaProductes;

    /**
     * Hashmap amb les prestatgeries.
     */
    protected final HashMap<Integer, Prestatgeria> llistaPrestatges;

    /**
     * Constructora de la classe Distribution.
     * @param llistaProductes Llista de productes.
     * @param llistaPrestatges Llista de prestatgeries.
     */
    public Distribution(HashMap<Integer, Producte> llistaProductes, HashMap<Integer, Prestatgeria> llistaPrestatges) {
        this.dist = new ArrayList<>();
        this.llistaProductes = llistaProductes;
        this.llistaPrestatges = llistaPrestatges;
    }

    /**
     * Constructora de la classe Distribution.
     * Aquesta constructora s'utilitza per a inicialitzar la distribució sense cap paràmetre.
     */
    public Distribution() { //acabar de matizar
        this(null, null);
    }

    /**
     * Calcula la distribució de productes en les prestatgeries.
     */
    abstract public void calcularDist();

    /**
     * Retorna la distribució de productes per ID.
     * @return Distribució de productes per ID.
     */
    public List<List<String>> imprimirPerID() {
        List<Map.Entry<Integer, Prestatgeria>> sortedList = new ArrayList<>(llistaPrestatges.entrySet());
        Collections.sort(sortedList, Map.Entry.comparingByKey());
        List<List<String>> sb = new ArrayList<>();
        boolean even = true;
        Iterator<Producte> productIterator = dist.iterator();
        for(Map.Entry<Integer, Prestatgeria> entry : sortedList) {
            Integer id = entry.getKey();
            Prestatgeria prestatgeria = entry.getValue();
            List<Producte> fila = new ArrayList<>();
            List<String> prestatge_fila = new ArrayList<>();
            for (int i = 0; i < prestatgeria.getNumPrestatges() && productIterator.hasNext(); ++i) {
                fila.add(productIterator.next());
            }
            if (even) {
                for (Producte producte : fila) {
                    prestatge_fila.add(String.valueOf(producte.getId()));
                }
            } else {
                ListIterator<Producte> iterator = fila.listIterator(fila.size());
                while (iterator.hasPrevious()) {
                    Producte producte = iterator.previous();
                    prestatge_fila.add(String.valueOf(producte.getId()));
                }
            }
            even = !even;
            sb.add(prestatge_fila);
        }
        return sb;
    }

    /**
     * Retorna la distribució de productes per nom.
     * @return Distribució de productes per nom.
     */
    public List<List<String>> imprimirPerNom() {
        List<Map.Entry<Integer, Prestatgeria>> sortedList = new ArrayList<>(llistaPrestatges.entrySet());
        Collections.sort(sortedList, Map.Entry.comparingByKey());
        List<List<String>> sb = new ArrayList<>();
        boolean even = true;
        Iterator<Producte> productIterator = dist.iterator();
        for(Map.Entry<Integer, Prestatgeria> entry : sortedList) {
            Integer id = entry.getKey();
            Prestatgeria prestatgeria = entry.getValue();
            List<Producte> fila = new ArrayList<>();
            List<String> prestatge_fila = new ArrayList<>();
            for (int i = 0; i < prestatgeria.getNumPrestatges() && productIterator.hasNext(); ++i) {
                fila.add(productIterator.next());
            }
            if (even) {
                for (Producte producte : fila) {
                    prestatge_fila.add(producte.getNom());
                }
            } else {
                ListIterator<Producte> iterator = fila.listIterator(fila.size());
                while (iterator.hasPrevious()) {
                    Producte producte = iterator.previous();
                    prestatge_fila.add(producte.getNom());
                }
            }
            even = !even;
            sb.add(prestatge_fila);
        }
        return sb;
    }

    /**
     * Modifica la posicio de dos productes en la distribució.
     * @param id1 Identificador del primer producte.
     * @param id2 Identificador del segon producte.
     * @throws MyException Es llença si algun dels productes no es troba a la distribució.
     */
    public void modificarDistribucio(int id1, int id2) throws MyException {
        Producte p1 = llistaProductes.get(id1);
        Producte p2 = llistaProductes.get(id2);
        int index1 = dist.indexOf(p1);
        int index2 = dist.indexOf(p2);
        if (index1 == -1 && index2 == -1)
            throw new MyException("Products with IDs " + id1 + " and " + id2 + " are not present in the distribution.");
        if (index1 == -1)
            throw new MyException("Product with ID " + id1 + " is not present in the distribution.");

        if (index2 == -1)
            throw new MyException("Product with ID " + id2 + " is not present in the distribution.");

        dist.set(index1, p2);
        dist.set(index2, p1);
    }
}