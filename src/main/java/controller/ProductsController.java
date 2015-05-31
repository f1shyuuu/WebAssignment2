package controller;


import flickr.Constants;
import model.Cart;
import model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fish on 15/5/26.
 */
@Controller
@SessionAttributes({"cart","products"})
@RequestMapping("/list")
public class ProductsController {




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
    public String a()
    {
        return "DisplayProducts";
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

//        System.out.println("hahahah");
//        System.out.println("product : " + products);
//        System.out.println("id : "+productId);

        if(productId.contains("}"))
        {
             productId=productId.substring(0,productId.length()-1);
        }


        //System.out.println("The processed is " + productId);

        long idLong=Long.parseLong(productId);

        System.out.println("Add is called");
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




//    public static void main(String argv[]){
//
//        System.setProperty("http.proxyHost", "www-cache.usyd.edu.au");
//        System.setProperty("http.proxyPort","8080");
//
//        ProductsController pq = new ProductsController();
//        pq.call();
//    }



}
