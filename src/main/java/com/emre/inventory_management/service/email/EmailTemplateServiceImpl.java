package com.emre.inventory_management.service.email;

import com.emre.inventory_management.model.order.OrderItem;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class EmailTemplateServiceImpl implements EmailTemplateService{
    @Override
    public String buildOrderEmail(String customerName, List<OrderItem> items) {

        StringBuilder productsHtml = new StringBuilder();
        for (OrderItem item : items) {
            BigDecimal totalCost = item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
            productsHtml.append(
                    "<tr>" +
                            "<td style='padding: 8px 0'>" + item.getProduct().getName() + "</td>" +
                            "<td style='padding: 8px 0'>" + item.getQuantity() + "</td>" +
                            "<td style='padding: 8px 0'>$" + totalCost + "</td>" +
                            "</tr>"
            );
        }

        return """
            <div style="font-family: Arial, sans-serif; font-size:16px; color:#333;">
                <h2>Thank you for your order!</h2>

                <p>Hi %s,</p>
                <p>We received your order successfully. Here are the details:</p>

                <table style="width:100%%; border-collapse: collapse;">
                    <thead>
                        <tr style="font-weight:bold; border-bottom: 1px solid #ddd;">
                            <td>Product</td>
                            <td>Quantity</td>
                            <td>Price</td>
                        </tr>
                    </thead>
                    <tbody>
                        %s
                    </tbody>
                </table>

                <br>
                <p>We will notify you again once your items are shipped.</p>
                <br>

                <p>Best Regards,</p>
                <b>Your Store Team</b>
            </div>
            """.formatted(customerName, productsHtml);
    }

    @Override
    public String buildUserEmail(String customerName) {

        return """
            <div style="font-family: Arial, sans-serif; font-size:16px; color:#333;">
                <h2>Welcome to our store!</h2>

                <p>Hi %s,</p>
                <p>Thank you for your registration</p>
                <p>Your account has been successfully created.</p>

                <br>
                <p>We are happy to have you here. Have a great day!</p>
                <br>

                <p>Best Regards,</p>
                <b>Your Store Team</b>
            </div>
            """.formatted(customerName);
    }

}
