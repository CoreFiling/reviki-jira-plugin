package ut.net.hillsdon.reviki.jira.renderer;

import org.junit.Test;
import net.hillsdon.reviki.jira.renderer.MyPluginComponent;
import net.hillsdon.reviki.jira.renderer.MyPluginComponentImpl;

import static org.junit.Assert.assertEquals;

public class MyComponentUnitTest
{
    @Test
    public void testMyName()
    {
        MyPluginComponent component = new MyPluginComponentImpl(null);
        assertEquals("names do not match!", "myComponent",component.getName());
    }
}