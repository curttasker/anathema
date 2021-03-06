package net.sf.anathema.lib.gui.action;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import net.sf.anathema.lib.gui.icon.IconImageIcon;
import net.sf.anathema.lib.gui.swing.GuiUtilities;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;
import java.awt.Component;
import java.awt.event.ActionEvent;

public abstract class SmartAction extends AbstractAction implements IEnableable {

  public SmartAction() {
    this(new ActionConfiguration());
  }

  public SmartAction(String name) {
    this(new ActionConfiguration(name));
  }

  public SmartAction(String name, Icon icon) {
    this(new ActionConfiguration(name, icon));
  }

  public SmartAction(Icon icon) {
    this(null, icon);
  }

  public SmartAction(IActionConfiguration configuration) {
    Preconditions.checkNotNull(configuration);
    setName(configuration.getName());
    setIcon(configuration.getIcon());
    String toolTipText = configuration.getToolTipText();
    setToolTipText(Strings.isNullOrEmpty(toolTipText) ? null : toolTipText);
  }

  @Override
  public final void actionPerformed(ActionEvent e) {
    Component parentComponent = getParentComponent(e);
    execute(parentComponent);
  }

  /**
   * Called from the action when being invoked by the user. The given parentComponeent can be used
   * as parent for any dialogs shown in this method.
   *
   * @param parentComponent A parent swing from which the action was invoked. Can be used as
   *                        parent for new dialogs.
   */
  protected abstract void execute(Component parentComponent);

  /**
   * Sets the name of the action - may include the mnemonic character but must not contain line
   * delimiters. Mnemonics are indicated by an '&' that causes the next character to be the
   * mnemonic. When the user presses a key sequence that matches the mnemonic, a selection event
   * occurs. On most platforms, the mnemonic appears underlined but may be emphasised in a platform
   * specific manner. The mnemonic indicator character '&' can be escaped by doubling it in the
   * string, causing a single '&' to be displayed.
   */
  public final void setName(String name) {
    if (name != null) {
      MnemonicLabel mnemonicLabel = MnemonicLabelParser.parse(name);
      putValue(Action.NAME, mnemonicLabel.getPlainText());
      if (mnemonicLabel.getMnemonicCharacter() != null) {
        setMnemonic(mnemonicLabel.getMnemonicCharacter().charValue());
      }
    } else {
      putValue(Action.NAME, name);
    }
  }

  public final void setNameWithoutMnemonic(String name) {
    putValue(Action.NAME, name);
  }

  protected final Component getParentComponent(ActionEvent e) {
    return GuiUtilities.getWindowFor(e);
  }

  public final void setMnemonic(int keyCode) {
    putValue(MNEMONIC_KEY, new Integer(keyCode));
  }

  public final void setMnemonic(char character) {
    char ch = Character.toUpperCase(character);
    if (!isLetter(ch) && !isDigit(ch)) {
      throw new IllegalArgumentException("Unsupported mnemonic character'" + character + "'.");
    }
    setMnemonic((int) ch);
  }

  public final void setAcceleratorKey(KeyStroke keyStroke) {
    putValue(ACCELERATOR_KEY, keyStroke);
  }

  private static boolean isDigit(char ch) {
    return ch >= '0' && ch <= '9';
  }

  private static boolean isLetter(char ch) {
    return Character.isLetter(ch);
  }

  public final void setIcon(Icon icon) {
    if (icon instanceof ImageIcon || icon == null) {
      putValue(SMALL_ICON, icon);
      return;
    }
    ImageIcon imageIcon = new IconImageIcon(icon);
    putValue(SMALL_ICON, imageIcon);
  }

  public final void setToolTipText(String shortDescription) {
    putValue(Action.SHORT_DESCRIPTION, shortDescription);
  }
}