package net.hillsdon.reviki.jira.renderer;

import java.util.HashMap;
import java.util.Map;

import com.atlassian.sal.api.pluginsettings.PluginSettings;
import com.atlassian.sal.api.pluginsettings.PluginSettingsFactory;

public class RevikiPluginConfiguration {
  public static final String SETTINGS_KEY = "JiraRevikiRenderer";

  private static final String CONFLUENCE_LINK_CONFIG_KEY = "convertConfluence";

  private static final String IWL_CONFIG_KEY = "interWikiLinks";

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

  /**
   * Get all the interwiki links
   */
  public Map<String, String> interWikiLinks() {
    Map<String, String> out = new HashMap<String, String>();

    for (String line : interWikiLinksAsString().split("\\n")) {
      int idx = line.indexOf(" ");

      if (idx == -1) {
        continue;
      }

      String prefix = line.substring(0, idx).trim();
      String target = line.substring(idx).trim();

      out.put(prefix, target);
    }

    return out;
  }

  /**
   * Get all the interwiki links as text.
   */
  public String interWikiLinksAsString() {
    Object iwls = _delegate.get(IWL_CONFIG_KEY);
    if (iwls != null && iwls instanceof String) {
      return (String) iwls;
    }
    else {
      return "";
    }
  }

  /**
   * Set the interwiki links. Takes a newline-delimited set of space-delimited
   * pairs. Lines which don't contain a "%s" are ignored.
   */
  public void interWikiLinks(final String iwls) {
    StringBuilder sb = new StringBuilder();

    for (String line : iwls.split("\\r?\\n")) {
      if (line.contains("%s")) {
        sb.append(line);
        sb.append("\n");
      }
    }

    _delegate.put(IWL_CONFIG_KEY, sb.toString());
  }
}
