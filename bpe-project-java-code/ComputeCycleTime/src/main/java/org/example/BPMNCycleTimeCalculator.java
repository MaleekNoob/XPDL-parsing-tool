package org.example;

import org.camunda.bpm.model.bpmn.Bpmn;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.Process;
import org.camunda.bpm.model.bpmn.instance.UserTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Collection;
import java.util.Random;

public class BPMNCycleTimeCalculator {

    public static void main(String[] args) {
        // Provide the path to your BPMN file here
        java.lang.String bpmnFilePath = "C:\\Programming projects\\Python\\XPDL-parsing-tool\\5 processes.bpmn";

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

            // Assign random duration to each user task and calculate cycle time
            for (UserTask userTask : userTasks) {
                long taskDuration = getRandomDuration();
                System.out.println("Assigned duration for task '" + userTask.getName() + "': " + taskDuration + " milliseconds");
                totalCycleTime += taskDuration;
            }

            System.out.println("Total cycle time of the process: " + totalCycleTime + " milliseconds");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static long getRandomDuration() {
        // Generate a random duration between 1 hour (3600 seconds) and 8 hours (28800 seconds)
        Random random = new Random();
        return (random.nextInt(8) + 1) * 3600 * 1000; // Convert hours to milliseconds
    }
}