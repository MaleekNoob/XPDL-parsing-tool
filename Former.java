import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.*;

import org.w3c.dom.Document;

public class Former {

    public static void main(String[] args) {
        String filename = "C:\\Programming projects\\Python\\XPDL-parsing-tool\\re-created-BPMN.bpmn"; // Change this to your BPMN file path
        countBPMNElements(filename);
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
            XPathExpression userTaskExpr = xpath.compile("count(//*[local-name()='userTask'])");
            XPathExpression serviceTaskExpr = xpath.compile("count(//*[local-name()='serviceTask'])");
            XPathExpression scriptTaskExpr = xpath.compile("count(//*[local-name()='scriptTask'])");
            XPathExpression manualTaskExpr = xpath.compile("count(//*[local-name()='manualTask'])");
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
            XPathExpression swimlaneExpr = xpath.compile("count(//*[local-name()='lane' or local-name()='pool'])");
            XPathExpression poolExpr = xpath.compile("count(//*[local-name()='pool'])");
            XPathExpression laneExpr = xpath.compile("count(//*[local-name()='lane'])");

            double totalEvents = (double) eventExpr.evaluate(doc, XPathConstants.NUMBER);
            double startEvents = (double) startEventExpr.evaluate(doc, XPathConstants.NUMBER);
            double intermediateEvents = (double) intermediateEventExpr.evaluate(doc, XPathConstants.NUMBER);
            double endEvents = (double) endEventExpr.evaluate(doc, XPathConstants.NUMBER);
            double userTasks = (double) userTaskExpr.evaluate(doc, XPathConstants.NUMBER);
            double serviceTasks = (double) serviceTaskExpr.evaluate(doc, XPathConstants.NUMBER);
            double scriptTasks = (double) scriptTaskExpr.evaluate(doc, XPathConstants.NUMBER);
            double manualTasks = (double) manualTaskExpr.evaluate(doc, XPathConstants.NUMBER);
            double tasks = userTasks + serviceTasks + scriptTasks + manualTasks;
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

            System.out.println("BPMN_Model_Elements:");
            System.out.println("Total_Events: " + (int) totalEvents);
            System.out.println("Start_Events: " + (int) startEvents);
            System.out.println("Intermediate_Events: " + (int) intermediateEvents);
            System.out.println("End_Events: " + (int) endEvents);
            System.out.println("Total_Activities: " + (int) totalActivities);
            System.out.println("Tasks: " + (int) tasks);
            System.out.println("User_Tasks: " + (int) userTasks);
            System.out.println("Service_Tasks: " + (int) serviceTasks);
            System.out.println("Script_Tasks: " + (int) scriptTasks);
            System.out.println("Manual_Tasks: " + (int) manualTasks);
            System.out.println("Sub_Processes: " + (int) subProcesses);
            System.out.println("Total_Gateways: " + (int) totalGateways);
            System.out.println("Exclusive_Gateways_XOR: " + (int) exclusiveGateways);
            System.out.println("Parallel_Gateways_AND: " + (int) parallelGateways);
            System.out.println("Inclusive_Gateways_OR: " + (int) inclusiveGateways);
            System.out.println("Total_Artifacts: " + (int) totalArtifacts);
            System.out.println("Data_Objects: " + (int) dataObjects);
            System.out.println("Groups: " + (int) groups);
            System.out.println("Annotations: " + (int) annotations);
            System.out.println("Total_Connecting_Objects: " + (int) totalConnectingObjects);
            System.out.println("Sequence_Flows: " + (int) sequenceFlows);
            System.out.println("Message_Flows: " + (int) messageFlows);
            System.out.println("Associations: " + (int) associations);
            System.out.println("Total_Swimlanes: " + (int) totalSwimlanes);
            System.out.println("Pools: " + (int) pools);
            System.out.println("Lanes: " + (int) lanes);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
