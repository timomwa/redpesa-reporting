package ke.co.technovation.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="transactions")
public class Transactions {
	
	private String searchString;
	private List<MsisdnTransactionsDTO> transactions = new ArrayList<MsisdnTransactionsDTO>();

	@XmlElement(name="transaction")
	public List<MsisdnTransactionsDTO> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<MsisdnTransactionsDTO> transactions) {
		this.transactions = transactions;
	}

	@XmlElement(name="searchString")
	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}
	

	
}
