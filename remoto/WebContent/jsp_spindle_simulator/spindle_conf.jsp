
<%@ page contentType="text/html;charset=ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- InstanceBegin template="/jsp/Templates/template.dwt" codeOutsideHTMLIsLocked="false" -->
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<!-- InstanceBeginEditable name="doctitle" -->
<title>Untitled Document</title>
<!-- InstanceEndEditable -->
<style type="text/css">
.fisrt {
	background-color: #D5E4F9;
	height: auto;
	width: 80%;
	min-width: 1100px;
	margin-right: auto;
	margin-left: auto;
	border-top-style: double;
	border-right-style: double;
	border-bottom-style: double;
	border-left-style: double;
	font-size: 22px;
	padding-top: 50px;
	padding-right: 50px;
	padding-bottom: 0px;
	padding-left: 50px;
}

.enter {
	background-color: #D5E4F9;
	height: auto;
	width: auto;
	margin-right: auto;
	margin-left: auto;
	font-size: 42px;
	text-align: center;
}

.top {
	height: auto;
	width: 80%;
	margin-right: auto;
	margin-left: auto;
	background-color: #FFD5AA;
	vertical-align: middle;
	text-align: center;
	font-family: "Trebuchet MS", Arial, Helvetica, sans-serif;
	font-size: 36px;
	font-weight: normal;
}

.image_div {
	background-color: #FFF;
	margin-right: auto;
	margin-left: auto;
	height: 500px;
	width: 1100px;
}
</style>
<!-- InstanceBeginEditable name="head" -->
<style type="text/css">
.selector {
	height: auto;
	width: 300px;
	background-color: #BCD5F5;
	font-size: 18px;
	padding: 10px;
	border: medium solid #EDF3FC;
}

.font_size {
	font-size: 16px;
}
</style>
<script src="SpryAssets/SpryCollapsiblePanel.js" type="text/javascript"></script>
<link href="SpryAssets/SpryCollapsiblePanel_spindle.css"
	rel="stylesheet" type="text/css" />
<!-- InstanceEndEditable -->
</head>

<body bgcolor="#EDF3FC">
<div class="top">
<p><strong>The Muscle Spindle Simulator</strong></p>
</div>
<div class="fisrt"><!-- InstanceBeginEditable name="EditRegion" -->
<div class="selector">
<p>Select the type of stretch to be applied:</p>

<table width="100%">
	<tr>
		<td align="center"><html:form method="post"
			action="/spindleSimulatorConf">

			<html:select property="stretchType">
				<html:option value="ramp-and-hold">Ramp-and-hold</html:option>
				<html:option value="sinusoidal">Sinusoidal</html:option>
				<html:option value="triangular">Triangular</html:option>
				<html:option value="constant">Constant</html:option>
			</html:select>

			<input type="button" value=" Comfirm " onclick="submit();" />

		</html:form></td>
	</tr>
</table>
</div>

<div class="selector">
<p>Simulation Setup</p>
<form action="/remoto/servlet/SpindleStart" method="post">
<div id="CollapsiblePanel1" class="CollapsiblePanel">
<div class="CollapsiblePanelTab" tabindex="0">Ramp and Hold
Stretch</div>
<div class="CollapsiblePanelContent">
<table width="100%" border="0">
	<tr>
		<td class="font_size">Start time [s]:</td>
		<td align="right"><input name="start_time5" size="6" value="1.0" /></td>
	</tr>
	<tr>
		<td class="font_size">Ramp velocity [L0/s]:</td>
		<td align="right"><input name="ramp_velocity5" size="6"
			value="0.11" /></td>
	</tr>
	<tr>
		<td class="font_size">Initial value [L0]:</td>
		<td align="right"><input name="initial_value5" size="6"
			value="0.95" /></td>
	</tr>
	<tr>
		<td class="font_size">Final value [L0]:</td>
		<td align="right"><input name="final_value5" size="6"
			value="1.08" /></td>
	</tr>
</table>
<p class="font_size">obs.: L0 is the optimal fascicle length</p>

</div>
</div>
<div id="CollapsiblePanel3" class="CollapsiblePanel">
<div class="CollapsiblePanelTab" tabindex="0">Fusimotor Activation</div>
<div class="CollapsiblePanelContent">
<table width="100%" border="0">
	<tr>
		<td class="font_size">Gamma static [pps]:</td>
		<td align="right"><input name="gamma_static6" size="6"
			value="50.0" /></td>
	</tr>
	<tr>
		<td class="font_size">Gamma dynamic [pps]:</td>
		<td align="right"><input name="gamma_dynamic6" size="6"
			value="50.0" /></td>
	</tr>
</table>
</div>
</div>
<div id="CollapsiblePanel4" class="CollapsiblePanel">
<div class="CollapsiblePanelTab" tabindex="0">Noise</div>
<div class="CollapsiblePanelContent">
<table width="100%" border="0">
	<tr>
		<td colspan="2"><input type="checkbox" name="isRandom5"
			value="is_random" checked="checked" /> <span class="font_size">Apply
		gaussian random noise: </span></td>
	</tr>
	<tr>
		<td class="font_size">Standard deviation [L0]:</td>
		<td align="right"><input name="stddev5" size="6" value="0.001" /></td>
	</tr>
	<tr>
		<td class="font_size">Low pass cutoff frequency [Hz]:</td>
		<td align="right"><input name="LPcutoff5" size="6" value="10.0" /></td>
	</tr>
</table>
</div>
</div>
<div id="CollapsiblePanel2" class="CollapsiblePanel">
<div class="CollapsiblePanelTab" tabindex="0">Time Setup</div>
<div class="CollapsiblePanelContent">
<table width="100%" border="0">
	<tr>
		<td class="font_size">Initial time [s]:</td>
		<td align="right"><input name="initial_time4" size="6"
			value="0.0" /></td>
	</tr>
	<tr>
		<td class="font_size">Time step [s]:</td>
		<td align="right"><input name="time_step4" size="6" value="0.001" /></td>
	</tr>
	<tr>
		<td class="font_size">Final time [s]:&nbsp;</td>
		<td align="right"><input name="final_time4" size="6" value="3.0" /></td>
	</tr>
</table>
</div>


</div>

<br />
<br />
<table width="100%">
	<tr>
		<td width="100" align="center" valign="middle"><input
			type="submit" value="Refresh" /></td>
		<td width="100" align="center" valign="middle"><input
			type="submit" value="Reset" /></td>
		<td width="100" align="center" valign="middle"><input
			type="submit" value="Start" /></td>
	</tr>
</table>
</form>

</div>
<p>&nbsp;</p>
<p>&nbsp;</p>
<script type="text/javascript">
var CollapsiblePanel1 = new Spry.Widget.CollapsiblePanel("CollapsiblePanel1", {contentIsOpen:false});
var CollapsiblePanel2 = new Spry.Widget.CollapsiblePanel("CollapsiblePanel2", {contentIsOpen:false});
var CollapsiblePanel3 = new Spry.Widget.CollapsiblePanel("CollapsiblePanel3", {contentIsOpen:false});
var CollapsiblePanel4 = new Spry.Widget.CollapsiblePanel("CollapsiblePanel4", {contentIsOpen:false});
  </script> <!-- InstanceEndEditable -->
<p>&nbsp;</p>
<p>&nbsp;</p>
</div>
<p>&nbsp;</p>
</body>
<!-- InstanceEnd -->
</html>
