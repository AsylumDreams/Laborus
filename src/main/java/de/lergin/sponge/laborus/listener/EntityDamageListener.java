package de.lergin.sponge.laborus.listener;

import de.lergin.sponge.laborus.job.Job;
import de.lergin.sponge.laborus.job.JobAction;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.cause.entity.damage.source.EntityDamageSource;
import org.spongepowered.api.event.entity.DamageEntityEvent;
import org.spongepowered.api.event.filter.cause.First;

import java.util.List;

/**
 * listener for entity damage jobEvents
 */
public class EntityDamageListener extends JobListener<EntityType> {
    public EntityDamageListener(Job job, List<EntityType> entityTypes) {
        super(job, entityTypes);
    }

    @Listener
    public void onEvent(DamageEntityEvent event, @First EntityDamageSource damageSource) {
        if (damageSource.getSource().getType().equals(EntityTypes.PLAYER) &&
                JOB.enabled((Player) damageSource.getSource())) {
            final EntityType ENTITY_TYPE = event.getTargetEntity().getType();

            if (JOB_ITEM_TYPES.contains(ENTITY_TYPE)) {
                event.setCancelled(!JOB.onJobListener(
                        ENTITY_TYPE,
                        (Player) damageSource.getSource(),
                        JobAction.ENTITY_DAMAGE
                ));
            }
        }
    }
}
