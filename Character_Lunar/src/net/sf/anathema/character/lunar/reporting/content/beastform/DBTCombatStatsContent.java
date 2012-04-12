package net.sf.anathema.character.lunar.reporting.content.beastform;

import net.sf.anathema.character.reporting.second.content.combat.CombatStatsContent;
import net.sf.anathema.character.generic.character.IGenericCharacter;
import net.sf.anathema.character.generic.character.IGenericTraitCollection;
import net.sf.anathema.character.lunar.beastform.BeastformTemplate;
import net.sf.anathema.character.lunar.beastform.presenter.IBeastformModel;
import net.sf.anathema.lib.resources.IResources;

public class DBTCombatStatsContent extends CombatStatsContent {
    
    private IGenericTraitCollection beastformTraitCollection;
    
    public DBTCombatStatsContent(IGenericCharacter character, IResources resources) {
        super(character, resources);
        IBeastformModel additionalModel = (IBeastformModel) character.getAdditionalModel(BeastformTemplate.TEMPLATE_ID);
        beastformTraitCollection = additionalModel.getBeastTraitCollection();
    }
    
    protected IGenericTraitCollection getTraitCollection() {
      return beastformTraitCollection;
    }
}