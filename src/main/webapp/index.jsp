<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Библиотека</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #F2F2F2;
        }
        .container {
            max-width: 800px;
            margin: 0 auto;
            padding: 20px;
        }
        h1 {
            font-size: 36px;
            color: #444;
        }
        nav {
            display: flex;
            justify-content: space-between;
            margin-bottom: 20px;
            background-color: #EEE;
            padding: 10px;
        }
        a {
            text-decoration: none;
            font-size: 24px;
            color: #333;
            margin-right: 20px;
            transition: all 0.2s ease-in-out;
        }
        a:hover {
            color: #f18f15;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            background-color: #FFF;
            margin-bottom: 20px;
        }
        th, td {
            padding: 10px;
            text-align: left;
        }
        th {
            background-color: #EEE;
        }
        tr:nth-child(even) {
            background-color: #F2F2F2;
        }
        form {
            margin-bottom: 20px;
        }
        label, input {
            display: block;
            margin-bottom: 10px;
        }
        input[type="submit"] {
            background-color: #333;
            color: #FFF;
            border: none;
            padding: 10px;
            font-size: 18px;
            cursor: pointer;
            transition: all 0.2s ease-in-out;
        }
        input[type="submit"]:hover {
            background-color: #666;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Библиотека</h1>
    <nav>
        <a href="/people">Читатели</a>
        <a href="/books">Книги</a>
    </nav>
    <% if (request.getRequestURI().endsWith("/people")) { %>
    <h2>Читатели</h2>
    <% } else if (request.getRequestURI().endsWith("/books")) { %>
    <h2>Книги</h2>
    <% } %>
    <div th:replace="${body}">
    </div>
</div>
</body>
</html>
