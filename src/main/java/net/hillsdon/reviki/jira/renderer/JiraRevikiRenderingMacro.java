package net.hillsdon.reviki.jira.renderer;

import com.atlassian.renderer.RenderContext;
import com.atlassian.renderer.v2.RenderMode;
import com.atlassian.renderer.v2.macro.BaseMacro;

import java.util.Map;

public class JiraRevikiRenderingMacro extends BaseMacro {

  private final JiraRevikiRenderer _renderer;

  public JiraRevikiRenderingMacro(final JiraRevikiRenderer renderer) {
    _renderer = renderer;
  }

  public boolean isInline() {
    return false;
  }

  public boolean hasBody() {
    return true;
  }

  public RenderMode getBodyRenderMode() {
    return RenderMode.NO_RENDER;
  }

  public String execute(final Map map, final String body, final RenderContext renderContext) {
    return _renderer.render(body);
  }
}