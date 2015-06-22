<%@ page contentType="text/html;charset=utf-8"%>
<%@ page import="java.util.*,br.remoto.model.vo.Summary"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>

<%
String cdSimulation = (String)request.getAttribute( "cdSimulation" );
%>

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
.result {
	background-color: #F5F9FA;
	height: auto;
	width: 990px;
	margin-right: auto;
	margin-left: auto;
	border: medium solid #C0DCE2;
	padding-top: 30px;
	padding-right: 5px;
	padding-bottom: 5px;
	padding-left: 5px;
}
</style>


<script src="SpryAssets/SpryTooltip.js" type="text/javascript"></script>
<script>

function change()
{
	document.ResultsForm.action.value = "change";
	document.ResultsForm.target = "";
	document.ResultsForm.submit();
}


function post(option)
{
	document.ResultsForm.opt.value = option;
	
	if( option == 'chart' )
	{
			document.ResultsForm.target = "_blank";
			document.ResultsForm.submit();
	}	
	else if( option == 'chart_img' )
	{
			document.ResultsForm.target = "";
			document.ResultsForm.submit();
	}	
	else if( option == 'file' )
	{
			document.ResultsForm.target = "_blank";
			document.ResultsForm.submit();
	}
	else if( option == 'chart_img_new' )
	{
			document.ResultsForm.target = "_blank";
			document.ResultsForm.submit();
	}
	else
	{
		document.ResultsForm.target = "_blank";
		document.ResultsForm.submit();
	}
	
}


</script>
<style type="text/css">
@import url("SpryAssets/SpryCollapsiblePanel_results.css");
</style>
<link href="hint.css" rel="stylesheet" type="text/css" />
<link href="SpryAssets/SpryTooltip.css" rel="stylesheet" type="text/css" />
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
        <li><a href="/remoto/biomechanicalInput.do">Biomechanical Input</a></li>
      </ul>
    </li>
    <li><a href="/remoto/start.do">Run</a></li>
    <li><a href="/remoto/results.do?opt=chart_img">Results</a>      </li>
    <li><a href="/remoto/login.do">Login</a></li>
  </ul>
</div>
  <!-- InstanceBeginEditable name="EditRegion1" -->
