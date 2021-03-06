package net.sf.anathema.hero.advance.overview.presenter;

import net.sf.anathema.lib.file.RelativePath;
import net.sf.anathema.lib.gui.AbstractUIConfiguration;
import net.sf.anathema.framework.environment.Resources;
import net.sf.anathema.lib.util.Identifier;

public class SelectIdentifierConfiguration<T extends Identifier> extends AbstractUIConfiguration<T> {
  private Resources resources;

  public SelectIdentifierConfiguration(Resources resources) {
    this.resources = resources;
  }

  @Override
  public String getLabel(T value) {
    if (isUnselected(value)) {
      return resources.getString("ComboBox.SelectLabel");
    }
    return resources.getString(getKeyForObject(value));
  }

  @Override
  public RelativePath getIconsRelativePath(T value) {
    if (isUnselected(value)) {
      return NO_ICON;
    }
    return getIconForObject(value);
  }

  @SuppressWarnings("UnusedParameters")
  protected RelativePath getIconForObject(T value) {
    return NO_ICON;
  }

  protected String getKeyForObject(T value) {
    return value.getId();
  }

  private boolean isUnselected(T value) {
    return value == null;
  }
}