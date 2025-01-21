package domini.models;

import exceptions.MyException;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Otman Ezzayat
 * Tests per validar el comportament de la classe Plantilla.
*/
public class TestPlantilla {

    /**

     Objecte de la prova: Verificar que el constructor de Plantilla inicialitza correctament.

     Valors estudiats: (Caixa Blanca) Es crea un model Plantilla amb el nom "PlantillaTest". Es comprova que el nom
     es correcte, el path es null, i que les llistes de productes, categories i prestatgeries estan buides.

     Efectes estudiats: Inicialitzacio correcta dels atributs interns de la classe.

     Operativa: S'executa el constructor amb un nom donat i es valida que l'objecte estigui buit.

     */
    @Test
    public void testConstructorPlantilla() {
        Plantilla plantilla = new Plantilla("PlantillaTest");
        assertEquals("PlantillaTest", plantilla.getNom());
        assertTrue(plantilla.getProductes().isEmpty());
        assertTrue(plantilla.getCategories().isEmpty());
        assertTrue(plantilla.getPrestatges().isEmpty());
        assertEquals(0, plantilla.numTotalPrestatges());
    }

    /**

     Objecte de la prova: Comprovar que un producte es pot crear correctament.

     Altres elements integrats a la prova: S'integren les funcionalitats de creacio de categories i plantilles.

     Valors estudiats: (Caixa Blanca) Es crea una categoria amb nom "CategoriaTest". Despres es crea un producte a aquella
     categoria. Es comprova que el producte s'ha afegit correctament a la llista interna.

     Efectes estudiats: Addicio del producte a la llista interna i associacio amb la categoria.

     Operativa: Es creen primer la categoria i despres el producte, validant l'estat de la llista de productes.

     */
    @Test
    public void testCrearProducte() throws MyException {
        Plantilla plantilla = new Plantilla("PlantillaTest");
        plantilla.crearCategoria("CategoriaTest");

        plantilla.crearProducte(1, "ProducteTest", "CategoriaTest", 10.5);
        HashMap<Integer, Producte> productes = plantilla.getProductes();

        assertEquals(1, productes.size());
        assertTrue(productes.containsKey(1));
        assertEquals(1, productes.get(1).getId());
        assertEquals("ProducteTest", productes.get(1).getNom());
        assertEquals("CategoriaTest", productes.get(1).getCategoria().getNom());
        assertEquals(10.5, productes.get(1).getPreu(), 0.001);

        assertEquals(1, plantilla.getCategories().get("CategoriaTest").getNumProducte());
    }

    /**

     Objecte de la prova: Comprovar que no es por crear un producte ja existent i que es llança l'excepcio corresponent.

     Altres elements integrats a la prova: S'integren les funcionalitats de creacio de categories i plantilles.

     Valors estudiats: (Caixa Negre) Es tracta de crear un producte amb ID 1 i crear-ne un altre amb la mateixa ID 1
     dins de la llista de productes i veure que l'usuari rep una excepcio.

     Efectes estudiats: Maneig correcte de l'excepcio per a intents de creacio d'un producte ja existent.

     Operativa: Es prova de crear un producte amb la mateixa ID d'un producte existent i es comprova que es llença
     l'excepcio MyException.

     */
    @Test(expected = MyException.class)
    public void testCrearProducteExistent() throws MyException {
        Plantilla plantilla = new Plantilla("PlantillaTest");
        plantilla.crearCategoria("CategoriaTest");

        plantilla.crearProducte(1, "ProducteTest", "CategoriaTest", 10.5);
        plantilla.crearProducte(1, "ProducteTest2", "CategoriaTest", 10.5);
    }
    
    /**

     Objecte de la prova: Comprovar que es pot eliminar un producte existent de la llista interna.

     Altres elements integrats a la prova: S'integren les funcionalitats de creacio de categories i plantilles.

     Valors estudiats: (Caixa Blanca) Es creen dos productes amb ID 1 i 2, respectivament. Es comprova que, despres
     d'eliminar-los, la llista interna de productes es mante coherent.

     Efectes estudiats: Eliminacio d'elements d'una col·leccio interna i manteniment correcte de l'estat.

     Operativa: Es creen els productes, s'eliminen per ID i es valida l'estat de la llista de productes despres de
     cada operacio.

     */
    @Test
    public void testEliminarProducte() throws MyException {
        Plantilla plantilla = new Plantilla("PlantillaTest");
        plantilla.crearCategoria("CategoriaTest");
        plantilla.crearProducte(1, "ProducteTest", "CategoriaTest", 10.5);
        plantilla.crearProducte(2, "ProducteTest2", "CategoriaTest", 10.5);

        plantilla.eliminarProducte(1);
        assertEquals(1, plantilla.getProductes().size());
        plantilla.eliminarProducte(2);
        assertTrue(plantilla.getProductes().isEmpty());
    }