<p>
<div class="result"><html:form method="post" action="/results">
	<html:hidden name="ResultsForm" property="action" value="" />
	<html:hidden name="ResultsForm" property="opt" />
	<input type="hidden" name="serialNumber" value="<%=Math.random()%>">

	<table width="100%">
		<tr>
			<td height="70" colspan=2 align="center" valign="top"><b><font
				size="5" face="Arial, Helvetica, sans-serif"> Simulation
			Results </font></b></td>
		</tr>
        
        
        
        
		<tr>


			<td height="50" align="center" valign="middle"
				bgcolor="#EEF5F7">

			<table width="100%" bgcolor="#DAEAEB">
            
            
                
				<tr>
					<td colspan="2" height="50" align="left" bgcolor="#EEF5F7"><b> &nbsp;&nbsp;&nbsp;Result type:</b>
                      <html:select
						name="ResultsForm" property="cdType" onchange="post('chart_img');">
                    <html:option value="biomechanical">Biomechanical Elements</html:option>
                    <html:option value="neural"> Neural Elements</html:option>
                    <html:option value="properties">Properties</html:option>
                    <html:option value="spikesMNs">Spikes MNs</html:option>
                    <html:option value="spikesIas">Spikes Ias</html:option>
                    <html:option value="forces">Forces</html:option>
                    <html:option value="lengths">Lengths</html:option>
                    <html:option value="general">General</html:option>
                    <html:option value="generalForSoleus">generalForSoleus</html:option>
                    <html:option value="generalForMG">generalForMG</html:option>
                    <html:option value="generalForLG">generalForLG</html:option>
                    <html:option value="generalForTA">generalForTA</html:option>
                    
                    
                    </html:select></td>
				</tr>
                
                <tr>
					<td colspan="2" height="50" align="left" bgcolor="#EEF5F7"><b>&nbsp;&nbsp;&nbsp;Joint:</b> <bean:write name="ResultsForm" property="cdJointType"></bean:write></td>
				</tr>
               
                <tr>
					<td colspan="2" height="50" align="left" bgcolor="#EEF5F7"><b>&nbsp;&nbsp;&nbsp;Muscle:</b> <html:select
						name="ResultsForm" property="cdMuscle" onchange="post('chart_img');">
						<html:optionsCollection name="ResultsForm" property="muscles"
							label="name" value="cd" />
					</html:select></td>
				</tr>
                
                
                <logic:equal name="ResultsForm" property="cdType" value="neural">
                <tr>
                  <td colspan="2"><table width="100%" bgcolor="#EEF5F7"><tr>
					<td height="30" colspan="1" align="left" valign="bottom" bgcolor="#EEF5F7"><b>&nbsp;&nbsp;Neural element:</b> <html:select
						name="ResultsForm" property="cdNeuralType" onchange="post('chart_img');">
						 <html:option value="mns"> Motoneurons</html:option>
                        <html:option value="afs"> Afferent Fibers</html:option>
                        <html:option value="dts"> Descending Tracts</html:option>
                        <html:option value="ins"> Interneurons</html:option>
                     
                        
                        
					</html:select></td></tr>
                    </table></td>
                  </tr>
                </logic:equal>
                
                <tr><td bgcolor="#EEF5F7" colspan="2" align="center">
                    
                    └►&nbsp;<html:select name="ResultsForm" property="cdNeuron" onchange="post('chart_img');">
						
                        <html:optionsCollection name="ResultsForm" property="cdNeurons"
							label="name" value="cd" />
                        
                        
					</html:select>
                  </td></tr>
                
                
                
                <tr>
                
					<td colspan="1" height="50" align="left" bgcolor="#EEF5F7"><b>&nbsp;&nbsp;&nbsp;Graphic:</b> <html:select name="ResultsForm" property="cdGraphicType" onchange="post('chart_img');">
						
                        <html:optionsCollection name="ResultsForm" property="resultTypes"
							label="name" value="cd" />
					</html:select>
                    
                    
                  
                    <br />
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    └►&nbsp;<html:select name="ResultsForm" property="cdSpecification" onchange="post('chart_img');">
						
                        <html:optionsCollection name="ResultsForm" property="cdSpecifications"
							label="name" value="cd" />
                        
					</html:select>
                    
              
              
                    </td>
                          
                          <td height="50" colspan="1" align="center" valign="middle" bgcolor="#D1DCDC">
				<input type="button" onclick="post('chart_img');" value="Plot" />
			</td>
			  </tr>
              <logic:equal name="ResultsForm" property="cdGraphicType" value="EMG">
              <tr><td bgcolor="#EEF5F7" colspan="2" align="center">
                    
                    <table width="400">
          <tr>
            <td>Sampling rate:&nbsp; <html:select name="ResultsForm"
					property="sample">
              <html:optionsCollection name="ResultsForm" property="listSamples"
						label="label" value="value" />
              </html:select> kHz 
                <br />              
              <html:checkbox name="ResultsForm" property="withEMGfiltering" onchange="post('chart_img');"/> EMG filtering <br />
              
              <logic:equal name="ResultsForm" property="withEMGfiltering" value="true">
              <table>
              <tr><td>
             
                &nbsp;&nbsp;&nbsp;low-freq
                <html:text name="ResultsForm" size="4" property="fcLow" />Hz
                
                &nbsp;&nbsp;&nbsp;high-freq
                <html:text name="ResultsForm" size="4" property="fcHigh" />
                Hz 
                  <html:hidden name="ResultsForm" property="fcLow" />
                  <html:hidden name="ResultsForm" property="fcHigh" />
                  <br />
                  
                  </td></tr>
                  
                  <tr>
                    <td>&nbsp;&nbsp;&nbsp;<html:radio name="ResultsForm"
								property="filter" value="1st2nd" /> 1st order low - 2nd order
                      high</td>
                    </tr>
                  <tr>
                    <td>&nbsp;&nbsp;&nbsp;<html:radio name="ResultsForm"
								property="filter" value="3rd3rd" /> 3rd order low - 3rd order
                      high</td>
                    </tr>
                    </table>
              
              </logic:equal>
              
              <html:checkbox name="ResultsForm" property="withEMGnoise" value="on"/> EMG
              with noise <br />
              
              <html:checkbox name="ResultsForm" property="withEMGattenuation" value="on"/>
              EMG with amplitude attenuation / lowpass filtering <br />
              
              </td>
            </tr>
          </table>
          
                  </td></tr>
                  </logic:equal>
                  
              	<tr>
                  <td colspan="2"><table width="100%">
                <tr>
            <td colspan="1" align="left" valign="middle" bgcolor="#D1DCDC">
                &nbsp;&nbsp;&nbsp;<html:checkbox name="ResultsForm" property="analysis" onchange="post('chart_img');"/>
                &nbsp;Analysis</td>
                
                
                <logic:equal name="ResultsForm" property="analysis" value="true">
                <td align="left" valign="middle" bgcolor="#D1DCDC">
                
                &nbsp;&nbsp;
				 <html:select
						name="ResultsForm" property="cdAnalysis" onchange="post('chart_img');">
						<html:option value="parameters">Basic statistical parameters</html:option>
                        <html:option value="fft">FFT</html:option>
					</html:select>
						</td>
                </logic:equal>
                
                
            </tr>
            
            <logic:equal name="ResultsForm" property="analysis" value="true">
            <logic:equal name="ResultsForm" property="cdAnalysis" value="parameters">
                <tr>
            	<td colspan=2 bgcolor="#D1DCDC">
            	<table width="100%" bgcolor="#D1DCDC">
					<tr>
						<td bgcolor="#B6C7C7" colspan=3>&nbsp;&nbsp;Basic statistical parameters:</td>
					</tr>
					<tr>
						
                        <td>
                        	<html:checkbox name="ResultsForm" property="numOfPoints"/>&nbsp;Number of points
                         </td>
					</tr>
                    <tr>
						
                        <td>
                        	<html:checkbox name="ResultsForm" property="mean"/>&nbsp;Mean
                         </td>
					</tr>
                    <tr>
						
                        <td>
                        	<html:checkbox name="ResultsForm" property="variance"/>&nbsp;Variance
                         </td>
					</tr>
                    <tr>
						
                        <td>
                        	<html:checkbox name="ResultsForm" property="std"/>&nbsp;Standard deviation
                         </td>
					</tr>
                    <tr>
						
                        <td>
                        	<html:checkbox name="ResultsForm" property="CV"/>&nbsp;Coeficient of variation
                         </td>
					</tr>
                    <tr>
						
                        <td>
                        	<html:checkbox name="ResultsForm" property="min"/>&nbsp;Minimum value
                         </td>
					</tr>
                    <tr>
						
                        <td>
                        	<html:checkbox name="ResultsForm" property="max"/>&nbsp;Maximum value
                         </td>
					</tr>
				</table>
               </td>
              </tr>
                </logic:equal>
                </logic:equal>
                
                <logic:equal name="ResultsForm" property="analysis" value="true">
            <logic:equal name="ResultsForm" property="cdAnalysis" value="fft">
            
            	<tr>
            	<td colspan=2 bgcolor="#D1DCDC">
            	<table width="100%" bgcolor="#D1DCDC">
					<tr>
						<td bgcolor="#B6C7C7" colspan=2>&nbsp;&nbsp;Parameters:</td>
					</tr>
					<tr>
						<td width="50%" align="left" bgcolor="#D1DCDC">&nbsp;&nbsp;Sampling rate [Hz]:&nbsp;<bean:write name="ResultsForm" property="samplingRateFFT"/></td>
					
					</tr>
                    
                    <tr>
						<td width="50%" align="left" bgcolor="#D1DCDC">&nbsp;&nbsp;Number of points used in FFT:&nbsp;<html:text name="ResultsForm" property="numOfPointsFFT" size="6"/></td>
					
						
					</tr>
                    <tr>
						
                        <td>
                        	&nbsp;<html:checkbox name="ResultsForm" property="periodicSignal"/>&nbsp;Periodic signal
                         </td>
					</tr>
				</table>
               </td>
              </tr>
              
                <tr>
            	<td colspan=2 bgcolor="#D1DCDC">
            	<table width="100%" bgcolor="#D1DCDC">
					<tr>
						<td bgcolor="#B6C7C7" colspan=2>&nbsp;&nbsp;Axis range:</td>
					</tr>
					<tr>
						<td width="50%" align="left" bgcolor="#D1DCDC">&nbsp;&nbsp;X axis:&nbsp;<html:text name="ResultsForm" property="xiniFFT" size="5"/>&nbsp;to <html:text name="ResultsForm" property="xfinFFT" size="5"/></td>
					
						<td align="left" bgcolor="#D1DCDC">&nbsp;&nbsp;Y axis:&nbsp;<html:text name="ResultsForm" property="yiniFFT" size="5"/>&nbsp;to <html:text name="ResultsForm" property="yfinFFT" size="5"/></td>
					</tr>
				</table>
               </td>
              </tr>
                
                </logic:equal>
                </logic:equal>
                
              <logic:notEqual name="ResultsForm" property="cdAnalysis" value="parameters">
              	
                <html:hidden name="ResultsForm" property="numOfPoints" />
                <html:hidden name="ResultsForm" property="mean" />
                <html:hidden name="ResultsForm" property="variance" />
                <html:hidden name="ResultsForm" property="std" />
                <html:hidden name="ResultsForm" property="CV" />
                
              </logic:notEqual>
              
              
              <logic:notEqual name="ResultsForm" property="cdAnalysis" value="fft">
              	<html:hidden name="ResultsForm" property="xiniFFT" />
				<html:hidden name="ResultsForm" property="xfinFFT" />
				<html:hidden name="ResultsForm" property="yiniFFT" />
				<html:hidden name="ResultsForm" property="yfinFFT" />
              	<html:hidden name="ResultsForm" property="samplingRateFFT" />
                <html:hidden name="ResultsForm" property="periodicSignal" />
              </logic:notEqual>
              
              
              <logic:notEqual name="ResultsForm" property="advancedSettings" value="true">
              	<html:hidden name="ResultsForm" property="xini" />
				<html:hidden name="ResultsForm" property="xfin" />
				<html:hidden name="ResultsForm" property="yini" />
				<html:hidden name="ResultsForm" property="yfin" />
                <html:hidden name="ResultsForm" property="numSubplots" />
                <html:hidden name="ResultsForm" property="cdSubplot" />
                <html:hidden name="ResultsForm" property="holdOn" />
                <html:hidden name="ResultsForm" property="chartHeigth" />
                <html:hidden name="ResultsForm" property="chartWidth" />
                <html:hidden name="ResultsForm" property="titleSize" />
                <html:hidden name="ResultsForm" property="xLabelFontSize" />
                <html:hidden name="ResultsForm" property="yLabelFontSize" />
                <html:hidden name="ResultsForm" property="legendFontSize" />
                <html:hidden name="ResultsForm" property="xLabelNumberSize" />
                <html:hidden name="ResultsForm" property="yLabelNumberSize" />
                <html:hidden name="ResultsForm" property="graphColor" />
                <html:hidden name="ResultsForm" property="darker" />
                <html:hidden name="ResultsForm" property="titleLabel" />
                <html:hidden name="ResultsForm" property="xLabel" />
                <html:hidden name="ResultsForm" property="yLabel" />
                <html:hidden name="ResultsForm" property="legendLabel" />
                <html:hidden name="ResultsForm" property="cdSpecification" />
                
              </logic:notEqual>
            
            
              </table></td></tr>
              
                <tr>
                  <td colspan="2"><table width="100%">
                <tr>
            <td colspan="3" align="left" valign="middle" bgcolor="#D1DCDC">
                &nbsp;&nbsp;&nbsp;<html:checkbox name="ResultsForm" property="advancedSettings" onchange="post('chart_img');"/>&nbsp; Advanced Graphic Settings
            
			</td>
            </tr>
            
            
            
                
                <logic:equal name="ResultsForm" property="advancedSettings" value="true">
                
                <tr>
            	<td colspan=2 bgcolor="#D1DCDC">
            	<table width="100%" bgcolor="#D1DCDC">
					<tr>
						<td bgcolor="#B6C7C7" colspan=3>&nbsp;&nbsp;Subplots:</td>
					</tr>
					<tr>
						<td width="50%" align="left" bgcolor="#D1DCDC">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Number of subplots:
                      <html:select
						name="ResultsForm" property="numSubplots" onchange="post('chart_img');">
						<html:option value="1">1</html:option>
                        <html:option value="2">2</html:option>
                        <html:option value="3">3</html:option>
                        <html:option value="4">4</html:option>
                        <html:option value="5">5</html:option>
                        <html:option value="6">6</html:option>
					</html:select></td>
					
                    <td align="left" valign="middle" bgcolor="#D1DCDC">
                
                &nbsp;&nbsp;
				 <html:select
						name="ResultsForm" property="cdSubplot">
						<html:optionsCollection name="ResultsForm" property="subplots"
							label="name" value="cd" />
					</html:select>
						</td>
                        <td>
                        	<html:checkbox name="ResultsForm" property="holdOn"/>&nbsp;Hold On
                         </td>
					</tr>
				</table>
               </td>
              </tr>
            
            <tr>
            	<td colspan=2 bgcolor="#D1DCDC">
            	<table width="100%" bgcolor="#D1DCDC">
					<tr>
						<td bgcolor="#B6C7C7" colspan=3>&nbsp;&nbsp;Color:</td>
					</tr>
					<tr>
                    	<td align="left" valign="middle" bgcolor="#D1DCDC">&nbsp;&nbsp;&nbsp;
				<html:checkbox name="ResultsForm" property="randomColors" onchange="post('chart_img');"/>
				&nbsp;Random</td>
                
                <logic:notEqual name="ResultsForm" property="randomColors" value="true">
						<td align="left" valign="middle" bgcolor="#D1DCDC">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Plot color:&nbsp;<html:select
						name="ResultsForm" property="graphColor">
						<html:option value="red">Red</html:option>
                        <html:option value="blue">Blue</html:option>
                        <html:option value="green">Green</html:option>
                        <html:option value="yellow">Yellow</html:option>
                        <html:option value="black">Black</html:option>
                        <html:option value="cyan">Cyan</html:option>
					</html:select></td>
				</logic:notEqual>
                    <td align="left" valign="middle" bgcolor="#D1DCDC">
                
                &nbsp;&nbsp;&nbsp;
				<html:checkbox name="ResultsForm" property="darker"/>&nbsp;Darker
						</td>
					</tr>
				</table>
               </td>
              </tr>
            
            <tr>
            	<td colspan=1 bgcolor="#D1DCDC">
            	<table width="100%" bgcolor="#D1DCDC">
					<tr>
						<td colspan=2 bgcolor="#B6C7C7">&nbsp;&nbsp;Chart size:</td>
					</tr>
					<tr>
						<td width="50%" align="left" bgcolor="#D1DCDC">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Heigth [px]:&nbsp; <html:text name="ResultsForm" property="chartHeigth" size="3"/></td>
					
						<td width="50%" align="left" bgcolor="#D1DCDC">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Width [px]:&nbsp;<html:text name="ResultsForm" property="chartWidth" size="3"/></td>
						
					</tr>
				</table>
               </td>
              </tr>
              
              
            <tr>
            	<td colspan=1 bgcolor="#D1DCDC">
            	<table width="100%" bgcolor="#D1DCDC">
					<tr>
						<td bgcolor="#B6C7C7" colspan=2>&nbsp;&nbsp;Axis range:</td>
					</tr>
					<tr>
						<td width="50%" align="left" bgcolor="#D1DCDC">&nbsp;&nbsp;X axis:&nbsp;<html:text name="ResultsForm" property="xini" size="5"/>&nbsp;to <html:text name="ResultsForm" property="xfin" size="5"/></td>
					
						<td align="left" bgcolor="#D1DCDC">&nbsp;&nbsp;Y axis:&nbsp;<html:text name="ResultsForm" property="yini" size="5"/>&nbsp;to <html:text name="ResultsForm" property="yfin" size="5"/></td>
					</tr>
				</table>
               </td>
              </tr>
            
            <tr>
            	<td colspan=2 bgcolor="#D1DCDC">
            	<table width="100%" bgcolor="#D1DCDC">
					<tr>
						<td bgcolor="#B6C7C7" colspan=2>&nbsp;&nbsp;Labels:</td>
					</tr>
					<tr>
						<td width="70" align="left" bgcolor="#D1DCDC">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Title:</td>
						<td align="left" bgcolor="#D1DCDC">
                          <html:text name="ResultsForm" property="titleLabel" size="40"/></td>
                    
					</tr>
                    <tr>
						<td align="left" bgcolor="#D1DCDC">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;x axis:</td>
						<td align="left" bgcolor="#D1DCDC">
						  <html:text name="ResultsForm" property="xLabel" size="40"/></td>
                    
					</tr>
                    <tr>
						<td align="left" bgcolor="#D1DCDC">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;y axis:</td>
						<td align="left" bgcolor="#D1DCDC">
						  <html:text name="ResultsForm" property="yLabel" size="40"/></td>
                    
					</tr>
                    <tr>
						<td align="left" bgcolor="#D1DCDC">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Legend:</td>
						<td align="left" bgcolor="#D1DCDC">
						  <html:text name="ResultsForm" property="legendLabel" size="40"/></td>
                    
					</tr>
				</table>
               </td>
              </tr>
              
              
            <tr>
            	<td colspan=1 bgcolor="#D1DCDC">
            	<table width="100%" bgcolor="#D1DCDC">
					<tr>
						<td bgcolor="#B6C7C7" colspan=4>&nbsp;&nbsp;Font size:</td>
					</tr>
					<tr>
						<td width="25%" align="left" bgcolor="#D1DCDC">&nbsp;Title:&nbsp; <html:text name="ResultsForm" property="titleSize" size="1"/></td>
					
						<td width="25%" align="left" bgcolor="#D1DCDC">&nbsp;X axis:&nbsp;<html:text name="ResultsForm" property="xLabelFontSize" size="1"/></td>
                        <td width="25%" align="left" bgcolor="#D1DCDC">&nbsp;Y axis:&nbsp;<html:text name="ResultsForm" property="yLabelFontSize" size="1"/></td>
						<td width="25%" align="left" bgcolor="#D1DCDC">&nbsp;Legend:&nbsp;<html:text name="ResultsForm" property="legendFontSize" size="1"/></td>
					</tr>
				</table>
               </td>
              </tr>
              
              <tr>
            	<td colspan=1 bgcolor="#D1DCDC">
            	<table width="100%" bgcolor="#D1DCDC">
					<tr>
						<td bgcolor="#B6C7C7" colspan=4>&nbsp;&nbsp;Axis number size:</td>
					</tr>
					<tr>
						
					
						<td width="50%" align="left" bgcolor="#D1DCDC">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;X axis:&nbsp;<html:text name="ResultsForm" property="xLabelNumberSize" size="1"/></td>
                        <td width="50%" align="left" bgcolor="#D1DCDC">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Y axis:&nbsp;<html:text name="ResultsForm" property="yLabelNumberSize" size="1"/></td>
						
					</tr>
				</table>
               </td>
              </tr>
              
              
              
              
              
              
              
              </logic:equal>
              
              <logic:notEqual name="ResultsForm" property="advancedSettings" value="true">
              	<html:hidden name="ResultsForm" property="xini" />
				<html:hidden name="ResultsForm" property="xfin" />
				<html:hidden name="ResultsForm" property="yini" />
				<html:hidden name="ResultsForm" property="yfin" />
                <html:hidden name="ResultsForm" property="numSubplots" />
                <html:hidden name="ResultsForm" property="cdSubplot" />
                <html:hidden name="ResultsForm" property="holdOn" />
                <html:hidden name="ResultsForm" property="chartHeigth" />
                <html:hidden name="ResultsForm" property="chartWidth" />
                <html:hidden name="ResultsForm" property="titleSize" />
                <html:hidden name="ResultsForm" property="xLabelFontSize" />
                <html:hidden name="ResultsForm" property="yLabelFontSize" />
                <html:hidden name="ResultsForm" property="legendFontSize" />
                <html:hidden name="ResultsForm" property="xLabelNumberSize" />
                <html:hidden name="ResultsForm" property="yLabelNumberSize" />
                <html:hidden name="ResultsForm" property="graphColor" />
                <html:hidden name="ResultsForm" property="darker" />
                <html:hidden name="ResultsForm" property="titleLabel" />
                <html:hidden name="ResultsForm" property="xLabel" />
                <html:hidden name="ResultsForm" property="yLabel" />
                <html:hidden name="ResultsForm" property="legendLabel" />
                <html:hidden name="ResultsForm" property="cdSpecification" />
                
              </logic:notEqual>
            
            
              </table></td></tr>
                <logic:equal name="ResultsForm" property="cdType" value="properties">
                 <tr>
                   <td colspan="2"><table width="100%"><tr>
                 <logic:notEmpty name="ResultsForm" property="elementsProperties">
			          
			            
			            <td colspan="1" height="43" align="left" valign="middle" bgcolor="#EEF5F7"><b> &nbsp;&nbsp;&nbsp;Properties</b></td>
			            <td bgcolor="#EEF5F7"><html:select name="ResultsForm" property="cdProperties">
			                  <html:optionsCollection name="ResultsForm"
									property="elementsProperties" label="cd" value="cd" />
			                  </html:select></td>
			          </logic:notEmpty>
                 </tr></table></td></tr>
               </logic:equal>
              
              
			</table>
			</td>






			<td width="530" height="430"  align="center" valign="middle"
				bgcolor="#D1DCDC"><IMG
				SRC="/remoto/servlet/ServletChartGenerator?cdSimulation=<%=cdSimulation%>&id=<%=Math.random()%>"
				alt="Choose an option on the left menu and use the buttons below" BORDER=0 /></td>
                
                
                
                
                
		</tr>
		
        
         
        
		<tr>
		  <td colspan="5"><table width="100%">
		<tr>
			<td width="150" height="70" align="center" valign="middle"
				bgcolor="#EEF5F7">
                
				<center><input type="button" id="sprytrigger2"
					onclick="post('chart_img');" value="Refresh" /></center>
			
            
			</td>

			<td height="70" align="center" valign="middle" bgcolor="#EEF5F7">
			

				<center><input type="button" id="sprytrigger4"
					onclick="post('chart_img_new');" value="  Generate in new window  " />
				</center>

			

			<td width="233" align="center" valign="middle" bgcolor="#EEF5F7">
			

				<center><input type="button" id="sprytrigger5"
					onclick="post('chart');" value="  View as interactive graphic  " />
				</center>

			</td>

			<td width="150" height="70" align="center" valign="middle"
				bgcolor="#EEF5F7">
            
            
				<center><input type="button" id="sprytrigger3"
					onclick="post('file');" value="Export Data" /></center>
			</td>
            
            <td width="150" height="70" align="center" valign="middle"
				bgcolor="#EEF5F7">
            
            
				<center><input name="Reset" type="reset" id="sprytrigger1"
					onclick="post('summary');" value="Summary" /></center>
			</td>
            
			
		</tr>
		</table></td></tr>


	</table>
