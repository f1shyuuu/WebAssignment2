package dao;

import model.*;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Arun on 29-05-2015.
 */
public class CartDAO {

    private DataSource dataSource;
    private static CartDAO sd = null;

    public static CartDAO getInstance() {
        if (sd == null) {
            try {
                sd = new CartDAO();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sd;
    }

    private CartDAO() throws Exception {
        try {
            Context iniCtx = new InitialContext();
            Context env = (Context) iniCtx.lookup("java:comp/env");
            dataSource = (DataSource) env.lookup("jdbc/assignment2");
        } catch (NamingException e) {
            throw new Exception("cannot find database");
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public int addOrder(String address, Cart cart, double totalCost) {

        Integer generatedId = 0;
        String sql = "INSERT INTO `ORDER` (`userId`, `destination`, `shippingFee`, `finalCost`) VALUES (?,?,?,?)";
        Connection conn = null;


        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, 1);
            ps.setString(2, address);
            ps.setString(3, Double.toString(totalCost));
            ps.setString(4, Double.toString(cart.getTotal()));
            ps.executeUpdate();

            ResultSet keys = ps.getGeneratedKeys();

            if (keys.next()) {
                generatedId = keys.getInt(1); //id returned after insert execution
            }
            ps.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);

        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {}
            }
        }

        return generatedId;
    }


    public boolean addCart(Cart cart, int orderId) {
        String sql = "INSERT INTO `cart` (`orderId`, `title`, `quantity`, `price`) VALUES (?,?,?,?)";
        Connection conn = null;

        for(CartItem cartItem : cart.getItems()) {
            try {
                conn = dataSource.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, orderId);
                ps.setString(2, cartItem.getProduct().getTitle());
                ps.setInt(3, cartItem.getQuantity());
                ps.setDouble(4, cartItem.getProduct().getPrice());
                ps.executeUpdate();
                ps.close();

            } catch (SQLException e) {
                throw new RuntimeException(e);

            } finally {
                if (conn != null) {
                    try {
                        conn.close();
                    } catch (SQLException e) {}
                }
            }
        }
        return true;
    }

    public void getCart(int userId) {
        String sql = "SELECT * FROM `order` LEFT JOIN `cart` ON order.orderId = cart.orderId WHERE userId = ?";
        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.executeQuery();

            ResultSet rs = ps.executeQuery();

            Cart userCart = new Cart();
            Map<Long, CartItem> carts = new HashMap<Long, CartItem>();

            while (rs.next()) {

                CartItem cartitem = new CartItem();
                Product orderedProduct = new Product();

                orderedProduct.setPrice(rs.getInt("price"));
                orderedProduct.setTitle(rs.getString("title"));
                cartitem.setProduct(orderedProduct);
                cartitem.setQuantity(rs.getInt("quantity"));

                Order userOrder = new Order(rs.getInt("orderId"), rs.getString("userId"), rs.getString("destination"), rs.getString("status"), rs.getInt("shippingFee"), rs.getInt("finalCost"));


            }


            ps.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);

        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {}
            }
        }


    }


}