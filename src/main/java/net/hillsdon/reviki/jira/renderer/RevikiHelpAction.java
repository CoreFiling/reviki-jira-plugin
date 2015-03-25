package net.hillsdon.reviki.jira.renderer;

import com.atlassian.jira.web.action.JiraWebActionSupport;

public class RevikiHelpAction extends JiraWebActionSupport {
  @Override
  protected String doExecute() throws Exception {
    return SUCCESS;
  }
}
