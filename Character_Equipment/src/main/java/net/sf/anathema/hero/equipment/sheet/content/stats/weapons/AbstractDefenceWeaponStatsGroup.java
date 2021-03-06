package net.sf.anathema.hero.equipment.sheet.content.stats.weapons;

import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.PdfPTable;
import net.sf.anathema.hero.equipment.sheet.content.stats.weapon.IWeaponStats;
import net.sf.anathema.hero.equipment.sheet.content.stats.AbstractValueEquipmentStatsGroup;
import net.sf.anathema.framework.environment.Resources;

public abstract class AbstractDefenceWeaponStatsGroup extends AbstractValueEquipmentStatsGroup<IWeaponStats> {

  public AbstractDefenceWeaponStatsGroup(Resources resources) {
    super(resources, "Defence");
  }

  @Override
  public int getColumnCount() {
    return 2;
  }

  @Override
  public void addContent(PdfPTable table, Font font, IWeaponStats weapon) {
    if (weapon == null) {
      table.addCell(createEmptyValueCell(font));
      table.addCell(createFinalValueCell(font));
    } else {
      table.addCell(createEquipmentValueCell(font, weapon.getDefence()));
      if (weapon.getDefence() == null) {
        table.addCell(createFinalValueCell(font, (Integer) null));
      } else {
        table.addCell(createFinalValueCell(font, getDefenceValue(weapon)));
      }
    }
  }

  protected abstract int getDefenceValue(IWeaponStats weapon);
}