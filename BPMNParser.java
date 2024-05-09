import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.Random;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.w3c.dom.Document;
import java.util.List;
import org.w3c.dom.Node;
import java.util.Scanner;
import java.util.Arrays;



public class BPMNParser {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the file name (e.g., myfile.bpmn): ");
        String fileName = scanner.nextLine(); // Get the file name from the user

        // Get the project directory
        String projectDirectory = System.getProperty("user.dir");

        // Construct the full file path
        String filePath = projectDirectory + File.separator + fileName;

        // Check if the file exists
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("File not found: " + fileName);
            scanner.close();
            return;
        }

        String modifiedFilePath = filePath.replace("work.bpmn", "modified_" + fileName);
        countBPMNElements(filePath);

        BPMNParser parser = new BPMNParser();
        String m_filepath = parser.addActivityTime(filePath); // Add activity times to tasks

        System.out.println("Activity times added successfully to " + m_filepath);
        double cycleTime = parser.calculateCT(m_filepath); // Calculate cycle time
        System.out.println("Cycle time calculated: " + cycleTime + " minutes");

        scanner.close();
    }

    public static void countBPMNElements(String filename) {
        try {
            File inputFile = new File(filename);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            Document doc = dbFactory.newDocumentBuilder().parse(inputFile);

            XPathFactory xPathfactory = XPathFactory.newInstance();
            XPath xpath = xPathfactory.newXPath();

            XPathExpression eventExpr = xpath.compile("count(//*[local-name()='startEvent' or local-name()='intermediateCatchEvent' or local-name()='intermediateThrowEvent' or local-name()='endEvent'])");
            XPathExpression startEventExpr = xpath.compile("count(//*[local-name()='startEvent'])");
            XPathExpression intermediateEventExpr = xpath.compile("count(//*[local-name()='intermediateCatchEvent' or local-name()='intermediateThrowEvent'])");
            XPathExpression endEventExpr = xpath.compile("count(//*[local-name()='endEvent'])");
            XPathExpression taskExpr = xpath.compile("count(//*[local-name()='task'])");
            XPathExpression noneTaskExpr = xpath.compile("count(//*[local-name()='task' and not(@activiti:class)])");
            XPathExpression userTaskExpr = xpath.compile("count(//*[local-name()='userTask'])");
            XPathExpression serviceTaskExpr = xpath.compile("count(//*[local-name()='serviceTask'])");
            XPathExpression scriptTaskExpr = xpath.compile("count(//*[local-name()='scriptTask'])");
            XPathExpression manualTaskExpr = xpath.compile("count(//*[local-name()='manualTask'])");
            XPathExpression businessRuleTaskExpr = xpath.compile("count(//*[local-name()='businessRuleTask'])");
            XPathExpression subProcessExpr = xpath.compile("count(//*[local-name()='subProcess'])");
            XPathExpression gatewayExpr = xpath.compile("count(//*[local-name()='exclusiveGateway' or local-name()='parallelGateway' or local-name()='inclusiveGateway'])");
            XPathExpression exclusiveGatewayExpr = xpath.compile("count(//*[local-name()='exclusiveGateway'])");
            XPathExpression parallelGatewayExpr = xpath.compile("count(//*[local-name()='parallelGateway'])");
            XPathExpression inclusiveGatewayExpr = xpath.compile("count(//*[local-name()='inclusiveGateway'])");
            XPathExpression artifactExpr = xpath.compile("count(//*[local-name()='dataObject' or local-name()='group' or local-name()='textAnnotation'])");
            XPathExpression dataObjectExpr = xpath.compile("count(//*[local-name()='dataObject'])");
            XPathExpression groupExpr = xpath.compile("count(//*[local-name()='group'])");
            XPathExpression annotationExpr = xpath.compile("count(//*[local-name()='textAnnotation'])");
            XPathExpression connectingObjectsExpr = xpath.compile("count(//*[local-name()='sequenceFlow' or local-name()='messageFlow' or local-name()='association'])");
            XPathExpression sequenceFlowExpr = xpath.compile("count(//*[local-name()='sequenceFlow'])");
            XPathExpression messageFlowExpr = xpath.compile("count(//*[local-name()='messageFlow'])");
            XPathExpression associationExpr = xpath.compile("count(//*[local-name()='association'])");
            XPathExpression swimlaneExpr = xpath.compile("count(//*[local-name()='lane' or local-name()='pool' or local-name()='participant'])");
            XPathExpression poolExpr = xpath.compile("count(//*[local-name()='pool'])");
            XPathExpression laneExpr = xpath.compile("count(//*[local-name()='lane'])");
            XPathExpression participantExpr = xpath.compile("count(//*[local-name()='participant'])");

            double totalEvents = (double) eventExpr.evaluate(doc, XPathConstants.NUMBER);
            double startEvents = (double) startEventExpr.evaluate(doc, XPathConstants.NUMBER);
            double intermediateEvents = (double) intermediateEventExpr.evaluate(doc, XPathConstants.NUMBER);
            double endEvents = (double) endEventExpr.evaluate(doc, XPathConstants.NUMBER);
            double noneTasks = (double) noneTaskExpr.evaluate(doc, XPathConstants.NUMBER);
            double userTasks = (double) userTaskExpr.evaluate(doc, XPathConstants.NUMBER);
            double serviceTasks = (double) serviceTaskExpr.evaluate(doc, XPathConstants.NUMBER);
            double scriptTasks = (double) scriptTaskExpr.evaluate(doc, XPathConstants.NUMBER);
            double manualTasks = (double) manualTaskExpr.evaluate(doc, XPathConstants.NUMBER);
            double businessRuleTasks = (double) businessRuleTaskExpr.evaluate(doc, XPathConstants.NUMBER);
            double tasks = userTasks + serviceTasks + scriptTasks + manualTasks + noneTasks + businessRuleTasks;
            double subProcesses = (double) subProcessExpr.evaluate(doc, XPathConstants.NUMBER);
            double totalActivities = tasks + subProcesses;
            double totalGateways = (double) gatewayExpr.evaluate(doc, XPathConstants.NUMBER);
            double exclusiveGateways = (double) exclusiveGatewayExpr.evaluate(doc, XPathConstants.NUMBER);
            double parallelGateways = (double) parallelGatewayExpr.evaluate(doc, XPathConstants.NUMBER);
            double inclusiveGateways = (double) inclusiveGatewayExpr.evaluate(doc, XPathConstants.NUMBER);
            double totalArtifacts = (double) artifactExpr.evaluate(doc, XPathConstants.NUMBER);
            double dataObjects = (double) dataObjectExpr.evaluate(doc, XPathConstants.NUMBER);
            double groups = (double) groupExpr.evaluate(doc, XPathConstants.NUMBER);
            double annotations = (double) annotationExpr.evaluate(doc, XPathConstants.NUMBER);
            double totalConnectingObjects = (double) connectingObjectsExpr.evaluate(doc, XPathConstants.NUMBER);
            double sequenceFlows = (double) sequenceFlowExpr.evaluate(doc, XPathConstants.NUMBER);
            double messageFlows = (double) messageFlowExpr.evaluate(doc, XPathConstants.NUMBER);
            double associations = (double) associationExpr.evaluate(doc, XPathConstants.NUMBER);
            double totalSwimlanes = (double) swimlaneExpr.evaluate(doc, XPathConstants.NUMBER);
            double pools = (double) poolExpr.evaluate(doc, XPathConstants.NUMBER);
            double lanes = (double) laneExpr.evaluate(doc, XPathConstants.NUMBER);
            double participants = (double) participantExpr.evaluate(doc, XPathConstants.NUMBER);

            System.out.println("BPMN_Model_Elements:");
            System.out.println("--------------------------------------------------");
            System.out.println("Total_Events: " + (int) totalEvents);
            System.out.println("Start_Events: " + (int) startEvents);
            System.out.println("Intermediate_Events: " + (int) intermediateEvents);
            System.out.println("End_Events: " + (int) endEvents);
            System.out.println("--------------------------------------------------");
            System.out.println("Total_Activities: " + (int) totalActivities);
            System.out.println("Total Tasks: " + (int) tasks);
            System.out.println("None_Tasks: " + (int) noneTasks);
            System.out.println("User_Tasks: " + (int) userTasks);
            System.out.println("Service_Tasks: " + (int) serviceTasks);
            System.out.println("Script_Tasks: " + (int) scriptTasks);
            System.out.println("Manual_Tasks: " + (int) manualTasks);
            System.out.println("Business_Rule_Tasks: " + (int) businessRuleTasks);
            System.out.println("Sub_Processes: " + (int) subProcesses);
            System.out.println("--------------------------------------------------");
            System.out.println("Total_Gateways: " + (int) totalGateways);
            System.out.println("Exclusive_Gateways_XOR: " + (int) exclusiveGateways);
            System.out.println("Parallel_Gateways_AND: " + (int) parallelGateways);
            System.out.println("Inclusive_Gateways_OR: " + (int) inclusiveGateways);
            System.out.println("--------------------------------------------------");
            System.out.println("Total_Artifacts: " + (int) totalArtifacts);
            System.out.println("Data_Objects: " + (int) dataObjects);
            System.out.println("Groups: " + (int) groups);
            System.out.println("Annotations: " + (int) annotations);
            System.out.println("--------------------------------------------------");
            System.out.println("Total_Connecting_Objects: " + (int) totalConnectingObjects);
            System.out.println("Sequence_Flows: " + (int) sequenceFlows);
            System.out.println("Message_Flows: " + (int) messageFlows);
            System.out.println("Associations: " + (int) associations);
            System.out.println("--------------------------------------------------");
            System.out.println("Total_Swimlanes: " + (int) totalSwimlanes);
            System.out.println("Pools: " + (int) pools);
            System.out.println("Lanes: " + (int) lanes);
            System.out.println("Participants: " + (int) participants);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public String addActivityTime(String filename) {
        try {
            // Create a DocumentBuilder
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            // Parse the XML file
            Document doc = dBuilder.parse(new File(filename));

            // Get the list of all task elements (userTasks, serviceTasks, scriptTasks, manualTasks, and noneTasks)
            NodeList userTaskList = doc.getElementsByTagName("bpmn:userTask");
            NodeList serviceTaskList = doc.getElementsByTagName("bpmn:serviceTask");
            NodeList scriptTaskList = doc.getElementsByTagName("bpmn:scriptTask");
            NodeList manualTaskList = doc.getElementsByTagName("bpmn:manualTask");
            NodeList noneTaskList = doc.getElementsByTagName("bpmn:noneTask");
            NodeList businessRuleTaskList = doc.getElementsByTagName("bpmn:businessRuleTask");
            NodeList taskList = doc.getElementsByTagName("bpmn:task");

            // Calculate the total number of all task elements
            int totalTasks = userTaskList.getLength() + serviceTaskList.getLength() +
                    scriptTaskList.getLength() + manualTaskList.getLength() + noneTaskList.getLength() + taskList.getLength() + businessRuleTaskList.getLength();

            System.out.println("Total number of all task elements: " + totalTasks);

            // Combine all task elements into one list for adding execution time
            List<NodeList> allTaskLists = Arrays.asList(userTaskList, serviceTaskList, scriptTaskList, manualTaskList, noneTaskList, taskList, businessRuleTaskList);
            for (NodeList nodeList : allTaskLists) {
                for (int i = 0; i < nodeList.getLength(); i++) {
                    Node taskNode = nodeList.item(i);
                    if (taskNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element task = (Element) taskNode;
                        // Add random execution time
                        Random rand = new Random();
                        int randomTime = rand.nextInt(11) + 5; // Random time between 5 to 15 minutes
                        task.setAttribute("executionTime", String.valueOf(randomTime));
                    }
                }
            }

            // Create a new file to save the modified XML
            String modifiedFilename = "modified_" + new File(filename).getName();
            File modifiedFile = new File(modifiedFilename);

            // Write the modified XML back to the file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(modifiedFile);
            transformer.transform(source, result);

            System.out.println("Activity times added successfully to " + modifiedFilename);

            // Get the project directory
            String projectDirectory = System.getProperty("user.dir");

            // Construct the full file path
            String modifiedfilePath = projectDirectory + File.separator + modifiedFilename;

            return modifiedfilePath;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    public double calculateCT(String filename) {
        double cycleTime = 0.0;

        try {
            File file = new File(filename);
            if (!file.exists()) {
                System.out.println("Error: File not found at " + filename);
                return cycleTime;
            }

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);

            NodeList userTaskList = doc.getElementsByTagName("bpmn:userTask");
            NodeList serviceTaskList = doc.getElementsByTagName("bpmn:serviceTask");
            NodeList scriptTaskList = doc.getElementsByTagName("bpmn:scriptTask");
            NodeList manualTaskList = doc.getElementsByTagName("bpmn:manualTask");
            NodeList noneTaskList = doc.getElementsByTagName("bpmn:noneTask");
            NodeList businessRuleTaskList = doc.getElementsByTagName("bpmn:businessRuleTask");
            NodeList taskList = doc.getElementsByTagName("bpmn:task");

            cycleTime += calculateTaskListCycleTime(userTaskList);
            cycleTime += calculateTaskListCycleTime(serviceTaskList);
            cycleTime += calculateTaskListCycleTime(scriptTaskList);
            cycleTime += calculateTaskListCycleTime(manualTaskList);
            cycleTime += calculateTaskListCycleTime(noneTaskList);
            cycleTime += calculateTaskListCycleTime(businessRuleTaskList);
            cycleTime += calculateTaskListCycleTime(taskList);
        } catch (Exception e) {
            System.out.println("Error processing the BPMN file: " + e.getMessage());
        }

        System.out.println("Total cycle time calculated: " + cycleTime);
        return cycleTime;
    }

    private double calculateTaskListCycleTime(NodeList taskList) {
        double cycleTime = 0.0;

        for (int i = 0; i < taskList.getLength(); i++) {
            Element task = (Element) taskList.item(i);
            String executionTimeString = task.getAttribute("executionTime");
            if (!executionTimeString.isEmpty()) {
                try {
                    int executionTime = Integer.parseInt(executionTimeString);
                    cycleTime += executionTime;
                    cycleTime += fetchAttributeTime(executionTimeString);
                } catch (NumberFormatException ex) {
                    System.out.println("Error parsing execution time as an integer for task at index " + i + ": " + ex.getMessage());
                }
            } else {
                System.out.println("Execution time not specified for task at index " + i);
            }
        }

        return cycleTime;
    }

    private double fetchAttributeTime(String attribute) {
        Random rand = new Random();
        double cycleTime = rand.nextDouble() * 100;
        return cycleTime;
    }

}