    /**

     Objecte de la prova: Comprovar que un producte no existent no es pot eliminar, i que es llança l'excepcio
     corresponent.

     Altres elements integrats a la prova: S'integren les funcionalitats de creacio de categories i plantilles.

     Valors estudiats: (Caixa Negre) Es tracta d'eliminar un producte amb ID 1 que no existeix dins de la llista
     de productes.

     Efectes estudiats: Maneig correcte de l'excepcio per a intents d'eliminacio d'un producte inexistent.

     Operativa: Es prova d'eliminar un producte inexistent i es comprova que es llença l'excepcio MyException.

     */
    @Test(expected = MyException.class)
    public void testEliminarProducteInexistent() throws MyException {
        Plantilla plantilla = new Plantilla("PlantillaTest");
        plantilla.eliminarProducte(1);
    }

    /**

     Objecte de la prova: Comprovar que la modificacio d'un producte existent s'executa correctament.

     Altres elements integrats a la prova: S'integren les funcionalitats de creacio de categories i plantilles.

     Valors estudiats: (Caixa Blanca) Es crea un producte amb ID 1, nom "ProducteTest" i preu 10.5. Es modifica el nom
     a "ProducteModificat" i el preu a 15.0, validant que els canvis s'apliquen correctament comprovant els valors interns.

     Efectes estudiats: Actualitzacio d'atributs d'un objecte dins d'una col·leccio interna.

     Operativa: Es crea el producte, es modifica i es comproven els atributs actualitzats.

     */
    @Test
    public void testModificarProducte() throws MyException {
        Plantilla plantilla = new Plantilla("PlantillaTest");
        plantilla.crearCategoria("CategoriaTest");
        plantilla.crearProducte(1, "ProducteTest", "CategoriaTest", 10.5);

        plantilla.modificarProducte(1, "ProducteModificat", 15.0);
        Producte producte = plantilla.getProductes().get(1);

        assertEquals("ProducteModificat", producte.getNom());
        assertEquals(15.0, producte.getPreu(), 0.01);
    }

    /**

     Objecte de la prova: Comprovar que un producte no existent no es pot modificar, i que es llança l'excepcio
     corresponent.

     Altres elements integrats a la prova: S'integren les funcionalitats de creacio de categories i plantilles.

     Valors estudiats: (Caixa Negre) Es tracta de modificar un producte amb ID 1 que no existeix dins de la llista de productes.
     i veure que l'usuari rep una excepcio.

     Efectes estudiats: Maneig correcte de l'excepcio per a intents de modificacio d'un producte inexistent.

     Operativa: Es prova de modificar un producte inexistent i es comprova que es llença l'excepcio MyException.

     */
    @Test(expected = MyException.class)
    public void testModificarProducteInexistent() throws MyException {
        Plantilla plantilla = new Plantilla("PlantillaTest");
        plantilla.modificarProducte(1, "ProducteModificat", 15.0);
    }

    /**

     Objecte de la prova: Comprovar que la impressio de la llista de productes s'executa correctament.

     Altres elements integrats a la prova: S'integren les funcionalitats de creacio de categories i plantilles.

     Valors estudiats: (Caixa Negre) Es crean dues categories amb noms CategoriaTest i CategoriaTest2 i tres producte amb ID 1, 2 i 3.
     assignant i s'imprimeix la llista de productes comprovant que l'usuari rep les dades correctes

     Efectes estudiats: Correcta impressio d'un string amb les dades d'una llista interna

     Operativa: Es creen els productes, es fa un assert dels atributs en un string i s'imprimeix per terminal el string.

     */
    @Test
    public void testLlistaProductes() throws MyException {
        Plantilla plantilla = new Plantilla("PlantillaTest");
        plantilla.crearCategoria("CategoriaTest");
        plantilla.crearCategoria("CategoriaTest2");
        plantilla.crearProducte(10, "ProducteTest", "CategoriaTest", 10.5);
        plantilla.crearProducte(30, "ProducteTest3", "CategoriaTest", 10.57);
        plantilla.crearProducte(20, "ProducteTest2", "CategoriaTest2", 15.0);

        List<List<String>>
        expected = List.of(
                List.of("20", "ProducteTest2", "CategoriaTest2", "15.0"),
                List.of("10", "ProducteTest", "CategoriaTest", "10.5"),
                List.of("30", "ProducteTest3", "CategoriaTest", "10.57")
        );
        assertEquals(expected, plantilla.getLlistaProductes());
    }

