
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
                                                                  
                                                                  
                                                                  var noOfParams = document.getElementById("noOfParameters_analyticTask").value;
                                                                  var noOfStrategies = document.getElementById("noOfStrategies").value;
                                                         
                                                         
                                                         
                                                                   
                                                                  var hiddenHTML = "<input type=\"hidden\" name=\"noOfParameters_analyticTask"+z+"\" id=\"noOfParameters_analyticTask"+z+"\" value=\""+noOfParams+"\">"
                                                                  + "<input type=\"hidden\" name=\"noOfResourceControlStrategies"+z+"\" id=\"noOfResourceControlStrategies"+z+"\" value=\""+noOfStrategies+"\">";
                                                                  
                                                                  
                                                                  var appendedHTML = hiddenHTML +"<br><br><table>"+
                                                                  "<tr>"+
                                                                  "<td>&#8226; Resource Control Case "+z+"</td>"+
                                                                  "<td></td>"+
                                                                  "</tr>"+
                                                                  "<tr>"+
                                                                  "<td>&#45; Estimated Result</td>"+
                                                                  "<td></td>"+
                                                                  "</tr>"+
                                                                  "<tr>"+
                                                                  "<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;From</td>"+
                                                                  "<td><input type=\"text\" id=\"es_from"+z+"\" name=\"es_from"+z+"\"></td>"+
                                                                  "</tr>"+
                                                                  "<tr>"+
                                                                  "<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;To</td>"+
                                                                  "<td><input type=\"text\" id=\"es_to"+z+"\" name=\"es_to"+z+"\"></td>"+
                                                                  "</tr>"+
                                                                  "<tr>"+
                                                                  "<td>&#45; Analytic Task</td>"+
                                                                  "<td></td>"+
                                                                  "</tr>"+
                                                                  "<tr>"+
                                                                  "<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;TaskName</td>"+
                                                                  "<td><input type=\"text\" id=\"taskName"+z+"\" name=\"taskName"+z+"\"></td>"+
                                                                  "</tr>"+
                                                                  "<tr>"+
                                                                  "<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Parameter</td>"+
                                                                  "<td></td>"+
                                                                  "</tr>"+
                                                                  
                                                                  "</table>";
                                                                  
                                                                  
                                                                  
                                                                  
                                                                  
                                                                  for (i = 1; i <= noOfParams; i++) {
                                                                  var paramHtml = "<table>"+
                                                                  "<tr>"+
                                                                  "<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Parameter "+i+"</td>"+
                                                                  "<td><input type=\"text\" id=\"ParameterName"+z+"_"+i+"\" name=\"ParameterName"+z+"_"+i+"\"></td>"+
                                                                  "</tr>"+
                                                                  "<tr>"+
                                                                  "<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Type</td>"+
                                                                  "<td><input type=\"text\" id=\"Type"+z+"_"+i+"\" name=\"Type"+z+"_"+i+"\"></td>"+
                                                                  "</tr>"+
                                                                  "<tr>"+
                                                                  "<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Value</td>"+
                                                                  "<td><input type=\"text\" id=\"ParameterValue"+z+"_"+i+"\" name=\"ParameterValue"+z+"_"+i+"\"></td>"+
                                                                  "</tr>"+
                                                                  "</table>";
                                                                  
                                                                  appendedHTML += paramHtml;

                                                                  
                                                                  
                                                                  
                                                                  
                                                                  }
                                                                  
                                                                  appendedHTML += "<table>"+
                                                                  "<tr>"+
                                                                  "<td>&#45; Resource Control Strategy</td>"+
                                                                  "<td></td>"+
                                                                  "</tr>"+
                                                                  "</table>";

                                                                  
                                                                  
                                                                  for (i = 1; i <= noOfStrategies; i++) {
                                                                  
                                                                  var strategyHTML = "<table>"+
                                                                  "<tr>"+
                                                                  "<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Resource Control Strategy "+i+"</td>"+
                                                                  "<td></td>"+
                                                                  "</tr>"+
                                                                  "<tr>"+
                                                                  "<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Control Metric</td>"+
                                                                  "<td><input type=\"text\" id=\"controlMetric"+z+"_"+i+"\" name=\"controlMetric"+z+"_"+i+"\"></td>"+
                                                                  "</tr>"+
                                                                  "<tr>"+
                                                                  "<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Effect on Primitive Action</td>"+
                                                                  "<td><input type=\"text\" id=\"primitiveAction"+z+"_"+i+"\" name=\"primitiveAction"+z+"_"+i+"\"></td>"+
                                                                  "</tr>"+
                                                                  "<tr>"+
                                                                  "<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Scale in Condition</td>"+
                                                                  "<td></td>"+
                                                                  "</tr>"+
                                                                  "<tr>"+
                                                                  "<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;From</td>"+
                                                                  "<td><input type=\"text\" id=\"es_from_in"+z+"_"+i+"\" name=\"es_from_in"+z+"_"+i+"\"></td>"+
                                                                  "</tr>"+
                                                                  "<tr>"+
                                                                  "<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;To</td>"+
                                                                  "<td><input type=\"text\" id=\"es_to_in"+z+"_"+i+"\" name=\"es_to_in"+z+"_"+i+"\"></td>"+
                                                                  "</tr>"+
                                                                  "<tr>"+
                                                                  "<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Scale out Condition</td>"+
                                                                  "<td></td>"+
                                                                  "</tr>"+
                                                                  "<tr>"+
                                                                  "<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;From</td>"+
                                                                  "<td><input type=\"text\" id=\"es_from_out"+z+"_"+i+"\" name=\"es_from_out"+z+"_"+i+"\"></td>"+
                                                                  "</tr>"+
                                                                  "<tr>"+
                                                                  "<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;To</td>"+
                                                                  "<td><input type=\"text\" id=\"es_to_out"+z+"_"+i+"\" name=\"es_to_out"+z+"_"+i+"\"></td>"+
                                                                  "</tr>"+
                                                                  
                                                                  
                                                                  "</tr>"+
                                                                  "</table>";
                                                                  
                                                                  
                                                                  appendedHTML += strategyHTML;

                                                                  
                                                                  }
                                                                  
                                                                  
                                                               
                                                               $(wrapper_adjustment_case).append(appendedHTML); //add input box
                                                               document.getElementById("noOfResourceControlCases").value = z;
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
    
    <form action="AddingResourceControlAction" method="post" enctype="multipart/form-data">
      
    <input type="hidden" name="noOfResourceControlCases" id="noOfResourceControlCases" value="1">
    
    <div>
        
        <div>
            
            <fieldset>
                <legend>Resource Control Action</legend>
            <table>
                <tr>
                    <td>Resource Control Action Name</td>
                    <td><input type="text" id="ResourceControlActionName" name="ResourceControlActionName"></td>
                </tr>
                <tr>
                    <td>Associated QoR Metric</td>
                    <td><input type="text" id="AssociatedQoRMetric" name="AssociatedQoRMetric"></td>
                </tr>
                

            </table>
            </fieldset>
            <br>
        
        
            
            <fieldset>
                <legend>Resource Control Cases</legend>
                
                <table>
                    <tr>
                        <td><button class="add_field_button_adjustment_case">Add More Resource Control Case</button></td>
                        <td><button class="remove_field_button_adjustment_case">Remove All Resource Control Cases</button></td>
                    </tr>
                    
                    <tr>
                        <td>Number of Parameters in Analytic Task: </td>
                        <td><input type="text" id="noOfParameters_analyticTask" name="noOfParameters_analyticTask" value="1"></td>
                    </tr>
                    
                    <tr>
                        <td>Number of Control Strategies: </td>
                        <td><input type="text" id="noOfStrategies" name="noOfStrategies" value="1"></td>
                    </tr>
                </table>
                
                <hr>
                
                <br>
                
                <div class="input_fields_adjustment_case">
                    <!--
                    <table>
                        <tr>
                            <td>&#8226; Resource Control Case 1</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td>&#45; Estimated Result</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;From</td>
                            <td><input type="text" id="es_from" name="es_from"></td>
                        </tr>
                        <tr>
                            <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;To</td>
                            <td><input type="text" id="es_to" name="es_to"></td>
                        </tr>
                        <tr>
                            <td>&#45; Analytic Task</td>
                            <td></td>
                        </tr>
                        <tr>
                            <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;TaskName</td>
                            <td><input type="text" id="taskName" name="taskName"></td>
                        </tr>
                        <tr>
                            <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Parameter</td>
                            <td></td>
                        </tr>
            
                        </table>
            
           
            
            <table>
                <tr>
                    <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Parameter 1</td>
                    <td><input type="text" id="ParameterName1" name="ParameterName1"></td>
                </tr>
                <tr>
                    <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Type</td>
                    <td><input type="text" id="Type1" name="Type1"></td>
                </tr>
                <tr>
                    <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Value</td>
                    <td><input type="text" id="ParameterValue1" name="ParameterValue1"></td>
                </tr>
            </table>
            
           
            
                        <table>
                            <tr>
                                <td>&#45; Resource Control Strategy</td>
                                <td></td>
                            </tr>
                        </table>
                        
                        
                        <table>
                            <tr>
                                <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Resource Control Strategy 1</td>
                                <td></td>
                            </tr>
                            <tr>
                                <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Control Metric</td>
                                <td><input type="text" id="controlMetric" name="controlMetric"></td>
                            </tr>
                            <tr>
                                <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Effect on Primitive Action</td>
                                <td><input type="text" id="primitiveAction" name="primitiveAction"></td>
                            </tr>
                            <tr>
                                <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Scale in Condition</td>
                                <td></td>
                            </tr>
                            <tr>
                                <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;From</td>
                                <td><input type="text" id="es_from_in" name="es_from_in"></td>
                            </tr>
                            <tr>
                                <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;To</td>
                                <td><input type="text" id="es_to_in" name="es_to_in"></td>
                            </tr>
                            <tr>
                                <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Scale out Condition</td>
                                <td></td>
                            </tr>
                            <tr>
                                <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;From</td>
                                <td><input type="text" id="es_from_out" name="es_from_out"></td>
                            </tr>
                            <tr>
                                <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;To</td>
                                <td><input type="text" id="es_to_out" name="es_to_out"></td>
                            </tr>


                        </tr>
                      </table>
                    -->
                     
                     
                </div>
            </fieldset>
            <br>
            
            <input type="submit" value="Add Resource Control Action">
        
        </div>
    </div>
    </form>
    
    </body>
    
    </html>
    
                               