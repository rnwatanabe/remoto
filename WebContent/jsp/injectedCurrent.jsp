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
	padding-right: 20px;
	padding-left: 20px;
}
</style>


<script>
function change()
{
	document.InjectedCurrentForm.task.value = "change";
	document.InjectedCurrentForm.submit();
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
	action="/injectedCurrent">

	<html:hidden name="InjectedCurrentForm" property="task"
		value="saveStimulus" />

	<table width="100%" border="0">
		<tr>
			<td colspan="4" width="515" height="90" align="center" valign="middle">
			<p><b><font size="4" face="Arial, Helvetica, sans-serif">Injected
			Current</font></b></p>
			</td>
		</tr>

		<tr>
       	  <td height="50" align="center" bgcolor="#EEF5F7"><b>Joint:
			</b> 
            <bean:write name="InjectedCurrentForm" property="cdJoint"></bean:write>
            
            
            </td>
  
  <td height="50" align="center" valign="middle"
						bgcolor="#EEF5F7"><b>Pool</b></font> <html:select
						name="InjectedCurrentForm" property="cdNucleus"
						onchange="change();">
						<html:optionsCollection name="InjectedCurrentForm"
							property="nuclei" label="name" value="cd" />
		  </html:select></td>
            
            <td>&nbsp;</td>
          
        </tr>

        
        
<tr>


          
          
		  <td colspan="3">
			<table width="100%" border="0" align="center" bgcolor="#DAEAEB">


				<tr align="left" valign="top">
					<td height="50" colspan="2" align="center" valign="middle"
						bgcolor="#CBE0E2"><font size="2"
						face="Arial, Helvetica, sans-serif"><b> Neuron</b></font></td>
					<td width="100" height="50" align="center" valign="middle"
						bgcolor="#CBE0E2"><strong><font size="2"
						face="Arial, Helvetica, sans-serif"> Compartment</font></strong></td>

					<td height="30" colspan="5" align="center" valign="middle"
						bgcolor="#DEEBEF">&nbsp;</td>

				</tr>
				<logic:iterate name="InjectedCurrentForm" property="currents"
					id="current" indexId="index">
					<tr align="left" valign="top">
						<td width="30" align="center" valign="middle" bgcolor="#EEF5F7"><html:checkbox
							name="current" property="active" indexed="true"
							onchange="submit();" /></td>
						<td width="50" height="30" align="center" valign="middle"
							bgcolor="#EEF5F7"><bean:write name="current"
							property="cdNeuronType" /></td>
					  <td height="30" align="center" valign="middle" bgcolor="#EEF5F7"><html:select
							name="current" property="compartment" indexed="true">
							<html:option value="S">soma</html:option>
							<html:option value="D">dendrite</html:option>
						</html:select></td>
						<td height="30" align="center" valign="middle" bgcolor="#EEF5F7">
						<font size="2" face="Arial, Helvetica, sans-serif">
						Amplitude:</font><html:text name="current" property="imax" size="5"
							indexed="true" /><font size="2"
							face="Arial, Helvetica, sans-serif">[nA]</font></td>
						<td height="30" align="center" valign="middle" bgcolor="#EEF5F7"><font
							size="2" face="Arial, Helvetica, sans-serif"> Modulating
						signal:</font><html:select name="current" property="cdSignal"
							indexed="true" onchange="submit();">
							<html:option value="none">none</html:option>
							<html:option value="constant">constant</html:option>
							<html:option value="ramp">ramp</html:option>
							<html:option value="random">random</html:option>
							<html:option value="sine">sine</html:option>
							<html:option value="square">pulse</html:option>
							<html:option value="trapezoid">trapezoid</html:option>
							<html:option value="triangle">triangle</html:option>
						</html:select> <html:hidden name="current" property="cdNucleus" indexed="true" />
						<html:hidden name="current" property="cdNeuronType" indexed="true" />
						<html:hidden name="current" property="index" indexed="true" /></td>

					</tr>

					<logic:equal name="current" property="active" value="true">
						<logic:notEqual name="current" property="cdSignal" value="">
							<logic:notEqual name="current" property="cdSignal" value="none">
								<tr>
									<td colspan="5">
									<table width="100%" bgcolor="#DAEAEB">
										<tr>
											<td height="30" colspan="3" align="left" bgcolor="#CBE0E2">
											└► Modulating signal:</td>

											<td height="30" align="center" valign="middle"
												bgcolor="#CBE0E2"><font size="2"
												face="Arial, Helvetica, sans-serif"> Start:</font><html:text
												name="current" property="ini" size="5" indexed="true" /><font
												size="2" face="Arial, Helvetica, sans-serif">[ms]</font></td>
											<td height="30" align="center" valign="middle"
												bgcolor="#CBE0E2"><font size="2"
												face="Arial, Helvetica, sans-serif"> Stop:</font><html:text
												name="current" property="fin" size="5" indexed="true" /><font
												size="2" face="Arial, Helvetica, sans-serif">[ms]</font></td>
											<td height="30" align="center" valign="middle"
												bgcolor="#CBE0E2"><font size="2"
												face="Arial, Helvetica, sans-serif"> Peak:</font><html:text
												name="current" property="amp" size="5" indexed="true" /><font
												size="2" face="Arial, Helvetica, sans-serif">[nA]</font></td>

											<logic:notEqual name="current" property="cdSignal"
												value="ramp">
												<logic:notEqual name="current" property="cdSignal"
													value="constant">

													<logic:notEqual name="current" property="cdSignal"
														value="random">
														<logic:notEqual name="current" property="cdSignal"
															value="trapezoid">
															<logic:notEqual name="current" property="cdSignal"
																value="triangle">
																<td height="30" align="center" valign="middle"
																	bgcolor="#CBE0E2"><font size="2"
																	face="Arial, Helvetica, sans-serif"> Frequency:</font><html:text
																	name="current" property="freq" size="5" indexed="true" /><font
																	size="2" face="Arial, Helvetica, sans-serif">[Hz]</font>
																</td>
															</logic:notEqual>
														</logic:notEqual>
													</logic:notEqual>
												</logic:notEqual>
											</logic:notEqual>

											<logic:equal name="current" property="cdSignal" value="sine">
												<td height="30" align="center" valign="middle"
													bgcolor="#CBE0E2"><font size="2"
													face="Arial, Helvetica, sans-serif"> Delay:</font><html:text
													name="current" property="delay" size="5" indexed="true" /><font
													size="2" face="Arial, Helvetica, sans-serif">[ms]</font></td>
											</logic:equal>

											<logic:notEqual name="current" property="cdSignal"
												value="constant">
												<logic:notEqual name="current" property="cdSignal"
													value="random">
													<logic:notEqual name="current" property="cdSignal"
														value="sine">
														<td height="30" align="center" valign="middle"
															bgcolor="#CBE0E2"><font size="2"
															face="Arial, Helvetica, sans-serif"> <logic:equal
															name="current" property="cdSignal" value="square">
                    Width:</logic:equal><logic:equal name="current"
															property="cdSignal" value="ramp">Time-to-peak:</logic:equal><logic:equal
															name="current" property="cdSignal" value="trapezoid">Time-to-peak and Plateau:</logic:equal><logic:equal
															name="current" property="cdSignal" value="triangle">Time-to-peak:</logic:equal></font><html:text
															name="current" property="width" size="5" indexed="true" /><font
															size="2" face="Arial, Helvetica, sans-serif">[ms]</font></td>
													</logic:notEqual>
												</logic:notEqual>
											</logic:notEqual>
										</tr>
									</table>
									</td>
								</tr>
							</logic:notEqual>
						</logic:notEqual>
					</logic:equal>

					<html:hidden name="current" property="ini" indexed="true" />
					<html:hidden name="current" property="fin" indexed="true" />
					<html:hidden name="current" property="amp" indexed="true" />
					<html:hidden name="current" property="freq" indexed="true" />
					<html:hidden name="current" property="delay" indexed="true" />
					<html:hidden name="current" property="width" indexed="true" />


				</logic:iterate>
			</table>
			</td>
            
            
  

		<tr>
        
        <td height="200" colspan="2" rowspan="1" align="center">
            <img src="motoneuron_current.png" width="475" />
          </td>
          
          
			<td colspan="3" width="795" height="320" align="center" valign="middle"
				bgcolor="#D1DCDC"><IMG
				SRC="/remoto/servlet/ServletChartGenerator?cdSimulation=injCurrent&id=<%=Math.random()%>"
				alt="Input graphic" BORDER=0 /></td>
		</tr>

	<tr><td height="90" colspan="3" align="center">
    
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
