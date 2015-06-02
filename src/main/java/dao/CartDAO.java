package dao;

import model.*;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
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

    public int addOrder(String address, Cart cart, double shippingCost, double finalTotal, int userId) {

        Integer generatedId = 0;
        String sql = "INSERT INTO `ORDER` (`userId`, `destination`, `shippingFee`, `finalCost`) VALUES (?,?,?,?)";
        Connection conn = null;


        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, userId);
            ps.setString(2, address);
            ps.setString(3, Double.toString(shippingCost));
            ps.setString(4, Double.toString(finalTotal));
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

    public int editOrder(String address, Cart cart, double shippingCost, double finalTotal, int userId, int orderId) {

        Integer generatedId = 0;
        String sql = "UPDATE `ORDER` set `userId`=?, `destination`=?, `shippingFee`=?, `finalCost`=? WHERE orderId=?";
        Connection conn = null;


        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setString(2, address);
            ps.setString(3, Double.toString(shippingCost));
            ps.setString(4, Double.toString(finalTotal));
            ps.setInt(4, orderId);
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

    public boolean editCart(Cart cart, int orderId) {
        String sql1 = "DELETE FROM `cart` WHERE orderId=?";
        Connection conn1 = null;

        try {
            conn1 = dataSource.getConnection();
            PreparedStatement ps = conn1.prepareStatement(sql1);
            ps.setInt(1, orderId);
            ps.executeQuery();
            ps.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);

        } finally {
            if (conn1 != null) {
                try {
                    conn1.close();
                } catch (SQLException e) {}
            }
        }

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

    public ArrayList<OrderAndCart> getUserCart(int userId) {
        String sql = "SELECT * FROM `order` LEFT JOIN `cart` ON order.orderId = cart.orderId WHERE userId = ?";
        Connection conn = null;

        int checkOrderID = 0;

        ArrayList<OrderAndCart> orderAndCartItems = new ArrayList<>();
        Order userOrder = new Order();
        ArrayList<CartItem> cartItems = new ArrayList<>();
        boolean check = false;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.executeQuery();

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Product orderedProduct = new Product();

                if(checkOrderID != rs.getInt("orderId")) {

                    if(userOrder.getId() != 0 && !cartItems.isEmpty()) {
                        OrderAndCart orderandCartItem = new OrderAndCart(userOrder,cartItems);
                        orderAndCartItems.add(orderandCartItem);
                    }

                    userOrder = new Order(rs.getInt("orderId"), rs.getString("userId"), rs.getString("destination"), rs.getString("status"), rs.getInt("shippingFee"), rs.getInt("finalCost"));
                    cartItems = new ArrayList<>();
                    checkOrderID = rs.getInt("orderId");
                    check = true;
                }

                CartItem cartitem = new CartItem();

                orderedProduct.setPrice(rs.getInt("price"));
                orderedProduct.setTitle(rs.getString("title"));

                cartitem.setProduct(orderedProduct);
                cartitem.setQuantity(rs.getInt("quantity"));

                cartItems.add(cartitem);
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

        return orderAndCartItems;

    }


    public ArrayList<OrderAndCart> getAllCart() {
        String sql = "SELECT * FROM `order` LEFT JOIN `cart` ON order.orderId = cart.orderId";
        Connection conn = null;

        int checkOrderID = 0;

        ArrayList<OrderAndCart> orderAndCartItems = new ArrayList<>();
        Order userOrder = new Order();
        ArrayList<CartItem> cartItems = new ArrayList<>();
        boolean check = false;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.executeQuery();

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Product orderedProduct = new Product();

                if(checkOrderID != rs.getInt("orderId")) {

                    if(userOrder.getId() != 0 && !cartItems.isEmpty()) {
                        OrderAndCart orderandCartItem = new OrderAndCart(userOrder,cartItems);
                        orderAndCartItems.add(orderandCartItem);
                    }

                    userOrder = new Order(rs.getInt("orderId"), rs.getString("userId"), rs.getString("destination"), rs.getString("status"), rs.getInt("shippingFee"), rs.getInt("finalCost"));
                    cartItems = new ArrayList<>();
                    checkOrderID = rs.getInt("orderId");
                    check = true;
                }

                CartItem cartitem = new CartItem();

                orderedProduct.setPrice(rs.getInt("price"));
                orderedProduct.setTitle(rs.getString("title"));

                cartitem.setProduct(orderedProduct);
                cartitem.setQuantity(rs.getInt("quantity"));

                cartItems.add(cartitem);
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

        return orderAndCartItems;

    }

    public void changeStatus(Order order) {

        String sql = "UPDATE `ORDER` SET status=? WHERE orderId=?";
        Connection conn = null;

        String newStatus = order.getNextStatus();

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, newStatus);
            ps.setInt(2, order.getId());
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

    public Order getOrder(int orderId) {

        Order userOrder = new Order();

        String sql = "SELECT * FROM `order` WHERE orderId=?";
        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, orderId);
            ps.executeQuery();

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                userOrder = new Order(rs.getInt("orderId"), rs.getString("userId"), rs.getString("destination"), rs.getString("status"), rs.getInt("shippingFee"), rs.getInt("finalCost"));
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

        return userOrder;
    }

    public Cart getCart(int orderId) {
        Order userOrder = new Order();

        Cart orderCart = new Cart();
        HashMap<Long,CartItem> cartItemList = new HashMap<>();

        String sql = "SELECT * FROM `order` LEFT JOIN `cart` ON order.orderId=cart.orderId WHERE orderId=?";
        Connection conn = null;

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, orderId);
            ps.executeQuery();

            ResultSet rs = ps.executeQuery();



            while (rs.next()) {
                CartItem tempCartItem = new CartItem();
                Product tempProduct = new Product();

                tempProduct.setTitle(rs.getString("title"));
                tempProduct.setPrice(rs.getDouble("price"));
                tempProduct.setProductId(rs.getInt("id"));

                tempCartItem.setQuantity(rs.getInt("quantity"));
                tempCartItem.setProduct(tempProduct);

                cartItemList.put(rs.getLong("id"), tempCartItem);
            }

            ps.close();

            orderCart.setCarts(cartItemList);

        } catch (SQLException e) {
            throw new RuntimeException(e);

        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {}
            }
        }

        return orderCart;

    }


    public HashMap<String, String> authenticate(String userName, String passWord) {

        String sql = "SELECT * FROM `users` LEFT JOIN `user_roles` ON users.userId=user_roles.userId WHERE userName=? AND userPassword=?";
        Connection conn = null;

        HashMap<String, String> sendData = new HashMap<>();

        String uName = "none";
        String uId = "none";
        String isValid = "false";
        String isAdmin = "false";

        try {
            conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, userName);
            ps.setString(2, passWord);
            ps.executeQuery();

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                uName = rs.getString("userName");
                uId = Integer.toString(rs.getInt("userId"));
                isValid = "true";
                if(rs.getString("roleName").equals("ROLE_ADMIN")) {
                    isAdmin = "true";
                } else {
                    isAdmin = "false";
                }
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

        sendData.put("userName", uName);
        sendData.put("userId", uId);
        sendData.put("isValid", isValid);
        sendData.put("isAdmin", isAdmin);

        return sendData;
    }


}
