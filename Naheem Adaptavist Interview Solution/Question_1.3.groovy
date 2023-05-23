import com.atlassian.jira.issue.IssueConstant
import com.onresolve.jira.groovy.user.FieldBehaviours
import groovy.transform.BaseScript


@BaseScript FieldBehaviours behaviours
def priorityField = getFieldById(fieldChanged)
def priorityValue = priorityField.value as IssueConstant
def summaryField = getFieldById("summary")


// Check if the chosen priority is "High" or "Highest". 
// Remove Urgency prefix in summmary if user chanage priority to low, medium, and others
if (priorityValue.name in  ["High", "Highest"]) {
   def summaryValue = summaryField.getValue() as String
   // Do nothing if Urgency is already added as a prefix,else, add Urgency as a prefix to summary
   if (summaryValue.startsWith("Urgency:")){
 
   }
   else {
      def updatedSummary = "Urgency: $summaryValue"
      summaryField.setFormValue(updatedSummary)
   }

} else {
   def summaryValue = summaryField.getValue() as String
   if (summaryValue.startsWith("Urgency:")){
      summaryValue = summaryValue.replace("Urgency:", "")
      summaryField.setFormValue(summaryValue)
   }
   else{
      summaryField.setFormValue(summaryValue)
   }

}