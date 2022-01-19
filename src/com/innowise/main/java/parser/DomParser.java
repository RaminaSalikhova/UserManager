package com.innowise.main.java.parser;

import com.innowise.main.java.user.User;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.*;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class DomParser {

    private DomParser(){}

    private static Document getDocument(File xmlFile) {
        Document document = null;
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            document = documentBuilder.parse(xmlFile);
        } catch (SAXException | ParserConfigurationException | IOException e) {
            e.printStackTrace();
        }
        return document;
    }

    public static List<User> parse(File xmlFile) {
        List<User> users = new ArrayList<>();

        Document document = getDocument(xmlFile);

        NodeList nodeList = document.getElementsByTagName("User");
        if (nodeList.getLength() == 0) {
            return users;

        }
        for (int i = 0; i <= nodeList.getLength() - 1; i++) {

            Node node = nodeList.item(i);
            Element element = (Element) node;

            Long id= Long.valueOf(element.getAttribute("id"));
            String name = getElementText(element, "name");
            String lastname = getElementText(element, "lastname");
            String email = getElementText(element, "email");
            NodeList roles = element.getElementsByTagName("role");
            List<String> rolesList = new ArrayList<>();
            for (int j = 0; j < roles.getLength(); j++) {
                rolesList.add(element.getElementsByTagName("role").item(j).getTextContent());
            }
            NodeList numbers = element.getElementsByTagName("number");
            List<String> numbersList = new ArrayList<>();
            for (int j = 0; j < numbers.getLength(); j++) {
                numbersList.add(element.getElementsByTagName("number").item(j).getTextContent());
            }

            User user = new User(id, name, lastname, email, rolesList, numbersList);

            users.add(user);

        }
        return users;
    }

    private static String getElementText(Element element, String tagName) {
        return element.getElementsByTagName(tagName).item(0).getTextContent();
    }

    public static void addElement(File xmlFile, User newUser) {
        try {
            Document document = getDocument(xmlFile);

            Node root = document.getFirstChild();
            NodeList nodeList = document.getElementsByTagName("User");


            XPathFactory xpf = XPathFactory.newInstance();
            XPath xpath = xpf.newXPath();
            XPathExpression expression = xpath.compile("//User[last()]");
            Node prevNode = (Node) expression.evaluate(document, XPathConstants.NODE);

            Element user = document.createElement("User");
            root.appendChild(user);
            Attr attr = document.createAttribute("id");
            Node prevId=prevNode.getAttributes().getNamedItem("id");
            attr.setValue(String.valueOf(Integer.parseInt(prevId.getTextContent())+1));
            user.setAttributeNode(attr);

            Element name = document.createElement("name");
            name.appendChild(document.createTextNode(newUser.getName()));
            user.appendChild(name);

            Element lastname = document.createElement("lastname");
            lastname.appendChild(document.createTextNode(newUser.getLastname()));
            user.appendChild(lastname);

            Element email = document.createElement("email");
            email.appendChild(document.createTextNode(newUser.getEmail()));
            user.appendChild(email);

            Element roles = document.createElement("roles");
            for (String el : newUser.getRoles()) {
                Element role = document.createElement("role");
                role.appendChild(document.createTextNode(el));
                roles.appendChild(role);
            }
            user.appendChild(roles);

            Element numbers = document.createElement("numbers");
            for (String el : newUser.getNumbers()) {
                Element number = document.createElement("number");
                number.appendChild(document.createTextNode(el));
                numbers.appendChild(number);
            }
            user.appendChild(numbers);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(String.valueOf(xmlFile)));

            transformer.transform(domSource, streamResult);

        } catch (TransformerException exception) {
            exception.printStackTrace();
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
    }

    public static void editElement(File xmlFile, User newUser, Long id) {
        try {
            Document document = getDocument(xmlFile);

            XPathFactory xpf = XPathFactory.newInstance();
            XPath xpath = xpf.newXPath();
            XPathExpression expression = xpath.compile("//User[@id=" + id + "]");

            Node searchedNode = (Node) expression.evaluate(document, XPathConstants.NODE);
            NodeList list = searchedNode.getChildNodes();
            for (int i = 0; i < list.getLength(); i++) {
                switch (list.item(i).getNodeName()) {
                    case "name" -> list.item(i).setTextContent(newUser.getName());
                    case "lastname" -> list.item(i).setTextContent(newUser.getLastname());
                    case "email" -> list.item(i).setTextContent(newUser.getEmail());
                    case "numbers" -> {
                        XPathExpression getAllNumbersExpression = xpath.compile("//User[@id=" + id + "]/numbers/*");

                        NodeList b13Node = (NodeList) getAllNumbersExpression.evaluate(document, XPathConstants.NODESET);
                        for (int j = 0; j < b13Node.getLength(); j++) {
                            b13Node.item(j).getParentNode().removeChild(b13Node.item(j));
                        }

                        XPathExpression getNumbersExpression = xpath.compile("//User[@id=" + id + "]/numbers");

                        Node numbers = (Node) getNumbersExpression.evaluate(document, XPathConstants.NODE);
                        for (String el : newUser.getNumbers()) {
                            Element number = document.createElement("number");
                            number.appendChild(document.createTextNode(el));
                            numbers.appendChild(number);
                        }

                        break;
                    }
                    case "roles" -> {
                        XPathExpression getAllRolesExpression = xpath.compile("//User[@id=" + id + "]/roles/*");

                        NodeList b13Node = (NodeList) getAllRolesExpression.evaluate(document, XPathConstants.NODESET);
                        for (int j = 0; j < b13Node.getLength(); j++) {
                            b13Node.item(j).getParentNode().removeChild(b13Node.item(j));
                        }

                        XPathExpression getRolesExpression = xpath.compile("//User[@id=" + id + "]/roles");

                        Node roles = (Node) getRolesExpression.evaluate(document, XPathConstants.NODE);
                        for (String el : newUser.getNumbers()) {
                            Element number = document.createElement("role");
                            number.appendChild(document.createTextNode(el));
                            roles.appendChild(number);
                        }
                        break;
                    }
                }
            }
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(String.valueOf(xmlFile)));

            transformer.transform(domSource, streamResult);

        } catch (XPathExpressionException | TransformerException
                exception) {
            exception.printStackTrace();
        }
    }

    public static void deleteElement(File xmlFile, Long id) {
        try {
            Document document = getDocument(xmlFile);

            XPathFactory xpf = XPathFactory.newInstance();
            XPath xpath = xpf.newXPath();
            XPathExpression expression = xpath.compile("//User[@id=" + id + "]");

            Node b13Node = (Node) expression.evaluate(document, XPathConstants.NODE);
            b13Node.getParentNode().removeChild(b13Node);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(new File(String.valueOf(xmlFile)));

            transformer.transform(domSource, streamResult);

        } catch (TransformerException | XPathExpressionException exception) {
            exception.printStackTrace();
        }
    }

    public static List<User> findUserByName(File xmlFile, String name) {
        List<User> users = new ArrayList<>();
        Document document = getDocument(xmlFile);

        XPathFactory xpf = XPathFactory.newInstance();
        XPath xpath = xpf.newXPath();
        XPathExpression expression;
        try {
            expression = xpath.compile("//User[./name[ contains(.,'" + name + "')]]");
            Optional<NodeList> nodeList = Optional.ofNullable((NodeList) expression.evaluate(document, XPathConstants.NODESET));
            if (nodeList.isPresent()) {
                for (int i = 0; i < nodeList.get().getLength(); i++) {
                    Node node = nodeList.get().item(i);
                    Element element = (Element) node;
                    String searchedname = getElementText(element, "name");
                    String lastname = getElementText(element, "lastname");
                    String email = getElementText(element, "email");
                    NodeList roles = element.getElementsByTagName("role");
                    List<String> rolesList = new ArrayList<>();
                    for (int j = 0; j < roles.getLength(); j++) {
                        rolesList.add(element.getElementsByTagName("role").item(j).getTextContent());
                    }
                    NodeList numbers = element.getElementsByTagName("number");
                    List<String> numbersList = new ArrayList<>();
                    for (int j = 0; j < numbers.getLength(); j++) {
                        numbersList.add(element.getElementsByTagName("number").item(j).getTextContent());
                    }

                    User user = new User(searchedname, lastname, email, rolesList, numbersList);

                    users.add(user);
                }
            }
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }

        return users;
    }
}
