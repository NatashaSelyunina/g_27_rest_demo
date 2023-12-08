package de.aittr.g_27_rest_demo.repositories;

import de.aittr.g_27_rest_demo.domain.SimpleCat;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.List;

@Repository
public class CatRepository implements CrudRepository<SimpleCat> {

    private File file = new File("cat.txt");
    private String delimiter = ";";

    @Override
    public SimpleCat save(SimpleCat obj) {
        try (FileWriter writer = new FileWriter(file, true)) {
            List<SimpleCat> cats = getAll();

            if (cats.size() == 0) {
                obj.setId(1);
            } else {
                SimpleCat lastCat = cats.get(cats.size() - 1);
                int idLastCat = lastCat.getId();
                obj.setId(idLastCat + 1);
            }

            String result =
                    obj.getId() + delimiter + obj.getAge() + delimiter + obj.getColor() + delimiter + obj.getWeight();

            writer.write(result + "\n");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return obj;
    }

    @Override
    public SimpleCat getById(int id) {
        return null;
    }

    @Override
    public List<SimpleCat> getAll() {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
           return reader.lines() // stream "1;3;black;4.56"
                   .map(line -> line.split(delimiter)) // "1;3;black;4.56" -> ["1", "3", "black", "4.56"]
                   .map(array -> new SimpleCat( // arrays -> cats
                           Integer.parseInt(array[0]),
                           Integer.parseInt(array[1]),
                           array[2],
                           Double.parseDouble(array[3])
                   ))
                   .toList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        List<SimpleCat> cats = new CatRepository().getAll();
        System.out.println();

        SimpleCat cat1 = new SimpleCat(4, "yellow", 4.34);
        CatRepository repository = new CatRepository();
        repository.save(cat1);

        SimpleCat cat2 = new SimpleCat(10, 7, "brown", 5.00);
        repository.save(cat2);
    }

    @Override
    public void deleteById(int id) {

    }
}