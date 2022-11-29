package utility;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import model.PortfolioImpl;

/**
 * This class implements the workwithfiletypes interface to work with xml file types.
 */
public class WorkWithXML implements WorkWithFileTypes {

  private final String path;

  private final String name;

  /**
   * This constructor initialises the file path and name.
   */
  public WorkWithXML(String path, String name) {
    this.path = path;
    this.name = name;
  }

  private static Document getDocument(String pathToFile) {
    Document d = null;
    try {
      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      DocumentBuilder db = dbf.newDocumentBuilder();
      d = db.parse(pathToFile);
    } catch (Exception ex) {
      d = null;
    }
    return d;
  }

  private static void saveXMLContent(Document d, String pathToFile) {
    try {
      TransformerFactory tff = TransformerFactory.newInstance();
      Transformer tf = tff.newTransformer();
      tf.setOutputProperty(OutputKeys.INDENT, "yes");
      DOMSource ds = new DOMSource(d);
      StreamResult sr = new StreamResult(pathToFile);
      tf.transform(ds, sr);
    } catch (Exception ex) {
      System.out.println(ex.getMessage());
    }
  }

  @Override
  public void create(ArrayList<HashMap<String, String>> stockData, String creationDate) {
    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

    try {
      DocumentBuilder builder = dbf.newDocumentBuilder();
      Document doc = builder.newDocument(); //created in temporary memory

      //create root node
      Element root = doc.createElement("Portfolio");
      root.setAttribute("creation-date", creationDate);


      Element name = doc.createElement("Portfolio-Name");
      Text nameText = doc.createTextNode(this.name);
      name.appendChild(nameText);

      root.appendChild(name);

      for (HashMap<String, String> i : stockData) {
        Element stockElement = doc.createElement("Stock");
        for (Map.Entry<String, String> j : i.entrySet()) {
          Element c = doc.createElement(j.getKey());
          Text textNode = doc.createTextNode(j.getValue());
          c.appendChild(textNode);
          stockElement.appendChild(c);
        }
        root.appendChild(stockElement);
      }
      doc.appendChild(root);
      File f = new File(this.path);
      saveXMLContent(doc, path);

    } catch (ParserConfigurationException ex) {
      Logger.getLogger(PortfolioImpl.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  @Override
  public String getFileCreationDate() {
    Document doc = getDocument(path);
    Element portfolio = doc.getDocumentElement();
    return portfolio.getAttribute("creation-date");
  }

  public String readDCAStart() {
    Document doc = getDocument(path);
    Element dca = doc.getDocumentElement();
    return dca.getElementsByTagName("Start").item(0).getTextContent();
  }

  public String readDCAEnd() {
    Document doc = getDocument(path);
    Element dca = doc.getDocumentElement();
    return dca.getElementsByTagName("End").item(0).getTextContent();
  }

  public String readDCAFreq() {
    Document doc = getDocument(path);
    Element dca = doc.getDocumentElement();
    return dca.getElementsByTagName("Frequency").item(0).getTextContent();
  }

  public String readFinished(){
    Document doc = getDocument(path);
    Element dca = doc.getDocumentElement();
    return dca.getElementsByTagName("Finished").item(0).getTextContent();
  }

  public String readLastUpdated() {
    Document doc = getDocument(path);
    Element dca = doc.getDocumentElement();
    return dca.getElementsByTagName("Last-updated").item(0).getTextContent();
  }

  public void setDCALastUpdated(String date) {
    Document doc = getDocument(path);
    Element dca = doc.getDocumentElement();
    dca.getElementsByTagName("Last-updated").item(0).setTextContent(date);
    saveXMLContent(doc, path);
  }

  @Override
  public HashMap<String, String> readDCA(){
    HashMap<String, String> proportions = new HashMap<>();

    try {
      Document doc = getDocument(path);
      Element root = doc.getDocumentElement();
      LocalDate endDate = LocalDate.parse(readDCAEnd());
      doc.getDocumentElement().normalize();
      NodeList props = doc.getElementsByTagName("Proportions");

      for (int temp = 0; temp < props.getLength(); temp++) {
        Node node = props.item(temp);
        if (node.getNodeType() == Node.ELEMENT_NODE) {
          Element element = (Element) node;
          NodeList nList = element.getElementsByTagName("*");

          for(int i = 0; i <nList.getLength(); i++){
            String stockName = nList.item(i).getNodeName();
            String amount = nList.item(i).getTextContent();
            proportions.put(stockName,amount);
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return proportions;
  }

  @Override
  public List<HashMap<String, String>> read() {
    List<HashMap<String, String>> stocks = new ArrayList<>();

    try {
      Document doc = getDocument(path);
      doc.getDocumentElement().normalize();
      NodeList list = doc.getElementsByTagName("Stock");

      for (int temp = 0; temp < list.getLength(); temp++) {
        Node node = list.item(temp);
        if (node.getNodeType() == Node.ELEMENT_NODE) {
          Element element = (Element) node;
          String ticker = element.getElementsByTagName("Stock-ticker").item(0).getTextContent();
          String numberOfShares = element.getElementsByTagName("Number-of-shares")
                  .item(0).getTextContent();
          String date = element.getElementsByTagName("Date").item(0).getTextContent();
          NodeList commission = element.getElementsByTagName("Commission");

          HashMap<String, String> stock = new HashMap<>();
          stock.put("Stock ticker", ticker);
          stock.put("Number of shares", numberOfShares);
          stock.put("Date of transaction", date);
          if (commission.getLength() > 0) {
            stock.put("Commission", element.getElementsByTagName("Commission")
                    .item(0).getTextContent());
          }
          stocks.add(stock);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return stocks;
  }

  @Override
  public boolean update(List<HashMap<String, String>> stocks) {
    try {
      Document d = getDocument(path);
      Element portfolio = d.getDocumentElement();

      for (HashMap<String, String> i : stocks) {
        Element stock = d.createElement("Stock");

        for (Map.Entry<String, String> j : i.entrySet()) {
          Element c = d.createElement(j.getKey());
          Text textNode = d.createTextNode(j.getValue());
          c.appendChild(textNode);
          stock.appendChild(c);
        }
        portfolio.appendChild(stock);
        saveXMLContent(d, path);
      }
    } catch (Exception ex) {
      return false;
    }
    return true;
  }

  public void setFinished(boolean finished) {
    Document doc = getDocument(path);
    Element dca = doc.getDocumentElement();
    dca.getElementsByTagName("Finished").item(0).setTextContent(String.valueOf(finished));
    saveXMLContent(doc, path);
  }

  @Override
  public void createDCAFile(int total, HashMap<String, Integer> proportions, String start,
                            String end, int days){
//    LocalDate dateObj = LocalDate.now();
//    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//    String date = dateObj.format(formatter);

    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

    try {
      DocumentBuilder builder = dbf.newDocumentBuilder();
      Document doc = builder.newDocument(); //created in temporary memory

      //create root node
      Element root = doc.createElement("DCA");
//      root.setAttribute("last-updated", "NA");
//      root.setAttribute("finished", "false");

      Element lu = doc.createElement("Last-updated");
      Text luText = doc.createTextNode("NA");
      lu.appendChild(luText);

      Element finished = doc.createElement("Finished");
      Text finishedText = doc.createTextNode("false");
      finished.appendChild(finishedText);

      Element name = doc.createElement("Total");
      Text nameText = doc.createTextNode(String.valueOf(total));
      name.appendChild(nameText);

      Element startEle = doc.createElement("Start"); //needs to be valid
      Text startText = doc.createTextNode(start);
      startEle.appendChild(startText);

      Element endEle = doc.createElement("End"); //needs to be later than start and valid
      Text endText = doc.createTextNode(end);
      endEle.appendChild(endText);

      Element freqEle = doc.createElement("Frequency");
      Text freqText = doc.createTextNode(String.valueOf(days));
      freqEle.appendChild(freqText);

      root.appendChild(finished);
      root.appendChild(lu);
      root.appendChild(name);
      root.appendChild(startEle);
      root.appendChild(endEle);
      root.appendChild(freqEle);

      Element proportionElem = doc.createElement("Proportions");

      for(Map.Entry<String, Integer> j : proportions.entrySet()){
        Element c = doc.createElement(j.getKey());
        Text textNode = doc.createTextNode(String.valueOf(((double)j.getValue()/100)*total));
//        System.out.println((double)(j.getValue())/100 +" "+(j.getValue()/100)*total);
        c.appendChild(textNode);
        proportionElem.appendChild(c);
      }

      root.appendChild(proportionElem);

      doc.appendChild(root);
      File f = new File(this.path);
      saveXMLContent(doc, path);

    } catch (ParserConfigurationException ex) {
      Logger.getLogger(PortfolioImpl.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
}
