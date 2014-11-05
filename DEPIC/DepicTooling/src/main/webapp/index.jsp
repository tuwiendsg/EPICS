<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Depic Tooling</title>
        
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.3.0/jquery.min.js"></script>
  
    <link rel="stylesheet" href="css/kanso.css?v=2.0.1">
    <link rel="stylesheet" href="css/dropdown.css">
    <link href='http://fonts.googleapis.com/css?family=Oswald' rel='stylesheet' type='text/css'>
    <link href="http://fonts.googleapis.com/css?family=Allerta" rel="stylesheet" type="text/css">
    </head>
    <body>
        
        <h3>DEPIC</h3>
        <header>

            <div class="nav">
                <ul>
                    <li class="tool"><a class="active" href="index.jsp">Depic Tooling</a></li>
                    <li class="edaas"><a href="index.jsp">eDaaS Manager</a></li>
                </ul>
            </div>

        </header>
        <form action="Uploader" method="post" enctype="multipart/form-data"> <br>
            <table class="smart-green">

                <tr>
                    <td>eDaaS Name</td>
                    <td><input type="text" name="edaas"></td> 
                </tr>
                 <tr>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td> 
                </tr>
                <tr>
                    <td>Quality of Results</td>
                    <td><input type="file" name="qor" /></td> 
                </tr>
                
                 <tr>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td> 
                </tr>
                
                <tr>
                    <td>Elasticity Processes</td>
                    <td><input type="file" name="ep" /></td> 
                </tr>
                  <tr>
                    <td>&nbsp;</td>
                    <td>&nbsp;</td> 
                </tr>
                <tr>
                    <td></td>
                    <td><input type="submit" name="submit" value="Generate eDaaS"/></td> 
                </tr>

            </table>
        </form>
        
    </body>
</html>
