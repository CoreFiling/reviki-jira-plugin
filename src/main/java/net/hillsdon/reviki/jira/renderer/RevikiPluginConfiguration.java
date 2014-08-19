package net.hillsdon.reviki.jira.renderer;

import com.atlassian.sal.api.pluginsettings.PluginSettings;
import com.atlassian.sal.api.pluginsettings.PluginSettingsFactory;

public class RevikiPluginConfiguration implements PluginSettings {
  public static final String SETTINGS_KEY = "JiraRevikiRenderer";

  private final String BOOLEAN_TRUE = "__reviki_bool_y";

  private final String BOOLEAN_FALSE = "__reviki_bool_n";

  private final PluginSettings _delegate;

  public RevikiPluginConfiguration(final PluginSettingsFactory pluginSettingsFactory) {
    _delegate = pluginSettingsFactory.createSettingsForKey(SETTINGS_KEY);
  }

  /**
   * This decodes Booleans in the same way {@link #put(String, Object)} encodes
   * them.
   */
  @Override
  public Object get(final String name) {
    Object out = _delegate.get(name);

    if (out != null && out.equals(BOOLEAN_TRUE)) {
      return Boolean.TRUE;
    }
    else if (out != null && out.equals(BOOLEAN_FALSE)) {
      return Boolean.FALSE;
    }
    else {
      return out;
    }
  }

  public <T> T get(final String name, T def) {
    @SuppressWarnings("unchecked")
    T out = (T) _delegate.get(name);

    return (out == null) ? def : out;
  }

  /**
   * Put a new value into the store.
   * 
   * The type is woefully misleading. Only Strings can be stored. Booleans are
   * allowed by encoding to values unlikely to collide with "actual" strings.
   */
  @Override
  public Object put(final String name, final Object value) throws IllegalArgumentException {
    if (value instanceof String) {
      return putString(name, (String) value);
    }
    else if (value instanceof Boolean) {
      return putBool(name, (Boolean) value);
    }
    else {
      throw new IllegalArgumentException("Can only store strings!");
    }
  }

  public String putString(final String name, final String value) {
    _delegate.put(name, value);
    return value;
  }

  public Boolean putBool(final String name, final Boolean value) {
    _delegate.put(name, value.booleanValue() ? BOOLEAN_TRUE : BOOLEAN_FALSE);
    return value;
  }

  public Boolean putBool(final String name, final boolean value) {
    return putBool(name, Boolean.valueOf(value));
  }

  @Override
  public Object remove(final String name) {
    return _delegate.remove(name);
  }
}
