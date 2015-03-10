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

  public JiraHtmlRenderer(SimplePageStore pageStore, LinkPartsHandler linkHandler, LinkPartsHandler imageHandler, Supplier<List<Macro>> macros) {
    super(pageStore, linkHandler, imageHandler, macros);
  }

  @Override
  public ASTNode parse(final PageInfo page) {
    _page = page;
    // Use JIRA style links
    CreoleTokens lexer = new CreoleTokens(null, true);
    return CreoleRenderer.renderWithLexer(getVisitor(_page), lexer, _macros);
  }
}
