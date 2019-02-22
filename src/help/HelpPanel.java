package help;

import java.awt.EventQueue;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import net.miginfocom.swing.MigLayout;
import ui.UIElements;
import javax.swing.JTextPane;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;

/**
 * A template for all the Help cards.  
 */
public class HelpPanel extends JPanel {
	private static final long serialVersionUID = 1445106592900321397L;

	/****************
	 * UI ELEMENTS *
	 ***************/

	// All the elements are public as this class is used as a template and they all
	// need to be overwritten (setText()) by the cards that extend this class.
	public JLabel titleText = new JLabel();

	public JTextPane problemText = new JTextPane();
	public JTextPane solutionText = new JTextPane();
	public JTextPane additionalText = new JTextPane();

	public JScrollPane scrollPaneProblem;
	public JScrollPane scrollPaneAdditional;
	public JScrollPane scrollPaneSolution;

	public JPanel panelRelated;

	public JButton button1;
	public JButton button2;
	public JButton button3;

	/*************
	 * VARIABLES *
	 *************/

	/***********
	 * HELPERS *
	 ***********/

	private UIElements uiLabel = new UIElements();

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new HelpPanel();
			}
		});
	}

	/***************
	 * CONSTRUCTOR *
	 **************/

	public HelpPanel() {
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(new MigLayout("", "[527.00px,grow][center]", "[14px][][230px,grow][230.00,grow][125px,grow]"));

		// Setup the title.
		{
			titleText = uiLabel.labelTitleFont("Title");
			add(titleText, "cell 0 0,growx,aligny top");

			JSeparator separator = new JSeparator();
			add(separator, "cell 0 1,grow");
		}

		// Setup the problem text segment.
		{
			problemText = uiLabel.textPaneText("No problem found.");
			scrollPaneProblem = new JScrollPane(problemText);
			scrollPaneProblem.setBorder(new TitledBorder(" Problem "));
			add(scrollPaneProblem, "cell 0 2,grow");
		}

		// Setup the solution text segment.
		{
			solutionText = uiLabel.textPaneText("No solution found.");
			scrollPaneSolution = new JScrollPane(solutionText);
			scrollPaneSolution.setBorder(new TitledBorder(" Solution "));
			add(scrollPaneSolution, "cell 0 3,grow");
		}

		// Setup the additional text segment.
		{
			additionalText = uiLabel.textPaneText("No additional information found.");
			scrollPaneAdditional = new JScrollPane(additionalText);
			scrollPaneAdditional.setBorder(new TitledBorder(" Additional Information "));
			add(scrollPaneAdditional, "cell 0 4,grow");
		}

		// Setup the related buttons segment.
		{
			panelRelated = new JPanel();
			panelRelated.setBorder(new TitledBorder(" Related Articles "));
			add(panelRelated, "cell 1 0 1 5,alignx center,growy");
			panelRelated.setLayout(new FormLayout(new ColumnSpec[] { FormSpecs.DEFAULT_COLSPEC, },
					new RowSpec[] { FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC,
							FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC,
							FormSpecs.DEFAULT_ROWSPEC, }));

			// The first button is set as such as the default.
			// The other buttons are only set visible when they are used.
			button1 = new JButton("No related articles.");
			button2 = new JButton("button2");
			button3 = new JButton("button3");

			button1.setEnabled(false);
			button2.setVisible(false);
			button3.setVisible(false);

			panelRelated.add(button1, "1, 2, fill, default");
			panelRelated.add(button2, "1, 4, fill, default");
			panelRelated.add(button3, "1, 6, fill, default");
		}
	}

	/***********
	 * METHODS *
	 **********/

	/*************
	 * LISTENERS *
	 ************/

	/***********
	 * GET/SET *
	 **********/
}