    /**

     Objecte de la prova: Comprovar que una categoria es pot crear correctament.

     Altres elements integrats a la prova: S'integra la funcionalitat de creacio de plantilla.

     Valors estudiats: (Caixa Blanca) Es crea una categoria amb nom "CategoriaTest". Es comprova que la categoria
     s'ha afegit correctament a la llista interna.

     Efectes estudiats: Addicio de la categoria a la llista interna.

     Operativa: Es crea la categoria i es validen els atributs i l'estat de la llista de categories.

     */
    @Test
    public void testCrearCategoria() throws MyException {
        Plantilla plantilla = new Plantilla("PlantillaTest");
        plantilla.crearCategoria("CategoriaTest");

        HashMap<String, Categoria> categories = plantilla.getCategories();
        assertEquals(1, categories.size());
        assertTrue(categories.containsKey("CategoriaTest"));
        assertEquals(0, categories.get("CategoriaTest").getNumProducte());
        assertEquals(categories.get("CategoriaTest").getDescripcio(), "");
    }

    /**

     Objecte de la prova: Comprovar que no es pot crear una categoria ja existent i que es llança l'excepcio corresponent.

     Altres elements integrats a la prova: S'integra la funcionalitat de creacio de plantilla.

     Valors estudiats: (Caixa Negre) Es crea una categoria amb nom "CategoriaTest" i es crear una altra amb
     el mateix nom. Es comprova que l'usuari rep una excepcio.

     Efectes estudiats: Maneig correcte de l'excepcio per a intents de creacio d'una categoria ja existent.

     Operativa: Es prova de crear una categoria amb el mateix nom d'una categoria existent i es comprova que es llença
     l'excepcio MyException.

     */
    @Test(expected = MyException.class)
    public void testCrearCategoriaExistent() throws MyException {
        Plantilla plantilla = new Plantilla("PlantillaTest");
        plantilla.crearCategoria("CategoriaTest");
        plantilla.crearCategoria("CategoriaTest");
    }

    /**

     Objecte de la prova: Comprovar que una categoria es pot crear correctament amb la descripcio.

     Altres elements integrats a la prova: S'integra la funcionalitat de creacio de plantilla.

     Valors estudiats: (Caixa Blanca) Es crea una categoria amb nom "CategoriaTest" i la descripcio "DescripcioTest".
     Es comprova que la categoria s'ha afegit correctament a la llista interna i que els atributs siguin correctes.

     Efectes estudiats: Addicio de la categoria amb descripcio a la llista interna.

     Operativa: Es crea la categoria amb una descripcio i es validen els atributs i l'estat de la llista de categories.

     */
    @Test
    public void testCrearCategoriaDescripcio() throws MyException {
        Plantilla plantilla = new Plantilla("PlantillaTest");
        plantilla.crearCategoria("CategoriaTest", "DescripcioTest");

        Categoria cat = plantilla.getCategories().get("CategoriaTest");

        assertEquals(1, plantilla.getCategories().size());
        assertTrue(plantilla.getCategories().containsKey("CategoriaTest"));
        assertEquals(0, cat.getNumProducte());
        assertEquals("CategoriaTest", cat.getNom());
        assertEquals("DescripcioTest", cat.getDescripcio());
    }

    /**

     Objecte de la prova: Comprovar que es pot eliminar una categoria existent de la llista interna.

     Altres elements integrats a la prova: S'integra la funcionalitat de creacio de plantilla.

     Valors estudiats: (Caixa Blanca) Es crea una categoria amb nom "CategoriaTest". Es comprova que, despres
     d'eliminar-la, la llista interna de productes es mante coherent.

     Efectes estudiats: Eliminacio d'elements d'una col·leccio interna i manteniment correcte de l'estat.

     Operativa: Es creen la categoria, s'elimina i es valida l'estat de la llista de categoria abans i despres de
     l'operacio.

     */
    @Test
    public void testEliminarCategoria() throws MyException {
        Plantilla plantilla = new Plantilla("PlantillaTest");
        plantilla.crearCategoria("CategoriaTest");
        assertEquals(1, plantilla.getCategories().size());
        assertTrue(plantilla.getCategories().containsKey("CategoriaTest"));
        plantilla.eliminarCategoria("CategoriaTest");

        assertTrue(plantilla.getCategories().isEmpty());
        assertFalse(plantilla.getCategories().containsKey("CategoriaTest"));
    }

    /**

     Objecte de la prova: Comprovar que una categoria amb productes no es pot eliminar, i que es llança l'excepcio
     corresponent.

     Altres elements integrats a la prova: S'integren les funcionalitats de creacio de plantilles i productes.

     Valors estudiats: (Caixa Negre) Es tracta d'eliminar una categoria amb nom Categoria Test que conte un producte
     amb ID 1. Es comprova que l'usuari rep l'excepcio

     Efectes estudiats: Maneig correcte de l'excepcio per a intents d'eliminacio d'una categoria amb productes.

     Operativa: Es prova d'eliminar una categoria amb productes i es comprova que es llença l'excepcio MyException.

     */
    @Test(expected = MyException.class)
    public void testEliminarCategoriaAmbProductes() throws MyException {
        Plantilla plantilla = new Plantilla("PlantillaTest");
        plantilla.crearCategoria("CategoriaTest");
        plantilla.crearProducte(1, "ProducteTest", "CategoriaTest", 10.5);

        plantilla.eliminarCategoria("CategoriaTest");
    }

