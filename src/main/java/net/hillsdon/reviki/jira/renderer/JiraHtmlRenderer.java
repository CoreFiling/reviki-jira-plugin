package net.hillsdon.reviki.jira.renderer;

import java.util.List;

import com.google.common.base.Supplier;

import net.hillsdon.reviki.vc.PageInfo;
import net.hillsdon.reviki.vc.SimplePageStore;
import net.hillsdon.reviki.wiki.renderer.HtmlRenderer;
import net.hillsdon.reviki.wiki.renderer.creole.CreoleRenderer;
import net.hillsdon.reviki.wiki.renderer.creole.CreoleTokens;
import net.hillsdon.reviki.wiki.renderer.creole.LinkPartsHandler;
import net.hillsdon.reviki.wiki.renderer.creole.ast.ASTNode;
import net.hillsdon.reviki.wiki.renderer.macro.Macro;

public class JiraHtmlRenderer extends HtmlRenderer {
  private final boolean _jiraStyleLinks;

  public JiraHtmlRenderer(final SimplePageStore pageStore, final LinkPartsHandler linkHandler, final LinkPartsHandler imageHandler, final Supplier<List<Macro>> macros, final boolean jiraStyleLinks) {
    super(pageStore, linkHandler, imageHandler, macros);
    _jiraStyleLinks = jiraStyleLinks;
  }

  @Override
  public ASTNode parse(final PageInfo page) {
    _page = page;
    // Use JIRA style links where requested
    CreoleTokens lexer = new CreoleTokens(null, _jiraStyleLinks);
    return CreoleRenderer.renderWithLexer(getVisitor(_page), lexer, _macros);
  }
}
