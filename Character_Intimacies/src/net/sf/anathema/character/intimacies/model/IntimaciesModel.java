package net.sf.anathema.character.intimacies.model;

import com.google.common.base.Strings;
import net.sf.anathema.character.generic.framework.additionaltemplate.listening.ConfigurableCharacterChangeListener;
import net.sf.anathema.character.generic.framework.additionaltemplate.listening.VirtueChangeListener;
import net.sf.anathema.character.generic.traits.GenericTrait;
import net.sf.anathema.character.generic.traits.TraitType;
import net.sf.anathema.character.generic.traits.types.OtherTraitType;
import net.sf.anathema.character.generic.traits.types.VirtueType;
import net.sf.anathema.character.intimacies.presenter.IIntimaciesModel;
import net.sf.anathema.character.library.removableentry.model.AbstractRemovableEntryModel;
import net.sf.anathema.character.library.trait.Trait;
import net.sf.anathema.character.library.virtueflaw.model.ConfigurableFlavorChangeAdapter;
import net.sf.anathema.character.main.hero.Hero;
import net.sf.anathema.character.main.hero.change.FlavoredChangeListener;
import net.sf.anathema.character.main.model.experience.ExperienceModelFetcher;
import net.sf.anathema.character.main.model.traits.TraitModelFetcher;
import net.sf.anathema.lib.control.IChangeListener;
import org.jmock.example.announcer.Announcer;

public class IntimaciesModel extends AbstractRemovableEntryModel<IIntimacy> implements IIntimaciesModel {

  private final Announcer<IChangeListener> changeControl = Announcer.to(IChangeListener.class);
  private String name;
  private Hero hero;

  public IntimaciesModel(Hero hero) {
    this.hero = hero;
    VirtueChangeListener convictionListener = new VirtueChangeListener() {
      @Override
      public void configuredChangeOccured() {
        for (IIntimacy entry : getEntries()) {
          entry.resetCurrentValue();
        }
        fireModelChangedEvent();
      }
    };
    convictionListener.addTraitTypes(VirtueType.Conviction);
    ConfigurableCharacterChangeListener maximumListener = new ConfigurableCharacterChangeListener() {
      @Override
      public void configuredChangeOccured() {
        fireModelChangedEvent();
        fireEntryChanged();
      }
    };
    maximumListener.addTraitTypes(VirtueType.Compassion, OtherTraitType.Willpower);
    hero.getChangeAnnouncer().addListener(new ConfigurableFlavorChangeAdapter(convictionListener));
    hero.getChangeAnnouncer().addListener(new ConfigurableFlavorChangeAdapter(maximumListener));
  }

  @Override
  public int getFreeIntimacies() {
    if (hero.getTemplate().getAdditionalRules().isRevisedIntimacies()) {
      return getCompassionValue() + getTrait(OtherTraitType.Willpower).getCurrentValue();
    }
    else {
      return getCompassionValue();
    }
  }

  protected int getCompassionValue() {
    return getTrait(VirtueType.Compassion).getCurrentValue();
  }

  @Override
  public void setCurrentName(String name) {
    this.name = name;
    fireEntryChanged();
  }

  @Override
  public void addChangeListener(FlavoredChangeListener listener) {
    hero.getChangeAnnouncer().addListener(listener);
  }

  @Override
  protected IIntimacy createEntry() {
    Intimacy intimacy = new Intimacy(hero, name, getInitialValue(), getConviction());
    intimacy.setComplete(!isCharacterExperienced());
    intimacy.addChangeListener(new IChangeListener() {
      @Override
      public void changeOccurred() {
        fireModelChangedEvent();
      }      
    });
    return intimacy;
  }

  private void fireModelChangedEvent() {
    changeControl.announce().changeOccurred();
  }

  @Override
  public int getCompletionValue() {
    return 5;
  }

  private GenericTrait getConviction() {
    return getTrait(VirtueType.Conviction);
  }

  private Trait getTrait(TraitType traitType) {
    return TraitModelFetcher.fetch(hero).getTrait(traitType);
  }

  @Override
  public int getIntimaciesLimit() {
    return getCompassionValue() + getTrait(OtherTraitType.Willpower).getCurrentValue();
  }

  private Integer getInitialValue() {
    if (isCharacterExperienced()) {
      return 0;
    }
    return getConviction().getCurrentValue();
  }

  @Override
  protected boolean isEntryAllowed() {
    return getEntries().size() < getIntimaciesLimit() && !Strings.isNullOrEmpty(name);
  }

  @Override
  public void addModelChangeListener(IChangeListener listener) {
    changeControl.addListener(listener);
  }

  @Override
  public boolean isCharacterExperienced() {
    return ExperienceModelFetcher.fetch(hero).isExperienced();
  }

}