    /**

     Objecte de la prova: Comprovar que una categoria inexistent no es pot eliminar, i que es llança l'excepcio
     corresponent.

     Altres elements integrats a la prova: S'integra la funcionalitat de creacio de plantilla.

     Valors estudiats: (Caixa Negre) Es tracta d'intentar eliminar una categoria inexistent.
     Es comprova que l'usuari rep una excepcio.

     Efectes estudiats: Maneig correcte de l'excepcio per a intents d'eliminacio d'una categoria inexistent.

     Operativa: Es prova d'eliminar una categoria no creada i es comprova que es llença l'excepcio MyException.

     */
    @Test(expected = MyException.class)
    public void testEliminarCategoriaInexistent() throws MyException {
        Plantilla plantilla = new Plantilla("PlantillaTest");
        plantilla.eliminarCategoria("CategoriaTest");
    }

    /**

     Objecte de la prova: Comprovar que es pot modificar la descripcio d'una categoria existent de la llista interna.

     Altres elements integrats a la prova: S'integra la funcionalitat de creacio de plantilla.

     Valors estudiats: (Caixa Blanca) Es crea una categoria amb nom "CategoriaTest". Es comprova que, despres
     de modificarla, els atributs es mantenen coherents.

     Efectes estudiats: Modificacio d'elements d'una col·leccio interna i manteniment correcte de l'estat.

     Operativa: Es crea la categoria, es modifica la descripcio i es valida l'estat de l'atribut despres de l'operacio.

     */
    @Test
    public void testModificarCategoria() throws MyException {
        Plantilla plantilla = new Plantilla("PlantillaTest");
        plantilla.crearCategoria("CategoriaTest");

        plantilla.modificarCategoria("CategoriaTest", "NovaDescripcioTest");
        Categoria cat = plantilla.getCategories().get("CategoriaTest");

        assertEquals("CategoriaTest", cat.getNom());
        assertEquals("NovaDescripcioTest", cat.getDescripcio());
    }

    /**

     Objecte de la prova: Comprovar que una categoria inexistent no es pot modificar, i que es llança l'excepcio
     corresponent.

     Altres elements integrats a la prova: S'integra la funcionalitat de creacio de plantilla.

     Valors estudiats: (Caixa Negre) Es tracta d'intentar eliminar una categoria inexistent. Es comprova que l'usuari
     rep una excepcio.

     Efectes estudiats: Maneig correcte de l'excepcio per a intents de modificacio d'una categoria inexistent.

     Operativa: Es prova de modificar una categoria no creada i es comprova que es llença l'excepcio MyException.

     */
    @Test(expected = MyException.class)
    public void testModificarCategoriaInexistent() throws MyException {
        Plantilla plantilla = new Plantilla("PlantillaTest");

        plantilla.modificarCategoria("CategoriaTest", "NovaDescripcioTest");
    }

    /**

     Objecte de la prova: Comprovar que la impressio de la llista de categories s'executa correctament.

     Altres elements integrats a la prova: S'integra les funcionalitats de creacio de plantilla i productes.

     Valors estudiats: (Caixa Negre) Es creen tres categories amb noms CategoriaTest, CategoriaTest2 i CategoriaTest3
     amb les seves descripcions i s'afegeix un producte a la segona categoria i dos productes a la tercera categoria.
     i s'imprimeix la llista de productes comprovant que l'usuari rep les dades correctes.

     Efectes estudiats: Correcta impresio d'un string amb les dades d'una llista interna

     Operativa: Es creen les categories, es fa un assert dels atributs en un string i s'imprimeix per terminal el string.

     */
    @Test
    public void testLlistaCategories() throws MyException {
        Plantilla plantilla = new Plantilla("PlantillaTest");

        List<List<String>> expected = List.of();

        assertEquals(expected, plantilla.getLlistaPrestatgeria());
        plantilla.crearCategoria("CategoriaTest", "DescripcioTest");
        plantilla.crearCategoria("CategoriaTest3", "DescripcioTest3");
        plantilla.crearCategoria("CategoriaTest2", "DescripcioTest2");

        plantilla.crearProducte(1, "Test" , "CategoriaTest2", 10);
        plantilla.crearProducte(2, "Test" , "CategoriaTest3", 10);
        plantilla.crearProducte(3, "Test" , "CategoriaTest3", 10);

        List<List<String>> expected2 = List.of(
                List.of("CategoriaTest2", "DescripcioTest2", "1"),
                List.of("CategoriaTest3", "DescripcioTest3", "2"),
                List.of("CategoriaTest", "DescripcioTest", "0")
        );

        assertEquals(expected2, plantilla.getLlistaCategories());
    }

