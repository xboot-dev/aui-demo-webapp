/* Copyright 2016 Tomas Hrdlicka <tomas@hrdlicka.co.uk>. All rights reserved.
 */

package uk.co.hrdlicka.tomas.webapp.demo.aui.portal.repository;

import java.util.List;

/**
 * Data Repository Service interface
 *
 * M = Model, E = Entity, PK = Primary Key, D = DataRepositoryManager
 *
 * @author Tomas Hrdlicka <tomas@hrdlicka.co.uk>
 * @see <a href="http://tomas.hrdlicka.co.uk">Tomas 'Xboot' Hrdlicka</a>
 */
public interface DataRepositoryService<M, E, PK, D extends DataRepositoryManager<E, PK>> {

    D getDataRepositoryManager();

    M add(final M model);
    void update(final M model);
    void remove(final M model);
    M get(final PK id);
    List<M> getAll();

    E modelToEntity(final M model);
    E modelToEntity(final M model, final boolean skipId);
    M entityToModel(final E entity);

}