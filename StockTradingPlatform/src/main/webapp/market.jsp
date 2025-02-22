<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Market Data</title>

    <!-- ✅ Chart.js और jQuery Add -->
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

    <style>
        /* ✅ Chart को Proper Size देने के लिए */
        #chart-container {
            width: 80%;
            max-width: 800px;
            margin: auto;
            padding: 20px;
        }
    </style>
</head>
<body>
    <h2>Stock Market Data</h2>

    <!-- ✅ Chart के लिए Container -->
    <div id="chart-container">
        <canvas id="stockChart"></canvas>
    </div>

    <script>
        let stockChart;

        function fetchMarketData() {
            $.ajax({
                url: "/StockTradingPlatform/market-data", // ✅ Servlet से Data Fetch करना
                type: "GET",
                dataType: "json",
                success: function(response) {
                    console.log("Market Data Response:", response); // ✅ Debugging के लिए

                    let stockNames = response.map(stock => stock.stock_name);
                    let stockPrices = response.map(stock => stock.current_price);

                    if (stockChart) {
                        stockChart.destroy();
                    }

                    let ctx = document.getElementById('stockChart').getContext('2d');
                    stockChart = new Chart(ctx, {
                        type: 'line',
                        data: {
                            labels: stockNames,
                            datasets: [{
                                label: 'Stock Prices (₹)',
                                data: stockPrices,
                                borderColor: 'blue',
                                backgroundColor: 'rgba(0, 0, 255, 0.2)',
                                fill: true,
                                tension: 0.4
                            }]
                        },
                        options: {
                            responsive: true,
                            plugins: {
                                legend: {
                                    display: true,
                                    position: 'top'
                                }
                            },
                            scales: {
                                x: {
                                    title: {
                                        display: true,
                                        text: "Stock Names"
                                    }
                                },
                                y: {
                                    beginAtZero: false,
                                    title: {
                                        display: true,
                                        text: "Price (₹)"
                                    }
                                }
                            }
                        }
                    });
                },
                error: function(xhr, status, error) {
                    console.error("Error fetching market data:", error);
                }
            });
        }

        $(document).ready(function() {
            fetchMarketData();
            setInterval(fetchMarketData, 5000);
        });
    </script>
</body>
</html>
