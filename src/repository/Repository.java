package repository;

import java.util.List;
import java.util.Optional;

public interface Repository<T, ID> {
    Optional<T> trouverParId(ID id);
    List<T> trouverTous();
    T sauvegarder(T entity);
    void supprimer(ID id);
}