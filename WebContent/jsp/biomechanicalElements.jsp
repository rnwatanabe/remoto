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
.stimulation1 {
	background-color: #F5F9FA;
	height: auto;
	width: 950px;
	margin-right: auto;
	margin-left: auto;
	border: medium solid #C0DCE2;
	text-align: center;
	padding-top: 0px;
	padding-right: 5px;
	padding-bottom: 0px;
	padding-left: 5px;
}
</style>
<script>

function change()
{
	document.BiomechanicalElementsForm.task.value = "change";
	document.BiomechanicalElementsForm.submit();
}

function configureMuscleModel()
{
	document.BiomechanicalElementsForm.action = 'musculotendon.do';
	document.BiomechanicalElementsForm.submit();
}
function configureJointModel()
{
	document.BiomechanicalElementsForm.action = 'joint.do';
	document.BiomechanicalElementsForm.submit();
}
</script>
<link href="hint.css" rel="stylesheet" type="text/css" />
<link href="apply.css" rel="stylesheet" type="text/css" />
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
<div class="stimulation1"><html:form method="post"
	action="/biomechanicalElements">

	<html:hidden name="BiomechanicalElementsForm" property="task"
		value="saveStimulus" />

	<table width="920" border="0">
	  <tr>
			<td colspan="2" width="515" height="90" align="center" valign="middle">
			<p><b><font size="4" face="Arial, Helvetica, sans-serif">Biomechanical Elements</font></b></p>
			</td>
		</tr>
        
        <tr><td valign="top"><table>

