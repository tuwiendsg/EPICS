<%-- 
    Document   : addMA
    Created on : Jun 10, 2015, 3:45:03 PM
    Author     : Jun
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
         <title>Depic Tooling</title>
        <link rel="stylesheet" href="css/kanso.css?v=2.0.1">
        <link rel="stylesheet" href="css/dropdown.css">
        <link href='http://fonts.googleapis.com/css?family=Oswald' rel='stylesheet' type='text/css'>
        <link href="http://fonts.googleapis.com/css?family=Allerta" rel="stylesheet" type="text/css">
        
        <script type="text/javascript" src="http://www.sanwebe.com/wp-content/themes/sanwebe/js/jquery-1.10.2.min.js"></script>
        <script>
            $(document).ready(function () {
                var max_fields = 10; //maximum input boxes allowed
                var wrapper = $(".input_fields_wrap"); //Fields wrapper
                var add_button = $(".add_field_button"); //Add button ID

                var x = 0; //initlal text box count
                $(add_button).click(function (e) { //on add input button click
                    e.preventDefault();
                    if (x < max_fields) { //max input box allowed
                        x++; //text box increment

                        var appendedHTML = "<br><table>" +
                                "<tr>" +
                                "<td>Parameter Name</td>" +
                                "<td><input type=\"text\" id=\"ParameterName" + x + "\" name=\"ParameterName" + x + "\"></td>" +
                                "</tr>" +
                                "<tr>" +
                                "<td>Type</td>" +
                                "<td><input type=\"text\" id=\"Type" + x + "\" name=\"Type" + x + "\"></td>" +
                                "</tr>" +
                                "<tr>" +
                                "<td>Parameter Value</td>" +
                                "<td><input type=\"text\" id=\"ParameterValue" + x + "\" name=\"ParameterValue" + x + "\"></td>" +
                                "</tr>" +
                                "</table>";

                        $(wrapper).append(appendedHTML); //add input box
                        document.getElementById("noOfParameters").value = x;
                    }
                });

                $(wrapper).on("click", ".remove_field", function (e) { //user click on remove text
                    e.preventDefault();
                    $(this).parent('div').remove();
                    x--;
                })



            });
        </script>
    </head>
    <body>
        
        <h3>DEPIC</h3>
        <header>

            <div class="nav">
                <ul>
                    <li class="tool"><a  href="index.jsp">Depic Tooling</a></li>
                    <li class="daf"><a class="active" href="daf_manager.jsp">PAM</a></li>
                </ul>
            </div>

        </header>
        
        <br>
        
        <form action="AddingMonitoringAction" method="post" enctype="multipart/form-data">
            <input type="hidden" name="noOfParameters" id="noOfParameters" value="1">
            <div>

                <div>

                    <fieldset>
                        <legend>Monitoring Action</legend>
                        <table>
                            <tr>
                                <td>Monitoring Action Name</td>
                                <td><input type="text" id="MonitoringActionName" name="MonitoringActionName"></td>
                            </tr>
                            <tr>
                                <td>Description</td>
                                <td><input type="text" id="Description" name="Description"></td>
                            </tr>
                            <tr>
                                <td>Associated QoR Metric</td>
                                <td><input type="text" id="AssociatedQoRMetric" name="AssociatedQoRMetric"></td>
                            </tr>


                        </table>
                    </fieldset>
                    <br>
                    <fieldset>
                        <legend>Artifact</legend>
                        <table>
                            <tr>
                                <td>ArtifactID</td>
                                <td><input type="text" id="ArtifactID" name="ArtifactID"></td>
                            </tr>
                            <tr>
                                <td>ArtifactDescription</td>
                                <td><input type="text" id="ArtifactDescription" name="ArtifactDescription"></td>
                            </tr>
                            <tr>
                                <td>Location</td>
                                <td><input type="text" id="Location" name="Location"></td>
                            </tr>
                            <tr>
                                <td>Type</td>
                                <td><input type="text" id="Type" name="Type"></td>
                            </tr>

                            <tr>
                                <td>RESTfulAPI</td>
                                <td><input type="text" id="RESTfulAPI" name="RESTfulAPI"></td>
                            </tr>
                            <tr>
                                <td>HttpMethod</td>
                                <td><input type="text" id="HttpMethod" name="HttpMethod"></td>
                            </tr>
                        </table>
                    </fieldset>
                    <br>
                    <fieldset>
                        <legend>Parameter</legend>
                        <button class="add_field_button">Add More Parameter</button>
                        <div class="input_fields_wrap">
                            <!--
                            <table>
                                <tr>
                                    <td>Parameter Name</td>
                                    <td><input type="text" id="ParameterName1" name="ParameterName1"></td>
                                </tr>
                                <tr>
                                    <td>Type</td>
                                    <td><input type="text" id="Type1" name="Type1"></td>
                                </tr>
                                <tr>
                                    <td>Parameter Value</td>
                                    <td><input type="text" id="ParameterValue1" name="ParameterValue1"></td>
                                </tr>
                            </table>
                            -->
                        </div>
                    </fieldset>
                    <br>
                    <input type="submit" value="Add Monitoring Action">

                </div>
            </div>
        </form>

    </body>
</html>
