package content;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.List;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class Controller implements Initializable {
	
	@FXML
	private Label labelTitel;
	@FXML
	private Label labelJahr;
	@FXML
	private Label labelStory;
	
	@FXML
	private Label labelTitelInhalt;
	@FXML
	private Label labelJahrInhalt;
	@FXML
	private Label labelStoryInhalt;
	
	
	
	
	@SuppressWarnings("rawtypes")
	@FXML
	private ListView<String> entityListe;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {		
		try {
			Connection verbindung = connect();
			Statement sqlStatement = verbindung.createStatement();
			ResultSet rs;
			rs = sqlStatement.executeQuery("Select name from entity");
			fillEntityListe(rs);
			
			setLabels(verbindung,4);
					
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		System.out.println("alles geladen");
	}
	
	private void setLabels(Connection con, Integer lang) {
		List labelBenennung = new List();
		try {
			ResultSet rs;
			Statement sta = con.createStatement();
			rs = sta.executeQuery("Select name from translation where languageID = " + lang);
			
			while(rs.next()){
				labelBenennung.add(rs.getString("name"));
			}			
			labelTitel.setText(labelBenennung.getItem(0));
			labelJahr.setText(labelBenennung.getItem(1));
			labelStory.setText(labelBenennung.getItem(2));			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static Connection connect(){
		Connection con = null;
		try {
			Class.forName("org.sqlite.JDBC");
			con = DriverManager.getConnection("jdbc:sqlite:DefaultDB.db");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		return con;	    
	}
	
	public void fillEntityListe(ResultSet resultS){
		try {
			while(resultS.next()){
				entityListe.getItems().add(resultS.getString("name"));				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}			
	}
	
	public void beispielRoutineDieVerknuepftWerdenKann(){		
		List x = new List();
				
		int index = entityListe.getSelectionModel().getSelectedIndex();		
		System.out.println(index);
		
		
	}	
}