    /**

     Objecte de la prova: Comprovar que una prestatgeria es pot crear correctament.

     Altres elements integrats a la prova: S'integra la funcionalitat de creacio de plantilla.

     Valors estudiats: Es crea una prestatgeria amb ID 1 i 10 prestatges. Es comprova que la prestatgeria s'ha afegit
     correctament.

     Efectes estudiats: (Caixa Blanca) Addicio d'una prestatgeria a la llista interna i assignacio correcta dels seus atributs.

     Operativa: Es crea una prestatgeria i es validen els atributs de l'objecte dins de la llista.

     */
    @Test
    public void testCrearPrestatgeria() throws MyException {
        Plantilla plantilla = new Plantilla("PlantillaTest");
        plantilla.crearPrestatgeria(1, 10);

        HashMap<Integer, Prestatgeria> prestatgeries = plantilla.getPrestatges();
        assertEquals(1, prestatgeries.size());
        assertTrue(prestatgeries.containsKey(1));
        assertEquals(10, prestatgeries.get(1).getNumPrestatges());
    }

    /**

     Objecte de la prova: Comprovar que no es pot crear una prestatgeria ja existent i que es llança l'excepcio corresponent.

     Altres elements integrats a la prova: S'integra la funcionalitat de creacio de plantilla.

     Valors estudiats: (Caixa Negre) Es crea una prestatgeria amb ID 1 i es crear una altra amb
     la mateixa ID. Es comprova que l'usuari rep una excepcio.

     Efectes estudiats: Maneig correcte de l'excepcio per a intents de creacio d'una prestatgeria ja existent.

     Operativa: Es prova de crear una prestatgeria amb la mateixa ID d'una prestatgeria existent i es comprova que es llença
     l'excepcio MyException.

     */
    @Test(expected = MyException.class)
    public void testCrearPrestatgeriaExistent() throws MyException {
        Plantilla plantilla = new Plantilla("PlantillaTest");
        plantilla.crearPrestatgeria(1, 10);
        plantilla.crearPrestatgeria(1, 12);
    }

    /**

     Objecte de la prova: Comprovar que es pot modificar una prestatgeria existent de la llista interna.

     Altres elements integrats a la prova: S'integra la funcionalitat de creacio de plantilla.

     Valors estudiats: (Caixa Blanca) Es crea una prestatgeria amb nom ID 1. Es comprova que, despres
     de modificar-la, els atributs es mantenen coherents.

     Efectes estudiats: Modificacio d'elements d'una col·leccio interna i manteniment correcte de l'estat.

     Operativa: Es crea la prestatgeria, es modifica el numero de prestatges i es valida l'estat de l'atribut
     despres de l'operacio.

     */
    @Test
    public void testModificarPrestatgeria() throws MyException {
        Plantilla plantilla = new Plantilla("PlantillaTest");
        plantilla.crearPrestatgeria(1, 10);
        plantilla.modificarPrestatgeria(1, 20);

        assertEquals(20, plantilla.getPrestatges().get(1).getNumPrestatges());
    }

    /**

     Objecte de la prova: Comprovar que una prestatgeria inexistent no es pot modificar, i que es llança l'excepcio
     corresponent.

     Altres elements integrats a la prova: S'integra la funcionalitat de creacio de plantilla.

     Valors estudiats: (Caixa Negre) Es tracta d'intentar modificar una prestatgeria inexistent. Es comprova que l'usuari
     rep una excepcio.

     Efectes estudiats: Maneig correcte de l'excepcio per a intents de modificacio d'una prestatgeria inexistent.

     Operativa: Es prova de modificar una prestatgeria no creada i es comprova que es llença l'excepcio MyException.

     */
    @Test(expected = MyException.class)
    public void testModificarPrestatgeriaInexistent() throws MyException {
        Plantilla plantilla = new Plantilla("PlantillaTest");
        plantilla.crearPrestatgeria(1, 10);
        plantilla.modificarPrestatgeria(2, 20);
    }

    /**

     Objecte de la prova: Comprovar que es pot eliminar una prestatgeria existent de la llista interna.

     Altres elements integrats a la prova: S'integra la funcionalitat de creacio de plantilla.

     Valors estudiats: (Caixa Blanca) Es crea prestatgeria amb ID 1. Es comprova que, despres
     d'eliminar-la, la llista interna de productes es mante coherent.

     Efectes estudiats: Eliminacio d'elements d'una col·leccio interna i manteniment correcte de l'estat.

     Operativa: Es crea la prestatgeria, s'elimina i es valida l'estat de la llista abans i despres de
     l'operacio.

     */
    @Test
    public void testEliminarPrestatgeria() throws MyException {
        Plantilla plantilla = new Plantilla("PlantillaTest");
        plantilla.crearPrestatgeria(1, 10);
        plantilla.eliminarPrestatgeria(1);
    }

