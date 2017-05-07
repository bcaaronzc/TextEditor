import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class TextEditor extends JFrame implements ActionListener {
	JPanel jp;// 窗体主面板
	JTextArea jta;// 文本域
	JScrollPane jsp;// 文本域的滚动条
	JMenuBar jmb;// 菜单栏组件
	JMenu file, edit, view;// 菜单
	File currentFile = null;// 当前打开的文件
	//boolean wasSaved = true;// 当前文件是否已保存
	boolean wasSaved = false;// 当前文件是否已保存
	
	String oldValue;

	public TextEditor() {
		super("My Text Editor v1.0");
		jp = new JPanel();
		jmb = new JMenuBar();
		jta = new JTextArea(50, 50);
		jta.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent arg0) {
				wasSaved = false;
			}

			public void insertUpdate(DocumentEvent arg0) {
				wasSaved = false;
			}

			public void removeUpdate(DocumentEvent arg0) {
				wasSaved = false;
			}

		});

		jsp = new JScrollPane(jta);
		file = new JMenu("File");
		JMenuItem item;
		file.add(item = new JMenuItem("New"));
		item.addActionListener(this);
		file.add(item = new JMenuItem("Open..."));
		item.addActionListener(this);
		file.addSeparator();
		file.add(item = new JMenuItem("Save"));
		item.addActionListener(this);
		file.add(item = new JMenuItem("Save As..."));
		item.addActionListener(this);
		file.addSeparator();
		file.add(item = new JMenuItem("Quit"));
		item.addActionListener(this);

		edit = new JMenu("Edit");
		edit.add(item = new JMenuItem("Find..."));
		item.addActionListener(this);
		edit.add(item = new JMenuItem("Replace..."));
		item.addActionListener(this);

		view = new JMenu("View");
		view.add(item = new JMenuItem("Font..."));
		item.addActionListener(this);
		view.add(item = new JMenuItem("Color..."));
		item.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if (command.equals("Open...")) {
			// open...菜单处理
			openFile();
			return;
		}
		if (command.equals("Color...")) {
			// Color 菜单处理
		 
		}
		if (command.equals("Quit")) {// Quit
			if (wasSaved){
				
			}
			else {
				
			}
		}
		if (command.equals("Save As...")) {
			 
		}
		if (command.equals("Save")) {
			saveFile(currentFile);
		}
	}

	private void saveFile(File f) {
		System.out.println(f.getName());
		
		// Save Dialog
		JButton saveButton, noSaveButton, cancelButton;
		//
		
		try {
			FileWriter fw = new FileWriter(f);
			BufferedWriter bfw = new BufferedWriter(fw);
			oldValue = jta.getText();
			bfw.write(jta.getText(), 0, jta.getText().length());
			bfw.flush();
			bfw.close();
			wasSaved = true;
		} catch(Exception e){
	    	e.printStackTrace();
	    }
	}

	private void openFile() {
		// TODO When opening a file, should also check if the current file needed to be saved
		String str=null;
		JFileChooser fileChooser=new JFileChooser();  
	    fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);  
	    //fileChooser.setApproveButtonText("确定");  
	    fileChooser.setDialogTitle("打开文件");  
	    int result=fileChooser.showOpenDialog(this);  
	    if(result==JFileChooser.CANCEL_OPTION)  
	    {   
	    	System.out.println("您没有选择任何文件");  
	        return;  
	    }  
	    //File fileName=fileChooser.getSelectedFile();
	    currentFile=fileChooser.getSelectedFile(); 
	    if(currentFile==null || currentFile.getName().equals("")) {   
	    	JOptionPane.showMessageDialog(this,"不合法的文件名","不合法的文件名",JOptionPane.ERROR_MESSAGE);  
	    }  
	    else {
	     	System.out.println("文件名\t" + currentFile.getName());
	       	System.out.println("文件大小\t" + currentFile.length());
	       	
	       	try {
	       		FileReader fr=new FileReader(currentFile);  
	            BufferedReader bfr=new BufferedReader(fr);
	            jta.setText("");
	            while((str=bfr.readLine())!=null) {   
	            	//System.out.println(str);
	            	jta.append(str + "\n");
	            	// For TODO oldValue = oldValue + str;
	            }
	            System.out.println(currentFile.getAbsoluteFile() );
	            fr.close();  
	        }  
	        catch (IOException e) {  
	        	e.printStackTrace();
	        }
	    }
	}

	private int askSave() {
		return 0;
	}

	public void init() {
		jmb.add(file);
		jmb.add(edit);
		jmb.add(view);
		this.setJMenuBar(jmb);
		this.add(jsp, BorderLayout.CENTER);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent arg0) {
				super.windowClosing(arg0);
				
			}			 
		});
		setSize(800, 600);
		jta.setFont(new Font("宋体", Font.PLAIN, 18));
		setVisible(true);
	}
	
	public void exitWindow(){
		jta.requestFocus();
		String currentValue = jta.getText();
		
		if (currentValue.equals(oldValue)){
			System.exit(0);
		}
		else{
			AskSaveDialog askSaveDialog = new AskSaveDialog();
			int returnedData = askSaveDialog.returnData();
			if (returnedData == 1){
				saveFile(currentFile);
			}
			else if (returnedData == 2){
				System.exit(0);
			}
		}
	}

	
	public static void main(String args[]){
		TextEditor textEditor = new TextEditor();
		textEditor.init();
	}
}

class AskSaveDialog extends JDialog implements ActionListener{
	// TODO Add a JDialog to ask if the user want to save
	JButton saveButton, noSaveButton, cancelButton;
	int saveData = 0;
	
	public AskSaveDialog(){
		this.setBounds(200, 200, 200, 200);
		this.setTitle("Save file?");
		
		JPanel labelPanel = new JPanel();
		JLabel saveLabel = new JLabel("Do you want to save this file?");
		labelPanel.add(saveLabel);
		this.add(labelPanel, BorderLayout.CENTER);
		
		JPanel buttonPanel = new JPanel();
		saveButton = new JButton("Save");
		noSaveButton = new JButton("Don't save");
		cancelButton = new JButton("Cancel");
		saveButton.addActionListener(this);
		noSaveButton.addActionListener(this);
		cancelButton.addActionListener(this);
		buttonPanel.add(saveButton);
		buttonPanel.add(noSaveButton);
		buttonPanel.add(cancelButton);
		this.add(buttonPanel, BorderLayout.SOUTH);
		
		this.setVisible(true);
	}
	
	public int returnData(){
		return saveData;
	}
	
	public void actionPerformed(ActionEvent e){
		String command = e.getActionCommand();
		if (command == "Save"){
			saveData = 1;
			this.dispose();
		}
		if (command == "Don't save"){
			saveData = 2;
			this.dispose();
		}
		if (command == "Cancel"){
			saveData = 3;
			this.dispose();
		}
	}
}