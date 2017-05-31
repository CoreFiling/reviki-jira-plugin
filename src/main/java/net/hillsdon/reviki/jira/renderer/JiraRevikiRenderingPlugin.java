package net.hillsdon.reviki.jira.renderer;

import com.atlassian.jira.issue.RendererManager;
import com.atlassian.jira.issue.fields.renderer.IssueRenderContext;
import com.atlassian.jira.issue.fields.renderer.JiraRendererPlugin;
import com.atlassian.jira.plugin.renderer.JiraRendererModuleDescriptor;

public class JiraRevikiRenderingPlugin implements JiraRendererPlugin {
  public static final String RENDERER_TYPE = "reviki-renderer";

  private JiraRendererModuleDescriptor _jiraRendererModuleDescriptor;

  private final JiraRevikiRenderer _renderer;
  private final RendererManager _rendererManager;

  public JiraRevikiRenderingPlugin(final RendererManager rendererManager, final JiraRevikiRenderer renderer) {
    _renderer = renderer;
    _rendererManager = rendererManager;
  }

  @Override
  public JiraRendererModuleDescriptor getDescriptor() {
    return _jiraRendererModuleDescriptor;
  }

  @Override
  public String getRendererType() {
    return RENDERER_TYPE;
  }

  @Override
  public void init(final JiraRendererModuleDescriptor jiraRMD) {
    _jiraRendererModuleDescriptor = jiraRMD;
  }

  @Override
  public String render(final String text, final IssueRenderContext ctx) {
    if (text == null) {
      return null;
    }
    if (text.startsWith("{")) {
      for (JiraRendererPlugin p : _rendererManager.getAllActiveRenderers()) {
        String[] names;
        if ("atlassian-wiki-renderer".equals(p.getRendererType())) {
          names = new String[]{p.getRendererType(), "jira"};
        }
        else if (RENDERER_TYPE.equals(p.getRendererType())) {
          names = new String[]{p.getRendererType(), "reviki"};
        }
        else if ("jira-gherkin-renderer".equals(p.getRendererType())) {
          names = new String[]{p.getRendererType(), "markdown"};
        }
        else {
          names = new String[]{p.getRendererType()};
        }
        for (String name : names) {
          String macro = "{" + name + "}";
          if (text.startsWith(macro)) {
            String trimmedText = text.substring(macro.length()).trim();
            if (trimmedText.endsWith(macro)) {
              trimmedText = trimmedText.substring(0, trimmedText.length() - macro.length());
            }
            return p.render(trimmedText, ctx);
          }
        }
      }
    }
    return _renderer.render(text);
  }

  @Override
  public String renderAsText(final String text, final IssueRenderContext ctx) {
    return text;
  }

  @Override
  public Object transformForEdit(final Object rawValue) {
    return rawValue;
  }

  @Override
  public Object transformFromEdit(final Object editValue) {
    return editValue;
  }
}
