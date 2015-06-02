package controller;


import dao.CartDAO;
import flickr.Constants;
import model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.json.*;

/**
 * Created by fish on 15/5/26.
 */
@Controller
@SessionAttributes({"cart","products"})
@RequestMapping("/list")
public class ProductsController {

    private List<CartItemJSON> cartData;


    @ModelAttribute("cart")
    public Cart createCart()
    {
        return new Cart();
    }

    @ModelAttribute("products")
    public ArrayList<Product> createProducts()
    {
        return new ArrayList<Product>();
    }


    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView a(Model model, HttpSession session)
    {
        if(session.getAttribute("userId") == null){
            return new ModelAndView("redirect:/");
        }

        model.addAttribute("isSuccess", 0);
        return new ModelAndView("DisplayProducts");
    }


    @RequestMapping("category/{tag}")
    public String searchProductByTag(@PathVariable String tag, Model model,@ModelAttribute("products") ArrayList products) {
        System.out.println("$$$$$$$");

        List<Product> productList = call(tag);
        products.clear();
        products.addAll(productList);

        model.addAttribute("productList",productList);



//        return "DisplayProducts";
        return "Category_Partial";
    }

    @RequestMapping("add/{productId}")
    public String add(@PathVariable String productId, @ModelAttribute("cart") Cart cart, @ModelAttribute("products") ArrayList<Product>products) {

        if(productId.contains("}"))
        {
             productId=productId.substring(0,productId.length()-1);
        }

        long idLong=Long.parseLong(productId);

        System.out.println("Add Function");
        System.out.println("The product is : "+products);


        for(Product p:products)
        {
            long id=p.getProductId();
            if(id==idLong)
            {

                cart.addItem(p);
                //System.out.println(p + " will be added to cart");
            }
            //System.out.print("Cart : "+cart.getItems());
        }


        return "Cart_Partial";
    }

    @RequestMapping("remove/{productId}")
    public String remove(@PathVariable String productId, @ModelAttribute("cart") Cart cart, @ModelAttribute("products") ArrayList<Product>products) {

        if(productId.contains("}"))
        {
            productId=productId.substring(0,productId.length()-1);
        }


        //System.out.println("The processed is " + productId);

        long idLong=Long.parseLong(productId);
        for(Product p:products)
        {
            long id=p.getProductId();
            if(id==idLong)
            {

                cart.removeItem(p);
                System.out.println(p + " will be added to cart");
            }
            System.out.print("Cart : "+cart.getItems());
        }


        return "Cart_Partial";
    }



    // set product attributes from flickr API
    public  List<Product> call(String tag) {
        try {

            List<Product> result=new ArrayList<Product>();
            String callUrlStr = Constants.REST_ENDPOINT + "?method=" + Constants.METHOD +
                    "&tags="+tag+"&extras=tags" + "&format=rest" + "&per_page=" + Constants.DEFAULT_NUMBER + "&api_key=" + Constants.API_KEY;

            // print the URL of the tag being searched
            System.out.println("The URL of the tag searched:" + callUrlStr);

            URL callUrl = new URL(callUrlStr);
            HttpURLConnection urlConnection = (HttpURLConnection) callUrl.openConnection();
            InputStream urlStream = urlConnection.getInputStream();


            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document response = db.parse(urlStream);


            NodeList nl = response.getElementsByTagName("photo");

            for(int i=0;i<nl.getLength();i++)
            {
                Product product=new Product();
                NamedNodeMap map= nl.item(i).getAttributes();

                // set product attributes
                product.setProductId(Long.parseLong( map.getNamedItem("id").getTextContent()));
                product.setTitle(map.getNamedItem("title").getTextContent());
                product.setTag(map.getNamedItem("tags").getTextContent());

                String[] tags = product.getTag().split(" ");
                double length = tags.length;
                product.setPrice(2+length*1);

                product.setDescription(map.getNamedItem("tags").getTextContent());
                product.setSecret(map.getNamedItem("secret").getTextContent());
                product.setServer(Integer.parseInt(map.getNamedItem("server").getTextContent()));
                product.setFarm(Integer.parseInt(map.getNamedItem("farm").getTextContent()));

                product.setImageUrl("http://farm"+product.getFarm()+".staticflickr.com/"+product.getServer()+"/"+product.getProductId()+"_"+
                        product.getSecret()+".jpg");


                // print product information for every product queried
                //System.out.println("Product is : "+product);
                result.add(product);

            }


            urlConnection.disconnect();
            return result;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }


    }

   /* @RequestMapping("checkout/{address}")
    public String checkout(Model model, @PathVariable("address") String address, @ModelAttribute("cart") Cart cart, @ModelAttribute("products") ArrayList<Product>products) {

        ArrayList<OrderAndCart> orderAndCartItems = new ArrayList<>();

        if(cart!=null) {

            cartData = new ArrayList<CartItemJSON>();


            int quantity = 0;
            int orderId = 0;
            int total = 0;
            double totalCost = 0;
            boolean isSuccess = false;

            String titles = "";
            String quantities = "";
            String prices = "";


            orderId = CartDAO.getInstance().addOrder(address, cart, totalCost);

            isSuccess = CartDAO.getInstance().addCart(cart, orderId);

            if(isSuccess == true) {
                model.addAttribute("isSuccess", 1);
            }


        }
        return "Cart_Partial";
    }*/

