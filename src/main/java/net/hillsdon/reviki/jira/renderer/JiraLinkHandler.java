package net.hillsdon.reviki.jira.renderer;

import net.hillsdon.reviki.vc.PageReference;
import net.hillsdon.reviki.wiki.renderer.creole.LinkParts;
import net.hillsdon.reviki.wiki.renderer.creole.LinkResolutionContext;
import net.hillsdon.reviki.wiki.renderer.creole.SimpleLinkHandler;

public class JiraLinkHandler extends SimpleLinkHandler {

  public JiraLinkHandler(String fmat, LinkResolutionContext context) {
    super(fmat, context);
  }

  /**
   * Allow all LinkParts to be links, non-JIRA issues have been filtered
   * by JiraInternalLinker
   */
  @Override
  public boolean isAcronymNotLink(PageReference page, LinkParts parts) {
    return false;
  }
}