    /**

     Objecte de la prova: Comprovar que una prestatgeria inexistent no es pot eliminar, i que es llança l'excepcio
     corresponent.

     Altres elements integrats a la prova: S'integra la funcionalitat de creacio de plantilla.

     Valors estudiats: (Caixa Negre) Es tracta d'intentar eliminar una prestatgeria inexistent. Es comprova que l'usuari
     rep una excepcio.

     Efectes estudiats: Maneig correcte de l'excepcio per a intents d'eliminacio' d'una prestatgeria inexistent.

     Operativa: Es prova d'eliminar una prestatgeria no creada i es comprova que es llença l'excepcio MyException.

     */
    @Test(expected = MyException.class)
    public void testEliminarPrestatgeriaInexistent() throws MyException {
        Plantilla plantilla = new Plantilla("PlantillaTest");
        plantilla.eliminarPrestatgeria(2);
    }

    /**

     Objecte de la prova: Comprovar que el total de prestatges es calcula correctament per totes les
     prestatgeries creades.

     Altres elements integrats a la prova: S'integra la funcionalitat de creacio de plantilla.

     Valors estudiats: Es creen tres prestatgeries amb diferents nombres de prestatges (10, 30, 15). Es comprova
     que la suma total de prestatges es correcta.

     Efectes estudiats: Calcul correcte de valors a partir d'una col·leccio interna.

     Operativa: Es creen les prestatgeries amb uns valors i es verifica el valor retornat per numTotalPrestatges.

     */
    @Test
    public void testnumTotalPrestatges() throws MyException {
        Plantilla plantilla = new Plantilla("PlantillaTest");

        assertEquals(0, plantilla.numTotalPrestatges());
        plantilla.crearPrestatgeria(1, 10);
        plantilla.crearPrestatgeria(2, 30);
        plantilla.crearPrestatgeria(3, 15);

        assertEquals(55, plantilla.numTotalPrestatges());
    }

    /**

     Objecte de la prova: Comprovar que la impresio de la llista de prestatgeries s'executa correctament.

     Altres elements integrats a la prova: S'integra la funcionalitat de creacio de plantilla.

     Valors estudiats: (Caixa Negre) Es creen tres prestatgeries amb IDs 10, 20 i 30 i s'imprimeix la llista de
     productes comprovant que l'usuari rep les dades correctes.

     Efectes estudiats: Correcta impresio d'un string amb les dades d'una llista interna

     Operativa: Es creen les prestatgeries, es fa un assert dels atributs en un string i s'imprimeix per terminal el string.

     */
    @Test
    public void testLlistaPrestatges() throws MyException {
        Plantilla plantilla = new Plantilla("PlantillaTest");

        List<List<String>> expected = List.of();

        assertEquals(expected, plantilla.getLlistaPrestatgeria());
        plantilla.crearPrestatgeria(10, 10);
        plantilla.crearPrestatgeria(30, 15);
        plantilla.crearPrestatgeria(20, 30);

        List<List<String>> expected2 = List.of(
                List.of("20", "30"),
                List.of("10", "10"),
                List.of("30", "15")
        );

        assertEquals(expected2, plantilla.getLlistaPrestatgeria());
    }

    /**

     Objecte de la prova: Comprovar que una similitud es pot afegir correctament.

     Altres elements integrats a la prova: S'integren les funcionalitats de creacio de plantilla, categoria i productes.

     Valors estudiats: Es creen quatre productes i s'assignen similituds entre el primer i els altres tres, i entre el
     segon i el tercer. Es comprova que aquestes similituds s'han afegit correctament.

     Efectes estudiats: (Caixa Blanca) Addicio de similituds a les llistes internes dels productes.

     Operativa: Es creen diverses similituds i es valida l'existencia dins de la llista.

     */
    @Test
    public void testCrearSimilitud() throws MyException {
        Plantilla plantilla = new Plantilla("PlantillaTest");
        plantilla.crearCategoria("CategoriaTest");
        plantilla.crearProducte(1, "ProducteTest", "CategoriaTest", 10.5);
        plantilla.crearProducte(2, "ProducteTest", "CategoriaTest", 10.5);
        plantilla.crearProducte(3, "ProducteTest", "CategoriaTest", 10.5);
        plantilla.crearProducte(4, "ProducteTest", "CategoriaTest", 10.5);

        plantilla.afegirSimilitud(1,2,0.1);
        plantilla.afegirSimilitud(1,3,0);
        plantilla.afegirSimilitud(1,4,1);
        plantilla.afegirSimilitud(2,3,0.5);

        HashMap<Integer, Producte> productes = plantilla.getProductes();
        assertTrue(productes.get(1).getSimilituds().containsKey(2));
        assertTrue(productes.get(1).getSimilituds().containsKey(3));
        assertTrue(productes.get(1).getSimilituds().containsKey(4));
        assertTrue(productes.get(2).getSimilituds().containsKey(1));
        assertTrue(productes.get(3).getSimilituds().containsKey(1));
        assertTrue(productes.get(4).getSimilituds().containsKey(1));

        assertTrue(productes.get(2).getSimilituds().containsKey(3));
        assertTrue(productes.get(3).getSimilituds().containsKey(2));

        assertEquals(0.1, productes.get(1).getSimilituds().get(2), 0.001);
        assertEquals(0.1, productes.get(2).getSimilituds().get(1), 0.001);
        assertEquals(0, productes.get(1).getSimilituds().get(3), 0.001);
        assertEquals(1, productes.get(1).getSimilituds().get(4), 0.001);
        assertEquals(0.5, productes.get(2).getSimilituds().get(3), 0.001);
    }

