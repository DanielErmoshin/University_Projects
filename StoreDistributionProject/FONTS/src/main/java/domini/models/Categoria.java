package domini.models;



/**
 * Aquesta classe representa una categoria de productes.
 * Cada categoria té un nom, una descripció i un número de productes associats.
 */
public class Categoria {
    /**
     * El nom de la categoria.
     */
    private String nom;
    /**
     * La descripció de la categoria.
     */
    private String descripcio;
    /**
     * El número de productes associats a la categoria.
     */
    private int numProducte;

    /**
     * Crea una nova categoria amb el nom i la descripció donats.
     * @param nom El nom de la categoria.
     * @param descripcio La descripció de la categoria.
     */
    public Categoria(String nom, String descripcio) {
        this.nom = nom;
        this.descripcio = descripcio;
        this.numProducte = 0;
    }

    /**
     * Obte el nom de la categoria.
     * @return El nom de la categoria.
     */
    public String getNom() {
        return nom;
    }

    /**
     * Obte el número de productes associats a la categoria.
     * @return El número de productes associats a la categoria.
     */
    public int getNumProducte() {
        return numProducte;
    }

    /**
     * Obte la descripció de la categoria.
     * @return La descripció de la categoria.
     */
    public String getDescripcio() {
        return descripcio;
    }

    /**
     * Modifica el nom de la categoria.
     * @param nom El nou nom de la categoria.
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Modifica la descripció de la categoria.
     * @param descripcio La nova descripció de la categoria.
     */
    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    /**
     * Incrementa el número de productes associats a la categoria.
     */
    public void incrementarNumProducte() {
        this.numProducte++;
    }

    /**
     * Decrementa el número de productes associats a la categoria.
     */
    public void decrementarNumProducte() {
        if (this.numProducte > 0) {
            this.numProducte--;
        }
    }
}