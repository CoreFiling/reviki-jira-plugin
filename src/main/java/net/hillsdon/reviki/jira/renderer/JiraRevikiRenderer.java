package net.hillsdon.reviki.jira.renderer;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Pattern;

import com.atlassian.jira.issue.RendererManager;
import com.atlassian.jira.issue.fields.renderer.IssueRenderContext;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
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
  private static final HtmlRenderer _renderer = new HtmlRenderer(LinkResolutionContext.SIMPLE_LINKS.apply("/jira/browse"));

  /** Plugin configuration. */
  private final RevikiPluginConfiguration _pluginSettings;

  /** Reference to the renderer manager. */
  private final RendererManager _rendererManager;

  public JiraRevikiRenderer(final RevikiPluginConfiguration pluginSettings, final RendererManager rendererManager) {
    _pluginSettings = pluginSettings;
    _rendererManager = rendererManager;
  }

  /**
   * Render some markup to HTML.
   */
  public String render(final String text, final IssueRenderContext ctx) {
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
