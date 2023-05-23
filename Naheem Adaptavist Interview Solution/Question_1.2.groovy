import com.atlassian.jira.component.ComponentAccessor

// Get signed in user and  issue service components
def SignedInUser = ComponentAccessor.jiraAuthenticationContext.getLoggedInUser()
def issueService = ComponentAccessor.issueService

// variables 
def issue = event.issue
def issueId = issue.id
def userComment = event.getComment()
def commentBody = event.getComment().getBody()

// create issue input parameters object
def issueInputParameters = issueService.newIssueInputParameters()

// construct new issue description or update description
if(issue.getDescription() != null){
    def newDecription = issue.getDescription()+"\n\n"+ userComment.getAuthorFullName() + " - " + userComment.getCreated().dateTimeString +"\n"+ commentBody
    issueInputParameters.setDescription(newDecription);
}
else{
    def newDescription = userComment.getAuthorFullName() + " - " + userComment.getCreated().dateTimeString +"\n"+ commentBody
    issueInputParameters.setDescription(newDescription);
}

def validationResultUpdate = issueService.validateUpdate(SignedInUser, issueId, issueInputParameters)

assert validationResultUpdate.isValid() : validationResultUpdate.errorCollection

def issueResultUpdate = issueService.update(SignedInUser, validationResultUpdate)

assert issueResultUpdate.isValid() : issueResultUpdate.errorCollection