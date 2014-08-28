package net.hillsdon.reviki.jira.renderer;

import java.util.regex.Pattern;

import com.atlassian.jira.component.ComponentAccessor;
import com.google.common.base.Optional;

import net.hillsdon.reviki.wiki.renderer.HtmlRenderer;
import net.hillsdon.reviki.wiki.renderer.creole.LinkResolutionContext;

/**
 * A simple interface to Reviki's HTML rendering capabilities.
 *
 * @author msw
 */
public final class JiraRevikiRenderer {
  /** Match Confluence-style links in single square brackets. */
  private static final Pattern confluenceLinks = Pattern.compile("([^\\[]|^)(\\[[~@]*[^\\\\,\\[\\]]+?\\])([^\\]]|$)");

  /** Replacement text to turn Confluence-style links into Reviki-style links. */
  private static final String revikiReplacement = "$1[$2]$3";

  /** Render Reviki markup to HTML, complete with link handling. */
  private static final String JIRA_PATH = ComponentAccessor.getApplicationProperties().getString("jira.baseurl");

  private static final HtmlRenderer _renderer = new HtmlRenderer(LinkResolutionContext.SIMPLE_LINKS.apply(JIRA_PATH + "/browse"));

  /** Plugin configuration. */
  private final RevikiPluginConfiguration _pluginSettings;

  public JiraRevikiRenderer(final RevikiPluginConfiguration pluginSettings) {
    _pluginSettings = pluginSettings;
  }

  /**
   * Render some markup to HTML.
   */
  public String render(final String text) {
    if (text == null) {
      return "";
    }

    String contents = text;

    // First fix any Confluence-style links for backwards-compatibility.
    if (_pluginSettings.convertConfluenceLinks()) {
      contents = confluenceToReviki(text);
    }

    // Try rendering it, and return the original markup if we fail.
    Optional<String> rendered = _renderer.render(contents);
    return rendered.isPresent() ? rendered.get() : text;
  }

  /**
   * Convert Confluence-style links ("[FOO-1]") to Reviki-style links
   * ("[[FOO-1]]"), this allows backwards compatibility in linking to issues.
   */
  private static String confluenceToReviki(final String text) {
    // Need to run the regex twice because of overlapping matches.
    //
    // eg: "[FOO-1] [FOO-1] [FOO-1]" - just replacing once results in
    // "[[FOO-1]] [FOO-1] [[FOO-1]]"

    String first = confluenceLinks.matcher(text).replaceAll(revikiReplacement);
    return confluenceLinks.matcher(first).replaceAll(revikiReplacement);
  }
}
