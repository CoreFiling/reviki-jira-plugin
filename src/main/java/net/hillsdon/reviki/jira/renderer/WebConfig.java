package net.hillsdon.reviki.jira.renderer;

import java.util.Map;

import com.atlassian.jira.web.action.JiraWebActionSupport;
import com.atlassian.sal.api.pluginsettings.PluginSettings;
import com.atlassian.sal.api.pluginsettings.PluginSettingsFactory;

import webwork.action.ActionContext;

public class WebConfig extends JiraWebActionSupport {
  private final PluginSettings _pluginSettings;

  public WebConfig(final PluginSettingsFactory pluginSettings) {
    _pluginSettings = pluginSettings.createSettingsForKey(JiraRevikiRenderingPlugin.SETTINGS_KEY);
  }

  @Override
  protected String doExecute() {
    return INPUT;
  }

  public String doUpdate() {
    ActionContext ctx = ActionContext.getContext();
    Map params = ctx.getParametersImpl();

    // If the option is set, update the configuration.
    //
    // See JiraRevikiRenderer for an explanation on why this is a string and not
    // a boolean.
    String val = params.containsKey("convertConfluence") ? "y" : "n";
    _pluginSettings.put(JiraRevikiRenderer.CONFLUENCE_LINK_CONFIG_KEY, val);

    return SUCCESS;
  }

  /**
   * Check if we're currently converting Confluence-style links.
   */
  public boolean isConverting() {
    Object val = _pluginSettings.get(JiraRevikiRenderer.CONFLUENCE_LINK_CONFIG_KEY);
    return val == null || val.equals("y");
  }

  private static final long serialVersionUID = 1L;
}