    /**

     Objecte de la prova: Comprovar que no es pot crear una similitud la mateixa ID als dos parametres
     i que es llança l'excepcio corresponent.

     Altres elements integrats a la prova: S'integren les funcionalitats de creacio de plantilla, categoria i productes.

     Valors estudiats: (Caixa Negre) Es crea un producte amb ID 1 i es crea una similitud entre el mateix producte.
     Es comprova que l'usuari rep una excepcio.

     Efectes estudiats: Maneig correcte de l'excepcio per a intents de creacio d'una similitud amb la mateixa ID.

     Operativa: Es prova de crear una similitud a un producte amb la mateixa ID del producte i es comprova
     que es llença l'excepcio MyException.

     */
    @Test(expected = MyException.class)
    public void testCrearSimilitudMateixID() throws MyException {
        Plantilla plantilla = new Plantilla("PlantillaTest");
        plantilla.crearCategoria("CategoriaTest");
        plantilla.crearProducte(1, "ProducteTest", "CategoriaTest", 10.5);

        plantilla.afegirSimilitud(1,1,0.1);
    }

    /**

     Objecte de la prova: Comprovar que es pot eliminar una similitud existent de la llista interna d'un
     producte.

     Altres elements integrats a la prova: S'integren les funcionalitats de creacio de plantilla, categoria i productes.

     Valors estudiats: (Caixa Blanca) Es creen tres productes i s'afegeix similituds entre els tres.
     Es comprova que, despres d'eliminar algunes similituds, la llista interna als productes es mante coherent.

     Efectes estudiats: Eliminacio d'elements d'una col·leccio interna de productes i manteniment correcte de l'estat.

     Operativa: Es creen els productes, s'afegeixen similituds i s'eliminen i es valida l'estat de la llista
     de similituds abans i despres de cada operacio.

     */
    @Test
    public void testEliminarSimilitud() throws MyException {
        Plantilla plantilla = new Plantilla("PlantillaTest");
        plantilla.crearCategoria("CategoriaTest");
        plantilla.crearProducte(1, "ProducteTest", "CategoriaTest", 10.5);
        plantilla.crearProducte(2, "ProducteTest", "CategoriaTest", 10.5);
        plantilla.crearProducte(3, "ProducteTest", "CategoriaTest", 10.5);
        plantilla.crearProducte(4, "ProducteTest", "CategoriaTest", 10.5);

        HashMap<Integer, Producte> productes = plantilla.getProductes();
        plantilla.afegirSimilitud(1,2,0.1);
        plantilla.afegirSimilitud(1,3,0);
        plantilla.afegirSimilitud(2,3,0.5);

        assertEquals(0.1, productes.get(1).getSimilituds().get(2), 0.001);
        assertEquals(0.1, productes.get(2).getSimilituds().get(1), 0.001);
        assertEquals(0, productes.get(1).getSimilituds().get(3), 0.001);
        assertEquals(0.5, productes.get(2).getSimilituds().get(3), 0.001);

        plantilla.eliminarSimilitud(1,2);
        assertFalse(productes.get(1).getSimilituds().containsKey(2));
        assertFalse(productes.get(2).getSimilituds().containsKey(1));
        assertEquals(0, productes.get(1).getSimilituds().get(3), 0.001);
        assertEquals(0.5, productes.get(2).getSimilituds().get(3), 0.001);

        plantilla.eliminarSimilitud(3,2);
        assertFalse(productes.get(3).getSimilituds().containsKey(2));
        assertFalse(productes.get(2).getSimilituds().containsKey(3));
        assertEquals(0,productes.get(2).getSimilituds().size());
        assertEquals(1,productes.get(3).getSimilituds().size());
        assertEquals(1,productes.get(1).getSimilituds().size());
        assertEquals(0, productes.get(1).getSimilituds().get(3), 0.001);
    }

