
<!DOCTYPE HTML>
<html>
    <head>
        
        <title>Depic Tooling</title>
        <link rel="stylesheet" href="css/kanso.css?v=2.0.1">
        <link rel="stylesheet" href="css/dropdown.css">
        <link href='http://fonts.googleapis.com/css?family=Oswald' rel='stylesheet' type='text/css'>
        <link href="http://fonts.googleapis.com/css?family=Allerta" rel="stylesheet" type="text/css">
        
        
        
        <script type="text/javascript" src="http://www.sanwebe.com/wp-content/themes/sanwebe/js/jquery-1.10.2.min.js"></script>
        <script>
            $(document).ready(function() {
                              var max_fields_param      = 10; //maximum input boxes allowed
                              var wrapper_param         = $(".input_fields_param"); //Fields wrapper
                              var add_button_param      = $(".add_field_button_param"); //Add button ID
                              
                              var max_fields_prerequisite      = 10; //maximum input boxes allowed
                              var wrapper_prerequisite         = $(".input_fields_prerequisite"); //Fields wrapper
                              var add_button_prerequisite      = $(".add_field_button_prerequisite"); //Add button ID
                              
                              var max_fields_adjustment_case      = 10; //maximum input boxes allowed
                              var wrapper_adjustment_case         = $(".input_fields_adjustment_case"); //Fields wrapper
                              var add_button_adjustment_case      = $(".add_field_button_adjustment_case"); //Add button ID
                              
                              var x = 1; //initlal text box count
                              $(add_button_param).click(function(e){ //on add input button click
                                                  e.preventDefault();
                                                  if(x < max_fields_param){ //max input box allowed
                                                  x++; //text box increment
                                                  
                                                  var appendedHTML = "<br><table>" +
                                                  "<tr>" +
                                                  "<td>Parameter Name</td>" +
                                                  "<td><input type=\"text\" id=\"ParameterName"+x+"\" name=\"ParameterName"+x+"\"></td>" +
                                                  "</tr>" +
                                                  "<tr>" +
                                                  "<td>Type</td>" +
                                                  "<td><input type=\"text\" id=\"Type"+x+"\" name=\"Type"+x+"\"></td>" +
                                                  "</tr>" +
                                                  "<tr>" +
                                                  "<td>Parameter Value</td>" +
                                                  "<td><input type=\"text\" id=\"ParameterValue"+x+"\" name=\"ParameterValue"+x+"\"></td>" +
                                                  "</tr>" +
                                                  "</table>";
                                                  
                                                  $(wrapper_param).append(appendedHTML); //add input box
                                                  document.getElementById("noOfParameters").value = x;
                                                  }
                                                  });
                              
                              
                              
                              var y = 0; //initlal text box count
                              $(add_button_prerequisite).click(function(e){ //on add input button click
                                                        e.preventDefault();
                                                        if(y < max_fields_prerequisite){ //max input box allowed
                                                        y++; //text box increment
                                                        
                                                               var appendedHTML = "<table>" +
                                                               "<tr>" +
                                                               "<td>Prerequisite Action "+y+"</td>" +
                                                               "<td><input type=\"text\" id=\"prerequisiteActionName"+y+"\" name=\"prerequisiteActionName"+y+"\"></td>" +
                                                               "</tr>" +
                                                               "</table>";
                                                        
                                                        $(wrapper_prerequisite).append(appendedHTML); //add input box
                                                        document.getElementById("noOfPrerequisiteActions").value = y;
                                                        }
                                                        });
                              
                              
                              var z = 0; //initlal text box count
                              $(add_button_adjustment_case).click(function(e){ //on add input button click
                                                               e.preventDefault();
                                                               if(z < max_fields_adjustment_case){ //max input box allowed
                                                               z++; //text box increment
                                                                  
                                                                  
                                                                  var noOfParams_analyticTask = document.getElementById("noOfParameters_analyticTask").value;
                                                                  var noOfParams_adjustmentCase = document.getElementById("noOfParameters_adjustmentCase").value;

                                                                  
                                                                  var hiddenHTML = "<input type=\"hidden\" name=\"noOfParameters_analyticTask"+z+"\" id=\"noOfParameters_analyticTask"+z+"\" value=\""+noOfParams_analyticTask+"\">"
                                                                        + "<input type=\"hidden\" name=\"noOfParameters_adjustmentCase"+z+"\" id=\"noOfParameters_adjustmentCase"+z+"\" value=\""+noOfParams_adjustmentCase+"\">";
                                                                  
                                                                  
                                                               
                                                                  var appendedHTML = hiddenHTML + "<br><br><table>" +
                                                                  "<tr>" +
                                                                  "<td>&#8226; Adjustment Case "+z+"</td>" +
                                                                  "<td></td>" +
                                                                  "</tr>" +
                                                                  "<tr>" +
                                                                  "<td>&#45; Estimated Result</i></td>" +
                                                                  "<td></td>" +
                                                                  "</tr>" +
                                                                  "<tr>" +
                                                                  "<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;From</td>" +
                                                                  "<td><input type=\"text\" id=\"es_from"+z+"\" name=\"es_from"+z+"\"></td>" +
                                                                  "</tr>" +
                                                                  "<tr>" +
                                                                  "<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;To</td>" +
                                                                  "<td><input type=\"text\" id=\"es_to"+z+"\" name=\"es_to"+z+"\"></td>" +
                                                                  "</tr>" +
                                                                  "<tr>" +
                                                                  "<td>&#45; Analytic Task</td>" +
                                                                  "<td></td>" +
                                                                  "</tr>" +
                                                                  "<tr>" +
                                                                  "<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;TaskName</td>" +
                                                                  "<td><input type=\"text\" id=\"taskName"+z+"\" name=\"taskName"+z+"\"></td>" +
                                                                  "</tr>" +
                                                                  "<tr>" +
                                                                  "<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Analytic Task Parameter</i></td>" +
                                                                  "</tr>" +
                                                                  "</table>";
                                                                  
                                                                  
                                                                  
                                                                  for (i = 1; i <= noOfParams_analyticTask; i++) {
                                                                  var paramHtml = "<table><tr>" +
                                                                  "<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Parameter "+i+"</td>" +
                                                                  "<td><input type=\"text\" id=\"ParameterName_analytic"+z+"_"+i+"\" name=\"ParameterName_analytic"+z+"_"+i+"\"></td>" +
                                                                  "</tr>" +
                                                                  "<tr>" +
                                                                  "<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Type</td>" +
                                                                  "<td><input type=\"text\" id=\"Type_analytic"+z+"_"+i+"\" name=\"Type_analytic"+z+"_"+i+"\"></td>" +
                                                                  "</tr>" +
                                                                  "<tr>" +
                                                                  "<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Value</td>" +
                                                                  "<td><input type=\"text\" id=\"ParameterValue_analytic"+z+"_"+i+"\" name=\"ParameterValue_analytic"+z+"_"+i+"\"></td>" +
                                                                  "</tr>" +
                                                                  "</table><br>";
                                                                  appendedHTML += paramHtml;
                                                                  }
                                                                  
                                                                  
                                                                  appendedHTML += "<table>"+
                                                                  "</tr>" +
                                                                  "<tr>" +
                                                                  "<td>&#45; Adjustment Case Parameter</i></td>" +
                                                                  "</tr>" +
                                                                  "</table>";
                                                                  
                                                                  
                                                                  
                                                                  
                                                                  for (i = 1; i <= noOfParams_adjustmentCase; i++) {
                                                                  var paramHtml = "<table><tr>" +
                                                                  "<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Parameter "+i+"</td>" +
                                                                  "<td><input type=\"text\" id=\"ParameterName_case"+z+"_"+i+"\" name=\"ParameterName_case"+z+"_"+i+"\"></td>" +
                                                                  "</tr>" +
                                                                  "<tr>" +
                                                                  "<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Type</td>" +
                                                                  "<td><input type=\"text\" id=\"Type_case"+z+"_"+i+"\" name=\"Type_case"+z+"_"+i+"\"></td>" +
                                                                  "</tr>" +
                                                                  "<tr>" +
                                                                  "<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Value</td>" +
                                                                  "<td><input type=\"text\" id=\"ParameterValue_case"+z+"_"+i+"\" name=\"ParameterValue_case"+z+"_"+i+"\"></td>" +
                                                                  "</tr>" +
                                                                  "</table><br>";
                                                                  appendedHTML += paramHtml;
                                                                  }

                                                                  
                                                                  
                                                                  
                                                               
                                                               $(wrapper_adjustment_case).append(appendedHTML); //add input box
                                                               document.getElementById("noOfAdjustmentCases").value = z;
                                                               }
                                                               });
                              
                              
                              $(wrapper_param).on("click",".remove_field", function(e){ //user click on remove text
                                            e.preventDefault(); $(this).parent('div').remove(); x--;
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
    
    <form action="AddingAdjustmentAction" method="post" enctype="multipart/form-data">
        <input type="hidden" name="noOfPrerequisiteActions" id="noOfPrerequisiteActions" value="1">
    <input type="hidden" name="noOfAdjustmentCases" id="noOfAdjustmentCases" value="1">
    
    <div>
        
        <div>
            
            <fieldset>
                <legend>Adjustment Action</legend>
            <table>
                <tr>
                    <td>Adjustment Action Name</td>
                    <td><input type="text" id="AdjustmentActionName" name="AdjustmentActionName"></td>
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
                <legend>Prerequisite Actions</legend>
                
                <button class="add_field_button_prerequisite">Add More Prerequisite Action</button>

                
                <div class="input_fields_prerequisite">
                    <!--
                <table>
                    <tr>
                        <td>Prerequisite Action Name</td>
                        <td><input type="text" id="prerequisiteActionName" name="prerequisiteActionName"></td>
                    </tr>
                </table>
                     
                     -->
                </div>
            </fieldset>
            
            
            <br>
            
            <fieldset>
                <legend>Adjustment Cases</legend>
                
             
                
                
                <table>
                    <tr>
                        <td><button class="add_field_button_adjustment_case">Add More Adjustment Case</button></td>
                        <td><button class="remove_field_button_adjustment_case">Remove All Adjustment Cases</button></td>
                    </tr>
                    
                    <tr>
                        <td>Number of Parameters in Analytic Task: </td>
                        <td><input type="text" id="noOfParameters_analyticTask" name="noOfParameters_analyticTask" value="1"></td>
                    </tr>
                    
                    <tr>
                        <td>Number of Parameters in Adjustment Case: </td>
                        <td><input type="text" id="noOfParameters_adjustmentCase" name="noOfParameters_adjustmentCase" value="1"></td>
                    </tr>
                </table>
                
                <hr>
                
                <br>
                
                <div class="input_fields_adjustment_case">
                    <!--
                    <table>
                        <tr>
                            <td><b>Estimated Result</b></td>
                            <td></td>
                        </tr>
                        <tr>
                            <td>From</td>
                            <td><input type="text" id="es_from" name="es_from"></td>
                        </tr>
                        <tr>
                            <td>To</td>
                            <td><input type="text" id="es_to" name="es_to"></td>
                        </tr>
                        <tr>
                            <td><b>Analytic Task</b></td>
                            <td></td>
                        </tr>
                        <tr>
                            <td>TaskName</td>
                            <td><input type="text" id="taskName" name="taskName"></td>
                        </tr>
                        <tr>
                            <td><b>Parameter</b></td>
                            <tr>
                                <td>Parameter Name</td>
                                <td><input type="text" id="ParameterName" name="ParameterName"></td>
                            </tr>
                            <tr>
                                <td>Type</td>
                                <td><input type="text" id="Type" name="Type"></td>
                            </tr>
                            <tr>
                                <td>Parameter Value</td>
                                <td><input type="text" id="ParameterValue" name="ParameterValue"></td>
                            </tr>
                        </tr>
                      </table>
                    -->
                </div>
            </fieldset>
            <br>
            <!--
            <fieldset>
                <legend>Parameter</legend>
                <button class="add_field_button">Add More Parameter</button>
                <div class="input_fields_wrap">
                
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
                </div>
            </fieldset>
            
            -->
            <br>
            <input type="submit" value="Add Adjustment Action">
        
        </div>
    </div>
    </form>
    
    </body>
    
    </html>
    
                               