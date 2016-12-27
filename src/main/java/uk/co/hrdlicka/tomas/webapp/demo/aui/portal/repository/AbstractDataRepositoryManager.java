/* Copyright 2016 Tomas Hrdlicka <tomas@hrdlicka.co.uk>. All rights reserved.
 */

package uk.co.hrdlicka.tomas.webapp.demo.aui.portal.repository;

import java.util.List;

import javax.persistence.PersistenceContext;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;

import org.apache.log4j.Logger;

import org.springframework.transaction.annotation.Transactional;

import uk.co.hrdlicka.tomas.webapp.demo.aui.portal.util.StringUtils;

/**
 * Abstract Repository Manager class
 *
 * @author Tomas Hrdlicka <tomas@hrdlicka.co.uk>
 * @see <a href="http://tomas.hrdlicka.co.uk">Tomas 'Xboot' Hrdlicka</a>
 */
public abstract class AbstractDataRepositoryManager<T, PK> implements DataRepositoryManager<T, PK> {
    protected final Logger log = Logger.getLogger(getClass().getName());
    protected final Class<T> entityClass;

    @PersistenceContext
    protected EntityManager entityManager;

    public AbstractDataRepositoryManager(final Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected Logger getLog() {
        return log;
    }

    @Override
    public Class<T> getEntityClass() {
        return entityClass;
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

    @Transactional
    @Override
    public T add(final T entity) {
        getEntityManager().persist(entity);
        getEntityManager().flush();
        return entity;
    }

    @Override
    public T get(final PK id) {
        return getEntityManager().find(getEntityClass(), id);
    }

    @Transactional
    @Override
    public T update(final T entity) {
        return getEntityManager().merge(entity);
    }

    @Transactional
    @Override
    public void remove(final T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<T> getAll() {
        CriteriaQuery<T> cq = (CriteriaQuery<T>) getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(getEntityClass()));
        return getEntityManager().createQuery(cq).getResultList();
    }

    @Transactional
    @Override
    public void executeQuery(final String query) {
        if (StringUtils.isNullOrEmpty(query)) {
            return;
        }

        getEntityManager().createQuery(query).executeUpdate();
    }
}