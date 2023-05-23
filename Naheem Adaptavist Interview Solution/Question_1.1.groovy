import com.atlassian.jira.component.ComponentAccessor

def customFieldManager = ComponentAccessor.getCustomFieldManager()
// Get field 1 and field 2
def field1 = customFieldManager.getCustomFieldObject("customfield_10114")
def field2 = customFieldManager.getCustomFieldObject("customfield_10113")

assert field1
assert field2

// Get field 1 and field 2 value
def value1 = issue.getCustomFieldValue(field1) as Double
def value2 = issue.getCustomFieldValue(field2) as Double

// Calculate custome field value and return
def calculatedValue = value1 + value2
return calculatedValue