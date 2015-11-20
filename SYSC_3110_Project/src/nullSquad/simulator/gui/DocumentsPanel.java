package nullSquad.simulator.gui;


import java.awt.Component;
import java.awt.Dimension;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;

import nullSquad.filesharingsystem.document.Document;

/**
 * Representation of the Documents Tab Panel
 * 
 * @author MVezina
 */
public class DocumentsPanel extends JPanel implements ListCellRenderer<Document>
{

	/* 'Documents' tab content */
	private JList<Document> documentsJList;
	private DefaultListModel<Document> allDocumentsListModel;
	private JScrollPane documentListScrollPane;

	// Fields for the document stats panel
	private JPanel documentStatsListPanel;
	private JLabel documentStatsLabel;


	/**
	 * Creates the Documents panel and all associated components
	 * 
	 * @author MVezina
	 */
	public DocumentsPanel(DefaultListModel<Document> documentsListModel)
	{

		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.allDocumentsListModel = documentsListModel;

		// Create the document list scroll pane
		documentsJList = new JList<>(allDocumentsListModel);
		documentsJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		documentsJList.addListSelectionListener((ListSelectionEvent e) -> documentJList_SelectionChanged(e));
		
		// Set the document cell renderer
		documentsJList.setCellRenderer(this);
		
		// So that the JList doesn't auto-resize
		documentsJList.setVisibleRowCount(-1);
		documentsJList.setFixedCellWidth(100);
		
		// Set the scroll pane for the documents
		documentListScrollPane = new JScrollPane(documentsJList);
		documentListScrollPane.setMaximumSize(new Dimension(documentListScrollPane.getMaximumSize().width, Integer.MAX_VALUE));
		
		// Document Statistics Panel
		documentStatsListPanel = new JPanel();
		documentStatsListPanel.setBorder(BorderFactory.createTitledBorder("Document Stats"));
		documentStatsListPanel.setPreferredSize(new Dimension(100, Integer.MAX_VALUE));
		

		// Add the stats label to the info panel
		documentStatsLabel = new JLabel("No Document Selected!");
		documentStatsListPanel.add(documentStatsLabel);

		

		// Add the info panel to the document tab panel
		this.add(documentListScrollPane);
		this.add(documentStatsListPanel);

	}

	/**
	 * Event Method: Called when the index of the documentJList is changed
	 * 
	 * @author MVezina
	 */
	void documentJList_SelectionChanged(ListSelectionEvent lse)
	{
		// Check to see when the value is finished adjusting and if the source
		// is a list
		if (!lse.getValueIsAdjusting() && lse.getSource() == documentsJList)
		{
			// Get the selected index
			if (documentsJList.getSelectedIndex() < 0)
			{
				// No document is selected
				updateDocumentStats(null);
				return;
			}

			// Update the document stats
			updateDocumentStats(documentsJList.getSelectedValue());
		}
	}

	/**
	 * Updates the document stats shown in the documents tab
	 * 
	 * @param selectedDoc The document currently selected. (null = No document
	 *        is selected)
	 */
	private void updateDocumentStats(Document selectedDoc)
	{
		if (selectedDoc == null)
		{
			documentStatsLabel.setText("No Document Selected!");
			return;
		}

		// HTML code is used in the statsLabel for text formatting
		String docStats = "<html>";
		String newLine = "<br>";

		docStats += ("<b>ID</b>: " + selectedDoc.getDocumentID() + newLine);
		docStats += ("<b>Name</b>: " + selectedDoc.getDocumentName() + newLine);
		docStats += ("<b>Tag</b>: " + selectedDoc.getTag() + newLine);
		docStats += ("<b>Producer</b>: " + selectedDoc.getProducer().getUserName() + newLine);
		docStats += ("<b>Date Uploaded</b>: " + selectedDoc.getDateUploaded() + newLine);
		docStats += ("<b>Likes</b>: " + selectedDoc.getUserLikes().size() + newLine);

		// Set the label text (ending it with a closing HTML tag)
		documentStatsLabel.setText(docStats + "</html>");

	}

	@Override
	public Component getListCellRendererComponent(JList<? extends Document> list, Document value, int index, boolean isSelected, boolean cellHasFocus)
	{
		// Create a new label to represent a cell in the document list
		JLabel label = new JLabel("(ID: " + value.getDocumentID() + "): " + value.getDocumentName());
		label.setOpaque(true);
		
		// Set the background and foreground colors
		if(isSelected)
		{
			label.setBackground(list.getSelectionBackground());
			label.setForeground(list.getSelectionForeground());
		}
		else
		{
			label.setBackground(list.getBackground());
			label.setForeground(list.getForeground());
		}
		
		return label;
	}

}
