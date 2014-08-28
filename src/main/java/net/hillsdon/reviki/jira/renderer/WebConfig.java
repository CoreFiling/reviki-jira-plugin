package net.hillsdon.reviki.jira.renderer;

import java.util.Map;

import com.atlassian.jira.web.action.JiraWebActionSupport;

import webwork.action.ActionContext;

public class WebConfig extends JiraWebActionSupport {
  private final RevikiPluginConfiguration _pluginSettings;

  public WebConfig(final RevikiPluginConfiguration pluginSettings) {
    _pluginSettings = pluginSettings;
  }

  @Override
  protected String doExecute() {
    return INPUT;
  }

  public String doUpdate() {
    ActionContext ctx = ActionContext.getContext();
    Map params = ctx.getParametersImpl();

    // If the option is set, update the configuration.
    _pluginSettings.convertConfluenceLinks(params.containsKey("convertConfluence"));

    // Set the interwiki links
    if (!params.containsKey("interWikiLinks") || params.get("interWikiLinks") == null) {
      _pluginSettings.interWikiLinks("");
    }
    else {
      String[] iwls = (String[]) params.get("interWikiLinks");
      _pluginSettings.interWikiLinks(iwls[0]);
    }

    return SUCCESS;
  }

  /**
   * Check if we're currently converting Confluence-style links.
   */
  public boolean isConverting() {
    return _pluginSettings.convertConfluenceLinks();
  }

  /**
   * Get the interwiki links as a string
   */
  public String interLinks() {
    return _pluginSettings.interWikiLinksAsString();
  }

  private static final long serialVersionUID = 1L;
}
