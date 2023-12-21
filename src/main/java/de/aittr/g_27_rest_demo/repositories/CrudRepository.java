package de.aittr.g_27_rest_demo.repositories;

import de.aittr.g_27_rest_demo.domain.Cat;
import de.aittr.g_27_rest_demo.domain.SimpleCat;

import java.util.List;

public interface CrudRepository<T> {

    T save(T obj);

    T getById(int id);

    List<T> getAll();

    void deleteById(int id);
}