    /**

     Objecte de la prova: Comprovar que una similitud inexistent no es pot eliminar, i que es llança l'excepcio
     corresponent.

     Altres elements integrats a la prova: S'integren les funcionalitats de creacio de plantilla, categoria i productes.

     Valors estudiats: (Caixa Negre) Es tracta d'intentar eliminar una similitud inexistent. Es comprova que l'usuari
     rep una excepcio.

     Efectes estudiats: Maneig correcte de l'excepcio per a intents d'eliminacio d'una similitud inexistent.

     Operativa: Es prova d'eliminar una similitud no creada i es comprova que es llença l'excepcio MyException.

     */
    @Test(expected = MyException.class)
    public void testEliminarSimilitudInexistent() throws MyException {
        Plantilla plantilla = new Plantilla("PlantillaTest");
        plantilla.crearCategoria("CategoriaTest");
        plantilla.crearProducte(1, "ProducteTest", "CategoriaTest", 10.5);
        plantilla.crearProducte(2, "ProducteTest", "CategoriaTest", 10.5);
        plantilla.eliminarSimilitud(1,2);
    }

    /**

     Objecte de la prova: Comprovar que es pot sobreescriure una similitud existent de la llista interna d'un producte.

     Altres elements integrats a la prova: S'integren les funcionalitats de creacio de plantilla, categoria i productes.

     Valors estudiats: (Caixa Blanca) Es creen dos productes i s'assigna una similitud. Es comprova que, despres
     de modificar-la la similitud tambe varia pels dos productes.

     Efectes estudiats: Modificacio d'elements d'una col·leccio interna de productes i manteniment correcte de l'estat.

     Operativa: Es creen els productes i la similitud, es modifica la similitud i es valida l'estat de l'atribut
     despres de l'operacio.

     */
    @Test
    public void testSobreescriureSimilitud() throws MyException {
        Plantilla plantilla = new Plantilla("PlantillaTest");
        plantilla.crearCategoria("CategoriaTest");
        plantilla.crearProducte(1, "ProducteTest", "CategoriaTest", 10.5);
        plantilla.crearProducte(2, "ProducteTest", "CategoriaTest", 10.5);

        HashMap<Integer, Producte> productes = plantilla.getProductes();

        plantilla.afegirSimilitud(1,2,0.4);
        assertEquals(0.4, productes.get(1).getSimilituds().get(2), 0.001);
        assertEquals(0.4, productes.get(2).getSimilituds().get(1), 0.001);

        plantilla.afegirSimilitud(2,1,0.7);
        assertEquals(0.7, productes.get(1).getSimilituds().get(2), 0.001);
        assertEquals(0.7, productes.get(2).getSimilituds().get(1), 0.001);

        plantilla.afegirSimilitud(1,2,0.2);
        assertEquals(0.2, productes.get(1).getSimilituds().get(2), 0.001);
        assertEquals(0.2, productes.get(2).getSimilituds().get(1), 0.001);

    }

    /**

     Objecte de la prova: Comprovar que la impressio de la llista de similitud s'executa correctament.

     Altres elements integrats a la prova: S'integra la funcionalitat de creacio de plantilla.

     Valors estudiats: (Caixa Negre) Es creen tres productes amb IDs 1, 2, 3, s'assignen similituds i s'imprimeix la llista de
     similituds comprovant que l'usuari rep les dades correctes.

     Efectes estudiats: Correcta impressio d'un string amb les dades d'una llista interna

     Operativa: Es creen els productes i les similituds, es fa un assert de les similituds en un string i s'imprimeix per
     terminal el string.

     */

    @Test
    public void testLlistaSimilituds() throws MyException {
        Plantilla plantilla = new Plantilla("PlantillaTest");
        plantilla.crearCategoria("CategoriaTest");
        plantilla.crearProducte(1, "ProducteTest", "CategoriaTest", 10.5);
        plantilla.crearProducte(2, "ProducteTest", "CategoriaTest", 10.5);
        plantilla.crearProducte(3, "ProducteTest", "CategoriaTest", 10.5);
        plantilla.crearProducte(4, "ProducteTest", "CategoriaTest", 10.5);

        plantilla.afegirSimilitud(1,2,0.1);
        plantilla.afegirSimilitud(1,3,0);
        plantilla.afegirSimilitud(1,4,1);
        plantilla.afegirSimilitud(2,3,0.5);

        double[][] expected = {
                {Double.NaN, 1, 2, 3, 4},
                {1, 0, 0.1, 0, 1},
                {2, 0.1, 0, 0.5, 0},
                {3, 0, 0.5, 0, 0},
                {4, 1, 0, 0, 0}
        };
        assertEquals(expected, plantilla.imprimirSimilituds());
    }

}
