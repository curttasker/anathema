package net.sf.anathema.character.main.presenter;

import net.sf.anathema.character.main.library.trait.Trait;
import net.sf.anathema.character.main.library.trait.presenter.TraitPresenter;
import net.sf.anathema.character.main.view.AdvantageView;
import net.sf.anathema.framework.value.IIntValueView;
import net.sf.anathema.lib.gui.Presenter;
import net.sf.anathema.lib.resources.Resources;

public class WillpowerConfigurationPresenter implements Presenter {

  private final Trait willpower;
  private final AdvantageView view;
  private final Resources resources;

  public WillpowerConfigurationPresenter(Resources resources, Trait willpower, AdvantageView view) {
    this.resources = resources;
    this.willpower = willpower;
    this.view = view;
  }

  @Override
  public void initPresentation() {
    String labelText = resources.getString("WillpowerType.Name");
    IIntValueView willpowerView = view.addWillpower(labelText, willpower.getCurrentValue(), willpower.getMaximalValue());
    new TraitPresenter(willpower, willpowerView).initPresentation();
  }
}