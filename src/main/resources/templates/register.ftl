<#ftl output_format="HTML">

<!DOCTYPE html>
<html>
<head>
    <title>确认注册</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f2f2f2;
            margin: 0;
            padding: 0;
        }

        .container {
            max-width: 600px;
            margin: auto;
            padding: 10px;
            background-color: #fff;
            border: 1px solid #ddd;
        }

        .header {
            text-align: center;
        }

        .header h1 {
            margin-top: 0;
            color: #4357ad;
        }

        .content {
            margin-top: 20px;
            text-align: center;
        }

        .content p {
            font-size: 18px;
            line-height: 1.5;
            color: #333;
        }

        .button {
            display: inline-block;
            background-color: #4357ad;
            color: #fff;
            padding: 10px 20px;
            border-radius: 5px;
            text-decoration: none;
            margin-top: 20px;
        }

        .button:hover {
            background-color: #31427e;
        }

        .footer {
            text-align: center;
            margin-top: 40px;
            color: #999;
        }

    </style>
</head>
<body>
<div class="container">
    <div class="header">
        <h1>确认注册</h1>
    </div>
    <div class="content">
        <p>你好!感谢你的注册,你的注册邮箱是:</p>
        <p>${email}</p>
        <p>你的邮箱注册码是:</p>
        <#noautoesc>
        <img src="${registCode}"/>
        </#noautoesc>



        <p>验证码在30分钟内有效，30分钟后需要重新激活邮箱 </p>
    </div>
    <div class="footer">
        <p>如果您并没有申请此账号，请忽略此邮件。</p>
        <p>&copy;2021 All Rights Reserved.Yozora Anime</p>
    </div>
</div>
</body>
</html>

