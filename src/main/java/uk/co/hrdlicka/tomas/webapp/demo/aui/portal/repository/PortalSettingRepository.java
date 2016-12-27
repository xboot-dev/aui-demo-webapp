/* Copyright 2016 Tomas Hrdlicka <tomas@hrdlicka.co.uk>. All rights reserved.
 */

package uk.co.hrdlicka.tomas.webapp.demo.aui.portal.repository;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import uk.co.hrdlicka.tomas.webapp.demo.aui.portal.entity.SettingEntity;

/**
 * Portal Setting Repository
 *
 * @author Tomas Hrdlicka <tomas@hrdlicka.co.uk>
 * @see <a href="http://tomas.hrdlicka.co.uk">Tomas 'Xboot' Hrdlicka</a>
 */
@Repository("portalSettingRepository")
public class PortalSettingRepository extends AbstractDataRepositoryManager<SettingEntity, Long> {

    public PortalSettingRepository() {
        super(SettingEntity.class);
    }

    public SettingEntity getByKey(final String key) {
        try {
            TypedQuery<SettingEntity> query = getEntityManager().createQuery(
                    "SELECT s FROM Setting s WHERE s.key = :key", getEntityClass())
                    .setParameter("key", key);

            return query.getSingleResult();

        } catch (final NoResultException | NonUniqueResultException ex) {
            return null;
        }
    }
}