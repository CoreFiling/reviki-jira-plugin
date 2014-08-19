package net.hillsdon.reviki.jira.renderer;

import com.atlassian.jira.issue.RendererManager;
import com.atlassian.jira.issue.fields.renderer.IssueRenderContext;
import com.atlassian.jira.issue.fields.renderer.JiraRendererPlugin;
import com.atlassian.jira.plugin.renderer.JiraRendererModuleDescriptor;
import com.atlassian.sal.api.pluginsettings.PluginSettingsFactory;

public class JiraRevikiRenderingPlugin implements JiraRendererPlugin {
  public static final String RENDERER_TYPE = "atlassian-jira-reviki-renderer";

  private JiraRendererModuleDescriptor _jiraRendererModuleDescriptor;

  private final JiraRevikiRenderer _renderer;

  private final RevikiPluginConfiguration _pluginSettings;

  public JiraRevikiRenderingPlugin(final RendererManager rendererManager, final PluginSettingsFactory pluginSettingsFactory) {
    // The dependency injection doesn't seem to be able to cope with depending
    // on a class in the same plugin, so we have to settle for getting the
    // PluginSettingsFactory directly.
    _pluginSettings = new RevikiPluginConfiguration(pluginSettingsFactory);
    _renderer = new JiraRevikiRenderer(_pluginSettings, rendererManager);
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
}
