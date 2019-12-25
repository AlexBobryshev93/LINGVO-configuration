import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Settings implements ActionListener {
	private int quantity = 5;
	private int difficulty = 5;
	private String preset = "--User defined--";
	
	JLabel num, diff, pres, msg;
	JComboBox<Integer> nList;
	JComboBox<String> dList;
	JComboBox<String> pList;
	JButton btn;
	
	public Settings() {
		List<String> presetList = new ArrayList<>(); // presets will be added to this list
		presetList.add("--User defined--");
		
		//Reading the presets list
		try (BufferedReader fin = new BufferedReader(new FileReader("Presets/Presets list.txt"))) {
			String str;
			
			for (;;) {
				str = fin.readLine();
				//if (str.trim().equals("")) continue;
				if (str == null) break;
				presetList.add(str);
			}
		}
		catch (IOException exc) {
			presetList.clear();
			presetList.add("--User defined--");
		}
		
		JFrame jfrm = new JFrame("LINGVO Configuration");
		jfrm.setLayout(new FlowLayout());
		jfrm.setSize(370, 250);
		jfrm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		num = new JLabel("Number of questions", SwingConstants.RIGHT);
		num.setPreferredSize(new Dimension(150,40));
		num.setFont(new Font("Arial", Font.PLAIN, 14));
		diff = new JLabel("Difficulty level", SwingConstants.RIGHT);
		diff.setPreferredSize(new Dimension(150,40));
		diff.setFont(new Font("Arial", Font.PLAIN, 14));
		pres = new JLabel("Preset", SwingConstants.RIGHT);
		pres.setPreferredSize(new Dimension(150,40));
		pres.setFont(new Font("Arial", Font.PLAIN, 14));
		msg = new JLabel("", SwingConstants.CENTER);
		msg.setPreferredSize(new Dimension(300,40));
		msg.setFont(new Font("Arial", Font.PLAIN, 14));
		
		Integer[] nItems = {5, 10, 15};
		nList = new JComboBox(nItems);
		String[] dItems = {"EASY", "NORMAL", "HARD"};
		dList = new JComboBox(dItems);
		String[] pItems = presetList.toArray(new String[presetList.size()]); 
		pList = new JComboBox(pItems);
		
		ActionListener actionListenerN = new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                JComboBox box = (JComboBox) ae.getSource();
                Integer item = (Integer) box.getSelectedItem();
                quantity = item;
				msg.setText("");
            }
        };
		nList.addActionListener(actionListenerN);
		
		ActionListener actionListenerD = new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                JComboBox box = (JComboBox) ae.getSource();
                String item = (String) box.getSelectedItem();
                if (item.equals("EASY")) difficulty = 4;
				else if (item.equals("HARD")) difficulty = 6;
				else difficulty = 5;
				msg.setText("");
            }
        };
		dList.addActionListener(actionListenerD);
		
		ActionListener actionListenerP = new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
				JComboBox box = (JComboBox) ae.getSource();
				String item = (String) box.getSelectedItem();
				preset = item;
				msg.setText("");
            }
        };
		pList.addActionListener(actionListenerP);
		
		btn = new JButton("Save changes");
		btn.setPreferredSize(new Dimension(200,30));
		btn.addActionListener(this);

		jfrm.add(num);
		jfrm.add(nList);
		jfrm.add(diff);
		jfrm.add(dList);
		jfrm.add(pres);
		jfrm.add(pList);
		jfrm.add(btn);
		jfrm.add(msg);
		jfrm.setResizable(false);
		jfrm.setVisible(true); 
	}
	
	public void actionPerformed(ActionEvent ae) {
		try (DataOutputStream dout = new DataOutputStream(new FileOutputStream("AppData/data1.dat")); 
												BufferedWriter fout = new BufferedWriter(new FileWriter("AppData/data2.dat"))) {
			dout.writeInt(quantity);
			dout.writeInt(difficulty);
			fout.write(preset);
			msg.setText("All changes successfully saved");
		}
		catch (IOException exc) {
			msg.setText("Save error!");
			return;
		}	
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new Settings();
			}
		});
	}
}