package net.hillsdon.reviki.jira.renderer;

import com.atlassian.renderer.RenderContext;
import com.atlassian.renderer.v2.RenderMode;
import com.atlassian.renderer.v2.macro.BaseMacro;

import java.util.Map;

/** Allow {jira} blocks when already using the JIRA renderer.  This means that comments or descriptions can always be surrounded by {jira} or {reviki} tags, whichever is the default renderer. */
public class JiraJiraRenderingMacro extends BaseMacro {
  public boolean isInline() {
    return false;
  }

  public boolean hasBody() {
    return true;
  }

  public RenderMode getBodyRenderMode() {
    return RenderMode.ALL;
  }

  public String execute(final Map map, final String body, final RenderContext renderContext) {
    return body;
  }
}
