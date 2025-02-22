<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            text-align: center;
            padding: 50px;
        }
        .logout-btn {
            background-color: red;
            color: white;
            border: none;
            padding: 10px 20px;
            font-size: 16px;
            cursor: pointer;
            border-radius: 5px;
        }
        .dashboard-options {
            margin-top: 20px;
        }
        .dashboard-options a {
            display: block;
            color: blue;
            font-size: 18px;
            margin-bottom: 10px;
            text-decoration: none;
        }
        .dashboard-options a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>

    <h1>Welcome, harlal!</h1>

    <button class="logout-btn">Logout</button>

    <h2>Dashboard Options:</h2>

    <div class="dashboard-options">
        <a href="view-portfolio.jsp">View Portfolio</a>
        <a href="market-data.jsp">Market Data</a>
        <a href="buy-stocks.jsp">Buy Stocks</a>
    </div>

</body>
</html>
