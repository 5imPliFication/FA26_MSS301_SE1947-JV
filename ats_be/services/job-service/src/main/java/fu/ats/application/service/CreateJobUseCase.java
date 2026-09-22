package fu.ats.application.service;

import fu.ats.application.command.JobCommand;
import fu.ats.domain.aggregate.JobAggregate;

public class CreateJobUseCase {
    public void execute(JobCommand command){

        // map command -> aggregate root

        Job job = JobAggregate.draft();
        // Call repo -> save(job)

    }
}
