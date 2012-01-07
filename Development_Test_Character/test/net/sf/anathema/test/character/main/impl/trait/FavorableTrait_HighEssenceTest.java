package net.sf.anathema.test.character.main.impl.trait;

import net.sf.anathema.character.generic.caste.ICasteType;
import net.sf.anathema.character.generic.dummy.DummyCasteType;
import net.sf.anathema.character.generic.dummy.DummyCharacterModelContext;
import net.sf.anathema.character.generic.dummy.DummyGenericTrait;
import net.sf.anathema.character.generic.framework.additionaltemplate.model.ICharacterModelContext;
import net.sf.anathema.character.generic.framework.additionaltemplate.model.ITraitContext;
import net.sf.anathema.character.generic.impl.traits.SimpleTraitTemplate;
import net.sf.anathema.character.generic.traits.ITraitTemplate;
import net.sf.anathema.character.generic.traits.types.AbilityType;
import net.sf.anathema.character.generic.traits.types.OtherTraitType;
import net.sf.anathema.character.impl.model.context.trait.CreationTraitValueStrategy;
import net.sf.anathema.character.impl.model.context.trait.ExperiencedTraitValueStrategy;
import net.sf.anathema.character.impl.model.context.trait.ProxyTraitValueStrategy;
import net.sf.anathema.character.library.trait.DefaultTrait;
import net.sf.anathema.character.library.trait.FriendlyValueChangeChecker;
import net.sf.anathema.character.library.trait.favorable.IIncrementChecker;
import net.sf.anathema.character.library.trait.rules.FavorableTraitRules;
import net.sf.anathema.test.character.BasicCharacterTestCase;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FavorableTrait_HighEssenceTest {

  private IIncrementChecker incrementChecker = EasyMock.createStrictMock(IIncrementChecker.class);
  private ProxyTraitValueStrategy valueStrategy;
  private DefaultTrait trait;
  private DummyCharacterModelContext modelContext;

  @Before
  public void createTrait() throws Exception {
    this.valueStrategy = new ProxyTraitValueStrategy(new CreationTraitValueStrategy());
    this.modelContext = new BasicCharacterTestCase().createModelContextWithEssence2(valueStrategy);
    this.trait = createObjectUnderTest(modelContext);
  }


  @Test
  public void testSetValueTo6OnExperiencedCharacterWithHighEssence() throws Exception {
    valueStrategy.setStrategy(new ExperiencedTraitValueStrategy());
    modelContext.getCharacter().addTrait(new DummyGenericTrait(OtherTraitType.Essence, 6));
    trait.setCurrentValue(6);
    assertEquals(6, trait.getCurrentValue());
  }

  private DefaultTrait createObjectUnderTest(ICharacterModelContext context) {
    ITraitTemplate archeryTemplate = SimpleTraitTemplate.createEssenceLimitedTemplate(0);
    ITraitContext traitContext = context.getTraitContext();
    FavorableTraitRules rules = new FavorableTraitRules(
            AbilityType.Archery,
            archeryTemplate,
            traitContext.getLimitationContext());
    return new DefaultTrait(
            rules,
            new ICasteType[]{new DummyCasteType()},
            traitContext,
            context.getBasicCharacterContext(),
            context.getCharacterListening(),
            new FriendlyValueChangeChecker(),
            incrementChecker);
  }
}