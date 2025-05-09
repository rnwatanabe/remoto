
<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"><!-- InstanceBegin template="/Templates/template.dwt" codeOutsideHTMLIsLocked="false" -->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="bg.css"/>
<link rel="shortcut icon" href="favicon.ico" />
<!-- InstanceBeginEditable name="doctitle" -->
<title>.: ReMoto :.</title>
<!-- InstanceEndEditable -->
<style type="text/css">
.first {
	margin-right: auto;
	margin-left: auto;
	width: 1020px;
	background-color: #666;
	min-height: 620px;
	min-width: 800px;
	padding-top: 5px;
	padding-right: 2px;
	padding-bottom: 10px;
	padding-left: 2px;
	border-top-width: 0px;
	border-right-width: 0px;
	border-bottom-width: 0px;
	border-left-width: 0px;
	border-top-style: none;
	border-right-style: none;
	border-bottom-style: none;
	border-left-style: none;
	height: auto;
}
.banner {
	height: 154px;
	width: 1024px;
	margin-right: auto;
	margin-left: auto;
	max-width: 1024px;
	border-top-width: 2px;
	border-right-width: 2px;
	border-bottom-width: 0px;
	border-left-width: 2px;
	border-top-style: none;
	border-right-style: none;
	border-bottom-style: none;
	border-left-style: none;
	background-image: url(remoto_top.png);
}
.second {
	background-color: #154151;
	width: 98%;
	margin-right: auto;
	margin-left: auto;
	border-left-width: medium;
	border-left-style: solid;
	border-left-color: #59ACBB;
	padding: 0px;
	height: 42px;
}

#links {
	font-family: "Times New Roman", Times, serif;
	font-size: small;
	text-align: center;
	width: 1024px;
	margin-right: auto;
	margin-left: auto;
}
#footer {
	font-family: "Times New Roman", Times, serif;
	font-size: small;
	text-align: center;
	width: 1024px;
	margin-right: auto;
	margin-left: auto;
}

.links_banner {
	padding-top: 112px;
	text-align: right;
	padding-right: 10px;
	color: #FFF;
	font-family: "Times New Roman", Times, serif;
}
</style>
<!-- InstanceBeginEditable name="head" -->
<style type="text/css">
.synapticInput {
	background-color: #F5F9FA;
	height: auto;
	width: 980px;
	margin-right: auto;
	margin-left: auto;
	border: medium solid #C0DCE2;
	text-align: center;
	padding-top: 0%;
	padding-right: 5px;
	padding-bottom: 1%;
	padding-left: 5px;
}
</style>

<script src="SpryAssets/SpryCollapsiblePanel.js" type="text/javascript"></script>
<script src="SpryAssets/SpryTabbedPanels.js" type="text/javascript"></script>
<script>

function change()
{
	document.InputForm.task.value = "change";
	document.InputForm.submit();
}

</script>


<script>
    <%
String tabIndex = (String)request.getAttribute("tabIndex");
if( tabIndex == null ) 
	tabIndex = "0";
%>
	var tabIndex = "<%= tabIndex %>";
	
  </script>



<link href="SpryAssets/SpryCollapsiblePanel.css" rel="stylesheet"
	type="text/css" />
<link href="hint.css" rel="stylesheet" type="text/css" />
<!-- InstanceEndEditable -->
<script src="SpryAssets/SpryMenuBar.js" type="text/javascript"></script>
<link href="SpryAssets/SpryMenuBarHorizontal.css" rel="stylesheet" type="text/css" />

<script type="text/javascript">

  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-5761986-3']);
  _gaq.push(['_trackPageview']);

  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();

</script>

</head>

<body>
<div class="banner">

<div class="links_banner"><br />
Current Version: 2.2 beta</div>

</div>
<div class="first">
  <div class="second">
  <ul id="MenuBar" class="MenuBarHorizontal">
    <li><a href="/remoto/overview.htm">Overview</a></li>
    <li><a href="/remoto/general.do">Scenarios</a>      </li>
