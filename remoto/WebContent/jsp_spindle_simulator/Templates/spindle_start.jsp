
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
</style>
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
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<!-- InstanceEndEditable -->
<p>&nbsp;</p>
<p>&nbsp;</p>
</div>
<p>&nbsp;</p>
</body>
<!-- InstanceEnd -->
</html>