<tr>
					<td colspan="2" height="50" align="left" bgcolor="#EEF5F7"><b> &nbsp;&nbsp;&nbsp;Select a joint:</b> <html:select
						name="BiomechanicalElementsForm" property="cdJoint" onchange="submit();">
						<html:option value="ankle">Ankle</html:option>
                        <html:option value="mcp">Metacarpophalangeal</html:option>
                        <html:option value="wrist">Wrist</html:option>
                     
					</html:select></td>
                    
				</tr>
                
                <logic:equal name="BiomechanicalElementsForm" property="cdJoint"
					value="ankle">
                <tr>
                  <td colspan="2">
                	<table width="100%" border="0" bgcolor="#DAEAEB">
            
			<tr>
				<td width="80%" height="25" align="left" valign="middle" bgcolor="#C2DDE0">
                	&nbsp;&nbsp;&nbsp;<html:checkbox
						name="BiomechanicalElementsForm" property="solActive"
						onchange="submit();" />&nbsp;Soleus
                </td>
                
                 
			</tr>
            <tr>
				<td height="25" align="left" valign="middle" bgcolor="#C2DDE0">
                	&nbsp;&nbsp;&nbsp;<html:checkbox
						name="BiomechanicalElementsForm" property="mgActive"
						onchange="submit();" />&nbsp;Medial Gastrocnemius
                </td>
                
                 
			</tr>
            <tr>
				<td height="25" align="left" valign="middle" bgcolor="#C2DDE0">
                	&nbsp;&nbsp;&nbsp;<html:checkbox
						name="BiomechanicalElementsForm" property="lgActive"
						onchange="submit();" />&nbsp;Lateral Gastrocnemius
                </td>
                
                 
			</tr>
            <tr>
				<td height="25" align="left" valign="middle" bgcolor="#C2DDE0">
                	&nbsp;&nbsp;&nbsp;<html:checkbox
						name="BiomechanicalElementsForm" property="taActive"
						onchange="submit();" />&nbsp;Tibialis Anterior</td>
                
                
			</tr>
            
	</table>
    </td></tr>
                </logic:equal>
                
                <logic:equal name="BiomechanicalElementsForm" property="cdJoint"
					value="mcp">
                <tr>
                  <td colspan="2">
                	<table width="100%" border="0" bgcolor="#DAEAEB">
            
			<tr>
				<td width="80%" height="25" align="left" valign="middle" bgcolor="#C2DDE0">
                	&nbsp;&nbsp;&nbsp;<html:checkbox
						name="BiomechanicalElementsForm" property="fdiActive"
						onchange="submit();" />&nbsp;First Dorsal Interosseus
                </td>
                
                 
			</tr>
            <tr>
				<td height="25" align="left" valign="middle" bgcolor="#C2DDE0">
                	&nbsp;&nbsp;&nbsp;<html:checkbox
						name="BiomechanicalElementsForm" property="spiActive"
						onchange="submit();" />&nbsp;Second Palmar Interosseus
                </td>
                
                 
			</tr>
           
            
	</table>
    </td></tr>
                </logic:equal>
                
                <logic:equal name="BiomechanicalElementsForm" property="cdJoint"
					value="wrist">
                <tr>
                  <td colspan="2">
                	<table width="100%" border="0" bgcolor="#DAEAEB">
            
			<tr>
				<td width="80%" height="25" align="left" valign="middle" bgcolor="#C2DDE0">
                	&nbsp;&nbsp;&nbsp;<html:checkbox
						name="BiomechanicalElementsForm" property="fcuActive"
						onchange="submit();" />&nbsp;Flexor Carpi Ulnaris
                </td>
                
                 
			</tr>
            <tr>
				<td height="25" align="left" valign="middle" bgcolor="#C2DDE0">
                	&nbsp;&nbsp;&nbsp;<html:checkbox
						name="BiomechanicalElementsForm" property="ecrlActive"
						onchange="submit();" />&nbsp;Extensor Carpi Radialis Longus
                </td>
                
                 
			</tr>
            
            
	</table>
    </td></tr>
                </logic:equal>
                
                
                
                <tr>
					<td height="50" align="left" bgcolor="#EEF5F7"><b> &nbsp;&nbsp;&nbsp;Joint model:</b> <html:select
						name="BiomechanicalElementsForm" property="cdJointModel" onchange="submit();">
						<html:option value="isometric">Isometric</html:option>
						<html:option value="pendulum">Inverted Pendulum</html:option>
						<html:option value="position">Position Task</html:option>
                        
					</html:select></td>
                    
                    <td height="50" align="center" bgcolor="#EEF5F7"><a href="#" onclick="configureJointModel();">Configure</a></td>
                    
				</tr>
			<tr>
       	  <td height="50" align="left" bgcolor="#EEF5F7"><b>&nbsp;&nbsp;&nbsp;Muscle model:
			</b> <html:select name="BiomechanicalElementsForm" property="cdMuscleModel" onchange="submit();">
				<html:option value="SOCDS"> Second-order critically-damped system</html:option>
                <html:option value="raikova"> Raikova-Aladjov Model</html:option>
                <html:option value="hill"> Hill-type Model</html:option>
			</html:select></td>
            
            <td width="100" height="50" align="center" bgcolor="#EEF5F7"><a href="#" onclick="configureMuscleModel();">Configure</a></td>
        </tr>
	
    
    
    </table></td>
    
    <td align="center">
    <logic:equal name="BiomechanicalElementsForm" property="cdJoint" value="ankle">
   		<img src="ankle.png" width="400" />
    </logic:equal>
    <logic:equal name="BiomechanicalElementsForm" property="cdJoint" value="wrist">
   		<img src="wrist.png" width="200" />
    </logic:equal>
    <logic:equal name="BiomechanicalElementsForm" property="cdJoint" value="mcp">
   		<img src="metacarpophalangeal2.png" width="300" />
    </logic:equal>
    </td>
    
    
    </tr>
   
        
	<tr><td height="90" colspan="2" align="center">
    	<input type="button" value="  Apply  "
		onClick="submit();">
    </td></tr>
	</table>


</html:form></div>

</p>
<!-- InstanceEndEditable --></div>
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