<li><a class="MenuBarItemSubmenu">Structure</a>
  <ul>
    <li><a href="/remoto/input.do">Descending Commands</a></li>
    <li><a href="/remoto/network.do">Neural Elements</a></li>
    <li><a class="MenuBarItemSubmenu">Neural Parameters</a>
      <ul>
        <li><a href="/remoto/synapse.do">Synapses</a></li>
        <li><a href="/remoto/conductance.do">Conductances</a></li>
        <li><a href="/remoto/motoneuron.do?task=MN">Motoneurons</a></li>
        <li><a href="/remoto/interneuron.do?task=IN">Interneurons</a></li>
        <li><a href="/remoto/sensory.do?task=AF">Sensory Fibers</a></li>
      </ul>
    </li>
    <li><a class="MenuBarItemSubmenu">Proprioceptors</a>
      <ul>
        <li><a href="/remoto/muscleSpindle.do">Muscle Spindle</a></li>
        <li><a href="/remoto/golgiTendonOrgan.do">Golgi Tendon Organ</a></li>
      </ul>
    </li>
    <li><a href="/remoto/biomechanicalElements.do">Biomechanical Elements</a></li>
    <li><a class="MenuBarItemSubmenu">Biomechanical Parameters</a>
      <ul>
        <li><a href="/remoto/joint.do">Joint</a></li>
        <li><a href="/remoto/musculotendon.do">Musculotendon</a></li>
      </ul>
    </li>
    <li><a href="/remoto/emg.do">Electromyogram</a></li>
    <li><a href="/remoto/miscellaneous.do">Miscellaneous</a>      </li>
      </ul>
  </li>
    <li><a class="MenuBarItemSubmenu">Stimulation</a>
      <ul>
        <li><a href="/remoto/stimulation.do">Nerve Stimulation</a></li>
        <li><a href="/remoto/injectedCurrent.do">Injected Current</a></li>
       
      </ul>
    </li>
    <li><a href="/remoto/start.do">Run</a></li>
    <li><a href="/remoto/results.do?opt=chart_img">Results</a>      </li>
    <li><a href="/remoto/login.do">Login</a></li>
  </ul>
</div>
  <!-- InstanceBeginEditable name="EditRegion1" -->
<p>
  <div class="synapticInput">
    <html:form method="post" action="/input">

<html:hidden name="InputForm" property="task" value="saveStimulus" />