    @RequestMapping("checkout/{address}/{orderId}")
    public String checkout(Model model, @PathVariable("address") String address, @PathVariable("orderId") int passedOrderId, @ModelAttribute("cart") Cart cart, @ModelAttribute("products") ArrayList<Product>products, HttpSession session) {

        ArrayList<OrderAndCart> orderAndCartItems = new ArrayList<>();

        if(cart!=null) {

            cartData = new ArrayList<CartItemJSON>();


            int quantity = 0;
            int orderId = 0;
            double totalCost = 0;
            double finalTotal = 0;
            boolean isSuccess = false;

            String titles = "";
            String quantities = "";
            String prices = "";
            int j=0;


            for(CartItem cartItem1 : cart.getItems()) {
                CartItemJSON c=new CartItemJSON(cartItem1.getProduct().getTitle(),cartItem1.getQuantity(),cartItem1.getProduct().getPrice());
                cartData.add(c);

                quantity = quantity + c.getQuantity();
                totalCost = totalCost + cartItem1.getProduct().getPrice();

                String titleToProcess = cartItem1.getProduct().getTitle();
                String[] tempTitles = titleToProcess.split("\\s+");
                String formattedTitles = "";

                for(int k=0; k<tempTitles.length;k++) {
                    if(k==0) {
                        formattedTitles = tempTitles[k];
                    } else {
                        formattedTitles = formattedTitles + "___" +tempTitles[k];
                    }
                }

                if(j==0) {
                    //prepare titles
                    titles = formattedTitles;
                    //prepare quantities
                    quantities = Integer.toString(cartItem1.getQuantity());
                    //prepare prices
                    prices = Double.toString(cartItem1.getProduct().getPrice());
                } else {
                    //prepare titles
                    titles = titles+"||"+formattedTitles;
                    //prepare quantities
                    quantities = quantities+","+Integer.toString(cartItem1.getQuantity());
                    //prepare prices
                    prices = prices+","+Double.toString(cartItem1.getProduct().getPrice());
                }
                j++;
                System.out.println("titles"+titles);
                System.out.println("quantities"+quantities);
                System.out.println("prices"+prices);
                ///Calling the shipping component
                String url = "http://localhost:9000/shippingModule/service/shipping?city="+address+"&quantity="+quantity+"&titles="+titles+"&quantities="+quantities+"&prices="+prices;
                String shippingmessage ="";
                int shippingCost = -1;
                System.out.println("url "+url);
                URL callUrl;
                try {
                    callUrl = new URL(url);
                    HttpURLConnection urlConnection;
                    urlConnection = (HttpURLConnection) callUrl.openConnection();
                    InputStream urlStream = urlConnection.getInputStream();
                    System.out.println(urlConnection.getResponseCode());
                    if (urlConnection.getResponseCode() != 200) {
                        throw new RuntimeException("Failed : HTTP error code : "
                                + urlConnection.getResponseCode());
                    }
                    BufferedReader br = new BufferedReader(new InputStreamReader(urlStream));

                    String output;
                    String returnXML = "";
                    while((output = br.readLine()) != null)
                    {
                        returnXML+=output;
                    }

                    try
                    {
                        JSONObject json = XML.toJSONObject(returnXML);
                        JSONObject root = (JSONObject) json.get("shipping");
                        //get the message
                        shippingmessage = root.getString("msg");
                        //get shipping cost
                        shippingCost = root.getInt("shippingPrice");
                        System.out.println("message is: "+shippingmessage+" and shipping price is "+shippingCost);
                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                }
                catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                finalTotal = totalCost + shippingCost;
                if((shippingCost != -1) && (shippingmessage.equals("Destination is valid")))
                {
                    System.out.println("Order state: receiving order");
                    int id = Integer.parseInt((String) session.getAttribute("userId"));
                    model.addAttribute("shippingCost", shippingCost);
                    model.addAttribute("finalTotal", finalTotal);
                    //insert*/
                    orderId = CartDAO.getInstance().addOrder(address, cart, shippingCost, finalTotal, id);
                    isSuccess = CartDAO.getInstance().addCart(cart, orderId);
                    System.out.println("Order state: finishing cost computation");
                } else
                {
                    System.out.println("You have entered invalid destination");
                }

            }

            if(isSuccess == true) {
                model.addAttribute("isSuccess", 1);
            }
        }


        return "Cart_Partial";
    }




//    public static void main(String argv[]){
//
//        System.setProperty("http.proxyHost", "www-cache.usyd.edu.au");
//        System.setProperty("http.proxyPort","8080");
//
//        ProductsController pq = new ProductsController();
//        pq.call();
//    }



}
