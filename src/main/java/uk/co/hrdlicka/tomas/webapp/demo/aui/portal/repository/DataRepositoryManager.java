/* Copyright 2016 Tomas Hrdlicka <tomas@hrdlicka.co.uk>. All rights reserved.
 */

package uk.co.hrdlicka.tomas.webapp.demo.aui.portal.repository;

import java.util.List;
import javax.persistence.EntityManager;

/**
 * Data Repository Manager interface
 *
 * @author Tomas Hrdlicka <tomas@hrdlicka.co.uk>
 * @see <a href="http://tomas.hrdlicka.co.uk">Tomas 'Xboot' Hrdlicka</a>
 */
public interface DataRepositoryManager<T, PK> {

    Class<T> getEntityClass();
    EntityManager getEntityManager();

    T add(final T entity);
    T get(final PK id);
    T update(final T entity);
    void remove(final T entity);

    List<T> getAll();
    void executeQuery(String query);

}