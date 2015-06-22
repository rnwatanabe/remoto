<%@ page contentType="text/html;charset=ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>

<html xmlns:v="urn:schemas-microsoft-com:vml"
	xmlns:o="urn:schemas-microsoft-com:office:office"
	xmlns:w="urn:schemas-microsoft-com:office:word"
	xmlns:m="http://schemas.microsoft.com/office/2004/12/omml"
	xmlns="http://www.w3.org/TR/REC-html40">



<body bgcolor="#faffff" lang=PT-BR link="#006600" vlink="#006600"
	style='tab-interval: 35.4pt'>



<table width=100% height=10% border=0>
	<TR>
		<TH width=100%>
		<H1>The Muscle Spindle Simulator</H1>
	</TR>
</table>



<table width=20% height=10% border=1>
	<TR>
		<TH bgcolor="#E6F7EC">Select the type of stretch to be applied: <br>
		<br>

		<FORM ACTION="/remoto/servlet/SpindleStart" METHOD=POST><INPUT
			TYPE="hidden" NAME="initial_time" VALUE=0.0> <INPUT
			TYPE="hidden" NAME="time_step" VALUE=0.001> <INPUT
			TYPE="hidden" NAME="final_time" VALUE=3.0> <INPUT
			TYPE="hidden" NAME="start_time" VALUE=1.0> <INPUT
			TYPE="hidden" NAME="constant_value" VALUE=0.95> <INPUT
			TYPE="hidden" NAME="initial_value" VALUE=0.95> <INPUT
			TYPE="hidden" NAME="final_value" VALUE=1.08> <INPUT
			TYPE="hidden" NAME="ramp_velocity" VALUE=0.11> <INPUT
			TYPE="hidden" NAME="start_time_sin" VALUE=0.0> <INPUT
			TYPE="hidden" NAME="end_time_sin" VALUE=3.0> <INPUT
			TYPE="hidden" NAME="start_time_triang" VALUE=1.0> <INPUT
			TYPE="hidden" NAME="end_time_triang" VALUE=2.0> <INPUT
			TYPE="hidden" NAME="amplitude" VALUE=0.012> <INPUT
			TYPE="hidden" NAME="frequency" VALUE=1> <INPUT
			TYPE="hidden" NAME="phase" VALUE=90> <INPUT TYPE="hidden"
			NAME="bias" VALUE=0.95> <INPUT TYPE="hidden"
			NAME="gamma_static" VALUE=50.0> <INPUT TYPE="hidden"
			NAME="gamma_dynamic" VALUE=50.0> <INPUT TYPE="hidden"
			NAME="isRandom" VALUE="is_random"> <INPUT TYPE="hidden"
			NAME="stddev" VALUE=0.001> <INPUT TYPE="hidden"
			NAME="LPcutoff" VALUE=10.0> &nbsp;&nbsp; <SELECT
			NAME="input_type">
			<OPTION>ramp-and-hold</OPTION>
			<OPTION>sinusoidal</OPTION>
			<OPTION>triangular</OPTION>
			<OPTION>constant</OPTION>
		</SELECT> <INPUT TYPE="submit" VALUE="Confirm"></INPUT></FORM>
		</TH>
</table>

</body>
</html>