</p>






</html:form></div>

<logic:notEmpty name="ResultsForm" property="elementsProperties">
	<div class="tooltipContent" id="sprytooltip6">Click on Export Data in order to see the properties.</div>
</logic:notEmpty>
    
	<div class="tooltipContent" id="sprytooltip5">An interactive
	graphic for the selected option will be generated in new window. Use
	the mouse and the CTRL key in order to zoom and/or drag the graphic.
	This option requires a fast connection and a recent version of Java
	installed.It is better viewed in the standalone version.</div>
    
	<div class="tooltipContent" id="sprytooltip4">Visualize a larger
	version of the graphical result in new window.</div>
    
	<div class="tooltipContent" id="sprytooltip2">Visualize the
	graphical result for the selected option.</div>
    
    <div class="tooltipContent" id="sprytooltip3">Generate a text
	file with simulated results for the selected option.</div>

	<div class="tooltipContent" id="sprytooltip1">Generate a summary of the network properties.</div>
    
</p>

<script type="text/javascript">
  
	var sprytooltip2 = new Spry.Widget.Tooltip("sprytooltip2", "#sprytrigger2", {followMouse:true});

	var sprytooltip4 = new Spry.Widget.Tooltip("sprytooltip4", "#sprytrigger4", {followMouse:true});

	var sprytooltip5 = new Spry.Widget.Tooltip("sprytooltip5", "#sprytrigger5", {followMouse:true});

	var sprytooltip3 = new Spry.Widget.Tooltip("sprytooltip3", "#sprytrigger3", {followMouse:true});
	
	var sprytooltip1 = new Spry.Widget.Tooltip("sprytooltip1", "#sprytrigger1", {followMouse:true});
	
	

</script>
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