<table width="100%" border="0">
<tr><td height="90" colspan=2 align="center" valign="middle"><table width="100%" border="0">
  <tr>
    <td height="90" colspan=2 align="center" valign="middle"><b><font size="4" face="Arial, Helvetica, sans-serif">Descending Commands</font></b></td>
  </tr>
  
  
  <tr>
    
  
  <td>
  
  <table width="970" border="0" bgcolor="#DAEAEB">
            
            <logic:iterate name="InputForm" property="inputs" id="neuronType" indexId="index">
              <tr>
              <td width="10" height="30" bgcolor="#EEF5F7"><html:checkbox name="neuronType" property="active" indexed="true" onchange="submit();"/></td>
                <td colspan="7" height="30" align="left" bgcolor="#EEF5F7"><strong><font face="Arial, Helvetica, sans-serif" size="2">
                  <bean:write name="neuronType" property="tractName"/>
                  </font></strong>
                  <html:hidden name="neuronType" property="type" indexed="true"/>
                  <html:hidden name="neuronType" property="cdNucleus" indexed="true"/>
                  <html:hidden name="neuronType" property="category" indexed="true"/></td></tr>
                  
                  <logic:equal name="neuronType" property="active" value="true">
                  
                  <tr>
                  <td colspan="8"><table width="100%"  bgcolor="#DAEAEB"><tr>
                  <td height="30" colspan="2" align="left" bgcolor="#CBE0E2">
                    └► Basic drive:
                    </td>
                    
                <td height="30" align="center" bgcolor="#CBE0E2"><font size="2" face="Arial, Helvetica, sans-serif">Axons:</font><html:text name="neuronType" property="quantity" size="2" indexed="true"/></td>
                
                <td colspan="2" height="30" align="center" bgcolor="#CBE0E2"><font size="2" face="Arial, Helvetica, sans-serif"> Distribution:</font><html:select name="neuronType" property="distribution" indexed="true" onchange="submit();">
                  <html:option value="Gm">Gamma</html:option>
                   <html:option value="P">Poisson</html:option>
                   <html:option value="G">Gaussian</html:option>
                </html:select></td>
                <td height="30" align="center" bgcolor="#CBE0E2"><font size="2" face="Arial, Helvetica, sans-serif">ISI mean:</font><html:text name="neuronType" property="mean" size="2" indexed="true"/><font size="2" face="Arial, Helvetica, sans-serif">[ms]</font></td>
                <logic:equal name="neuronType" property="distribution" value="Gm">
                <td height="30" align="center" bgcolor="#CBE0E2"><font size="2" face="Arial, Helvetica, sans-serif">
                Order:
                
                </font><html:text name="neuronType" property="std" size="2" indexed="true"/><font size="2" face="Arial, Helvetica, sans-serif"></td>
                </logic:equal>
                <logic:equal name="neuronType" property="distribution" value="G">
                <td height="30" align="center" bgcolor="#CBE0E2"><font size="2" face="Arial, Helvetica, sans-serif">
                Std:
                
                </font><html:text name="neuronType" property="std" size="2" indexed="true"/><font size="2" face="Arial, Helvetica, sans-serif"></td>
                </logic:equal>
                
                <td colspan="4" height="30" align="center" bgcolor="#CBE0E2"><font size="2" face="Arial, Helvetica, sans-serif">Modulation:</font><html:select name="neuronType" property="cdSignal" indexed="true" onchange="submit();">
                  <html:option value="none">none</html:option>
                  <html:option value="constant">constant</html:option>
                  <html:option value="ramp">ramp</html:option>
                  <html:option value="random">random</html:option>
                  <html:option value="sine">sine</html:option>
                  <html:option value="square">pulse</html:option>
                  <html:option value="trapezoid">trapezoid</html:option>
                  <html:option value="triangle">triangle</html:option>
                   <html:option value="AM">AM</html:option>
                </html:select></td>
              </tr></table></td></tr>
              </logic:equal>
              
              <html:hidden name="neuronType" property="cdNucleus" indexed="true" />
              
              
              <logic:equal name="neuronType" property="active" value="true">
              <logic:notEqual name="neuronType" property="cdSignal" value="">
                    <logic:notEqual name="neuronType" property="cdSignal" value="none">
              <tr><td colspan="8"><table width="100%" bgcolor="#DAEAEB"><tr>
                    <td height="30" colspan="2" align="left" bgcolor="#CBE0E2">
                    └► Modulating signal:
                    </td>
                    
                    <td height="30" align="center" bgcolor="#CBE0E2"><font size="2" face="Arial, Helvetica, sans-serif">Target:</font>
                    <html:select name="neuronType" property="modType" indexed="true" onchange="submit();">
                  <html:option value="ISI">ISI</html:option>
                  <html:option value="rate">rate</html:option>
                </html:select>
                </td>
                    
                    <td height="30" align="center" valign="middle" bgcolor="#CBE0E2"><font size="2" face="Arial, Helvetica, sans-serif">
                    Start:</font><html:text name="neuronType" property="ini" size="5" indexed="true"/><font size="2" face="Arial, Helvetica, sans-serif">[ms]</font></td>
                      <td height="30" align="center" valign="middle" bgcolor="#CBE0E2"><font size="2" face="Arial, Helvetica, sans-serif">
                    Stop:</font><html:text name="neuronType" property="fin" size="5" indexed="true"/><font size="2" face="Arial, Helvetica, sans-serif">[ms]</font></td>
                      <td height="30" align="center" valign="middle" bgcolor="#CBE0E2"><font size="2" face="Arial, Helvetica, sans-serif">
                    Peak:</font><html:text name="neuronType" property="amp" size="5" indexed="true"/><font size="2" face="Arial, Helvetica, sans-serif"><logic:equal name="neuronType" property="modType" value="ISI">[ms]</logic:equal>
                    <logic:equal name="neuronType" property="modType" value="rate">[Hz]</logic:equal>
                     </font></td>
                     
                     <logic:notEqual name="neuronType" property="cdSignal" value="ramp">
                     <logic:notEqual name="neuronType" property="cdSignal" value="constant">
                        
                        <logic:notEqual name="neuronType" property="cdSignal" value="trapezoid">
                        <logic:notEqual name="neuronType" property="cdSignal" value="random">
                        <logic:notEqual name="neuronType" property="cdSignal" value="triangle">
                      <td height="30" align="center" valign="middle" bgcolor="#CBE0E2"><font size="2" face="Arial, Helvetica, sans-serif">
                    Frequency:</font><html:text name="neuronType" property="freq" size="5" indexed="true"/><font size="2" face="Arial, Helvetica, sans-serif">[Hz]</font> 
                      </td>
                      </logic:notEqual>
                      </logic:notEqual>
                      </logic:notEqual>
                      </logic:notEqual>
                      </logic:notEqual>
                      
                    <logic:notEqual name="neuronType" property="cdSignal" value="ramp">
                    <logic:notEqual name="neuronType" property="cdSignal" value="constant">                        
                    <logic:notEqual name="neuronType" property="cdSignal" value="trapezoid">
                    <logic:notEqual name="neuronType" property="cdSignal" value="random">
                    <logic:notEqual name="neuronType" property="cdSignal" value="triangle">
                    <logic:notEqual name="neuronType" property="cdSignal" value="pulse">
                      
                    <td height="30" align="center" valign="middle" bgcolor="#CBE0E2"><font size="2" face="Arial, Helvetica, sans-serif">
                    Delay:</font><html:text name="neuronType" property="delay" size="5" indexed="true"/><font size="2" face="Arial, Helvetica, sans-serif">[ms]</font> 
                    </td>
                    </logic:notEqual>
                    </logic:notEqual>
                    </logic:notEqual>
                    </logic:notEqual>
                    </logic:notEqual>
                    </logic:notEqual>
                       
                     
                     <logic:notEqual name="neuronType" property="cdSignal" value="ramp">
                     <logic:notEqual name="neuronType" property="cdSignal" value="constant">
                     <logic:notEqual name="neuronType" property="cdSignal" value="sine">
                     <logic:notEqual name="neuronType" property="cdSignal" value="pulse">
                     <logic:notEqual name="neuronType" property="cdSignal" value="trapezoid">
                     <logic:notEqual name="neuronType" property="cdSignal" value="random">
                     <logic:notEqual name="neuronType" property="cdSignal" value="triangle">
                     <td height="30" align="center" valign="middle" bgcolor="#CBE0E2"><font size="2" face="Arial, Helvetica, sans-serif">
                    Modulation Frequency:</font><html:text name="neuronType" property="modFreq" size="5" indexed="true"/><font size="2" face="Arial, Helvetica, sans-serif">[Hz]</font> 
                      </td>
                      </logic:notEqual>
                      </logic:notEqual>
                      </logic:notEqual>
                      </logic:notEqual>
                      </logic:notEqual>
                     </logic:notEqual>
                      </logic:notEqual>
                      
                       <logic:notEqual name="neuronType" property="cdSignal" value="ramp">
                     <logic:notEqual name="neuronType" property="cdSignal" value="constant">
                     <logic:notEqual name="neuronType" property="cdSignal" value="sine">
                     <logic:notEqual name="neuronType" property="cdSignal" value="pulse">
                     <logic:notEqual name="neuronType" property="cdSignal" value="trapezoid">
                     <logic:notEqual name="neuronType" property="cdSignal" value="random">
                     <logic:notEqual name="neuronType" property="cdSignal" value="triangle">
                     <td height="30" align="center" valign="middle" bgcolor="#CBE0E2"><font size="2" face="Arial, Helvetica, sans-serif">
                    Modulation Factor:</font><html:text name="neuronType" property="modFactor" size="5" indexed="true"/><font size="2" face="Arial, Helvetica, sans-serif"></font> 
                      </td>
                      </logic:notEqual>
                      </logic:notEqual>
                      </logic:notEqual>
                      </logic:notEqual>
                      </logic:notEqual>
                     </logic:notEqual>
                      </logic:notEqual>
                     
                      
                      <logic:notEqual name="neuronType" property="cdSignal" value="constant">
                        <logic:notEqual name="neuronType" property="cdSignal" value="random">
                        <logic:notEqual name="neuronType" property="cdSignal" value="sine">
                        <logic:notEqual name="neuronType" property="cdSignal" value="AM">
                      <td height="30" align="center" valign="middle" bgcolor="#CBE0E2">
                      <font size="2" face="Arial, Helvetica, sans-serif">
                    <logic:equal name="neuronType" property="cdSignal" value="square">
                    Width:</logic:equal><logic:equal name="neuronType" property="cdSignal" value="ramp">Time-to-peak:</logic:equal><logic:equal name="neuronType" property="cdSignal" value="trapezoid">Time-to-peak and Plateau:</logic:equal><logic:equal name="neuronType" property="cdSignal" value="triangle">Time-to-peak:</logic:equal></font><html:text name="neuronType" property="width" size="5" indexed="true"/><font size="2" face="Arial, Helvetica, sans-serif">[ms]</font></td>
                      </logic:notEqual>
                      </logic:notEqual>
                      </logic:notEqual>
                      </logic:notEqual>
                      
                      
                        
                        
                      </tr></table></td></tr>
                    </logic:notEqual>
                      </logic:notEqual>
                      </logic:equal>
                      <html:hidden name="neuronType" property="ini" indexed="true" />
              <html:hidden name="neuronType" property="fin" indexed="true" />
              <html:hidden name="neuronType" property="modType" indexed="true" />
              <html:hidden name="neuronType" property="amp" indexed="true" />
              
              
              <html:hidden name="neuronType" property="quantity" indexed="true" />
              
              
              <html:hidden name="neuronType" property="cdSignal" indexed="true" />
              
              <html:hidden name="neuronType" property="distribution" indexed="true" />
              
              <html:hidden name="neuronType" property="mean" indexed="true" />
              
              <html:hidden name="neuronType" property="std" indexed="true" />
            </logic:iterate>
          </table>
  </td>
  </tr>
  
  
  
  
