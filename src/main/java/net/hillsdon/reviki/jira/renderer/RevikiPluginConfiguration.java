package net.hillsdon.reviki.jira.renderer;

import com.atlassian.sal.api.pluginsettings.PluginSettings;
import com.atlassian.sal.api.pluginsettings.PluginSettingsFactory;

public class RevikiPluginConfiguration {
  public static final String SETTINGS_KEY = "JiraRevikiRenderer";

  private static final String CONFLUENCE_LINK_CONFIG_KEY = "convertConfluence";

  private static final String BOOLEAN_TRUE = "__reviki_bool_y";

  private static final String BOOLEAN_FALSE = "__reviki_bool_n";

  private final PluginSettings _delegate;

  public RevikiPluginConfiguration(final PluginSettingsFactory pluginSettingsFactory) {
    _delegate = pluginSettingsFactory.createSettingsForKey(SETTINGS_KEY);
  }

  /**
   * Check if we're converting Confluence-style links to Reviki-style links.
   */
  public boolean convertConfluenceLinks() {
    Object out = _delegate.get(CONFLUENCE_LINK_CONFIG_KEY);

    return out == null || out.equals(BOOLEAN_TRUE);
  }

  public void convertConfluenceLinks(boolean val) {
    _delegate.put(CONFLUENCE_LINK_CONFIG_KEY, val ? BOOLEAN_TRUE : BOOLEAN_FALSE);
  }
}
