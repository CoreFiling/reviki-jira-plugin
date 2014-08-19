package net.hillsdon.reviki.jira.renderer;

import java.util.Map;

import com.atlassian.jira.web.action.JiraWebActionSupport;
import com.atlassian.sal.api.pluginsettings.PluginSettingsFactory;

import webwork.action.ActionContext;

public class WebConfig extends JiraWebActionSupport {
  private final RevikiPluginConfiguration _pluginSettings;

  public WebConfig(final PluginSettingsFactory pluginSettingsFactory) {
    _pluginSettings = new RevikiPluginConfiguration(pluginSettingsFactory);
  }

  @Override
  protected String doExecute() {
    return INPUT;
  }

  public String doUpdate() {
    ActionContext ctx = ActionContext.getContext();
    Map params = ctx.getParametersImpl();

    // If the option is set, update the configuration.
    _pluginSettings.put(JiraRevikiRenderer.CONFLUENCE_LINK_CONFIG_KEY, params.containsKey("convertConfluence"));

    return SUCCESS;
  }

  /**
   * Check if we're currently converting Confluence-style links.
   */
  public boolean isConverting() {
    Object val = _pluginSettings.get(JiraRevikiRenderer.CONFLUENCE_LINK_CONFIG_KEY);
    return val == null || ((Boolean) val).booleanValue();
  }

  private static final long serialVersionUID = 1L;
}