</table>  <b><font size="4" face="Arial, Helvetica, sans-serif"></font></b></td></tr>
</table>

<table width="100%"><tr><td align="center" valign="middle">
<div id="CollapsiblePanel2" class="CollapsiblePanel">
  <div class="CollapsiblePanelTab" tabindex="0"><font size="2" face="Arial, Helvetica, sans-serif"><b>Synaptic noise (click here to expand/collapse)</b></font></div>
  <div class="CollapsiblePanelContent">
    <table align="center" border="0" bgcolor="#DAEAEB">
      <tr align="center" valign="middle">
        <td bgcolor="#EEF5F7"></td>
        <td valign="middle" bgcolor="#CBE0E2"><font size="2" face="Arial, Helvetica, sans-serif"><b>Type</b></font></td>
        <td width="100" height="50" align="center" valign="middle" bgcolor="#CBE0E2"><font size="2" face="Arial, Helvetica, sans-serif">ISI mean [ms]</font></td>
      </tr>
      <logic:iterate name="InputForm" property="noises" id="noise" indexId="index">
        <tr align="left" valign="top" bgcolor="#EEF5F7">
          <td valign="middle"><html:checkbox name="noise" property="active" indexed="true"/></td>
          <td height="30" align="left" valign="middle"><bean:write name="noise" property="tractName"/></td>
          <td height="30" align="center" valign="middle"><html:text name="noise" property="mean" size="3" indexed="true" /></td>
          <html:hidden name="noise" property="type" indexed="true"/>
          <html:hidden name="noise" property="category" indexed="true"/>
          <html:hidden name="noise" property="cdNucleus" indexed="true"/>
          <html:hidden name="noise" property="distribution" indexed="true"/>
          <html:hidden name="noise" property="quantity" indexed="true"/>
        </tr>
      </logic:iterate>
    </table>
  </div>
