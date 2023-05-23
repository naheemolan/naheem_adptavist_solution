/*
The email to send to the customer


Hello,

Thanks for reaching out. I provided the script below that will update the textField and Select List to display "Updated" as you intended. For the "Select List", "Updated" needs to be added as part of the Select List Options. This is because the Select List custom field is a dropdown and not a freeform text field.  Thus, we need to feed it an option, instead of a string like in textField. This link provide an example: https://library.adaptavist.com/entity/update-cf-value-listener

Thanks and let me know if you have any questions. 

Regards

*/


// The Script:

import com.atlassian.jira.issue.Issue
import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.issue.ModifiedValue
import com.atlassian.jira.issue.util.DefaultIssueChangeHolder

// Obtain the necessary Jira components
def issueManager = ComponentAccessor.getIssueManager()
def customFieldManager = ComponentAccessor.getCustomFieldManager()
def optionsManager = ComponentAccessor.getOptionsManager()

// custom field's ID
final selectListcustomFieldId = "customfield_10302" // replace with selectList ID 
final textFieldCustomFieldId = "customfield_10300" //replace with textFieldID

// issue that triggered the event
def issue = event.issue

// new value to replace select list. It has been part of an option in the select list
final newValue = "Updated"

def changeHolder = new DefaultIssueChangeHolder()

// current value of the custom textfield
def textField =customFieldManager.getCustomFieldObject(textFieldCustomFieldId)
def textFieldValue = issue.getCustomFieldValue(textField)
// check if there is textfield in issue before updating with new value
if (textField && textFieldValue){
    textField.updateValue(null, issue, new ModifiedValue(textFieldValue, newValue), changeHolder)
}

// current value of the custom select list
def selectListCustomField = customFieldManager.getCustomFieldObject(selectListcustomFieldId)
def fieldValue = issue.getCustomFieldValue(selectListCustomField)
// check if there is select list in issue before updating with new value
if (selectListCustomField && fieldValue){
    def config = selectListCustomField.getRelevantConfig(issue)
    // Get all options
    def options = optionsManager.getOptions(config)
    // Get option to replace option with
    def optionToSet = options.find { it.value == newValue }
    assert optionToSet
    
    selectListCustomField.updateValue(null, issue, new ModifiedValue(fieldValue, optionToSet), changeHolder)
}
