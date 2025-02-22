<%@ page import="java.sql.*" %>
<%@ page import="org.json.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Stock Portfolio</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
    <h1>Stock Portfolio</h1>
    <table border="1">
        <thead>
            <tr>
                <th>Stock Name</th>
                <th>Buy Price</th>
                <th>Current Price</th>
                <th>Buy</th>
                <th>Sell</th>
                <th>Delete</th>
            </tr>
        </thead>
        <tbody id="stockTableBody">
            <!-- Data will be loaded here via JavaScript -->
        </tbody>
    </table>

    <script>
        fetch('PortfolioDataServlet')
            .then(response => response.json())
            .then(data => {
                const tableBody = document.getElementById('stockTableBody');
                data.forEach(stock => {
                    const row = `
                        <tr>
                            <td>${stock.stockName}</td>
                            <td>${stock.buyPrice}</td>
                            <td>${stock.currentPrice}</td>
                            <td><input type="number" placeholder="Qty"><button>Buy</button></td>
                            <td><input type="number" placeholder="Qty"><button>Sell</button></td>
                            <td><button>Delete</button></td>
                        </tr>
                    `;
                    tableBody.innerHTML += row;
                });
            });
    </script>
</body>
</html>
