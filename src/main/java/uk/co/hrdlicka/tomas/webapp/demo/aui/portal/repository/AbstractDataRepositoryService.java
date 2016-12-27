/* Copyright 2016 Tomas Hrdlicka <tomas@hrdlicka.co.uk>. All rights reserved.
 */

package uk.co.hrdlicka.tomas.webapp.demo.aui.portal.repository;

import java.util.List;
import java.util.ArrayList;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;

/**
 * Abstract Data Repository Service class
 *
 * M = Model, E = Entity, PK = Primary Key, D = DataRepositoryManager
 *
 * @author Tomas Hrdlicka <tomas@hrdlicka.co.uk>
 * @see <a href="http://tomas.hrdlicka.co.uk">Tomas 'Xboot' Hrdlicka</a>
 */
public abstract class AbstractDataRepositoryService<M, E, PK, D extends DataRepositoryManager<E, PK>> implements DataRepositoryService<M, E, PK, D> {
    protected final Logger log = Logger.getLogger(getClass().getName());

    protected Logger getLog() {
        return log;
    }

    public abstract D getDataRepositoryManager();

    protected EntityManager getEntityManager() {
        if (getDataRepositoryManager() == null) {
            return null;
        }

        return getDataRepositoryManager().getEntityManager();
    }

    @Override
    public M add(final M model) {
        try {
            E entity = modelToEntity(model, true);
            getDataRepositoryManager().add(entity);
            return entityToModel(entity);

        } catch (final Exception ex) {
            return null;
        }
    }

    @Override
    public void update(final M model) {
        try {
            getDataRepositoryManager().update(modelToEntity(model));

        } catch (final Exception ex) {
        }
    }

    @Override
    public void remove(final M model) {
        try {
            getDataRepositoryManager().remove(modelToEntity(model));

        } catch (final Exception ex) {
        }
    }

    @Override
    public M get(final PK id) {
        return entityToModel(getDataRepositoryManager().get(id));
    }

    @Override
    public List<M> getAll() {
        List<E> entities = getDataRepositoryManager().getAll();

        if (entities == null) {
            return null;
        }

        List<M> models = new ArrayList<M>();

        for (E entity : entities) {
            models.add(entityToModel(entity));
        }

        return models;
    }

    public abstract E modelToEntity(final M model);
    public abstract E modelToEntity(final M model, final boolean skipId);
    public abstract M entityToModel(final E entity);

}