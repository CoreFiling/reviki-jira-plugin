package net.hillsdon.reviki.jira.renderer;

import com.atlassian.jira.issue.RendererManager;
import com.atlassian.jira.issue.fields.renderer.IssueRenderContext;
import com.atlassian.jira.issue.fields.renderer.JiraRendererPlugin;
import com.atlassian.jira.plugin.renderer.JiraRendererModuleDescriptor;

public class JiraRevikiRenderingPlugin implements JiraRendererPlugin {
  public static final String RENDERER_TYPE = "atlassian-jira-reviki-renderer";

  private JiraRendererModuleDescriptor _jiraRendererModuleDescriptor;

  private final RendererManager _rendererManager;

  public JiraRevikiRenderingPlugin(final RendererManager rendererManager)
  {
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
    return JiraRevikiRenderer.render(text, _rendererManager, ctx);
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