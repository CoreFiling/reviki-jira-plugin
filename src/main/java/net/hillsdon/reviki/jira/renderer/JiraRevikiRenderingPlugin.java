package net.hillsdon.reviki.jira.renderer;

import java.io.InputStream;
import java.util.Properties;

import com.atlassian.jira.issue.RendererManager;
import com.atlassian.jira.issue.fields.renderer.IssueRenderContext;
import com.atlassian.jira.issue.fields.renderer.JiraRendererPlugin;
import com.atlassian.jira.plugin.renderer.JiraRendererModuleDescriptor;

public class JiraRevikiRenderingPlugin implements JiraRendererPlugin {
  public static final String RENDERER_TYPE = "atlassian-jira-reviki-renderer";

  private JiraRendererModuleDescriptor _jiraRendererModuleDescriptor;

  private final JiraRevikiRenderer _renderer;

  public JiraRevikiRenderingPlugin(final RendererManager rendererManager) {
    _renderer = new JiraRevikiRenderer(getProperties(), rendererManager);
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
    return _renderer.render(text, ctx);
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

  /**
   * Get the plugin properties.
   */
  private Properties getProperties() {
    try {
      Properties properties = new Properties();
      InputStream stream = getClass().getClassLoader().getResourceAsStream("reviki-renderer.properties");
      properties.load(stream);
      stream.close();
      return properties;
    }
    catch (Exception e) {
      return new Properties();
    }
  }
}
