package tech.tresearchgroup.babygalago.controller.queues;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import tech.tresearchgroup.babygalago.controller.controllers.QueueEntityController;
import tech.tresearchgroup.schemas.galago.entities.SettingsEntity;

public class ConverterWorker implements Job {

    @Override
    public void execute(JobExecutionContext context) {
        for (; ; ) {
            try {
                //Todo accept incoming requests
                for (int i = 0; i < QueueEntityController.jobs.size(); i++) {
                    System.out.println(QueueEntityController.jobs.get(i).toString());
                    QueueEntityController.jobs.remove(i);
                    i--;
                }
                Thread.sleep(1000);
            } catch (Exception e) {
                if (SettingsEntity.debug) {
                    e.printStackTrace();
                }
            }
        }
    }
}
