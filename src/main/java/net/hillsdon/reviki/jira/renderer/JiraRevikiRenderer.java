package net.hillsdon.reviki.jira.renderer;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.atlassian.jira.issue.RendererManager;
import com.atlassian.jira.issue.fields.renderer.IssueRenderContext;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.base.Optional;

import net.hillsdon.reviki.vc.PageInfo;
import net.hillsdon.reviki.vc.SimplePageStore;
import net.hillsdon.reviki.vc.impl.DummyPageStore;
import net.hillsdon.reviki.vc.impl.PageInfoImpl;
import net.hillsdon.reviki.web.urls.InterWikiLinker;
import net.hillsdon.reviki.web.urls.InternalLinker;
import net.hillsdon.reviki.web.urls.URLOutputFilter;
import net.hillsdon.reviki.web.urls.SimpleWikiUrls;
import net.hillsdon.reviki.wiki.renderer.HtmlRenderer;
import net.hillsdon.reviki.wiki.renderer.creole.DummyLinkHandler;
import net.hillsdon.reviki.wiki.renderer.creole.LinkPartsHandler;
import net.hillsdon.reviki.wiki.renderer.creole.LinkResolutionContext;
import net.hillsdon.reviki.wiki.renderer.macro.Macro;

/**
 * A simple interface to Reviki's HTML rendering capabilities.
 *
 * @author msw
 */
public final class JiraRevikiRenderer {
  private static final HtmlRenderer _renderer;

  static {
    SimpleWikiUrls wikiUrls = new SimpleWikiUrls() {
      public String pagesRoot() {
        return "/";
      }

      public URI page(String pageName) {
        URI root = URI.create(pagesRoot());
        try {
          String path = root.getPath();
          if (!path.endsWith("/")) {
            path = path + "/";
          }
          return new URI(root.getScheme(), root.getUserInfo(), root.getHost(), root.getPort(), path + pageName, root.getQuery(), root.getFragment());
        }
        catch (URISyntaxException e) {
          throw new RuntimeException(e);
        }
      }
    };

    InternalLinker linker = new InternalLinker(wikiUrls);
    InterWikiLinker wikilinker = new InterWikiLinker();
    SimplePageStore pageStore = new DummyPageStore();

    LinkResolutionContext resolver = new LinkResolutionContext(linker, wikilinker, pageStore);

    LinkPartsHandler linkHandler = new DummyLinkHandler(DummyLinkHandler.ANCHOR, resolver);
    LinkPartsHandler imageHandler = new DummyLinkHandler(DummyLinkHandler.IMAGE, resolver);
    Supplier<List<Macro>> macros = Suppliers.ofInstance((List<Macro>) new LinkedList<Macro>());

    _renderer = new HtmlRenderer(pageStore, linkHandler, imageHandler, macros);
  }

  public static String render(final String text, final RendererManager rendererManager, final IssueRenderContext ctx) {
    PageInfo page = new PageInfoImpl("", "", text, Collections.<String, String> emptyMap());
    Optional<String> rendered = _renderer.render(page, URLOutputFilter.NULL);

    return rendered.isPresent() ? rendered.get() : text;
  }
}
