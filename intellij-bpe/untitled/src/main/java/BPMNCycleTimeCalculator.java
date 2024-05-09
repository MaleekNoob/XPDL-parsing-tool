import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.Process;
import org.camunda.bpm.model.bpmn.instance.SequenceFlow;
import org.camunda.bpm.model.bpmn.instance.StartEvent;
import org.camunda.bpm.model.bpmn.instance.UserTask;
import org.camunda.bpm.model.xml.instance.ModelElementInstance;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Collection;

public class BPMNCycleTimeCalculator {

    public static void main(String[] args) {
        // Provide the path to your BPMN file here
        String bpmnFilePath = "path/to/your/file.bpmn";

        try {
            // Parse the BPMN file
            BpmnModelInstance modelInstance;
            try (InputStream inputStream = new FileInputStream(new File(bpmnFilePath))) {
                modelInstance = Bpmn.readModelFromStream(inputStream);
            }

            // Get the main process
            Process mainProcess = modelInstance.getModelElementsByType(Process.class).iterator().next();

            // Get all user tasks within the process
            Collection<UserTask> userTasks = mainProcess.getChildElementsByType(UserTask.class);

            long totalCycleTime = 0;

            // Calculate cycle time as the sum of all user task durations
            for (UserTask userTask : userTasks) {
                totalCycleTime += getUserTaskDuration(userTask);
            }

            System.out.println("Total cycle time of the process: " + totalCycleTime + " milliseconds");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static long getUserTaskDuration(UserTask userTask) {
        // You may implement logic here to extract duration information from user tasks,
        // for example, by reading custom properties or BPMN extension elements.
        // For simplicity, let's assume each user task has a fixed duration of 1 hour.
        return 3600000; // 1 hour in milliseconds
    }
}
