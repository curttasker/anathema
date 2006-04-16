package net.sf.anathema.character.generic.template.magic;

import net.sf.anathema.character.generic.caste.ICasteType;
import net.sf.anathema.character.generic.magic.ICharm;
import net.sf.anathema.character.generic.magic.charms.MartialArtsLevel;
import net.sf.anathema.character.generic.rules.IExaltedRuleSet;

public interface ICharmTemplate {

  public ICharm[] getCharms(IExaltedRuleSet rules);

  public ICharm[] getMartialArtsCharms(IExaltedRuleSet rules);

  public MartialArtsLevel getMartialArtsLevel();

  public boolean knowsCharms();

  public boolean isMartialArtsCharmAllowed(
      ICharm martialArtsCharm,
      IGenericCharmConfiguration charmConfiguration,
      boolean isExperienced);

  public boolean isAllowedAlienCharms(ICasteType caste);
}