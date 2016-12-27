/* Copyright 2016 Tomas Hrdlicka <tomas@hrdlicka.co.uk>. All rights reserved.
 */

package uk.co.hrdlicka.tomas.webapp.demo.aui.portal.repository;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import uk.co.hrdlicka.tomas.webapp.demo.aui.portal.model.Setting;
import uk.co.hrdlicka.tomas.webapp.demo.aui.portal.entity.SettingEntity;

/**
 * Portal Setting Service
 *
 * @author Tomas Hrdlicka <tomas@hrdlicka.co.uk>
 * @see <a href="http://tomas.hrdlicka.co.uk">Tomas 'Xboot' Hrdlicka</a>
 */
@Service("portalSettingService")
public class PortalSettingService extends AbstractDataRepositoryService<Setting, SettingEntity, Long, PortalSettingRepository> {

    @Autowired
    private PortalSettingRepository dataRepositoryManager;

    @Override
    public PortalSettingRepository getDataRepositoryManager() {
        return dataRepositoryManager;
    }

    public Setting getByKey(final String settingKey) {
        return entityToModel(getDataRepositoryManager().getByKey(settingKey));
    }

    @Override
    public SettingEntity modelToEntity(final Setting model) {
        return settingToEntity(model);
    }

    @Override
    public SettingEntity modelToEntity(final Setting model, final boolean skipId) {
        return settingToEntity(model, skipId);
    }

    @Override
    public Setting entityToModel(final SettingEntity entity) {
        return entityToSetting(entity);
    }

    public static SettingEntity settingToEntity(final Setting setting) {
        return settingToEntity(setting, false);
    }

    public static SettingEntity settingToEntity(final Setting setting, final boolean skipId) {
        if (setting == null) {
            return null;
        }

        SettingEntity entity = new SettingEntity();

        if (!skipId) {
            entity.setId(setting.getId());
        }

        entity.setKey(setting.getKey());
        entity.setValue(setting.getValue());
        return entity;
    }

    public static Setting entityToSetting(final SettingEntity entity) {
        if (entity == null) {
            return null;
        }

        Setting setting = new Setting();
        setting.setId(entity.getId());
        setting.setKey(entity.getKey());
        setting.setValue(entity.getValue());
        return setting;
    }
}