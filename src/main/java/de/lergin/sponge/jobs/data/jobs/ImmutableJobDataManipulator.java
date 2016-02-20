package de.lergin.sponge.jobs.data.jobs;

import de.lergin.sponge.jobs.data.JobKeys;
import de.lergin.sponge.jobs.job.Job;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.manipulator.immutable.common.AbstractImmutableData;
import org.spongepowered.api.data.value.BaseValue;
import org.spongepowered.api.data.value.immutable.ImmutableListValue;
import org.spongepowered.api.data.value.immutable.ImmutableMapValue;
import org.spongepowered.api.data.value.immutable.ImmutableValue;
import org.spongepowered.api.data.value.mutable.Value;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ImmutableJobDataManipulator extends AbstractImmutableData<ImmutableJobDataManipulator, JobData> {
    Map<String, Double> jobs = new HashMap<>();
    boolean jobsEnabled = true;
    List<String> selectedJobs;

    protected ImmutableJobDataManipulator(Map<String, Double> jobs, boolean jobsEnabled, List<String> selectedJobs) {
        this.jobs.putAll(jobs);
        this.jobsEnabled = jobsEnabled;
        this.selectedJobs = selectedJobs;

        registerGetters();
    }

    public ImmutableMapValue<String, Double> jobs(){
        return Sponge.getRegistry().getValueFactory().createMapValue(JobKeys.JOB_DATA, this.jobs).asImmutable();
    }

    public ImmutableValue<Boolean> jobsEnabled(){
        return Sponge.getRegistry().getValueFactory().createValue(JobKeys.JOB_ENABLED, this.jobsEnabled).asImmutable();
    }

    public ImmutableListValue<String> selectedJobs(){
        return Sponge.getRegistry().getValueFactory().createListValue(JobKeys.JOB_SELECTED, this.selectedJobs).asImmutable();
    }

    @Override
    protected void registerGetters() {
        registerFieldGetter(JobKeys.JOB_DATA, () -> this.jobs);
        registerKeyValue(JobKeys.JOB_DATA, this::jobs);

        registerFieldGetter(JobKeys.JOB_ENABLED, () -> this.jobsEnabled);
        registerKeyValue(JobKeys.JOB_ENABLED, this::jobsEnabled);

        registerFieldGetter(JobKeys.JOB_SELECTED, () -> this.selectedJobs);
        registerKeyValue(JobKeys.JOB_SELECTED, this::selectedJobs);
    }

    @Override
    public <E> Optional<ImmutableJobDataManipulator> with(Key<? extends BaseValue<E>> key, E e) {
        if(this.supports(key)) {
            return Optional.of(asMutable().set(key, e).asImmutable());
        } else {
            return Optional.empty();
        }
    }

    @Override
    public JobData asMutable() {
        return new JobData(jobs, jobsEnabled, selectedJobs);
    }

    @Override
    public int compareTo(ImmutableJobDataManipulator o) {
        return 0;
    }

    @Override
    public int getContentVersion() {
        return 7;
    }
}
