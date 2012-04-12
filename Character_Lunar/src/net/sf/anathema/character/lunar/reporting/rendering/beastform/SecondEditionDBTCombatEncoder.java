package net.sf.anathema.character.lunar.reporting.rendering.beastform;

import net.sf.anathema.character.equipment.impl.character.model.stats.CharacterStatsModifiers;
import net.sf.anathema.character.generic.character.IGenericTraitCollection;
import net.sf.anathema.character.generic.equipment.ICharacterStatsModifiers;
import net.sf.anathema.character.generic.impl.CharacterUtilities;
import net.sf.anathema.character.generic.type.ICharacterType;
import net.sf.anathema.character.generic.traits.types.AbilityType;
import net.sf.anathema.character.library.trait.specialties.HighestSpecialty;
import net.sf.anathema.character.lunar.beastform.BeastformTemplate;
import net.sf.anathema.character.lunar.beastform.presenter.IBeastformModel;
import net.sf.anathema.character.lunar.reporting.content.beastform.DBTCombatStatsContent;
import net.sf.anathema.character.reporting.second.content.combat.CombatStatsContent;
import net.sf.anathema.character.reporting.pdf.content.ReportSession;
import net.sf.anathema.character.reporting.pdf.rendering.extent.Bounds;
import net.sf.anathema.character.reporting.pdf.rendering.extent.Position;
import net.sf.anathema.character.reporting.pdf.rendering.general.LabelledValueEncoder;
import net.sf.anathema.character.reporting.pdf.rendering.general.box.ContentEncoder;
import net.sf.anathema.character.reporting.pdf.rendering.graphics.SheetGraphics;
import net.sf.anathema.lib.resources.IResources;


public class SecondEditionDBTCombatEncoder implements ContentEncoder {

  private final IResources resources;

  public SecondEditionDBTCombatEncoder(IResources resources) {
    this.resources = resources;
  }

  @Override
  public void encode(SheetGraphics graphics, ReportSession reportSession, Bounds bounds) {
    DBTCombatStatsContent content = createContent(reportSession);
    Position upperLeftCorner = new Position(bounds.x, bounds.getMaxY());
    LabelledValueEncoder encoder = new LabelledValueEncoder(2, upperLeftCorner, bounds.width, 3);
    encoder.addLabelledValue(graphics, 0, content.getJoinLabel(), content.getJoinBattle());
    displayDodgeWithSpecialty(graphics, encoder, content);
    upperLeftCorner = new Position(bounds.x, bounds.getMaxY() - 25);
    encoder = new LabelledValueEncoder(2, upperLeftCorner, bounds.width, 3);
    encoder.addLabelledValue(graphics, 0, content.getKnockdownLabel(), content.getKnockdownThreshold(), content.getKnockdownPool());
    encoder.addLabelledValue(graphics, 1, content.getStunningLabel(), content.getStunningThreshold(), content.getStunningPool());
    encoder.addComment(graphics, content.getThresholdPoolLabel(), 0);
    encoder.addComment(graphics, content.getThresholdPoolLabel(), 1);
  }

  @Override
  public boolean hasContent(ReportSession session) {
    return true;
  }

  @Override
  public String getHeader(ReportSession session) {
    return resources.getString("Sheet.Header.Lunar.WarForm.CombatValues");
  }
  
  private DBTCombatStatsContent createContent(ReportSession session) {
    return session.createContent(DBTCombatStatsContent.class);
  }
  
  private void displayDodgeWithSpecialty( SheetGraphics graphics, LabelledValueEncoder encoder, CombatStatsContent content ) {
      if( content.getDodgeDv() != content.getDodgeDvWithSpecialty() ) {
          encoder.addLabelledValue(graphics, 1, content.getDodgeLabel(), content.getDodgeDv(), content.getDodgeDvWithSpecialty());
          encoder.addComment(graphics, content.getDodgeSpecialtyLabel(), 1);
      } else {
          encoder.addLabelledValue(graphics, 1, content.getDodgeLabel(), content.getDodgeDv());
      }     
  }
}