</div>

</td></tr></table>

<table width="100%">
<tr><td width="50%" height="50" align="center" valign="middle" bgcolor="#D1DCDC">

	<IMG SRC="/remoto/servlet/ServletChartGenerator?cdSimulation=inputHist&id=<%=Math.random()%>" alt="Input graphic" BORDER=0/>

      
      </td>
      <td width="50%" height="320" align="center" valign="middle" bgcolor="#D1DCDC">
          
          <IMG SRC="/remoto/servlet/ServletChartGenerator?cdSimulation=input&id=<%=Math.random()%>" alt="Input graphic" BORDER=0/>
          
 </td></tr>
 
 
</table>



<p>
  <input type="button" value="  Apply  " onclick="submit();" />
</p>

    </html:form>
  </div>
  </p>
  
  <script type="text/javascript">
var CollapsiblePanel2 = new Spry.Widget.CollapsiblePanel("CollapsiblePanel2", {contentIsOpen:false});
	
  </script> <!-- InstanceEndEditable --></div>
<div id="links">
			<hr /><a href="http://www.usp.br/" target="_blank">USP</a> |
			<a href="http://www.poli.usp.br/" target="_blank">POLI</a> |
<a href="http://www.leb.usp.br/" target="_blank">LEB</a><hr />
		</div>
<div id="footer">
			Copyright &copy; 2012 Biomedical Engineering Laboratory. All rights 
			reserved.</div>
<script type="text/javascript">
var MenuBar = new Spry.Widget.MenuBar("MenuBar", {imgDown:"SpryAssets/SpryMenuBarDownHover.gif", imgRight:"SpryAssets/SpryMenuBarRightHover.gif"});
</script>
</body>
<!-- InstanceEnd --></html>
